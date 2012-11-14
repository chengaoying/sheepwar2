package sheepwar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.microedition.midlet.MIDlet;

import cn.ohyeah.itvgame.model.GameAttainment;
import cn.ohyeah.itvgame.model.GameRecord;
import cn.ohyeah.stb.game.GameCanvasEngine;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.util.DateUtil;

/**
 * 游戏引擎
 * @author Administrator
 */
public class SheepWarGameEngine extends GameCanvasEngine implements Common {
	
	public static boolean BATE_VERSION = true;	//广东公测版本
	
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
	//public String amountUnit;
	
	private int recordId;
	private int attainmentId;
	
	public static boolean result;   	//是否有游戏记录
	public static boolean isFirstGame = true;   //是否第一次玩游戏
	
	/**
	 * 存放成就的二维数组，第一维是成就类型，第二维是某一成就类型中的一个成就对象
	 */
	public Attainment[][] attainments;
	

	private SheepWarGameEngine(MIDlet midlet) {
		super(midlet);
		setRelease(true);
		ScrW = screenWidth;
		ScrH = screenHeight;
		stateGame = new StateGame(this);
		stateMain = new StateMain(this);
		props = new Prop[8];
		pm = new PropManager(this);
		pm.props = props;
		attainments = new Attainment[6][5];
	}

	public int state;
	public int mainIndex, playingIndex;
	
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
	public void initAttainmen(){
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
		//每次更新成就前重新赋值
		StateGame.scores3 = StateGame.scores3>StateGame.scores?StateGame.scores3:StateGame.scores;
	    StateGame.hitTotalNum2 = StateGame.hitTotalNum2>StateGame.hitTotalNum?StateGame.hitTotalNum2:StateGame.hitTotalNum;
	    StateGame.hitBooms2 = StateGame.hitBooms2>StateGame.hitBooms?StateGame.hitBooms2:StateGame.hitBooms;
	    StateGame.useProps2 = StateGame.useProps2>StateGame.useProps?StateGame.useProps2:StateGame.useProps;
	    StateGame.hitFruits2 = StateGame.hitFruits2>StateGame.hitFruits?StateGame.hitFruits2:StateGame.hitFruits;
	    StateGame.level2 = StateGame.level2>StateGame.level?StateGame.level2:StateGame.level;
		
		Attainment attainment;
		for(int i=0;i<attainments.length;i++){
			for(int j=0;j<attainments[i].length;j++){
				attainment = attainments[i][j];
				/*判断玩家是否达到成就条件*/
				setAttainmentResult(attainment, i, j);
			}
		}
	}
	
	private void setAttainmentResult(Attainment attainment, int i, int j){
		if(attainment.getType()==Attainment_Type_Scores){
			if(attainment.isResult()==false&&StateGame.scores3>=Integer.parseInt(Attainments[i][j][3])){
				attainment.setResult(true);
				StateGame.attainment += attainment.getAward();
			}
		}else if(attainment.getType()==Attainment_Type_HitWolf){
			if(attainment.isResult()==false&&StateGame.hitTotalNum2>=Integer.parseInt(Attainments[i][j][3])){
				attainment.setResult(true);
				StateGame.attainment += attainment.getAward();
			}
		}else if(attainment.getType()==Attainment_Type_HitBomb){
			if(attainment.isResult()==false&&StateGame.hitBooms2>=Integer.parseInt(Attainments[i][j][3])){
				attainment.setResult(true);
				StateGame.attainment += attainment.getAward();
			}
		}else if(attainment.getType()==Attainment_Type_UseProp){
			if(attainment.isResult()==false&&StateGame.useProps2>=Integer.parseInt(Attainments[i][j][3])){
				attainment.setResult(true);
				StateGame.attainment += attainment.getAward();
			}
		}else if(attainment.getType()==Attainment_Type_HitFruit){
			if(attainment.isResult()==false&&StateGame.hitFruits2>=Integer.parseInt(Attainments[i][j][3])){
				attainment.setResult(true);
				StateGame.attainment += attainment.getAward();
			}
		}else if(attainment.getType()==Attainment_Type_Level){
			if(attainment.isResult()==false&&StateGame.level2>=Integer.parseInt(Attainments[i][j][3])){
				attainment.setResult(true);
				StateGame.attainment += attainment.getAward();
			}
		}
	}

	private void init() {
		
		/*查用户余额*/
		//queryBalance();
		
		/*创建道具数组*/
		pm.initProps(props);
		
		/*初始化道具信息*/
		pm.updateProps();
		
		setRecordId();
		
		/*读取游戏记录*/
		readRecord();
		
		/*初始化玩家成就信息*/
		initAttainmen();
		state = STATUS_MAIN_MENU;  
	}
	

	public void setRecordId() {
		try
		{
		    Date date = new Date(getEngineService().getCurrentTime().getTime());
		    int year = DateUtil.getYear(date);
			int month = DateUtil.getMonth(date);
		    recordId = year*100+(month+1);
		    attainmentId = year*100+(month+1);
		    System.out.println("GetServiceDate: Date=" + date);
		    System.out.println("GetServiceDate: Date=" + recordId);
		} 
		catch (Exception e)
		{
		   System.out.println("获取系统时间失败，原因："+e.getMessage());
		   state = STATUS_MAIN_MENU;
		}
	}
	
	/*保存游戏成就(用于排行的积分)*/
	public void saveAttainment(){
		ServiceWrapper sw = getServiceWrapper();
		GameAttainment ga = sw.readAttainment(attainmentId);
		if((ga==null && StateGame.scores>0) || (ga!=null && StateGame.scores>ga.getScores())){
			GameAttainment attainment = new GameAttainment();
			attainment.setAttainmentId(attainmentId);
			attainment.setScores(StateGame.scores);
			attainment.setRemark("游戏积分");
			sw.saveAttainment(attainment);
		}
	}
	
	private void setRecordData(DataOutputStream dout) throws IOException{
		//dout.write(StateGame.scores);
		//dout.write(StateGame.scores2);
		dout.write(StateGame.hitTotalNum);
		dout.write(StateGame.hitBooms);
		dout.write(StateGame.useProps);
		dout.write(StateGame.lifeNum);
		dout.write(StateGame.hitFruits);
		dout.writeShort(StateGame.level);
		dout.writeShort(StateGame.rewardLevel);
		dout.write(StateGame.hitNum);
		dout.write(StateGame.hitRatio);
		
		dout.writeBoolean(StateGame.isFourRepeating);
		dout.writeBoolean(StateGame.second);
		dout.writeBoolean(StateGame.pasueState);
		dout.writeBoolean(StateGame.isUsePasue);
		dout.write((int)(StateGame.pasueTimeE-StateGame.pasueTimeS));
		dout.writeBoolean(StateGame.protectState);
		dout.write((int)(StateGame.proEndTime-StateGame.proStartTime));
		dout.writeBoolean(StateGame.isUseGlove);
		dout.writeBoolean(StateGame.golveFlag);
		dout.writeBoolean(StateGame.isShowGlove);
		dout.write((int) (StateGame.gloveEndTime-StateGame.gloveStartTime));
		
		dout.writeBoolean(StateGame.isRewardLevel);
		dout.writeBoolean(StateGame.isReward);
		dout.write(StateGame.reward_nums);
		dout.writeShort(StateGame.batch);
		dout.writeBoolean(StateGame.rewardLevelFail);
		dout.writeBoolean(StateGame.HASWOLF_ONE);
		dout.writeBoolean(StateGame.HASWOLF_TWO);
		dout.writeBoolean(StateGame.HASWOLF_THREE);
		dout.writeBoolean(StateGame.HASWOLF_FOUR);
		dout.writeBoolean(StateGame.IS_FOUR_WOLF);
		
		/*成就信息*/
		StateGame.scores3 = StateGame.scores3>StateGame.scores?StateGame.scores3:StateGame.scores;
	    StateGame.hitTotalNum2 = StateGame.hitTotalNum2>StateGame.hitTotalNum?StateGame.hitTotalNum2:StateGame.hitTotalNum;
	    StateGame.hitBooms2 = StateGame.hitBooms2>StateGame.hitBooms?StateGame.hitBooms2:StateGame.hitBooms;
	    StateGame.useProps2 = StateGame.useProps2>StateGame.useProps?StateGame.useProps2:StateGame.useProps;
	    StateGame.hitFruits2 = StateGame.hitFruits2>StateGame.hitFruits?StateGame.hitFruits2:StateGame.hitFruits;
	    StateGame.level2 = StateGame.level2>StateGame.level?StateGame.level2:StateGame.level;
	    
	    //dout.write(StateGame.scores3);
	    dout.write(StateGame.hitTotalNum2);
	    dout.write(StateGame.hitBooms2);
	    dout.write(StateGame.useProps2);
	    dout.write(StateGame.hitFruits2);
	    dout.write(StateGame.level2);
	}
	
	private void initRecordInfo(DataInputStream dis) throws IOException{
			//StateGame.scores = dis.read();
			//StateGame.scores2 = dis.read();
			StateGame.hitTotalNum = dis.read();
			StateGame.hitBooms = dis.read();
			StateGame.useProps = dis.read();
			StateGame.lifeNum =dis.read();
			StateGame.hitFruits = dis.read();
			StateGame.level = dis.readShort();
			StateGame.rewardLevel = dis.readShort();
			StateGame.hitNum = dis.read();
			StateGame.hitRatio = dis.read();
			
			StateGame.isFourRepeating = dis.readBoolean();
			StateGame.second = dis.readBoolean();
			StateGame.pasueState = dis.readBoolean();
			StateGame.isUsePasue = dis.readBoolean();
			StateGame.pasueValideTime = dis.read();
			StateGame.protectState = dis.readBoolean();
			StateGame.protectValideTime = dis.read();
			StateGame.isUseGlove = dis.readBoolean();
			StateGame.golveFlag = dis.readBoolean();
			StateGame.isShowGlove = dis.readBoolean();
			StateGame.gloveValideTime = dis.read();
			
			StateGame.isRewardLevel = dis.readBoolean();
			StateGame.isReward = dis.readBoolean();
			StateGame.reward_nums = dis.read();
			StateGame.batch = dis.readShort();
			StateGame.rewardLevelFail = dis.readBoolean();
			StateGame.HASWOLF_ONE = dis.readBoolean();
			StateGame.HASWOLF_TWO = dis.readBoolean();
			StateGame.HASWOLF_THREE = dis.readBoolean();
			StateGame.HASWOLF_FOUR = dis.readBoolean();
			StateGame.IS_FOUR_WOLF = dis.readBoolean();
			
			//StateGame.scores3 = dis.read();
		    StateGame.hitTotalNum2 = dis.read();
		    StateGame.hitBooms2 = dis.read();
		    StateGame.useProps2 = dis.read();
		    StateGame.hitFruits2 = dis.read();
		    StateGame.level2 = dis.read();
	}
	
	/*保存游戏记录*/
	public void saveRecord(){
		byte record[];
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);
		ServiceWrapper sw = getServiceWrapper();
		try {
			setRecordData(dout);
			printGameInfo();
			record = bout.toByteArray();
			GameRecord gameRecord = new GameRecord();
			gameRecord.setData(record);
			gameRecord.setScores(StateGame.scores);
			gameRecord.setPlayDuration(StateGame.scores2);
			gameRecord.setRemark(String.valueOf(StateGame.scores3));
			gameRecord.setRecordId(recordId);
			sw.saveRecord(gameRecord);
		} catch (IOException e) {
			System.out.println("保存游戏失败，原因："+e.getMessage());
			state = STATUS_MAIN_MENU;
		} finally{
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
	}
	
	/*读取游戏记录*/
	public boolean readRecord(){
		ServiceWrapper sw = getServiceWrapper();
		GameRecord gameRecord = sw.readRecord(recordId);
		if(!sw.isServiceSuccessful() || gameRecord==null){
			return result = false;
		}
		ByteArrayInputStream bin = new ByteArrayInputStream(gameRecord.getData());
		DataInputStream din = new DataInputStream(bin);
		try {
			initRecordInfo(din);
			StateGame.scores = gameRecord.getScores();
			StateGame.scores2 = gameRecord.getPlayDuration();
			StateGame.scores3 = Integer.parseInt(gameRecord.getRemark()==null?"0":gameRecord.getRemark());
			printGameInfo();
			isFirstGame = false;  //玩家不是第一次玩
			return result = true;
		} catch (Exception e) {
			System.out.println("读取游戏失败，原因："+e.getMessage());
			state = STATUS_MAIN_MENU;
			return result = false;
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
	}
	
 	private void printGameInfo() {
 		System.out.println("record_socres:"+StateGame.scores );
 		System.out.println("record_scores2:"+StateGame.scores2 );
 		System.out.println("record_hitTotalNum:"+StateGame.hitTotalNum );
 		System.out.println("record_hitBooms:"+StateGame.hitBooms );
 		System.out.println("record_useProps:"+StateGame.useProps );
 		System.out.println("record_lifeNum:"+StateGame.lifeNum );
 		System.out.println("record_hitFruits:"+StateGame.hitFruits );
 		System.out.println("record_level:"+StateGame.level );
 		System.out.println("record_rewardLevel:"+StateGame.rewardLevel );
 		System.out.println("record_hitNum:"+StateGame.hitNum  );
 		System.out.println("record_hitRatio:"+StateGame.hitRatio  );
		
 		System.out.println("record_isFourRepeating:"+StateGame.isFourRepeating  );
 		System.out.println("record_second:"+StateGame.second  );
 		System.out.println("record_pasueState:"+StateGame.pasueState );
 		System.out.println("record_isUsePasue:"+StateGame.isUsePasue );
 		System.out.println("record_pasueValideTime:"+StateGame.pasueValideTime );
 		System.out.println("record_protectState:"+StateGame.protectState );
 		System.out.println("record_protectValideTime:"+StateGame.protectValideTime );
 		System.out.println("record_isUseGlove:"+StateGame.isUseGlove );
 		System.out.println("record_golveFlag:"+StateGame.golveFlag );
 		System.out.println("record_isShowGlove:"+StateGame.isShowGlove );
 		System.out.println("record_gloveValideTime:"+StateGame.gloveValideTime );
		
 		System.out.println("record_isRewardLevel:"+StateGame.isRewardLevel );
 		System.out.println("record_isReward:"+StateGame.isReward );
 		System.out.println("record_reward_nums:"+StateGame.reward_nums );
 		System.out.println("record_batch:"+StateGame.batch );
 		System.out.println("record_rewardLevelFail:"+StateGame.rewardLevelFail );
 		System.out.println("record_HASWOLF_ONE:"+StateGame.HASWOLF_ONE );
 		System.out.println("record_HASWOLF_TWO:"+StateGame.HASWOLF_TWO );
 		System.out.println("record_HASWOLF_THREE:"+StateGame.HASWOLF_THREE );
 		System.out.println("record_HASWOLF_FOUR:"+StateGame.HASWOLF_FOUR );
 		System.out.println("record_IS_FOUR_WOLF:"+StateGame.IS_FOUR_WOLF );
 		
 		System.out.println("StateGame.scores3 "+StateGame.scores3 );
 		System.out.println("StateGame.hitTotalNum2 "+StateGame.hitTotalNum2 );
 		System.out.println("StateGame.hitBooms2 "+StateGame.hitBooms2 );
 		System.out.println("StateGame.useProps2 "+StateGame.useProps2 );
 		System.out.println("StateGame.hitFruits2 "+StateGame.hitFruits2 );
 		System.out.println("StateGame.level2 "+StateGame.level2 );
	}
}
