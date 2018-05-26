package model.bean;

import model.Model;

import java.io.Serializable;

public class DoctorBean implements Serializable{

    private String createdAt;
    private String objectId;
    private Integer department=-1;
    private String updatedAt;
    private String username;



    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return Model.getGson().toJson(this);
    }

    public Pointer toPointer() {
        return new Pointer("Pointer",getClass().getName(),objectId);
    }
}
