package model.bean;

import java.io.Serializable;

public class ClinicRegister implements Serializable {
    private String PatientID;
    private Integer department;
    private String doctorID;
    private Integer queueNumber;
    private Integer hasVisited=0;
    private String caseDetail;

    public String getPatientID() {
        return PatientID;
    }

    public void setPatientID(String patientID) {
        PatientID = patientID;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public Integer getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(Integer queueNumber) {
        this.queueNumber = queueNumber;
    }

    public Integer getHasVisited() {
        return hasVisited;
    }

    public void setHasVisited(Integer hasVisited) {
        this.hasVisited = hasVisited;
    }
}
