package controller;

import Utils.StringUtil;
import model.ApiUrl;
import model.Model;
import model.OnStringResponseListener;
import model.bean.ClinicRegisterBean;

import java.util.HashMap;

public class ItemController {
    public void putRegister(ClinicRegisterBean clinicRegisterBean, SimpleListener simpleListener){
        HashMap<String,Object> map=new HashMap<>();
        map.put("hasVisited",clinicRegisterBean.getHasVisited());
        if (!StringUtil.isEmpty(clinicRegisterBean.getCaseDetail())) {
            map.put("caseDetail",clinicRegisterBean.getCaseDetail());
        }
        Model model = new Model();
        model.putData(ApiUrl.Post.REGISTER_Info + Controller.getToday()+"/" + clinicRegisterBean.getObjectId(), map, new OnStringResponseListener() {
            @Override
            public void onFinish(String responseBean, Exception e) {
                if (e==null) {
                    if (responseBean.contains("updatedAt")) {
                        simpleListener.done(responseBean);
                    }else {
                        simpleListener.fail(responseBean);
                    }
                }else {
                    simpleListener.fail(e.getMessage());
                }
            }
        });
    }
}
