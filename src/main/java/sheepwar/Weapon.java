package sheepwar;


/**
 * ��������
 * @author Administrator
 */
public class Weapon implements Common {
	int id;                 //����id
	int objectId;			//����������	ID
	int speedX;             //X ���ƶ��ٶ�
	int speedY;             //Y���ƶ��ٶ�
	int direction;          //�����ƶ����� 2��3��
	int harm;               //��ɵ��˺�
	int mapx;				//X������
	int mapy;				//Y������
	int width;				//�������
	int height;				//�����߶�
	float terminalX;		//�յ������
	float terminalY;		//�յ�������(������ˮ�׵Ļ�,��ʾˮ�׵���ʼ����)
	float flagx;			//��ʶx
	float flagy;			//��ʶy
	int random;				//�������
	boolean isSingle;		//��������
	
	/**
	 * ������ͨ����---Shuriken
	 * @param own   �����ٶ���(����и���Ч��)
	 * @param objectId   ������ID
	 * @param mapx        ��ͨ����������
	 * @param mapy        ��ͨ����������
	 * @param direction   ��ͨ��������(2��3��)
	 * @param width       ��������ߵĿ�(���ڶ�λ�ӵ�������)
	 * @param height      ��������ߵĸ� (���ڶ�λ�ӵ�������
	 */
	public void createShuriken(Role own, int objectId, int mapx, int mapy, int direction, int width, int height) {
		Weapon shuriKen=new Weapon();
		shuriKen.direction = direction;
		shuriKen.objectId = objectId;
		
		
	}

}
