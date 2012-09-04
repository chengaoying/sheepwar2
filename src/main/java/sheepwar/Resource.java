package sheepwar;

import java.io.IOException;

import javax.microedition.lcdui.Image;

/**
 * ͼƬ���غ� �ͷ�
 * @author Administrator
 *
 */
public class Resource implements Common {
	
	
	private static short NUMS = 0;
	
	public static short id_main_bg = NUMS++;
	public static short id_main_menu = NUMS++;
	
	public static short id_playing_menu = NUMS++;//��Ϸ�� �Ľ���
	public static short id_playing_cloudbig = NUMS++;
	public static short id_playing_cloudsmall = NUMS++;
	public static short id_playing_lawn = NUMS++;
	public static short id_playing_step = NUMS++;
	public static short id_playing_tree = NUMS++;
	public static short id_playing_stop = NUMS++;
	public static short id_game_bg = NUMS++;
	public static short id_playing_prop_memu = NUMS++;
	public static short id_playing_prop = NUMS++;      //���˵���Ʒ��
	public static short id_playing_lunzi = NUMS++;     //����
	public static short id_playing_shenzi = NUMS++;    //����
	public static short id_playing_lift = NUMS++;      //��ĵ���
	public static short id_playing_shenzi1 = NUMS++;
	public static short id_playing_sheep = NUMS++;
	public static short id_sheep_eye = NUMS++;
	public static short id_sheep_hand = NUMS++;
	public static short id_bomb = NUMS++;
	public static short id_wolf_run = NUMS++;
	public static short id_wolf_down = NUMS++;
	public static short id_balloon_blue = NUMS++;      
	public static short id_balloon_green = NUMS++;
	public static short id_balloon_multicolour = NUMS++;
	public static short id_balloon_red = NUMS++;
	public static short id_balloon_yellow = NUMS++;
	public static short id_balloon_yellowred = NUMS++;
	public static short id_ladder = NUMS++;
	
	
	public static short id_shop_balance = NUMS++;//�̳ǽ���
	public static short id_shop_big = NUMS++;
	public static short id_shop_figure = NUMS++;
	public static short id_shop_go_pay = NUMS++;
	public static short id_shop_midding = NUMS++;
	public static short id_shop_out_base = NUMS++;
	public static short id_shop_out = NUMS++;
	public static short id_shop_small_base = NUMS++;
	public static short id_shop_small = NUMS++;
	public static short id_price_quantity = NUMS++;
	public static short id_shop = NUMS++;
	
	public static short id_achievement = NUMS++;//�ɾ�ϵͳ
	public static short id_achievement_left_right = NUMS++;
	public static short id_achievement_left_right1 = NUMS++;
	public static short id_achievement_long = NUMS++;
	public static short id_achievement_long1 = NUMS++;
	public static short id_achievement_out1 = NUMS++;
	public static short id_achievement_points = NUMS++;
	public static short id_archivement_hoof = NUMS++;
	public static short id_archivement_hoof1 = NUMS++;
	public static short id_achievement_word = NUMS++;
	public static short id_achievement_left = NUMS++;
	public static short id_slash = NUMS++;				//�ֺţ�б��
	
	public static short id_current_ranking = NUMS++;//���н���
	public static short id_ranking_option = NUMS++;
	public static short id_ranking_option1 = NUMS++;
	public static short id_ranking_stripe = NUMS++;
	public static short id_ranking = NUMS++;
	public static short id_ranking_show = NUMS++;
	public static short id_ranking_word = NUMS++;			//��������
	
	
	public static short id_game_help = NUMS++;     //��Ϸ����
	
	public static String[] imagesrcs = {
		"/main_bg.jpg",
		"/main_menu.png",
		
		"/playing_menu.png",             //��Ϸ�е� ����
		"/playing_cloudbig.png",
		"/playing_cloudsmall.png",
		"/playing_lawn.png",
		"/playing_step.png",
		"/playing_tree.png",
		"/playing_stop.png",
		"/game_bg.jpg",
		"/playing_prop_memu.png",
		"/playing_prop.png",
		"/playing_lunzi.png",//���ӡ�΢�ֻ������Ի
		"/playing_shenzi.png",
		"/playing_lift.png",     //��ĵ���
		"/playing_shenzi1.png",
		"/playing_sheep.png",
		"/sheep_eye.png",
		"/sheep_hand.png",
		"/bomb.png",
		"/wolf_run.png",
		"/wolf_down.png",
		"/balloon_blue.png",
		"/balloon_green.png",
		"/balloon_multicolour.png",
		"/balloon_red.png",
		"/balloon_yellow.png",
		"/balloon_yellowred.png",
		"/ladder.png",
		
		"/shop_balance.png",            //�̳ǽ���ͼƬ��Դ
		"/shop_big.png",
		"/shop_figure.png",
		"/shop_go_pay.png",
		"/shop_midding.png",
		"/shop_out_base.png",
		"/shop_out.png",
		"/shop_small_base.png",
		"/shop_small.png",
		"/price_quantity.png",
		"/shop.png",
		
		"/achievement.png",             //�ɾ�ϵͳͼƬ--big midding����
		"/achievement_left_right.png",
		"/achievement_left_right1.png",
		"/achievement_long.png",
		"/achievement_long1.png",
		"/achievement_out1.png",
		"/achievement_points.png",
		"/archivement_hoof.png",
		"/archivement_hoof1.png",
		"/achievement_word.png",
		"/achievement_left.png",
		"/slash.png",
		
		"/current_ranking.jpg",       //����ͼƬ��Դ
		"/ranking_option.png",
		"/ranking_option1.png",
		"/ranking_stripe.jpg",        //����ͼƬ
		"/ranking.png",
		"/ranking_show.jpg",
		"/ranking_word.png",
		
		
		"/game_help.png",              //��Ϸ����
	};
	
	private static final Image[] images = new Image[NUMS];

	public static Image loadImage(int id){
		if(images[id]==null){
			try {
				images[id] = Image.createImage(imagesrcs[id]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return images[id];
	}
	
	/*�ͷ�ͼƬ*/
	public static void freeImage(int id){
		images[id] = null;
	}
	
	/**
	 * ��Ϸ�������ֽ���
	 */
	public static String gameInfo[] = {   
		"������˵����#r���·������������ҵ��ƶ���#rȷ������������ڻ��޵�ȭ�ס�#r���ּ�1��8��ʹ�õ��ߡ�#r���ּ�0���˳���Ϸ��#r" +
		"���ּ�9:��Ϸ������",
		
		"������˵����#r�����ӣ�ʱ�侲ֹ10�롣#r����������������ӵ�������̫�Ǿͻ��ſ�һ������#r�����ڵĻ�̫�Ƕ�����䡣#r������װ��������õ�5����޵�Ч�����������ֹ�����#r���ǹⲨ:����һ��ʮ����صĵ��������������Ļ�̫��,����5�롣#r������ż������һ������#r��ɢ���٣�ʹ�ú�������е����ӻ���������ʯͷ�Ļ�̫�ǡ�#r�ٶ�����Һ��ʹ�ú�����ϲ������ƶ��ٶȣ�����30�롣#rǿ����ʯ���������п��еĻ�̫�ǡ�",
		
		"����Ϸ��顿#rϲ�����ս��̫����һ�������Ϸ��#r�ܹ���15�ء���ҿ���ϲ�������һ�������Ļ�̫�Ǽ��ɹ��ء�#r������һ������ڵ����̳��ڹ�����ֵ�������ø���Ȥ�����顣#r���˴����⣬��Ϸ�л��Ƴ��˳ɾ�ϵͳ�����а�#r�������������Ϸ�Ĺ����ж�����Ŀ�ꡣ",
		"",
	};

}
