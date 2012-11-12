package sheepwar;

import javax.microedition.lcdui.Image;

import cn.ohyeah.itvgame.model.GameRanking;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

public class StateRanking implements Common{
	
	private SheepWarGameEngine engine = SheepWarGameEngine.instance;
	private boolean running;
	private int rankingIndex, /*rankX,*/ rankY;
	private  GameRanking[]  ranklist_month;
	private  GameRanking[]  ranklist_week;
	private GameRanking[] rankList;
	
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
		try
		{
			ServiceWrapper sw = engine.getServiceWrapper();
			ranklist_month = sw.queryRankingList(0, 5);
			
		    if (ranklist_month==null || ranklist_month.length<0)
		        return;
		    
		    for (int i = 0; i <  ranklist_month.length; i++)
		    {
		        System.out.println(" <rankinfo>:"+Integer.toString(i+1));
		        System.out.println("UserID  =" + engine.getEngineService().getUserId());
		        System.out.println(" RankNo =" + Integer.toString(ranklist_month[i].getRanking()));
		        System.out.println(" Score  =" + Integer.toString(ranklist_month[i].getScores()));
		    }
		} 
		catch (Exception e)
		{
		    e.printStackTrace();
		} 
	}

	/*private int cloudIndex, cloud2Index;
	private int down_cloudIndex, down_cloud2Index;*/
	int x1 = 20, x2 = 550, x3 = 424;
	private void showRanking(SGraphics g) {

		Image game_bg = Resource.loadImage(Resource.id_rank_bottom);
		Image achievement_out1 = Resource.loadImage(Resource.id_achievement_out1);//{61,462}
		Image shop_big = Resource.loadImage(Resource.id_shop_big);//{233,101}
		Image current_ranking=Resource.loadImage(Resource.id_current_ranking);//{253,448}
		Image ranking_option=Resource.loadImage(Resource.id_ranking_option);//{39,112} Y相差54 
		Image ranking_option1=Resource.loadImage(Resource.id_ranking_option1);
		Image ranking_stripe=Resource.loadImage(Resource.id_ranking_stripe);//{241,151}  条高度57
		Image ranking=Resource.loadImage(Resource.id_ranking);//{232,18}
		Image ranking_show=Resource.loadImage(Resource.id_ranking_show);//{241,108}
		Image ranking_word=Resource.loadImage(Resource.id_ranking_word);    
		Image pass_cloud = Resource.loadImage(Resource.id_pass_cloud);
		g.drawImage(game_bg, 0, 0, 20);
		
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
		
		int  rankLeftX = 39,rankLeftY = 112,rankLeftYSpace = 16;			//rankLeftX 左侧x坐标，rankLeftYSpace 上下间距
		int optionW = ranking_option.getWidth(), optionH = ranking_option.getHeight();
		int workH = ranking_word.getHeight() / 2, workW = ranking_word.getWidth();
		g.drawRegion(ranking_option1, 0, 0, optionW, optionH, 0,
						rankLeftX, rankLeftY+(optionH+rankLeftYSpace)*0, 20);
		g.drawRegion(ranking_word,0,0*workH, workW,	workH, 0, rankLeftX+8,
					rankLeftY+7+(ranking_option.getHeight()+rankLeftYSpace)*0, 20);
		
		/*排行数据*/
		g.drawImage(shop_big, 233,101, 20);
		g.drawImage(ranking_show,260,122, 20);
		for(int i=0;i<5;i++){
			g.drawImage(ranking_stripe,240+18,151+i*56, 20);
		}
		g.drawImage(current_ranking, 253,448, 20);
		engine.setFont(25,true);
		if(rankY==0){
			rankList = ranklist_month;
		}else{
			rankList = ranklist_week;
		}
		GameRanking info = null;
		int offY = 159;
		String ownRank = "";
		if(rankList!=null){
			for(int m=0;m<rankList.length;m++){
				g.setColor(0x00000);
				info = rankList[m];
				String id = info.getUserId();
				int rank = info.getRanking();
				int scores = info.getScores();
				g.drawString(String.valueOf(rank), 270, offY, 20);
				int offX = 380;
				if(id.length()>7 && id.length()<=11){
					offX -= 20;
				}else if(id.length()>11){
					offX -= 35;
				}
				g.drawString(id, offX, offY, 20);
				g.drawString(String.valueOf(scores), 505, offY, 20);
				offY += 56;
				if(id.equals(engine.getEngineService().getUserId())){
					ownRank = String.valueOf(info.getRanking());
				}
			}
		}
		g.drawString(ownRank, 260+current_ranking.getWidth(), 448, 20);
		g.setColor(red);
		engine.setDefaultFont();
		g.drawImage(ranking, 194,18, 20);
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
			/*if(rankX == 0 && rankY > 0){
				rankY = rankY - 1;
			}*/
		}else if(keyState.contains(KeyCode.DOWN)){
			keyState.remove(KeyCode.DOWN);
			/*if(rankX == 0 && rankY <2){
				rankY = (rankY + 1)%2;
			}*/
		}
	}

	private void clear() {
		Resource.freeImage(Resource.id_rank_bottom);
    	Resource.freeImage(Resource.id_achievement_out1);
    	Resource.freeImage(Resource.id_shop_big);
    	Resource.freeImage(Resource.id_current_ranking);
    	Resource.freeImage(Resource.id_ranking_option);
    	Resource.freeImage(Resource.id_ranking_option1);
    	Resource.freeImage(Resource.id_ranking_stripe);
    	Resource.freeImage(Resource.id_ranking);
    	Resource.freeImage(Resource.id_ranking_show);
    	Resource.freeImage(Resource.id_ranking_word);
    	Resource.freeImage(Resource.id_pass_cloud);       
	}
}
