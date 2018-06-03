package controller;

import Utils.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.ApiUrl;
import model.Model;
import model.OnStringResponseListener;
import model.bean.*;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;

public class DoctorController extends Controller {
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
        }, null);
    }

    public DoctorController(IControllerListener iControllerListener, Type type) {
        super(iControllerListener, type);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void getRegister(SimpleListener simpleListener) {
        Model model = new Model();
        type = new TypeToken<ResultBean<ClinicRegisterBean>>() {
        }.getType();
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("doctorID", Controller.getUser().getObjectId());
        model.getData(ApiUrl.Post.REGISTER_Info + Controller.getToday(), map, new OnStringResponseListener() {
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


    public void newMedicalCase(MedicalCase medicalCase, SimpleListener simpleListener) {
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

    public static List<DrugBean> getDrugDatabase() {
        List<DrugBean> drugBeans=new ArrayList<>();
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        ResultSetMetaData resultSetMetaData;
        try {
            connection = Model.getDrugDatabase();
            statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            resultSet = statement.executeQuery("SELECT * FROM drugInfo limit 1;");
            resultSetMetaData = resultSet.getMetaData();
            String[] columnNames=new String[resultSetMetaData.getColumnCount()];
            for (int i = 1; i <=resultSetMetaData.getColumnCount(); i++) {
                columnNames[i-1]=resultSetMetaData.getColumnName(i);
            }
            resultSet = statement.executeQuery("SELECT * FROM drugInfo;");
            print(Arrays.toString(columnNames));
            int i=0;
            while (resultSet.next())
            {
                i++;
                DrugBean drugBean=new DrugBean();
                drugBean.setNum(resultSet.getString(1));
                drugBean.setName(resultSet.getString(2));
                drugBean.setSpecification(resultSet.getString(3));
                drugBean.setAddress(resultSet.getString(4));
                drugBean.setDate(resultSet.getString(5));
                drugBean.setForm(resultSet.getString(6));
                drugBean.setPrice(resultSet.getDouble(7));
                drugBean.setObjectId("st"+i);
                drugBeans.add(drugBean);
            }
            resultSet.close();
            connection.close();
            setDrugBeanList(drugBeans);
            return drugBeans;
        } catch (SQLException e) {
            e.printStackTrace();
            drugBeans=new ArrayList<>();
        }
        return drugBeans;
    }

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
