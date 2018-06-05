package view.Clinic;

import Utils.FormatUtil;
import com.google.gson.Gson;
import controller.Controller;
import controller.DoctorController;
import model.ApiUrl;
import model.Model;
import model.OnStringResponseListener;
import model.bean.ClinicRegister;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
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
//        int i= JOptionPane.showConfirmDialog(null,"该患者当天已挂号，确认继续挂号？");
//        print(i);
//            print(URLDecoder.decode("%7B%22doctorID%22:%22RUnc999A%22%7D"));
//        DoctorController.openSql();
//        test test1=new test();
//        test1.testpath();
        print(System.getProperty("user.dir").replace("\\","/"));
    }

    public void testpath(){
        File f = new File(this.getClass().getResource("/").getPath());
        System.out.println(f);

        // 获取当前类的所在工程路径; 如果不加“/”  获取当前类的加载目录  D:\git\daotie\daotie\target\classes\my
        File f2 = new File(this.getClass().getResource("").getPath());
        System.out.println(f2);

        // 第二种：获取项目路径    D:\git\daotie\daotie
        File directory = new File("");// 参数为空
        String courseFile = null;
        try {
            courseFile = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(courseFile);


        // 第三种：  file:/D:/git/daotie/daotie/target/classes/
        URL xmlpath = getClass().getClassLoader().getResource("");
        System.out.println(xmlpath);


        // 第四种： D:\git\daotie\daotie
        System.out.println(System.getProperty("user.dir"));
        /*
         * 结果： C:\Documents and Settings\Administrator\workspace\projectName
         * 获取当前工程路径
         */

        // 第五种：  获取所有的类路径 包括jar包的路径
        System.out.println(System.getProperty("java.class.path"));
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
