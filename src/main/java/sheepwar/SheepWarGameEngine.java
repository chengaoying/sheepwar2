package sheepwar;

import javax.microedition.midlet.MIDlet;

import cn.ohyeah.stb.game.GameCanvasEngine;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;

/**
 * ��Ϸ����
 * 
 * @author Administrator
 */
public class SheepWarGameEngine extends GameCanvasEngine implements Common {
	public Role own;      //��Ҳٿص���
	public Role wolf;     //npc
	public Role buble;     //����

	public static boolean isSupportFavor = false;
	public static int ScrW = 0;
	public static int ScrH = 0;
	public CreateRole createRole;
	public Weapon weapon;
	public Attacks attacks;
	
	public static int bombAmount;		//�����ӵ�����
	public static SheepWarGameEngine instance = buildGameEngine();

	private static SheepWarGameEngine buildGameEngine() {
		return new SheepWarGameEngine(SheepWarMIDlet.getInstance());
	}

	private SheepWarGameEngine(MIDlet midlet) {
		super(midlet);
		setRelease(true);
		ScrW = screenWidth;
		ScrH = screenHeight;
	}

	public ShowGame showGame;
	public int status;
	public int mainIndex, playingIndex;
	
	protected void loop() {

		switch (status) {
		case STATUS_INIT: // ��ʼ��
			init();
			break;
		case STATUS_MAIN_MENU: // ���˵�
			processGameMenu();
			break;
		case STATUS_GAME_PLAYING: // ��Ϸ��
			processGamePlaying();// ��Ϸ�еĲ���
			break;
		case STATUS_GAME_SHOP: // �����̳�
			 //processShop();
			break;
		case STATUS_GAME_ARCHI:// ��Ϸ�ɾ�
			//processArchi();
			break;
		case STATUS_GAME_RANKING:// ��Ϸ����
			 //processRanking();
			break;
		case STATUS_GAME_HELP:// ��Ϸ����
			 //processHelp();
			break;
		}

		switch (status) {
		case STATUS_INIT:
			showInit(g);// ������ʼ���Ķ���
			break;
		case STATUS_MAIN_MENU:
			showGameMenu(g);
			break;
		case STATUS_GAME_PLAYING:
			showGamePlaying(g);
			break;
		case STATUS_GAME_SHOP: // �����̳�
			 //showGameShop(g);
			break;
		case STATUS_GAME_ARCHI:// ��Ϸ�ɾ�
			 //showGameArchi(g);
			break;
		case STATUS_GAME_RANKING:// ��Ϸ����
			//showRanking(g);
			break;
		case STATUS_GAME_HELP:// ��Ϸ����
			//showHelp(g);
			break;
		}
	}

	private void moveRole(int towards) {
		switch (towards) {
		case 0: // ����--����
			if(own.mapy>=164){//�����ϼ�������164
				own.mapy -= own.speed;
			}
			break;
		case 1: // ����--����
			own.direction = 1;
			if(own.mapy + own.height<460){//�����¼�������ԭ460--���⣺����Y���꼫�޻����area out of Iamge ?
				own.mapy += own.speed;
			}
			break;
		}
	}

	private void showInit(SGraphics g) {
		/*
		 * g.setColor(0X000000); g.setClip(0, 0, 100, 100);
		 * g.setColor(0Xffffff); g.drawString("������,���Ժ�...", 300, 260, 10);
		 */
	}

	private void init() {
		status = STATUS_MAIN_MENU;                            // ������Ϸ�˵�
		showGame = new ShowGame(this);
		weapon = new Weapon();
		attacks = new Attacks(this);
		createRole = new CreateRole();
		own = createRole.createSheep();
		createRole.createWolf();
	}

	private void showGameMenu(SGraphics g) {
		showGame.drawMainMenu(g, mainIndex);
	}

	private void showGamePlaying(SGraphics g) {
		showGame.drawGamePlaying(g, playingIndex,own);
		createRole.showSheep(g,own);//��̬����
		int len = CreateRole.npcs.size();
		for(int i=0;i<len;i++){
			wolf = (Role) CreateRole.npcs.elementAt(i);
			buble=(Role)CreateRole.bubles.elementAt(i);
			createRole.showWolf(g, wolf,buble);
		}
		weapon.showBomb(g);
	}
	
	private void processGameMenu() {
		if (keyState.contains(KeyCode.UP)) {
			keyState.remove(KeyCode.UP);
			mainIndex = (mainIndex + 6 - 1) % 6;
		} else if (keyState.contains(KeyCode.DOWN)) {
			keyState.remove(KeyCode.DOWN);
			mainIndex = (mainIndex + 1) % 6;
		} else if (keyState.contains(KeyCode.OK)) {
			keyState.remove(KeyCode.OK);
			showGame.clearMainMenu();
			processSubMenu();
		}
		if (keyState.contains(KeyCode.BACK)) { // ���ؼ�ֱ���˳�
			keyState.remove(KeyCode.BACK);
			showGame.clearMainMenu();
			exit = true;
		}
	}

	private void processGamePlaying() {
		
		if (keyState.containsMoveEventAndRemove(KeyCode.UP)) {
			moveRole(0);//
		} else if (keyState.containsMoveEventAndRemove(KeyCode.DOWN)) {
			moveRole(1);
		} else if (keyState.contains(KeyCode.OK)) { // ��ͨ����
			keyState.remove(KeyCode.OK);
			weapon.createBomb(own, 2);
//			weapon.bombAmount++;  //�����������Ŀ����
			
		}else if(keyState.contains(KeyCode.NUM1)){    //ʱ������
			keyState.remove(KeyCode.NUM1);
		}else if(keyState.contains(KeyCode.NUM2)){ //������
			keyState.remove(KeyCode.NUM2);
		}else if(keyState.contains(KeyCode.NUM3)){//����
			keyState.remove(KeyCode.NUM3);
		}else if(keyState.contains(KeyCode.NUM4)){//����ǹ
			keyState.remove(KeyCode.NUM4);
		}else if(keyState.contains(KeyCode.NUM5)){//��ɢ����
			keyState.remove(KeyCode.NUM5);
		}else if(keyState.contains(KeyCode.NUM6)){//
			keyState.remove(KeyCode.NUM6);
		}else if(keyState.contains(KeyCode.NUM7)){
			keyState.remove(KeyCode.NUM7);
		}else if(keyState.contains(KeyCode.NUM8)){//ľż->��������һ������
			keyState.remove(KeyCode.NUM8);
		}else if(keyState.contains(KeyCode.NUM9)){
			keyState.remove(KeyCode.NUM9);
			showGame.clearGamePlaying();
			status = STATUS_GAME_HELP;
		}
		else if (keyState.containsAndRemove(KeyCode.NUM0)) { // ��0����-----���ƺ�Ӧ��Ϊ0����ͣ��but �����еĲ�������ȴ���˳���Ϸ
			showGame.clearGamePlaying();
			status = STATUS_MAIN_MENU;
		}
		if(timePass(10000)){
			createRole.createWolf();
		}
		attacks.bompAttack(wolf, own);
		
	}
	
	private long recordTime;
	private boolean timePass(int millisSeconds) {
		long curTime = System.currentTimeMillis();
		if (recordTime <= 0) {
			recordTime = curTime;
		}
		else {
			if (curTime-recordTime >= millisSeconds) {
				recordTime = 0;
				return true;
			}
		}
		return false;
	}
	
	
	
	
	/*ע��ͽ��水ť��˳��һ��*/
	private void processSubMenu() {
		if (mainIndex == 0) { //����Ϸ
			status = STATUS_GAME_PLAYING;
			
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
		showGame.clearMainMenu();
	}
}
