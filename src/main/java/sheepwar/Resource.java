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
	
	public static short id_shop_balance = NUMS++;//�̳ǽ���
	public static short id_shop_big = NUMS++;
	public static short id_shop_figure = NUMS++;
	public static short id_shop_go_pay = NUMS++;
	public static short id_shop_midding = NUMS++;
	public static short id_shop_out_base = NUMS++;
	public static short id_shop_out = NUMS++;
	public static short id_shop_small_base = NUMS++;
	public static short id_shop_small = NUMS++;
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
	
	public static short id_current_ranking = NUMS++;//���н���
	public static short id_ranking_option = NUMS++;
	public static short id_ranking_option1 = NUMS++;
	public static short id_ranking_stripe = NUMS++;
	public static short id_ranking = NUMS++;
	public static short id_ranking_show = NUMS++;
	public static short id_wolf_run = NUMS++;
	
	public static String[] imagesrcs = {
		"/main_bg.jpg",
		"/main_menu.png",
		
		"/playing_menu.png",//��Ϸ�е� ����
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
		
		"/shop_balance.png",//�̳ǽ���ͼƬ��Դ
		"/shop_big.png",
		"/shop_figure.png",
		"/shop_go_pay.png",
		"/shop_midding.png",
		"/shop_out_base.png",
		"/shop_out.png",
		"/shop_small_base.png",
		"/shop_small.png",
		"/shop.png",
		
		"/achievement.png",//�ɾ�ϵͳͼƬ--big midding����
		"/achievement_left_right.png",
		"/achievement_left_right1.png",
		"/achievement_long.png",
		"/achievement_long1.png",
		"/achievement_out1.png",
		"/achievement_points.png",
		"/archivement_hoof.png",
		"/archivement_hoof1.png",
		
		"/current_ranking.jpg",//����ͼƬ��Դ
		"/ranking_option.png",
		"/ranking_option1.png",
		"/ranking_stripe.jpg",  //����ͼƬ
		"/ranking.png",
		"/ranking_show.jpg",
		"/wolf_run.png",
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

}
