package sheepwar;

/**
 * �ɾ�ʵ����
 * @author Administrator
 *
 */
public class Attainment {
	/*id*/
	private int id;
	
	/*�ɾ�����*/
	private int type;
	
	/*����*/
	private String name;
	
	/*��ȡ����*/
	private int condition;
	
	/*�Ƿ���*/
	private boolean result;
	
	/*����*/
	private int award;
	
	/*������Ϣ*/
	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCondition() {
		return condition;
	}

	public void setCondition(int condition) {
		this.condition = condition;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public int getAward() {
		return award;
	}

	public void setAward(int award) {
		this.award = award;
	}
	
}
