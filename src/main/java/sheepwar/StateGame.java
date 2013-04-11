package sheepwar;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.DrawUtil;
import cn.ohyeah.stb.ui.PopupConfirm;
import cn.ohyeah.stb.ui.PopupText;
import cn.ohyeah.stb.util.Collision;
import cn.ohyeah.stb.util.RandomValue;

public class StateGame implements Common{
	
	public static Exploder[] exploders = new Exploder[12];
	public static Exploder[] ss = new Exploder[12];
	public static Exploder[] mf = new Exploder[12];
	public static Exploder[] sprop = new Exploder[12];
//	public SGraphics g ;
	
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
	
	public static Role npc;
	private int eIndex, sIndex, mIndex, pIndex;

	/*��Ϸ�ؿ�*/
	public static short level = 1; 
	/*�����ؿ�*/
	public static short rewardLevel = 1;
	
	public static boolean isRewardLevel, isReward;
	
	/*�����ؿ����ѵ�����*/
	public static int reward_nums;
	
	/*��ǰ�ؿ��ǳ��ֵ�����*/
	public static short batch;
	
	/*�����ؿ�������ʱʧ�ܱ�� trueΪ��������falseû������*/
	public static boolean rewardLevelFail;
	
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
		{16, 0, 5, -1},  //Ԥ��
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
	private int bulletInterval = 2000;  //��λ����
	
	/*������*/
	//private long repeatingT;
	private int repeatingInterval = 200; //��λ����
	private boolean isRepeating;
	
	/*������*/
	private long repeatingSTime, repeatingETime;
	private int repeatingInterval2 = 100;	//��λ����
	public static boolean isFourRepeating;
	public static boolean second;
	private boolean third;
	private boolean fourth;
	
	/*ʱ������*/
	public static boolean pasueState, isUsePasue;
	public static long pasueTimeS,  pasueTimeE;
	private int pasueInterval = 10; 	//��λ��
	public static int pasueValideTime;	//����ʣ����Чʱ��
	
	/*����*/
	//public static boolean speedFlag;
	//private long addSpeedTime,addSpeedTime2;
	//private int speedLiquidInterval = 30;
	
	/*������װ*/
	public static long proEndTime;
	public static long proStartTime;
	private int protectInterval = 30;
	public static boolean protectState; //ʹ���˷��ǵ���
	public static int protectValideTime; //����ʣ����Чʱ��
	
	/*��������*/
	private long harpStartTime,harpEndTime;
	private int harpInterval = 3;				//���ڿ�����ɢ���ٵ�Ч��
	public static boolean harpState;
	
	/*ǿ����ʯ*/
	private long magnetStartTime,magnetEndTime;
	private int magnetInterval = 1;
	public static boolean magnetState;
	
	/*����ǹ״̬*/
//	public static boolean glareState,isHitted = false;

	/*�޵�ȭ��*/
	public static boolean isUseGlove, isShowGlove, golveFlag=true;
	public static long gloveEndTime, gloveStartTime;
	public static int gloveValideTime;
	private int gloveInterval = 20;
	
	public boolean down;	//�Ϲ��Ƿ���
	
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
	public static int hitBooms;		//�����ӵ���
	public static int attainment;	//�ɾ���
	
	public static int scores3;		//�ɾ��еĻ���
	public static int level2;		//�ɾ��еĹؿ�
	public static int useProps2;	//�ɾ���ʹ�õĵ�����
	public static int hitFruits2;	//�ɾ��л��е�ˮ����
	public static int hitTotalNum2;	//�ɾ��л����ǵ�����
	public static int hitBooms2;	//�ɾ��л����ӵ���
	
	public static boolean a=true, b, c ,d ,e;	//��ѧ�ؿ�������ʾ
	public static boolean  b2, c2 ,d2 ,e2;	   //��ѧ�ؿ�������ʾ
	public static boolean  c3 ,d3;	   //��ѧ�ؿ�������ʾ
	private long bStartTime, bEndTime,cStartTime, cEndTime, dStartTime, dEndTime, eStartTime, eEndTime;
	
	private int tempx=ScrW, tempy=20, tempx2=ScrW, tempy2=30, sWidth, sTempy;
	
	public void handleKey(KeyState keyState){
		if (keyState.containsMoveEventAndRemove(KeyCode.UP)) {
			moveRole(0);
			
		} else if (keyState.containsMoveEventAndRemove(KeyCode.DOWN)) {
			moveRole(1);
			
		} else if (keyState.containsAndRemove(KeyCode.OK)&& own.status ==ROLE_ALIVE) {	
			if(isUseGlove){
				weapon.createGloves(own, 0,own.mapy + 4);	//����ȭ��
				weapon.createGloves(own, 1,own.mapy + 30);	//����ȭ��
				isUseGlove = false;
				golveFlag = true;
				gloveStartTime = System.currentTimeMillis() / 1000;
			}else if(isAttack){ //��ͨ����
				weapon.createBomb(own, Weapon.WEAPON_MOVE_LEFT);
				own.bombNum ++;
				//if(own.bombNum%2==0){
					isAttack = false;
					startTime = System.currentTimeMillis();
				//}
				if(!isFourRepeating){  //�ж��Ƿ���������������,
					isRepeating = true;	 //û���þ�����ͨ��������
				}else{
					second = true;
				}
				repeatingSTime = System.currentTimeMillis();
			}
			
		}else if(keyState.containsAndRemove(KeyCode.NUM1)&& own.status ==ROLE_ALIVE){    	//ʱ������
			int propId = engine.pm.propIds[0]-53;
			if(engine.props[propId].getNums()>0 || engine.isDebugMode()){
				pasueState = true;
				isUsePasue = true;
				pasueTimeS = System.currentTimeMillis()/1000;
				if(!engine.isDebugMode()){
					updateProp(propId);
				}
				createPromptProp(propId);
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM3)&& own.status ==ROLE_ALIVE){ 		//������
			int propId = engine.pm.propIds[1]-53;
			if(engine.props[propId].getNums()>0 || engine.isDebugMode()){
				weapon.createNet(own, Weapon.WEAPON_MOVE_LEFT);
				if(!engine.isDebugMode()){
					updateProp(propId);
				}
				createPromptProp(propId);
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM5)&& own.status ==ROLE_ALIVE){		//����
			int propId = engine.pm.propIds[2]-53;
			if(engine.props[propId].getNums()>0 || engine.isDebugMode()){
				protectState = true;
				//weapon.createProtect(own);
				proStartTime = System.currentTimeMillis()/1000;
				if(!engine.isDebugMode()){
					updateProp(propId);
				}
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM7)&& own.status ==ROLE_ALIVE){		//����ǹ
			int propId = engine.pm.propIds[3]-53;
			if(engine.props[propId].getNums()>0 || engine.isDebugMode()){
				weapon.createGlare(own,Weapon.WEAPON_MOVE_LEFT);
//				glareState = true;
				if(!engine.isDebugMode()){
					updateProp(propId);
				}
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM2)&& own.status ==ROLE_ALIVE){		//��ɢ����
			int propId = engine.pm.propIds[4]-53;
			if(engine.props[propId].getNums()>0 || engine.isDebugMode()|| SheepWarGameEngine.isFirstGame){
				harpState = true;
				//weapon.createHarp(own);
				harpStartTime = System.currentTimeMillis()/1000;
				if(!engine.isDebugMode() && !SheepWarGameEngine.isFirstGame){
					updateProp(propId);
				}
				createPromptProp(propId);
				if(SheepWarGameEngine.isFirstGame){
					d3 = false;
				}
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM4)&& own.status ==ROLE_ALIVE){		//����������
				//if(!speedFlag){
					int propId = engine.pm.propIds[5]-53;
					if(!isFourRepeating && engine.props[propId].getNums()>0 || engine.isDebugMode() || SheepWarGameEngine.isFirstGame){
						//own.speed = own.speed + CreateRole.para[4];
						//speedFlag = true;
						//addSpeedTime = System.currentTimeMillis()/1000;
						isFourRepeating = true;
						if(!engine.isDebugMode() && !SheepWarGameEngine.isFirstGame){
							updateProp(propId);
						}
						createPromptProp(propId);
						if(SheepWarGameEngine.isFirstGame){
							c3 = false;
						}
					}
				//}
		}else if(keyState.containsAndRemove(KeyCode.NUM6)&& own.status ==ROLE_ALIVE){		//ǿ����ʯ
			int propId = engine.pm.propIds[6]-53;
			if(engine.props[propId].getNums()>0 || engine.isDebugMode()){
				magnetStartTime = System.currentTimeMillis()/1000;
				magnetState = true;
				if(!engine.isDebugMode()){
					updateProp(propId);
				}
				createPromptProp(propId);
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM8) && own.status ==ROLE_ALIVE){		//ľż->��������һ������
			int propId = engine.pm.propIds[7]-53;
			if(engine.props[propId].getNums()>0 || engine.isDebugMode()){
				own.lifeNum ++;
				lifeNum = own.lifeNum;
				if(!engine.isDebugMode()){
					updateProp(propId);
				}
				createPromptProp(propId);
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM9)){		//��ͣ							
			
		}else if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)){ 	//����
			if(SheepWarGameEngine.isFirstGame){
				SheepWarGameEngine.isFirstGame = false;
				initDataGameOver();
				engine.state = STATUS_MAIN_MENU;
				clear();
			}else{
				StateSubMenu sm = new StateSubMenu();
				int index = sm.processSubMenu();
				if(index == 0){		//������Ϸ
					
				}else if(index == 1){
					clear();
					StateShop ss =  new StateShop(engine);
					ss.processShop();
				}else if(index == 2){
					clear();
					StateHelp sh = new StateHelp();
					sh.processHelp();
				}else if(index == 3){
					System.out.println("�˳���Ϸ");
					printInfo();
					
					//ͬ������
					//engine.pm.sysProps();
					
					//�˳���Ϸ�������ѵ���
					setWolfStatus();
					
					//�����������еĻ���
					engine.saveAttainment();
					
					//������Ϸ��¼
					engine.saveRecord();
					
					/*������ҳɾ�*/
					engine.updateAttainmen();
					
					initDataGameOver();
					engine.state = STATUS_MAIN_MENU;
					clear();
				}
				
				/*ʱ�����ã���ͣ��Ҫ�ָ����ߵ���Чʱ��*/
				if(pasueState && isUsePasue){		//ʱ������
					long t1 = pasueTimeE-pasueTimeS;
					long t2 = System.currentTimeMillis()/1000;
					pasueTimeS = t2-t1;
				}
				
				if(protectState){					//���ǵ���
					long t3 = proEndTime-proStartTime;
					long t4 = System.currentTimeMillis()/1000;
					proStartTime = t4-t3;
				}
				
				if(!isUseGlove && golveFlag){
					long t5 = gloveEndTime-gloveStartTime;
					long t6 = System.currentTimeMillis()/1000;
					gloveStartTime = t6-t5;
				}
			}
		}
	}
	
	private void setWolfStatus() {
		int count=0;
		for(int j=batches.npcs.size()-1;j>=0;j--){
			Role wolf = (Role) batches.npcs.elementAt(j);
			if(wolf.status == ROLE_SUCCESS){
				count++;
			}
		}
		if(count==1){
			HASWOLF_ONE = true;
		}else if(count==2){
			HASWOLF_ONE = true;
			HASWOLF_TWO = true;
		}else if(count==3){
			HASWOLF_ONE = true;
			HASWOLF_TWO = true;
			HASWOLF_THREE = true;
		}else if(count == 4){
			HASWOLF_ONE = true;
			HASWOLF_TWO = true;
			HASWOLF_THREE = true;
			HASWOLF_FOUR = true;
		}
	}

	private void updateProp(int propId){
		engine.props[propId].setNums(engine.props[propId].getNums()-1);
		own.useProps++;
		useProps = own.useProps;
		own.scores += 1000;  //ʹ�õ��߼�1000��
		own.scores2 += 1000;
		scores = own.scores;
		scores2 = own.scores2;
	}
	
	public void show(SGraphics g){
		drawGamePlaying(g);
		createRole.showSheep(g,own);
		batches.showBuble(g);
//		if(weapon.id != 4){
			batches.showWolf(g, weapon);
//		}
		weapon.showFruit(g);
		weapon.showBomb(g);
		weapon.showBoom(g,own);			
		weapon.showNet(g);
		weapon.showProtect(g, own);
		weapon.showGlare(g, own, batches);
		//weapon.showHarp(g, batches);
		//weapon.showMagnetEffect(g, batches);
		if(batches.redWolf!=null){
			batches.showRedWolf(g,weapon);				
		}
		//if(isShowGlove){
			weapon.showGloveCreate(g, own);
		//}
		weapon.showGloves(g, own);
		
		//��ʾ��̬Ч��
		showDynamic(g);
		
		//��ʾ���߳���ʱ��
		showTime(g);
		
		/*��ѧ�ؿ�*/
		teachingLevel();
	}
	
	/*��ѧ�ؿ�*/
	private void teachingLevel(){
		if(SheepWarGameEngine.isFirstGame){
			bEndTime = System.currentTimeMillis()/1000;
			cEndTime = System.currentTimeMillis()/1000;
			dEndTime = System.currentTimeMillis()/1000;
			eEndTime = System.currentTimeMillis()/1000;
			PopupText pt = UIResource.getInstance().buildDefaultPopupText();
			if(a){	//��ѧ�ؿ���һ����ʾ
				pt.setText("��ӭ�����ѧ�ؿ�����ȷ�ϼ�#W������");
				pt.popup();
				a = false;
				b2 = true;
				bStartTime = System.currentTimeMillis()/1000;
			}
			if(b){
				pt.setText("�����¼�#W�ƶ�ϲ���򣬰�ȷ�ϼ�#W�������");
				pt.popup();
				b = false;
				b2 = false;
				c2 = true;
				cStartTime = System.currentTimeMillis()/1000;
			}
			if(c){
				pt.setText("���÷��ڵ����������𣿰����ּ�4#Wʹ�õ���������");
				pt.popup();
				c = false;
				c2 = false;
				c3 = true;
				d2 = true;
				dStartTime = System.currentTimeMillis()/1000;
			}
			if(d){
				pt.setText("�����ּ�2#Wʹ�õ���������������ѽ���Ļ�̫�ǡ�");
				pt.popup();
				d = false;
				d2 = false;
				d3 = true;
				e2 = true;
				eStartTime = System.currentTimeMillis()/1000;
			}
			if(e){
				pt.setText("�����ּ�#Wʹ�õ��ߣ����߿����̳ǹ���");
				pt.popup();
				e = false;
				e2 = false;
				SheepWarGameEngine.isFirstGame = false;
				initDataGameOver();
				weapon = new Weapon(this);
				createRole = new CreateRole();
				batches = new Batches();
				own = createRole.createSheep();
				engine.state = STATUS_GAME_PLAYING;
			}
			if(e2 && (eEndTime-eStartTime)>10){
				e = true;
			}
			if(d2 && (dEndTime-dStartTime)>10){
				d = true;
			}
			if(c2 && (cEndTime-cStartTime)>7){
				c = true;
			}
			if(b2 && (bEndTime-bStartTime)>5){
				b = true;
			}
		}
	}
	
	private void showDynamic(SGraphics g) {
		Exploder exploder = null;
		for(int i=0;i<exploders.length;i++){
			if(exploders[i] != null){
				exploder = exploders[i];
				exploder.drawExplode(g, this);
			}
		}
		
		for(int i=0;i<ss.length;i++){
			if(ss[i] != null){
				exploder = ss[i];
				exploder.showScore(engine, g, exploder.score);
			}
		}
		
		for(int i=0;i<mf.length;i++){
			if(mf[i] != null){
				exploder = mf[i];
				exploder.showMagnetEffect(g);
			}
		}
		
		for(int i=0;i<sprop.length;i++){
			if(sprop[i] != null){
				exploder = sprop[i];
				exploder.showUseProp(g, exploder.propId);
			}
		}
	}

	private void showTime(SGraphics g) {
		Image prop = Resource.loadImage(Resource.id_prop);
		engine.setFont(30, true);
		int col = g.getColor();
		g.setColor(0xff0000);
		
		int propW = prop.getWidth()/8, propH = prop.getHeight();
		int mapx = 370, mapx2 = 370, mapx3 = 430, mapy = 46, offX, offX2;
		/*ʱ�����ӳ���ʱ��*/
		if(pasueState && isUsePasue){
			if(isFourRepeating && protectState){
				mapx = 310;
			}else if(isFourRepeating && !protectState){
				mapx = 370;
			}else if(!isFourRepeating && !protectState){
				mapx = 430;
			}else if(!isFourRepeating && protectState){
				mapx = 370;
			}
			if(pasueInterval-(pasueTimeE-pasueTimeS)<10){
				offX = 15;
			}else{
				offX = 5;
			}
			g.drawRegion(prop, 0, 0, propW, propH, 0, mapx, 4, 20);
			drawNum(g, Integer.parseInt(String.valueOf(pasueInterval-(pasueTimeE-pasueTimeS))), mapx+offX, mapy);
			//g.drawString(String.valueOf(pasueInterval-(pasueTimeE-pasueTimeS)), mapx, mapy, 20);
		}
		
		/*������װ��ʱ�����*/
		if(protectState){
			if(isFourRepeating && pasueState){
				mapx2 = 370;
			}else if(isFourRepeating && !pasueState){
				mapx2 = 370;
			}else if(!isFourRepeating && !pasueState){
				mapx2 = 430;
			}else if(!isFourRepeating && pasueState){
				mapx2 = 430;
			}
			if(pasueInterval-(pasueTimeE-pasueTimeS)<10){
				offX2 = 10;
			}else{
				offX2 = 5;
			}
			g.drawRegion(prop, 2*propW, 0, propW, propH, 0, mapx2, 5, 20);
			drawNum(g, Integer.parseInt(String.valueOf(protectInterval-(proEndTime - proStartTime))), mapx2+offX2, mapy);
			//g.drawString(String.valueOf(protectInterval-(proEndTime - proStartTime)), mapx+55, mapy, 20);
		}
		
		if(isFourRepeating){
			g.drawRegion(prop, 5*propW, 0, propW, propH, 0, mapx3, 5, 20);
		}
		
		engine.setDefaultFont();
		g.setColor(col);
	}

	public void execute(){
		
		/*����ʹ����ʾ*/
		promptUseProp();
		
		/*�����ӵ�������*/
		endTime = System.currentTimeMillis(); 
		if(isAttack==false && endTime-startTime>=bulletInterval){
			isAttack = true;
		}
		
		/*�������ڶ����ӵ�*/
		if(isRepeating && endTime-startTime>=repeatingInterval){
			weapon.createBomb(own, Weapon.WEAPON_MOVE_LEFT);
			own.bombNum ++;
			isRepeating = false;
		}
		
		repeatingETime = System.currentTimeMillis();
		/*����������--�ڶ����ӵ�*/
		if(isFourRepeating && second && repeatingETime-repeatingSTime>=repeatingInterval2){
			weapon.createBomb(own, Weapon.WEAPON_MOVE_LEFT);
			own.bombNum ++;
			third = true;
			second = false;
			repeatingSTime = System.currentTimeMillis();
		}
		/*����������--�������ӵ�*/
		if(isFourRepeating && third && repeatingETime-repeatingSTime>=repeatingInterval2){
			weapon.createBomb(own, Weapon.WEAPON_MOVE_LEFT);
			own.bombNum ++;
			fourth = true;
			third = false;
			repeatingSTime = System.currentTimeMillis();
		}
		/*����������--���ĸ��ӵ�*/
		if(isFourRepeating && fourth && repeatingETime-repeatingSTime>=repeatingInterval2){
			weapon.createBomb(own, Weapon.WEAPON_MOVE_LEFT);
			own.bombNum ++;
			fourth = false;
			repeatingSTime = System.currentTimeMillis();
		}
		
		
		/*����ȭ��ʱ����*/
		gloveEndTime = System.currentTimeMillis()/1000;
		if(!SheepWarGameEngine.isFirstGame && !isRewardLevel && golveFlag && (gloveEndTime  - gloveStartTime >= gloveInterval)){
			isShowGlove = true;
			golveFlag = false;
		}
		if(isShowGlove && own.mapy<=190){
			isUseGlove = true;
			isShowGlove = false;
		}
		
		/*ʱ������Ч��ʱ��*/
		pasueTimeE = System.currentTimeMillis()/1000;
		if(isUsePasue && pasueState && (pasueTimeE-pasueTimeS)>=pasueInterval){
			pasueState = false;
			isUsePasue = false;
		}
		
		/*����Ч��ʱ��*/
		//addSpeedTime2 = System.currentTimeMillis()/1000;
		//if(addSpeedTime2 - addSpeedTime >speedLiquidInterval){			
		//	speedFlag = false;
		//	own.speed = CreateRole.para[4];
		//}
		
		/*������װ��ʱ�����*/
		proEndTime = System.currentTimeMillis()/1000;
		if((proEndTime - proStartTime > protectInterval)){
			protectState = false;
		}
		//��������ͣʱ��
		if(!isUsePasue && pasueState && (pasueTimeE - pasueTimeS > 3)){
			pasueState = false;
		}
		
		/*��������ʱ��������*/
		harpEndTime = System.currentTimeMillis()/1000;
		if(harpEndTime - harpStartTime >harpInterval){
			harpState = false;
		}
		
		//System.out.println("magnetState:"+magnetState);
		/*ǿ����ʯ����ʱ��*/
		magnetEndTime = System.currentTimeMillis()/1000;
		if(magnetEndTime - magnetStartTime > magnetInterval){
			magnetState = false;
		}
		/*
		����ǹ״̬����
		glareState = false;*/
		
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
		
		/*������ʯ*/
		magnetEffect();
		
		/*�Ƴ���������*/
		removeDeath();

		/*�ж�4��λ�����Ƿ�����*/
		checkFourPosition();
		
		/*���Ĺ�֮���˫���ؿ���������*/
		createBubles();
		
		/*�ж���Ϸ�ɹ���ʧ��*/
		gameSuccessOrFail();
		gameOver();
		
		/*����������ѵ���ȫ���Ƴ�*/
		removeSuccessWolf();
		
		/*ʹ����������ѣ��״̬*/
		wolfDizzy();
	}

	private boolean isUseProp2=true, isUseProp4;
	private void promptUseProp() {
		if(!SheepWarGameEngine.isFirstGame && !isRewardLevel && level==1 && !isNextLevel){
			if(isUseProp2 && HASWOLF_ONE && HASWOLF_TWO){
				PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
				pc.setText("�Ѿ��ж�ֻ�����ѣ��Ƿ�ʹ�õ�����������������ɹ�����Ļ�̫��?");
				int index = pc.popup();
				if(index==0){
					int propId = engine.pm.propIds[4]-53;
					System.out.println("propNum:"+engine.props[propId].getNums());
					if(engine.props[propId].getNums()>0){
						harpState = true;
						harpStartTime = System.currentTimeMillis()/1000;
						updateProp(propId);
						createPromptProp(propId);
					}else{
						pc.setText("�����������㣬�Ƿ�ȥ�̳ǹ���?");
						int i = pc.popup();
						if(i==0){
							StateShop ss =  new StateShop(engine);
							ss.processShop();
						}
					}
				}
				isUseProp2 = false;
			}
		}
		if(!SheepWarGameEngine.isFirstGame && !isRewardLevel && level==2 && !isNextLevel){
			if(isUseProp4 && !isFourRepeating){
				PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
				pc.setText("�Ƿ�ʹ���������ߣ��÷��ڷ��Ӹ����������");
				int index = pc.popup();
				if(index==0){
					int propId = engine.pm.propIds[5]-53;
					if(engine.props[propId].getNums()>0){
						isFourRepeating = true;
						updateProp(propId);
						createPromptProp(propId);
					}else{
						pc.setText("�����������㣬�Ƿ�ȥ�̳ǹ���?");
						int i = pc.popup();
						if(i==0){
							StateShop ss =  new StateShop(engine);
							ss.processShop();
						}
					}
				}
				isUseProp4 = false;
			}
		}
	}

	private void wolfDizzy() {
		if(own!=null && own.status != ROLE_DEATH && harpState){
			for(int j=batches.npcs.size()-1;j>=0;j--){
				Role npc = (Role) batches.npcs.elementAt(j);
				if(npc.status == ROLE_SUCCESS){
					npc.status = ROLE_DIZZY;
					//System.out.println("ѣ��"+npc.status);
				}
			}
			HASWOLF_ONE = false;
			HASWOLF_TWO = false;
			HASWOLF_THREE = false;
			HASWOLF_FOUR = false;
		}
	}

	private void createBubles() {
		batches.bublesETime = System.currentTimeMillis()/1000;
		if(!isRewardLevel && level>=4 && level%2==0 && (batches.bublesETime-batches.bublesSTime)>3){
			batches.createBallon();
			batches.bublesSTime = System.currentTimeMillis()/1000;
		}
	}
	
	private void createPromptProp(int propId){
		Exploder e = new Exploder(own.mapx, own.mapy, 1, propId);
		sprop[mIndex] = e;
		if(pIndex < sprop.length-1){
			pIndex ++;
		}else{
			pIndex=0;
		}
	}

	private void magnetEffect() {
		for(int j = batches.npcs.size() - 1;j>=0;j--){
			Role npc = (Role)batches.npcs.elementAt(j);
			if(npc.status2 == ROLE_IN_AIR && npc.status != ROLE_SUCCESS/*&& npc.mapy>30*/ && StateGame.magnetState){
				Exploder m = new Exploder(npc.mapx,npc.mapy);
				mf[mIndex] = m;
				if(mIndex < mf.length-1){
					mIndex ++;
				}else{
					mIndex=0;
				}
				hitWolf(npc, null);
				batches.npcs.removeElement(npc);
				print();
			}
		}
	}

	private void removeSuccessWolf() {
		if(own!=null && own.status == ROLE_DEATH){
			for(int j=batches.npcs.size()-1;j>=0;j--){
				Role npc = (Role) batches.npcs.elementAt(j);
				if(npc.status == ROLE_SUCCESS){
					batches.npcs.removeElement(npc);
				}
			}
		}
	}

	private void checkFourPosition() {
		if(IS_FOUR_WOLF){
			IS_FOUR_WOLF = false;
			if(!isRewardLevel){		//��ͨ�ؿ���ֻ������
				if(level%2!=0){
					hitOwn(own);
				}else{
					down = true;
				}
			}else{				//�����ؿ���ֻ������
				down = true;
			}
			
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
				if((rewardLevel%2==1 && (REWARD_LEVEL_INFO[rewardLevel-1][1]-own.hitNum-reward_nums<=0)/*batch >= (RewardLevelBatchesInfo[rewardLevel-1].length - 1)*/)
						||(rewardLevel%2==0 && batches.redWolf.bombNum>=16) 
						|| (engine.isDebugMode() && (batch>1 || (rewardLevel%2==0 && batches.redWolf.bombNum>1)))
						|| rewardLevelFail){
					System.out.println("�����ؿ�����");
					gameBufferTimeS = System.currentTimeMillis()/1000;
					isNextLevel = true;
					isReward = true;
					batch = 0;
					hitNum = own.hitNum = 0;
					isRewardLevel = false;
					if(rewardLevel <6){		
						rewardLevel++;
					}
					if(batches.redWolf!=null){
						batches.redWolf.bombNum = 0;
					}
				}
			}
		}

		/*������ؽ���*/
		if(level<=15 && isNextLevel==true && gameBufferTimeE-gameBufferTimeS>1){
			isNextLevel = false;
			isReward = false;
			StateNextLevel stateLevel = new StateNextLevel();
			stateLevel.processNextLevel(own);
			initDataNextLevel();	//�������
			isUseProp4 = true;
		}
	}
	
	private void gameSuccessOrFail() {
		if(own.lifeNum<=0 && !isGameOver){		/*��Ϸʧ��*/
			System.out.println("��Ϸʧ��:");
			isGameOver = true;
			isSuccess = false;
			gameBufferTimeS = System.currentTimeMillis()/1000;
			
			//��������
			engine.saveAttainment();
			
			/*������ҳɾ�*/
			engine.updateAttainmen();
		}else if(level > 15 && !isGameOver){	/*��Ϸͨ��*/
			System.out.println("��Ϸ�ɹ���");
			isGameOver = true;
			isSuccess = true;
			gameBufferTimeS = System.currentTimeMillis()/1000;
			//ͬ������
			engine.pm.sysProps();
			
			//��������
			engine.saveAttainment();
			/*������ҳɾ�*/
			engine.updateAttainmen();
		}
	}
	
	private void gameOver(){
		//System.out.println("gameBufferTimeE-gameBufferTimeS="+(gameBufferTimeE-gameBufferTimeS));
		if(isGameOver && gameBufferTimeE-gameBufferTimeS>1){
			isGameOver=false;
			StateGameSuccessOrFail sgs = new StateGameSuccessOrFail();
			int index = sgs.processGameSuccessOrFail(isSuccess, own);
			if(index==1){//����������
				engine.state = STATUS_MAIN_MENU;
				initDataGameOver();  //�������
				clear();
			}else{//������Ϸ
				initDataGameOver();
				weapon = new Weapon(this);
				createRole = new CreateRole();
				batches = new Batches();
				own = createRole.createSheep();
				engine.state = STATUS_GAME_PLAYING;
			}
		}
	}
	
	/*�޵�ȭ�׻���npc�ж�*/
	private void gloveAttackNpcs() {
		for(int i=weapon.gloves.size()-1;i>=0;i--){
			Weapon boxing = (Weapon) weapon.gloves.elementAt(i);
			for(int j=batches.npcs.size()-1;j>=0;j--){
				Role npc = (Role) batches.npcs.elementAt(j);
				Role ballon = npc.role;
				if(ballon != null && npc.status == ROLE_ALIVE && npc.status2 == ROLE_IN_AIR){
					if(Collision.checkSquareCollision(boxing.mapx, boxing.mapy, boxing.width, boxing.height, ballon.mapx, ballon.mapy, ballon.width, npc.height+30/*ballon.height*/)
							&& npc.status2!=ROLE_ON_GROUND){	//������������ʱ���κ�����������ɱ��
						
						boxing.hitNum++;  //�޵�ȭ�׻����ǵ�������һ
						hitWolf(npc,boxing);
						print();
					}
				}
			}
			
			/*��ˮ��*/
			for(int k=weapon.fruits.size()-1;k>=0;k--){
				Weapon fruit = (Weapon) weapon.fruits.elementAt(k);
				if(Collision.checkSquareCollision(boxing.mapx, boxing.mapy, boxing.width, boxing.height, fruit.mapx, fruit.mapy, fruit.width, fruit.height)){
					hitFruit(fruit);
					print();
				}
			}
			/*������*/
			for(int k=batches.ballons.size()-1;k>=0;k--){
				Role buble = (Role) batches.ballons.elementAt(k);
				if(Collision.checkSquareCollision(boxing.mapx, boxing.mapy, boxing.width, boxing.height, buble.mapx, buble.mapy, buble.width, 30)){
					hitBuble(buble);
					print();
				}
			}
			
			/*�����Ƿ�����ӵ�*/
			for(int k=weapon.booms.size()-1;k>=0;k--){
				Weapon boom = (Weapon) weapon.booms.elementAt(k);
				if(Collision.checkSquareCollision(boxing.mapx, boxing.mapy, boxing.width, boxing.height,boom.mapx, boom.mapy, boom.width, boom.height)){
					hitBoom(boom);
					print();
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
					if(npc.status == ROLE_ALIVE ){
						//System.out.println("����Ŀ�ȣ�"+glare.width);
						if(Collision.checkSquareCollision(npc.mapx, npc.mapy, npc.width, npc.height, 
								glare.mapx, glare.mapy, glare.width, glare.height)&& npc.status2 == ROLE_IN_AIR ){
							Exploder exploder = new Exploder(npc.mapx,npc.mapy);
							exploders[eIndex] = exploder;
							if(eIndex < exploders.length-1){
								eIndex ++;
							}else{
								eIndex=0;
							}
							batches.npcs.removeElementAt(j);
							hitWolf(npc, null);
							print();
						}
					}
				}
				
				/*�����Ƿ�����ӵ�*/
				for(int k=weapon.booms.size()-1;k>=0;k--){
					Weapon boom = (Weapon) weapon.booms.elementAt(k);
					if(Collision.checkSquareCollision(boom.mapx, boom.mapy, boom.width, boom.height,glare.mapx, glare.mapy, glare.width, glare.height)){
						hitBoom(boom);
						print();
					}
				}
				
				/*������*/
				for(int k=batches.ballons.size()-1;k>=0;k--){
					Role buble = (Role) batches.ballons.elementAt(k);
					if(Collision.checkSquareCollision(buble.mapx, buble.mapy, buble.width, 30,glare.mapx, glare.mapy, glare.width, glare.height)){
						hitBuble(buble);
						print();
					}
				}
				
				/*��ˮ��*/
				for(int k=weapon.fruits.size()-1;k>=0;k--){
					Weapon fruit = (Weapon) weapon.fruits.elementAt(k);
					if(Collision.checkSquareCollision(fruit.mapx, fruit.mapy, fruit.width, fruit.height,glare.mapx, glare.mapy, glare.width, glare.height)){
						hitFruit(fruit);
						weapon.fruits.removeElement(fruit);
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
 		System.out.println("own.hitBuble:"+own.hitBuble);
 		System.out.println("own.hitFruits:"+own.hitFruits);
 		System.out.println("own.hitNum:"+own.hitNum);
 		System.out.println("own.hitTotalNum:"+own.hitTotalNum);
 		System.out.println("own.scores:"+own.scores);
 		System.out.println("own.scores2:"+own.scores2);
 		System.out.println("own.hitRatio:"+own.hitRatio);
 		System.out.println("own.lifeNum:"+own.lifeNum);
 		System.out.println("own.useProps:"+own.useProps);
 	}

	/*�ж����Ƿ�������*/
 	private void boomAttackPlayer(){
		for(int i = weapon.booms.size() - 1;i >=0;i--){
			Weapon boom = (Weapon)weapon.booms.elementAt(i);
			if(own.status == ROLE_ALIVE){
				if(Collision.checkSquareCollision(boom.mapx, boom.mapy, boom.width, boom.height, own.mapx,
						own.mapy, own.width, own.height)){
					if(!protectState && !SheepWarGameEngine.isFirstGame){			//����Ƿ��з�����װ, �����ڽ�ѧ�ؿ�
						if(isRewardLevel){
							rewardLevelFail = true;
							System.out.println("�����ؿ���Ϸʧ��");
						}else{
							hitOwn(own);
						}
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
					/*Role ballon = npc.role;*/			//��ɫ����ֻ�ܱ�ȭ�׻���,����ɹ���½������Ҫ�����������5����̫��
					if(npc.status == ROLE_ALIVE	/*&& ballon.id !=multicolour*//*&& npc.status2 == ROLE_IN_AIR*/){		//Ϊ�˽�����ǳɹ����Ѻ��ܹ���
						if(Collision.checkSquareCollision(npc.mapx, npc.mapy, npc.width,
								npc.height, net.mapx, net.mapy, net.width, net.height)
								&& (npc.status != ROLE_SUCCESS && npc.status2 != ROLE_ON_GROUND)){
							hitWolf(npc, null);
							print();
						}
					}
				}
				
				/*��ˮ��*/
				for(int k=weapon.fruits.size()-1;k>=0;k--){
					Weapon fruit = (Weapon) weapon.fruits.elementAt(k);
					if(Collision.checkSquareCollision(fruit.mapx, fruit.mapy, fruit.width, fruit.height,net.mapx, net.mapy, net.width, net.height)){
						hitFruit(fruit);
						print();
					}
				}
				
				/*������*/
				for(int k=batches.ballons.size()-1;k>=0;k--){
					Role buble = (Role) batches.ballons.elementAt(k);
					if(Collision.checkSquareCollision(buble.mapx, buble.mapy, buble.width, 30,net.mapx, net.mapy, net.width, net.height)){
						hitBuble(buble);
						print();
					}
				}
				
				Weapon.netTimeE = System.currentTimeMillis()/1000;
				if(net.isUse && Weapon.netTimeE-Weapon.netTimeS>=Weapon.netInterval){
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
			}else if(npc.mapx>=gameW){  
				batches.npcs.removeElement(npc);
			}else if(npc.direction==ROLE_MOVE_LEFT && (npc.mapx+npc.width)<=0){
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
		
		/*�ǵ��ӵ�*/
		/*for(int m=weapon.booms.size()-1;m>=0;m--){
			Weapon boom = (Weapon)weapon.booms.elementAt(m);
			if(boom.status==OBJECT_HIT){
				weapon.booms.removeElement(boom);
			}
		}*/
		
		/*�����ؿ����ѵ���*/
		if(isRewardLevel){
			for(int w=batches.npcs.size()-1;w>=0;w--){
				Role r = (Role) batches.npcs.elementAt(w);
				if(r.status == ROLE_SUCCESS){
					reward_nums ++;
					batches.npcs.removeElement(r);
				}
			}
		}
		
		/*�������*/
		for(int n=batches.ballons.size()-1;n>=0;n--){
			Role babule = (Role) batches.ballons.elementAt(n);
			if((babule.mapy+babule.width)<=0){
				batches.ballons.removeElement(babule);
			}
		}
	}
	
	private void bombAttackNpcs(){
		for(int i=weapon.bombs.size()-1;i>=0;i--){
			Weapon bomb = (Weapon) weapon.bombs.elementAt(i);
			for(int j=batches.npcs.size()-1;j>=0;j--){
				Role npc = (Role) batches.npcs.elementAt(j);
				Role ballon = npc.role;
				if(ballon != null && npc.status == ROLE_ALIVE && npc.status2 == ROLE_IN_AIR){
					if(Collision.checkSquareCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height,
							ballon.mapx, ballon.mapy, ballon.width, 30/*ballon.height*/)){
						if(ballon.id != multicolour){
							hitWolf(npc, null);
							print();
							weapon.bombs.removeElement(bomb);
						}else{
							bomb.direction = Weapon.WEAPON_MOVE_DOWN;
							bomb.speedY = bomb.speedX + 10;
						}
					}
					else if(Collision.checkSquareCollision(bomb.mapx, bomb.mapy, bomb.width,
							bomb.height, npc.mapx, npc.mapy, npc.width, npc.height)&& npc.status != ROLE_SUCCESS){
						bomb.direction = Weapon.WEAPON_MOVE_DOWN;
						bomb.speedY = bomb.speedX + 10;
					}
				}
			}
			
			/*��ˮ��*/
			for(int k=weapon.fruits.size()-1;k>=0;k--){
				Weapon fruit = (Weapon) weapon.fruits.elementAt(k);
				if(Collision.checkSquareCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, fruit.mapx, fruit.mapy, fruit.width, fruit.height)){
					hitFruit(fruit);
					print();
					weapon.bombs.removeElement(bomb);
				}
			}
			
			/*������*/
			for(int k=batches.ballons.size()-1;k>=0;k--){
				Role buble = (Role) batches.ballons.elementAt(k);
				if(Collision.checkSquareCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, buble.mapx, buble.mapy, buble.width, 30)){
					if(buble.id != multicolour){
						hitBuble(buble);
						print();
						weapon.bombs.removeElement(bomb);
					}else{
						bomb.direction = Weapon.WEAPON_MOVE_DOWN;
						bomb.speedY = bomb.speedX + 10;
					}
				}
			}
			
			/*�����Ƿ�����ӵ�*/
			for(int k=weapon.booms.size()-1;k>=0;k--){
				Weapon boom = (Weapon) weapon.booms.elementAt(k);
				if(Collision.checkSquareCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height,boom.mapx, boom.mapy, boom.width, boom.height)){
					hitBoom(boom);
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
	int nanX = 256, nanY = 27, nanY2 = 25;
	int nanFlag,nanIndex=0, arrowFlag, arrowIndex;
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
		Image ladder = Resource.loadImage(Resource.id_ladder);
		Image playing_level = Resource.loadImage(Resource.id_playing_level);
		Image playing_level2 = Resource.loadImage(Resource.id_playing_level2);
		Image sheep_head = Resource.loadImage(Resource.id_sheep_head);
		Image wolf_head = Resource.loadImage(Resource.id_wolf_head);
		Image multiply = Resource.loadImage(Resource.id_multiply);
		/*�����ؿ�ͼƬ��Դ*/
		Image pass_cloud2 = Resource.loadImage(Resource.id_pass_cloud2);
		Image pass_cloud = Resource.loadImage(Resource.id_pass_cloud);
		Image passShadowCloud = Resource.loadImage(Resource.id_cloud1);
		Image pass_cloud1 = Resource.loadImage(Resource.id_pass_cloud1);
		Image pumpkin = Resource.loadImage(Resource.id_pumpkin);
		Image control = Resource.loadImage(Resource.id_control);
		Image fruit = Resource.loadImage(Resource.id_watermelon);
		Image stop = Resource.loadImage(Resource.id_game_stop);
		Image teach_level = Resource.loadImage(Resource.id_teach_level);
		Image arrowhead = Resource.loadImage(Resource.id_arrowhead);
		
		g.drawImage(game_bg, 0, 0, 20);
		int nanW = pumpkin.getWidth()/5, nanH = pumpkin.getHeight();
		if((isRewardLevel && !isNextLevel) || isReward){		//���������ؿ�����
			g.drawImage(pass_cloud, 50, 80, 20);
			g.drawImage(pass_cloud, 216, 80, 20);
			g.drawImage(pass_cloud, 404, 140, 20);		//����������ƶ�
			for(int i=0;i<4;i++){			//�̶����Ʋ� �Ϲ�û��
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
			g.drawImage(playing_level2, 491+12, 18, 20);								//��Ϸ�� ���Ĺؿ�ͼƬ
			if(isNextLevel){
				drawNum(g, rewardLevel-1, 491+32+playing_level.getWidth()+30, 20);
			}else{
				drawNum(g, rewardLevel, 491+32+playing_level.getWidth()+30, 20);
			}
			drawNum(g, own.lifeNum, 491+66+multiply.getWidth()+10, 147);			//�����ؿ����������,Ӧ�ø�Ϊһ����
			g.drawImage(multiply, 491+66, 147, 20);
			
			if(rewardLevel%2==1){
				int num=0;
				if(!isNextLevel){
					num = REWARD_LEVEL_INFO[rewardLevel-1][1]-own.hitNum-reward_nums;
					if(num<0){
						num = 0;
					}
					g.drawImage(multiply, 50, 12, 20);	
					drawNum(g, num, 55+multiply.getWidth(), 12);
					g.drawImage(wolf_head, 12, 10, 20);								//��Ϸ�� ��� ���ǵ�ͷ��
				}else{
					g.drawImage(multiply, 50, 12, 20);	
					drawNum(g, 0, 55+multiply.getWidth(), 12);
					g.drawRegion(fruit, 0, 0, fruit.getWidth()/3, fruit.getHeight(), 0, 12, 2, 20);
				}
			}else{
				int num=0;
				if(!isNextLevel){
					if(batches.redWolf!=null){
						num =16-batches.redWolf.bombNum;
						if(num<0){
							num = 0;
						}
					}
					g.drawImage(multiply, 50, 12, 20);	
					drawNum(g, num, 55+multiply.getWidth(), 12);
					g.drawRegion(fruit, 0, 0, fruit.getWidth()/3, fruit.getHeight(), 0, 12, 2, 20);
				}else{
					g.drawImage(multiply, 50, 12, 20);	
					drawNum(g, 0, 55+multiply.getWidth(), 12);
					g.drawImage(wolf_head, 12, 10, 20);								//��Ϸ�� ��� ���ǵ�ͷ��
				}
			}
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
			if(SheepWarGameEngine.isFirstGame){
				g.drawImage(teach_level, 491+32, 18, 20);	
			}else{
				g.drawImage(playing_level, 491+32, 18, 20);						//��Ϸ�� ���Ĺؿ�ͼƬ	
				if(isNextLevel){
					drawNum(g, level-1, 491+32+playing_level.getWidth()+10, 20);
				}else{
					drawNum(g, level, 491+32+playing_level.getWidth()+10, 20);
				}
			}
			drawNum(g, own.lifeNum, 491+66+multiply.getWidth()+10, 147);			//���������
			g.drawImage(multiply, 491+66, 147, 20);
			
			/*�Ϲ�Ч��*/
			if((level%2==0 && !isNextLevel) || (level!=1 && level%2==1 && isNextLevel)){														//ż���ؿ������Ϲ�(������ֻ�����Ϲ����Ϲ����£����ʧ��)\
				if(down){
					if(nanX<310){
						nanX += 10;
						nanY += 5;
						if(nanX<=276){
							nanIndex=2;
						}else if(nanIndex<=296){
							nanIndex=3;
						}else{
							nanIndex=4;
						}
					}else{
						nanY += 10;
						nanIndex=4;
					}
					g.drawRegion(pumpkin, nanIndex*nanW, 0, nanW, nanH, 0, nanX, nanY, 20);
					if(own.status==ROLE_ALIVE && Collision.checkSquareCollision(own.mapx, own.mapy, own.width, own.height, nanX, nanY, nanW, nanH)){
						hitOwn(own);
					}
				}else{
					nanX = 256; nanY =27;
					if(HASWOLF_ONE && !HASWOLF_TWO && !HASWOLF_THREE && !HASWOLF_FOUR){
						nanIndex=0;
					}
					if(HASWOLF_ONE && HASWOLF_TWO && !HASWOLF_THREE && !HASWOLF_FOUR){
						if(nanFlag<4){
							nanFlag++;
						}else{
							nanIndex = (nanIndex+1)%2;
							nanFlag=0;
						}
						if(nanIndex==1){
							nanX += 5;
						}else{
							nanX = 256;
						}
					}
					if(HASWOLF_ONE && HASWOLF_TWO && HASWOLF_THREE && !HASWOLF_FOUR){
						if(nanFlag<4){
							nanFlag++;
						}else{
							nanIndex =(nanIndex+1)%3;
							nanFlag=0;
						}
						if(nanIndex>0){
							nanX += 5;
						}else{
							nanX = 256;
						}
					}
					g.drawRegion(pumpkin, nanIndex*nanW, 0, nanW, nanH, 0, nanX, nanY, 20);
				}
			}
			
			
			int num =  LEVEL_INFO[level-1][1]-own.hitNum;
			if(num<0){
				num = 0;
			}
			if(!isNextLevel){
				g.drawImage(multiply, 45, 12, 20);	
				drawNum(g, num, 55+multiply.getWidth(), 12);
				g.drawImage(wolf_head, 12, 10, 20);								
			}else{
				g.drawImage(multiply, 45, 12, 20);	
				drawNum(g, 0, 55+multiply.getWidth(), 12);
				g.drawImage(wolf_head, 12, 10, 20);								//��Ϸ�� ��� ���ǵ�ͷ��
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
		//g.drawRegion(playing_point, 0, 0, 46, playing_point.getHeight()/2, 0, 504+35, 59, 20);
		if(own.scores>99999){
			drawNum(g, own.scores, 499+12, 89);
		}else if(own.scores<1000){
			drawNum(g, own.scores, 499+46, 89);
		}else if(own.scores>9999 && own.scores<100000){
			drawNum(g, own.scores, 499+22, 89);
		}else{
			drawNum(g, own.scores, 499+35, 89);
		}
		g.drawImage(sheep_head, 491+26, 142, 20);						//��Ϸ�� �Ҳ� �����ͷ��		

		int LeftMenuX = 497+2,RightMenuX= 564+5,propMenuY = 185,distanceMenuY = 6;
		int controlW = control.getWidth()/8, controlH = control.getHeight();
		for(int i=0;i<4;i++){                                                                
			drawProp(g, i, LeftMenuX+5,propMenuY+10+i*(distanceMenuY+65));           
			g.drawRegion(control, (i+i)*controlW, 0, controlW, controlH, 0, LeftMenuX+5, propMenuY+65-10+i*(distanceMenuY+65), 20);
			
			drawProp(g, i+4, RightMenuX+5,propMenuY+10+i*(distanceMenuY+65));  //�ڶ��ж�ӦԭͼƬ�еĺ��ĸ�
			g.drawRegion(control, (i+i+1)*controlW, 0, controlW, controlH, 0, RightMenuX+5, propMenuY+65-10+i*(distanceMenuY+65), 20);

		}
		
		int sW = stop.getWidth()/2, sH = stop.getHeight();
		int aW = arrowhead.getWidth()/2, aH = arrowhead.getHeight();
		g.drawRegion(stop, 0, 0, sW, sH, 0, 498+29, 467+9, 20);
		if(SheepWarGameEngine.isFirstGame){
			if(arrowFlag<3){
				arrowFlag++;
				arrowIndex=0;
			}else{
				arrowFlag=0;
				arrowIndex=1;
			}
			if(c3){
				g.drawRegion(arrowhead, arrowIndex * aW, 0, aW, aH, 0, 555, 226, 20);
			}
			if(d3){
				g.drawRegion(arrowhead, arrowIndex * aW, 0, aW, aH, 0, 555, 166, 20);
			}
		}
		
		
		/*��������*/
		int propX=512, propY=193, spaceY=71, spaceX=70;
		for(int j=0;j<4;j++){
			for(int k=0;k<2;k++){
				String str = String.valueOf(engine.props[getPropIndex(j, k)].getNums());
				int color = g.getColor();
				engine.setFont(19, true);
				if(engine.props[getPropIndex(j, k)].getNums()<10){
					g.setColor(0x000000);
					DrawUtil.drawRect(g, propX+spaceX*k, propY+spaceY*j+3, 10, 13);
					g.setColor(0xffffff);
					g.drawString(str, propX+spaceX*k, propY+spaceY*j, 20);
				}else{
					g.setColor(0x000000);
					DrawUtil.drawRect(g, propX+spaceX*k-7, propY+spaceY*j+3, 16, 13);
					g.setColor(0xffffff);
					g.drawString(str, propX+spaceX*k-7, propY+spaceY*j, 20);
				}
				g.setColor(color);
				engine.setDefaultFont();
			}
		}
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
	
	public void drawNum(SGraphics g, int num, int x, int y) {
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
		if(fruit.status == OBJECT_NOT_HIT){
			Exploder s = new Exploder(fruit.mapx,fruit.mapy,1);
			s.score = fruit.scores;
			ss[sIndex] = s;
			if(sIndex < ss.length-1){
				sIndex ++;
			}else{
				sIndex=0;
			}
		}
		fruit.status = OBJECT_HIT;
		printInfo();
	}
	
	/*��������Ҫ���ĵ�����*/
	private void hitBuble(Role buble){
		own.scores += buble.scores;
		own.scores2 += buble.scores;
		own.hitRatio++;
		scores = own.scores;
		scores2 = own.scores2;
		hitRatio = own.hitRatio;
		if(buble.status == ROLE_ALIVE){
			if(buble.id == multicolour){
				buble.scores = 1000;
			}
			Exploder s = new Exploder(buble.mapx,buble.mapy,1);
			s.score = buble.scores;
			ss[sIndex] = s;
			if(sIndex < ss.length-1){
				sIndex ++;
			}else{
				sIndex=0;
			}
		}
		buble.status = ROLE_DEATH;
		printInfo();
	}
	
	/*�����ӵ�Ҫ�ı������ */
	private void hitBoom(Weapon boom) {
		own.scores += boom.scores;
		own.scores2 += boom.scores;
		scores = own.scores;
		scores2 = own.scores2;
		own.hitBooms ++;
		hitBooms = own.hitBooms;
		if(boom.status == OBJECT_NOT_HIT){
			Exploder s = new Exploder(boom.mapx,boom.mapy,1);
			s.score = boom.scores;
			ss[sIndex] = s;
			if(sIndex < ss.length-1){
				sIndex ++;
			}else{
				sIndex=0;
			}
		}
		boom.status = OBJECT_HIT;
		printInfo();
	}

	public void hitOwn(Role role){
		down = false;
		nanIndex=0;
		own.status = ROLE_DEATH;
		own.lifeNum --;
		lifeNum = own.lifeNum;
		HASWOLF_ONE = false;
		HASWOLF_TWO = false;
		HASWOLF_THREE = false;
		HASWOLF_FOUR = false;
		System.out.println("��������һ");
	}
	
	/*��������Ҫ�ñ������*/
	public void hitWolf(Role wolf, Weapon w){
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
		if(w==null){
			if(wolf.role != null){
				if(wolf.role.id == multicolour){
					wolf.role.scores = 1000;
				}
				own.scores += wolf.role.scores;
				own.scores2 += wolf.role.scores;
				scores = own.scores;
				scores2 = own.scores2;
			}
			Exploder s = new Exploder(wolf.mapx,wolf.mapy,1);
			s.score = wolf.role.scores;
			ss[sIndex] = s;
			if(sIndex < ss.length-1){
				sIndex ++;
			}else{
				sIndex=0;
			}
		}else{
			if(wolf.role != null){
				if(w.hitNum<=1){
					if(wolf.role.id == multicolour){
						wolf.role.scores = 1000;
					}else{
						wolf.role.scores = 400;
					}
				}else if(w.hitNum==2){
					wolf.role.scores = 800;
				}else{
					wolf.role.scores = 2000;
				}
				own.scores += wolf.role.scores;
				own.scores2 += wolf.role.scores;
				scores = own.scores;
				scores2 = own.scores2;
			}
			Exploder s = new Exploder(wolf.mapx,wolf.mapy,1);
			s.score = wolf.role.scores;
			ss[sIndex] = s;
			if(sIndex < ss.length-1){
				sIndex ++;
			}else{
				sIndex=0;
			}
		}
		
		printInfo();
	}
	
	public void printInfo(){
		System.out.println("scores:"+scores);
		System.out.println("scores2:"+scores2);
		System.out.println("hitTotalNum:"+hitTotalNum);
		System.out.println("hitBooms:"+hitBooms);
		System.out.println("useProps:"+useProps);
		System.out.println("hitFruits:"+hitFruits);
		System.out.println("level:"+level);
	}
	
	/*����Ҫ����ľ�*/
	private void initDataNextLevel(){
		pasueState=false;
		isUsePasue=false;
		isUseGlove=false;
		isShowGlove=false;
		gloveStartTime = 0;
		isAttack = false;
		protectState = false;
		harpState = false;
		magnetState = false;
		golveFlag=true;
		isFourRepeating = false;
		rewardLevelFail = false;
		scores2 = own.scores2 = 0;
		hitNum = own.hitNum = 0;
		if(batches.redWolf!=null){
			batches.redWolf.bombNum = 0;
		}
		
		reward_nums = 0;
		batch = 0;
		nanIndex=0;
		HASWOLF_ONE = false;
		HASWOLF_TWO = false;
		HASWOLF_THREE = false;
		HASWOLF_FOUR = false;
		weapon.clearObjects(); // ��ն���
		batches.clearObject(); // ��ն���
		for(int j=0;j<ss.length;j++){
			ss[j]=null;
		}
		for(int i=0;i<exploders.length;i++){
			exploders[i]=null;
		}
		for(int i=0;i<mf.length;i++){
			mf[i]=null;
		}
		for(int i=0;i<sprop.length;i++){
			sprop[i]=null;
		}
	}
	
	/*��Ϸ����Ҫ���������*/
	public void initDataGameOver(){
		hitNum = 0;
		lifeNum = 0;
		scores = 0;
		scores2 = 0;
		hitBuble = 0;
		hitFruits = 0;
		hitTotalNum = 0;
		hitRatio = 0;
		useProps = 0;
		for(int j=0;j<ss.length;j++){
			ss[j]=null;
		}
		for(int i=0;i<exploders.length;i++){
			exploders[i]=null;
		}
		for(int i=0;i<mf.length;i++){
			mf[i]=null;
		}
		for(int i=0;i<sprop.length;i++){
			sprop[i]=null;
		}
		pasueState=false;
		isUsePasue=false;
		isUseGlove=false;
		isShowGlove=false;
		gloveStartTime = 0;
		isAttack = false;
		protectState = false;
		harpState = false;
		magnetState = false;
		golveFlag=true;
		isFourRepeating = false;
		rewardLevelFail = false;
		isShowGlove=false;
		level = 1;
		rewardLevel = 1;
		batch = 0;
		nanIndex=0;
		isRewardLevel = false;
		isReward = false;
		isNextLevel = false;
		reward_nums = 0;
		HASWOLF_ONE = false;
		HASWOLF_TWO = false;
		HASWOLF_THREE = false;
		HASWOLF_FOUR = false;
		own = null;
		weapon.clearObjects(); // ��ն���
		batches.clearObject(); // ��ն���
	}
	
	private void clear() {
		weapon.clear();
		Resource.freeImage(Resource.id_playing_menu);
		Resource.freeImage(Resource.id_control);
		Resource.freeImage(Resource.id_pumpkin);
		Resource.freeImage(Resource.id_multiply);
		Resource.freeImage(Resource.id_cloud1);
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
		Resource.freeImage(Resource.id_wolf_climb);   
		Resource.freeImage(Resource.id_wolf_die);   
		Resource.freeImage(Resource.id_wolf_shove);   
		Resource.freeImage(Resource.id_balloon_blue);   
		Resource.freeImage(Resource.id_balloon_green);   
		Resource.freeImage(Resource.id_balloon_multicolour);   
		Resource.freeImage(Resource.id_balloon_yellow);   
		Resource.freeImage(Resource.id_balloon_yellowred);   
		Resource.freeImage(Resource.id_balloon_red);   
		Resource.freeImage(Resource.id_ladder);   
		Resource.freeImage(Resource.id_playing_level);   
		Resource.freeImage(Resource.id_playing_level2);   
		Resource.freeImage(Resource.id_playing_point);   
		Resource.freeImage(Resource.id_sheep_head);   
		Resource.freeImage(Resource.id_wolf_head);   
		Resource.freeImage(Resource.id_multiply);   
		Resource.freeImage(Resource.id_pass_cloud2);   
		Resource.freeImage(Resource.id_pass_cloud1);   
		Resource.freeImage(Resource.id_pass_cloud);   
		Resource.freeImage(Resource.id_watermelon);   
		Resource.freeImage(Resource.id_prop_2_eff);   
		Resource.freeImage(Resource.id_prop);   
		Resource.freeImage(Resource.id_game_stop);   
		Resource.freeImage(Resource.id_teach_level);   
		Resource.freeImage(Resource.id_arrowhead);   
	}
	
}
