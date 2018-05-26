package view.Clinic;

import Utils.StringUtil;
import controller.ClinicController;
import controller.SimpleListener;
import model.bean.ErrInfo;
import model.bean.PatientInfo;
import model.bean.SucceedInfo;
import view.ViewUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * 建信息表界面
 */

public class AddPatient{
    static JFrame frame;
    private JPanel panel1;
    private JTextField tfIDNumber;
    private JTextField tfName;
    private JRadioButton rb1;
    private JRadioButton rb2;
    private JTextField tfADrug;
    private JTextField tfAdress;
    private JTextField tfInsuranceNumber;
    private JButton btnOK;
    private JTextField tfPhone;
    private ButtonGroup buttonGroup;

    private String name;
    private Integer gender;
    private String phone;
    private String idNumber;
    private String aDrug;
    private String adress;
    private String insuranceNumber;

    private ClinicController clinicController;


    public static void main(String[] args) {
        if (frame!=null){
            frame.setVisible(true);
            return;
        }
        frame = new JFrame("新建患者信息表 "+new Date().toString());
        frame.setContentPane(new AddPatient().panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        ViewUtils.toCenter(frame);
    }


    public void initView() {
        panel1.setBorder(new EmptyBorder(10, 10, 10, 10));
        ViewUtils.changeFont(panel1);
        buttonGroup = new ButtonGroup();
        buttonGroup.add(rb1);
        buttonGroup.add(rb2);
    }

    public AddPatient() {
        clinicController=new ClinicController();

        initView();
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkIsNull()) {
                    JOptionPane.showMessageDialog(null, "不能为空", "提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (idNumber.length()!=18){
                    JOptionPane.showMessageDialog(null, "身份证号不正确", "提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                PatientInfo patientInfo = new PatientInfo();
                patientInfo.setName(name);
                patientInfo.setGender(gender);
                patientInfo.setPhone(phone);
                patientInfo.setBirth(idNumber.substring(6,14));
                patientInfo.setIdCardNumber(idNumber);
                patientInfo.setDrugAllergies(aDrug);
                patientInfo.setAdress(adress);
                patientInfo.setInsuranceNumber(insuranceNumber);

                clinicController.newPatientInfo(patientInfo, new SimpleListener() {
                    @Override
                    public void done(Object data) {
                        SucceedInfo succeedInfo=(SucceedInfo) data;
                        JOptionPane.showMessageDialog(null, "建卡成功，就诊卡号为 "+succeedInfo.getObjectId(), "提示", JOptionPane.INFORMATION_MESSAGE);
                    }

                    @Override
                    public void fail(String s) {
                        JOptionPane.showMessageDialog(null, s, "操作失败", JOptionPane.ERROR_MESSAGE);
                    }

                    @Override
                    public void fail(ErrInfo errInfo) {
                        JOptionPane.showMessageDialog(null, errInfo.getError() == null ? errInfo.toString() : "错误码：" + errInfo.getCode() + "\n" +
                                "信息：" + errInfo.getError(), "操作失败", JOptionPane.ERROR_MESSAGE);
                    }
                });

            }
        });
    }



    public boolean checkIsNull() {
        name = tfName.getText();
        gender = rb1.isSelected() ? 0 : 1;
        idNumber = tfIDNumber.getText();
        phone=tfPhone.getText();
        aDrug = tfADrug.getText();
        adress = tfAdress.getText();
        insuranceNumber = tfInsuranceNumber.getText();
        if (StringUtil.isEmpty(name) || StringUtil.isEmpty(idNumber)||(!rb1.isSelected()&&!rb2.isSelected()))
            return true;
        else
            return false;
    }

}
