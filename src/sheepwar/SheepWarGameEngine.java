package sheepwar;

import javax.microedition.midlet.MIDlet;

import com.zte.iptv.j2me.stbapi.Account;
import com.zte.iptv.j2me.stbapi.GameData;
import com.zte.iptv.j2me.stbapi.STBAPI;

import cn.ohyeah.stb.game.GameCanvasEngine;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupText;
import cn.ohyeah.stb.util.ConvertUtil;

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
		if(instance==null){
			return new SheepWarGameEngine(SheepWarMIDlet.getInstance());
		}else{
			return instance;
		}
	}

	public StateMain stateMain;
	public StateGame stateGame;
	public PropManager pm;
	public Prop[] props;
	public String amountUnit = "游戏币";
	
	private String recordId;
	private String data;
	
	public String achi[];
	public String p[];
	
	public Account account;
	
	/**
	 * 存放成就的二维数组，第一维是成就类型，第二维是某一成就类型中的一个成就对象
	 */
	public Attainment[][] attainments;
	

	private SheepWarGameEngine(MIDlet midlet) {
		super(midlet);
		setRelease(false);
		ScrW = screenWidth;
		ScrH = screenHeight;
		stateGame = new StateGame(this);
		stateMain = new StateMain(this);
		props = new Prop[8];
		achi = new String[6];
		p = new String[8];
		pm = new PropManager(this);
		pm.props = props;
		attainments = new Attainment[6][5];
	}

	public int state;
	public int mainIndex, playingIndex;
	//private long gameStartTime;
	//public int recordId;
	
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
		/*case STATUS_GAME_RECHARGE:  
			if(!isRecharge){
				recharge();
			}
			break;*/
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
	
	

	/*初始化玩家成就*/
	private void initAttainmen(){
		Attainment[] ams;
		Attainment attainment;
		for(int i=0;i<Attainments.length;i++){
			ams = new Attainment[5];
			for(int j=0;j<Attainments[i].length;j++){
				attainment = new Attainment();
				attainment.setId(j);
				attainment.setName(Attainments[i][j][0]);
				attainment.setDesc(Attainments[i][j][1]);
				attainment.setAward(Integer.parseInt(Attainments[i][j][2]));
				attainment.setType(i);
				
				/*判断玩家是否达到成就条件*/
				setAttainmentResult(attainment, i, j);
				ams[j] = attainment;
			}
			attainments[i] = ams;
		}
	}
	
	/*更新玩家成就(主要是标记玩家是否达到某一成就的条件)*/
	public void updateAttainmen(){
		this.initAttainmen();
	}
	
	private void setAttainmentResult(Attainment attainment, int i, int j){
		if(attainment.getType()==Attainment_Type_Scores){
			if(StateGame.scores3>=Integer.parseInt(Attainments[i][j][3])){
				attainment.setResult(true);
			}else{
				attainment.setResult(false);
			}
		}else if(attainment.getType()==Attainment_Type_HitWolf){
			if(StateGame.hitTotalNum>=Integer.parseInt(Attainments[i][j][3])){
				attainment.setResult(true);
			}else{
				attainment.setResult(false);
			}
		}else if(attainment.getType()==Attainment_Type_HitBomb){
			if(StateGame.hitBooms>=Integer.parseInt(Attainments[i][j][3])){
				attainment.setResult(true);
			}else{
				attainment.setResult(false);
			}
		}else if(attainment.getType()==Attainment_Type_UseProp){
			if(StateGame.useProps>=Integer.parseInt(Attainments[i][j][3])){
				attainment.setResult(true);
			}else{
				attainment.setResult(false);
			}
		}else if(attainment.getType()==Attainment_Type_HitFruit){
			if(StateGame.hitFruits>=Integer.parseInt(Attainments[i][j][3])){
				attainment.setResult(true);
			}else{
				attainment.setResult(false);
			}
		}else if(attainment.getType()==Attainment_Type_Level){
			if(StateGame.level>=Integer.parseInt(Attainments[i][j][3])){
				attainment.setResult(true);
			}else{
				attainment.setResult(false);
			}
		}
	}

	private void init() {
		
		/*查用户余额*/
		queryBalance();
		
		/*创建道具数组*/
		pm.initProps(props);
		
		setRecordId();
		//saveRecord();
		loadGameRecord();

		/*初始化道具信息*/
		pm.updateProps();
		
		/*初始化玩家成就信息*/
		initAttainmen();
		state = STATUS_MAIN_MENU;  
	}
	
	public void queryBalance() {
		try {
			account = STBAPI.GetBalance();
			System.out.println("查询余额");
		} catch (Exception e) {
			PopupText pt = UIResource.getInstance().buildDefaultPopupText();
			pt.setText("查询余额失败，原因："+e.getMessage());
			pt.popup();
		} 
	}


	private void loadGameRecord(){
		try	{
		    GameData  gamedata = STBAPI.LoadGameData(recordId);
		    System.out.println("LoadGameData:");
		    System.out.println("    Result   ="   + gamedata.getResult());
		    System.out.println("    SaveID   ="   + gamedata.getSaveID());
		    System.out.println("    DataValue="   + gamedata.getDataValue());
		    
		    /*解析成就和道具数据*/
		    String data = gamedata.getDataValue();
		    String[] ds = ConvertUtil.split(data, "|");
		    String[] acrs = ConvertUtil.split(ds[0], "/");
		    String[] props = ConvertUtil.split(ds[1], "/");
		    
		    String[] str = null;
		    for(int i=0;i<acrs.length;i++){
		    	str = ConvertUtil.split(acrs[i], ":");
		    	achi[i] = str[1];
		    }
		    
		    StateGame.scores3 = Integer.parseInt(achi[0]);
		    StateGame.hitTotalNum = Integer.parseInt(achi[1]);
		    StateGame.hitBooms = Integer.parseInt(achi[2]);
		    StateGame.useProps = Integer.parseInt(achi[3]);
		    StateGame.hitFruits = Integer.parseInt(achi[4]);
		    StateGame.level = Short.parseShort(achi[5]);
		    
		    for(int j=0;j<props.length;j++){
		    	str = ConvertUtil.split(props[j], ":");
		    	p[j] = str[1];
		    }
		} catch (Exception e){
			System.out.println("获取加载游戏数据失败，原因："+e.getMessage());
		}
	}

	/*data前半部分是成就，后半部分是道具*/
	public void setData() {
		int scores = StateGame.scores>StateGame.scores3?StateGame.scores:StateGame.scores3;
		data = "scores:"+scores+"/hitTotalNum:"+StateGame.hitTotalNum
					+"/hitBooms:"+StateGame.hitBooms+"/useProps:"+StateGame.useProps
					+"/hitFruits:"+StateGame.hitFruits+"/level:"+StateGame.level
					+"|"
					+"0:"+props[0].getNums()+"/1:"+props[1].getNums()
					+"/2:"+props[2].getNums()+"/3:"+props[3].getNums()
					+"/4:"+props[4].getNums()+"/5:"+props[5].getNums()
					+"/6:"+props[6].getNums()+"/7:"+props[7].getNums();
		print(scores);
	}
	
	private void print(int scores){
		System.out.println("scores:"+scores);
		System.out.println("/hitTotalNum:"+StateGame.hitTotalNum);
		System.out.println("/hitBooms:"+StateGame.hitBooms);
		System.out.println("/useProps:"+StateGame.useProps);
		System.out.println("/hitFruits:"+StateGame.hitFruits);
		for(int i=0;i<props.length-1;i++){
			System.out.println(i+"："+props[i].getNums());
		}
	}
	
	public String setRecordId() {
		try
		{
		    String Date = STBAPI.GetServiceDate();
		    recordId = Date.substring(0,8);
		    System.out.println("GetServiceDate: Date=" + Date);
		    System.out.println("GetServiceDate: Date=" + recordId);
		} 
		catch (Exception e)
		{
		   System.out.println("获取系统时间失败，原因："+e.getMessage());
		}
		return recordId;
	}
	
	/*保存游戏成就*/
	public void saveRecord(){
		/*存玩家道具和成就*/
		try
		{
			setData();
			int result = STBAPI.SaveGameData(recordId,"我的存档",data);
			System.out.println("SaveGameData: result=" + Integer.toString(result));
		} 
		catch (Exception e)
		{
			 System.out.println("获取保存记录失败，原因："+e.getMessage());
		}
		
		/*上报积分*/
		try
		{
			int scores = StateGame.scores>StateGame.scores3?StateGame.scores:StateGame.scores3;
			int result = STBAPI.ReportScore (STBAPI.SysConfig.RankID,scores);
			System.out.println("ReportScore: result=" + Integer.toString(result));
		} 
		catch (Exception e)
		{
			System.out.println("获取上报积分失败，原因："+e.getMessage());
		}
	}
	
}
