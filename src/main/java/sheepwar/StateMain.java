package sheepwar;


import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

public class StateMain implements Common{
	
	public boolean exit;
	private StateGame stateGame;
	private SheepWarGameEngine engine;
	public StateMain(SheepWarGameEngine engine, StateGame stateGame){
		this.engine = engine;
		this.stateGame = stateGame;
	}
	
	public int menuAxis[][] = { { 523, 243 }, { 466, 288 }, { 523, 333 },
			{ 466, 378 }, { 523, 423 }, { 466, 468 }, };
	
	private int mainIndex;
	
	public void handleKey(KeyState keyState){
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			//exit = true;
			//clear();
		}
		if (keyState.containsAndRemove(KeyCode.UP)) {
			mainIndex = (mainIndex + 6 - 1) % 6;
		} else if (keyState.containsAndRemove(KeyCode.DOWN)) {
			mainIndex = (mainIndex + 1) % 6;
		} else if (keyState.containsAndRemove(KeyCode.OK)) {
			processSubMenu();
			clear();
		}
	}

	public void show(SGraphics g) {
		Image main_bg = Resource.loadImage(Resource.id_main_bg);
		Image main_menu = Resource.loadImage(Resource.id_main_menu);
		g.drawImage(main_bg, 0, 0, 0);
		int sw = main_menu.getWidth() / 2, sh = main_menu.getHeight() / 6;
		for (int i = 0; i < menuAxis.length; ++i) {
			g.drawRegion(main_menu,(mainIndex != i) ? sw : 0, i * sh, sw,sh, 0, menuAxis[i][0], menuAxis[i][1], 0);
		}
	}
	
	public void execute(){
		
		/*mainIndexΪ0�ǿ�ʼ��Ϸ*/
		if(mainIndex == 0){
			stateGame.weapon = new Weapon();
			stateGame.createRole = new CreateRole();
			stateGame.batches = new Batches();
			StateGame.own = stateGame.createRole.createSheep();
		}
	}
	
	/*ע��ͽ��水ť��˳��һ��*/
	private void processSubMenu() {
		if (mainIndex == 0) { //����Ϸ
			engine.state = STATUS_GAME_PLAYING;
			
		} else if (mainIndex == 1) {// �����̳�
			StateShop ss =  new StateShop();
			ss.processShop();
			
		} else if (mainIndex == 2){ //�ɾ�ϵͳ
			StateAttainment sa = new StateAttainment();
			sa.processAttainment();
			
		} else if (mainIndex == 3) {// ���а�
			StateRanking sr = new StateRanking();
			sr.processRanking();
			
		} else if (mainIndex == 4) {// ��Ϸ����
			StateHelp sh = new StateHelp();
			sh.processHelp();
			
		}else if(mainIndex==5){//�˳���Ϸ
			exit = true;
		} 
	}

	private void clear() {
		Resource.freeImage(Resource.id_main_bg);
		Resource.freeImage(Resource.id_main_menu);
	}
}
