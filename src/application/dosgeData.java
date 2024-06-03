package application;

public class dosgeData {
	private int dosageFormID;
	private String name;
	
	
	public dosgeData() {
		super();
	}

	static dosgeData dos;
	public dosgeData(int dosageFormID, String name) {
		super();
		this.dosageFormID = dosageFormID;
		this.name = name;
	}
	public int getDosageFormID() {
		return dosageFormID;
	}
	public void setDosageFormID(int dosageFormID) {
		this.dosageFormID = dosageFormID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
