package view;

import Utils.FormatUtil;
import Utils.StringUtil;
import controller.Controller;
import model.bean.ClinicRegisterBean;
import model.bean.PatientInfoBean;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ItemView {
    public JPanel panel1;
    public JPanel panelmain;
    public JLabel lbName;
    private JButton btnRecept;
    public JLabel lb1;
    public JLabel lb2;
    public JLabel lb3;
    public JLabel lb4;
    private JPanel paneldetail;
    public PatientInfoBean patientInfoBean;
    private Color color;
    ClinicRegisterBean clinicRegisterBean;

    public ItemView(ClinicRegisterBean clinicRegisterBean) {
        this.clinicRegisterBean=clinicRegisterBean;
        panelmain.setBorder(new EmptyBorder(5, 5, 5, 5));
        btnRecept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                print(e);
            }
        });
        paneldetail.setBackground(null);
        try {
            patientInfoBean=Controller.findLocalPatient(clinicRegisterBean.getPatientID());
            if (patientInfoBean!=null){
                clinicRegisterBean.setPatientInfoBean(patientInfoBean);
                lbName.setText(patientInfoBean.getName());
                lb1.setText(patientInfoBean.getGender()==0?"男":"女");
                lb2.setText(clinicRegisterBean.getQueueNumber()+"号");
                lb3.setText((Integer.parseInt(Controller.getToday().substring(0,4))-Integer.parseInt(patientInfoBean.getBirth().substring(0,4)))+"岁");
                lb4.setText(StringUtil.isEmpty(patientInfoBean.getDrugAllergies())?"无过敏史":patientInfoBean.getDrugAllergies());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        panelmain.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount()==2){
                    String datastr="";
                    datastr= FormatUtil.formatJsonPretty(clinicRegisterBean);
                    JOptionPane.showMessageDialog(null,datastr,"挂号详情",JOptionPane.INFORMATION_MESSAGE);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                color=panelmain.getBackground();
                panelmain.setBackground(new Color(100,183,255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                panelmain.setBackground(color);
            }
        });
    }

    public void print(Object o){
        System.out.println(o);
    }
}
