package sheepwar;

import java.util.Vector;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.util.RandomValue;

/**
 * ÿ����������ʽ���ֵ���
 * @author xiaochen
 */
public class Batches implements Common{
	
	int count;				//ÿ���ǵ�����
	int period;				//ÿֻ���ֵļ��
	int pattern;			//���зֲ���ʽ
	int ballonId;			//��������
	
	public Vector npcs = new Vector();   
	public Vector ballons = new Vector();   
	public Role redWolf;
	private int[] coors = {60,110,160,210}; 					 //�������ĺ�����
	private int[] coorY = {245,230,245,295,315,355,295,315};  	 //�Ƿ����ӵ���Y����
	private long startTime,endTime;
	public long bublesSTime,bublesETime;

	/* �������� */
	public static int bublePara[] = {
	/* 0 ͼƬ��1 ͼƬ�ߣ�2�ṩ�Ļ���, 3����Y�����꣬4�ٶ� */
	   45, 75, 200, 445, 8
	};
	private int[] bubleX = {70,120,170,220};
	
	/*npc���ǣ�����*/
	public static int npcPara[] = {
		/*0-ͼƬ��ȣ�1-ͼƬ�߶ȣ�2-�ƶ��ٶȣ�3-��ʼX����*/
		45, 56, 5, -45-45
	};
	/*ˮ������*/
	public static int fruitPara[] = {
		/*0-ͼƬ��ȣ�1-ͼƬ�߶ȣ�2�ṩ�Ļ���*/
		35,45,100,
	};
	
	/*����һ����*/
 	public void createBatches(int level, int batch, int position2){
		int count = BatchesInfo[level-1][batch][0];				//�����ǵ�����
		int spreed_mode = BatchesInfo[level-1][batch][2];
		int ran = RandomValue.getRandInt(regular.length-1);  	//���߷�ʽ
		for(int i=0;i<count;i++){
			Role wolf = new Role();
			wolf.status = ROLE_ALIVE;
			wolf.status2 = ROLE_ON_GROUND;
			if(SheepWarGameEngine.isFirstGame){
				wolf.speed = npcPara[2]*2;
			}else{
				wolf.speed = npcPara[2];
			}
			wolf.width = npcPara[0];
			wolf.height = npcPara[1];
			wolf.direction = ROLE_MOVE_RIGHT;
			wolf.coorY = coorY[RandomValue.getRandInt(8)];
			wolf.position = 0;
			wolf.position2 = position2;
			wolf.scores = bublePara[2];
			wolf.colorId = BatchesInfo[level-1][batch][1];
			if(wolf.position2 == WOLF_POSITION_TOP){
				wolf.mapy = 25;
			}else{
				wolf.mapy = 460;
			}
			setWolfInfo(count, spreed_mode, wolf, i, ran); 
			
			npcs.addElement(wolf);
		}
	}
	
	/*���������ؿ�һ����*/
	public void createBatchesReward(int level, int batch, int position2){
		int count = RewardLevelBatchesInfo[level-1][batch][0];	//�����ǵ�����
		int spreed_mode = RewardLevelBatchesInfo[level-1][batch][2];
		int ran = RandomValue.getRandInt(regular.length-1);  //���߷�ʽ
		for(int i=0;i<count;i++){
			Role wolf = new Role();
			wolf.status = ROLE_ALIVE;
			wolf.status2 = ROLE_ON_GROUND;
			wolf.speed = npcPara[2];
			wolf.width = npcPara[0];
			wolf.height = npcPara[1];
			wolf.direction = ROLE_MOVE_RIGHT;
			wolf.coorY = coorY[RandomValue.getRandInt(8)];
			wolf.position = 0;
			wolf.position2 = position2;
			wolf.scores = bublePara[2];
			wolf.colorId = RewardLevelBatchesInfo[level-1][batch][1];
			if(wolf.position2 == WOLF_POSITION_TOP){
				wolf.mapy = 26;
			}else{
				wolf.mapy = 460;
			}
			setWolfInfo(count, spreed_mode, wolf, i, ran); 
			
			npcs.addElement(wolf);
		}
	}
	
	/*����һֻ��(�����ѵ��ǣ�������Ϸ֮��ָ�)*/
	public void createWolf(int mapx, int mapy, int direction, int position, int position2){
		Role wolf = new Role();
		wolf.status = ROLE_SUCCESS;
		wolf.status2 = ROLE_IN_AIR;
		wolf.speed = npcPara[2];
		wolf.width = npcPara[0];
		wolf.height = npcPara[1];
		wolf.direction = direction;
		wolf.position = position;
		wolf.position2 = position2;
		wolf.mapx = mapx;
		wolf.mapy = mapy;
		npcs.addElement(wolf);
	}
	
	/**
	 * ���ݸ����ǵ��������ֲ���ʽ�����ǵ���ʼ����ͽ����
	 * @param count  �ǵ�����
	 * @param spreed_mode  �ֲ���ʽ
	 * @param wolf
	 * @param i     �����ǵ��еڼ�ֻ
	 */
	private void setWolfInfo(int count, int spreed_mode, Role wolf, int i, int ran){
		int space = 45;
		switch (count){
		case 1:
			wolf.mapx = npcPara[3];
			wolf.coorX = coors[RandomValue.getRandInt(0,4)];
			break;
		case 2:
			switch (spreed_mode){
			case SPREED_BELOW:
				wolf.mapx = npcPara[3] - i*(space*3);
				wolf.coorX = coors[2-i];
				break;
			case SPREED_ABOVE:
				wolf.mapx = npcPara[3] - i*space;
				wolf.coorX = coors[1+i];
				break;
			case SPREED_VERTICAL:
				wolf.mapx = npcPara[3] - i*(space+space);
				wolf.coorX = coors[1];
				break;
			}
			break;
		case 3:
			switch (spreed_mode){
			case SPREED_BELOW:
				wolf.mapx = npcPara[3] - i*(space*3);
				wolf.coorX = coors[2-i];
				break;
			case SPREED_ABOVE:
				wolf.mapx = npcPara[3] - i*space;
				wolf.coorX = coors[0+i];
				break;
			case SPREED_VERTICAL:
				wolf.mapx = npcPara[3] - i*(space+space);
				wolf.coorX = coors[1];
				break;
			}
			break;
		case 4:
			switch (spreed_mode){
			case SPREED_BELOW:
				wolf.mapx = npcPara[3] - i*(space*3);
				wolf.coorX = coors[3-i];
				break;
			case SPREED_ABOVE:
				wolf.mapx = npcPara[3] - i*space;
				wolf.coorX = coors[0+i];
				break;
			case SPREED_VERTICAL:
				wolf.mapx = npcPara[3] - i*(space+space);
				wolf.coorX = coors[1];
				break;
			case SPREED_IRREGULAR:
				wolf.coorX = coors[regular[ran][i]-1];
				wolf.mapx = npcPara[3] - i*(space+space);
				/*switch (ran){
				case 0:
					wolf.coorX = coors[regular[ran][i]-1];
					wolf.mapx = npcPara[3] - i*space;
					break;
				case 1:
					wolf.coorX = coors[regular[ran][i]-1];
					wolf.mapx = npcPara[3] - i*space;
					break;
				case 2:
					wolf.coorX = coors[regular[ran][i]-1];
					wolf.mapx = npcPara[3] - i*space;
					break;
				case 3:
					wolf.coorX = coors[regular[ran][i]-1];
					wolf.mapx = npcPara[3] - i*space;
					break;
				case 4:
					wolf.coorX = coors[regular[ran][i]-1];
					wolf.mapx = npcPara[3] - i*space;
					break;
				}*/
				break;
			}
			break;
		}
	}
	
	/*��
	private void createWolf(int level) {
		Role wolf = new Role();
		wolf.status = ROLE_ALIVE;
		wolf.mapx = npcPara[3];
		wolf.mapy = npcPara[4];
		wolf.speed = npcPara[2];
		wolf.width = npcPara[0];
		wolf.height = npcPara[1];
		wolf.coorX = coors[RandomValue.getRandInt(0,4)];		
		wolf.direction = ROLE_MOVE_RIGHT;         
		
		npcs.addElement(wolf);
	}*/
	
	/*������̫��*/
	public Role createRedWolf(){
		Role role = new Role();
		role.speed = 3;
		role.width = 29;
		role.height = 55;
		role.mapx = 300;
		role.mapy = 26;
		role.direction = ROLE_MOVE_LEFT;
		startTime = System.currentTimeMillis()/1000;
		return role;
	}
	
	/*�����ϵ�����*/
	public void createBallon(Role wolf){
		if(wolf.role != null){
			return;
		}
		Role ballon = new Role();
		ballon.id = wolf.colorId;
		ballon.width = bublePara[0];
		ballon.height = bublePara[1];
		ballon.scores = bublePara[2];
		ballon.mapx = wolf.mapx+17;
		ballon.mapy = wolf.mapy+37 - ballon.height;      
		ballon.speed = wolf.speed;
		wolf.role = ballon;
	}
	
	/*����*/
	public void createBallon(){
		Role ballon = new Role();
		ballon.id = bubleColor[RandomValue.getRandInt(5)];
		ballon.status = ROLE_ALIVE;
		ballon.width = bublePara[0];
		ballon.height = bublePara[1];
		ballon.scores = bublePara[2];
		ballon.mapx = bubleX[RandomValue.getRandInt(4)];
		ballon.mapy = bublePara[3];     
		ballon.speed = bublePara[4]; 
		ballons.addElement(ballon);
	}
	
	public void showBuble(SGraphics g){
		Role buble = null;
		for(int i=ballons.size()-1;i>=0;i--){
			buble = (Role) ballons.elementAt(i);
			Image ballon = Resource.loadImage(buble.id);
			int ballonW = ballon.getWidth()/5, ballonH = ballon.getHeight();
			if(!StateGame.pasueState){
				if(buble.status==ROLE_ALIVE){
					if(buble.frame<2){
						buble.frame ++;
					}else{
						buble.mapy -= buble.speed;
					}
					g.drawRegion(ballon, buble.frame*ballonW, 0, ballonW, ballonH, 0, buble.mapx, buble.mapy, 20);
				}else if(buble.status==ROLE_DEATH){
					if(buble.frame<4){
						buble.frame ++;
						g.drawRegion(ballon, buble.frame*ballonW, 0, ballonW, ballonH, 0, buble.mapx, buble.mapy, 20);
					}else{
						ballons.removeElement(buble);
					}
				}
				//g.drawImage(ballon, buble.mapx, buble.mapy, 20);
			}
		}
	}

	public void showWolf(SGraphics g, Weapon weapon) {
		g.setClip(0, 0, gameW, ScrH);
		Image wolf_Image = Resource.loadImage(Resource.id_wolf_run); 
		Image wolf_down = Resource.loadImage(Resource.id_wolf_down);
		Image wolf_climb = Resource.loadImage(Resource.id_wolf_climb);
		Image wolf_die = Resource.loadImage(Resource.id_wolf_die);
		Image wolf_shove = Resource.loadImage(Resource.id_wolf_shove);
		int len = npcs.size();
		Role wolf = null;
		for(int i=len-1;i>=0;i--){
			wolf = (Role)npcs.elementAt(i);
			if(wolf.position2 == WOLF_POSITION_TOP){  //����������
				/*���µ��ٽ��*/
				if(wolf.mapx >= wolf.coorX){    
					wolf.direction = ROLE_MOVE_DOWN;
				}
				/*���ҵ��ٽ��*/
				if(wolf.mapy >= 463){
					wolf.direction = ROLE_MOVE_RIGHT;
					if(wolf.status!=ROLE_DIZZY){
						wolf.status = ROLE_SUCCESS;				//��״̬˵���ǳɹ����ѹ���(����)�������������������
					}
				}
				/*�����ٽ��*/
				if(wolf.mapx == 420){
					if(wolf.status==ROLE_DIZZY){
						wolf.direction = ROLE_MOVE_RIGHT;
					}else{
						wolf.direction = ROLE_MOVE_UP;
						if(wolf.position == 0){
							setWolfLadders(wolf);
						}
					}
					if(SheepWarGameEngine.isFirstGame){
						if(StateGame.HASWOLF_ONE && StateGame.HASWOLF_TWO
								&& StateGame.HASWOLF_THREE && StateGame.HASWOLF_FOUR
								&& wolf.position==0){
							wolf.direction = ROLE_MOVE_RIGHT;
						}
					}
				}
				wolfDown(g, wolf, weapon, wolf_Image, wolf_down, wolf_climb, wolf_die);
				
			}else if(wolf.position2 == WOLF_POSITION_BOTTOM){  //����������
				
				/*���ϵ��ٽ��*/
				if(wolf.mapx == wolf.coorX){    
					wolf.direction = ROLE_MOVE_UP;
				}
				/*���ҵ��ٽ��*/
				if(wolf.mapy <= 26){
					if(wolf.status!=ROLE_DIZZY){
						wolf.direction = ROLE_MOVE_RIGHT;
						wolf.status = ROLE_SUCCESS;
						if(wolf.position == 0){
							setWolfLadders(wolf);
						}
					}else{
						wolf.direction = ROLE_MOVE_LEFT;
					}
				}
				wolfUp(g, wolf, weapon, wolf_Image, wolf_down, wolf_climb, wolf_die,wolf_shove);
			}
		}	
		g.setClip(0, 0, ScrW, ScrH);
	}
	
	/*����������*/
	private void wolfDown(SGraphics g, Role wolf, Weapon weapon, Image wolf_Image, Image wolf_down,Image wolf_climb, Image wolf_die){
		Image dizzy = Resource.loadImage(Resource.id_prop_2_eff);
		int dizzyW = dizzy.getWidth()/3, dizzyH = dizzy.getHeight();
		int tempx = wolf.mapx;
		int tempy = wolf.mapy;
		if(wolf.direction == ROLE_MOVE_RIGHT){  //������
			if(!StateGame.pasueState){
				tempx += wolf.speed;
				wolf.mapx = tempx;
				wolf.frame = (wolf.frame + 1) % 6; 
				if(wolf.dizzyFlag<2){
					wolf.dizzyFlag++;
				}else{
					wolf.dizzyFlag=0;
					wolf.dizzyIndex = (wolf.dizzyIndex+1)%3;
				}
			}
			g.drawRegion(wolf_Image, wolf.frame*wolf.width, 0, wolf.width, wolf.height, 0, tempx, tempy, 20);
			if(wolf.status==ROLE_DIZZY){
				g.drawRegion(dizzy, wolf.dizzyIndex*dizzyW, 0, dizzyW, dizzyH, 0, tempx, tempy-15, 20);
			}
		}else if(wolf.direction == ROLE_MOVE_DOWN){   //������
			createBallon(wolf); //��������
			wolf.status2 = ROLE_IN_AIR;
			int tempx_ballon = wolf.role.mapx;
			int tempy_ballon = wolf.role.mapy;
			Image ballon = Resource.loadImage(wolf.role.id);
			if(wolf.status == ROLE_ALIVE || wolf.status == ROLE_ATTACK){
				if(!StateGame.pasueState){
					if(wolf.role.frame<2){
						wolf.role.frame = (wolf.role.frame+1);
					}else{
						tempy += wolf.speed;
						wolf.mapy = tempy;
						tempy_ballon += wolf.role.speed;
						wolf.role.mapy = tempy_ballon;
					}
				}
				int wolfDownW = wolf_down.getWidth()/3, wolfDownH = wolf_down.getHeight();
				if(wolf.colorId != orange && wolf.mapy == wolf.coorY){			//�������������ɫ�����Ƿ񹥻�����,��ɫ�Ͳ�ɫ���ṥ��
					weapon.createBoom(wolf, Weapon.WEAPON_MOVE_RIGHT);
					wolf.status = ROLE_ATTACK;
				}
				if(wolf.status==ROLE_ATTACK){
					wolf.frame = (wolf.frame+1)%3;
					if(wolf.frame==0){
						wolf.frame=1;
					}
					g.drawRegion(wolf_down, wolf.frame*wolfDownW, 0, wolfDownW, wolfDownH, 0, tempx, tempy, 20);
					if(wolf.frame==2){
						wolf.status = ROLE_ALIVE;
					}
				}else{
					if(wolf.role.id != orange){
						g.drawRegion(wolf_down, wolfDownW, 0, wolfDownW, wolfDownH, 0, tempx, tempy, 20);
					}else{
						g.drawRegion(wolf_down, 0, 0, wolfDownW, wolfDownH, 0, tempx, tempy, 20);
					}
				}
				g.drawRegion(ballon, wolf.role.frame*wolf.role.width, 0, wolf.role.width, wolf.role.height, 0, tempx_ballon, tempy_ballon, 20);
			}else if(wolf.status == ROLE_DEATH){
				if(wolf.role.frame<4){
					wolf.role.frame = (wolf.role.frame+1);
					g.drawRegion(ballon, wolf.role.frame*wolf.role.width, 0, wolf.role.width, wolf.role.height, 0, tempx_ballon, tempy_ballon, 20);
				}else{
					tempy += wolf.speed;
					wolf.mapy = tempy;
				}
				int w = wolf_die.getWidth()/2;
				int h = wolf_die.getHeight();
				if(wolf.index<2){
					wolf.index++;
					wolf.frame=0;
				}else{
					wolf.index=0;
					wolf.frame=1;
				}
				//wolf.frame = (wolf.frame+1)%2;
				g.drawRegion(wolf_die, wolf.frame*w, 0, w, h, 0, tempx, tempy, 20);
			}
			
		}else if(wolf.direction == ROLE_MOVE_UP){    //������
			wolf.frame = 0;
			int positionY = 190;
			if(wolf.position == ON_ONE_LADDER){
				if(tempy >= positionY+89*3){
					tempy -= wolf.speed;
					wolf.mapy = tempy;
					wolf.frame = (wolf.frame+1)%2;
				}else if(wolf.status==ROLE_DIZZY){
					wolf.direction=ROLE_MOVE_RIGHT;
				}
			}else if(wolf.position == ON_TWO_LADDER){
				if(tempy >= positionY+89*2){
					tempy -= wolf.speed;
					wolf.mapy = tempy;
					wolf.frame = (wolf.frame+1)%2;
				}else if(wolf.status==ROLE_DIZZY){
					wolf.direction=ROLE_MOVE_RIGHT;
				}
			}else if(wolf.position == ON_THREE_LADDER){
				if(tempy >= positionY+89){
					tempy -= wolf.speed;
					wolf.mapy = tempy;
					wolf.frame = (wolf.frame+1)%2;
				}else if(wolf.status==ROLE_DIZZY){
					wolf.direction=ROLE_MOVE_RIGHT;
				}
			}else if(wolf.position == ON_FOUR_LADDER){
				if(tempy >= positionY){
					tempy -= wolf.speed;
					wolf.mapy = tempy;
					wolf.frame = (wolf.frame+1)%2;
				}else if(wolf.status==ROLE_DIZZY){
					wolf.direction=ROLE_MOVE_RIGHT;
				}else{
					if(!SheepWarGameEngine.isFirstGame){  //���ǽ�ѧ�ؿ�
						StateGame.IS_FOUR_WOLF = true;  // ���������ϵ�������
					}
				}
			}
			int w = wolf_climb.getWidth()/2;
			int h = wolf_climb.getHeight();
			g.drawRegion(wolf_climb, wolf.frame*w, 0, w, h, 0, tempx, tempy, 20);
			if(wolf.dizzyFlag<2){
				wolf.dizzyFlag++;
			}else{
				wolf.dizzyFlag=0;
				wolf.dizzyIndex = (wolf.dizzyIndex+1)%3;
			}
			if(wolf.status==ROLE_DIZZY){
				g.drawRegion(dizzy, wolf.dizzyIndex*dizzyW, 0, dizzyW, dizzyH, 0, tempx, tempy-15, 20);
			}
		}
	}
	
	/*����������*/
	private void wolfUp(SGraphics g, Role wolf, Weapon weapon, Image wolf_Image, Image wolf_down,Image wolf_climb, Image wolf_die, Image wolf_shove){
		Image dizzy = Resource.loadImage(Resource.id_prop_2_eff);
		int dizzyW = dizzy.getWidth()/3, dizzyH = dizzy.getHeight();
		int tempx = wolf.mapx;
		int tempy = wolf.mapy;
		if(wolf.direction == ROLE_MOVE_RIGHT){  //������
			//if(!StateGame.pasueState){
				if(wolf.status == ROLE_SUCCESS){
					int positionX = 210;
					if(wolf.position == ON_ONE_LADDER){
						if(tempx+wolf.speed < positionX){
							tempx += wolf.speed;
							wolf.mapx = tempx;
							wolf.frame = (wolf.frame + 1) % 6; 
							g.drawRegion(wolf_Image, wolf.frame*wolf.width, 0, wolf.width, wolf.height, 0, tempx, tempy+10, 20);
						}else{
							wolf.mapx = tempx = positionX;
							if(wolf.index<10){
								wolf.frame=0;
								wolf.index++;
							}else{
								wolf.index=0;
								wolf.frame = 1;
							}
							g.drawRegion(wolf_shove, wolf.frame*wolf_shove.getWidth()/2, 0, wolf_shove.getWidth()/2, wolf_shove.getHeight(), 0, tempx, tempy+10, 20);
						}
					}else if(wolf.position == ON_TWO_LADDER){
						if(tempx+wolf.speed < positionX-wolf.width){
							tempx += wolf.speed;
							wolf.mapx = tempx;
							wolf.frame = (wolf.frame + 1) % 6; 
							g.drawRegion(wolf_Image, wolf.frame*wolf.width, 0, wolf.width, wolf.height, 0, tempx, tempy+10, 20);
						}else if(tempx>positionX-wolf.width){
							tempx -= wolf.speed;
							wolf.mapx = tempx;
							wolf.frame = (wolf.frame + 1) % 6; 
							g.drawRegion(wolf_Image, wolf.frame*wolf.width, 0, wolf.width, wolf.height, Sprite.TRANS_MIRROR, tempx, tempy+10, 20);
						}else{
							if(wolf.index<10){
								wolf.frame=0;
								wolf.index++;
							}else{
								wolf.index=0;
								wolf.frame = 1;
							}
							g.drawRegion(wolf_shove, wolf.frame*wolf_shove.getWidth()/2, 0, wolf_shove.getWidth()/2, wolf_shove.getHeight(), 0, tempx, tempy+10, 20);
						}
					}else if(wolf.position == ON_THREE_LADDER){
						if(tempx+wolf.speed < positionX-wolf.width*2){
							tempx += wolf.speed;
							wolf.mapx = tempx;
							wolf.frame = (wolf.frame + 1) % 6; 
							g.drawRegion(wolf_Image, wolf.frame*wolf.width, 0, wolf.width, wolf.height, 0, tempx, tempy+10, 20);
						}else if(tempx>positionX-wolf.width*2){
							tempx -= wolf.speed;
							wolf.mapx = tempx;
							wolf.frame = (wolf.frame + 1) % 6; 
							g.drawRegion(wolf_Image, wolf.frame*wolf.width, 0, wolf.width, wolf.height, Sprite.TRANS_MIRROR, tempx, tempy+10, 20);
						}else{
							if(wolf.index<10){
								wolf.frame=0;
								wolf.index++;
							}else{
								wolf.index=0;
								wolf.frame = 1;
							}
							g.drawRegion(wolf_shove, wolf.frame*wolf_shove.getWidth()/2, 0, wolf_shove.getWidth()/2, wolf_shove.getHeight(), 0, tempx, tempy+10, 20);
						}
					}else if(wolf.position == ON_FOUR_LADDER){
						if(tempx+wolf.speed < positionX-wolf.width*3){
							tempx += wolf.speed;
							wolf.mapx = tempx;
							wolf.frame = (wolf.frame + 1) % 6; 
							g.drawRegion(wolf_Image, wolf.frame*wolf.width, 0, wolf.width, wolf.height, 0, tempx, tempy+10, 20);
						}else if(tempx>positionX-wolf.width*3){
							tempx -= wolf.speed;
							wolf.mapx = tempx;
							wolf.frame = (wolf.frame + 1) % 6; 
							g.drawRegion(wolf_Image, wolf.frame*wolf.width, 0, wolf.width, wolf.height, Sprite.TRANS_MIRROR, tempx, tempy+10, 20);
						}else{
							if(wolf.index<10){
								wolf.frame=0;
								wolf.index++;
							}else{
								wolf.index=0;
								wolf.frame = 1;
							}
							g.drawRegion(wolf_shove, wolf.frame*wolf_shove.getWidth()/2, 0, wolf_shove.getWidth()/2, wolf_shove.getHeight(), 0, tempx, tempy+10, 20);
							StateGame.IS_FOUR_WOLF = true;  // ���������ϵ�������
						}
					}
				}else{
					if(!StateGame.pasueState){
						tempx += wolf.speed;
						wolf.mapx = tempx;
						wolf.frame = (wolf.frame + 1) % 6; 
					}
					g.drawRegion(wolf_Image, wolf.frame*wolf.width, 0, wolf.width, wolf.height, 0, tempx, tempy+10, 20);
				}
			//}
		}else if(wolf.direction == ROLE_MOVE_UP){   //������
			createBallon(wolf); //��������
			wolf.status2 = ROLE_IN_AIR;
			int tempx_ballon = wolf.role.mapx;
			int tempy_ballon = wolf.role.mapy;
			Image ballon = Resource.loadImage(wolf.role.id);
			if(wolf.status == ROLE_ALIVE || wolf.status == ROLE_ATTACK){
				if(!StateGame.pasueState){
					if(wolf.role.frame<2){
						wolf.role.frame = (wolf.role.frame+1);
					}else{
						tempy -= wolf.speed;
						wolf.mapy = tempy;
						tempy_ballon -= wolf.role.speed;
						wolf.role.mapy = tempy_ballon;
					}
				}
				int wolfDownW = wolf_down.getWidth()/3, wolfDownH = wolf_down.getHeight();
				if(wolf.colorId != orange && wolf.mapy == wolf.coorY){			//�������������ɫ�����Ƿ񹥻�����,��ɫ�Ͳ�ɫ���ṥ��
					weapon.createBoom(wolf, Weapon.WEAPON_MOVE_RIGHT);
					wolf.status = ROLE_ATTACK;
				}
				if(wolf.status==ROLE_ATTACK){
					wolf.frame = (wolf.frame+1)%3;
					if(wolf.frame==0){
						wolf.frame=1;
					}
					g.drawRegion(wolf_down, wolf.frame*wolfDownW, 0, wolfDownW, wolfDownH, 0, tempx, tempy, 20);
					if(wolf.frame==2){
						wolf.status = ROLE_ALIVE;
					}
				}else{
					if(wolf.role.id != orange){
						g.drawRegion(wolf_down, wolfDownW, 0, wolfDownW, wolfDownH, 0, tempx, tempy, 20);
					}else{
						g.drawRegion(wolf_down, 0, 0, wolfDownW, wolfDownH, 0, tempx, tempy, 20);
					}
				}
				g.drawRegion(ballon, wolf.role.frame*ballon.getWidth()/5, 0, ballon.getWidth()/5, ballon.getHeight(), 0, tempx_ballon, tempy_ballon, 20);
			}else if(wolf.status == ROLE_DEATH){
				if(wolf.role.frame<4){
					wolf.role.frame = (wolf.role.frame+1)%5;
					g.drawRegion(ballon, wolf.role.frame*ballon.getWidth()/5, 0, ballon.getWidth()/5, wolf.role.height, 0, tempx_ballon, tempy_ballon, 20);
				}else{
					tempy += wolf.speed;
					wolf.mapy = tempy;
				}
				int w = wolf_die.getWidth()/2;
				int h = wolf_die.getHeight();
				if(wolf.index<2){
					wolf.index++;
					wolf.frame=0;
				}else{
					wolf.index=0;
					wolf.frame=1;
				}
				//wolf.frame = (wolf.frame+1)%2;
				g.drawRegion(wolf_die, wolf.frame*w, 0, w, h, 0, tempx, tempy, 20);
			}
		}else if(wolf.direction == ROLE_MOVE_LEFT){  //������
			tempx -= wolf.speed;
			wolf.mapx = tempx;
			wolf.frame = (wolf.frame + 1) % 6; 
			g.drawRegion(wolf_Image, wolf.frame*wolf.width, 0, wolf.width, wolf.height, Sprite.TRANS_MIRROR, tempx, tempy+10, 20);
			if(wolf.dizzyFlag<2){
				wolf.dizzyFlag++;
			}else{
				wolf.dizzyFlag=0;
				wolf.dizzyIndex = (wolf.dizzyIndex+1)%3;
			}
			if(wolf.status==ROLE_DIZZY){
				g.drawRegion(dizzy, wolf.dizzyIndex*dizzyW, 0, dizzyW, dizzyH, 0, tempx, tempy-15, 20);
			}
		}
	}
	
	/*û�б����������ǣ��������������ϵ�λ��*/
	private void setWolfLadders(Role wolf){
		if(!StateGame.HASWOLF_ONE){
			wolf.position = ON_ONE_LADDER;
			StateGame.HASWOLF_ONE = true;
			
		}else if(!StateGame.HASWOLF_TWO){
			wolf.position = ON_TWO_LADDER;
			StateGame.HASWOLF_TWO = true;
			
		}else if(!StateGame.HASWOLF_THREE){
			wolf.position = ON_THREE_LADDER;
			StateGame.HASWOLF_THREE = true;
			
		}else if(!StateGame.HASWOLF_FOUR){
			wolf.position = ON_FOUR_LADDER;
			StateGame.HASWOLF_FOUR = true;
		}
	}
	
	public void showRedWolf(SGraphics g, Weapon weapon){
		Image redWolfI = Resource.loadImage(Resource.id_red_wolf);
		if(redWolf.mapx<=0){
			redWolf.direction = ROLE_MOVE_RIGHT;
		}
		if(redWolf.mapx>=300){
			redWolf.direction = ROLE_MOVE_LEFT;
		}
		if(!StateGame.pasueState){
			if(redWolf.direction == ROLE_MOVE_LEFT){
				redWolf.mapx -= redWolf.speed;
			}else{
				redWolf.mapx += redWolf.speed;
			}
			redWolf.frame = (redWolf.frame+1)%2;
		}
		g.drawRegion(redWolfI, redWolf.frame*redWolfI.getWidth()/2, 0, redWolfI.getWidth() / 2, redWolfI.getHeight(),
				redWolf.direction==ROLE_MOVE_LEFT?0:Sprite.TRANS_MIRROR, redWolf.mapx, redWolf.mapy, 20);
		
		endTime = System.currentTimeMillis()/1000;
		if(endTime-startTime>=5&&!StateGame.pasueState){
			weapon.createFruit(redWolf);
			redWolf.bombNum ++;
			startTime = System.currentTimeMillis()/1000;
		}
	}
	
	public void clearObject(){
		npcs.removeAllElements();
		ballons.removeAllElements();
		redWolf = null;
	}
}
