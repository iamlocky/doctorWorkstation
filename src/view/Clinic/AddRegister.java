package view.Clinic;

import com.google.gson.reflect.TypeToken;
import controller.Controller;
import controller.IControllerListener;
import controller.SimpleListener;
import model.bean.*;
import view.ViewUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 挂号对话框
 */

public class AddRegister {
    private static JFrame frame;
    private JPanel panel;
    private JButton btnOK;
    private JTextField tfName;
    private JTextField tfID;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private PatientInfoBean patientInfo;
    private Controller controller;
    public ResultBean<DoctorBean> dataRaw;
    private List<DoctorBean> doctorsResult;

    public static void main(String[] args) {
        if (frame!=null){
            frame.dispose();
        }
        frame = new JFrame("新建挂号");
        frame.setContentPane(new AddRegister().panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        ViewUtils.toCenter(frame);
    }


    public void initView() {
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        ViewUtils.changeFont(panel);
    }

    public void renewDoctorCombox(){
        int departmentIndex=comboBox1.getSelectedIndex();
        List<DoctorBean> doctors=dataRaw.getResults();
        doctorsResult=new ArrayList<>();
        for (int i = 0; i < doctors.size(); i++) {
            DoctorBean doctorBean=doctors.get(i);
            if (doctorBean.getDepartment()==departmentIndex){
                doctorsResult.add(doctorBean);
            }
        }
        comboBox2.removeAllItems();
        for (int i = 0; i <doctorsResult.size() ; i++) {
            comboBox2.addItem(doctorsResult.get(i).getUsername());
        }
    }

    public AddRegister() {
        controller=new Controller(new IControllerListener<ResultBean<DoctorBean>>() {

            @Override
            public void done(ResultBean<DoctorBean> data) {
                dataRaw=data;
                renewDoctorCombox();
            }

            @Override
            public void doneRaw(String data) {

            }

            @Override
            public void showMessage(String msg) {

            }
        }, new TypeToken<ResultBean<DoctorBean>>() {
        }.getType());
        initView();

        controller.getDoctors();

        tfName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                showFindWindow();
            }
        });

        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                renewDoctorCombox();
            }
        });

    }

    public void showFindWindow(){
        FindInfo findInfo = FindInfo.main(null, 0);
        findInfo.setSimpleListener(new SimpleListener<PatientInfoBean>() {
            @Override
            public void done(PatientInfoBean data) {
                patientInfo=data;
                tfName.setText(data.getName());
                tfID.setText(data.getObjectId());
            }

            @Override
            public void fail(ErrInfo errInfo) {

            }

            @Override
            public void fail(String s) {

            }
        });
    }
}
