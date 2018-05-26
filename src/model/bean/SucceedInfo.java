package model.bean;

import java.io.Serializable;

public class SucceedInfo implements Serializable{

    /**
     * createdAt : 2018-05-25 23:47:57
     * objectId : 398b6357b6
     */

    private String createdAt;
    private String objectId;

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

    @Override
    public String toString() {
        return "SucceedInfo{" +
                "createdAt='" + createdAt + '\'' +
                ", objectId='" + objectId + '\'' +
                '}';
    }
}
