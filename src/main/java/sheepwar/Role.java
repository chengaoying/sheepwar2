package sheepwar;
public class Role {
	int id; 			//ID
	int mapx; 			//�ڵ�ͼ�ϵĺ�����
	int mapy; 			//�ڵ�ͼ�ϵ�������
	int frame;			//��ɫ��ǰ֡��
	int index;			//����֡�л�
	int direction; 		//�ƶ����� 0 ���ϣ�1����
	int status;  		//״̬(0�״̬, -1��״̬, 1�ɹ�����״̬, 2����״̬)
	int status2;		//�ڶ�״̬��0�ڵ��棬 1�뿪���棩
	int position;		//���������ϵ�λ��( 1�ڵ�һ�������ϣ�2�ڵڶ��������ϣ�3�ڵ����������ϣ�4�ڵ��ĸ�������)
	int position2;		//�ǳ��ֵĳ�ʼλ��(0-����, -1-����)
	int nonceLife;		//����ֵ
	int lifeNum; 		//������
	int speed;			//�ƶ��ٶ�
	int scores; 		//����
	int scores2;		//�������û���
	int coorX;			//�����
	int coorY;			//�Ƿ����ӵ���������
	int height;         //��ɫ����߶�
	int width;          //��ɫ������
	int hitNum;			//���ػ��е�����
	int hitTotalNum;	//�����ǵ�����
	int hitBuble;		//���е�������
	int useProps;		//ʹ�õĵ�����
	int hitFruits;		//���е�ˮ����
	int bombNum;		//�����ӵ�����
	int hitRatio;		//����Ŀ����
	int hitBooms;		//�����ӵ���
	int colorId;		//����ID
	int attackTime;		//�����д���
	int attainment;		//�ɾ͵���
	int dizzyIndex;		//����ѣ��
	int dizzyFlag;
	
	Role role;		   //�Ӷ���
}
