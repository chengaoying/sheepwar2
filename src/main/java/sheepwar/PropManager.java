package sheepwar;

import cn.ohyeah.itvgame.model.OwnProp;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupText;


public class PropManager implements Common{
	
	public SheepWarGameEngine engine;
	public Prop[] props;   			//玩家道具列表
	
	public int[] propIds = {53,54,55,56,57,58,59,60};		//道具id 
	public int[] propPrice = {2,2,3,3,3,3,5,5};				//道具价格
	
	public PropManager(SheepWarGameEngine engine){
		this.engine = engine;
		props = engine.props;
	}
	
	/*查询玩家道具*/
	public void updateProps(){
		//initProps(props);
		ServiceWrapper sw = engine.getServiceWrapper();
		OwnProp[] pps = sw.queryOwnPropList();
		if(pps==null){
			return;
		}
		for(int i=0;i<props.length;i++){
			for(int j=0;j<pps.length;j++){
				if(pps[j].getPropId()==props[i].getPropId()){
					props[i].setNums(pps[j].getCount());
				}
			}
		}
		
		for(int i=0;i<pps.length;i++){
			System.out.println("ID=="+pps[i].getPropId());
			System.out.println("count=="+pps[i].getCount());
		}
		for(int i=0;i<props.length;i++){
			System.out.println("道具ID=="+props[i].getPropId());
			System.out.println("道具数量=="+props[i].getNums());
		}
	}
	
	/*初始道具设为0*/
	public void initProps(Prop[] p){
		System.out.println("创建道具数据并初始化道具信息");
		for(int i=0;i<p.length;i++){
			Prop prop = new Prop();
			p[i] = prop;
			p[i].setId(i);
			p[i].setNums(0);
			p[i].setPropId(53+i);
			p[i].setPrice(propPrice[i]);
		}
	}
	
	/*根据道具ID查询该道具数量*/
	public int getPropNumsById(int propId){
		int len = props.length;
		for(int i=len-1;i>=0;i--){
			if(props[i].getPropId()==propId){
				return props[i].getNums();
			}
		}
		return -1;
	}
	
	private boolean buyProp(int propId, int propCount, int price, String propName){
		
		ServiceWrapper sw = engine.getServiceWrapper();
		//sw.purchaseProp(propId, propCount, "购买"+propName);
		sw.expend(price, propId, "购买"+propName);
		PopupText pt = UIResource.getInstance().buildDefaultPopupText();
		if (sw.isServiceSuccessful()) {
			pt.setText("购买"+propName+"成功");
		}
		else {
			pt.setText("购买"+propName+"失败, 原因: "+sw.getServiceMessage());
			
		}
		pt.popup();
		return sw.isServiceSuccessful();
	
		/*if (engine.getEngineService().getBalance()1000 >= price) {
			ServiceWrapper sw = engine.getServiceWrapper();
			sw.purchaseProp(propId, 1, "购买"+propName);
			PopupText pt = UIResource.getInstance().buildDefaultPopupText();
			if (sw.isServiceSuccessful()) {
				pt.setText("购买"+propName+"成功");
			}
			else {
				pt.setText("购买"+propName+"失败, 原因: "+sw.getServiceMessage());
				
			}
			pt.popup();
			return sw.isServiceSuccessful();
		}else {
				PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
				pc.setText("游戏币不足,是否充值");
				if (pc.popup() == 0) {
					StateRecharge recharge = new StateRecharge(engine);
					recharge.recharge();
				}
				return false;
		}*/
	}
	
	
	
	/*购买道具*/
	public void purchaseProp(int shopX, int shopY) {
		
		if (shopX == 0 && shopY == 0) {
			int propId = propIds[0];
			if (buyProp(propId, 1, propPrice[0], "时光闹钟")) {
				props[0].setNums(props[0].getNums()+1);
			}
		}
		if (shopX == 0 && shopY == 1) {
			int propId = propIds[1];
			if (buyProp(propId, 1, propPrice[1], "捕狼网")) {
				props[1].setNums(props[1].getNums()+1);
			}
		}
		if (shopX == 0 && shopY == 2) {
			int propId = propIds[2];
			if (buyProp(propId, 1, propPrice[2], "防狼套装")) {
				props[2].setNums(props[2].getNums()+1);
			}
		}
		if (shopX == 0 && shopY == 3) {
			int propId = propIds[3];
			if (buyProp(propId, 1, propPrice[3], "驱狼光波")) {
				props[3].setNums(props[3].getNums()+1);
			}
		}
		if (shopX == 1 && shopY == 0) {
			int propId = propIds[4];
			if (buyProp(propId, 1, propPrice[4], "驱狼竖琴")) {
				props[4].setNums(props[4].getNums()+1);
			}
		}
		if (shopX == 1 && shopY == 1) {
			int propId = propIds[5];
			if (buyProp(propId, 1, propPrice[5], "连发")) {
				props[5].setNums(props[5].getNums()+1);
			}
		}
		if (shopX == 1 && shopY == 2) {
			int propId = propIds[6];
			if (buyProp(propId, 1, propPrice[6], "强力磁石")) {
				props[6].setNums(props[6].getNums()+1);
			}
		}
		if (shopX == 1 && shopY == 3) {
			int propId = propIds[7];
			if (buyProp(propId, 1, propPrice[7], "替身玩偶")) {
				props[7].setNums(props[7].getNums()+1);
			}
		}
	}
	

	/*同步道具*/
	public void sysProps(){
		int[] ids = new int[8];
		int[] counts = new int[8];
		for(int i=0;i<props.length;i++){
			ids[i] = props[i].getPropId();
			counts[i] = props[i].getNums();
		}
		ServiceWrapper sw = engine.getServiceWrapper();
		sw.synProps(ids, counts);
		System.out.println("同步道具:"+sw.isServiceSuccessful());
		for(int i=0;i<props.length;i++){
			System.out.println("道具ID=="+props[i].getPropId());
			System.out.println("道具数量=="+props[i].getNums());
		}
	}
}
