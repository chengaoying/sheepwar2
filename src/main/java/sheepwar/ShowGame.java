package sheepwar;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.ui.DrawUtil;
import cn.ohyeah.stb.ui.TextView;
import cn.ohyeah.stb.util.RandomValue;

/**
 * ��ʾͼ��
 * 
 * @author Administrator
 * 
 */
public class ShowGame implements Common {
	public Image playing_cloudsmall,playing_cloudbig;
	private int tempx=ScrW, tempy=20, tempx2=ScrW, tempy2=30;//ScrW��Ļ��ȣ�tempx��ʼ=ScrW�������ñ��ʽtempx-=1��ʹ���ƶ�
	
	/* ��½�������� */
	public int menuAxis[][] = { { 523, 243 }, { 466, 288 }, { 523, 333 },
			{ 466, 378 }, { 523, 423 }, { 466, 468 }, };
	/* ��Ϸ�������� */
	public int playingAxis[][] = { { 491, 0 }, { 0, 529 }, { 0, 72 },
			{ 377, 153 }, { 377, 240 }, { 377, 324 }, { 377, 409 }, };

	public void drawMainMenu(Graphics g, int index) {
		Image main_bg = Resource.loadImage(Resource.id_main_bg);
		Image main_menu = Resource.loadImage(Resource.id_main_menu);
		g.drawImage(main_bg, 0, 0, 0);
		for (int i = 0; i < menuAxis.length; ++i) {
			g.drawRegion(main_menu,
					(index != i) ? main_menu.getWidth() / 2 : 0,
					i * main_menu.getHeight() / 6, main_menu.getWidth() / 2,
					main_menu.getHeight() / 6, 0, menuAxis[i][0],
					menuAxis[i][1], 0);
		}
	}
	
	/*�ͷ�MainMenuͼƬ*/
	public void clearMainMenu() {
		Resource.freeImage(Resource.id_main_bg);
		Resource.freeImage(Resource.id_main_menu);
	}
	
	/*�ͷ���Ϸ�е�ͼƬ*/
	public void clearGamePlaying(){
		Resource.freeImage(Resource.id_playing_menu);
		Resource.freeImage(Resource.id_playing_cloudbig);
		Resource.freeImage(Resource.id_playing_cloudbig);
		Resource.freeImage(Resource.id_playing_cloudsmall);
		Resource.freeImage(Resource.id_playing_lawn);
		Resource.freeImage(Resource.id_playing_step);
		Resource.freeImage(Resource.id_playing_tree);
		Resource.freeImage(Resource.id_game_bg);
		Resource.freeImage(Resource.id_playing_lunzi);
		Resource.freeImage(Resource.id_playing_shenzi);
		Resource.freeImage(Resource.id_playing_lift);
		Resource.freeImage(Resource.id_playing_shenzi1);
		Resource.freeImage(Resource.id_playing_prop_memu);
		Resource.freeImage(Resource.id_playing_prop);
		Resource.freeImage(Resource.id_playing_stop);   //��Ϸ��ͣ��ť
		Resource.freeImage(Resource.id_playing_sheep);   
		Resource.freeImage(Resource.id_sheep_eye);   
		Resource.freeImage(Resource.id_sheep_hand);   
	}
	
	/*���̳ǽ���*/
	public void clearShop() {
		Resource.freeImage(Resource.id_shop);
		Resource.freeImage(Resource.id_shop_balance);
		Resource.freeImage(Resource.id_shop_big);
		Resource.freeImage(Resource.id_shop_figure);
		Resource.freeImage(Resource.id_shop_balance);
		Resource.freeImage(Resource.id_shop_go_pay);
		Resource.freeImage(Resource.id_shop_midding);
		Resource.freeImage(Resource.id_shop_out);
		Resource.freeImage(Resource.id_price_quantity);
		Resource.freeImage(Resource.id_playing_prop);        //��ƷͼƬ
	}
	
	/*�� �ɾ�ϵͳ����*/
    public void clearGameArchi(){
    	Resource.freeImage(Resource.id_game_bg);
    	Resource.freeImage(Resource.id_shop_midding);
    	Resource.freeImage(Resource.id_shop_big);
    	Resource.freeImage(Resource.id_shop_go_pay);
    	Resource.freeImage(Resource.id_achievement);
    	Resource.freeImage(Resource.id_achievement_left_right);
    	Resource.freeImage(Resource.id_achievement_left_right1);
    	Resource.freeImage(Resource.id_achievement_long);
    	Resource.freeImage(Resource.id_achievement_long1);
    	Resource.freeImage(Resource.id_achievement_out1);
    	Resource.freeImage(Resource.id_achievement_points);
    	Resource.freeImage(Resource.id_archivement_hoof);
    	Resource.freeImage(Resource.id_archivement_hoof1);
    }
    
    /*�������ϵͳ����*/
    public void clearRanking() {
    	Resource.freeImage(Resource.id_game_bg);
    	Resource.freeImage(Resource.id_achievement_out1);
    	Resource.freeImage(Resource.id_shop_big);
    	Resource.freeImage(Resource.id_achievement_left_right);
    	Resource.freeImage(Resource.id_current_ranking);
    	Resource.freeImage(Resource.id_ranking_option);
    	Resource.freeImage(Resource.id_ranking_option1);
    	Resource.freeImage(Resource.id_ranking_stripe);
    	Resource.freeImage(Resource.id_ranking);
    	Resource.freeImage(Resource.id_ranking_show);
	}
    
    /*�����������*/
    public void clearHelp() {
    	Resource.freeImage(Resource.id_game_bg);
    	Resource.freeImage(Resource.id_achievement_out1);
    	Resource.freeImage(Resource.id_shop_big);
    	Resource.freeImage(Resource.id_game_help);
	}
    
//    private int index, flag;
    public CreateRole createRole;
	public void drawGamePlaying(Graphics g, int index, Role own) {
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image playing_menu = Resource.loadImage(Resource.id_playing_menu);// {491,0}
		Image playing_cloudbig = Resource.loadImage(Resource.id_playing_cloudbig);
		Image playing_cloudsmall = Resource.loadImage(Resource.id_playing_cloudsmall);// {404,164}
		Image playing_lawn = Resource.loadImage(Resource.id_playing_lawn);// {0,499}
		Image playing_step = Resource.loadImage(Resource.id_playing_step);// {377,153},{377,240}{377,324}{377,409} Y ���89
		Image playing_tree = Resource.loadImage(Resource.id_playing_tree);// {0,72}
		Image playing_lunzi = Resource.loadImage(Resource.id_playing_lunzi);//{374,132}
		Image playing_shenzi = Resource.loadImage(Resource.id_playing_shenzi); //{379,154}
		Image playing_lift = Resource.loadImage(Resource.id_playing_lift); //{342,303}
		Image playing_shenzi1 = Resource.loadImage(Resource.id_playing_shenzi1); //{399, 135}//��ŵ�����
		Image wolf = Resource.loadImage(Resource.id_wolf_run); //{399, 135}
		Image playing_sheep = Resource.loadImage(Resource.id_playing_sheep); //{399, 135}
		Image sheep_eye = Resource.loadImage(Resource.id_sheep_eye);
		Image sheep_hand = Resource.loadImage(Resource.id_sheep_hand);
		Image playing_prop_memu = Resource.loadImage(Resource.id_playing_prop_memu); //{497,192}{564,192}//�������70
		Image playing_stop = Resource.loadImage(Resource.id_playing_stop); //{501,466}
		g.drawImage(game_bg, 0, 0, TopLeft);
		
		if(tempx+playing_cloudbig.getWidth()>0){
			tempx -= 1;
		}else{
			tempy = RandomValue.getRandInt(0, 114);
			tempx = ScrW;
		}
		g.drawRegion(playing_cloudbig, 0, 0, playing_cloudbig.getWidth(), playing_cloudbig.getHeight(), 
				0, tempx, tempy, TopLeft);
		
		if(tempx2+playing_cloudsmall.getWidth()>0){
			tempx2 -= 2;
		}else{
			tempy2 = RandomValue.getRandInt(0, 114);
			tempx2 = ScrW;
		}
		g.drawRegion(playing_cloudsmall, 0, 0, playing_cloudsmall.getWidth(), playing_cloudsmall.getHeight(), 
				0, tempx2, tempy2, TopLeft);
		g.drawImage(playing_lawn, 0, 499, TopLeft);
		g.drawImage(playing_tree, 0, 72, TopLeft);
		g.drawImage(playing_shenzi1, 399, 135, TopLeft);
		for(int i=0;i<4;i++){   //����
			g.drawImage(playing_step, 377, 153+i*89, TopLeft);
		}
		g.drawRegion(playing_shenzi, 0, 0, playing_shenzi.getWidth(), (own.mapy-154),        //�����ƶ�������
				0, 379, 154, TopLeft);                                                        //��ֱ���� �������� 154
		createRole=new CreateRole();                                                           //��������ʵ����
		createRole.showSheep(g,sheep_hand,playing_sheep,sheep_eye,own);                        //��̬����

		g.drawRegion(playing_lift, 0, 0, playing_lift.getWidth(), playing_lift.getHeight(),     //��ĵ���
				0, 342, 154+(own.mapy-154), TopLeft);
		
		g.drawImage(playing_lunzi, 374,132, TopLeft);
		g.drawImage(playing_menu, 491, 0, TopLeft);
		for(int i=0;i<4;i++){                                                                //��Ϸ�е���������---��������
			g.drawImage(playing_prop_memu, 497,185+i*68, TopLeft);
			drawProp(g, i, 497+5,185+i*(68+3));                                              //��һ�ж�ӦԭͼƬ�е�ǰ�ĸ�
			drawNum(g, i+1, 540+7, 223-17+i*73);//��ʾ���ܰ�����1-4{540,223}
			
			g.drawImage(playing_prop_memu, 564,185+i*68, TopLeft);
			drawProp(g, i+4, 564+5,185+i*(68+2));  //�ڶ��ж�ӦԭͼƬ�еĺ��ĸ�
			drawNum(g, i+4+1, 612, 223-17+i*73);//��ʾ���ܼ�5-8{}
		}
		g.drawImage(playing_stop, 500,459, TopLeft);//��ͣ��Ϸ��ť
		
//		createRole.showWolf(g,index,flag);
//		if(flag>2){    //�ǵ�Ч��ͼ
//			index = (index+1)%6;
//			flag=0;
//		}else{
//			flag++;
//		}
//		g.drawRegion(wolf, index*wolf.getWidth()/6, 0, wolf.getWidth()/6, wolf.getHeight(), 0, 50, 50, TopLeft);
	}
	
	/*���̵����*/
	public void drawGameShop(Graphics g,int shopX,int shopY) {
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image shop_balance = Resource.loadImage(Resource.id_shop_balance);//{46,454}
		Image shop_big = Resource.loadImage(Resource.id_shop_big);//{29,103}
		//Image shop_figure = Resource.loadImage(Resource.id_shop_figure);//{103,452}//TODO
		Image shop_go_pay = Resource.loadImage(Resource.id_shop_go_pay);//{457,381}
		Image shop_midding = Resource.loadImage(Resource.id_shop_midding);//{434,103}
		Image shop_out_base = Resource.loadImage(Resource.id_shop_out_base);//TODO
		Image shop_out = Resource.loadImage(Resource.id_shop_out);//{457,429}
		Image shop_small_base = Resource.loadImage(Resource.id_shop_small_base);//TODO
		Image shop_small = Resource.loadImage(Resource.id_shop_small);
		Image price_quantity = Resource.loadImage(Resource.id_price_quantity);
		Image shop = Resource.loadImage(Resource.id_shop);//{217,18}
		Image playing_prop=Resource.loadImage(Resource.id_playing_prop);
		//{42,115},{233,115},{42,195},{233,195},{42,279},{233,279},{42,361},{233,361}
		g.drawImage(game_bg, 0, 0, TopLeft);
		g.drawImage(shop, 217, 18, TopLeft);
		g.drawImage(shop_big, 29, 103, TopLeft);
		g.drawImage(shop_balance, 46, 454, TopLeft);
	
		int x =42, y = 120, spaceX = 15, spaceY = 8;//
		for(int i=0;i<4;i++){
			for(int j=0;j<2;j++){
				g.drawRegion(shop_small_base, 0, 0, shop_small_base.getWidth(), shop_small_base.getHeight(),
						0, x+(spaceX+shop_small_base.getWidth())*j, y+(spaceY+shop_small_base.getHeight())*i, TopLeft);
			}
		}
		g.drawImage(shop_midding, 434, 103, TopLeft);
		for(int i=0;i<2;i++){             //midding�µİ�ť��Ӱ
			g.drawRegion(shop_out_base, 0, 0, shop_out_base.getWidth(), shop_out_base.getHeight(), 
					0, 457, 381+(spaceY+shop_out_base.getHeight())*i, TopLeft);
			}
		int mapx=37,mapy=112;       //��ѡ�к������ӰЧ��������
		 for(int i=0;i<4;i++){
		     for(int j=0;j<2;j++){
				if(shopX==j && shopY==i){
					g.drawRegion(shop_small, 0, 0, shop_small.getWidth(), shop_small.getHeight(),
							0, mapx+(spaceX+shop_small.getWidth())*j, mapy+(spaceY+shop_small.getHeight())*i, TopLeft);
					g.drawImage(price_quantity, mapx+(spaceX+shop_small.getWidth())*j+65, 
							mapy+(spaceY+shop_small.getHeight())*i+12, TopLeft);
					g.drawRegion(playing_prop, getIndex(j, i)*playing_prop.getWidth()/8, 0, playing_prop.getWidth()/8, playing_prop.getHeight(), 0,
							mapx+(spaceX+shop_small.getWidth())*j+8, mapy+(spaceY+shop_small.getHeight())*i+9, TopLeft);
					drawNum(g, 111, mapx+(spaceX+shop_small.getWidth())*j+119, mapy+(spaceY+shop_small.getHeight())*i+11);
					drawNum(g, 333, mapx+(spaceX+shop_small.getWidth())*j+119, mapy+(spaceY+shop_small.getHeight())*i+36);
				}else{
					g.drawRegion(shop_small, 0, 0, shop_small.getWidth(), shop_small.getHeight(), 0,
							x+(spaceX+shop_small.getWidth())*j, y+(spaceY+shop_small.getHeight())*i, TopLeft);
					g.drawImage(price_quantity, x+(spaceX+shop_small.getWidth())*j+65, 
							y+(spaceY+shop_small.getHeight())*i+12, TopLeft);
					g.drawRegion(playing_prop, getIndex(j, i)*playing_prop.getWidth()/8, 0, playing_prop.getWidth()/8, playing_prop.getHeight(), 0,
							x+(spaceX+shop_small.getWidth())*j+8, y+(spaceY+shop_small.getHeight())*i+9, TopLeft);
					drawNum(g, 111, x+(spaceX+shop_small.getWidth())*j+119, y+(spaceY+shop_small.getHeight())*i+11);
					drawNum(g, 333, x+(spaceX+shop_small.getWidth())*j+119, y+(spaceY+shop_small.getHeight())*i+36);
				}
			}
		}
		 if(shopX==2){          //��ֵ�ͷ��ر�ѡ�����ӰЧ��
			 if(shopY==0){    //���Ʒ��������ҵ���ڷ���
				 g.drawImage(shop_go_pay, 457-8, 381-5, TopLeft);
			   	 g.drawImage(shop_out, 457, 429, TopLeft);
			  }else{
				 g.drawImage(shop_go_pay, 457, 381, TopLeft);
			   	 g.drawImage(shop_out, 457-8, 429-5, TopLeft);
			 }
		    }else{
		    	g.drawImage(shop_go_pay, 457, 381, TopLeft);
		   		g.drawImage(shop_out, 457, 429, TopLeft);
		    }
		drawNum(g, 10, 103,452);                                          //TODO �������
	}
	
	private int getIndex(int x, int y){    //ȡ����Ӧ�̵��̳�����λ��
		if(y==0 && x==0)return 0;
		if(y==1 && x==0)return 1;
		if(y==2 && x==0)return 2;
		if(y==3 && x==0)return 3;
		if(y==0 && x==1)return 4;
		if(y==1 && x==1)return 5;
		if(y==2 && x==1)return 6;
		if(y==3 && x==1)return 7;
		if(x==2)        return 8;
		return -1;
	}
	
	/*�����ɾ�ϵͳ*/
	public void drawGameArchi(Graphics g, int archiIndex) {
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image shop_midding = Resource.loadImage(Resource.id_shop_midding);//{28,102}
		Image shop_big = Resource.loadImage(Resource.id_shop_big);//{235,102}
		Image shop_go_pay = Resource.loadImage(Resource.id_shop_go_pay);//{457,381}//{51,123},{51,178},{51,232},{51,286},{51,342},{51,396}
		Image achievement = Resource.loadImage(Resource.id_achievement);//{270,19}
		Image achievement_left_right = Resource.loadImage(Resource.id_achievement_left_right);//{458,441}
		Image achievement_left_right1 = Resource.loadImage(Resource.id_achievement_left_right1);
		Image achievement_long = Resource.loadImage(Resource.id_achievement_long);//{247,114},{247,198},{247,277},{247,361},{}
		Image achievement_long1 = Resource.loadImage(Resource.id_achievement_long1);
		Image achievement_out1 = Resource.loadImage(Resource.id_achievement_out1);//{55,451}
		Image achievement_points = Resource.loadImage(Resource.id_achievement_points);//{250,448}
		Image archivement_hoof = Resource.loadImage(Resource.id_archivement_hoof);//{539,130},{539,211},{539,293},{539,378}
		Image archivement_hoof1 = Resource.loadImage(Resource.id_archivement_hoof1);
		g.drawImage(game_bg, 0, 0, TopLeft);
		g.drawImage(achievement, 270, 19, TopLeft);
		g.drawImage(shop_midding, 28, 102, TopLeft);
		g.drawImage(achievement_out1, 55, 451, TopLeft);
		for(int i=0;i<6;i++){  //�ɾ������Ŀ
			g.drawImage(shop_go_pay, 51, 123+i*55, TopLeft);//Y�������55
		}
		g.drawImage(shop_big, 235, 102, TopLeft);
		g.drawImage(achievement_points, 250, 448, TopLeft);
		g.drawImage(achievement_left_right,458,441, TopLeft);
		for(int i=0;i<4;i++){//�ɾ��Ҳ���Ŀ
			g.drawImage(achievement_long1, 247, 114+i*83, TopLeft);//Y�������83
			g.drawImage(archivement_hoof1, 539, 130+i*83, TopLeft);
		}
	}
	
	/*�������а�*/
	public void showRanking(Graphics g, int rankingIndex) {
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image achievement_out1 = Resource.loadImage(Resource.id_achievement_out1);//{61,462}
		Image shop_big = Resource.loadImage(Resource.id_shop_big);//{233,101}
		Image achievement_left_right = Resource.loadImage(Resource.id_achievement_left_right);//{457,440}
		Image current_ranking=Resource.loadImage(Resource.id_current_ranking);//{253,448}
		Image ranking_option=Resource.loadImage(Resource.id_ranking_option);//{39,112} Y���54 
		Image ranking_option1=Resource.loadImage(Resource.id_ranking_option1);
		Image ranking_stripe=Resource.loadImage(Resource.id_ranking_stripe);//{241,151}  ���߶�57
		Image ranking=Resource.loadImage(Resource.id_ranking);//{232,18}
		Image ranking_show=Resource.loadImage(Resource.id_ranking_show);//{241,108}
		g.drawImage(game_bg, 0, 0, TopLeft);
		g.drawImage(achievement_out1, 61,462, TopLeft);
		for(int i=0;i<3;i++){//���������Ŀ
			g.drawImage(ranking_option, 39, 112+i*54, TopLeft);
		}
		g.drawImage(shop_big, 233,101, TopLeft);
		g.drawImage(ranking_show,241,108, TopLeft);
		for(int i=0;i<5;i++){
			g.drawImage(ranking_stripe,241,151+i*57, TopLeft);
		}
		g.drawImage(current_ranking, 253,448, TopLeft);
		g.drawImage(ranking, 232,18, TopLeft);
		g.drawImage(achievement_left_right, 457,440, TopLeft);
	}
	
	/*������������*/
	private String gameIntro[]={
			"������˵����",
			"���·������������ҵ��ƶ���",
			"ȷ������������ڻ��޵�ȭ�ס�",
			"���ּ�1��8��ʹ�õ��ߡ�",
			"���ּ�0���˳���Ϸ��",
			"���ּ�9:��Ϸ������",
			"",
			"������˵����",
			"ʱ�����ӣ�ʱ�侲ֹ10�롣",
			"����������������ӵ�������̫�Ǿͻ��ſ�һ�����������ڵĻ�̫�Ƕ�����䡣",
			"������װ��������õ�5����޵�Ч�����������ֹ�����",
			"���ǹⲨ������һ��ʮ����صĵ��������������Ļ�̫�ǣ�����5�롣",
			"������ż������һ������",
			"��ɢ���٣�ʹ�ú�������е����ӻ���������ʯͷ�Ļ�̫�ǡ�",
			"�ٶ�����Һ��ʹ�ú�����ϲ������ƶ��ٶȣ�����30�롣",
			"ǿ����ʯ���������п��еĻ�̫�ǡ�",
			"",
			"����Ϸ��顿",
			"ϲ�����ս��̫����һ�������Ϸ���ܹ���15�ء���ҿ���ϲ�������һ�������Ļ�",
			"̫�Ǽ��ɹ��ء�������һ������ڵ����̳��ڹ�����ֵ�������ø���Ȥ�����顣���˴���",
			"�⣬��Ϸ�л��Ƴ��˳ɾ�ϵͳ�����а��������������Ϸ�Ĺ����ж�����Ŀ�ꡣ",
			"",
	};
	public void showHelp(Graphics g,int helpIndex) {
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image shop_big = Resource.loadImage(Resource.id_shop_big);       //{137,108}
		Image game_help = Resource.loadImage(Resource.id_game_help);     //{214,18}
		Image achievement_out1 = Resource.loadImage(Resource.id_achievement_out1);   //{17,498}
		g.drawImage(game_bg, 0, 0, TopLeft);
		g.drawImage(shop_big, 137, 108, TopLeft);
		g.drawImage(game_help, 214,18, TopLeft);
		g.drawImage(achievement_out1, 17,498, TopLeft);
		g.setColor(0xffffff);
		String info="";
		for(int i=0;i<gameIntro.length;i++){
			info += gameIntro[i];
		}
		TextView.showMultiLineText(g, info, 10, 150, 130, 350, 350);
	}
	
	/*��Ϸ�е�����*/
	private void drawNum(Graphics g, int num, int x, int y) {
		Image imgNumeber = Resource.loadImage(Resource.id_shop_figure);
		String number = String.valueOf(num);
		for (byte i = 0; i < number.length(); i++) {
			g.drawRegion(imgNumeber, (number.charAt(i) - '0') * imgNumeber.getWidth()/10, 0, 
					imgNumeber.getWidth()/10, imgNumeber.getHeight(), 0, x + i * (imgNumeber.getWidth()/10 + 1), y, 0);
		}
	}
	
	/*���ߵ�ͼƬ����----����ת��ΪͼƬ*/
	private void drawProp(Graphics g,int num,int x,int y){
		Image playing_prop=Resource.loadImage(Resource.id_playing_prop);
		String number=String.valueOf(num);
		for(byte i=0;i<number.length();i++){
			g.drawRegion(playing_prop, (number.charAt(i) - '0')* playing_prop.getWidth()/8, 0, playing_prop.getWidth()/8,
					playing_prop.getHeight(), 0, x+i * (playing_prop.getWidth()/8 + 1), y, 0);
		}
	}
}
