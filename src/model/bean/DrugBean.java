package model.bean;

public class DrugBean {
    /**
     * address : 昆山龙灯瑞迪制药有限公司
     * classify : 化学药品
     * createdAt : 2018-05-27 16:04:44
     * date : 09/30/2010
     * form : 胶囊剂
     * name : 氟康唑胶囊
     * num : 国药准字H20023054
     * objectId : f1e1a75865
     * specification : 50mg
     * updatedAt : 2018-05-27 16:04:44
     */

    private String address;
    private String classify;
    private String createdAt;
    private String date;
    private String form;
    private String name;
    private String num;
    private Integer count=1;
    private Double price=0.0;
    private String objectId;
    private String specification;
    private String updatedAt;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
