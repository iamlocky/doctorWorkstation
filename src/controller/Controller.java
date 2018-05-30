package controller;

import Utils.StringUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
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
import java.text.SimpleDateFormat;
import java.util.*;


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
    private static DoctorBean user;
    private static MedicalCase medicalCase;
    public static List<DoctorBean> doctorList;
    public static HashMap<String, DoctorBean> doctorListMap;
    public static List<PatientInfoBean> patientInfoBeanList;
    public static HashMap<String, PatientInfoBean> patientListMap;
    private static List<DrugBean> drugBeanList;
    public static HashMap<String, DrugBean> drugBeanListMap;

    public Controller(IControllerListener<T> iControllerListener, Type type) {
        this.iControllerListener = iControllerListener;
        this.type = type;
        getPatientList();
    }

    public static List<DoctorBean> getDoctorList() {
        return doctorList;
    }

    public static void setDoctorList(List<DoctorBean> doctorList) {
        Controller.doctorList = doctorList;
        if (doctorList != null) {
            if (doctorListMap != null) {
                doctorListMap.clear();
            } else
                doctorListMap = new HashMap<>();
            for (int i = 0; i < doctorList.size(); i++) {
                doctorListMap.put(doctorList.get(i).getObjectId(), doctorList.get(i));
            }
        }
    }

    public static List<PatientInfoBean> getPatientInfoBeanList() {
        return patientInfoBeanList;
    }

    public static void setPatientInfoBeanList(List<PatientInfoBean> patientInfoBeanList) {
        Controller.patientInfoBeanList = patientInfoBeanList;
        if (patientInfoBeanList != null) {
            if (patientListMap != null) {
                patientListMap.clear();
            } else
                patientListMap = new HashMap<>();
            for (int i = 0; i < patientInfoBeanList.size(); i++) {
                patientListMap.put(patientInfoBeanList.get(i).getObjectId(), patientInfoBeanList.get(i));
            }
        }
    }

    public static List<DrugBean> getDrugBeanList() {
        return drugBeanList;
    }

    public static void setDrugBeanList(List<DrugBean> drugBeanList) {
        Controller.drugBeanList = drugBeanList;
        if (drugBeanList != null) {
            if (drugBeanListMap != null) {
                drugBeanListMap.clear();
            } else
                drugBeanListMap = new HashMap<>();
            for (int i = 0; i < drugBeanList.size(); i++) {
                drugBeanListMap.put(drugBeanList.get(i).getObjectId(), drugBeanList.get(i));
            }
        }
    }

    public static DoctorBean getUser() {
        return user;
    }

    public static String getToday() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(new Date());
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
                        user = (DoctorBean) data;
                        iControllerListener.done(data);
                    }
                } catch (Exception e1) {
                    iControllerListener.doneRaw(responseBean);
                }
            }
        });
    }

    public static void callPrinter(String s) {
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


    public void findPatient(LinkedHashMap<String, String> data) {
        Model model = new Model();
        if (data!=null) {
            if (StringUtil.isEmpty(data.get("name"))) {
                data = null;
            }
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

    public void getDoctors() {
        Model model = new Model();
        model.getData(ApiUrl.Get.DOCTOR_URL, null, new OnStringResponseListener() {
            @Override
            public void onFinish(String responseBean, Exception e) {
                if (responseBean.contains("error") || e != null) {
                    iControllerListener.showMessage(responseBean + "");
                } else {
                    try {
                        ResultBean<DoctorBean> resultBean = gson.fromJson(responseBean, new TypeToken<ResultBean<DoctorBean>>() {
                        }.getType());

                        setDoctorList(resultBean.getResults());
                        iControllerListener.done((T) resultBean);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public void getPatientList() {
        Model model = new Model();
        model.getData(ApiUrl.Post.PatientInfo_URL, null, new OnStringResponseListener() {
            @Override
            public void onFinish(String responseBean, Exception e) {
                if (responseBean.contains("error") || e != null) {
                } else {
                    try {
                        ResultBean<PatientInfoBean> resultBean = gson.fromJson(responseBean, new TypeToken<ResultBean<PatientInfoBean>>() {
                        }.getType());
                        setPatientInfoBeanList(resultBean.getResults());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public static PatientInfoBean findLocalPatient(String objectId) {
        if (patientInfoBeanList != null && !StringUtil.isEmpty(objectId)) {
//            print("do find " + objectId);
            return patientListMap.get(objectId);
        } else {
//            print("do not find ");
            return null;
        }
    }

    public static DoctorBean findLocalDoctor(String objectId) {
        if (doctorListMap != null && !StringUtil.isEmpty(objectId)) {
//            print("do find " + objectId);
            return doctorListMap.get(objectId);
        } else {
//            print("do not find ");
            return null;
        }
    }

    public static void print(Object o) {
        System.out.println(o);
    }

    public void findDoctor(LinkedHashMap<String, String> data) {

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

    public void findDrug(LinkedHashMap<String, String> data) {
        Model model = new Model();
        if (data!=null) {
            if (StringUtil.isEmpty(data.get("name"))) {
                data = null;
            }
        }
        model.getData(ApiUrl.Get.Drug_URL, data, new OnStringResponseListener() {
            @Override
            public void onFinish(String responseBean, Exception e) {
                if (responseBean.contains("error") || e != null) {
                    iControllerListener.showMessage(responseBean + "");
                } else {
                    try {
                        type=new TypeToken<ResultBean<DrugBean>>(){}.getType();
                        ResultBean<DrugBean> resultBean=gson.fromJson(responseBean, type);
                        setDrugBeanList(resultBean.getResults());
                        iControllerListener.done((T)resultBean);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public DrugBean findLocalDrug(String objectId){
        if (drugBeanListMap != null && !StringUtil.isEmpty(objectId)) {
            return drugBeanListMap.get(objectId);
        } else {
            return null;
        }
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
