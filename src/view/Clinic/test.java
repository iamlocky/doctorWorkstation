package view.Clinic;

import Utils.FormatUtil;
import com.google.gson.Gson;
import controller.Controller;
import model.ApiUrl;
import model.Model;
import model.OnStringResponseListener;
import model.bean.ClinicRegister;

import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class test {
    public static void main(String[] args) {
//        Map<String,String> map=new HashMap<>();
//        map.put("name","good");
//        System.out.println(Model.getGson().toJson(map));
//        String s=Model.getGson().toJson(map);
//        try {
//            System.out.println(URLEncoder.encode(s,"UTF-8").replace("%3A",":"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        test();
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
//        callPrinter(simpleDateFormat.format(new Date()));
//        callPrinter(FormatUtil.formatJson(Model.getGson().toJson(new ClinicRegister())));
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
//        String date=simpleDateFormat.format(new Date());
//        callPrinter(date);
//        callPrinter(Controller.getToday());
        int i= JOptionPane.showConfirmDialog(null,"该患者当天已挂号，确认继续挂号？");
        print(i);

    }


    public static void test(){
        Model model=new Model();
        model.getData(ApiUrl.Get.Drug_URL, null, new OnStringResponseListener() {
            @Override
            public void onFinish(String responseBean, Exception e) {

            }
        });
    }

    public static void test2(){

    }

    public static void print(Object o){
        System.out.println(o);
    }
}
