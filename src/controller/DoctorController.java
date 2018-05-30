package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.ApiUrl;
import model.Model;
import model.OnStringResponseListener;
import model.bean.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DoctorController extends Controller{
    Gson gson = Model.getGson();
    Type type = new Type() {
    };


    public DoctorController() {
        super(new IControllerListener() {
            @Override
            public void done(Object data) {

            }

            @Override
            public void doneRaw(String data) {

            }

            @Override
            public void showMessage(String msg) {

            }
        },null);
    }

    public DoctorController(IControllerListener iControllerListener,Type type) {
        super(iControllerListener,type);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void getRegister(SimpleListener simpleListener) {
        Model model = new Model();
        type= new TypeToken<ResultBean<ClinicRegisterBean>>() {
        }.getType();
        LinkedHashMap<String,String> map=new LinkedHashMap<>();
        map.put("doctorID",Controller.getUser().getObjectId());
        model.getData(ApiUrl.Post.REGISTER_Info+Controller.getToday(),map, new OnStringResponseListener() {
            @Override
            public void onFinish(String responseBean, Exception e) {
                if (e == null) {
                    try {
                        if (responseBean.contains("objectId")) {

                            simpleListener.done(gson.fromJson(responseBean, new TypeToken<ResultBean<ClinicRegisterBean>>() {
                            }.getType()));

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



    public void newMedicalCase(MedicalCase medicalCase,SimpleListener simpleListener) {
        Model model = new Model();
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

}
