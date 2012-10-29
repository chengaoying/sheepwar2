package sheepwar;

import java.io.IOException;

import javax.microedition.lcdui.Image;

/**
 * ͼƬ��Դ��������Ϣ��
 * @author Administrator
 *
 */
public class Resource implements Common {
	
	
	private static short NUMS = 0;
	
	public static short id_main_bg = NUMS++;
	public static short id_main_menu = NUMS++;
	
	public static short id_playing_menu = NUMS++;
	public static short id_playing_cloudbig = NUMS++;
	public static short id_playing_cloudsmall = NUMS++;
	public static short id_playing_lawn = NUMS++;
	public static short id_playing_step = NUMS++;
	public static short id_playing_tree = NUMS++;
	public static short id_playing_stop = NUMS++;
	public static short id_game_bg = NUMS++;
	public static short id_playing_prop_memu = NUMS++;
	public static short id_playing_prop = NUMS++;      
	public static short id_playing_lunzi = NUMS++;    
	public static short id_playing_shenzi = NUMS++;   
	public static short id_playing_lift = NUMS++;     
	public static short id_playing_shenzi1 = NUMS++;
	public static short id_playing_sheep = NUMS++;
	public static short id_sheep_eye = NUMS++;
	public static short id_sheep_hand = NUMS++;
	public static short id_bomb = NUMS++;
	public static short id_wolf_run = NUMS++;
	public static short id_wolf_down = NUMS++;
	public static short id_wolf_climb = NUMS++;
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
	public static short id_slash = NUMS++;				
	
	public static short id_current_ranking = NUMS++;//���н���
	public static short id_ranking_option = NUMS++;
	public static short id_ranking_option1 = NUMS++;
	public static short id_ranking_stripe = NUMS++;
	public static short id_ranking = NUMS++;
	public static short id_ranking_show = NUMS++;
	public static short id_ranking_word = NUMS++;			//��������
	
	public static short id_playing_level= NUMS++;			
	public static short id_playing_level2= NUMS++;			
	public static short id_playing_point = NUMS++;			
	public static short id_sheep_head = NUMS++;			
	public static short id_wolf_head = NUMS++;			
	public static short id_multiply = NUMS++;	
	
	public static short id_game_help = NUMS++;     //��Ϸ����
	
	public static short id_logo = NUMS++;    
	public static short id_pass_cloud = NUMS++;    
	public static short id_pass_cloud1 = NUMS++;    
	public static short id_pass_cloud2 = NUMS++;    
	public static short id_pass_num = NUMS++;    
	public static short id_pass_rainbow = NUMS++;    
	public static short id_pass_score = NUMS++;    
	public static short id_pass_star = NUMS++;    
	public static short id_pass_star2 = NUMS++;    
	public static short id_net = NUMS++;    
	public static short id_net2 = NUMS++;    
	public static short id_boom = NUMS++;    
	public static short id_boom1 = NUMS++;    
	public static short id_prop_3 = NUMS++;    
	public static short id_prop_4 = NUMS++;    
	public static short id_prop_4_effect = NUMS++;    
	public static short id_prop_5_effect = NUMS++;    
	public static short id_prop_7_effect = NUMS++;    
	public static short id_cloud1 = NUMS++;    
	public static short id_apple = NUMS++;    
	public static short id_lemon = NUMS++;    
	public static short id_orange = NUMS++;    
	public static short id_watermelon = NUMS++;    
	public static short id_red_wolf = NUMS++;    
	public static short id_game_result = NUMS++;    
	public static short id_game_return = NUMS++;    
	public static short id_sub_menu_bg = NUMS++;    
	public static short id_sub_menu = NUMS++;    
	public static short id_prop_fist = NUMS++;    
	public static short id_prop_fist_effect = NUMS++;    
	public static short id_pumpkin = NUMS++;    
	public static short id_burn = NUMS++;    				
	public static short id_gloveLeft = NUMS++;    				
	public static short id_gloveRight = NUMS++;    				
	public static short id_main_select_right_base = NUMS++;    				
	public static short id_main_select_left_base = NUMS++;     				
	public static short id_shop_bottom = NUMS++;     				
	public static short id_achievement_bottom = NUMS++;     				
	public static short id_rank_bottom = NUMS++;     				
	public static short id_control = NUMS++;     				
	public static short id_wolf_die = NUMS++;     				
	public static short id_wolf_shove = NUMS++;     				
	public static short id_shop_selected = NUMS++;     				
	public static short id_return_selected = NUMS++;     				
	public static short id_prop_2_eff = NUMS++;     				
	public static short id_prop = NUMS++;     				
	public static short id_game_stop = NUMS++;     				
	public static short id_teach_level = NUMS++;     				
	public static short id_arrowhead = NUMS++;     				
	
	
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
		"/prop1.png",						//�޸Ĺ��ĵ���ͼƬ
		"/playing_lunzi.png",
		"/playing_shenzi.png",
		"/playing_lift.png",    
		"/playing_shenzi1.png",
		"/playing_sheep.png",
		"/sheep_eye.png",
		"/sheep_hand.png",
		"/bomb.png",
		"/wolf_run.png",
		"/wolf_down.png",
		"/wolf_cilmb.png",
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
		
		"/current_ranking.png",       //����ͼƬ��Դ
		"/ranking_option.png",
		"/ranking_option1.png",
		"/ranking_stripe.jpg",       
		"/ranking.png",
		"/show.png",								//�滻��ranking_show.jpg
		"/ranking_word.png",
		
		"/playing_level.png",
		"/playing_level2.png",
		"/playing_point.png",
		"/sheep_head.png",
		"/wolf_head.png",
		"/multiply.png",
		
		"/game_help.png",              //��Ϸ����
		
		"/logo.png",             
		"/pass_cloud.png",             
		"/pass_cloud1.png",             
		"/pass_cloud2.png",             
		"/pass_num.png",             
		"/pass_rainbow.png",             
		"/pass_score.png",             
		"/pass_star.png",             
		"/pass_star1.png",             
		"/net.png",             
		"/net_effect.png",             
		"/boom.png",             
		"/boom1.png",             
		"/prop_3.png",             
		"/prop_4.png",             
		"/prop_4_effect.png",             
		"/prop_5_effect.png",             
		"/prop_7_effect.png",             
		"/cloud1.png",             
		"/apple.png",             
		"/lemon.png",             
		"/orange.png",             
		"/watermelon.png",             
		"/red_wolf.png",             
		"/game_result.png",             
		"/game_return.png",             
		"/submenu_bg.png",             
		"/submenu.png",             
		"/prop_fist.png",             
		"/prop_fist_effect.png",             
		"/pumpkin.png",             
		"/burn.png",             
		"/gloveLeft.png",             
		"/gloveRight.png",             
		"/main_select_right_base.png",             
		"/main_select_left_base.png",             
		"/shop_bottom.jpg",             
		"/achievement_bottom.jpg",             
		"/ranking_bottom.jpg",             
		"/control.png",             
		"/die.png",             
		"/shove.png",             
		"/shop_selected.png",             
		"/return_selected.png",             
		"/prop_2_effect.png",             
		"/prop.png",             
		"/stop.png",             
		"/teach_level.png",             
		"/arrowhead.png",             
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
		
		"������˵����#r�����ӣ�ʱ�侲ֹ10�롣#r����������������ӵ�������̫�Ǿͻ��ſ�һ�����������ڵĻ�̫�Ƕ�����䡣#r������װ����������5����޵�Ч�����������ֹ�����#r���ǹⲨ:����һ��ʮ����صĵ��������������Ļ�̫��,����5�롣#r������ż������һ������#r�������٣�ʹ�ú�������е����ӻ���������ʯͷ�Ļ�̫�ǡ�#r����:#rʹ�ú����������Ŀ��ӵ���������Ч��#r�۸�30��Ϸ�ҡ�#rǿ����ʯ���������п��еĻ�̫�ǡ�",
		
		"����Ϸ��顿#rϲ�����ս��̫����һ�������Ϸ��#r�ܹ���15�ء���ҿ���ϲ�������һ�������Ļ�̫�Ǽ��ɹ��ء�#r������һ������ڵ����̳��ڹ�����ֵ�������ø���Ȥ�����顣#r���˴����⣬��Ϸ�л��Ƴ��˳ɾ�ϵͳ�����а�#r�������������Ϸ�Ĺ����ж�����Ŀ�ꡣ",
		"",
	};
	
	/*�̳���Ʒ����*/    	//��ά���鴴��ע��
	public static String propIntroduce [][]= {
		{"ʱ������:#rʱ�侲ֹ10�롣#r �۸�20��Ϸ��","��������:#rʹ�ú�������������ϻ����������ϹϵĻ�̫�ǡ�#r�۸�30��Ϸ��"},//shopY
		{"������:#r��������ӵ�������̫�Ǿͻ��ſ�һ������#r�����ڵĻ�̫�Ƕ�����䡣#rֻ���������Ļ�̫�ǡ�#r�۸�20��Ϸ��","����:#rʹ�ú����������Ŀ��ӵ���������Ч��#r�۸�30��Ϸ��"},
		{"������װ:#r������õ�5����޵�Ч#r�����������ֹ�����#r�۸�30��Ϸ��","ǿ����ʯ:#r�������п��еĻ�̫�ǡ�#r�۸�50��Ϸ��"},
		{"���ǹⲨ:#r����һ��ʮ����صĵ��������������Ļ�̫�ǣ�����5�롣#r�۸�30��Ϸ��","������ż:#r����һ������#r�۸�50��Ϸ��"},
	};

}
