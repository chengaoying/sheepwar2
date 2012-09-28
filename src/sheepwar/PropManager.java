package sheepwar;


public class PropManager implements Common{
	
	public SheepWarGameEngine engine;
	public Prop[] props;   			//��ҵ����б�
	
	public int[] propIds = {53,54,55,56,57,58,59,60};		//����id 
	public int[] propPrice = {20,20,30,30,30,30,50,50};		//���߼۸�
	
	public PropManager(SheepWarGameEngine e){
		this.engine = e;
		props = engine.props;
	}
	
	/*��ѯ��ҵ���*/
	public void queryAllOwnProps(){/*
		
		initProps(props);
		ServiceWrapper sw = engine.getServiceWrapper();
		OwnProp[] propList = sw.queryOwnPropList();
		if(propList==null){
			return;
		}
		for(int i=0;i<propList.length;i++){
			if(propList[i].getPropId()==propIds[0]){
				props[0].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==propIds[1]){
				props[1].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==propIds[2]){
				props[2].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==propIds[3]){
				props[3].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==propIds[4]){
				props[4].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==propIds[5]){
				props[5].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==propIds[6]){
				props[6].setNums(propList[i].getCount());
			}
			if(propList[i].getPropId()==propIds[7]){
				props[7].setNums(propList[i].getCount());
			}
		}
		
		for(int i=0;i<props.length;i++){
			System.out.println("����ID=="+props[i].getPropId());
			System.out.println("��������=="+props[i].getNums());
		}
	*/}
	
	/*��ʼ������Ϊ0*/
	public void initProps(Prop[] p){
		System.out.println("��ʼ������");
		for(int i=0;i<p.length;i++){
			Prop prop = new Prop();
			p[i] = prop;
			p[i].setId(i);
			p[i].setNums(0);
			p[i].setPropId(53+i);
			p[i].setPrice(propPrice[i]);
		}
	}
	
	/*���ݵ���ID��ѯ�õ�������*/
	public int getPropNumsById(int propId){
		int len = props.length;
		for(int i=len-1;i>=0;i--){
			if(props[i].getPropId()==propId){
				return props[i].getNums();
			}
		}
		return -1;
	}
	
	//private boolean buyProp(int propId, int propCount, int price, String propName){
	/*
		if (engine.getEngineService().getBalance() >= price) {
			ServiceWrapper sw = engine.getServiceWrapper();
			sw.purchaseProp(propId, propCount, "����"+propName);
			PopupText pt = UIResource.getInstance().buildDefaultPopupText();
			if (sw.isServiceSuccessful()) {
				pt.setText("����"+propName+"�ɹ�");
			}
			else {
				pt.setText("����"+propName+"ʧ��, ԭ��: "+sw.getServiceMessage());
				
			}
			pt.popup();
			return sw.isServiceSuccessful();
		}
		else {
			PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
			pc.setText("��Ϸ�Ҳ���,�Ƿ��ֵ");
			if (pc.popup() == 0) {
				StateRecharge recharge = new StateRecharge(engine);
				recharge.recharge();
			}
			return false;
		}
	*///}
	
	/*�������*/
	public void purchaseProp(int shopX, int shopY) {/*
		
		if (shopX == 0 && shopY == 0) {
			int propId = propIds[0];
			if (buyProp(propId, 1, propPrice[0], "ʱ������")) {
				props[0].setNums(props[0].getNums()+1);
			}
		}
		if (shopX == 0 && shopY == 1) {
			int propId = propIds[1];
			if (buyProp(propId, 1, propPrice[1], "������")) {
				props[1].setNums(props[1].getNums()+1);
			}
		}
		if (shopX == 0 && shopY == 2) {
			int propId = propIds[2];
			if (buyProp(propId, 1, propPrice[2], "������װ")) {
				props[2].setNums(props[2].getNums()+1);
			}
		}
		if (shopX == 0 && shopY == 3) {
			int propId = propIds[3];
			if (buyProp(propId, 1, propPrice[3], "����ǹ")) {
				props[3].setNums(props[3].getNums()+1);
			}
		}
		if (shopX == 1 && shopY == 0) {
			int propId = propIds[4];
			if (buyProp(propId, 1, propPrice[4], "��ɢ����")) {
				props[4].setNums(props[4].getNums()+1);
			}
		}
		if (shopX == 1 && shopY == 1) {
			int propId = propIds[5];
			if (buyProp(propId, 1, propPrice[5], "�ٶ�����Һ")) {
				props[5].setNums(props[5].getNums()+1);
			}
		}
		if (shopX == 1 && shopY == 2) {
			int propId = propIds[6];
			if (buyProp(propId, 1, propPrice[6], "ǿ����ʯ")) {
				props[6].setNums(props[6].getNums()+1);
			}
		}
		if (shopX == 1 && shopY == 3) {
			int propId = propIds[7];
			if (buyProp(propId, 1, propPrice[7], "������ż")) {
				props[7].setNums(props[7].getNums()+1);
			}
		}*/}
	

	/*ͬ������*/
	public void sysProps(){/*
		int[] ids = new int[8];
		int[] counts = new int[8];
		for(int i=0;i<props.length;i++){
			ids[i] = props[i].getPropId();
			counts[i] = props[i].getNums();
		}
		ServiceWrapper sw = engine.getServiceWrapper();
		sw.synProps(ids, counts);
		System.out.println("ͬ������:"+sw.isServiceSuccessful());
	*/}
}