package sheepwar;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.util.Collision;
import cn.ohyeah.stb.util.RandomValue;

public class StateGame implements Common{
	
	public static Exploder[] exploders = new Exploder[12];
//	public SGraphics g ;
	
	/*从下往上判断四个梯子上是否有狼，从右到左*/
	public static boolean HASWOLF_ONE;
	public static boolean HASWOLF_TWO;
	public static boolean HASWOLF_THREE;
	public static boolean HASWOLF_FOUR;
	
	/*判断梯子上是否都有狼*/
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
	public StateSingleScore stateSingleScore;
	
	public static Role npc;
	private int eIndex;

	/*游戏关卡*/
	public short level = 1; 
	/*奖励关卡*/
	public short rewardLevel = 1;
	
	public boolean isRewardLevel, isReward;
	
	/*当前关卡狼出现的批次*/
	public short batch;
	
	/*关卡信息*/
	public static int[][] LEVEL_INFO = {
		/*0-关卡，1-该关卡击中狼的数量， 2-每批狼出现的间隔时间（秒），3-该关卡狼的位置（0-上面, -1-下面）*/
		{1, 32, 3, 0},  	//第一关
		{2, 36, 3, -1},  	//第二关
		{3, 40, 3, 0},  	//第三关
		{4, 44, 3, -1},  	//第四关
		{5, 48, 3, 0},  	//第五关
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
	
	/*奖励关卡信息*/
	public static int [][] REWARD_LEVEL_INFO = {
		/*0-关卡，1-该关卡击中狼的数量， 2-每批狼出现的间隔时间（秒），3-该关卡狼的位置（0-上面, -1-下面）*/
		{1,16,3,-1},			//奖励关卡一
		{2,16,3,-1},			//奖励关卡二
		{3,16,3,-1},			//奖励关卡三
		{4,16,3,-1},			
		{5,16,3,-1},			
		{6,16,2,-1},			
		{7,16,2,-1},			
	};
	
	/*游戏过度时间*/
	private long gameBufferTimeS, gameBufferTimeE;
	private boolean isNextLevel;
	
	/*控制子弹发射的变量*/
	private long startTime, endTime;
	private boolean isAttack = true;
	private int bulletInterval = 2;   
	
	/*时光闹钟*/
	public static boolean pasueState;
	private long pasueTimeS,  pasueTimeE;
	private int pasueInterval = 10;
	
	/*加速*/
	public static boolean speedFlag;
	private long addSpeedTime,addSpeedTime2;
	private int speedLiquidInterval = 30;
	
	/*防狼套装*/
	private long proEndTime;
	private long proStartTime;
	private long protectInterval = 5;
	public static boolean protectState;
	
	/*驱散竖琴*/
	private long harpStartTime,harpEndTime;
	private long harpInterval = 1;				//便于控制驱散竖琴的效果
	public static boolean harpState;
	
	/*强力磁石*/
	private long magnetStartTime,magnetEndTime;
	private long magnetInterval = (long) 0.1;
	public static boolean magnetState;
	
	/*激光枪状态*/
//	public static boolean glareState,isHitted = false;
//	
	/*无敌拳套*/
	private boolean isUseGlove, isShowGlove, golveFlag=true;
	private long gloveEndTime, gloveStartTime;
	private int gloveInterval = 15;
	
	/*玩家存档数据*/
	public static int lifeNum;		//生命数
	public static int scores;		//积分
	public static int scores2;		//单关积分
	public static int hitNum;		//单关击中狼的数
	public static int hitTotalNum;	//击中狼的总数
	public static int hitBuble;		//击中的气球数
	public static int useProps;		//使用的道具数
	public static int hitFruits;	//击中的水果数
	public static int hitRatio;		//击中目标数
	public static int hitBooms;		//击中子弹数
	public static int attainment;	//成就数
	
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
			}else if(isAttack){ //普通攻击
				weapon.createBomb(own, Weapon.WEAPON_MOVE_LEFT);
				own.bombNum ++;
				if(own.bombNum%2==0){
					isAttack = false;
					startTime = System.currentTimeMillis()/1000;
				}
			}
			
		}else if(keyState.containsAndRemove(KeyCode.NUM1)&& own.status ==ROLE_ALIVE){    	//时光闹钟
				pasueState = true;
				pasueTimeS = System.currentTimeMillis()/1000;
				own.scores +=1000;
				int propId = engine.pm.propIds[0]-53;
				updateProp(propId);
		}else if(keyState.containsAndRemove(KeyCode.NUM2)&& own.status ==ROLE_ALIVE){ 		//捕狼网
				weapon.createNet(own, Weapon.WEAPON_MOVE_LEFT);
				own.scores +=1000;
				int propId = engine.pm.propIds[1]-53;
				updateProp(propId);
		}else if(keyState.containsAndRemove(KeyCode.NUM3)&& own.status ==ROLE_ALIVE){		//盾牌
				protectState = true;
				weapon.createProtect(own);
				own.scores +=1000;
				proStartTime = System.currentTimeMillis()/1000;
				int propId = engine.pm.propIds[2]-53;
				updateProp(propId);
		}else if(keyState.containsAndRemove(KeyCode.NUM4)&& own.status ==ROLE_ALIVE){		//激光枪
				weapon.createGlare(own,Weapon.WEAPON_MOVE_LEFT);
//				glareState = true;
				int propId = engine.pm.propIds[3]-53;
				own.scores +=1000;
				updateProp(propId);

		}else if(keyState.containsAndRemove(KeyCode.NUM5)&& own.status ==ROLE_ALIVE){		//驱散竖琴
			    harpState = true;
				weapon.createHarp(own);
				own.scores +=1000;
				harpStartTime = System.currentTimeMillis()/1000;
				int propId = engine.pm.propIds[4]-53;
				updateProp(propId);
		}else if(keyState.containsAndRemove(KeyCode.NUM6)&& own.status ==ROLE_ALIVE){		//速度提升液
				if(!speedFlag){
					own.speed = own.speed + CreateRole.para[4];
					speedFlag = true;
					own.scores +=1000;
					addSpeedTime = System.currentTimeMillis()/1000;
					int propId = engine.pm.propIds[5]-53;
					updateProp(propId);
				}
		}else if(keyState.containsAndRemove(KeyCode.NUM7)&& own.status ==ROLE_ALIVE){		//强力磁石
				magnetStartTime = System.currentTimeMillis()/1000;
				magnetState = true;
				int propId = engine.pm.propIds[6]-53;
				own.scores +=1000;
				updateProp(propId);
		}else if(keyState.containsAndRemove(KeyCode.NUM8) && own.status ==ROLE_ALIVE){		//木偶->可以增加一条生命
				own.lifeNum ++;
				lifeNum = own.lifeNum;
				own.scores +=1000;					//使用道具增加1000分
				int propId = engine.pm.propIds[7]-53;
				updateProp(propId);
		}else if(keyState.containsAndRemove(KeyCode.NUM9)){		//暂停							
			
		}else if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)){ 	//返回
			StateSubMenu sm = new StateSubMenu();
			int index = sm.processSubMenu();
			if(index == 0){		//返回游戏
				
			}else if(index == 1){
				StateShop ss =  new StateShop();
				ss.processShop();
			}else if(index == 2){
				StateHelp sh = new StateHelp();
				sh.processHelp();
			}else if(index == 3){
				engine.state = STATUS_MAIN_MENU;
				clear();
				initDataGameOver();
				
				//同步道具
				engine.pm.sysProps();
				
				//保存成就
			/*	ServiceWrapper sw = engine.getServiceWrapper();
				GameAttainment ga = sw.readAttainment(engine.attainmentId);
				if(((ga==null && own.scores>0) || (ga.getScores()<=own.scores) && own.scores>0)){
					engine.saveAttainment(own);
				}*/
			}
		}
	}
	
	private void updateProp(int propId){
		if(!engine.isDebugMode()){
			/*engine.props[propId].setNums(engine.props[propId].getNums()-1);
			own.useProps++;
			useProps = own.useProps;
			own.scores += 1000;  //使用道具加1000分*/
		}
	}
	
	public void show(SGraphics g){
		drawGamePlaying(g);
		createRole.showSheep(g,own);
//		if(weapon.id != 4){
			batches.showWolf(g, weapon);
//		}
		weapon.showFruit(g);
		weapon.showBomb(g);
		weapon.showBoom(g,own);			
		weapon.showNet(g);
		weapon.showProtect(g, own);
		/*for(int i= batches.npcs.size() -1;i>=0;i--){
			npc = (Role)batches.npcs.elementAt(i);
		}*/
		weapon.showGlare(g, own, batches);
//		if(glareState){
//			weapon.showGlareEffect(g, batches);
//		}
		weapon.showHarp(g, batches);
		weapon.showMagnetEffect(g, batches);
		if(batches.redWolf!=null){
			batches.showRedWolf(g,weapon);				
		}
		if(isShowGlove){
			weapon.showGloveCreate(g);
		}
		weapon.showGloves(g, own);
		
		Exploder exploder = null;
		for(int i=0;i<exploders.length;i++){
			if(exploders[i] != null){
				exploder = exploders[i];
				exploder.drawExplode(g, this);
			}
		}
		stateSingleScore.showSingleScore(g, batches,own);		//弹出射中气球（狼）的得分
		stateSingleScore.showAttackBoom(g, weapon, own);		//弹出射中子弹的得分
	}
	
	public void execute(){
		/*控制子弹发射间隔*/
		endTime = System.currentTimeMillis()/1000; 
		if(isAttack==false && endTime-startTime>=bulletInterval){
			isAttack = true;
		}
		/*控制拳套时间间隔*/
		gloveEndTime = System.currentTimeMillis()/1000;
		if(golveFlag && (gloveEndTime  - gloveStartTime >= gloveInterval)){
			isShowGlove = true;
			golveFlag = false;
		}
		if(isShowGlove && own.mapy<=190){
			isUseGlove = true;
			isShowGlove = false;
		}
		
		/*时光闹钟效果时间*/
		pasueTimeE = System.currentTimeMillis()/1000;
		if(pasueState && (pasueTimeE-pasueTimeS)>=pasueInterval){
			pasueState = false;
		}
		/*加速效果时间*/
		addSpeedTime2 = System.currentTimeMillis()/1000;
		if(addSpeedTime2 - addSpeedTime >speedLiquidInterval){			
			speedFlag = false;
			own.speed = CreateRole.para[4];
		}
		/*防狼套装的时间控制*/
		proEndTime = System.currentTimeMillis()/1000;
		if(proEndTime - proStartTime > protectInterval){
			protectState = false;
		}
		
		/*驱散竖琴时间间隔控制*/
		harpEndTime = System.currentTimeMillis()/1000;
		if(harpEndTime - harpStartTime >harpInterval){
			harpState = false;
		}
		
		System.out.println("magnetState:"+magnetState);
		/*强力磁石控制时间*/
		magnetEndTime = System.currentTimeMillis()/1000;
		if(magnetEndTime - magnetStartTime > magnetInterval){
			magnetState = false;
		}
		/*
		激光枪状态控制
		glareState = false;*/
		
		/*游戏过度时间*/
		gameBufferTimeE = System.currentTimeMillis()/1000;
		
		/*创建一批狼*/
		createNpc();
		
		/*创建狼*/
		createRedNpc();
		
		/*关卡过关判断*/
		isNextLevel();  			
		
		/*普通攻击碰撞检测*/
		bombAttackNpcs();
		
		/*无敌拳套碰撞检测*/
		gloveAttackNpcs();
		
		/*捕狼道具碰撞检测*/
		netAttackNpcs();
		
		/*狼的普通攻击碰撞检测*/
		boomAttackPlayer();
		
		/*激光枪碰撞检测*/
		glareAttackNpcs();
		
		/*移除死亡对象*/
		removeDeath();

		/*判断4个位置上是否都有狼*/
		checkFourPosition();
		
		/*判断游戏成功或失败*/
		gameSuccessOrFail();
		gameOver();
	}

	private void checkFourPosition() {
		if(IS_FOUR_WOLF){
			IS_FOUR_WOLF = false;
			own.status = ROLE_DEATH;
			own.lifeNum --;
			lifeNum = own.lifeNum;
			System.out.println("生命数减一");
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
			if(!isRewardLevel){  //普通关卡过关判断
				for (int i = 1; i < 16; i++) {
					if ((level == i && own.hitNum >= LEVEL_INFO[level - 1][1]) 
							|| (engine.isDebugMode() && own.hitNum>=3)) {
						System.out.println("过关");
						gameBufferTimeS = System.currentTimeMillis()/1000;
						hitNum = own.hitNum = 0;
						isNextLevel = true;
						if(level%2==0){
							isRewardLevel = true;
							System.out.println("下一关为奖励关卡");
						}
						level++;
					}
				}
			}else{	//奖励关卡过关判断
				if((rewardLevel%2==1 && batch >= (RewardLevelBatchesInfo[rewardLevel-1].length - 1))
						||(rewardLevel%2==0 && batches.redWolf.bombNum>=16) 
						|| (engine.isDebugMode() && (batch>1 || (rewardLevel%2==0 && batches.redWolf.bombNum>1)))){
					System.out.println("奖励关卡结束");
					gameBufferTimeS = System.currentTimeMillis()/1000;
					isNextLevel = true;
					batch = 0;
					hitNum = own.hitNum = 0;
					isRewardLevel = false;
					isReward = true;
					if(rewardLevel <6){		//如果没有判断，rewardLevel=7后，rewardLevel++=8数组越界10-11
						rewardLevel++;
					}
					if(batches.redWolf!=null){
						batches.redWolf.bombNum = 0;
					}
				}
			}
		}

		/*进入过关界面*/
		if(isNextLevel==true && gameBufferTimeE-gameBufferTimeS>1){
			isNextLevel = false;
			initDataNextLevel();	//清空数据
			StateNextLevel stateLevel = new StateNextLevel();
			stateLevel.processNextLevel(own);
			isReward = false;
		}
	}
	
	private void gameSuccessOrFail() {
		if(own.lifeNum<=0 && !isGameOver){		/*游戏失败*/
			System.out.println("游戏失败:");
			isGameOver = true;
			isSuccess = false;
			gameBufferTimeS = System.currentTimeMillis()/1000;
			
		}else if(level > 15 && !isGameOver){	/*游戏通关*/
			System.out.println("游戏成功：");
			isGameOver = true;
			isSuccess = true;
			gameBufferTimeS = System.currentTimeMillis()/1000;
			//同步道具
			engine.pm.sysProps();
			
			//保存成就
			/*ServiceWrapper sw = engine.getServiceWrapper();
			GameAttainment ga = sw.readAttainment(engine.attainmentId);
			if(((ga==null && own.scores>0) || (ga.getScores()<=own.scores) && own.scores>0)){
				engine.saveAttainment(own);
			}*/
		}
	}
	
	private void gameOver(){
		//System.out.println("gameBufferTimeE-gameBufferTimeS="+(gameBufferTimeE-gameBufferTimeS));
		if(isGameOver && gameBufferTimeE-gameBufferTimeS>1){
			isGameOver=false;
			initDataGameOver();  //清空数据
			StateGameSuccessOrFail sgs = new StateGameSuccessOrFail();
			sgs.processGameSuccessOrFail(isSuccess, own);
			engine.state = STATUS_MAIN_MENU;
			clear();
		}
	}
	
	/*无敌拳套击中npc判断*/
	private void gloveAttackNpcs() {
		for(int i=weapon.gloves.size()-1;i>=0;i--){
			Weapon boxing = (Weapon) weapon.gloves.elementAt(i);
			for(int j=batches.npcs.size()-1;j>=0;j--){
				Role npc = (Role) batches.npcs.elementAt(j);
				Role ballon = npc.role;
				if(ballon != null && npc.status == ROLE_ALIVE && npc.status2 == ROLE_IN_AIR){
					if(Collision.checkSquareCollision(boxing.mapx, boxing.mapy, boxing.width, boxing.height, ballon.mapx, ballon.mapy, ballon.width, npc.height+30/*ballon.height*/)
							&& npc.status!=ROLE_MOVE_DOWN){	//当狼行至地面时，任何武器将不能杀死
						hitWolf(npc);
						print();
					}
				}
			}
			
			/*拳套出界时移除*/
			if((boxing.mapx+boxing.width <=0) || boxing.mapy >= 466){
				weapon.gloves.removeElement(boxing);
			}
		}
	}

	/*判断激光枪是否击中狼*/
 	private void glareAttackNpcs() {
		for(int i=weapon.glares.size()-1;i>=0;i--){
			Weapon glare = (Weapon) weapon.glares.elementAt(i);
			if(!glare.isUse){
				for(int j=batches.npcs.size()-1;j>=0;j--){
					Role npc = (Role) batches.npcs.elementAt(j);
					System.out.println("狼的状态："+npc.status);
					if(npc.status == ROLE_ALIVE ){
//						System.out.println("激光枪是否碰到了狼："+Collision.checkSquareCollision(npc.mapx, npc.mapy, npc.width, npc.height, 
//								glare.mapx, glare.mapy, glare.width, glare.height));
						System.out.println("碰撞时激光的宽度："+glare.width);
						if(Collision.checkSquareCollision(npc.mapx, npc.mapy, npc.width, npc.height, 
								glare.mapx, glare.mapy, glare.width, glare.height)&& npc.status2 == ROLE_IN_AIR /*&& npc.status == ROLE_IN_AIRglareState == true*/){
							Exploder exploder = new Exploder(npc.mapx,npc.mapy);
							exploders[eIndex] = exploder;
							if(eIndex < exploders.length-1){
								eIndex ++;
							}else{
								eIndex=0;
							}
							batches.npcs.removeElementAt(j);
							hitWolf(npc);
							glare.status = HIT_NPC;
							print();
						}else{
							glare.status = NOT_HIT_NPC;
						}
					}
				}
				
			/*射水果*/
			for(int k=weapon.fruits.size()-1;k>=0;k--){
					Weapon fruit = (Weapon) weapon.fruits.elementAt(k);
					if(Collision.checkSquareCollision(fruit.mapx, fruit.mapy, fruit.width, fruit.height,glare.mapx, glare.mapy, glare.width, glare.height)){
						hitFruit(fruit);
						print();
					}
				}
			}
			/*激光出界时移除*/
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

	/*判断狼是否击中玩家*/
 	private void boomAttackPlayer(){
		for(int i = weapon.booms.size() - 1;i >=0;i--){
			Weapon boom = (Weapon)weapon.booms.elementAt(i);
			if(own.status == ROLE_ALIVE){
				if(Collision.checkSquareCollision(boom.mapx, boom.mapy, boom.width, boom.height, own.mapx,
						own.mapy, own.width, own.height)){
					if(!protectState){			//玩家是否有防狼套装
						own.status = ROLE_DEATH;
						own.lifeNum --;
						lifeNum = own.lifeNum;
						System.out.println("生命数减一");
					}
					weapon.booms.removeElement(boom);
				}
			}
			/*子弹出界时移除*/
			if(boom.mapx >= gameW){
				weapon.booms.removeElement(boom);
			}
		}
	}

	/*羊的捕狼网技能攻击*/
	private void netAttackNpcs() {
		for(int i=weapon.nets.size()-1;i>=0;i--){
			Weapon net = (Weapon) weapon.nets.elementAt(i);
			if(net.isUse){
				for(int j=batches.npcs.size()-1;j>=0;j--){
					Role npc = (Role) batches.npcs.elementAt(j);
					/*Role ballon = npc.role;*/			//彩色气球只能被拳套击中,如果成功着陆，则需要击落的狼增加5个灰太狼
					if(npc.status == ROLE_ALIVE	/*&& ballon.id !=multicolour*//*&& npc.status2 == ROLE_IN_AIR*/){		//为了解决当狼成功逃脱后不受攻击
						if(Collision.checkSquareCollision(npc.mapx, npc.mapy, npc.width,
								npc.height, net.mapx, net.mapy, net.width, net.height)
								&& npc.status != ROLE_SUCCESS){
							System.out.println("狼的状态2:"+npc.status2);
							hitWolf(npc);
							System.out.println("狼的生命状态:"+npc.status);
							print();
						}
					}
				}
				
				/*射水果*/
				for(int k=weapon.fruits.size()-1;k>=0;k--){
					Weapon fruit = (Weapon) weapon.fruits.elementAt(k);
					if(Collision.checkSquareCollision(fruit.mapx, fruit.mapy, fruit.width, fruit.height,net.mapx, net.mapy, net.width, net.height)){
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

	/*创建红太狼npc*/
	private void createRedNpc(){
		if(!isNextLevel){
			if(isRewardLevel){
				//System.out.println("当前奖励关卡:"+rewardLevel);
				if(rewardLevel % 2 ==0 && batches.redWolf==null){					//偶数奖励关卡出现红太狼
					System.out.println("奖励关卡创建红太狼");
					batches.redWolf = batches.createRedWolf();
				}
			}else{
				//System.out.println("当前关卡："+level);
				if(level % 2!=0 && level != 1 && batches.redWolf==null){			//奇数关卡会有红太狼的出现
					System.out.println("普通关卡创建红太狼");
					batches.redWolf = batches.createRedWolf();
				}
			}
		}
	}
	
	private void createNpc(){
		if(isAllDown() && !isNextLevel){
			if(!isRewardLevel){
				if(engine.timePass(LEVEL_INFO[level-1][2]*1000)){
					System.out.println("普通关卡创建一批狼");
					batches.createBatches(level, batch, LEVEL_INFO[level-1][3]);
					batch = (short) ((batch+1) % BatchesInfo[level-1].length);
				}
			}else{
				if(engine.timePass(REWARD_LEVEL_INFO[rewardLevel -1][2]*1000)&&rewardLevel%2!=0){	
					System.out.println("奖励关卡创建一批狼");
					batches.createBatchesReward(rewardLevel, batch, REWARD_LEVEL_INFO[rewardLevel-1][3]);
					batch = (short)((batch+1) % RewardLevelBatchesInfo[rewardLevel-1].length);
				}
			}
		}
	}
	
	/*判断狼是否都已经下降或者上升*/
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
		/*水果出界移除*/
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
				if(ballon != null && npc.status == ROLE_ALIVE /*&& npc.scores2 == ROLE_IN_AIR*/	/*&& ballon.id != multicolour*/){
					if(Collision.checkSquareCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height,
							ballon.mapx, ballon.mapy, ballon.width, 30/*ballon.height*/)){
						hitWolf(npc);
						print();
						weapon.bombs.removeElement(bomb);
					}
					else if(Collision.checkSquareCollision(bomb.mapx, bomb.mapy, bomb.width,
							bomb.height, npc.mapx, npc.mapy, npc.width, npc.height)&& npc.status != ROLE_SUCCESS){
						bomb.direction = Weapon.WEAPON_MOVE_DOWN;
						bomb.speedY = bomb.speedX + 10;
					}
				}
			}
			
			/*射水果*/
			for(int k=weapon.fruits.size()-1;k>=0;k--){
				Weapon fruit = (Weapon) weapon.fruits.elementAt(k);
				if(Collision.checkSquareCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, fruit.mapx, fruit.mapy, fruit.width, fruit.height)){
					hitFruit(fruit);
					print();
					weapon.bombs.removeElement(bomb);
				}
			}
			
			/*击中狼发射的子弹*/
			for(int k=weapon.booms.size()-1;k>=0;k--){
				Weapon boom = (Weapon) weapon.booms.elementAt(k);
				if(Collision.checkSquareCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height,boom.mapx, boom.mapy, boom.width, boom.height)){
					hitBoom(boom);
					System.out.println("weapon的状态"+weapon.status);
					print();
					weapon.bombs.removeElement(bomb);
				}
			}
			
			/*子弹出界时移除*/
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
		/*奖励关卡图片资源*/
		Image pass_cloud2 = Resource.loadImage(Resource.id_pass_cloud2);
		Image pass_cloud = Resource.loadImage(Resource.id_pass_cloud);
		Image passShadowCloud = Resource.loadImage(Resource.id_cloud1);
		Image pass_cloud1 = Resource.loadImage(Resource.id_pass_cloud1);
		Image pumpkin = Resource.loadImage(Resource.id_pumpkin);
		
		g.drawImage(game_bg, 0, 0, 20);
		if((isRewardLevel && !isNextLevel) || isReward){		//画出奖励关卡界面
			g.drawImage(pass_cloud, 50, 80, 20);
			g.drawImage(pass_cloud, 216, 80, 20);
			g.drawImage(pass_cloud, 404, 140, 20);		//轮子下面的云朵
			for(int i=0;i<4;i++){			//固定的云层 南瓜没有
				g.drawImage(passShadowCloud, 0+i*60, 80+10, 20);
				g.drawImage(pass_cloud, 0+i*60, 80, 20);
			}
			/*上面第二层云*/
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
			
			/*下面第二层云*/
			int down_cloud2Y = 484;
			down_cloud2Index=(down_cloud2Index+1)%cloud2W;
			if(down_cloud2Index<=len){
				g.drawRegion(pass_cloud2, len-down_cloud2Index, 0, ScrW, cloud2H, 0, 0, down_cloud2Y, 20);
			}else{
				g.drawRegion(pass_cloud2, (cloud2W-down_cloud2Index), 0, ScrW-(cloud2W-down_cloud2Index), cloud2H, 0, 0, down_cloud2Y, 20);
				g.drawRegion(pass_cloud2, 0, 0, (cloud2W-down_cloud2Index), cloud2H, 0, ScrW-(cloud2W-down_cloud2Index), down_cloud2Y, 20);
			}

			/*中间的云*/
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
			
			/*上面第一层云*/
			int cloud1W = pass_cloud1.getWidth(),cloud1H = pass_cloud1.getHeight();
			int cloud1Y = -23;
			cloudIndex=(cloudIndex+1)%cloud1W;
			if(cloudIndex<=cloud1W-ScrW){
				g.drawRegion(pass_cloud1, cloudIndex, 0, ScrW, cloud1H, 0, 0, cloud1Y, 20);
			}else{
				g.drawRegion(pass_cloud1, cloudIndex, 0, cloud1W-cloudIndex, cloud1H, 0, 0, cloud1Y, 20);
				g.drawRegion(pass_cloud1, 0, 0, cloudIndex, cloud1H, 0, cloud1W-cloudIndex, cloud1Y, 20);
			}
			
			/*下面第一层云*/
			int down_cloud1Y = 496;
			down_cloudIndex=(down_cloudIndex+1)%cloud1W;
			if(down_cloudIndex<=cloud1W-ScrW){
				g.drawRegion(pass_cloud1, down_cloudIndex, 0, ScrW, cloud1H, 0, 0, down_cloud1Y, 20);
			}else{
				g.drawRegion(pass_cloud1, down_cloudIndex, 0, cloud1W-down_cloudIndex, cloud1H, 0, 0, down_cloud1Y, 20);
				g.drawRegion(pass_cloud1, 0, 0, down_cloudIndex, cloud1H, 0, cloud1W-down_cloudIndex, down_cloud1Y, 20);
			}
			g.drawImage(playing_menu, 491, 0, 20);
			g.drawImage(playing_level, 491+32, 25, 20);								//游戏中 左侧的关卡图片		
			drawNum(g, rewardLevel, 491+32+playing_level.getWidth()+10, 25);
			drawNum(g, own.lifeNum, 491+66+multiply.getWidth()+10, 147);			//奖励关卡羊的生命数,应该改为一条命
			System.out.println("奖励关卡--------》》》》"+rewardLevel);		//  8?
			System.out.println("击中的狼数"+own.hitNum);
			int num = REWARD_LEVEL_INFO[rewardLevel-1][1]-own.hitNum;
			if(num<0){
				num = 0;
			}
			drawNum(g, num, 45+multiply.getWidth()+10, 12);
			
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
			for(int i=0;i<4;i++){   //阶梯
				g.drawImage(playing_step, 377, 153+i*89, 20);
				g.drawImage(ladder, 426, 183+i*89, 20);
			}
			
			g.drawImage(playing_menu, 491, 0, 20);
			g.drawImage(playing_level, 491+32, 25, 20);						//游戏中 左侧的关卡图片		
			drawNum(g, level, 491+32+playing_level.getWidth()+10, 25);
			drawNum(g, own.lifeNum, 491+66+multiply.getWidth()+10, 147);			//羊的生命数
			
			if(level % 2 == 0){														//偶数关卡出现南瓜(出现四只狼推南瓜则南瓜砸下，玩家失败)\
				g.drawRegion(pumpkin, 0, 0, pumpkin.getWidth(), pumpkin.getHeight(), 0, 256, 15, 20);
				//TODO 如果狼到达上面的总数为四只则南瓜掉落
			/*	if (npc.status == ROLE_SUCCESS) {
					g.drawRegion(pumpkin, 0, 0, pumpkin.getWidth(), pumpkin.getHeight(), 0, 256, 15, 20);	
				}
				*/
			}
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
		g.drawImage(sheep_head, 491+26, 142, 20);						//游戏中 右侧 的羊的头像		
		g.drawImage(wolf_head, 12, 10, 20);								//游戏中 左侧 的狼的头像		
		g.drawImage(multiply, 491+66, 147, 20);	
		g.drawImage(multiply, 45, 12, 20);	
		System.out.println("正常关卡------"+level);
		int num =  LEVEL_INFO[level-1][1]-own.hitNum;
		if(num<0){
			num = 0;
		}
		drawNum(g, num, 45+multiply.getWidth()+10, 12);

		int propLeftMenuX = 497+1,propRightMenuX= 564+1,propMenuY = 185-7,distanceMenuY = 4;
		int numLeftX = 547,numRight = 612;
		int menuH = playing_prop_memu.getHeight();
		for(int i=0;i<4;i++){                                                                
			g.drawImage(playing_prop_memu, propLeftMenuX,propMenuY+i*(distanceMenuY+menuH), 20);
			drawProp(g, i, propLeftMenuX+5,propMenuY+4+i*(distanceMenuY+menuH));                                              
			drawNum(g, i+1, numLeftX, propMenuY+menuH-29+i*(distanceMenuY+menuH));//提示技能按键：1-4{540,223}
			
			g.drawImage(playing_prop_memu, propRightMenuX,propMenuY+i*(distanceMenuY+menuH), 20);
			drawProp(g, i+4, propRightMenuX+5,propMenuY+4+i*(distanceMenuY+menuH));  //第二列对应原图片中的后四个
			drawNum(g, i+4+1, numRight, propMenuY+menuH-29+i*(distanceMenuY+menuH));//提示技能键5-8{}
		}
		
		/*道具数量*/
		/*int propX=503, propY=220, spaceY=71, spaceX=67;
		for(int j=0;j<4;j++){
			for(int k=0;k<2;k++){
				String str = String.valueOf(engine.props[getPropIndex(j, k)].getNums());
				int color = g.getColor();
				g.setColor(0x000000);
				g.drawString(str, propX+spaceX*k, propY+spaceY*j, 20);
				g.setColor(color);
			}
		}*/
		
		g.drawImage(playing_stop, 500,459, 20);			//暂停游戏按钮
	}
	
	/*private int getPropIndex(int x, int y){
		if(x==0 && y==0)return 0;
		if(x==1 && y==0)return 1;
		if(x==2 && y==0)return 2;
		if(x==3 && y==0)return 3;
		if(x==0 && y==1)return 4;
		if(x==1 && y==1)return 5;
		if(x==2 && y==1)return 6;
		if(x==3 && y==1)return 7;
		return -1;
	}*/
	
	private void moveRole(int towards) {
		switch (towards) {
		case 0: // 往上--主角
			if(own.mapy>=176){
				own.mapy -= own.speed;
			}
			break;
		case 1: // 往下--主角
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
	
	/*击中水果要更改的数据*/
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
	
	/*击中子弹要改变的数据 */
	private void hitBoom(Weapon boom) {
		scores += boom.scores;
		own.hitBooms ++;
		hitNum = own.hitNum;
		hitBuble = own.hitBuble;
		hitRatio = own.hitRatio;
		hitBooms = own.hitBooms;
		boom.status = BOOM_HIT;
	}
	
	/*击中狼所要该变的数据*/
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
	
	/*过关要清楚的据*/
	private void initDataNextLevel(){
		batch = 0;
		HASWOLF_ONE = false;
		HASWOLF_TWO = false;
		HASWOLF_THREE = false;
		HASWOLF_FOUR = false;
		weapon.clearObjects(); // 清空对象
		batches.clearObject(); // 清空对象
	}
	
	/*游戏结束要清楚的数据*/
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
		weapon.clearObjects(); // 清空对象
		batches.clearObject(); // 清空对象
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
