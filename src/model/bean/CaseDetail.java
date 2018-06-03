package model.bean;

import java.util.List;

public class CaseDetail {
    private String time;
    private String main;
    private String inspection;
    private String diagnosis;
    private List<DrugBean> drugBeans;
    private String advice;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getInspection() {
        return inspection;
    }

    public void setInspection(String inspection) {
        this.inspection = inspection;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<DrugBean> getDrugBeans() {
        return drugBeans;
    }

    public void setDrugBeans(List<DrugBean> drugBeans) {
        this.drugBeans = drugBeans;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    @Override
    public String toString() {
        return "CaseDetail{" +
                "time='" + time + '\'' +
                ", main='" + main + '\'' +
                ", inspection='" + inspection + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", drugBeans=" + drugBeans +
                ", advice='" + advice + '\'' +
                '}';
    }
}
