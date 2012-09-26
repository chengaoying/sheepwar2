package sheepwar;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import cn.ohyeah.stb.game.SGraphics;

/**
 * ������ҽ�ɫ��
 * @author Administrator
 *
 */
public class CreateRole implements Common {

	private int flag, index;
	private int handFlag, handIndex;
	public static int[] para = {
			/*0-x���꣬1-y���꣬2-��ȣ�3-�߶ȣ�4-�ٶȣ�5-������*/
			365, 300, 40, 50, 8, 3
	};
	/*������ҽ�ɫ*/
	public Role createSheep() {
		Role role = new Role();
		role.mapx = para[0]; 
		role.mapy = para[1];
		role.width = para[2];
		role.height = para[3];
		role.speed = para[4];
		role.lifeNum = para[5];
		role.scores = 0;
		role.hitNum = 0;
		role.status = ROLE_ALIVE;
		return role;
	}
	
	/*��Ҹ���*/
	public Role reviveSheep() {
		Role role = new Role();
		role.mapx = para[0]; 
		role.mapy = para[1];
		role.width = para[2];
		role.height = para[3];
		role.speed = para[4];
		role.status = ROLE_ALIVE;
		role.lifeNum = StateGame.lifeNum;
		role.scores = StateGame.scores;
		role.hitNum = StateGame.hitNum;
		role.hitBuble = StateGame.hitBuble;
		role.hitRatio = StateGame.hitRatio;
		role.hitTotalNum = StateGame.hitTotalNum;
		role.hitFruits = StateGame.hitFruits;
		role.useProps = StateGame.useProps;
		return role;
	}
	
	public void showSheep(SGraphics g, Role role) {
		Image sheep = Resource.loadImage(Resource.id_playing_sheep); 
		Image sheep_eye = Resource.loadImage(Resource.id_sheep_eye);
		Image sheep_hand = Resource.loadImage(Resource.id_sheep_hand);
		
		int sheepW = sheep.getWidth(), sheepH = sheep.getHeight();
		int handW = sheep_hand.getWidth()/2, handH = sheep_hand.getHeight();
		int eyeW = sheep_eye.getWidth()/2, eyeH = sheep_eye.getHeight();
		/*�����۾�Ƶ��*/
		if(flag<8){
			flag ++;
			index=1;
		}else{
			index=0;
			flag=0;
		}
		/*������Ƶ��*/
		if(handFlag<6){
			handFlag ++;
			handIndex=1;
		}else{
			handIndex=0;
			handFlag=0;
		}
		if(role.status == ROLE_ALIVE){
			g.drawRegion(sheep_hand, handIndex*handW, 0, handW, handH, 0, role.mapx-4, 36+role.mapy, 20); 
			g.drawImage(sheep, role.mapx, role.mapy, 20);
			g.drawRegion(sheep_eye, index*eyeW, 0, eyeW,	eyeH, 0, role.mapx+3, 20+role.mapy, 20);
			
		}else if(role.status == ROLE_DEATH){
			if(role.mapy<=466){
				role.mapy += 4*role.speed;
				g.drawRegion(sheep_hand, handW, 0, handW, handH, Sprite.TRANS_MIRROR_ROT180, role.mapx-4, 36+role.mapy, 20); 
				g.drawRegion(sheep, 0, 0, sheepW, sheepH, Sprite.TRANS_MIRROR_ROT180, role.mapx, role.mapy, 20);
				g.drawRegion(sheep_eye, 0, 0, eyeW,	eyeH, Sprite.TRANS_MIRROR_ROT180, role.mapx+3, 20+role.mapy, 20);
				//g.drawImage(sheep, role.mapx, role.mapy, 20);
			}else if(role.lifeNum>0){
				System.out.println("��Ҹ���");
				StateGame.own = reviveSheep();
			}
		}
	}
	
}
