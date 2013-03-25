package sheepwar;
import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.Configurations;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.game.ServiceWrapper;
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
	
	public int menuAxis[][] = { { 484, 97 }, { 484, 97+52 }, { 484, 97+104 },
					{ 484, 97+156 }, { 484, 97+208 }, { 484, 97+260 }, { 484, 97+312 },	};
	
	private int groupIndex, mainIndex;
	
	public void handleKey(KeyState keyState){
		if (!Configurations.getInstance().isFavorWayNonsupport()) {
			if (keyState.containsAndRemove(KeyCode.LEFT)) {
				if (groupIndex == 0) {
					groupIndex = 1;
				}
			}
			
			if (keyState.containsAndRemove(KeyCode.RIGHT)) {
				if (groupIndex == 1) {
					groupIndex = 0;
				}
			}
		}
		if(groupIndex == 0){
			if (keyState.containsAndRemove(KeyCode.UP)) {
				mainIndex = (mainIndex + 7 - 1) % 7;
			} else if (keyState.containsAndRemove(KeyCode.DOWN)) {
				mainIndex = (mainIndex + 1) % 7;
			}
		}
		
		if (keyState.containsAndRemove(KeyCode.OK)) {
			if(groupIndex == 0){
				processSubMenu();
				clear();
			}else{
				addfavorite();
			}
		}
	}
	
	private void addfavorite() {
		PopupText pt = UIResource.getInstance().buildDefaultPopupText();
		try {
			ServiceWrapper sw = engine.getServiceWrapper();
			sw.addFavor();
			if (sw.isServiceSuccessful()) {
				pt.setText("����ղؼгɹ�");
			}
			else {
				pt.setText("����ղؼ�ʧ��, ԭ��: "+sw.getMessage());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			pt.setText("����ղؼ�ʧ�ܣ����Ժ�����");
		}
		pt.popup();
	}

	public void show(SGraphics g) {
		Image main_bg = Resource.loadImage(Resource.id_main_bg);
		Image main_menu = Resource.loadImage(Resource.id_main_menu);
		Image main_menu1 = Resource.loadImage(Resource.id_main_menu1);		//��ɫ����
		Image main_select_base = Resource.loadImage(Resource.id_main_select_base);
		Image main_select = Resource.loadImage(Resource.id_main_select);
		g.drawImage(main_bg, 0, 0, 0);
		int sw = main_menu.getWidth(), sh = main_menu.getHeight() / 7;
		int shadowX = 2,shadowY = 2;
		for (int i = 0; i < menuAxis.length; ++i) {
			if(mainIndex != i){
				g.drawRegion(main_select, 0, 0, main_select.getWidth(), 
						main_select.getHeight(), 0, menuAxis[i][0] - 11, menuAxis[i][1] - 14, 20);
				g.drawRegion(main_menu, /*(mainIndex != i) ? sw : */0, i * sh, sw, sh,
						0, menuAxis[i][0], menuAxis[i][1]+15, 20);
			}else{	
				if(groupIndex == 0){
					g.drawRegion(main_select_base, 0, 0, main_select_base.getWidth(), 
							main_select_base.getHeight(), 0, menuAxis[i][0] - 11, menuAxis[i][1] - 14, 20);
					g.drawRegion(main_menu1, /*(mainIndex != i) ? sw : */0, i * sh, sw, sh,
							0, menuAxis[i][0]+shadowX, menuAxis[i][1]+12+shadowY, 20);
					g.drawRegion(main_menu, /*(mainIndex != i) ? sw : */0, i * sh, sw, sh,
							0, menuAxis[i][0], menuAxis[i][1]+15, 20);
				}else{
					g.drawRegion(main_select, 0, 0, main_select.getWidth(), 
							main_select.getHeight(), 0, menuAxis[i][0] - 11, menuAxis[i][1] - 14, 20);
					g.drawRegion(main_menu, /*(mainIndex != i) ? sw : */0, i * sh, sw, sh,
							0, menuAxis[i][0], menuAxis[i][1]+15, 20);
				}
			}
		}
		
		if (!Configurations.getInstance().isFavorWayNonsupport()) {
			Image favoriteImg = Resource.loadImage(Resource.id_favorite);
			int w = favoriteImg.getWidth()/2, h = favoriteImg.getHeight();
			if(groupIndex == 0){
				g.drawRegion(favoriteImg, 0, 0, w, h, 0, 0, 380, 20);
			}else{
				g.drawRegion(favoriteImg, w, 0, w, h, 0, 0, 380, 20);
			}
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
			stateGame.initDataGameOver();
			StateGame.own = stateGame.createRole.createSheep();
			engine.state = STATUS_GAME_PLAYING;
			clear();
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
			clear();
		} else if (mainIndex == 2) {// �����̳�
			clear();
			StateShop ss =  new StateShop(engine);
			ss.processShop();
		} else if (mainIndex == 3){ //�ɾ�ϵͳ
			clear();
			engine.updateAttainmen();
			StateAttainment sa = new StateAttainment();
			sa.processAttainment();
		} else if (mainIndex == 4) {// ���а�
			clear();
			StateRanking sr = new StateRanking();
			sr.processRanking();
		} else if (mainIndex == 5) {// ��Ϸ����
			clear();
			StateHelp sh = new StateHelp();
			sh.processHelp();
		}else if(mainIndex==6){//�˳���Ϸ
			clear();
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
		Resource.freeImage(Resource.id_main_menu1);
		Resource.freeImage(Resource.id_main_select);
		Resource.freeImage(Resource.id_main_select_base);
		Resource.freeImage(Resource.id_favorite);
	}
}
