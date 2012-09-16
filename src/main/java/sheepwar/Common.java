package sheepwar;

/*
 * ���������� 
 */
public interface Common {
	
	public final static int STATUS_INIT = 0;			//��ʼ
	public final static int STATUS_MAIN_MENU = 1;		//��Ϸ���˵� 
	public final static int STATUS_GAME_PLAYING = 2;	//��Ϸ��

	public final static int ScrW = SheepWarGameEngine.ScrW;
	public final static int ScrH = SheepWarGameEngine.ScrH;
	public final static int gameW = 490;				//��Ϸ������
	
	public final static short ROLE_ALIVE = 0; 			//��ɫ����״̬ 
	public final static short ROLE_DEATH = -1;  		//��ɫ����״̬ 
	
	public final static short ROLE_ON_GROUND = 0;  		//���ڵ���
	public final static short ROLE_IN_AIR = 1;  		//���뿪����
	
	public final static short ON_ONE_LADDER = 1; 			//��ɫ�ڵ�һ��������
	public final static short ON_TWO_LADDER = 2;  			//��ɫ�ڵڶ��������� 
	public final static short ON_THREE_LADDER = 3;  		//��ɫ�ڵ�����������
	public final static short ON_FOUR_LADDER = 4;  			//��ɫ�ڵ��ĸ�������
	
	/*�ǵ��ƶ�����*/
	public final static int ROLE_MOVE_UP = 0;  		//��
	public final static int ROLE_MOVE_DOWN = 1;		//��
	public final static int ROLE_MOVE_LEFT = 2;		//��
	public final static int ROLE_MOVE_RIGHT = 3;	//��
	
	/*�ǵĳ�ʼY��λ��*/
	public final static short WOLF_POSITION_TOP = 0;	//������
	public final static short WOLF_POSITION_BOTTOM = 1;	//������
	
	/*���ڿ��зֲ���ʽ*/
	public final static short NONE = 0;					//��
	public final static short SPREED_BELOW = 1;			//б��ֱ��
	public final static short SPREED_ABOVE = 2;			//б��ֱ��
	public final static short SPREED_VERTICAL = 3;		//��ֱ
	public final static short SPREED_IRREGULAR = 4;		//����
	
	/*����ID(��Ӧ��ɫ)*/
	public final static short blue = Resource.id_balloon_blue;					//������
	public final static short green = Resource.id_balloon_green;				//������
	public final static short multicolour = Resource.id_balloon_multicolour;	//��ɫ����
	public final static short red = Resource.id_balloon_red;					//������
	public final static short yellow = Resource.id_balloon_yellow;				//������
	public final static short orange = Resource.id_balloon_yellowred;			//��ɫ����
	
	/*���߷�ʽ*/
	public final static short[][] regular = {
		{4,2,3,1},
		{1,2,4,3},
		{1,3,4,3},
		{4,3,2,3},
		{3,1,2,4},
	};
	
	/*ÿ���ǳ��ֵķ�ʽ*/
	public static int[][][] BatchesInfo = {
		
		/*��һ��*/
		{   
			/*0-������ 1-����ID(��Ӧ��ɫ) 2-���зֲ���ʽ */
			{4, orange, SPREED_ABOVE },
			{2, blue, SPREED_VERTICAL },
			{2, orange, SPREED_BELOW },
			{2, blue, SPREED_ABOVE },
			{3, orange, SPREED_BELOW },
			{1, blue, NONE },
			{4, blue, SPREED_ABOVE },
			{2, red, SPREED_VERTICAL },
			{2, orange, SPREED_ABOVE },
		},
		
		/*�ڶ���*/
		{   
			/*0-������ 1-����ID(��Ӧ��ɫ), 2-���зֲ���ʽ*/
			{4, orange, SPREED_IRREGULAR },
			{4, orange, SPREED_IRREGULAR },
			{4, orange, SPREED_IRREGULAR },
		},
		
		/*������*/
		{   
			/*0-������ 1-����ID(��Ӧ��ɫ), 2-���зֲ���ʽ*/
			{},
			{},
		}
	};
	
}
