package sheepwar;

import javax.microedition.lcdui.Graphics;

/*
 * ���������� 
 */
public interface Common {
	
	public final static int STATUS_INIT = 0;			//��ʼ
	public final static int STATUS_MAIN_MENU = 1;		//��Ϸ���˵� 
	public final static int STATUS_GAME_PLAYING = 2;	//��Ϸ��
	public final static int STATUS_GAME_SHOP = 3;		//��Ϸ�̳�
	public final static int STATUS_GAME_ARCHI = 4;		//��Ϸ�ɾ�
	public final static int STATUS_GAME_RANKING = 5;	//��Ϸ����
	public final static int STATUS_GAME_HELP = 6;		//��Ϸ����
	public final static int STATUS_GAME_EXIT = 7;		//�˳���Ϸ
	
	public final static short TopLeft = Graphics.LEFT|Graphics.TOP;
	public final static int ScrW = SheepWarGameEngine.ScrW;
	public final static int ScrH = SheepWarGameEngine.ScrH;
}
