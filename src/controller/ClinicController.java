package controller;

import com.google.gson.Gson;
import model.ApiUrl;
import model.Model;
import model.OnStringResponseListener;
import model.bean.ClinicRegisterBean;
import model.bean.ErrInfo;
import model.bean.PatientInfo;
import model.bean.SucceedInfo;

import java.lang.reflect.Type;

public class ClinicController {
    Model model;
    Gson gson = Model.getGson();
    Type type = new Type() {
    };

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ClinicController() {
        model = new Model();
    }

    public void newPatientInfo(PatientInfo patientInfo, SimpleListener simpleListener) {
        model.postData(ApiUrl.Post.PatientInfo_URL, patientInfo, new OnStringResponseListener() {
            @Override
            public void onFinish(String responseBean, Exception e) {
                if (e == null) {
                    try {
                        if (responseBean.contains("objectId")) {

                            simpleListener.done(gson.fromJson(responseBean, SucceedInfo.class));

                        } else {
                            if (responseBean.contains("code") && responseBean.contains("error")) {
                                simpleListener.fail(gson.fromJson(responseBean, ErrInfo.class));
                            } else
                                simpleListener.fail(responseBean);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        simpleListener.fail(e1.getMessage());
                    }
                } else {
                    simpleListener.fail(e.getMessage());
                }
            }
        });
    }

    public void newRegister(ClinicRegisterBean clinicRegisterBean, SimpleListener simpleListener) {
        model.postData(ApiUrl.Post.REGISTER_Info, clinicRegisterBean, new OnStringResponseListener() {
            @Override
            public void onFinish(String responseBean, Exception e) {
                if (e == null) {
                    try {
                        simpleListener.done(responseBean);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else {
                    simpleListener.fail(e.getMessage());
                }
            }
        });
    }

    public void delRegister(ClinicRegisterBean clinicRegisterBean, SimpleListener simpleListener) {

    }
}

