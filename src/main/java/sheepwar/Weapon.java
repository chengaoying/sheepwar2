package sheepwar;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;


/**
 * ��������
 * @author Administrator
 */
public class Weapon implements Common {
	int id;                 //����id
	int objectId;			//����������	ID
	int speedX;             //X ���ƶ��ٶ�
	int speedY;             //Y���ƶ��ٶ�
	int direction;          //�����ƶ����� 2��3��
	int harm;               //��ɵ��˺�
	int mapx;				//X������
	int mapy;				//Y������
	int width;				//�������
	int height;				//�����߶�
	float terminalX;		//�յ������
	float terminalY;		//�յ�������
	float flagx;			//��ʶx
	float flagy;			//��ʶy
	int random;				//�������
	boolean isSingle;		//��������
	
	private Vector bombs = new Vector();
	
	/**
	 * ������ͨ���� ---Shuriken
	 * @param own   �����ٶ���(����и���Ч��)
	 * @param objectId   ������ID
	 * @param mapx        ��ͨ����������
	 * @param mapy        ��ͨ����������
	 * @param direction   ��ͨ��������(2��3��)
	 * @param width       ��������ߵĿ�(���ڶ�λ�ӵ�������)
	 * @param height      ��������ߵĸ� (���ڶ�λ�ӵ�������
	 */
	public void createBomb(Role own) {
		Weapon w = new Weapon();
		w.direction = direction;
		w.objectId = objectId;
		w.mapx = own.mapx-40;  //���ڷ���ĳ�ʼX����
		w.mapy = own.mapy+45;  //���ڷ���ĳ�ʼY����
		w.speedX = 5;
		bombs.addElement(w);
	}
	
	private int bombIndex, bombFlag;
	/**
	 * ������ͨ����
	 * @param g
	 */
	public void showBomb(Graphics g){
		Image bomb = Resource.loadImage(Resource.id_bomb);
		Weapon w = null;
		int tempx, tempy;
		int len = bombs.size()-1;
		for(int i=len; i>=0; i--){
			w = (Weapon)bombs.elementAt(i);
			tempx = w.mapx;
			tempy = w.mapy;
			tempx -= w.speedX;
			w.mapx = tempx;
			
			if(bombFlag>=0){                  
				bombIndex = (bombIndex+1)%3;
				bombFlag=0;
			}else{
				bombFlag++;
			}
			g.drawRegion(bomb, bombIndex *bomb.getWidth()/3, 0, bomb.getWidth()/3, bomb.getHeight(), 0, tempx, tempy, 0);
		}
	}
	

}
