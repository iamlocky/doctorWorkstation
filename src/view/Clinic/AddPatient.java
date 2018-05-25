package view.Clinic;

import Utils.StringUtil;
import controller.ClinicController;
import controller.IControllerListener;
import controller.SimpleListener;
import model.bean.PatientInfo;
import view.ViewUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private ButtonGroup buttonGroup;

    private String name;
    private Integer gender;
    private String idNumber;
    private String aDrug;
    private String adress;
    private String insuranceNumber;

    private ClinicController clinicController;


    public static void main(String[] args) {
        frame = new JFrame("新建患者信息表");
        frame.setContentPane(new AddPatient().panel1);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
                PatientInfo patientInfo = new PatientInfo();
                patientInfo.setName(name);
                patientInfo.setGender(gender);
                patientInfo.setIdCardNumber(idNumber);
                patientInfo.setDrugAllergies(aDrug);
                patientInfo.setAdress(adress);
                patientInfo.setInsuranceNumber(insuranceNumber);

                clinicController.newPatientInfo(patientInfo, new SimpleListener() {
                    @Override
                    public void done(Object data) {
                        JOptionPane.showMessageDialog(null, data, "提示", JOptionPane.INFORMATION_MESSAGE);
                    }

                    @Override
                    public void fail(String s) {
                        JOptionPane.showMessageDialog(null, s, "失败", JOptionPane.ERROR_MESSAGE);
                    }
                });

            }
        });
    }



    public boolean checkIsNull() {
        name = tfName.getText();
        gender = rb1.isSelected() ? 0 : 1;
        idNumber = tfIDNumber.getText();
        aDrug = tfADrug.getText();
        adress = tfAdress.getText();
        insuranceNumber = tfInsuranceNumber.getText();
        if (StringUtil.isEmpty(name) || StringUtil.isEmpty(idNumber))
            return true;
        else
            return false;
    }

}
