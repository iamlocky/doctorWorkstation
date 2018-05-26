package controller;

import Utils.StringUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import Utils.AESUtil;
import model.ApiUrl;
import model.Model;
import model.OnStringResponseListener;
import model.bean.*;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;


/**
 *
 */

public class Controller<T> {

    private T data;

    private IControllerListener<T> iControllerListener;
    private Gson gson = new GsonBuilder().serializeNulls().create();
    private String jsonData;
    File file = new File("data.txt");
    private Type type;
    private static User user = new User();
    private static MedicalCase medicalCase;

    public Controller(IControllerListener<T> iControllerListener, Type type) {
        this.iControllerListener = iControllerListener;
        this.type = type;
    }

    public static MedicalCase getMedicalCase() {
        return medicalCase;
    }

    public static void setMedicalCase(MedicalCase medicalCase) {
        Controller.medicalCase = medicalCase;
    }


    public static User getUser() {
        return user;
    }

    public void login(String u, String p) {
        Model model = new Model();
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(u);
        loginUser.setPassword(p);
        model.getData(ApiUrl.Get.LOGIN_USER_URL, loginUser.getMap(), new OnStringResponseListener() {
            @Override
            public void onFinish(String responseBean, Exception e) {

                try {
                    JsonObject json = gson.fromJson(responseBean, JsonObject.class);
                    if (json.get("code") != null) {
                        iControllerListener.doneRaw(responseBean);
                    } else {
                        data = Model.getGson().fromJson(responseBean, type);
                        user = (User) data;
                        iControllerListener.done(data);
                    }
                } catch (Exception e1) {
                    iControllerListener.doneRaw(responseBean);
                }
            }
        });
    }

    public static void print(String s) {
        HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        // 设置打印格式，因为未确定类型，所以选择autosense
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        // 查找所有的可用的打印服务
        PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
        // 定位默认的打印服务
        PrintService defaultService = PrintServiceLookup
                .lookupDefaultPrintService();
        // 显示打印对话框
        PrintService service = ServiceUI.printDialog(null, 200, 200,
                printService, defaultService, flavor, pras);
        if (service != null) {
            try {
                DocPrintJob job = service.createPrintJob(); // 创建打印作业
                FileInputStream fis = new FileInputStream(s); // 构造待打印的文件流
                DocAttributeSet das = new HashDocAttributeSet();
                Doc doc = new SimpleDoc(fis, flavor, das);
                job.print(doc, pras);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void register(String u, String p, JProgressBar progressBar) {
        Model model = new Model();
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(u);
        loginUser.setPassword(p);
        model.postData(ApiUrl.Post.REGISTER_USER_URL, loginUser, new OnStringResponseListener() {
            @Override
            public void onFinish(String responseBean, Exception e) {
                SwingUtilities.invokeLater(() -> {
                    progressBar.setVisible(false);
                });
                try {
                    JsonObject json = gson.fromJson(responseBean, JsonObject.class);
                    if (json.get("code") != null) {
                        JOptionPane.showMessageDialog(null, responseBean, "注册失败", JOptionPane.ERROR_MESSAGE);
                    } else if (json.get("objectId") != null) {
                        JOptionPane.showMessageDialog(null, "注册成功");
                    } else {
                        JOptionPane.showMessageDialog(null, responseBean, "注册失败", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, responseBean, "注册失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void getData() {
        new Thread(getDataInBackground).start();
    }

    private Runnable getDataInBackground = new Runnable() {
        @Override
        public void run() {
            try {
                StringBuffer stringBuffer = new StringBuffer();
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                byte[] buffer = new byte[1024];
                int flag = 0;
                while ((flag = bufferedInputStream.read(buffer)) != -1) {
                    stringBuffer.append(new String(buffer, 0, flag));
                }
                jsonData = stringBuffer.toString();
                log(jsonData);
                if (type != null) {
                    data = gson.fromJson(jsonData, type);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (type != null) {

                        iControllerListener.done(data);
                    } else {
                        iControllerListener.doneRaw(jsonData);
                    }
                }
            });
        }
    };


    public void findPatient(Map<String, String> data) {
        Model model = new Model();
        if (StringUtil.isEmpty(data.get("name")))
        {
            data=null;
        }
            model.getData(ApiUrl.Post.PatientInfo_URL, data, new OnStringResponseListener() {
            @Override
            public void onFinish(String responseBean, Exception e) {
                if (responseBean.contains("error") || e != null) {
                    iControllerListener.showMessage(responseBean + "");
                } else {
                    try {
                        iControllerListener.done(gson.fromJson(responseBean, type));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public void getDoctors(){
        Model model = new Model();
        model.getData(ApiUrl.Get.DOCTOR_URL, null, new OnStringResponseListener() {
            @Override
            public void onFinish(String responseBean, Exception e) {
                if (responseBean.contains("error") || e != null) {
                    iControllerListener.showMessage(responseBean + "");
                } else {
                    try {
                        iControllerListener.done(gson.fromJson(responseBean, new TypeToken<ResultBean<DoctorBean>>() {
                        }.getType()));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public void findDoctor(Map<String, String> data) {

        Model model = new Model();
        model.getData(ApiUrl.Get.DOCTOR_URL, data, new OnStringResponseListener() {
            @Override
            public void onFinish(String responseBean, Exception e) {
                if (responseBean.contains("error") || e != null) {
                    iControllerListener.showMessage(responseBean + "");
                } else {
                    try {
                        iControllerListener.done(gson.fromJson(responseBean, type));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }


    public void setData(T data) {
        this.data = data;
    }


    private Runnable saveDataInBackground = new Runnable() {
        @Override
        public void run() {

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                jsonData = gson.toJson(data);
                log(jsonData);
                fileOutputStream.write(jsonData.getBytes());
                fileOutputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
//            iControllerListener.done(data);
        }
    };

    public void saveData(T data) {
        this.data = data;
        new Thread(saveDataInBackground).start();
    }

    void log(Object msg) {
        StackTraceElement[] trace = new Throwable().getStackTrace();
        for (StackTraceElement traceElement : trace)
            System.out.println("\tat " + traceElement);
        System.out.println(msg);
    }

    public static String leftPading(String strSrc, String flag, int strSrcLength) {
        String strReturn = "";
        String strtemp = "";
        int curLength = strSrc.trim().length();
        if (strSrc != null && curLength > strSrcLength) {
            strReturn = strSrc.trim().substring(0, strSrcLength);
        } else if (strSrc != null && curLength == strSrcLength) {
            strReturn = strSrc.trim();
        } else {

            for (int i = 0; i < (strSrcLength - curLength); i++) {
                strtemp = strtemp + flag;
            }
            strReturn = strtemp + strSrc.trim();
        }
        return strReturn;
    }

}
