package model.bean;

import java.io.Serializable;

public class ClinicRegisterBean implements Serializable {
    private Pointer patientInfo;
    private String department;
    private Pointer doctor;
    private String objectId;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Pointer getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(Pointer patientInfo) {
        this.patientInfo = patientInfo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Pointer getDoctor() {
        return doctor;
    }

    public void setDoctor(Pointer doctor) {
        this.doctor = doctor;
    }

    @Override
    public String toString() {
        return "ClinicRegisterBean{" +
                "patientInfo=" + patientInfo +
                ", department='" + department + '\'' +
                ", doctor=" + doctor +
                ", objectId='" + objectId + '\'' +
                '}';
    }

    public Pointer toPointer() {
        return new Pointer("Pointer", getClass().getName(), objectId);
    }
}
