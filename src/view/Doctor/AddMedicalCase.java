package view.Doctor;

import controller.Controller;
import controller.DoctorController;
import controller.IControllerListener;
import model.bean.ClinicRegisterBean;
import model.bean.PatientInfoBean;
import view.ItemView;
import view.ViewUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddMedicalCase {
    private static JFrame frame;
    private JPanel panelContent1;
    private JPanel panelContent2;
    private JPanel panelContent3;
    private ClinicRegisterBean clinicRegisterBean;
    private PatientInfoBean patientInfoBean;
    private JPanel panel1;
    private JPanel panelLeft;
    private JPanel panelmain;
    private JProgressBar progressBar1;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JTabbedPane tabbedPane;
    private DoctorController doctorController;

    public static void main(ClinicRegisterBean clinicRegisterBean, PatientInfoBean patientInfoBean){
        frame = new JFrame("医生工作站---当前工作人员: " + Controller.getUser().getName() +" "+ Controller.getUser().getUsername());
        frame.setContentPane(new AddMedicalCase(clinicRegisterBean,patientInfoBean).panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        ViewUtils.toCenter(frame);
    }

    void initView(){
        progressBar1.setVisible(false);
        panel1.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelLeft.setBorder(new EmptyBorder(0, 0, 0, 10));
        panelContent1 =new JPanel();
        panelContent2 =new JPanel();
        panelContent3 =new JPanel();
        tabbedPane=new JTabbedPane();
        tabbedPane.addTab("病历", panelContent1);
        tabbedPane.addTab("处方", panelContent2);
        tabbedPane.addTab("医嘱", panelContent3);
        panelmain.add(tabbedPane);

        ItemView itemView=new ItemView(clinicRegisterBean,"detail");
        panelLeft.add(itemView.panel1);

        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.callPrinter("hh");
            }
        });
    }

    public AddMedicalCase(ClinicRegisterBean clinicRegisterBean, PatientInfoBean patientInfoBean) {
        this.clinicRegisterBean = clinicRegisterBean;
        this.patientInfoBean = patientInfoBean;
        initView();
        doctorController=new DoctorController(new IControllerListener() {
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
}
