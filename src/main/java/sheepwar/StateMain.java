package sheepwar;
import javax.microedition.lcdui.Image;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupText;

public class StateMain implements Common{
	
	public boolean exit;
	private StateGame stateGame;
	private SheepWarGameEngine engine;
	public StateMain(SheepWarGameEngine engine){
		this.engine = engine;
		this.stateGame = engine.stateGame;
	}
	
	public int menuAxis[][] = { { 527, 204 }, { 467, 247 }, { 527, 294 },
			{ 467, 337 }, { 527, 384 }, { 467, 427 }, { 527, 473 },};
	
	private int mainIndex;
	
	public void handleKey(KeyState keyState){
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			//exit = true;
			//clear();
		}
		if (keyState.containsAndRemove(KeyCode.UP)) {
			mainIndex = (mainIndex + 7 - 1) % 7;
		} else if (keyState.containsAndRemove(KeyCode.DOWN)) {
			mainIndex = (mainIndex + 1) % 7;
		} else if (keyState.containsAndRemove(KeyCode.OK)) {
			processSubMenu();
			clear();
		}
	}

	public void show(SGraphics g) {
		Image main_bg = Resource.loadImage(Resource.id_main_bg);
		Image main_menu = Resource.loadImage(Resource.id_main_menu);
		Image main_select_right_base = Resource.loadImage(Resource.id_main_select_right_base);
		Image main_select_left_base = Resource.loadImage(Resource.id_main_select_left_base);
		g.drawImage(main_bg, 0, 0, 0);
		int sw = main_menu.getWidth() / 2, sh = main_menu.getHeight() / 7;

		for (int i = 0; i < menuAxis.length; ++i) {
			if (i % 2 == 0 && mainIndex != i) {
				g.drawRegion(main_select_right_base, 0, 0,
						main_select_right_base.getWidth(),
						main_select_right_base.getHeight(), 0,
						menuAxis[i][0] - 11, menuAxis[i][1] - 14, 20);
			} else if (i % 2 == 1 && mainIndex != i){
				g.drawRegion(main_select_left_base, 0, 0,
						main_select_left_base.getWidth(),
						main_select_left_base.getHeight(), 0,
						menuAxis[i][0] - 19, menuAxis[i][1] - 14, 20);
			}
			g.drawRegion(main_menu, (mainIndex != i) ? sw : 0, i * sh, sw, sh,
					0, menuAxis[i][0], menuAxis[i][1], 0);
		}
	}
	
	public void execute(){
		
		/*mainIndexΪ0�ǿ�ʼ��Ϸ*/
		/*if(mainIndex == 0){
			stateGame.weapon = new Weapon(stateGame);
			stateGame.createRole = new CreateRole();
			stateGame.batches = new Batches();
			StateGame.own = stateGame.createRole.createSheep();
		}*/
	}
	
	/*ע��ͽ��水ť��˳��һ��*/
	private void processSubMenu() {
		if (mainIndex == 0) { //����Ϸ
			stateGame.weapon = new Weapon(stateGame);
			stateGame.createRole = new CreateRole();
			stateGame.batches = new Batches();
			StateGame.own = stateGame.createRole.createSheep();
			engine.state = STATUS_GAME_PLAYING;
		} else if(mainIndex == 1){
			engine.readRecord();
			if(SheepWarGameEngine.result){
				stateGame.weapon = new Weapon(stateGame);
				stateGame.createRole = new CreateRole();
				stateGame.batches = new Batches();
				StateGame.own = stateGame.createRole.reviveSheep(true);
				setPropValideTime(); 	//���õ���ʣ��ʱ��
				setWolfInLadder(); 		//�ָ�ԭ�������ϵ���
				engine.state = STATUS_GAME_PLAYING;
				SheepWarGameEngine.isFirstGame = false;
			}else{
				PopupText pt = UIResource.getInstance().buildDefaultPopupText();
				pt.setText("û����Ϸ��¼�������¿�ʼ��Ϸ!");
				pt.popup();
				mainIndex=0;
			}
		} else if (mainIndex == 2) {// �����̳�
			StateShop ss =  new StateShop(engine);
			ss.processShop();
		} else if (mainIndex == 3){ //�ɾ�ϵͳ
			engine.updateAttainmen();
			StateAttainment sa = new StateAttainment();
			sa.processAttainment();
		} else if (mainIndex == 4) {// ���а�
			StateRanking sr = new StateRanking();
			sr.processRanking();
		} else if (mainIndex == 5) {// ��Ϸ����
			StateHelp sh = new StateHelp();
			sh.processHelp();
		}else if(mainIndex==6){//�˳���Ϸ
			exit = true;
			
			//��������
			engine.saveAttainment();
		} 
	}
	
	private void setWolfInLadder() {
		if(StateGame.HASWOLF_ONE){
			if(StateGame.level%2==0){
				stateGame.batches.createWolf(210, 26, ROLE_MOVE_RIGHT, ON_ONE_LADDER,WOLF_POSITION_BOTTOM );
			}else{
				stateGame.batches.createWolf(420, 190+89*3, ROLE_MOVE_UP, ON_ONE_LADDER,WOLF_POSITION_TOP);
			}
		}
		if(StateGame.HASWOLF_TWO){
			if(StateGame.level%2==0){
				stateGame.batches.createWolf(165, 26, ROLE_MOVE_RIGHT, ON_TWO_LADDER,WOLF_POSITION_BOTTOM );
			}else{
				stateGame.batches.createWolf(420, 190+89*2, ROLE_MOVE_UP, ON_TWO_LADDER,WOLF_POSITION_TOP);
			}
		}
		if(StateGame.HASWOLF_THREE){
			if(StateGame.level%2==0){
				stateGame.batches.createWolf(120, 26, ROLE_MOVE_RIGHT, ON_THREE_LADDER,WOLF_POSITION_BOTTOM );
			}else{
				stateGame.batches.createWolf(420, 190+89, ROLE_MOVE_UP, ON_THREE_LADDER,WOLF_POSITION_TOP);
			}
		}
		if(StateGame.HASWOLF_FOUR){
			if(StateGame.level%2==0){
				stateGame.batches.createWolf(75, 26, ROLE_MOVE_RIGHT, ON_FOUR_LADDER,WOLF_POSITION_BOTTOM );
			}else{
				stateGame.batches.createWolf(420, 190, ROLE_MOVE_UP, ON_FOUR_LADDER,WOLF_POSITION_TOP);
			}
		}
	}

	private void setPropValideTime(){
		/*ʱ�����ã���ͣ��Ҫ�ָ����ߵ���Чʱ��*/
		if(StateGame.pasueState && StateGame.isUsePasue){		//ʱ������
			long t1 = StateGame.pasueValideTime;
			long t2 = System.currentTimeMillis()/1000;
			StateGame.pasueTimeS = t2-t1;
		}
		
		if(StateGame.protectState){					//���ǵ���
			long t3 = StateGame.protectValideTime;
			long t4 = System.currentTimeMillis()/1000;
			StateGame.proStartTime = t4-t3;
		}
		
		if(!StateGame.isUseGlove && StateGame.golveFlag){
			long t5 = StateGame.gloveValideTime;
			long t6 = System.currentTimeMillis()/1000;
			StateGame.gloveStartTime = t6-t5;
		}
	}

	private void clear() {
		Resource.freeImage(Resource.id_main_bg);
		Resource.freeImage(Resource.id_main_menu);
		//Resource.freeImage(Resource.id_main_select_right);
		//Resource.freeImage(Resource.id_main_select_left);
		Resource.freeImage(Resource.id_main_select_right_base);
		Resource.freeImage(Resource.id_main_select_left_base);
	}
}
