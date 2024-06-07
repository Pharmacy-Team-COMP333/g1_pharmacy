package application;

public class Disease {
    private int diseaseID;
    private String name;
    private String description;
    private String treatment;
    
    public Disease() {
        super();
    }

    public Disease(int diseaseID, String name, String description, String treatment) {
        super();
        this.setDiseaseID(diseaseID);
        this.setName(name);
        this.setDescription(description);
        this.setTreatment(treatment);
    }

	public int getDiseaseID() {
		return diseaseID;
	}

	public void setDiseaseID(int diseaseID) {
		this.diseaseID = diseaseID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

}
