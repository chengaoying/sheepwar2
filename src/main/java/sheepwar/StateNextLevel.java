package sheepwar;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

public class StateNextLevel implements Common{
	
	private SheepWarGameEngine engine = SheepWarGameEngine.instance;
	private boolean running;
	private int cloudIndex, cloud2Index;
	private int down_cloudIndex, down_cloud2Index;
	
	private int starNum;
	private int[][] starJudge ={					
			{6400,12800,19200},
			{7200,14400,21600},
			{8000,16000,24000},
			{8800,17600,26400},
			{9600,19200,28800},
			{10400,20800,31200},
			{11200,22400,33600},
			{12000,24000,36000},
			{12800,25600,38400},
			{13600,27200,40800},
			{14400,28800,43200},
			{15200,30400,45600},
			{16000,32000,48000},
			{16800,33600,50400},
			{17600,35200,52800},
	};
	
	public void processNextLevel(Role own){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			SGraphics g = engine.getSGraphics();
			
			/*星星等级*/
			judeStar(own);
			
			while (running) {
				handleNextLevel(keyState);
				if (running) {
					long t1 = System.currentTimeMillis();
					showNextLevel(g, own);
					engine.flushGraphics();
					System.gc();
					int sleepTime = (int)(125-(System.currentTimeMillis()-t1));
					if (sleepTime <= 0) {
						Thread.sleep(0);
					}
					else {
						Thread.sleep(sleepTime);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			clear();
		}
		
	}
	
	private void judeStar(Role own){
		/*根据当前关数与星级标准进行判断画几颗星星*/
		for(int i= 1;i<starJudge[StateGame.level-2].length;i++){	//关数
			if(own.scores2 >= starJudge[i][2]){
				starNum = 3;
			}else if(own.scores2 <starJudge[i][2] && (own.scores2 >= starJudge[i][1])){
				starNum = 2;
			}else if(own.scores2 <starJudge[i][1] && own.scores2 >= starJudge[i][0]){
				starNum = 1;
			}else{
				starNum = 0;
			}
		}

	}
	
	int x1 = 20, x2 = 550, x3 = 424;
	int ballonY = 114, ballon2Y = 336, ballon3Y = 59, ballon4Y = 420, ballon5Y = 560;
	private void showNextLevel(SGraphics g, Role own) {
		Image pass_bg = Resource.loadImage(Resource.id_game_bg);
		Image pass_cloud = Resource.loadImage(Resource.id_pass_cloud);
		Image pass_cloud1 = Resource.loadImage(Resource.id_pass_cloud1);
		Image pass_cloud2 = Resource.loadImage(Resource.id_pass_cloud2);
		Image pass_rainbow = Resource.loadImage(Resource.id_pass_rainbow);
		Image pass_score = Resource.loadImage(Resource.id_pass_score);
		Image pass_star = Resource.loadImage(Resource.id_pass_star);
		Image pass_star2 = Resource.loadImage(Resource.id_pass_star2);
		Image logo = Resource.loadImage(Resource.id_logo);
		Image blue = Resource.loadImage(Resource.id_balloon_blue);
		Image yellow = Resource.loadImage(Resource.id_balloon_yellow);
		Image multi = Resource.loadImage(Resource.id_balloon_multicolour);
		Image red = Resource.loadImage(Resource.id_balloon_red);
		Image green = Resource.loadImage(Resource.id_balloon_green);
		Image game_return = Resource.loadImage(Resource.id_game_return);
		Image return_bg = Resource.loadImage(Resource.id_achievement_left);
		g.drawImage(pass_bg, 0, 0, 20);
		
		/*上面第二层云*/
		int cloud2W = pass_cloud2.getWidth(),cloud2H = pass_cloud2.getHeight();
		int len = cloud2W-ScrW;
		int cloud2Y = -6;
		cloud2Index=(cloud2Index+1)%cloud2W;
		if(cloud2Index<=len){
			g.drawRegion(pass_cloud2, len-cloud2Index, 0, ScrW, cloud2H, 0, 0, cloud2Y, 20);
		}else{
			g.drawRegion(pass_cloud2, (cloud2W-cloud2Index), 0, ScrW-(cloud2W-cloud2Index), cloud2H, 0, 0, cloud2Y, 20);
			g.drawRegion(pass_cloud2, 0, 0, (cloud2W-cloud2Index), cloud2H, 0, ScrW-(cloud2W-cloud2Index), cloud2Y, 20);
		}
		
		/*下面第二层云*/
		int down_cloud2Y = 484;
		down_cloud2Index=(down_cloud2Index+1)%cloud2W;
		if(down_cloud2Index<=len){
			g.drawRegion(pass_cloud2, len-down_cloud2Index, 0, ScrW, cloud2H, 0, 0, down_cloud2Y, 20);
		}else{
			g.drawRegion(pass_cloud2, (cloud2W-down_cloud2Index), 0, ScrW-(cloud2W-down_cloud2Index), cloud2H, 0, 0, down_cloud2Y, 20);
			g.drawRegion(pass_cloud2, 0, 0, (cloud2W-down_cloud2Index), cloud2H, 0, ScrW-(cloud2W-down_cloud2Index), down_cloud2Y, 20);
		}

		/*中间的云*/
		int cloudW = pass_cloud.getWidth();
		if(x1+cloudW<=0){
			x1 = ScrW;
		}else{
			x1 -= 1;
		}
		if(x2+cloudW<=0){
			x2 = ScrW;
		}else{
			x2 -= 1;
		}
		if(x3+cloudW<=0){
			x3 = ScrW;
		}else{
			x3 -= 1;
		}
		g.drawImage(pass_cloud, x1, 152, 20);
		g.drawImage(pass_cloud, x2, 180, 20);
		g.drawImage(pass_cloud, x3, 265, 20);
		
		/*暗星星*/
		int space = 130;
		for(int i=0;i<3;i++){
			g.drawImage(pass_star2, 125+(i*space), 130, 20);
		}
		/*亮星星（根据积分确定星星个数）*/
		if(starNum > 0){
			for(int i=0;i<starNum;i++){
				g.drawImage(pass_star, 125+(i*space), 130, 20);			
			}
		}
		
		/*积分*/
		g.drawImage(pass_score, 155, 286, 20);
		drawNum(g, StateGame.scores2, 180+pass_score.getWidth(), 288);
		drawNum(g, StateGame.scores, 180+pass_score.getWidth(), 330);
		
		/*彩虹*/
		g.drawImage(pass_rainbow, 395, 258, 20);
		
		/*气球*/
		int ballonW = blue.getWidth()/5, ballonH = blue.getHeight();
		if(ballonY+ballonH<=0){
			ballonY = ScrH;
		}else{
			ballonY -= 5;
		}
		if(ballon2Y+ballonH<=0){
			ballon2Y = ScrH;
		}else{
			ballon2Y -= 5;
		}
		if(ballon3Y+ballonH<=0){
			ballon3Y = ScrH;
		}else{
			ballon3Y -= 5;
		}
		if(ballon4Y+ballonH<=0){
			ballon4Y = ScrH;
		}else{
			ballon4Y -= 5;
		}
		if(ballon5Y+ballonH<=0){
			ballon5Y = ScrH;
		}else{
			ballon5Y -= 5;
		}
		g.drawRegion(green, 2*ballonW, 0, ballonW, ballonH, 0, 43, ballonY, 20);
		g.drawRegion(red, 2*ballonW, 0, ballonW, ballonH, 0, 214, ballon2Y, 20);
		g.drawRegion(yellow, 2*ballonW, 0, ballonW, ballonH, 0, 560, ballon3Y, 20);
		g.drawRegion(blue, 2*ballonW, 0, ballonW, ballonH, 0, 100, ballon4Y, 20);
		g.drawRegion(multi, 2*ballonW, 0, ballonW, ballonH, 0, 439, ballon5Y, 20);
		
		/*上面第一层云*/
		int cloud1W = pass_cloud1.getWidth(),cloud1H = pass_cloud1.getHeight();
		int cloud1Y = -23;
		cloudIndex=(cloudIndex+1)%cloud1W;
		if(cloudIndex<=cloud1W-ScrW){
			g.drawRegion(pass_cloud1, cloudIndex, 0, ScrW, cloud1H, 0, 0, cloud1Y, 20);
		}else{
			g.drawRegion(pass_cloud1, cloudIndex, 0, cloud1W-cloudIndex, cloud1H, 0, 0, cloud1Y, 20);
			g.drawRegion(pass_cloud1, 0, 0, cloudIndex, cloud1H, 0, cloud1W-cloudIndex, cloud1Y, 20);
		}
		
		/*下面第一层云*/
		int down_cloud1Y = 496;
		down_cloudIndex=(down_cloudIndex+1)%cloud1W;
		if(down_cloudIndex<=cloud1W-ScrW){
			g.drawRegion(pass_cloud1, down_cloudIndex, 0, ScrW, cloud1H, 0, 0, down_cloud1Y, 20);
		}else{
			g.drawRegion(pass_cloud1, down_cloudIndex, 0, cloud1W-down_cloudIndex, cloud1H, 0, 0, down_cloud1Y, 20);
			g.drawRegion(pass_cloud1, 0, 0, down_cloudIndex, cloud1H, 0, cloud1W-down_cloudIndex, down_cloud1Y, 20);
		}
		
		/*logo*/
		g.drawImage(logo, 6, 2, 20);
		int bgX = SheepWarGameEngine.ScrW/2-return_bg.getWidth()/2, bgY = 390;
		int gameW = game_return.getWidth()/2, gameH = game_return.getHeight()/3;
		int gameX = SheepWarGameEngine.ScrW/2-gameW/2;
		g.drawImage(return_bg, bgX, bgY, 20);
		g.drawRegion(game_return, 0, 0, gameW, gameH, 0, gameX, 398, 20);
	}
	
	private void drawNum(SGraphics g, int num, int x, int y){
		Image pass_num = Resource.loadImage(Resource.id_pass_num);
		String number = String.valueOf(num);
		for (byte i = 0; i < number.length(); i++) {
			g.drawRegion(pass_num, (number.charAt(i) - '0') * pass_num.getWidth()/10, 0, 
					pass_num.getWidth()/10, pass_num.getHeight(), 0, x + i * (pass_num.getWidth()/10 + 1), y, 0);
		}
	}
	
	private void handleNextLevel(KeyState keyState) {
	    if(keyState.containsAndRemove(KeyCode.OK)){ 
	    	running = false;
		}
	}

	private void clear() {
		Resource.freeImage(Resource.id_logo);
		Resource.freeImage(Resource.id_game_return);
		Resource.freeImage(Resource.id_game_bg);
		Resource.freeImage(Resource.id_pass_cloud);
		Resource.freeImage(Resource.id_pass_cloud1);
		Resource.freeImage(Resource.id_pass_cloud2);
		Resource.freeImage(Resource.id_pass_num);
		Resource.freeImage(Resource.id_pass_rainbow);
		Resource.freeImage(Resource.id_pass_score);
		Resource.freeImage(Resource.id_pass_star);
		Resource.freeImage(Resource.id_pass_star2);
		Resource.freeImage(Resource.id_balloon_yellow);
		Resource.freeImage(Resource.id_balloon_multicolour);
		Resource.freeImage(Resource.id_balloon_red);
		Resource.freeImage(Resource.id_balloon_green);
		Resource.freeImage(Resource.id_balloon_blue);
		Resource.freeImage(Resource.id_achievement_left);
	}
}
