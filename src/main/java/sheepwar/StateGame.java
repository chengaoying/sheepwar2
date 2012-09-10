package sheepwar;

import javax.microedition.lcdui.Image;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.util.Collision;
import cn.ohyeah.stb.util.RandomValue;

public class StateGame implements Common{
	
	/*���������ж��ĸ��������Ƿ�����*/
	public static boolean HASWOLF_ONE;
	public static boolean HASWOLF_TWO;
	public static boolean HASWOLF_THREE;
	public static boolean HASWOLF_FOUR;
	
	private SheepWarGameEngine engine;
	
	public StateGame(SheepWarGameEngine engine){
		this.engine = engine;
	}
	
	public CreateRole createRole;
	public Weapon weapon;
	public Role own; 
	
	/*�ؿ���Ϣ*/
	public static int[][] LEVEL_INFO = {
		
		/*0-�ؿ���1-�ùؿ������ǵ������� 2-ÿ���ǳ��ֵļ��ʱ�䣨�룩��3-*/
		{1, 32, 3},  //��һ��
		{},  //�ڶ���
		{},  //������
	};
	
	/*�����ӵ�����ı���*/
	private long startTime, endTime;
	private boolean isAttack = true;
	private int bulletInterval = 2;   //�ӵ�������
	
	private int tempx=ScrW, tempy=20, tempx2=ScrW, tempy2=30;//ScrW��Ļ��ȣ�tempx��ʼ=ScrW�������ñ��ʽtempx-=1��ʹ���ƶ�
	
	public void handleKey(KeyState keyState){
		
		if (keyState.containsMoveEventAndRemove(KeyCode.UP)) {
			moveRole(0);
			
		} else if (keyState.containsMoveEventAndRemove(KeyCode.DOWN)) {
			moveRole(1);
			
		} else if (keyState.containsAndRemove(KeyCode.OK)) {	
			if(isAttack){ //��ͨ����
				weapon.createBomb(own, 2);
				startTime = System.currentTimeMillis()/1000;
				isAttack = false;
			}
			
		}else if(keyState.containsAndRemove(KeyCode.NUM1)){    	//ʱ������
			
		}else if(keyState.containsAndRemove(KeyCode.NUM2)){ 	//������
			
		}else if(keyState.containsAndRemove(KeyCode.NUM3)){		//����
			
		}else if(keyState.containsAndRemove(KeyCode.NUM4)){		//����ǹ
			
		}else if(keyState.containsAndRemove(KeyCode.NUM5)){		//��ɢ����
			
		}else if(keyState.containsAndRemove(KeyCode.NUM6)){
			
		}else if(keyState.containsAndRemove(KeyCode.NUM7)){
			
		}else if(keyState.containsAndRemove(KeyCode.NUM8)){		//ľż->��������һ������
			
		}else if(keyState.containsAndRemove(KeyCode.NUM9)){
			
		}else if (keyState.containsAndRemove(KeyCode.NUM0)) { 
			engine.status = STATUS_MAIN_MENU;
			clear();
		}
	}
	
	public void show(SGraphics g){
		drawGamePlaying(g);
		createRole.showSheep(g,own);
		createRole.showWolf(g);
		weapon.showBomb(g);
	}
	
	public void execute(){
		
		/*�����ӵ�������*/
		endTime = System.currentTimeMillis()/1000; 
		if(endTime-startTime>=bulletInterval){
			isAttack = true;
		}
		
		/*������*/
		createNpc();
		
		/*�����ͨ�����Ƿ����Ŀ��*/
		bombAttackNpcs();
		
		/*�Ƴ���������*/
		removeDeath();
		
	}
	
	private void createNpc(){
		if(engine.timePass(2000)){
			createRole.createWolf();
		}

	}
	
	private void removeDeath(){
		for(int j=createRole.npcs.size()-1;j>=0;j--){
			Role npc = (Role) createRole.npcs.elementAt(j);
			if(npc.status == ROLE_DEATH && npc.mapy >= 446){
				createRole.npcs.removeElement(npc);
			}
		}
	}
	
	private void bombAttackNpcs(){
		for(int i=weapon.bombs.size()-1;i>=0;i--){
			Weapon bomb = (Weapon) weapon.bombs.elementAt(i);
			for(int j=createRole.npcs.size()-1;j>=0;j--){
				Role npc = (Role) createRole.npcs.elementAt(j);
				Role ballon = npc.role;
				if(ballon != null && npc.status == ROLE_ALIVE){
					if(Collision.checkCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, ballon.mapx, ballon.mapy, ballon.width, ballon.height)){
						npc.status = ROLE_DEATH;
						npc.speed += 10;
						weapon.bombs.removeElement(bomb);
					}
				}
			}
			/*�ӵ�����ʱ�Ƴ�*/
			if(bomb.mapx+bomb.width <=0){
				weapon.bombs.removeElement(bomb);
			}
		}
	}
	
	private void drawGamePlaying(SGraphics g){
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image playing_menu = Resource.loadImage(Resource.id_playing_menu);
		Image playing_cloudbig = Resource.loadImage(Resource.id_playing_cloudbig);
		Image playing_cloudsmall = Resource.loadImage(Resource.id_playing_cloudsmall);
		Image playing_lawn = Resource.loadImage(Resource.id_playing_lawn);
		Image playing_step = Resource.loadImage(Resource.id_playing_step);
		Image playing_tree = Resource.loadImage(Resource.id_playing_tree);
		Image playing_lunzi = Resource.loadImage(Resource.id_playing_lunzi);
		Image playing_shenzi = Resource.loadImage(Resource.id_playing_shenzi);
		Image playing_lift = Resource.loadImage(Resource.id_playing_lift);
		Image playing_shenzi1 = Resource.loadImage(Resource.id_playing_shenzi1);
		Image playing_prop_memu = Resource.loadImage(Resource.id_playing_prop_memu);
		Image playing_stop = Resource.loadImage(Resource.id_playing_stop);
		Image ladder = Resource.loadImage(Resource.id_ladder);
		g.drawImage(game_bg, 0, 0, TopLeft);
		
		if(tempx+playing_cloudbig.getWidth()>0){
			tempx -= 1;
		}else{
			tempy = RandomValue.getRandInt(0, 114);
			tempx = ScrW;
		}
		g.drawRegion(playing_cloudbig, 0, 0, playing_cloudbig.getWidth(), playing_cloudbig.getHeight(), 
				0, tempx, tempy, TopLeft);
		
		if(tempx2+playing_cloudsmall.getWidth()>0){
			tempx2 -= 2;
		}else{
			tempy2 = RandomValue.getRandInt(0, 114);
			tempx2 = ScrW;
		}
		g.drawRegion(playing_cloudsmall, 0, 0, playing_cloudsmall.getWidth(), playing_cloudsmall.getHeight(), 
				0, tempx2, tempy2, TopLeft);
		g.drawImage(playing_lawn, 0, 499, TopLeft);
		g.drawImage(playing_tree, 0, 72, TopLeft);
		g.drawImage(playing_shenzi1, 399, 135, TopLeft);
		for(int i=0;i<4;i++){   //����
			g.drawImage(playing_step, 377, 153+i*89, TopLeft);
			g.drawImage(ladder, 426, 183+i*89, TopLeft);
		}
		g.drawRegion(playing_shenzi, 0, 0, playing_shenzi.getWidth(), (own.mapy-154),        //�����ƶ�������
				0, 379, 154, TopLeft);                                                        //��ֱ���� �������� 154

		g.drawRegion(playing_lift, 0, 0, playing_lift.getWidth(), playing_lift.getHeight(),     //��ĵ���
				0, 342, 154+(own.mapy-154), TopLeft);
		
		g.drawImage(playing_lunzi, 374,132, TopLeft);
		g.drawImage(playing_menu, 491, 0, TopLeft);
		
		for(int i=0;i<4;i++){                                                                
			g.drawImage(playing_prop_memu, 497,185+i*68, TopLeft);
			drawProp(g, i, 497+5,185+i*(68+3));                                              
			drawNum(g, i+1, 540+7, 223-17+i*73);//��ʾ���ܰ�����1-4{540,223}
			
			g.drawImage(playing_prop_memu, 564,185+i*68, TopLeft);
			drawProp(g, i+4, 564+5,185+i*(68+2));  //�ڶ��ж�ӦԭͼƬ�еĺ��ĸ�
			drawNum(g, i+4+1, 612, 223-17+i*73);//��ʾ���ܼ�5-8{}
		}
		g.drawImage(playing_stop, 500,459, TopLeft);//��ͣ��Ϸ��ť
	}
	
	private void moveRole(int towards) {
		switch (towards) {
		case 0: // ����--����
			if(own.mapy>=164){
				own.mapy -= own.speed;
			}
			break;
		case 1: // ����--����
			own.direction = 1;
			if(own.mapy + own.height<460){
				own.mapy += own.speed;
			}
			break;
		}
	}
	
	private void drawNum(SGraphics g, int num, int x, int y) {
		Image imgNumeber = Resource.loadImage(Resource.id_shop_figure);
		String number = String.valueOf(num);
		for (byte i = 0; i < number.length(); i++) {
			g.drawRegion(imgNumeber, (number.charAt(i) - '0') * imgNumeber.getWidth()/10, 0, 
					imgNumeber.getWidth()/10, imgNumeber.getHeight(), 0, x + i * (imgNumeber.getWidth()/10 + 1), y, 0);
		}
	}
	
	private void drawProp(SGraphics g,int num,int x,int y){
		Image playing_prop=Resource.loadImage(Resource.id_playing_prop);
		String number=String.valueOf(num);
		for(byte i=0;i<number.length();i++){
			g.drawRegion(playing_prop, (number.charAt(i) - '0')* playing_prop.getWidth()/8, 0, playing_prop.getWidth()/8,
					playing_prop.getHeight(), 0, x+i * (playing_prop.getWidth()/8 + 1), y, 0);
		}
	}
	
	private void clear() {
		Resource.freeImage(Resource.id_playing_menu);
		Resource.freeImage(Resource.id_playing_cloudbig);
		Resource.freeImage(Resource.id_playing_cloudbig);
		Resource.freeImage(Resource.id_playing_cloudsmall);
		Resource.freeImage(Resource.id_playing_lawn);
		Resource.freeImage(Resource.id_playing_step);
		Resource.freeImage(Resource.id_playing_tree);
		Resource.freeImage(Resource.id_game_bg);
		Resource.freeImage(Resource.id_playing_lunzi);
		Resource.freeImage(Resource.id_playing_shenzi);
		Resource.freeImage(Resource.id_playing_lift);
		Resource.freeImage(Resource.id_playing_shenzi1);
		Resource.freeImage(Resource.id_playing_prop_memu);
		Resource.freeImage(Resource.id_playing_prop);
		Resource.freeImage(Resource.id_playing_stop);   
		Resource.freeImage(Resource.id_playing_sheep);   
		Resource.freeImage(Resource.id_sheep_eye);   
		Resource.freeImage(Resource.id_sheep_hand);   
		Resource.freeImage(Resource.id_bomb);   
		Resource.freeImage(Resource.id_wolf_down);   
		Resource.freeImage(Resource.id_wolf_run);   
		Resource.freeImage(Resource.id_balloon_blue);   
		Resource.freeImage(Resource.id_balloon_green);   
		Resource.freeImage(Resource.id_balloon_multicolour);   
		Resource.freeImage(Resource.id_balloon_yellow);   
		Resource.freeImage(Resource.id_balloon_yellowred);   
		Resource.freeImage(Resource.id_balloon_red);   
	}

}
