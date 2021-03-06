package model.bean;


public class PatientInfoBean{
    private String objectId;
    private String name;
    private Integer gender;
    private String phone;
    private String birth;
    private String idCardNumber;
    private String adress;
    private String drugAllergies;
    private String insuranceNumber;
    private String createdAt;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }


    public String getDrugAllergies() {
        return drugAllergies;
    }

    public void setDrugAllergies(String drugAllergies) {
        this.drugAllergies = drugAllergies;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "PatientInfoBean{" +
                "objectId='" + objectId + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                ", birth='" + birth + '\'' +
                ", idCardNumber='" + idCardNumber + '\'' +
                ", adress='" + adress + '\'' +
                ", drugAllergies='" + drugAllergies + '\'' +
                ", insuranceNumber='" + insuranceNumber + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

    public Pointer toPointer() {
        return new Pointer("Pointer","PatientInfo",objectId);
    }
}
