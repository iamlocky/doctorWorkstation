package controller;

import com.google.gson.Gson;
import model.ApiUrl;
import model.Model;
import model.OnStringResponseListener;
import model.bean.MedicalCase;

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
        model.getData(ApiUrl.Post.Patient_URL, new Object(), new OnStringResponseListener() {
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
        Controller.print("print.txt");
    }
}
