package sheepwar;

import java.util.Vector;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;


/**
 * ��������
 * @author Administrator
 */
public class Weapon implements Common {
	int id;                 //����id
	int objectId;			//����������	ID
	int speedX;             //X���ƶ��ٶ�
	int speedY;             //Y���ƶ��ٶ�
	int direction;          //�����ƶ����� 
	int frame;				//֡��
	int mapx;				//X������
	int mapy;				//Y������
	int width;				//�������
	int height;				//�����߶�
	
	public final static int WEAPON_MOVE_LEFT = 0;
	public final static int WEAPON_MOVE_RIGHT = 1;
	public final static int WEAPON_MOVE_DOWN = 2;
	
	public Vector bombs = new Vector();
	
	/**
	 * ������ͨ���� ---Shuriken
	 * @param own  
	 * @param direction   ��ͨ��������(2��3��)
	 */
	public void createBomb(Role own, int direction) {
		Weapon w = new Weapon();
		w.direction = direction;
		w.mapx = own.mapx-20;  
		w.mapy = own.mapy+45;  
		w.width = 18;
		w.height = 19;
		w.speedX = 10;
		bombs.addElement(w);
	}
	
	/**
	 * ������ͨ����
	 * @param g
	 */
	public void showBomb(SGraphics g){
		int len = bombs.size();
		Image bomb = Resource.loadImage(Resource.id_bomb);
		Weapon w = null;
		int tempx=0, tempy=0;
		for(int i=len-1; i>=0; i--){
			w = (Weapon)bombs.elementAt(i);
			if(w.direction == WEAPON_MOVE_LEFT){
				tempx = w.mapx;
				tempy = w.mapy;
				tempx -= w.speedX;
				w.mapx = tempx;
				w.frame = (w.frame+1)%3;
			}else if(w.direction == WEAPON_MOVE_DOWN){
				tempx = w.mapx;
				tempy = w.mapy;
				tempy += w.speedY;
				w.mapy = tempy;
			}
			g.drawRegion(bomb, w.frame *bomb.getWidth()/3, 0, bomb.getWidth()/3, bomb.getHeight(), 0, tempx, tempy, 0);
		}
	}
	
	/*����ڴ��еĶ���*/
	public void clearObjects() {
      bombs.removeAllElements();
	}
}
