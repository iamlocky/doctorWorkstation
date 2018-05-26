package view.Clinic;

import model.Model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class test {
    public static void main(String[] args) {
        Map<String,String> map=new HashMap<>();
        map.put("name","good");
        System.out.println(Model.getGson().toJson(map));
        String s=Model.getGson().toJson(map);
        try {
            System.out.println(URLEncoder.encode(s,"UTF-8").replace("%3A",":"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
