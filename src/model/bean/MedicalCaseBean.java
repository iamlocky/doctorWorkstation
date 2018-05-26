package model.bean;

import java.io.Serializable;

public class MedicalCaseBean implements Serializable {
    private Pointer patientInfo;
    private User doctor;
    private String doctorID;
    private String condition;
    private String diagnose;
    private String handle;
    private String objectId;
    private String createdAt;
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }


    public Pointer getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(Pointer patientInfo) {
        this.patientInfo = patientInfo;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Pointer toPointer() {
        return new Pointer("Pointer",getClass().getName(),objectId);
    }
}
