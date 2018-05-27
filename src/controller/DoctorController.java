package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.ApiUrl;
import model.Model;
import model.OnStringResponseListener;
import model.bean.ClinicRegisterBean;
import model.bean.MedicalCase;
import model.bean.ResultBean;

import java.lang.reflect.Type;

public class DoctorController {
    Model model;
    Gson gson = Model.getGson();
    Type type = new Type() {
    };

    public DoctorController() {
        model = new Model();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void getRegister(SimpleListener simpleListener) {
        type= new TypeToken<ResultBean<ClinicRegisterBean>>() {
        }.getType();
        model.getData(ApiUrl.Post.Patient_URL,null, new OnStringResponseListener() {
            @Override
            public void onFinish(String responseBean, Exception e) {
                if (e == null) {
                    try {

                        simpleListener.done(gson.fromJson(responseBean, type));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else {
                    simpleListener.fail(e.getMessage());
                }
            }
        });
    }

    public void newMedicalCase(MedicalCase medicalCase,SimpleListener simpleListener) {
        model.postData(ApiUrl.Post.Patient_URL, medicalCase, new OnStringResponseListener() {
            @Override
            public void onFinish(String responseBean, Exception e) {
                if (e == null) {
                    try {
                        simpleListener.done(gson.fromJson(responseBean, type));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else {
                    simpleListener.fail(e.getMessage());
                }
            }
        });
    }

    public void print(Object object){
        Controller.callPrinter("callPrinter.txt");
    }
}
