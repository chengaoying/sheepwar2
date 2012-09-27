package sheepwar;

import javax.microedition.lcdui.Image;

import cn.ohyeah.itvgame.model.GameAttainment;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.game.ServiceWrapper;
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
	public static boolean isGameOver;
	public static boolean isSuccess;
	
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
	/*�����ؿ�*/
	public short rewardLevel = 1;
	
	public boolean isRewardLevel, isReward;
	
	/*��ǰ�ؿ��ǳ��ֵ�����*/
	public short batch;
	
	/*�ؿ���Ϣ*/
	public static int[][] LEVEL_INFO = {
		/*0-�ؿ���1-�ùؿ������ǵ������� 2-ÿ���ǳ��ֵļ��ʱ�䣨�룩��3-�ùؿ��ǵ�λ�ã�0-����, -1-���棩*/
		{1, 32, 3, 0},  	//��һ��
		{2, 36, 3, -1},  	//�ڶ���
		{3, 40, 3, 0},  	//������
		{4, 44, 3, -1},  	//���Ĺ�
		{5, 48, 3, 0},  	//�����
		{6, 52, 3, -1},  
		{7, 56, 2, 0},  
		{8, 60, 2, -1},  
		{9, 64, 1, 0},  
		{10, 68, 1, -1},  
		{11, 72, 3,0},  
		{12, 76, 3, -1},  
		{13, 80, 2, 0},  
		{14, 84, 2, -1},  
		{15, 88, 2, 0},  
	};
	
	/*�����ؿ���Ϣ*/
	public static int [][] REWARD_LEVEL_INFO = {
		/*0-�ؿ���1-�ùؿ������ǵ������� 2-ÿ���ǳ��ֵļ��ʱ�䣨�룩��3-�ùؿ��ǵ�λ�ã�0-����, -1-���棩*/
		{1,16,3,-1},			//�����ؿ�һ
		{2,16,3,-1},			//�����ؿ���
		{3,16,3,-1},			//�����ؿ���
		{4,16,3,-1},			
		{5,16,3,-1},			
		{6,16,2,-1},			
		{7,16,2,-1},			
	};
	
	/*��Ϸ����ʱ��*/
	private long gameBufferTimeS, gameBufferTimeE;
	private boolean isNextLevel;
	
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
	
	/*ǿ����ʯ*/
	private long magnetStartTime,magnetEndTime;
	private long magnetInterval = 3;
	public static boolean magnetState;
	
	/*�޵�ȭ��*/
	private boolean isUseGlove, isShowGlove, golveFlag=true;
	private long gloveEndTime, gloveStartTime;
	private int gloveInterval = 5;
	
	/*��Ҵ浵����*/
	public static int lifeNum;		//������
	public static int scores;		//����
	public static int scores2;		//���ػ���
	public static int hitNum;		//���ػ����ǵ���
	public static int hitTotalNum;	//�����ǵ�����
	public static int hitBuble;		//���е�������
	public static int useProps;		//ʹ�õĵ�����
	public static int hitFruits;	//���е�ˮ����
	public static int hitRatio;		//����Ŀ����
	public static int attainment;	//�ɾ���
	
	private int tempx=ScrW, tempy=20, tempx2=ScrW, tempy2=30, sWidth, sTempy;
	
	public void handleKey(KeyState keyState){
		if (keyState.containsMoveEventAndRemove(KeyCode.UP)) {
			moveRole(0);
			
		} else if (keyState.containsMoveEventAndRemove(KeyCode.DOWN)) {
			moveRole(1);
			
		} else if (keyState.containsAndRemove(KeyCode.OK)&& own.status ==ROLE_ALIVE) {	
			if(isUseGlove){
				weapon.createGloves(own, Weapon.WEAPON_MOVE_LEFT);
				isUseGlove = false;
				golveFlag = true;
				gloveStartTime = System.currentTimeMillis() / 1000;
			}else if(isAttack){ //��ͨ����
				weapon.createBomb(own, Weapon.WEAPON_MOVE_LEFT);
				own.bombNum ++;
				if(own.bombNum%2==0){
					isAttack = false;
					startTime = System.currentTimeMillis()/1000;
				}
			}
			
		}else if(keyState.containsAndRemove(KeyCode.NUM1)&& own.status ==ROLE_ALIVE){    	//ʱ������
			if(engine.pm.getPropNumsById(engine.pm.propIds[0])>0 || engine.isDebugMode()){
				pasueState = true;
				pasueTimeS = System.currentTimeMillis()/1000;
				int propId = engine.pm.propIds[0]-53;
				updateProp(propId);
			}
			
		}else if(keyState.containsAndRemove(KeyCode.NUM2)&& own.status ==ROLE_ALIVE){ 		//������
			if(engine.pm.getPropNumsById(engine.pm.propIds[1])>0 || engine.isDebugMode()){
				weapon.createNet(own, Weapon.WEAPON_MOVE_LEFT);
				int propId = engine.pm.propIds[1]-53;
				updateProp(propId);
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM3)&& own.status ==ROLE_ALIVE){		//����
			if(engine.pm.getPropNumsById(engine.pm.propIds[2])>0 || engine.isDebugMode()){
				protectState = true;
				weapon.createProtect(own);
				proStartTime = System.currentTimeMillis()/1000;
				int propId = engine.pm.propIds[2]-53;
				updateProp(propId);
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM4)&& own.status ==ROLE_ALIVE){		//����ǹ
			if(engine.pm.getPropNumsById(engine.pm.propIds[3])>0 || engine.isDebugMode()){
				weapon.createGlare(own,Weapon.WEAPON_MOVE_LEFT);
				int propId = engine.pm.propIds[3]-53;
				updateProp(propId);
			}

		}else if(keyState.containsAndRemove(KeyCode.NUM5)&& own.status ==ROLE_ALIVE){		//��ɢ����
			if(engine.pm.getPropNumsById(engine.pm.propIds[4])>0 || engine.isDebugMode()){
				weapon.createHarp(own);
				int propId = engine.pm.propIds[4]-53;
				updateProp(propId);
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM6)&& own.status ==ROLE_ALIVE){		//�ٶ�����Һ
			if(engine.pm.getPropNumsById(engine.pm.propIds[5])>0 || engine.isDebugMode()){
				if(!speedFlag){
					own.speed = own.speed + CreateRole.para[4];
					speedFlag = true;
					addSpeedTime = System.currentTimeMillis()/1000;
					int propId = engine.pm.propIds[5]-53;
					updateProp(propId);
				}
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM7)&& own.status ==ROLE_ALIVE){		//ǿ����ʯ
			if(engine.pm.getPropNumsById(engine.pm.propIds[6])>0 || engine.isDebugMode()){
				magnetStartTime = System.currentTimeMillis()/1000;
				magnetState = true;
				int propId = engine.pm.propIds[6]-53;
				updateProp(propId);
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM8) && own.status ==ROLE_ALIVE){		//ľż->��������һ������
			if(engine.pm.getPropNumsById(engine.pm.propIds[7])>0 || engine.isDebugMode()){
				own.lifeNum ++;
				lifeNum = own.lifeNum;
				int propId = engine.pm.propIds[7]-53;
				updateProp(propId);
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM9)){		//��ͣ							
			
		}else if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)){ 	//����
			StateSubMenu sm = new StateSubMenu();
			int index = sm.processSubMenu();
			if(index == 0){		//������Ϸ
				
			}else if(index == 1){
				StateShop ss =  new StateShop();
				ss.processShop();
			}else if(index == 2){
				StateHelp sh = new StateHelp();
				sh.processHelp();
			}else if(index == 3){
				engine.state = STATUS_MAIN_MENU;
				clear();
				
				//ͬ������
				engine.pm.sysProps();
				
				//����ɾ�
				ServiceWrapper sw = engine.getServiceWrapper();
				GameAttainment ga = sw.readAttainment(engine.attainmentId);
				if(((ga==null && own.scores>0) || (ga.getScores()<=own.scores) && own.scores>0)){
					engine.saveAttainment(own);
				}
			}
		}
	}
	
	private void updateProp(int propId){
		if(!engine.isDebugMode()){
			engine.props[propId].setNums(engine.props[propId].getNums()-1);
			own.useProps++;
			useProps = own.useProps;
		}
	}
	
	public void show(SGraphics g){
		drawGamePlaying(g);
		createRole.showSheep(g,own);
		batches.showWolf(g, weapon);
		weapon.showFruit(g);
		weapon.showBomb(g);
		weapon.showBoom(g,own);			
		weapon.showNet(g);
		weapon.showProtect(g, own);
		weapon.showGlare(g, own);
		weapon.showHarp(g, batches);
		weapon.showMagnetEffect(g, batches);
		if(batches.redWolf!=null){
			batches.showRedWolf(g,weapon);				
		}
		if(isShowGlove){
			weapon.showGloveCreate(g);
		}
		weapon.showGloves(g, own);
	}
	
	public void execute(){
		/*�����ӵ�������*/
		endTime = System.currentTimeMillis()/1000; 
		if(isAttack==false && endTime-startTime>=bulletInterval){
			isAttack = true;
		}
		/*����ȭ��ʱ����*/
		gloveEndTime = System.currentTimeMillis()/1000;
		if(golveFlag && (gloveEndTime  - gloveStartTime >= gloveInterval)){
			isShowGlove = true;
			golveFlag = false;
		}
		if(isShowGlove && own.mapy<=190){
			isUseGlove = true;
			isShowGlove = false;
		}
		
		/*ʱ������Ч��ʱ��*/
		pasueTimeE = System.currentTimeMillis()/1000;
		if(pasueState && (pasueTimeE-pasueTimeS)>=pasueInterval){
			pasueState = false;
		}
		/*����Ч��ʱ��*/
		addSpeedTime2 = System.currentTimeMillis()/1000;
		if(addSpeedTime2 - addSpeedTime >speedLiquidInterval){			
			speedFlag = false;
			own.speed = CreateRole.para[4];
		}
		/*������װ��ʱ�����*/
		proEndTime = System.currentTimeMillis()/1000;
		if(proEndTime - proStartTime > protectInterval){
			protectState = false;
		}
		
		/*ǿ����ʯ����ʱ��*/
		magnetEndTime = System.currentTimeMillis()/1000;
		if(magnetEndTime - magnetStartTime > magnetInterval){
			magnetState = false;
		}
		
		/*��Ϸ����ʱ��*/
		gameBufferTimeE = System.currentTimeMillis()/1000;
		
		/*����һ����*/
		createNpc();
		
		/*������*/
		createRedNpc();
		
		/*�ؿ������ж�*/
		isNextLevel();  			
		
		/*��ͨ������ײ���*/
		bombAttackNpcs();
		
		/*�޵�ȭ����ײ���*/
		gloveAttackNpcs();
		
		/*���ǵ�����ײ���*/
		netAttackNpcs();
		
		/*�ǵ���ͨ������ײ���*/
		boomAttackPlayer();
		
		/*����ǹ��ײ���*/
		glareAttackNpcs();
		
		/*�Ƴ���������*/
		removeDeath();

		/*�ж�4��λ�����Ƿ�����*/
		checkFourPosition();
		
		/*�ж���Ϸ�ɹ���ʧ��*/
		gameSuccessOrFail();
		gameOver();
	}

	private void checkFourPosition() {
		if(IS_FOUR_WOLF){
			IS_FOUR_WOLF = false;
			own.status = ROLE_DEATH;
			own.lifeNum --;
			lifeNum = own.lifeNum;
			System.out.println("��������һ");
			for(int j = batches.npcs.size() - 1;j>=0;j--){
				Role npc = (Role)batches.npcs.elementAt(j);
				if(npc.position == ON_ONE_LADDER || npc.position ==ON_TWO_LADDER 
						|| npc.position == ON_THREE_LADDER || npc.position == ON_FOUR_LADDER){
					npc.status = ROLE_DEATH;
					StateGame.HASWOLF_ONE = false;
					StateGame.HASWOLF_TWO = false;
					StateGame.HASWOLF_THREE = false;
					StateGame.HASWOLF_FOUR = false;
					batches.npcs.removeElement(npc);
				}
			}
		}
	}

	private void isNextLevel(){
		if(!isNextLevel){
			if(!isRewardLevel){  //��ͨ�ؿ������ж�
				for (int i = 1; i < 16; i++) {
					if ((level == i && own.hitNum >= LEVEL_INFO[level - 1][1]) 
							|| (engine.isDebugMode() && own.hitNum>=3)) {
						System.out.println("����");
						gameBufferTimeS = System.currentTimeMillis()/1000;
						hitNum = own.hitNum = 0;
						isNextLevel = true;
						if(level%2==0){
							isRewardLevel = true;
							System.out.println("��һ��Ϊ�����ؿ�");
						}
						level++;
					}
				}
			}else{	//�����ؿ������ж�
				if((rewardLevel%2==1 && batch >= (RewardLevelBatchesInfo[rewardLevel-1].length - 1))
						||(rewardLevel%2==0 && batches.redWolf.bombNum>=16) 
						|| (engine.isDebugMode() && (batch>1 || (rewardLevel%2==0 && batches.redWolf.bombNum>1)))){
					System.out.println("�����ؿ�����");
					gameBufferTimeS = System.currentTimeMillis()/1000;
					isNextLevel = true;
					batch = 0;
					hitNum = own.hitNum = 0;
					isRewardLevel = false;
					isReward = true;
					rewardLevel++;
					if(batches.redWolf!=null){
						batches.redWolf.bombNum = 0;
					}
				}
			}
		}

		/*������ؽ���*/
		if(isNextLevel==true && gameBufferTimeE-gameBufferTimeS>1){
			isNextLevel = false;
			initDataNextLevel();	//�������
			StateNextLevel stateLevel = new StateNextLevel();
			stateLevel.processNextLevel(own);
			isReward = false;
		}
	}
	
	private void gameSuccessOrFail() {
		if(own.lifeNum<=0 && !isGameOver){		/*��Ϸʧ��*/
			System.out.println("��Ϸʧ��:");
			isGameOver = true;
			isSuccess = false;
			gameBufferTimeS = System.currentTimeMillis()/1000;
			
		}else if(level > 15 && !isGameOver){	/*��Ϸͨ��*/
			System.out.println("��Ϸ�ɹ���");
			isGameOver = true;
			isSuccess = true;
			gameBufferTimeS = System.currentTimeMillis()/1000;
			//ͬ������
			engine.pm.sysProps();
			
			//����ɾ�
			ServiceWrapper sw = engine.getServiceWrapper();
			GameAttainment ga = sw.readAttainment(engine.attainmentId);
			if(((ga==null && own.scores>0) || (ga.getScores()<=own.scores) && own.scores>0)){
				engine.saveAttainment(own);
			}
		}
	}
	
	private void gameOver(){
		//System.out.println("gameBufferTimeE-gameBufferTimeS="+(gameBufferTimeE-gameBufferTimeS));
		if(isGameOver && gameBufferTimeE-gameBufferTimeS>1){
			isGameOver=false;
			initDataGameOver();  //�������
			StateGameSuccessOrFail sgs = new StateGameSuccessOrFail();
			sgs.processGameSuccessOrFail(isSuccess, own);
			engine.state = STATUS_MAIN_MENU;
			clear();
		}
	}
	
	/*�޵�ȭ�׻���npc�ж�*/
	private void gloveAttackNpcs() {
		for(int i=weapon.gloves.size()-1;i>=0;i--){
			Weapon boxing = (Weapon) weapon.gloves.elementAt(i);
			for(int j=batches.npcs.size()-1;j>=0;j--){
				Role npc = (Role) batches.npcs.elementAt(j);
				Role ballon = npc.role;
				if(ballon != null && npc.status == ROLE_ALIVE){
					if(Collision.checkCollision(boxing.mapx, boxing.mapy, boxing.width, boxing.height, ballon.mapx, ballon.mapy, ballon.width, npc.height+30/*ballon.height*/)){
						hitWolf(npc);
						print();
					}
				}
			}
			
			/*ȭ�׳���ʱ�Ƴ�*/
			if((boxing.mapx+boxing.width <=0) || boxing.mapy >= 466){
				weapon.gloves.removeElement(boxing);
			}
		}
	}

	/*�жϼ���ǹ�Ƿ������*/
 	private void glareAttackNpcs() {
		for(int i=weapon.glares.size()-1;i>=0;i--){
			Weapon glare = (Weapon) weapon.glares.elementAt(i);
			if(!glare.isUse){
				for(int j=batches.npcs.size()-1;j>=0;j--){
					Role npc = (Role) batches.npcs.elementAt(j);
					if(npc.status == ROLE_ALIVE){
						if(Collision.checkCollision(npc.mapx, npc.mapy, npc.width, npc.height, glare.mapx, glare.mapy, glare.width, glare.height)){
							hitWolf(npc);
							print();
						}
					}
				}
				
				/*��ˮ��*/
				for(int k=weapon.fruits.size()-1;k>=0;k--){
					Weapon fruit = (Weapon) weapon.fruits.elementAt(k);
					if(Collision.checkCollision(fruit.mapx, fruit.mapy, fruit.width, fruit.height,glare.mapx, glare.mapy, glare.width, glare.height)){
						hitFruit(fruit);
						print();
					}
				}
			}
			/*�������ʱ�Ƴ�*/
			if(glare.mapx+glare.width <= 0){
				weapon.glares.removeElement(glare);
			}
		}
	}
 	
 	private void print(){
 		System.out.println("hitBuble:"+own.hitBuble);
 		System.out.println("hitFruits:"+own.hitFruits);
 		System.out.println("hitNum:"+own.hitNum);
 		System.out.println("hitTotalNum:"+own.hitTotalNum);
 		System.out.println("scores:"+own.scores);
 		System.out.println("scores2:"+own.scores2);
 		System.out.println("hitRatio:"+own.hitRatio);
 		System.out.println("lifeNum:"+own.lifeNum);
 		System.out.println("useProps:"+own.useProps);
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
						System.out.println("��������һ");
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
							hitWolf(npc);
							print();
						}
					}
				}
				
				/*��ˮ��*/
				for(int k=weapon.fruits.size()-1;k>=0;k--){
					Weapon fruit = (Weapon) weapon.fruits.elementAt(k);
					if(Collision.checkCollision(fruit.mapx, fruit.mapy, fruit.width, fruit.height,net.mapx, net.mapy, net.width, net.height)){
						hitFruit(fruit);
						print();
					}
				}
				
				Weapon.netTimeE = System.currentTimeMillis()/1000;
				if(Weapon.netTimeE-Weapon.netTimeS>=Weapon.netInterval){
					net.isUse = false;
					weapon.nets.removeElement(net);
				}
			}
		}
	}

	/*������̫��npc*/
	private void createRedNpc(){
		if(!isNextLevel){
			if(isRewardLevel){
				//System.out.println("��ǰ�����ؿ�:"+rewardLevel);
				if(rewardLevel % 2 ==0 && batches.redWolf==null){					//ż�������ؿ����ֺ�̫��
					System.out.println("�����ؿ�������̫��");
					batches.redWolf = batches.createRedWolf();
				}
			}else{
				//System.out.println("��ǰ�ؿ���"+level);
				if(level % 2!=0 && level != 1 && batches.redWolf==null){			//�����ؿ����к�̫�ǵĳ���
					System.out.println("��ͨ�ؿ�������̫��");
					batches.redWolf = batches.createRedWolf();
				}
			}
		}
	}
	
	private void createNpc(){
		if(isAllDown() && !isNextLevel){
			if(!isRewardLevel){
				if(engine.timePass(LEVEL_INFO[level-1][2]*1000)){
					System.out.println("��ͨ�ؿ�����һ����");
					batches.createBatches(level, batch, LEVEL_INFO[level-1][3]);
					batch = (short) ((batch+1) % BatchesInfo[level-1].length);
				}
			}else{
				if(engine.timePass(REWARD_LEVEL_INFO[rewardLevel -1][2]*1000)&&rewardLevel%2!=0){	
					System.out.println("�����ؿ�����һ����");
					batches.createBatchesReward(rewardLevel, batch, REWARD_LEVEL_INFO[rewardLevel-1][3]);
					batch = (short)((batch+1) % RewardLevelBatchesInfo[rewardLevel-1].length);
					
				}
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
		/*ˮ�������Ƴ�*/
		for(int k=weapon.fruits.size()-1;k>=0;k--){
			Weapon fruit = (Weapon) weapon.fruits.elementAt(k);
			if(fruit.mapx>=gameW || fruit.mapx+fruit.width <=0 || fruit.mapy>=ScrH){
				weapon.fruits.removeElement(fruit);
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
						hitWolf(npc);
						print();
						weapon.bombs.removeElement(bomb);
					}
					else if(Collision.checkCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, npc.mapx, npc.mapy, npc.width, npc.height)){
						bomb.direction = Weapon.WEAPON_MOVE_DOWN;
						bomb.speedY = bomb.speedX + 10;
					}
				}
			}
			
			/*��ˮ��*/
			for(int k=weapon.fruits.size()-1;k>=0;k--){
				Weapon fruit = (Weapon) weapon.fruits.elementAt(k);
				if(Collision.checkCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, fruit.mapx, fruit.mapy, fruit.width, fruit.height)){
					hitFruit(fruit);
					print();
					weapon.bombs.removeElement(bomb);
				}
			}
			
			/*�ӵ�����ʱ�Ƴ�*/
			if((bomb.mapx+bomb.width <=0) || bomb.mapy >= 466){
				weapon.bombs.removeElement(bomb);
			}
		}
	}
	
	private int cloudIndex, cloud2Index;
	private int down_cloudIndex, down_cloud2Index;
	int x1 = 20, x2 = 550, x3 = 424;
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
		/*�����ؿ�ͼƬ��Դ*/
		Image pass_cloud2 = Resource.loadImage(Resource.id_pass_cloud2);
		Image pass_cloud = Resource.loadImage(Resource.id_pass_cloud);
		Image passShadowCloud = Resource.loadImage(Resource.id_cloud1);
		Image pass_cloud1 = Resource.loadImage(Resource.id_pass_cloud1);
		
		g.drawImage(game_bg, 0, 0, 20);
		if((isRewardLevel && !isNextLevel) || isReward){		//���������ؿ�����
			g.drawImage(pass_cloud, 50, 80, 20);
			g.drawImage(pass_cloud, 216, 80, 20);
			g.drawImage(pass_cloud, 404, 140, 20);		//����������ƶ�
			for(int i=0;i<4;i++){			//�̶����Ʋ� �Ϲ�
				g.drawImage(passShadowCloud, 0+i*60, 80+10, 20);
				g.drawImage(pass_cloud, 0+i*60, 80, 20);
			}
			/*����ڶ�����*/
			int cloud2W = pass_cloud2.getWidth(),cloud2H = pass_cloud2.getHeight();
			int len = cloud2W-ScrW;
			int cloud2Y = -6;
			cloud2Index=(cloud2Index+1)%cloud2W;
			if(cloud2Index<=len){
				g.drawRegion(pass_cloud2, len-cloud2Index, 0, ScrW, cloud2H, 0, 0, cloud2Y, 20);
			}else{
				g.drawRegion(pass_cloud2, (cloud2W-cloud2Index), 0, ScrW-(cloud2W-cloud2Index), cloud2H, 0, 0, cloud2Y, 20);
				g.drawRegion(pass_cloud2, 0, 0, (cloud2W-cloud2Index), cloud2H, 0, ScrW-(cloud2W-cloud2Index), cloud2Y, 20);
			}
			
			/*����ڶ�����*/
			int down_cloud2Y = 484;
			down_cloud2Index=(down_cloud2Index+1)%cloud2W;
			if(down_cloud2Index<=len){
				g.drawRegion(pass_cloud2, len-down_cloud2Index, 0, ScrW, cloud2H, 0, 0, down_cloud2Y, 20);
			}else{
				g.drawRegion(pass_cloud2, (cloud2W-down_cloud2Index), 0, ScrW-(cloud2W-down_cloud2Index), cloud2H, 0, 0, down_cloud2Y, 20);
				g.drawRegion(pass_cloud2, 0, 0, (cloud2W-down_cloud2Index), cloud2H, 0, ScrW-(cloud2W-down_cloud2Index), down_cloud2Y, 20);
			}

			/*�м����*/
			int cloudW = pass_cloud.getWidth();
			if(x1+cloudW<=0){
				x1 = ScrW;
			}else{
				x1 -= 1;
			}
			if(x2+cloudW<=0){
				x2 = ScrW;
			}else{
				x2 -= 1;
			}
			if(x3+cloudW<=0){
				x3 = ScrW;
			}else{
				x3 -= 1;
			}
			g.drawImage(pass_cloud, x1, 152, 20);
			g.drawImage(pass_cloud, x2, 180, 20);
			g.drawImage(pass_cloud, x3, 265, 20);
			
			/*�����һ����*/
			int cloud1W = pass_cloud1.getWidth(),cloud1H = pass_cloud1.getHeight();
			int cloud1Y = -23;
			cloudIndex=(cloudIndex+1)%cloud1W;
			if(cloudIndex<=cloud1W-ScrW){
				g.drawRegion(pass_cloud1, cloudIndex, 0, ScrW, cloud1H, 0, 0, cloud1Y, 20);
			}else{
				g.drawRegion(pass_cloud1, cloudIndex, 0, cloud1W-cloudIndex, cloud1H, 0, 0, cloud1Y, 20);
				g.drawRegion(pass_cloud1, 0, 0, cloudIndex, cloud1H, 0, cloud1W-cloudIndex, cloud1Y, 20);
			}
			
			/*�����һ����*/
			int down_cloud1Y = 496;
			down_cloudIndex=(down_cloudIndex+1)%cloud1W;
			if(down_cloudIndex<=cloud1W-ScrW){
				g.drawRegion(pass_cloud1, down_cloudIndex, 0, ScrW, cloud1H, 0, 0, down_cloud1Y, 20);
			}else{
				g.drawRegion(pass_cloud1, down_cloudIndex, 0, cloud1W-down_cloudIndex, cloud1H, 0, 0, down_cloud1Y, 20);
				g.drawRegion(pass_cloud1, 0, 0, down_cloudIndex, cloud1H, 0, cloud1W-down_cloudIndex, down_cloud1Y, 20);
			}
			g.drawImage(playing_menu, 491, 0, 20);
			g.drawImage(playing_level, 491+32, 25, 20);								//��Ϸ�� ���Ĺؿ�ͼƬ		
			drawNum(g, rewardLevel, 491+32+playing_level.getWidth()+10, 25);
			drawNum(g, own.lifeNum, 491+66+multiply.getWidth()+10, 147);			//�����ؿ����������
			
		}else {
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
			for(int i=0;i<4;i++){   //����
				g.drawImage(playing_step, 377, 153+i*89, 20);
				g.drawImage(ladder, 426, 183+i*89, 20);
			}
			
			g.drawImage(playing_menu, 491, 0, 20);
			g.drawImage(playing_level, 491+32, 25, 20);						//��Ϸ�� ���Ĺؿ�ͼƬ		
			drawNum(g, level, 491+32+playing_level.getWidth()+10, 25);
			drawNum(g, own.lifeNum, 491+66+multiply.getWidth()+10, 147);			//���������
		}
		
		if(own.status == ROLE_ALIVE){
			sWidth = own.mapy - 160;
			sTempy = own.mapy-10;
		}
		g.drawImage(playing_shenzi1, 399, 135, 20);												
		g.drawRegion(playing_shenzi, 0, 0, playing_shenzi.getWidth(), sWidth, 0, 379, 154, 20);                                                        		
		g.drawRegion(playing_lift, 0, 0, playing_lift.getWidth(), playing_lift.getHeight(), 0, 342, sTempy, 20);
		
		g.drawImage(playing_lunzi, 374,132, 20);
		g.drawRegion(playing_point, 0, 0, 46, playing_point.getHeight()/2, 0, 491+35, 66, 20);
		if(own.scores>100000){
			drawNum(g, own.scores, 491+15, 98);
		}else{
			drawNum(g, own.scores, 491+35, 98);
		}
		g.drawImage(sheep_head, 491+26, 142, 20);						//��Ϸ�� �Ҳ� �����ͷ��		
		g.drawImage(wolf_head, 12, 10, 20);								//��Ϸ�� ��� ���ǵ�ͷ��		
		g.drawImage(multiply, 491+66, 147, 20);	
		g.drawImage(multiply, 45, 12, 20);	
		drawNum(g, own.hitNum, 45+multiply.getWidth()+10, 12);

		int propLeftMenuX = 497+1,propRightMenuX= 564+1,propMenuY = 185-7,distanceMenuY = 4;
		int numLeftX = 547,numRight = 612;
		int menuH = playing_prop_memu.getHeight();
		for(int i=0;i<4;i++){                                                                
			g.drawImage(playing_prop_memu, propLeftMenuX,propMenuY+i*(distanceMenuY+menuH), 20);
			drawProp(g, i, propLeftMenuX+5,propMenuY+4+i*(distanceMenuY+menuH));                                              
			drawNum(g, i+1, numLeftX, propMenuY+menuH-29+i*(distanceMenuY+menuH));//��ʾ���ܰ�����1-4{540,223}
			
			g.drawImage(playing_prop_memu, propRightMenuX,propMenuY+i*(distanceMenuY+menuH), 20);
			drawProp(g, i+4, propRightMenuX+5,propMenuY+4+i*(distanceMenuY+menuH));  //�ڶ��ж�ӦԭͼƬ�еĺ��ĸ�
			drawNum(g, i+4+1, numRight, propMenuY+menuH-29+i*(distanceMenuY+menuH));//��ʾ���ܼ�5-8{}
		}
		
		/*��������*/
		int propX=503, propY=220, spaceY=71, spaceX=67;
		for(int j=0;j<4;j++){
			for(int k=0;k<2;k++){
				String str = String.valueOf(engine.props[getPropIndex(j, k)].getNums());
				int color = g.getColor();
				g.setColor(0x000000);
				g.drawString(str, propX+spaceX*k, propY+spaceY*j, 20);
				g.setColor(color);
			}
		}
		
		g.drawImage(playing_stop, 500,459, 20);			//��ͣ��Ϸ��ť
	}
	
	private int getPropIndex(int x, int y){
		if(x==0 && y==0)return 0;
		if(x==1 && y==0)return 1;
		if(x==2 && y==0)return 2;
		if(x==3 && y==0)return 3;
		if(x==0 && y==1)return 4;
		if(x==1 && y==1)return 5;
		if(x==2 && y==1)return 6;
		if(x==3 && y==1)return 7;
		return -1;
	}
	
	private void moveRole(int towards) {
		switch (towards) {
		case 0: // ����--����
			if(own.mapy>=176){
				own.mapy -= own.speed;
			}
			break;
		case 1: // ����--����
			own.direction = 1;
			if(own.mapy + own.height<452){
				own.mapy += own.speed;
			}
			break;
		}
	}
	
	private void drawNum(SGraphics g, int num, int x, int y) {
		Image imgNumeber = Resource.loadImage(Resource.id_shop_figure);
		String number = String.valueOf(num);
		int numW = imgNumeber.getWidth()/10, numH = imgNumeber.getHeight();
		for (byte i = 0; i < number.length(); i++) {
			g.drawRegion(imgNumeber, (number.charAt(i) - '0') * numW, 0, numW, numH, 0, x + i * (numW + 1), y, 0);
		}
	}
	
	private void drawProp(SGraphics g,int num,int x,int y){
		Image playing_prop=Resource.loadImage(Resource.id_playing_prop);
		String number=String.valueOf(num);
		int propW = playing_prop.getWidth()/8, propH = playing_prop.getHeight();
		for(byte i=0;i<number.length();i++){
			g.drawRegion(playing_prop, (number.charAt(i) - '0')* propW, 0, propW, propH, 0, x+i * (propW + 1), y, 0);
		}
	}
	
	/*����ˮ��Ҫ���ĵ�����*/
	private void hitFruit(Weapon fruit){
		own.scores += fruit.scores;
		own.scores2 += fruit.scores;
		own.hitFruits++;
		own.hitRatio++;
		scores = own.scores;
		scores2 = own.scores2;
		hitFruits = own.hitFruits;
		hitRatio = own.hitRatio;
		fruit.status = FRUIT_HIT;
	}
	
	/*��������Ҫ�ñ������*/
	private void hitWolf(Role wolf){
		wolf.status = ROLE_DEATH;		
		wolf.speed += 10;
		own.hitNum++;
		own.hitTotalNum++;
		own.hitBuble++;
		own.hitRatio++;
		hitNum = own.hitNum;
		hitTotalNum = own.hitTotalNum;
		hitBuble = own.hitBuble;
		hitRatio = own.hitRatio;
		if(wolf.role != null){
			own.scores += wolf.role.scores;
			own.scores2 += wolf.role.scores2;
			scores = own.scores;
			scores2 = own.scores2;
		}
	}
	
	/*����Ҫ����ľ�*/
	private void initDataNextLevel(){
		batch = 0;
		HASWOLF_ONE = false;
		HASWOLF_TWO = false;
		HASWOLF_THREE = false;
		HASWOLF_FOUR = false;
		weapon.clearObjects(); // ��ն���
		batches.clearObject(); // ��ն���
	}
	
	/*��Ϸ����Ҫ���������*/
	private void initDataGameOver(){
		hitNum = own.hitNum = 0;
		lifeNum = own.lifeNum = 0;
		scores = own.scores = 0;
		scores2 = own.scores2 = 0;
		hitBuble = own.hitBuble = 0;
		hitFruits = own.hitFruits = 0;
		hitTotalNum = own.hitTotalNum = 0;
		hitRatio = own.hitRatio = 0;
		useProps = own.useProps = 0;
		level = 1;
		rewardLevel = 1;
		batch = 0;
		HASWOLF_ONE = false;
		HASWOLF_TWO = false;
		HASWOLF_THREE = false;
		HASWOLF_FOUR = false;
		weapon.clearObjects(); // ��ն���
		batches.clearObject(); // ��ն���
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
		Resource.freeImage(Resource.id_pass_cloud2);   
		Resource.freeImage(Resource.id_pass_cloud1);   
		Resource.freeImage(Resource.id_pass_cloud);   
	}

}
