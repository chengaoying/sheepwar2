package sheepwar;
public class Role {
	int id; 			//ID
	int mapx; 			//�ڵ�ͼ�ϵĺ�����
	int mapy; 			//�ڵ�ͼ�ϵ�������
	int frame;			//��ɫ��ǰ֡��
	int direction; 		//�ƶ����� 0 ���ϣ�1����
	int status;  		//״̬(0�״̬, -1��״̬, 1�ɹ�����״̬)
	int status2;		//�ڶ�״̬��0�ڵ��棬 1�뿪���棩
	int position;		//���������ϵ�λ��( 1�ڵ�һ�������ϣ�2�ڵڶ��������ϣ�3�ڵ����������ϣ�4�ڵ��ĸ�������)
	int position2;		//�ǳ��ֵĳ�ʼλ��(0-����, -1-����)
	int nonceLife;		//����ֵ
	int lifeNum; 		//������
	int speed;			//�ƶ��ٶ�
	int scores; 		//����
	int coorX;			//�����
	int coorY;			//�Ƿ����ӵ���������
	int height;         //��ɫ����߶�
	int width;          //��ɫ������
	int eatNum;			//���ػ��е�����
	int bombNum;		//�����ӵ�����
	int colorId;		//����ID
	int attackTime;		//�����д���
	
	Role role;		   //�Ӷ���
}
