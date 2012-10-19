package sheepwar;

import cn.ohyeah.stb.game.SGraphics;

/**
 *动态 弹出单个得分类
 * @author Administrator
 */
public class ShowScore implements Common {
	
	private int mapx, mapy, tempy;
	public int score;
	
	public ShowScore(int mapx, int mapy){
		this.mapx = mapx;
		this.mapy = mapy-20;
		this.tempy = mapy-20;
	}
	
	public void showScore(SheepWarGameEngine engine, SGraphics g, int score) {
		if(tempy-mapy<50){
			engine.setFont(25, true);
			int col = g.getColor();
			g.setColor(0xff0000);
			g.drawString("+"+String.valueOf(score), mapx, mapy, 20);
			g.setColor(col);
			mapy -= 10;
			engine.setDefaultFont();
		}
	}
	
}
