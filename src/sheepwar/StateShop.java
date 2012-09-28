package sheepwar;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.ui.TextView;

public class StateShop implements Common{
	
	private SheepWarGameEngine engine = SheepWarGameEngine.instance;
	private boolean running;
	private int shopX,  shopY;
	
	public void processShop(){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			SGraphics g = engine.getSGraphics();
			while (running) {
				handleShop(keyState);
				if (running) {
					long t1 = System.currentTimeMillis();
					showShop(g);
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

	private int cloudIndex, cloud2Index;
	private int down_cloudIndex, down_cloud2Index;
	int x1 = 20, x2 = 550, x3 = 424;
	private void showShop(SGraphics g) {
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image shop_balance = Resource.loadImage(Resource.id_shop_balance);//{46,454}
		Image shop_big = Resource.loadImage(Resource.id_shop_big);//{29,103}
		Image shop_go_pay = Resource.loadImage(Resource.id_shop_go_pay);//{457,381}				//9/29被修改为单纯的文字图片
		Image shop_go_pay_base = Resource.loadImage(Resource.id_achievement_left);				//单纯的 按钮图片
		Image shop_midding = Resource.loadImage(Resource.id_shop_midding);//{434,103}
		Image shop_out_base = Resource.loadImage(Resource.id_shop_out_base);
		Image shop_out = Resource.loadImage(Resource.id_shop_out);//{457,429}					//9/29被修改为单纯的文字图片“返回”
		Image shop_small_base = Resource.loadImage(Resource.id_shop_small_base);
		Image shop_small = Resource.loadImage(Resource.id_shop_small);
		Image price_quantity = Resource.loadImage(Resource.id_price_quantity);
		Image shop = Resource.loadImage(Resource.id_shop);//{217,18}
		Image playing_prop=Resource.loadImage(Resource.id_playing_prop);
		/*增加的元素*/
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
		
		
		g.drawImage(shop, 217, 18, 20);
		g.drawImage(shop_big, 29, 103, 20);
		g.drawImage(shop_balance, 46, 454, 20);
		g.drawImage(shop_midding, 434, 103, 20);
		
		int x =42, y = 120, spaceX = 15, spaceY = 8;
		int mapx=37,mapy=112;       
		int smallW = shop_small.getWidth(), smallH = shop_small.getHeight();
		int p_propW = playing_prop.getWidth()/8, p_propH = playing_prop.getHeight();
		int baseW = shop_small_base.getWidth(), baseH = shop_small_base.getHeight();
		int /*outW = shop_out_base.getWidth(), */outH = shop_out_base.getHeight();
		for(int i=0;i<4;i++){
		     for(int j=0;j<2;j++){
		    	 g.drawImage(shop_out_base, 457, 381+(spaceY+outH)*j, 20);
		    	 g.drawImage(shop_small_base, x+(spaceX+baseW)*j, y+(spaceY+baseH)*i, 20);
				if(shopX==j && shopY==i){
					engine.setFont(25);	
					g.drawImage(shop_small, x+(spaceX+smallW)*j, y+(spaceY+smallH)*i, 20);
					g.drawImage(price_quantity, x+(spaceX+smallW)*j+65, y+(spaceY+smallH)*i+12, 20);
					g.drawRegion(playing_prop, getPropIndex(i, j)*p_propW, 0, p_propW, p_propH, 0,x+(spaceX+smallW)*j+8, y+(spaceY+smallH)*i+9, 20);
//					drawNum(g, engine.props[getPropIndex(i, j)].getPrice(), x+(spaceX+smallW)*j+119, y+(spaceY+smallH)*i+11);
					g.drawString(/*String.valueOf(engine.props[getPropIndex(i, j)].getPrice())*/"1212",
							x+(spaceX+smallW)*j+119, y+(spaceY+smallH)*i+11, 20);
//					drawNum(g, engine.props[getPropIndex(i, j)].getNums(), x+(spaceX+smallW)*j+119, y+(spaceY+smallH)*i+36);
					g.drawString(/*String.valueOf(engine.props[getPropIndex(i, j)].getNums())*/"12123", 
							x+(spaceX+smallW)*j+119, y+(spaceY+smallH)*i+36, 20);
					g.setColor(0xffffff);				
					
					TextView.showMultiLineText(g, Resource.propIntroduce[shopY][shopX], 5, 444, 140, 162, 220);
					engine.setDefaultFont();
				}else{
					g.drawImage(shop_small, mapx+(spaceX+smallW)*j, mapy+(spaceY+smallH)*i, 20);
					g.drawImage(price_quantity, mapx+(spaceX+smallW)*j+65, mapy+(spaceY+smallH)*i+12, 20);
					g.drawRegion(playing_prop, getPropIndex(i, j)*p_propW, 0, p_propW, p_propH, 0,mapx+(spaceX+smallW)*j+8, mapy+(spaceY+smallH)*i+9, 20);
//					drawNum(g, engine.props[getPropIndex(i, j)].getPrice(), mapx+(spaceX+smallW)*j+119, mapy+(spaceY+smallH)*i+11);
					g.drawString(/*String.valueOf(engine.props[getPropIndex(i, j)].getPrice())*/"226",
							mapx+(spaceX+smallW)*j+119, mapy+(spaceY+smallH)*i+11, 20);
//					drawNum(g, engine.props[getPropIndex(i, j)].getNums(), mapx+(spaceX+smallW)*j+119, mapy+(spaceY+smallH)*i+36);
					g.drawString(/*String.valueOf(engine.props[getPropIndex(i, j)].getNums())*/"138", 
							mapx+(spaceX+smallW)*j+119, mapy+(spaceY+smallH)*i+36, 20);
				}
			}
		}
		 if(shopX==2){          //充值和返回被选择的阴影效果
			 if(shopY==0){      //控制方向由左到右的入口方向
				 g.drawImage(shop_go_pay_base, 457, 381, 20);
				 g.drawImage(shop_go_pay, 457+16, 381+5, 20);
				 g.drawImage(shop_go_pay_base, 457-8, 429-5, 20);		//返回文字上的按钮
			   	 g.drawImage(shop_out, 457-8+16, 429-5+7, 20);
			  }else{
			   	 g.drawImage(shop_go_pay_base, 457-8, 381-5, 20);
			   	 g.drawImage(shop_go_pay, 457-8+16, 381-5+5, 20);
			   	 g.drawImage(shop_go_pay_base, 457, 429, 20);
			   	 g.drawImage(shop_out, 457+16, 429+7, 20);
			 }
		    }else{
		    	g.drawImage(shop_go_pay_base, 457 -8, 381-5, 20);
		    	g.drawImage(shop_go_pay, 457-8+16, 381-5+5, 20);
		   		g.drawImage(shop_go_pay_base, 457-8, 429-5, 20);
		   		g.drawImage(shop_out, 457-8+16, 429-5+7, 20);
		    }
//		drawNum(g, engine.getEngineService().getBalance(), 103,452);  
		g.drawString(/*String.valueOf(engine.getEngineService().getBalance())*/"1000000",103,452, 20);//用户余额
	
	}
	
	private int getPropIndex(int x, int y){
		if(x==0 && y==0)return 0;
		if(x==1 && y==0)return 1;
		if(x==2 && y==0)return 2;
		if(x==3 && y==0)return 3;
		if(x==0 && y==1)return 4;
		if(x==1 && y==1)return 5;
		if(x==2 && y==1)return 6;
		if(x==3 && y==1)return 7;
		return -1;
	}
	
	private void handleShop(KeyState keyState) {
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			running = false;
			shopX = 0;
			shopY = 0;
			
			/*同步道具*/
			engine.pm.sysProps();
		}else if (keyState.contains(KeyCode.UP)) {
			keyState.remove(KeyCode.UP);
			if(shopY>0){
				if(shopX==2){
					shopY=(shopY-1)%2;
				}else{
					shopY=(shopY-1)%4;
				}
			}
		}else if (keyState.contains(KeyCode.DOWN)) {
			keyState.remove(KeyCode.DOWN);
			if(shopY<4){
				if(shopX<2){
					shopY = (shopY+1)%4;
				}else{
					shopY = (shopY+1)%2;
				}
			}
		}else if (keyState.contains(KeyCode.LEFT)) {
			keyState.remove(KeyCode.LEFT);
			if(shopX>0){
				shopX = shopX-1;
			}
		}else if (keyState.contains(KeyCode.RIGHT)) {
			keyState.remove(KeyCode.RIGHT);
			if(shopX<2){
				shopX = (shopX+1)%3;
			}
			if(shopX==2){//当控制由左到右时，shopY置零
				shopY=0;
			}
		}else if (keyState.contains(KeyCode.OK)) {
			keyState.remove(KeyCode.OK);
			if(shopX==2 && shopY==0){//进入充值
				/*StateRecharge sr = new StateRecharge(engine);
				sr.recharge();
				*/
			}else if(shopX==2 && shopY==1){
				running = false;
				shopX = 0;shopY = 0;
			}else{
				engine.pm.purchaseProp(shopX, shopY);
			}
		}
	
	}
	
	/*private void drawNum(SGraphics g, int num, int x, int y) {
		Image imgNumeber = Resource.loadImage(Resource.id_shop_figure);
		String number = String.valueOf(num);
		for (byte i = 0; i < number.length(); i++) {
			g.drawRegion(imgNumeber, (number.charAt(i) - '0') * imgNumeber.getWidth()/10, 0, 
					imgNumeber.getWidth()/10, imgNumeber.getHeight(), 0, x + i * (imgNumeber.getWidth()/10 + 1), y, 0);
		}
	}*/
	
	private void clear() {
		Resource.freeImage(Resource.id_shop);
		Resource.freeImage(Resource.id_shop_balance);
		Resource.freeImage(Resource.id_shop_big);
		Resource.freeImage(Resource.id_shop_figure);
		Resource.freeImage(Resource.id_shop_balance);
		Resource.freeImage(Resource.id_shop_go_pay);
		Resource.freeImage(Resource.id_shop_midding);
		Resource.freeImage(Resource.id_shop_out);
		Resource.freeImage(Resource.id_price_quantity);
		Resource.freeImage(Resource.id_playing_prop);    
		Resource.freeImage(Resource.id_pass_cloud);       
		Resource.freeImage(Resource.id_pass_cloud1);       
		Resource.freeImage(Resource.id_pass_cloud1);   
	}
}
