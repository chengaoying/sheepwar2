package sheepwar;

import javax.microedition.midlet.MIDlet;
import cn.ohyeah.stb.game.GameCanvasEngine;
import cn.ohyeah.stb.game.ServiceWrapper;
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
	//public String amountUnit;
	
	private int recordId = 3;
	private String recordData;
	private String attainmentData;
	public String[] gameRecordInfo;
	public String[] gameAttainmentInfo;
	
	//public static boolean result;   	//是否有游戏记录
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
	
	public void loadAttainmenInfo(){
		ServiceWrapper sw = getServiceWrapper();
		String data = sw.loadGobalData();
		if(data==null){
			return ;
		}
		
		String[] ds = ConvertUtil.split(data, ",");
	    gameAttainmentInfo = new String[ds.length];
	    for(int i=0;i<ds.length;i++){
	    	gameAttainmentInfo[i] = ConvertUtil.split(ds[i], ":")[1];
	    }
	    initAttainmentInfo(gameAttainmentInfo);
	    printAttainmentInfo();
	}
	
	public void saveAttainmentInfo(){
		ServiceWrapper sw = getServiceWrapper();
		setAttainmentInfo();
		sw.saveGobalData(attainmentData);
		if(sw.isServiceSuccessful()){
			printAttainmentInfo();
		}
	}
	
	private void setAttainmentInfo(){
		//每次更新成就前重新赋值
		StateGame.scores3 = StateGame.scores3>StateGame.scores?StateGame.scores3:StateGame.scores;
	    StateGame.hitTotalNum2 = StateGame.hitTotalNum2>StateGame.hitTotalNum?StateGame.hitTotalNum2:StateGame.hitTotalNum;
	    StateGame.hitBooms2 = StateGame.hitBooms2>StateGame.hitBooms?StateGame.hitBooms2:StateGame.hitBooms;
	    StateGame.useProps2 = StateGame.useProps2>StateGame.useProps?StateGame.useProps2:StateGame.useProps;
	    StateGame.hitFruits2 = StateGame.hitFruits2>StateGame.hitFruits?StateGame.hitFruits2:StateGame.hitFruits;
	    StateGame.level2 = StateGame.level2>StateGame.level?StateGame.level2:StateGame.level;
	    
	    attainmentData = "scores3:"+StateGame.scores3
	    				 +",hitTotalNum2:"+StateGame.hitTotalNum2
	    				 +",hitBooms2:"+StateGame.hitBooms2
	    				 +",useProps2:"+StateGame.useProps2
	    				 +",hitFruits2:"+StateGame.hitFruits2
	    				 +",level2:"+StateGame.level2;
	}
	
	private void initAttainmentInfo(String[] str){
		StateGame.scores3 = Integer.parseInt(str[0]);
		StateGame.hitTotalNum2 = Integer.parseInt(str[1]);
		StateGame.hitBooms2 = Integer.parseInt(str[2]);
		StateGame.useProps2 = Integer.parseInt(str[3]);
		StateGame.hitFruits2 = Integer.parseInt(str[4]);
		StateGame.level2 = Integer.parseInt(str[5]);
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
		setAttainmentInfo();
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

		try {
			/* 初始化道具信息 */
			pm.queryProps();
		} catch (Exception e) {
			errorMessage += "\n初始化道具出错" + e.getMessage();
		}

		try {
			/* 读取游戏记录 */
			readRecord();
		} catch (Exception e) {
			errorMessage += "\n读取游戏记录出错" + e.getMessage();
		}

		try {
			loadAttainmenInfo();
		} catch (Exception e) {
			errorMessage += "\n读取全局数据出错" + e.getMessage();
		}

		/* 初始化玩家成就信息 */
		initAttainmen();
		state = STATUS_MAIN_MENU;
	}
	
	/*保存游戏成就(用于排行的积分)*/
	public void saveAttainment(){
		ServiceWrapper sw = getServiceWrapper();
		String data = sw.loadRanking(3);
		int myScore = sw.getMyScore(data);
		if(StateGame.scores > myScore){
			sw.saveScore(StateGame.scores);
		}
		
	}
	
	private void setRecordData(){
		recordData = "scores:"+StateGame.scores
					+"/sorces:"+StateGame.scores2
					+"/hitTotalNum:"+StateGame.hitTotalNum
					+"/hitBooms:"+StateGame.hitBooms
					+"/useProps:"+StateGame.useProps
					+"/lifeNum:"+StateGame.lifeNum
					+"/hitFruits:"+StateGame.hitFruits
					+"/level:"+StateGame.level
					+"/hitNum:"+StateGame.hitNum
					+"/hitRatio:"+StateGame.hitRatio
					
					+"/isFourRepeating:"+StateGame.isFourRepeating
					+"/second:"+StateGame.second
					+"/pasueState:"+StateGame.pasueState
					+"/isUsePasue:"+StateGame.isUsePasue
					+"/pasueTime:"+(StateGame.pasueTimeE-StateGame.pasueTimeS)
					+"/protectState:"+StateGame.protectState
					+"/protectTime:"+(StateGame.proEndTime-StateGame.proStartTime)
					+"/isUseGlove:"+StateGame.isUseGlove
					+"/golveFlag:"+StateGame.golveFlag
					+"/isGolveShow:"+StateGame.isShowGlove
					+"/gloveValideTime:"+(StateGame.gloveEndTime-StateGame.gloveStartTime)
					
					+"/isRewardLevel:"+StateGame.isRewardLevel
					+"/isReward:"+StateGame.isReward
					+"/reward_nums:"+StateGame.reward_nums
					+"/batch:"+StateGame.batch
					+"/rewardLevelFail:"+StateGame.rewardLevelFail
					+"/haswolf_one:"+StateGame.HASWOLF_ONE
					+"/haswolf_two:"+StateGame.HASWOLF_TWO
					+"/haswolf_three:"+StateGame.HASWOLF_THREE
					+"/haswolf_four:"+StateGame.HASWOLF_FOUR
					+"/isfourwolf:"+StateGame.IS_FOUR_WOLF;
	}
	
	private void initRecordInfo(String[] str){
		StateGame.scores = Integer.parseInt(str[0]);
		StateGame.scores2 = Integer.parseInt(str[1]);
		StateGame.hitTotalNum = Integer.parseInt(str[2]);
		StateGame.hitBooms = Integer.parseInt(str[3]);
		StateGame.useProps = Integer.parseInt(str[4]);
		StateGame.lifeNum =Integer.parseInt(str[5]);
		StateGame.hitFruits = Integer.parseInt(str[6]);
		StateGame.level = Short.parseShort(str[7]);
		StateGame.hitNum = Integer.parseInt(str[8]);
		StateGame.hitRatio = Integer.parseInt(str[9]);
		
		StateGame.isFourRepeating = str[10].equals("true")?true:false;
		StateGame.second = str[11].equals("true")?true:false;
		StateGame.pasueState = str[12].equals("true")?true:false;
		StateGame.isUsePasue = str[13].equals("true")?true:false;
		StateGame.pasueValideTime = Integer.parseInt(str[14]);
		StateGame.protectState = str[15].equals("true")?true:false;
		StateGame.protectValideTime = Integer.parseInt(str[16]);
		StateGame.isUseGlove = str[17].equals("true")?true:false;
		StateGame.golveFlag = str[18].equals("true")?true:false;
		StateGame.isShowGlove = str[19].equals("true")?true:false;
		StateGame.gloveValideTime = Integer.parseInt(str[20]);
		
		StateGame.isRewardLevel = str[21].equals("true")?true:false;
		StateGame.isReward = str[22].equals("true")?true:false;
		StateGame.reward_nums = Integer.parseInt(str[23]);
		StateGame.batch = Short.parseShort(str[24]);
		StateGame.rewardLevelFail = str[25].equals("true")?true:false;
		StateGame.HASWOLF_ONE = str[26].equals("true")?true:false;
		StateGame.HASWOLF_TWO = str[27].equals("true")?true:false;
		StateGame.HASWOLF_THREE = str[28].equals("true")?true:false;
		StateGame.HASWOLF_FOUR = str[29].equals("true")?true:false;
		StateGame.IS_FOUR_WOLF = str[30].equals("true")?true:false;
	}
	
	/*保存游戏记录*/
	public void saveRecord(){
		ServiceWrapper sw = getServiceWrapper();
		setRecordData();
		sw.saveRecord(recordId, recordData);
		if(sw.isServiceSuccessful()){
			printRecordInfo();
		}
	}
	
	/*读取游戏记录*/
	public boolean readRecord(){
		try{
			ServiceWrapper sw = getServiceWrapper();
			String data = sw.loadRecord(recordId);
			if(data==null){
				return false;
			}
			String[] ds = ConvertUtil.split(data, "/");
		    gameRecordInfo = new String[ds.length];
		    for(int i=0;i<ds.length;i++){
		    	gameRecordInfo[i] = ConvertUtil.split(ds[i], ":")[1];
		    }
		    initRecordInfo(gameRecordInfo);
		    printRecordInfo();
		    return true;
		}catch(Exception e){
			return false;
		}
	}
	
 	private void printRecordInfo() {
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
	}
 	
 	private void printAttainmentInfo(){
 		System.out.println("StateGame.scores3 "+StateGame.scores3 );
 		System.out.println("StateGame.hitTotalNum2 "+StateGame.hitTotalNum2 );
 		System.out.println("StateGame.hitBooms2 "+StateGame.hitBooms2 );
 		System.out.println("StateGame.useProps2 "+StateGame.useProps2 );
 		System.out.println("StateGame.hitFruits2 "+StateGame.hitFruits2 );
 		System.out.println("StateGame.level2 "+StateGame.level2 );
 	}
}
