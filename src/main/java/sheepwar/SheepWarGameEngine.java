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
	public Role wolf;

	public static boolean isSupportFavor = false;
	public static int ScrW = 0;
	public static int ScrH = 0;
	private CreateRole createRole;
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
	public int mainIndex, playingIndex,shopIndex,archiIndex,rankingIndex,helpIndex;
	
	private int mainOrgame=-1;                                 //�����̳ǽ��棺0���������棬1������Ϸ�еĽ���
	private int shopX=0,shopY=0;

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
			 processShop();
			break;
		case STATUS_GAME_ARCHI:// ��Ϸ�ɾ�
			processArchi();
			break;
		case STATUS_GAME_RANKING:// ��Ϸ����
			 processRanking();
			break;
		case STATUS_GAME_HELP:// ��Ϸ����
			 processHelp();
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
			 showGameShop(g);
			break;
		case STATUS_GAME_ARCHI:// ��Ϸ�ɾ�
			 showGameArchi(g);
			break;
		case STATUS_GAME_RANKING:// ��Ϸ����
			showRanking(g);
			break;
		case STATUS_GAME_HELP:// ��Ϸ����
			showHelp(g);
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

	private void showInit(Graphics g) {
		/*
		 * g.setColor(0X000000); g.setClip(0, 0, 100, 100);
		 * g.setColor(0Xffffff); g.drawString("������,���Ժ�...", 300, 260, 10);
		 */
	}

	private void init() {
		status = STATUS_MAIN_MENU;                            // ������Ϸ�˵�
		showGame = new ShowGame();
		createRole = new CreateRole();
		own = createRole.createSheep();
		wolf = createRole.createWolf();
	}

	private void showGameMenu(Graphics g) {
		showGame.drawMainMenu(g, mainIndex);
	}

	private void showGamePlaying(Graphics g) {
		showGame.drawGamePlaying(g, playingIndex, own);
	}
	
	/*�����̵�*/
	private void showGameShop(Graphics g) {
		showGame.drawGameShop(g,shopX,shopY);
	}
	
	/*�����ɾ�ϵͳ*/
	private void showGameArchi(Graphics g) {
		showGame.drawGameArchi(g,archiIndex);
	}
	
	/*�������а�*/
	private void showRanking(Graphics g) {
		showGame.showRanking(g, rankingIndex);
	}
	
	/*��������*/
	private void showHelp(Graphics g) {
        showGame.showHelp(g,helpIndex);
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
		} else if (keyState.containsAndRemove(KeyCode.OK)) { // ��ͨ����
			
		}else if(keyState.contains(KeyCode.NUM1)){    //ʱ������
			keyState.remove(KeyCode.NUM1);
		}else if(keyState.contains(KeyCode.NUM2)){ //������
			keyState.remove(KeyCode.NUM2);
		}else if(keyState.contains(KeyCode.NUM3)){//����
			keyState.remove(KeyCode.NUM3);
		}else if(keyState.contains(KeyCode.NUM4)){//����ǹ
			keyState.remove(KeyCode.NUM4);
		}else if(keyState.contains(KeyCode.NUM5)){//��ɢ���٣�ƽ�׹�����֮��
			keyState.remove(KeyCode.NUM5);
		}else if(keyState.contains(KeyCode.NUM6)){//
			keyState.remove(KeyCode.NUM6);
		}else if(keyState.contains(KeyCode.NUM7)){
			keyState.remove(KeyCode.NUM7);
		}else if(keyState.contains(KeyCode.NUM8)){//ľż->��������һ������
			keyState.remove(KeyCode.NUM8);
		}
		else if (keyState.containsAndRemove(KeyCode.NUM0)) { // ��0����-----���ƺ�Ӧ��Ϊ0����ͣ��
			status=STATUS_MAIN_MENU;
			showGame.clearGamePlaying();
		}
	}
	
	/*�̳ǲ���*/ //TODO���ƴ����ҵ��жϣ�Ϊ�˽�����Ҳ����¹���ʱһ�ε����Ч
	private void processShop() {
		if (keyState.contains(KeyCode.NUM0) || keyState.contains(KeyCode.BACK)) {
			keyState.remove(KeyCode.NUM0);
			keyState.remove(KeyCode.BACK);
//			if(mainOrgame==0){          //����������------��-1�����̳ǽ��棩
//				status=STATUS_MAIN_MENU;
//			}else if(mainOrgame==1){     //������Ϸ�еĽ���
//				status=STATUS_GAME_PLAYING;
//			}
			status=STATUS_MAIN_MENU;          //TODO �жϷ�����Ϸ�л���������
			shopX = 0;shopY = 0;
			showGame.clearShop();
		}
		if (keyState.contains(KeyCode.UP)) {
			keyState.remove(KeyCode.UP);
			if( shopY >0){
				shopY=shopY-1;
			}else {
				shopY = 0;
			}
		}
		if (keyState.contains(KeyCode.DOWN)) {
			keyState.remove(KeyCode.DOWN);
			if(shopY<4){
				shopY = shopY+1;
			}else{
				shopY=0;
			}
		}
		if (keyState.contains(KeyCode.LEFT)) {
			keyState.remove(KeyCode.LEFT);
			if(shopX>0){
				shopX = shopX-1;
			}
		}
		if (keyState.contains(KeyCode.RIGHT)) {
			keyState.remove(KeyCode.RIGHT);
			if(shopX<3){
				shopX = shopX+1;
			}
			if(shopX>2){
				shopX=0;
			}
			if(shopX==2){//������������ʱ��shopY����
				shopY=0;
			}
		}
		if (keyState.contains(KeyCode.OK)) {
			keyState.remove(KeyCode.OK);
			showGame.clearMainMenu();
			if(shopX==2 && shopY==0){//�����ֵ
				showGame.clearShop();
			}
			if(shopX==2 && shopY==1){
//				if(mainOrgame==0){//����������
//					showGame.clearShop();
//					status=STATUS_MAIN_MENU;
//				}else if(mainOrgame==1){//������Ϸ�еĽ���
//					showGame.clearShop();
//					status=STATUS_GAME_PLAYING;
//				}
				showGame.clearShop();
				status=STATUS_MAIN_MENU;
			}
		}
	}
	
	/*�ɾͲ���*/
	private void processArchi() {
         if(keyState.contains(KeyCode.NUM0)){  //��0������������
        	 keyState.remove(KeyCode.NUM0);
        	 showGame.clearGameArchi();
        	 status=STATUS_MAIN_MENU;
         }
	}
	
	/*���а����*/
	private void processRanking() {
		if(keyState.contains(KeyCode.NUM0)){       //��0������������
			showGame.clearRanking();
			status=STATUS_MAIN_MENU;
		}
	}
	
	/*��������*/
	private void processHelp() {
     if(keyState.contains(KeyCode.NUM0)){          //��0������������
    	 keyState.remove(KeyCode.NUM0);
    	 showGame.clearHelp();
    	 status=STATUS_MAIN_MENU;
     }
	}
	
	/*ע��ͽ��水ť��˳��һ��*/
	private void processSubMenu() {
		if (mainIndex == 0) { //����Ϸ
			status = STATUS_GAME_PLAYING;
			
		} else if (mainIndex == 1) {// �����̳�
			showGame.clearGamePlaying();
			status=STATUS_GAME_SHOP;
			
		} else if (mainIndex == 2){ //�ɾ�ϵͳ
			status=STATUS_GAME_ARCHI;
			
		} else if (mainIndex == 3) {// ���а�
			status=STATUS_GAME_RANKING;
			
		} else if (mainIndex == 4) {// ��Ϸ����
			status=STATUS_GAME_HELP;
			
		}else if(mainIndex==5){//�˳���Ϸ
			exit = true;
		} 
		showGame.clearMainMenu();
	}
}
