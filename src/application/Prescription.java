package application;

import java.sql.Date;

public class Prescription {
    private int prescriptionID;
    private Date prescriptionDate;
    private String dosage;
    private String instructions;
    private int doctorID;
    private int customerID;

    

    public Prescription() {
        super();
    }

    public Prescription(int prescriptionID,  Date prescriptionDate, String dosage, String instructions , int doctorID ,int customerID) {
        super();
       this.setPrescriptionID(prescriptionID);
        this.setPrescriptionDate(prescriptionDate) ;
        this.setDosage(dosage);
        this.setInstructions(instructions);
        this.setDoctorID(doctorID);
        this.setCustomerID(customerID);
    }

	public int getPrescriptionID() {
		return prescriptionID;
	}

	public void setPrescriptionID(int prescriptionID) {
		this.prescriptionID = prescriptionID;
	}

	public Date getPrescriptionDate() {
		return prescriptionDate;
	}

	public void setPrescriptionDate(Date prescriptionDate) {
		this.prescriptionDate = prescriptionDate;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public int getDoctorID() {
		return doctorID;
	}

	public void setDoctorID(int doctorID) {
		this.doctorID = doctorID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

}
