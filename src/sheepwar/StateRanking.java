package sheepwar;

import javax.microedition.lcdui.Image;

import com.zte.iptv.j2me.stbapi.RankInfo;
import com.zte.iptv.j2me.stbapi.RankList;
import com.zte.iptv.j2me.stbapi.STBAPI;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

public class StateRanking implements Common{
	
	private SheepWarGameEngine engine = SheepWarGameEngine.instance;
	private boolean running;
	private int rankingIndex, rankX, rankY;
	private  RankList  ranklist_month;
	private  RankList  ranklist_week;
	private RankList rankList;
	
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
			ranklist_month = STBAPI.GetRankList(STBAPI.SysConfig.RankID,0,1,10);
			ranklist_week = STBAPI.GetRankList(STBAPI.SysConfig.RankID,2,1,10);
		    System.out.println("GetRankList:");
		    System.out.println("   Result  ="   + Integer.toString(ranklist_month.getResult()));
		    System.out.println("   TotalNum="  + Integer.toString(ranklist_month.getTotalNum()));
		    System.out.println("   CurNum ="  + Integer.toString(ranklist_month.getCurNum()));
		    System.out.println("   MyRankNo=" + Integer.toString(ranklist_month.getMyRankNo()));
		    System.out.println("   MyScore ="  + Integer.toString(ranklist_month.getMyScore()));
		    
		    if (ranklist_month.getResult() != 0 || ranklist_month.getCurNum() ==0 )
		        return;
		    
		    for (int i = 0; i <  ranklist_month.list.length; i++)
		    {
		        System.out.println(" <rankinfo>:"+Integer.toString(i+1));
		        System.out.println("UserID  =" + ranklist_month.list[i].getUserID());
		        System.out.println("UserName=" + ranklist_month.list[i].getUserName());
		        System.out.println(" RankNo =" + Integer.toString(ranklist_month.list[i].getRankNo()));
		        System.out.println(" Score  =" + Integer.toString(ranklist_month.list[i].getScore()));
		    }
		} 
		catch (Exception e)
		{
		    e.printStackTrace();
		} 
	}

	private int cloudIndex, cloud2Index;
	private int down_cloudIndex, down_cloud2Index;
	int x1 = 20, x2 = 550, x3 = 424;
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
		
		/*新添加的云朵元素*/
		Image pass_cloud = Resource.loadImage(Resource.id_pass_cloud);
		Image pass_cloud1 = Resource.loadImage(Resource.id_pass_cloud1);
		Image pass_cloud2 = Resource.loadImage(Resource.id_pass_cloud2);
		
		g.drawImage(game_bg, 0, 0, 20);
		
		/*增加的云层*/
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
		
		int  rankLeftX = 39,rankLeftY = 112,rankLeftYSpace = 16;			//rankLeftX 左侧x坐标，rankLeftYSpace 上下间距
		int rankShadowX = 4,rankShadowY = 4;								//排行阴影效果坐标差
		
		int workH = ranking_word.getHeight() / 2, workW = ranking_word.getWidth();
		int option1W = ranking_option1.getWidth(), option1H = ranking_option1.getHeight();
		int optionW = ranking_option.getWidth(), optionH = ranking_option.getHeight();
		for(int i=0;i<2;i++){//排行左侧条目
			g.drawRegion(ranking_option1, 0, 0, option1W, option1H, 0,
					rankLeftX, rankLeftY+(option1H+rankLeftYSpace)*i, 20);
			if(rankY ==i){     		
				g.drawRegion(ranking_option, 0, 0, optionW, optionH, 0,
						rankLeftX, rankLeftY+(optionH+rankLeftYSpace)*i, 20);
				g.drawRegion(ranking_word,0,i*workH, workW,	workH, 0, rankLeftX+8,
						rankLeftY+8+(ranking_option.getHeight()+rankLeftYSpace)*i, 20);
			}else{
				g.drawRegion(ranking_option, 0, 0, optionW, optionH, 0,
						rankLeftX-rankShadowX, rankLeftY-rankShadowY+(optionH+rankLeftYSpace)*i, 20);
				g.drawRegion(ranking_word,0,i*workH, workW,	workH, 0, rankLeftX-rankShadowX+8,
						rankLeftY-rankShadowY+8+(optionH+rankLeftYSpace)*i, 20);
			}
		}
		
		/*排行数据*/
		g.drawImage(shop_big, 233,101, 20);
		g.drawImage(ranking_show,260,116, 20);
		for(int i=0;i<5;i++){
			g.drawImage(ranking_stripe,240,151+i*56, 20);
		}
		g.drawImage(current_ranking, 253,448, 20);
		engine.setFont(25,true);
		if(rankY==0){
			rankList = ranklist_month;
		}else{
			rankList = ranklist_week;
		}
		RankInfo info = null;
		int offY = 155;
		for(int m=0;m<rankList.list.length;m++){
			info = rankList.list[m];
			String id = info.getUserID();
			int rank = info.getRankNo();
			int scores = info.getScore();
			g.drawString(String.valueOf(rank), 270, offY, 20);
			g.drawString(id, 380, offY, 20);
			g.drawString(String.valueOf(scores), 505, offY, 20);
			offY += 28;
		}
		String myRankNo = rankList.getMyRankNo()==0?"":String.valueOf(rankList.getMyRankNo());
		g.drawString(myRankNo, 260+current_ranking.getWidth(), 448, 20);
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
			if(rankX == 0 && rankY > 0){
				rankY = rankY - 1;
			}
		}else if(keyState.contains(KeyCode.DOWN)){
			keyState.remove(KeyCode.DOWN);
			if(rankX == 0 && rankY <2){
				rankY = (rankY + 1)%2;
			}
		}
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
    	Resource.freeImage(Resource.id_pass_cloud);       
		Resource.freeImage(Resource.id_pass_cloud1);       
		Resource.freeImage(Resource.id_pass_cloud1);     
	}
}
