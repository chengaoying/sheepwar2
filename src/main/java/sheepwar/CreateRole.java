package sheepwar;

import java.util.Vector;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.util.RandomValue;

/** ����npc(wolf)����ҽ�ɫ(sheep) */
public class CreateRole implements Common {
	public static Vector npcs = new Vector();    //��������
	public static Vector bubles=new Vector(); //����������
	private int tempx, tempy;
	
	private int[] coors = {80, 160,240,320};  //�Ǵӿ�ʼ���е�����ĵ�ĺ�����
	
	
	/* �������� */
	public static int bublePara[][] = {
	/* 0 ͼƬ��1 ͼƬ�ߣ�2 �����ٶȣ� */
	        { 45, 75, 3, }, // ��ɫ����
			{ 45, 75, 3, }, // ��ɫ����
			{ 45, 75, 3,  }, // multicolour��ɫ����
			{ 45, 75, 3, }, // ��ɫ����
			{ 45, 75, 3, }, // ��ɫ����
			{ 45, 75, 3,}, // �ƺ�ɫ����
	};

	/* ������ҽ�ɫ */
	public Role createSheep() {
		Role role = new Role();
		role.mapx = 366; // ���Ե�����ҵĿ�ʼʱ��λ��
		role.mapy = 307;
		role.width = 49;
		role.height = 59;
		role.speed = 5;
		return role;
	}

	/** ������ɫ-�� */
	public void showSheep(SGraphics g, Role role) {
		Image playing_sheep = Resource.loadImage(Resource.id_playing_sheep); // {399,
																				// 135}
		Image sheep_eye = Resource.loadImage(Resource.id_sheep_eye);
		Image sheep_hand = Resource.loadImage(Resource.id_sheep_hand);

		g.drawRegion(sheep_hand, 0, 0, sheep_hand.getWidth(),
				sheep_hand.getHeight(), 0, 359 - 14,
				154 + 50 + (role.mapy - 154), TopLeft); // sheep_hand

		g.drawRegion(playing_sheep, 0, 0, playing_sheep.getWidth(),
				playing_sheep.getHeight(), 0, 361, 154 + 2 + (role.mapy - 154),
				TopLeft); // sheep��ʼ����361,154

		g.drawRegion(sheep_eye, 0, 0, sheep_eye.getWidth(),
				sheep_eye.getHeight(), 0, 371 - 13,
				154 + 23 + (role.mapy - 154), TopLeft); // sheep_eye
	}

	/* ����npc---��wolf �� ����buble */
	public void createWolf() {
		Role role = new Role();
		Role buble=new Role();
		role.mapx = 0;
		role.mapy = 16;
		role.speed = 4;
		role.coorX = coors[RandomValue.getRandInt(0,3)];		//�������漴���ĸ����ѡ�������˶�
		role.direction = -1;          //��ʼ�����Ķ����Ǻ��� ,0 ���£�1  ����
		
		buble.mapy = role.mapy-24;      //������ʼ��Y����
		buble.speed	= role.speed;
		npcs.addElement(role);
		bubles.addElement(buble);
	}

	/* ������ */
	private int windex, wflag; // ������̬�ǵĲ���
	private int bindex, bflag; // ������̬����Ĳ���
	public void showWolf(SGraphics g, Role role,Role buble) {
		Image wolf = Resource.loadImage(Resource.id_wolf_run); // {399, 135}
		Image wolf_down = Resource.loadImage(Resource.id_wolf_down); // ���򱻻���ʱ�ǵ�ͼƬ
		Image blue = Resource.loadImage(Resource.id_balloon_blue);
		Image green = Resource.loadImage(Resource.id_balloon_green);
		Image multicolour = Resource.loadImage(Resource.id_balloon_multicolour);
		Image red = Resource.loadImage(Resource.id_balloon_red);
		Image yellow = Resource.loadImage(Resource.id_balloon_yellow);
		Image yellowRed = Resource.loadImage(Resource.id_balloon_yellowred);
		Image ladder = Resource.loadImage(Resource.id_ladder);
		long startTime =(long) (System.currentTimeMillis()/1000);
		
//		for(int i =	0;i<CreateRole.npcs.size()-1;i++){
//			role =	(Role)npcs.elementAt(i);
//		}
//		System.out.println("windex:"+windex);
		/* ������̬���� */
		if (wflag >= 0) { // ǰ�ĸ��ǳɹ���غ������ߣ����ұ߳���������
			windex = (windex + 1) % 6; // ֡��
			wflag = 0;
		} else {
			wflag++;
		}
		
		if(role.mapx == role.coorX ){     //���ض��ĵ�����ת��
			role.direction = 0;
			windex = 1;        
		}
		
		if(role.mapx ==424){      //���������ײ�������ת������ ,ǰ��ƥ�Ǵ����
			role.direction = 1;
		}
		
		if (role.direction == 0 && role.mapy<(516-wolf.getHeight())) {// ������
			tempy = role.mapy;
			
			buble.mapy = tempy-21;		//�����Y��������wolf��Y���С����
			buble.mapx = role.mapx;
			tempx = role.mapx;
			if(bindex==2){    //�����򻭵������ŵ�ʱ�������һ�������˶�
				role.mapy += role.speed;
			}
			//TODO �ж������Ƿ񱻻��У����еĻ���wolf_down��
			g.drawRegion(wolf, windex * wolf.getWidth() / 6, 0,
					wolf.getWidth() / 6, wolf.getHeight(), 0, tempx, tempy,TopLeft); // TODO ���������ʱ����ô�����ߵ�ͼƬ��Ϊ��ֹ�� ����������ʧ
			
			/* ��������ǰ���ŵ�״̬ */ //TODO ���������� ʱ�������������ѵĺ�����ͼƬЧ��
			if (bindex < 2) {
				if (bflag >= 0) {
					bindex = (bindex + 1) % 3; // ֡��
					bflag = 0;
				} else {
					bflag++;
				}
			} else {
				bindex = 2;
			}
			//TODO ��ν���ȡ��ͬ��ɫ������
			if(buble.id==1){  //��ɫ����
				g.drawRegion(blue, bindex * blue.getWidth() / 5, 0,
						blue.getWidth() / 5, blue.getHeight(), 0,
						tempx + blue.getWidth() / 5-27, tempy-28, TopLeft);
			}
			if(buble.id == 2){  //��ɫ��
				g.drawRegion(green, bindex * green.getWidth() / 5, 0,
						blue.getWidth() / 5, blue.getHeight(), 0,
						tempx + green.getWidth() / 5-27, tempy-28, TopLeft);
			}
			if(buble.id == 3){  //multicolour ��ɫ����
				g.drawRegion(multicolour, bindex * multicolour.getWidth() / 5, 0,
						multicolour.getWidth() / 5, blue.getHeight(), 0,
						tempx + multicolour.getWidth() / 5-27, tempy-28, TopLeft);
			}
			if(buble.id == 4){ //��ɫ����
				g.drawRegion(red, bindex * red.getWidth() / 5, 0,
						red.getWidth() / 5, red.getHeight(), 0,
						tempx + red.getWidth() / 5-27, tempy-28, TopLeft);
			}
			if(buble.id == 5){   //��ɫ����
				g.drawRegion(yellow, bindex * yellow.getWidth() / 5, 0,
						yellow.getWidth() / 5, yellow.getHeight(), 0,
						tempx + yellow.getWidth() / 5-27, tempy-28, TopLeft);
			}
			if(buble.id == 6){   //�ƺ�ɫ����
				g.drawRegion(yellowRed, bindex * yellowRed.getWidth() / 5, 0,
						yellowRed.getWidth() / 5, yellowRed.getHeight(), 0,
						tempx + yellowRed.getWidth() / 5-27, tempy-28, TopLeft);
			}
			g.drawRegion(multicolour, bindex * multicolour.getWidth() / 5, 0,
					multicolour.getWidth() / 5, multicolour.getHeight(), 0,
					tempx + multicolour.getWidth() / 5-27, tempy-28, TopLeft);
		}else if(role.mapy ==516-wolf.getHeight()){ //�����е��״���Ϊ������
				role.direction = -1;
				role.mapx += role.speed;
				tempx = role.mapx;
				tempy = role.mapy;
				g.drawRegion(wolf, windex * wolf.getWidth() / 6, 0,
						wolf.getWidth() / 6, wolf.getHeight(), 0, tempx, tempy,TopLeft);      
		}else if(role.direction == 1 && role.mapx ==424){ //���ڵײ����е�mapx=424ʱ��ʼ����
//			System.out.println("�Ǹ������ߵĺ����꣺"+role.mapx);
			tempx = role.mapx;
			role.mapy -= role.speed;
//			System.out.println("�Ǵ��µ��ϵ������꣺"+role.mapy);
			if(role.mapy>200-24){
				tempy = role.mapy;
			}else{
				tempy =200-24;
				windex=1;
			}
			
			g.drawRegion(ladder, 0, 0,ladder.getWidth() , ladder.getHeight(), 0,
					role.mapx, 516-ladder.getHeight(), TopLeft);       //�� ���ض�λ�÷ŵ�����
			
			g.drawRegion(wolf, windex * wolf.getWidth() / 6, 0,
					wolf.getWidth() / 6, wolf.getHeight(), 0, tempx, tempy,TopLeft);
		}
		else { //��ʼ���Ǻ���
			role.mapx += role.speed;
			tempx = role.mapx;
			tempy = role.mapy;
//			System.out.println("��ʼ�����꣺"+role.mapx);
			g.drawRegion(wolf, windex * wolf.getWidth() / 6, 0,
					wolf.getWidth() / 6, wolf.getHeight(), 0, tempx, tempy,TopLeft);
		}
		// }

		// if(tempy<(520-wolf.getHeight())){ //520���ǵ����Y����
		// if(role.direction == 1){ //��direction == 1ʱ���ǻ������˶�
		// tempx = role.mapx;
		// tempy += role.speed;
		// role.mapy = tempy;
		// } else if(role.direction == 0) { //��direction == 0ʱ���������˶�
		// tempx = role.mapx;
		// tempy -= role.speed;
		// role.mapy = tempy;
		// }else{//�ǽ���ƽ��
		// tempx+=role.speed;
		//
		// }
		// tempy = role.mapy;
		// }else{
		// tempx = role.mapx+role.speed;
		// tempy=0;
		// tempy += role.speed;
		// role.mapy = tempy;
		// }
		// if(tempx==80){
		// g.drawRegion(wolf, index*wolf.getWidth()/6, 0, wolf.getWidth()/6,
		// wolf.getHeight(), 0,
		// tempy, 82, TopLeft);
		// role.direction=1;
		// //����ǰ��������ģ��
		//
		// //�ǿ�ʼ����
		//
		// //�������û�����ƣ��ǳɹ���أ����Ը���ҵ���������
		// }else{
		// g.drawRegion(wolf, index*wolf.getWidth()/6, 0, wolf.getWidth()/6,
		// wolf.getHeight(), 0,
		// tempy+=role.speed, 82, TopLeft);
		// }

		// if(tempy-175<=80){
		// g.drawRegion(wolf, index*wolf.getWidth()/6, 0, wolf.getWidth()/6,
		// wolf.getHeight(), 0,
		// tempy-175, tempx-354, TopLeft);
		// }
		// // g.drawRegion(wolf, index*wolf.getWidth()/6, 0, wolf.getWidth()/6,
		// wolf.getHeight(), 0,
		// // tempy-175, tempx-354, TopLeft); //�Ǻ���
		// g.drawRegion(wolf_down, 0, 0, wolf_down.getWidth(),
		// wolf_down.getHeight(), 0,
		// tempx-100, tempy, TopLeft); //�ǵ���ֱ����(ҵ��ʵ���ǵ�������ʱ�������ѣ�����׹)
	}


}
