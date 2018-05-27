package model.bean;

import java.io.Serializable;

public class ClinicRegisterBean implements Serializable {
    private String PatientID;
    private Integer department;
    private String doctorID;
    private PatientInfoBean patientInfoBean;
    private DoctorBean doctorBean;
    private String objectId;
    private String createdAt;
    private Integer queueNumber;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public String getPatientID() {
        return PatientID;
    }

    public void setPatientID(String patientID) {
        PatientID = patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public PatientInfoBean getPatientInfoBean() {
        return patientInfoBean;
    }

    public void setPatientInfoBean(PatientInfoBean patientInfoBean) {
        this.patientInfoBean = patientInfoBean;
    }

    public DoctorBean getDoctorBean() {
        return doctorBean;
    }

    public void setDoctorBean(DoctorBean doctorBean) {
        this.doctorBean = doctorBean;
    }

    public Integer getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(Integer queueNumber) {
        this.queueNumber = queueNumber;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
