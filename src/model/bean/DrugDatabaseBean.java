package model.bean;

public class DrugDatabaseBean {
    /**
     * createdAt : 2018-06-05 22:12:20
     * data : {"__type":"File","cdn":"upyun","filename":"drugDatabase.db","url":"http://bmob-cdn-19708.b0.upaiyun.com/2018/06/05/6725171f40562989804615781e094caf.db"}
     * name : drug
     * objectId : bMlF777G
     * updatedAt : 2018-06-05 22:17:53
     */

    private String createdAt;
    private DataBean data;
    private String name;
    private String objectId;
    private String updatedAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static class DataBean {
        /**
         * __type : File
         * cdn : upyun
         * filename : drugDatabase.db
         * url : http://bmob-cdn-19708.b0.upaiyun.com/2018/06/05/6725171f40562989804615781e094caf.db
         */

        private String __type;
        private String cdn;
        private String filename;
        private String url;

        public String get__type() {
            return __type;
        }

        public void set__type(String __type) {
            this.__type = __type;
        }

        public String getCdn() {
            return cdn;
        }

        public void setCdn(String cdn) {
            this.cdn = cdn;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
