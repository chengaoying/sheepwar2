package sheepwar;

import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

import cn.ohyeah.stb.game.GameCanvasEngine;
import cn.ohyeah.stb.key.KeyCode;

/**
 * ��Ϸ����
 * 
 * @author Administrator
 */
public class SheepWarGameEngine extends GameCanvasEngine implements Common {
	public Role own;
	public static boolean isMove = true;//

	public static boolean isSupportFavor = false;
	public static int ScrW = 0;
	public static int ScrH = 0;
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
			// processShop();
			break;
		case STATUS_GAME_ARCHI:// ��Ϸ�ɾ�
			break;
		case STATUS_GAME_RANKING:// ��Ϸ����
			// processRanking();
			break;
		case STATUS_GAME_HELP:// ��Ϸ����
			// processHelp();
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
			// TODO showGameShop
			break;
		case STATUS_GAME_ARCHI:// ��Ϸ�ɾ�
			// TODO showGameArchi
			break;
		case STATUS_GAME_RANKING:// ��Ϸ����
			break;
		case STATUS_GAME_HELP:// ��Ϸ����
			break;
		}
	}

	private void moveRole(int i) {
		switch (i) {
		case 0: // ����--����

			break;
		case 1: // ����--����

			break;
		}

	}

	private void showInit(Graphics g) {
		/*
		 * g.setColor(0X000000); g.setClip(0, 0, 100, 100);
		 * g.setColor(0Xffffff); g.drawString("������,���Ժ�...", 300, 260, 10);
		 */
	}

	private void init() {
		showGame = new ShowGame();
		status = STATUS_MAIN_MENU; // ������Ϸ�˵�
	}

	private void showGameMenu(Graphics g) {
		showGame.drawMainMenu(g, mainIndex);
	}

	private void showGamePlaying(Graphics g) {
		showGame.drawGamePlaying(g, playingIndex);
	}

	private void processGameMenu() {

		if (keyState.containsAndRemove(KeyCode.UP)) {
			mainIndex = (mainIndex + 6 - 1) % 6;
		} else if (keyState.containsAndRemove(KeyCode.DOWN)) {
			mainIndex = (mainIndex + 1) % 6;
		} else if (keyState.containsAndRemove(KeyCode.OK)) {
			processSubMenu();
			showGame.clearMainMenu();
		}
		if (keyState.containsAndRemove(KeyCode.BACK)) { // ���ؼ�ֱ���˳�
		}

	}

	private void processGamePlaying() {
		if (keyState.containsMoveEventAndRemove(KeyCode.UP)) {
			moveRole(0);//
		} else if (keyState.containsMoveEventAndRemove(KeyCode.DOWN)) {
			// moveRole(1);
		} else if (keyState.contains(KeyCode.OK) && own.status != 1) { // ��ͨ����
			keyState.remove(KeyCode.OK);
		} else if (keyState.contains(KeyCode.NUM5) && own.status != 1) { // ʱ������
			keyState.remove(KeyCode.NUM5);
		}
	}

	private void processSubMenu() {
		if (mainIndex == 0) { //����Ϸ
			status = STATUS_GAME_PLAYING;
		} else if (mainIndex == 1) {// ������Ϸ
		} else if (mainIndex == 2){ //��Ϸ����
		} else if (mainIndex == 3) {// ��Ϸ�̳�
		} else if (mainIndex == 4) {// ��Ϸ���
		} else if (mainIndex == 5) {// �˳�
			exit = true;
		}
		mainIndex=0;
	}

}
