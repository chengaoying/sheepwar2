package sheepwar;

import javax.microedition.lcdui.Image;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.util.Collision;
import cn.ohyeah.stb.util.RandomValue;

public class StateGame implements Common{
	
	/*���������ж��ĸ��������Ƿ����ǣ����ҵ���*/
	public static boolean HASWOLF_ONE;
	public static boolean HASWOLF_TWO;
	public static boolean HASWOLF_THREE;
	public static boolean HASWOLF_FOUR;
	
	/*�ж��������Ƿ�����*/
	public static boolean IS_FOUR_WOLF;
	
	private SheepWarGameEngine engine;
	
	public StateGame(SheepWarGameEngine engine){
		this.engine = engine;
	}
	
	public CreateRole createRole;
	public Batches batches;
	public Weapon weapon;
	public static Role own; 

	/*��Ϸ�ؿ�*/
	public short level = 1; 
	public boolean isNext;
	
	/*��ǰ�ؿ��ǳ��ֵ�����*/
	public short batch;
	
	/*�ؿ���Ϣ*/
	public static int[][] LEVEL_INFO = {
		
		/*0-�ؿ���1-�ùؿ������ǵ������� 2-ÿ���ǳ��ֵļ��ʱ�䣨�룩��3-�ùؿ��ǵ�λ�ã�0-����, 1-���棩*/
		{1, 2, 3, 0},  //��һ��
		{1, 32, 3, -1},  //�ڶ���
		{},  //������
	};
	
	/*�����ӵ�����ı���*/
	private long startTime, endTime;
	private boolean isAttack = true;
	private int bulletInterval = 2;   
	
	/*ʱ������*/
	public static boolean pasueState;
	private long pasueTimeS,  pasueTimeE;
	private int pasueInterval = 10;
	
	/*����*/
	public static boolean speedFlag;
	private long addSpeedTime,addSpeedTime2;
	private int speedLiquidInterval = 30;
	
	/*�޵�*/
	private long proEndTime;
	private long proStartTime;
	private long protectInterval = 5;
	public static boolean protectState;
	
	/*��Ҹ�������*/
	public static int lifeNum;
	public static int scores;
	public static int eatNum;
	
	private int tempx=ScrW, tempy=20, tempx2=ScrW, tempy2=30, sWidth = 155, sTempy = 309;
	
	public void handleKey(KeyState keyState){
		
		if (keyState.containsMoveEventAndRemove(KeyCode.UP)) {
			moveRole(0);
			
		} else if (keyState.containsMoveEventAndRemove(KeyCode.DOWN)) {
			moveRole(1);
			
		} else if (keyState.containsAndRemove(KeyCode.OK)) {	
			if(isAttack){ //��ͨ����
				weapon.createBomb(own, Weapon.WEAPON_MOVE_LEFT);
				own.bombNum ++;
				if(own.bombNum%2==0){
					isAttack = false;
					startTime = System.currentTimeMillis()/1000;
				}
			}
			
		}else if(keyState.containsAndRemove(KeyCode.NUM1)){    	//ʱ������
			pasueState = true;
			pasueTimeS = System.currentTimeMillis()/1000;
			
		}else if(keyState.containsAndRemove(KeyCode.NUM2)){ 	//������
			weapon.createNet(own, Weapon.WEAPON_MOVE_LEFT);
			
		}else if(keyState.containsAndRemove(KeyCode.NUM3)){		//����
			protectState = true;
			if(own.status == ROLE_ALIVE){
				weapon.createProtect(own);
			}
			proStartTime = System.currentTimeMillis()/1000;
			
		}else if(keyState.containsAndRemove(KeyCode.NUM4)){		//����ǹ
			
		}else if(keyState.containsAndRemove(KeyCode.NUM5)){		//��ɢ����
			
		}else if(keyState.containsAndRemove(KeyCode.NUM6)){		//����
			if(!speedFlag){
				addSpeedTime = System.currentTimeMillis()/1000;
				own.speed = own.speed + 5;
				speedFlag = true;
			}
			
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
		batches.showWolf(g, weapon);
		weapon.showBomb(g);
		weapon.showBoom(g,own);			//��ʾ�Ƿ�����ӵ�
		weapon.showNet(g);
		weapon.showProtect(g, own);
	}
	
	public void execute(){
		
		/*�����ӵ�������*/
		endTime = System.currentTimeMillis()/1000; 
		if(isAttack==false && endTime-startTime>=bulletInterval){
			isAttack = true;
		}
		/*ʱ������Ч��ʱ��*/
		pasueTimeE = System.currentTimeMillis()/1000;
		if(pasueState && (pasueTimeE-pasueTimeS)>=pasueInterval){
			pasueState = false;
		}
		/*����Ч��ʱ��*/
		addSpeedTime2 = System.currentTimeMillis()/1000;
		if(addSpeedTime2 - addSpeedTime >= speedLiquidInterval){			
			speedFlag = false;
		}
		
		/*������װ��ʱ�����*/
		proEndTime = System.currentTimeMillis()/1000;
		if(proEndTime - proStartTime > protectInterval){
			protectState = false;
		}

		/*������*/
		createNpc();
		
		/*�����ͨ�����Ƿ����Ŀ��*/
		bombAttackNpcs();
		
		/*���ǵ���*/
		netAttackNpcs();
		
		/*�ǵ���ͨ����*/
		boomAttackPlayer();
		
		/*�Ƴ���������*/
		removeDeath();
		
		/*�����ж�*/
		nextLevel();
		
		/*ÿ����֮����ֽ����ؿ�*/
		rewardLevel();
		
		/**/
		
		/*��Ϸ�ɹ���ʧ��*/
		gameSuccessOrFail();
	}
	
	private void gameSuccessOrFail() {
		
		if(own.lifeNum<=0){		/*��Ϸʧ��*/
			System.out.println("isSuccess:"+false);
			StateGameSuccessOrFail sgs = new StateGameSuccessOrFail();
			sgs.processGameSuccessOrFail(false);
			engine.status = STATUS_MAIN_MENU;
			
		}else if(level > 15){	/*��Ϸͨ��*/
			System.out.println("isSuccess:"+true);
			StateGameSuccessOrFail sgs = new StateGameSuccessOrFail();
			sgs.processGameSuccessOrFail(true);
			engine.status = STATUS_MAIN_MENU;
		}
		
		
		
	}

	private void nextLevel(){
		if(level==1 && own.eatNum >= LEVEL_INFO[level-1][1]){
			StateNextLevel stateLevel = new StateNextLevel();
			stateLevel.processNextLevel();
			isNext = true;
			batch = 0;
			level ++;
			weapon.clearObjects();  //��ն���
			batches.clearObject();	//��ն���
		}else if(level==2 && own.eatNum >= LEVEL_INFO[level-1][1]){
			StateNextLevel stateLevel = new StateNextLevel();
			stateLevel.processNextLevel();
			isNext = true;
			batch = 0;
			level ++;
			weapon.clearObjects();  //��ն���
			batches.clearObject();	//��ն���
		}
	}
	
	private void rewardLevel() {
		if(isNext && level==2/*(level-1)%2==0*/){
			System.out.println("���뽱���ؿ�");
			StateRewardLevel sr = new StateRewardLevel();
			sr.processRewardLevel();
			isNext = false;
		}
	}

	/*�ж����Ƿ�������*/
	private void boomAttackPlayer(){
		for(int i = weapon.booms.size() - 1;i >=0;i--){
			Weapon boom = (Weapon)weapon.booms.elementAt(i);
			if(own.status == ROLE_ALIVE){
				if(Collision.checkCollision(boom.mapx, boom.mapy, boom.width, boom.height, own.mapx, own.mapy, own.width, own.height)){
					if(!protectState){			//����Ƿ��з�����װ
						own.status = ROLE_DEATH;
						own.lifeNum --;
						lifeNum = own.lifeNum;
					}
					weapon.booms.removeElement(boom);
				}
			}
			/*�ӵ�����ʱ�Ƴ�*/
			if(boom.mapx >= gameW){
				weapon.booms.removeElement(boom);
			}
		}
	}

	/*��Ĳ��������ܹ���*/
	private void netAttackNpcs() {
		for(int i=weapon.nets.size()-1;i>=0;i--){
			Weapon net = (Weapon) weapon.nets.elementAt(i);
			if(net.isUse){
				for(int j=batches.npcs.size()-1;j>=0;j--){
					Role npc = (Role) batches.npcs.elementAt(j);
					if(npc.status == ROLE_ALIVE){
						if(Collision.checkCollision(npc.mapx, npc.mapy, npc.width, npc.height, net.mapx, net.mapy, net.width, net.height)){
							npc.status = ROLE_DEATH;
							npc.speed += 10;
						}
					}
				}
			}
			/*������ʱ�Ƴ�*/
			if(net.mapy >= ScrH){
				net.isUse = false;
				weapon.nets.removeElement(net);
			}
		}
	
	}

	private void createNpc(){
		if(isAllDown()){
			if(engine.timePass(LEVEL_INFO[level-1][2]*1000)){
				batches.createBatches(level, batch, LEVEL_INFO[level-1][3]);
				batch = (short) ((batch+1) % BatchesInfo[level-1].length);
			}
		}
	}
	
	/*�ж����Ƿ��Ѿ��½���������*/
	private boolean isAllDown(){
		int len = batches.npcs.size();
		for(int i=len-1;i>=0;i--){
			Role role = (Role) batches.npcs.elementAt(i);
			if(role.status2 == ROLE_ON_GROUND){
				return false;
			}
		}
		return true;
	}
	
	private void removeDeath(){
		for(int j=batches.npcs.size()-1;j>=0;j--){
			Role npc = (Role) batches.npcs.elementAt(j);
			if(npc.status == ROLE_DEATH && npc.mapy >= 446){
				batches.npcs.removeElement(npc);
			}
		}
	}
	
	private void bombAttackNpcs(){
		for(int i=weapon.bombs.size()-1;i>=0;i--){
			Weapon bomb = (Weapon) weapon.bombs.elementAt(i);
			for(int j=batches.npcs.size()-1;j>=0;j--){
				Role npc = (Role) batches.npcs.elementAt(j);
				Role ballon = npc.role;
				if(ballon != null && npc.status == ROLE_ALIVE){
					if(Collision.checkCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, ballon.mapx, ballon.mapy, ballon.width, 30/*ballon.height*/)){
						npc.status = ROLE_DEATH;
						npc.speed += 10;
						weapon.bombs.removeElement(bomb);
						
						own.eatNum ++;
						own.scores += ballon.scores;
						scores = own.scores;
						eatNum = own.eatNum;
					}
					else if(Collision.checkCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, npc.mapx, npc.mapy, npc.width, npc.height)){
						bomb.direction = Weapon.WEAPON_MOVE_DOWN;
						bomb.speedY = bomb.speedX + 10;
					}
				}
			}
			/*�ӵ�����ʱ�Ƴ�*/
			if((bomb.mapx+bomb.width <=0) || bomb.mapy >= 466){
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
		Image playing_level = Resource.loadImage(Resource.id_playing_level);
		Image playing_point = Resource.loadImage(Resource.id_playing_point);
		Image sheep_head = Resource.loadImage(Resource.id_sheep_head);
		Image wolf_head = Resource.loadImage(Resource.id_wolf_head);
		Image multiply = Resource.loadImage(Resource.id_multiply);
		g.drawImage(game_bg, 0, 0, 20);
		
		if(tempx+playing_cloudbig.getWidth()>0){
			tempx -= 1;
		}else{
			tempy = RandomValue.getRandInt(0, 114);
			tempx = ScrW;
		}
		g.drawRegion(playing_cloudbig, 0, 0, playing_cloudbig.getWidth(), playing_cloudbig.getHeight(), 
				0, tempx, tempy, 20);
		
		if(tempx2+playing_cloudsmall.getWidth()>0){
			tempx2 -= 2;
		}else{
			tempy2 = RandomValue.getRandInt(0, 114);
			tempx2 = ScrW;
		}
		g.drawRegion(playing_cloudsmall, 0, 0, playing_cloudsmall.getWidth(), playing_cloudsmall.getHeight(), 
				0, tempx2, tempy2, 20);
		g.drawImage(playing_lawn, 0, 499, 20);
		g.drawImage(playing_tree, 0, 72, 20);
		g.drawImage(playing_shenzi1, 399, 135, 20);
		for(int i=0;i<4;i++){   //����
			g.drawImage(playing_step, 377, 153+i*89, 20);
			g.drawImage(ladder, 426, 183+i*89, 20);
		}
		
		if(own.status == ROLE_ALIVE){
			sWidth = own.mapy - 154;
			sTempy = own.mapy;
		}
		g.drawRegion(playing_shenzi, 0, 0, playing_shenzi.getWidth(), sWidth,        //�����ƶ�������
				0, 379, 154, 20);                                                        	//��ֱ���� �������� 154
		g.drawRegion(playing_lift, 0, 0, playing_lift.getWidth(), playing_lift.getHeight(),     //��ĵ���
				0, 342, sTempy, 20);
		
		g.drawImage(playing_lunzi, 374,132, 20);
		g.drawImage(playing_menu, 491, 0, 20);
		
		g.drawImage(playing_level, 491+32, 25, 20);				//��Ϸ�� ���Ĺؿ�ͼƬ		
		g.drawImage(playing_point, 491+11, 66, 20);				//��Ϸ�� ��� �ĵ÷�ͼƬ	
		g.drawImage(sheep_head, 491+26, 142, 20);				//��Ϸ�� �Ҳ� �����ͷ��		
		g.drawImage(wolf_head, 12, 10, 20);						//��Ϸ�� ��� ���ǵ�ͷ��		
		g.drawImage(multiply, 491+66, 147, 20);						
		g.drawImage(multiply, 45, 12, 20);	
		int propLeftMenuX = 497+1,propRightMenuX= 564+1,propMenuY = 185-7,distanceMenuY = 4;
		int numLeftX = 547,numRight = 612;
		for(int i=0;i<4;i++){                                                                
			g.drawImage(playing_prop_memu, propLeftMenuX,propMenuY+i*(distanceMenuY+playing_prop_memu.getHeight()), 20);
			drawProp(g, i, propLeftMenuX+5,propMenuY+4+i*(distanceMenuY+playing_prop_memu.getHeight()));                                              
			drawNum(g, i+1, numLeftX, propMenuY+playing_prop_memu.getHeight()-29			//29����ʾ
					+i*(distanceMenuY+playing_prop_memu.getHeight()));//��ʾ���ܰ�����1-4{540,223}
			
			g.drawImage(playing_prop_memu, propRightMenuX,propMenuY+i*(distanceMenuY+playing_prop_memu.getHeight()), 20);
			drawProp(g, i+4, propRightMenuX+5,propMenuY+4+i*(distanceMenuY+playing_prop_memu.getHeight()));  //�ڶ��ж�ӦԭͼƬ�еĺ��ĸ�
			drawNum(g, i+4+1, numRight, propMenuY+playing_prop_memu.getHeight()-29
					+i*(distanceMenuY+playing_prop_memu.getHeight()));//��ʾ���ܼ�5-8{}
		}
		g.drawImage(playing_stop, 500,459, 20);//��ͣ��Ϸ��ť
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
		Resource.freeImage(Resource.id_ladder);   
		Resource.freeImage(Resource.id_playing_level);   
		Resource.freeImage(Resource.id_playing_point);   
		Resource.freeImage(Resource.id_sheep_head);   
		Resource.freeImage(Resource.id_wolf_head);   
		Resource.freeImage(Resource.id_multiply);   
	}

}
