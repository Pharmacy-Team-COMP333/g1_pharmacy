package application;

public class Doctor {
    private int doctorID;
    private String name;
    private String specialization;
    private String phoneNumber;

    public Doctor() {
        super();
    }

    public Doctor(int doctorID, String name, String specialization, String phoneNumber) {
        super();
        this.doctorID = doctorID;
        this.name = name;
        this.specialization = specialization;
        this.phoneNumber = phoneNumber;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
