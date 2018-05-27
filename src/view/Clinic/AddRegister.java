package view.Clinic;

import com.google.gson.reflect.TypeToken;
import controller.ClinicController;
import controller.Controller;
import controller.IControllerListener;
import controller.SimpleListener;
import model.Model;
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
    private JProgressBar progressBar1;
    private PatientInfoBean patientInfo;
    private Controller controller;
    private ClinicController clinicController;
    public ResultBean<DoctorBean> dataRaw;
    private List<DoctorBean> doctorsResult;

    public static void main(String[] args) {
        if (frame!=null){
            frame.dispose();
        }
        frame = new JFrame("新建挂号"){

        };
        frame.setContentPane(new AddRegister().panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        ViewUtils.toCenter(frame);
    }


    public void initView() {
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        ViewUtils.changeFont(panel);
        progressBar1.setVisible(false);

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
            comboBox2.addItem(doctorsResult.get(i).getName());
        }
    }

    public AddRegister() {
        clinicController=new ClinicController();

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

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClinicRegister clinicRegister=new ClinicRegister();
                clinicRegister.setDepartment(comboBox1.getSelectedIndex());
                clinicRegister.setPatientID(patientInfo.getObjectId());
                clinicRegister.setDoctorID(doctorsResult.get(comboBox2.getSelectedIndex()).getObjectId());
                Model.log(Model.getGson().toJson(clinicRegister));
                progressBar1.setVisible(true);
                clinicController.newRegister(clinicRegister, new SimpleListener() {
                    @Override
                    public void done(Object data) {
                        progressBar1.setVisible(false);
                        JOptionPane.showMessageDialog(null,data," 挂号成功！", JOptionPane.INFORMATION_MESSAGE);

                    }

                    @Override
                    public void fail(ErrInfo errInfo) {
                        progressBar1.setVisible(false);
                        JOptionPane.showMessageDialog(null,"失败"," 挂号失败！", JOptionPane.INFORMATION_MESSAGE);
                    }

                    @Override
                    public void fail(String s) {
                        progressBar1.setVisible(false);
                        JOptionPane.showMessageDialog(null,s," 挂号失败！", JOptionPane.INFORMATION_MESSAGE);

                    }
                });
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
