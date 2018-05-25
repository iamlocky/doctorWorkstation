package controller;

import com.google.gson.Gson;
import model.ApiUrl;
import model.Model;
import model.OnStringResponseListener;
import model.bean.ClinicRegisterBean;
import model.bean.PatientInfo;

import java.lang.reflect.Type;

public class ClinicController {
    Model model;
    Gson gson=Model.getGson();
    Type type = new Type() {
    };
    public ClinicController(){
        model=new Model();
    }

    public void newPatientInfo(PatientInfo patientInfo,SimpleListener simpleListener){
        model.postData(ApiUrl.Post.Patient_URL, patientInfo, new OnStringResponseListener() {
            @Override
            public void onFinish(String responseBean, Exception e) {
                if (e==null){
                    try {
                        simpleListener.done(gson.fromJson(responseBean,type));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }else {
                    simpleListener.fail(e.getMessage());
                }
            }
        });
    }

    public void newRegister(ClinicRegisterBean clinicRegisterBean, SimpleListener simpleListener){
        model.postData(ApiUrl.Post.Patient_URL, clinicRegisterBean, new OnStringResponseListener() {
            @Override
            public void onFinish(String responseBean, Exception e) {
                if (e==null){
                    try {
                        simpleListener.done(gson.fromJson(responseBean,type));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }else {
                    simpleListener.fail(e.getMessage());
                }
            }
        });
    }

    public void delRegister(ClinicRegisterBean clinicRegisterBean, SimpleListener simpleListener){

    }
}

