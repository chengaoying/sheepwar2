package sheepwar;

import javax.microedition.lcdui.Graphics;

/*
 * ���������� 
 */
public interface Common {
	
	public final static int STATUS_INIT = 0;			//��ʼ
	public final static int STATUS_MAIN_MENU = 1;		//��Ϸ���˵� 
	public final static int STATUS_GAME_PLAYING = 2;	//��Ϸ��

	
	public final static short TopLeft = Graphics.LEFT|Graphics.TOP;
	
	public final static int ScrW = SheepWarGameEngine.ScrW;
	public final static int ScrH = SheepWarGameEngine.ScrH;
	
	public final static int gameMapX = 640;                 //��Ϸ����Ŀ��
	public final static int gameMapY = 530;                  //��Ϸ����ĸ߶�
	
	//public static int sheepMapY = 290;                     //364-74:�������ƶ������Ʒ�Χ
	
}
