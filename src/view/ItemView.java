package view;

import Utils.FormatUtil;
import Utils.StringUtil;
import controller.Controller;
import model.bean.ClinicRegisterBean;
import model.bean.PatientInfoBean;
import view.Doctor.AddMedicalCase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ItemView extends JComponent{
    public JPanel panel1;
    public JPanel panelmain;//
    public JLabel lbName;
    private JButton btnRecept;
    public JLabel lb1;
    public JLabel lb2;
    public JLabel lb3;
    public JLabel lb4;
    private JPanel paneldetail;
    private JLabel lb5;
    private JLabel lb6;

    public PatientInfoBean patientInfoBean;
    public ClinicRegisterBean clinicRegisterBean;
    private Color color;

    public ItemView(ClinicRegisterBean clinicRegisterBean,String...params) {
        this.clinicRegisterBean=clinicRegisterBean;
        if (params!=null&&params.length>0){
            lb6.setVisible(true);
            btnRecept.setVisible(false);
        }else {
            lb6.setVisible(false);
            btnRecept.setVisible(true);
        }

        panelmain.setBorder(new EmptyBorder(5, 5, 5, 5));
        btnRecept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(null,"!!!","接诊",JOptionPane.INFORMATION_MESSAGE);
                AddMedicalCase.main(clinicRegisterBean,patientInfoBean);
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
                lb4.setText(clinicRegisterBean.getDepartment()==0?"内科":"外科");
                if (lb6.isVisible()){
                    lb5.setText(patientInfoBean.getIdCardNumber());
                    lb6.setText(patientInfoBean.getObjectId());
                }else {
                    lb5.setText(patientInfoBean.getObjectId());

                    lb6.setPreferredSize(new Dimension(0,0));
                    lb6.setMinimumSize(new Dimension(0,0));
                }
                if (clinicRegisterBean.getHasVisited()==1){
                    btnRecept.setText("继续接诊");
                }
                panelmain.setToolTipText(clinicRegisterBean.getCreatedAt());
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
