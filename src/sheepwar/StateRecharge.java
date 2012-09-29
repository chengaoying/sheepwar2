package sheepwar;

import java.io.IOException;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

import com.zte.iptv.j2me.stbapi.Account;
import com.zte.iptv.j2me.stbapi.STBAPI;
import com.zte.iptv.j2me.stbapi.recharge.RechageInterface;
import com.zte.iptv.j2me.stbapi.xmlParser.XmlPullParserException;

import cn.ohyeah.stb.game.Configurations;
import cn.ohyeah.stb.game.IEngine;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.res.ResourceManager;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.DrawUtil;
import cn.ohyeah.stb.ui.PopupText;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

public class StateRecharge implements RechageInterface{
	
	private static final byte STATE_SELECT_AMOUNT = 0;
	
	
	private static short NUM_PICS = 0;
	private static final short PIC_ID_RECHARGE_BG = NUM_PICS++;
	private static final short PIC_ID_RECHARGE_TITLE = NUM_PICS++;
	private static final short PIC_ID_BACK1 = NUM_PICS++;
	private static final short PIC_ID_RECHARGE0 = NUM_PICS++;
	
	private static final String[] imagePaths = {
		"/business/recharge-bg.jpg",
		"/business/recharge-title.png",
		"/business/back1.png",
		"/business/recharge0.png",
	};
	
	private byte groupIndex;
	private byte amountIndex;
	private byte state;
	private boolean back;
	private ResourceManager resource;
	private IEngine engine;
	private String unit = "游戏币";
	private int count = 4;
	
	private String feeCode;
	private String productName;
	private String priceDesc;
	
	
	public StateRecharge(IEngine engine) {
		this.engine = engine;
		resource = ResourceManager.createImageResourceManager(imagePaths);
	}
	
	public int recharge() {
		int result = 0;
		SGraphics g = engine.getSGraphics();
		KeyState KeyState = engine.getKeyState();
		groupIndex = 1;
		boolean run = true;
		try {
			while (run) {
				handle(KeyState);
				show(g);
				engine.flushGraphics();
				execute();
				
				if (back) {
					break;
				}
				engine.trySleep();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			result = -1;
		}
		finally {
			clear();
		}
		return result;
	}
	
	public void clear() {
		resource.clear();
	}
	
	private void execute() {
		switch(state) {
		case STATE_SELECT_AMOUNT: 
			break;
		default:
			throw new RuntimeException("未知的状态, state="+state);
		}
	}
	
	private void show(SGraphics g) {
		switch(state) {
		case STATE_SELECT_AMOUNT: 
			showSelectAmount(g);
			break;
		default:
			throw new RuntimeException("未知的状态, state="+state);
		}
	}
		
	private void showSelectAmount(SGraphics g) {
		g.setColor(43, 39, 36);
		Image bgImg = resource.loadImage(PIC_ID_RECHARGE_BG);
		g.drawImage(bgImg, 0, 0, 20);
		Font font = g.getFont();
		int amountsBgX = (engine.getScreenWidth()-434)>>1;
		int amountsBgY = (engine.getScreenHeight()-324)>>1;
		amountsBgX -= Configurations.Abs_Coords_X;
		amountsBgY -= Configurations.Abs_Coords_Y;
		Image title = null;
		title = resource.loadImage(PIC_ID_RECHARGE_TITLE);
		if (title != null) {
			g.drawImage(title, amountsBgX + ((434-title.getWidth())>>1), amountsBgY + 17,20);
		}
		/*显示支付方式*/
		g.setColor(0XFFFF00);
		int sx, sy, sw, sh;
		String ss = "支付方式:";
		
		/*显示金额列表*/
		sw = 276;
		sh = 33;
		int amount = 0;
		int amountY = 0;
		Image recharge = resource.loadImage(PIC_ID_RECHARGE0);
		int textDelta = (sh-font.getHeight())>>1;
		int btnDelta = (sh-recharge.getHeight())>>1;
		int btnX = amountsBgX+330;
		sx = amountsBgX+33;
		sy = amountsBgY+90;
		
		for(int i=0;i<count;i++){
			g.setColor(0X062C39);
			g.fillRect(sx, sy, sw, sh);
			g.setColor(0XA2FFFF);
	        DrawUtil.drawRect(g, sx, sy, sw, sh, 2);
	        if(i==0){
	        	amount = 10;
	        	ss = "1元";
	        }else if(i==1){
	        	amount = 20;
	        	ss = "2元";
	        }else if(i==2){
	        	amount = 50;
	        	ss = "5元";
	        }else if(i==3){
	        	amount = 100;
	        	ss = "10元";
	        }
			g.setColor(0XFFFF00);
			g.drawString(amount+unit, sx+2, sy+textDelta, 20);
			g.setColor(0XFFFF00);
			g.drawString(ss, sx+sw-font.stringWidth(ss)-2, sy+textDelta, 20);
			g.drawImage(recharge, btnX, sy+btnDelta, 20);
			if (groupIndex == 1 && amountIndex == i) {
				amountY = sy;
			}
			g.setColor(0xffffff);
			sy += sh+10;
		}
		
		Image back = resource.loadImage(PIC_ID_BACK1);
		sx = amountsBgX+((434-back.getWidth())>>1);
		sy = amountsBgY+284;
		g.drawImage(back, sx, sy, 20);
		
		if (groupIndex == 1) {
			DrawUtil.drawRect(g, btnX, amountY+2, recharge.getWidth(), recharge.getHeight(), 2, 0XFFFF00);
		}
		else if (groupIndex == 2){
			DrawUtil.drawRect(g, sx, sy, back.getWidth(), back.getHeight(), 2, 0XFFFF00);
		}
	}
	
	private void handle(KeyState key) {
		switch(state) {
		case STATE_SELECT_AMOUNT: 
			handleSelectAmount(key);
			break;
		default:
			throw new RuntimeException("未知的状态, state="+state);
		}
	}


	private void handleSelectAmount(KeyState key) {
		if (key.containsAndRemove(KeyCode.UP)) {
			if (groupIndex == 1){
				if (amountIndex > 0) {
					--amountIndex;
				}
			}
			else if (groupIndex == 2){
				groupIndex = 1;
			}
		}
		
		if (key.containsAndRemove(KeyCode.DOWN)) {
			if (groupIndex == 1) {
				if (amountIndex < count-1) {
					++amountIndex;
				}
				else {
					groupIndex = 2;
				}
			}
		}
		if (key.containsAndRemove(KeyCode.NUM0|KeyCode.BACK)) {
			key.clear();
			clear();
			back = true;
		}
		
		if (key.containsAndRemove(KeyCode.OK)) {
			key.clear();
			if(groupIndex==1){	//充值
				getFeeCode(amountIndex);
				try {
					STBAPI.RechargeEx(feeCode, productName, priceDesc, 0, "充值", this);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
			}else{
				clear();
				back = true;
			}
		}
	}
	
	private void getFeeCode(int index){
		if(index==0){
			feeCode = STBAPI.SysConfig.Fee100;
			productName = "10"+unit;
			priceDesc = "1元";
		}else if(index==1){
			feeCode = STBAPI.SysConfig.Fee200;
			productName = "20"+unit;
			priceDesc = "2元";
		}else if(index==2){
			feeCode = STBAPI.SysConfig.Fee500;
			productName = "50"+unit;
			priceDesc = "5元";
		}else if(index==3){
			feeCode = STBAPI.SysConfig.Fee1000;
			productName = "100"+unit;
			priceDesc = "10元";
		} 
	}

	public void AfterRechage(String s, Account account) {
		if (account != null && account.getResult() == 0){
			PopupText pt = UIResource.getInstance().buildDefaultPopupText();
			pt.setText("充值成功!");
			pt.popup();
		}else{ 
			PopupText pt = UIResource.getInstance().buildDefaultPopupText();
			pt.setText("充值失败!");
			pt.popup();
		}
	}
}
