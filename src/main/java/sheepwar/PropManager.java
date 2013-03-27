package sheepwar;

import cn.ohyeah.stb.game.Recharge;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupConfirm;
import cn.ohyeah.stb.ui.PopupText;
import cn.ohyeah.stb.util.ConvertUtil;


public class PropManager implements Common{
	
	public SheepWarGameEngine engine;
	public Prop[] props;   									//��ҵ����б�
	
	public int[] propIds = {53,54,55,56,57,58,59,60};		//����id 
	public int[] propPrice = {20,20,30,30,30,30,50,50};				//���߼۸�
	private String[] propName = {"ʱ������","������","������װ","����ǹ","��ɢ����","����","ǿ����ʯ","������ż",};
	
	public PropManager(SheepWarGameEngine engine){
		this.engine = engine;
		props = engine.props;
	}
	
	/*��ѯ��ҵ���*/
	public void queryProps(){
		initProps(props);
		//sysProps();
		ServiceWrapper sw = engine.getServiceWrapper();
		String datas = sw.loadPropItem();
		if(datas==null){
			return;
		}
		
		String[] data1 = ConvertUtil.split(datas, "|");
		for(int j=0;j<props.length;j++){
			Prop prop = new Prop();
			String[] data2 = ConvertUtil.split(data1[j], ",");
			String[] data3 = new String[data2.length];
			for(int k=0;k<data2.length;k++){
				data3[k] = ConvertUtil.split(data2[k], ":")[1];
			}
			prop.setPropId(Integer.parseInt(data3[0]));
			prop.setPrice(Integer.parseInt(data3[1]));
			prop.setNums(Integer.parseInt(data3[2]));
			prop.setName(data3[3]);
			props[j] = prop;
		}
		
		printInfo();
	}
	
	/*��ʼ������Ϊ0*/
	public void initProps(Prop[] p){
		System.out.println("�����������ݲ���ʼ��������Ϣ");
		for(int i=0;i<p.length;i++){
			Prop prop = new Prop();
			p[i] = prop;
			p[i].setId(i);
			p[i].setNums(0);
			p[i].setPropId(propIds[i]);
			p[i].setPrice(propPrice[i]);
			p[i].setName(propName[i]);
		}
	}
	
	/*���ݵ���ID��ѯ�õ�������*/
	public Prop getPropById(int propId){
		int len = props.length;
		for(int i=len-1;i>=0;i--){
			if(props[i].getPropId()==propId){
				return props[i];
			}
		}
		return null;
	}
	
	public int getPropNumsById(int id){
		int len = props.length;
		for(int i=len-1;i>=0;i--){
			if(props[i].getPropId()==id){
				return props[i].getNums();
			}
		}
		return 0;
	}
	
	public int getPriceById(int propId){
		int len = props.length;
		for(int i=len-1;i>=0;i--){
			if(props[i].getPropId()==propId){
				return props[i].getPrice();
			}
		}
		return 0;
	}
	
	public void addPropNum(int propId){
		int len = props.length;
		for(int i=len-1;i>=0;i--){
			if(props[i].getPropId()==propId){
				props[i].setNums(props[i].getNums()+1);
			}
		}
	}
	
	public void reducePropNum(int propId){
		int len = props.length;
		for(int i=len-1;i>=0;i--){
			if(props[i].getPropId()==propId){
				if(props[i].getNums()>0){
					props[i].setNums(props[i].getNums()-1);
				}
			}
		}
	}
	
	private boolean buyProp(int propId){
		Prop pp = getPropById(propId);
		if(engine.getEngineService().getBalance()<pp.getPrice()){
			PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
			pc.setText(engine.getEngineService().getExpendAmountUnit()+"����,�Ƿ�ȥ��ֵ?");
			if(pc.popup()==0){
				Recharge recharge = new Recharge(engine);
				recharge.recharge();
			}
			return false;
		}else{
			ServiceWrapper sw = engine.getServiceWrapper();
			//sw.purchaseProp(propId, propCount, "����"+propName);
			//sw.expend(pp.getPrice(), propId, "����"+pp.getName());
			sw.consume(1, pp.getPrice(), pp.getName());
			PopupText pt = UIResource.getInstance().buildDefaultPopupText();
			if (sw.isServiceSuccessful()) {
				pt.setText("����"+pp.getName()+"�ɹ�");
				//addPropNum(propId);
			}
			else {
				pt.setText("����"+pp.getName()+"ʧ��, ԭ��: "+sw.getMessage());
			}
			pt.popup();
			return sw.isServiceSuccessful();
		}
	
	}
	
	
	
	/*�������*/
	public void purchaseProp(int shopX, int shopY) {
		
		if (shopX == 0 && shopY == 0) {
			int propId = propIds[0];
			if (buyProp(propId)) {
				props[0].setNums(props[0].getNums()+1);
			}
		}
		if (shopX == 0 && shopY == 1) {
			int propId = propIds[1];
			if (buyProp(propId)) {
				props[1].setNums(props[1].getNums()+1);
			}
		}
		if (shopX == 0 && shopY == 2) {
			int propId = propIds[2];
			if (buyProp(propId)) {
				props[2].setNums(props[2].getNums()+1);
			}
		}
		if (shopX == 0 && shopY == 3) {
			int propId = propIds[3];
			if (buyProp(propId)) {
				props[3].setNums(props[3].getNums()+1);
			}
		}
		if (shopX == 1 && shopY == 0) {
			int propId = propIds[4];
			if (buyProp(propId)) {
				props[4].setNums(props[4].getNums()+1);
			}
		}
		if (shopX == 1 && shopY == 1) {
			int propId = propIds[5];
			if (buyProp(propId)) {
				props[5].setNums(props[5].getNums()+1);
			}
		}
		if (shopX == 1 && shopY == 2) {
			int propId = propIds[6];
			if (buyProp(propId)) {
				props[6].setNums(props[6].getNums()+1);
			}
		}
		if (shopX == 1 && shopY == 3) {
			int propId = propIds[7];
			if (buyProp(propId)) {
				props[7].setNums(props[7].getNums()+1);
			}
		}
	}
	

	/*ͬ������*/
	public void sysProps(){
		ServiceWrapper sw = engine.getServiceWrapper();
		//sw.synProps(66, StateGame.ventoseNum);
		System.out.println("ͬ������:");
		String datas = "";
		for(int i=0;i<props.length;i++){
			datas += "id:"+props[i].getPropId()
					+",price:"+props[i].getPrice()
					+",nums:"+props[i].getNums()
					+",name:"+props[i].getName();
			if(i!=props.length-1){
				datas += "|";
			}
		}
		sw.savePropItem(datas);
		if(sw.isServiceSuccessful()){
			printInfo();
		}
	}
	
	private void printInfo(){
		for(int i=0;i<props.length;i++){
			System.out.println("��������=="+props[i].getName());
			System.out.println("����ID=="+props[i].getPropId());
			System.out.println("��������=="+props[i].getNums());
		}
	}
}
