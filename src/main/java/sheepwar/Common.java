package sheepwar;

/*
 * ���������� 
 */
public interface Common {
	
	public final static int STATUS_INIT = 0;			//��ʼ
	public final static int STATUS_MAIN_MENU = 1;		//��Ϸ���˵� 
	public final static int STATUS_GAME_PLAYING = 2;	//��Ϸ��
	public final static int STATUS_GAME_RECHARGE = 3;	//��Ϸ��

	public final static int ScrW = SheepWarGameEngine.ScrW;
	public final static int ScrH = SheepWarGameEngine.ScrH;
	public final static int gameW = 490;				//��Ϸ������
	
	public final static short ROLE_ALIVE = 0; 			//��ɫ����״̬ 
	public final static short ROLE_DEATH = -1;  		//��ɫ����״̬ 
	public final static short ROLE_SUCCESS = 1;  		//�����ѵ�״̬ 
	public final static short ROLE_ATTACK = 2;  		//�ǹ�����״̬ 
	public final static short ROLE_DIZZY = 3;  			//��ѣ�ε�״̬ 
	
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
	public final static short WOLF_POSITION_TOP = 0;		//�ǳ��ֵĳ�ʼλ�ã����棩
	public final static short WOLF_POSITION_BOTTOM = -1;	//�ǳ��ֵĳ�ʼλ�ã����棩
	
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
	
	public final static short[] bubleColor = {blue, green, multicolour, red, yellow, orange};
	
	/*������Ӧid*/
	public final static short clockWeapon = 1001;							//ʱ������
	public final static short netWeapon = 1002;							
	public final static short playerProtected = 1003;							
	public final static short glareWeapon = 1004;							
	public final static short harpWeapon = 1005;							
	public final static short speedLiquid = 1006;							
	public final static short magnetWeapon= 1007;							
	public final static short puppet= 1008;							
	
	/*ˮ��id*/
	public final static short apple = Resource.id_apple;						//ƻ��
	public final static short lemon = Resource.id_lemon;						//����
	public final static short pear = Resource.id_orange;						//Ѽ��
	public final static short watermelon = Resource.id_watermelon;				//����
	
	/*ˮ��״̬*/
	public static short OBJECT_NOT_HIT = 0;			//���󱻻���
	public static short OBJECT_HIT = 1;				//����δ������
	
	/*�漴ѡ��ˮ��*/
	public final static short selectFruit[] = {apple,lemon,pear,watermelon};
	
	/*���߷�ʽ*/
	public final static short[][] regular = {
		{4,2,3,1},
		{1,2,4,3},
		{1,3,4,3},
		{4,3,2,3},
		{3,1,2,4},
	};
	
	/*�����ؿ���15�أ���ÿ���ǳ��ֵķ�ʽ*/
	public static int[][][] BatchesInfo = {
		{/*��һ��*/   
			/*0-������ 1-����ID(��Ӧ��ɫ) 2-���зֲ���ʽ */
			{4, orange, SPREED_ABOVE },
			{2, blue, SPREED_VERTICAL },
			{2, orange, SPREED_BELOW },
			{2, blue, SPREED_ABOVE },
			{3, orange, SPREED_BELOW },
			{1, blue, SPREED_IRREGULAR },
			{4, blue, SPREED_ABOVE },
			{2, red, SPREED_VERTICAL },
			{2, orange, SPREED_ABOVE },
		},
		{/*�ڶ���*/   
			/*0-������ 1-����ID(��Ӧ��ɫ), 2-���зֲ���ʽ*/
			{4, orange, SPREED_IRREGULAR },
			{4, orange, SPREED_IRREGULAR },
			{4, orange, SPREED_IRREGULAR },
		},
		{/*������*/   
			/*0-������ 1-����ID(��Ӧ��ɫ), 2-���зֲ���ʽ*/
			{4, orange, SPREED_IRREGULAR },
			{4, orange, SPREED_IRREGULAR },
			{4, orange, SPREED_IRREGULAR },
		},
		{/*���Ĺ�*/
			{1,green,NONE},
			{2,green,SPREED_VERTICAL},
			{2,green,SPREED_BELOW},
			{2,green,SPREED_ABOVE},
			{4,red,SPREED_ABOVE},
			{4,red,SPREED_ABOVE},
			{4,red,SPREED_ABOVE},
			{4,green,SPREED_ABOVE},
		},
		{/*�����*/
			{4,green,SPREED_BELOW},
			{4,green,SPREED_VERTICAL},
		},
		{/*������*/
			{1,multicolour,NONE},
			{2,green,SPREED_BELOW},				
			{2,green,SPREED_VERTICAL},			
			{2,red,SPREED_BELOW},
			{3,green,SPREED_ABOVE},				
			{3,red,SPREED_BELOW},
			{4,red,SPREED_ABOVE},
			{4,red,SPREED_VERTICAL},
			{4,green,SPREED_VERTICAL},
			{4,green,SPREED_ABOVE},
		},
		{/*���߹�*/
			{1, green, NONE },
			{1, multicolour, NONE},
			{2, green, SPREED_VERTICAL },
			{2, green, SPREED_ABOVE },
			{4, green, SPREED_ABOVE },
			{4, green, SPREED_BELOW },
			{4, green, SPREED_IRREGULAR },			
		},
		{/*�ڰ˹�*/
			{1, multicolour, NONE },
			{1, yellow, NONE},
			{2, green, SPREED_VERTICAL },
			{2, green, SPREED_ABOVE },
			{4, green, SPREED_VERTICAL },
			{4, green, SPREED_BELOW },	
			{4, green, SPREED_IRREGULAR },			
			{4, green, SPREED_IRREGULAR },			
		},
		{/*�ھŹ�*/
			{1, multicolour, NONE },
			{1, yellow, NONE},
			{4, green, SPREED_ABOVE },
			{4, green, SPREED_ABOVE },			
			{4, green, SPREED_VERTICAL },
			{4, green, SPREED_BELOW },		
			{4, green, SPREED_IRREGULAR },				
			{4, green, SPREED_IRREGULAR },				
		},
		{/*��ʮ��*/
			{1, yellow, NONE },
			{1, multicolour, NONE},
			{4, green, SPREED_VERTICAL },
			{4, green, SPREED_ABOVE },		
			{4, green, SPREED_VERTICAL },	
			{4, green, SPREED_IRREGULAR },		
			{4, green, SPREED_VERTICAL },
			{4, green, SPREED_IRREGULAR },		
			{4, green, SPREED_ABOVE },			
		},
		{/*��ʮһ��*/
			{4, yellow, SPREED_ABOVE},
			{4, yellow, SPREED_VERTICAL },	
			{3, yellow, SPREED_BELOW},	
			{2, yellow, SPREED_VERTICAL },	
			{2, yellow, SPREED_ABOVE},	
			{4, yellow, SPREED_VERTICAL },
			{1, multicolour, NONE },
		},
		{/*��ʮ����*/
			{4, yellow, SPREED_ABOVE},
			{4, yellow, SPREED_VERTICAL },	
			{4, yellow, SPREED_BELOW},	
			{2, yellow, SPREED_ABOVE},	
			{4, yellow, SPREED_VERTICAL },
			{1, multicolour, NONE },
		},
		{/*��ʮ����*/
			{4, yellow, SPREED_ABOVE},
			{4, yellow, SPREED_VERTICAL },
			{2, yellow, SPREED_BELOW},	
			{2, yellow, SPREED_ABOVE},
			{5, yellow, SPREED_VERTICAL },
			{1, multicolour, NONE },
		},
		{/*��ʮ�Ĺ�*/
			{4, yellow, SPREED_ABOVE},
			{4, yellow, SPREED_VERTICAL },
			{4, yellow, SPREED_BELOW },
			{2, yellow, SPREED_BELOW},	
			{2, yellow, SPREED_ABOVE},
			{4, yellow, SPREED_VERTICAL },
			{1, multicolour, NONE },
		},
		{/*��ʮ���*/
			{4, yellow, SPREED_ABOVE},
			{4, yellow, SPREED_VERTICAL },
			{4, yellow, SPREED_BELOW },
			{2, yellow, SPREED_BELOW},	
			{2, yellow, SPREED_ABOVE},
			{4, yellow, SPREED_VERTICAL },
			{1, multicolour, NONE },
			{1, yellow, NONE },
		},
	};
	
	/*�����ؿ���role���ֵķ�ʽ*/
	public static int [][][] RewardLevelBatchesInfo = {
		/*�����ؿ���һ��*/
		{/*0-������ 1-����ID(��Ӧ��ɫ) 2-���зֲ���ʽ */
			{2,orange,SPREED_ABOVE},
			{4,orange,SPREED_ABOVE},
			{4,orange,SPREED_ABOVE},
			{2,orange,SPREED_ABOVE},
			{2,orange,SPREED_ABOVE},
			{2,orange,SPREED_ABOVE},
		},
		/*�����ؿ�2*/
		{	/*0-������ 1-ˮ��ID�� 2-���зֲ���ʽ */
			{2,orange,SPREED_BELOW},
			{4,orange,SPREED_BELOW},
			{4,orange,SPREED_BELOW},
			{2,orange,SPREED_BELOW},
			{2,orange,SPREED_BELOW},
			{2,green,SPREED_BELOW},
		},
		{//�����ؿ�3
			{2,red,SPREED_BELOW},
			{4,green,SPREED_BELOW},
			{4,orange,SPREED_BELOW},
			{2,orange,SPREED_BELOW},
			{2,orange,SPREED_BELOW},
			{2,multicolour,SPREED_BELOW},
		},
		{//�����ؿ�4
			{2,red,SPREED_BELOW},
			{4,green,SPREED_BELOW},
			{4,orange,SPREED_BELOW},
		},
		{//�����ؿ�5
			{2,orange,SPREED_BELOW},
			{2,orange,SPREED_BELOW},
			{2,multicolour,SPREED_BELOW},
		},
		{//�����ؿ�6
			{2,red,SPREED_BELOW},
			{4,green,SPREED_BELOW},
			{4,orange,SPREED_BELOW},
		},
		{//�����ؿ�7
			{2,red,SPREED_BELOW},
			{4,green,SPREED_BELOW},
			{4,orange,SPREED_BELOW},
		},
	};
	
	/*�ɾ�����*/
	public static int Attainment_Type_Scores = 0;	//�÷ֳɾ�
	public static int Attainment_Type_HitWolf = 1;	//���л�̫�ǳɾ�
	public static int Attainment_Type_HitBomb = 2;	//�����ӵ��ɾ�
	public static int Attainment_Type_UseProp = 3;	//ʹ�õ��߳ɾ�
	public static int Attainment_Type_HitFruit = 4;	//����ˮ���ɾ�
	public static int Attainment_Type_Level = 5;	//���سɾ�
	
	/*�ɾ���Ϣ*/
	public static String[][][] Attainments = {
		/*�÷ֳɾ�*/
		{	/*0-����,1-����,2-����, 3-����*/
			{"һ�����","���50000��","10", "50000"},
			{"�ⲻ��ʲô","���100000��","15", "100000"},
			{"�÷ִ���","���150000��","20", "150000"},
			{"��ɳ����","���200000��","30", "200000"},
			{"�Ʊ���","���250000��","50", "250000"},
		},
		/*���л�̫�ǳɾ�*/
		{	/*0-����,1-����,2-����*/
			{"��ʼ����","����50ֻ��̫��","10", "50"},
			{"��������","����100ֻ��̫��","15", "100"},
			{"����ϣ��","����300ֻ��̫��","20", "300"},
			{"��嶷ʿ","����500ֻ��̫��","30", "500"},
			{"����ػ���","����900ֻ��̫��","50", "900"},
		},
		/*�����ӵ��ɾ�*/
		{	/*0-����,1-����,2-����*/
			{"��������","����1���ӵ�","10", "1"},
			{"������׼","����10���ӵ�","15", "10"},
			{"�ٷ�����","����20���ӵ�","20", "20"},
			{"�ѻ���","����50���ӵ�","30", "50"},
			{"��Ļ���","����100���ӵ�","50", "100"},
		},
		/*ʹ�õ��߳ɾ�*/
		{	/*0-����,1-����,2-����*/
			{"С��ţ��","ʹ��1�ε���","10", "1"},
			{"����ר��","ʹ��5�ε���","15", "5"},
			{"����ר��","ʹ��10�ε���","20", "10"},
			{"���ߴ�ʦ","ʹ��20�ε���","30", "20"},
			{"��˧��","ʹ��30�ε���","50", "30"},
		},
		/*����ˮ���ɾ�*/
		{	/*0-����,1-����,2-����*/
			{"��ˮ��","����10��ˮ��","10", "10"},
			{"ˮ������","����20��ˮ��","15", "20"},
			{"��Ͷ���","����50��ˮ��","20", "50"},
			{"ˮ������","����100��ˮ��","30", "100"},
			{"�����ܵ�����","����200��ˮ��","50", "200"},
		},
		/*���سɾ�*/
		{	/*0-����,1-����,2-����*/
			{"������·","ͨ����1��","10", "1"},
			{"ð�ռ�","ͨ����5��","15", "5"},
			{"��������","ͨ����10��","20", "10"},
			{"ʤ������ǰ��","ͨ����13��","30", "13"},
			{"������ʿ","ͨ����15��","50", "15"},
		},
	};
}
