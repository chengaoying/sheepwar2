package sheepwar;

import javax.microedition.midlet.MIDlet;
import cn.ohyeah.stb.game.GameCanvasEngine;

/**
 * 游戏引擎
 * @author Administrator
 */
public class SheepWarGameEngine extends GameCanvasEngine implements Common {
	public static boolean isSupportFavor = false;
	public static int ScrW = 0;
	public static int ScrH = 0;
	
	public static SheepWarGameEngine instance = buildGameEngine();

	private static SheepWarGameEngine buildGameEngine() {
		return new SheepWarGameEngine(SheepWarMIDlet.getInstance());
	}

	public StateMain stateMain;
	public StateGame stateGame;
	public PropManager pm;
	public Prop[] props;

	private SheepWarGameEngine(MIDlet midlet) {
		super(midlet);
		setRelease(false);
		ScrW = screenWidth;
		ScrH = screenHeight;
		stateGame = new StateGame(this);
		stateMain = new StateMain(this,stateGame);
		props = new Prop[8];
		pm = new PropManager(this);
	}

	public int state;
	public int mainIndex, playingIndex;
	//private long gameStartTime;
	public int attainmentId;
	
	protected void loop() {
		
		/*处理键值*/
		switch (state) {   	
		case STATUS_INIT:
			init();
			break;
		case STATUS_MAIN_MENU: 
			stateMain.handleKey(keyState);
			break;
		case STATUS_GAME_PLAYING:
			stateGame.handleKey(keyState);
			break;
		}

		/*显示界面*/
		switch (state) {
		case STATUS_INIT:
			//showInit(g);
			break;
		case STATUS_MAIN_MENU:
			stateMain.show(g);
			break;
		case STATUS_GAME_PLAYING:
			stateGame.show(g);
			break;
		}
		
		/*执行逻辑*/
		switch (state) {
		case STATUS_INIT:
			//showInit(g);
			break;
		case STATUS_MAIN_MENU:
			stateMain.execute();
			break;
		case STATUS_GAME_PLAYING:
			stateGame.execute();
			break;
		}
		
		/*退出游戏*/
		exit();
	}

	private void init() {
		/*gameStartTime = engineService.getCurrentTime().getTime();
		java.util.Date gst = new java.util.Date(gameStartTime);
		int year = DateUtil.getYear(gst);
		int month = DateUtil.getMonth(gst);
		attainmentId = year*100+(month);*/
		
		/*查询道具*/
		pm.queryAllOwnProps();
		
		/*读取成就*/
		readAttainment();
		
		state = STATUS_MAIN_MENU;  
	}
	private void exit(){
		if(stateMain.exit){
			exit = true;
		}
	}
	
	private long recordTime;
	public boolean timePass(int millisSeconds) {
		long curTime = System.currentTimeMillis();
		if (recordTime <= 0) {
			recordTime = curTime;
		}
		else {
			if (curTime-recordTime >= millisSeconds) {
				recordTime = 0;
				return true;
			}
		}
		return false;
	}
	
	/*保存游戏成就*/
	public void saveAttainment(Role own){/*
		byte record[];
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);
		try {
			dout.writeByte(own.hitBuble);
			dout.writeByte(own.hitFruits);
			dout.writeByte(own.hitNum);
			dout.writeByte(own.hitTotalNum);
			dout.writeByte(own.useProps);
			dout.writeByte(own.attainment);

			printSaveAttainment(own);
			record = bout.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				dout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally{
				try {
					bout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	*/}
	
	/*读取成就*/
	public void readAttainment(){/*
		ByteArrayInputStream bin = new ByteArrayInputStream(ga.getData());
		DataInputStream din = new DataInputStream(bin);
		try{
			StateGame.hitBuble = din.readByte();
			StateGame.hitFruits = din.readByte();
			StateGame.hitNum = din.readByte();
			StateGame.hitTotalNum = din.readByte();
			StateGame.useProps = din.readByte();
			StateGame.attainment = din.readByte();
			
			printReadAttainment();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				din.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally{
				try {
					bin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		System.out.println("读取成就状态："+sw.getServiceResult());
	*/}
	
	/*private void printSaveAttainment(Role own){
		System.out.println("own.hitBuble="+own.hitBuble);
		System.out.println("own.hitFruits="+own.hitFruits);
		System.out.println("own.hitNum="+own.hitNum);
		System.out.println("own.hitTotalNum="+own.hitTotalNum);
		System.out.println("own.useProps="+own.useProps);
		System.out.println("own.attainment="+own.attainment);
		System.out.println("own.scores="+own.scores);
	}
	
	private void printReadAttainment(){
		System.out.println("StateGame.hitBuble="+StateGame.hitBuble);
		System.out.println("StateGame.hitFruits="+StateGame.hitFruits);
		System.out.println("StateGame.hitNum="+StateGame.hitNum);
		System.out.println("StateGame.hitTotalNum="+StateGame.hitTotalNum);
		System.out.println("StateGame.useProps="+StateGame.useProps);
		System.out.println("StateGame.attainment="+StateGame.attainment);
		System.out.println("StateGame.scores="+StateGame.scores);
	}*/

	public String getAppProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getAppStartMillis() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void quitCurrentState() {
		// TODO Auto-generated method stub
		
	}

	public boolean isOffline() {
		// TODO Auto-generated method stub
		return false;
	}
}
