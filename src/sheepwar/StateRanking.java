package sheepwar;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

public class StateRanking implements Common{
	
	private SheepWarGameEngine engine = SheepWarGameEngine.instance;
	private boolean running;
	private int rankingIndex, rankX, rankY;
	
	public void processRanking(){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			SGraphics g = engine.getSGraphics();
			queryRanking();  //查询排行
			while (running) {
				handleRanking(keyState);
				if (running) {
					long t1 = System.currentTimeMillis();
					showRanking(g);
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
	
	private void queryRanking(){
		/*Date date = new Date(engine.getEngineService().getCurrentTime().getTime());
		System.out.println(date);
		int year = DateUtil.getYear(date);
		int month = DateUtil.getMonth(date);
		//int day = DateUtil.getDay(date);
		Date start = DateUtil.createTime(year, month, 1);
		Date end = DateUtil.createTime(year, month+1, 1);
		System.out.println("start:"+start);
		System.out.println("end:"+end);
		ServiceWrapper sw = engine.getServiceWrapper();
		rankingList = sw.queryRankingList(0, 10);
		ranking_month = sw.queryRankingList(start, end, 0, 10);*/
	}

	private void showRanking(SGraphics g) {

		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image achievement_out1 = Resource.loadImage(Resource.id_achievement_out1);//{61,462}
		Image shop_big = Resource.loadImage(Resource.id_shop_big);//{233,101}
		//Image achievement_left_right = Resource.loadImage(Resource.id_achievement_left_right);//{457,440}
		//Image achievement_left_right1 = Resource.loadImage(Resource.id_achievement_left_right1);//{457,440}
		Image current_ranking=Resource.loadImage(Resource.id_current_ranking);//{253,448}
		Image ranking_option=Resource.loadImage(Resource.id_ranking_option);//{39,112} Y相差54 
		Image ranking_option1=Resource.loadImage(Resource.id_ranking_option1);
		Image ranking_stripe=Resource.loadImage(Resource.id_ranking_stripe);//{241,151}  条高度57
		Image ranking=Resource.loadImage(Resource.id_ranking);//{232,18}
		Image ranking_show=Resource.loadImage(Resource.id_ranking_show);//{241,108}
		Image ranking_word=Resource.loadImage(Resource.id_ranking_word);    
		//Image slash = Resource.loadImage(Resource.id_slash);
		g.drawImage(game_bg, 0, 0, 20);
		int  rankLeftX = 39,rankLeftY = 112,rankLeftYSpace = 16;			//rankLeftX 左侧x坐标，rankLeftYSpace 上下间距
		int rankShadowX = 4,rankShadowY = 4;								//排行阴影效果坐标差
		
		int workH = ranking_word.getHeight() / 3, workW = ranking_word.getWidth();
		int option1W = ranking_option1.getWidth(), option1H = ranking_option1.getHeight();
		int optionW = ranking_option.getWidth(), optionH = ranking_option.getHeight();
		for(int i=0;i<3;i++){//排行左侧条目
			g.drawRegion(ranking_option1, 0, 0, option1W, option1H, 0,
					rankLeftX, rankLeftY+(option1H+rankLeftYSpace)*i, 20);
			if(rankY ==i){     		
				g.drawRegion(ranking_option, 0, 0, optionW, optionH, 0,
						rankLeftX-rankShadowX, rankLeftY-rankShadowY+(optionH+rankLeftYSpace)*i, 20);
				g.drawRegion(ranking_word,0,i*workH, workW,	workH, 0, rankLeftX-rankShadowX+8,
						rankLeftY-rankShadowY+8+(optionH+rankLeftYSpace)*i, 20);
			}else{
				g.drawRegion(ranking_option, 0, 0, optionW, optionH, 0,
						rankLeftX, rankLeftY+(optionH+rankLeftYSpace)*i, 20);
				g.drawRegion(ranking_word,0,i*workH, workW,	workH, 0, rankLeftX+8,
						rankLeftY+8+(ranking_option.getHeight()+rankLeftYSpace)*i, 20);
			}
		}
		
		/*排行数据*/
		/*if(rankY==0 && rankingList!=null){
			for(int k=0;k<rankingList.length;k++){
				
			}
		}*/
		g.drawImage(shop_big, 233,101, 20);
		//g.drawImage(slash, 523-slash.getWidth()/2, 447, 20);								//画出斜杠
		g.drawImage(ranking_show,260,116, 20);
		for(int i=0;i<5;i++){
			g.drawImage(ranking_stripe,241,151+i*57, 20);
		}
		g.drawImage(current_ranking, 253,448, 20);
		g.drawImage(ranking, 232,18, 20);
		g.drawImage(achievement_out1, 447,447, 20);
		
	}
	
	private void handleRanking(KeyState keyState) {
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			running = false;
		}else if(keyState.contains(KeyCode.OK)){
			keyState.remove(KeyCode.OK);
			if(rankingIndex==0){
				if(rankingIndex>0){
					rankingIndex--;
				}
			}
			if(rankingIndex==1){
				if(rankingIndex<2){
					rankingIndex++;
				}
			}
		}else if(keyState.contains(KeyCode.UP)){
			keyState.remove(KeyCode.UP);
			if(rankX == 0 && rankY > 0){
				rankY = rankY - 1;
			}
		}else if(keyState.contains(KeyCode.DOWN)){
			keyState.remove(KeyCode.DOWN);
			if(rankX == 0 && rankY <3){
				rankY = (rankY + 1)%3;
			}
		}
		/*if(keyState.contains(KeyCode.LEFT)){
			keyState.remove(KeyCode.LEFT);
			if(rankX > 0){
				rankX = rankX - 1;
			}else {
				rankX = 0;
			}
		}
		if(keyState.contains(KeyCode.RIGHT)){
			keyState.remove(KeyCode.RIGHT);
			if(rankX < 2){
				rankX = rankX + 1;
			}else{
				rankX = 2;
			}
		}*/
	
	}

	private void clear() {
    	Resource.freeImage(Resource.id_game_bg);
    	Resource.freeImage(Resource.id_achievement_out1);
    	Resource.freeImage(Resource.id_shop_big);
    	Resource.freeImage(Resource.id_achievement_left_right);
    	Resource.freeImage(Resource.id_achievement_left_right1);
    	Resource.freeImage(Resource.id_current_ranking);
    	Resource.freeImage(Resource.id_ranking_option);
    	Resource.freeImage(Resource.id_ranking_option1);
    	Resource.freeImage(Resource.id_ranking_stripe);
    	Resource.freeImage(Resource.id_ranking);
    	Resource.freeImage(Resource.id_ranking_show);
    	Resource.freeImage(Resource.id_slash);
    	Resource.freeImage(Resource.id_shop_figure);
	}
}
