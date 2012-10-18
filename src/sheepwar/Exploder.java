package sheepwar;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;

/**
 * ±¬Õ¨Ð§¹ûÀà
 * @author Administrator
 */
public class Exploder implements Common {
	private int mapx;
	private int mapy;
	private int[] frame={0,1,0,1,2,3,4,5,6,7,};
	private int i;
	
	public Exploder(int mapx, int mapy){
		this.mapx = mapx;
		this.mapy = mapy;
	}
	
	private Image burstImage;
	public void drawExplode(SGraphics g, StateGame stateGame) {
		if (i <7) {	
			try {
				burstImage = Resource.loadImage(Resource.id_burn);
				g.drawRegion(burstImage, frame[i] * burstImage.getWidth() / 8, 0, burstImage.getWidth() / 8,
						burstImage.getHeight(), 0, mapx, mapy, 20);
				i++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
