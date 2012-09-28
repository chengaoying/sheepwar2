package sheepwar;

/**
 * 道具
 * @author Administrator
 *
 */
public class Prop {
	
	/*ID*/
	private int id;
	
	/*道具名称*/
	private String name;
	
	/*道具ID*/
	private int propId;
	
	/*扣费点*/
	private int feeCode;
	
	/*道具数量*/
	private int nums;
	
	/*描述*/
	private String desc;
	
	/*道具价格*/
	private int price;
	

	public int getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(int feeCode) {
		this.feeCode = feeCode;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getPropId() {
		return propId;
	}

	public void setPropId(int propId) {
		this.propId = propId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNums() {
		return nums;
	}

	public void setNums(int nums) {
		this.nums = nums;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
