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
	
	public final static short ROLE_ALIVE = 0; 			//��ɫ����״̬ 
	public final static short ROLE_DEATH = -1;  		//��ɫ����״̬ 
	
	public final static short ON_ONE_LADDER = 1; 			//��ɫ�ڵ�һ��������
	public final static short ON_TWO_LADDER = 2;  			//��ɫ�ڵڶ��������� 
	public final static short ON_THREE_LADDER = 3;  		//��ɫ�ڵ�����������
	public final static short ON_FOUR_LADDER = 4;  			//��ɫ�ڵ��ĸ�������
	
	/*���ڿ��зֲ���ʽ*/
	public final static short SPREED_BELOW = 1;			//б��ֱ��
	public final static short SPREED_ABOVE = 2;			//б��ֱ��
	public final static short SPREED_VERTICAL = 3;		//��ֱ
	
}
