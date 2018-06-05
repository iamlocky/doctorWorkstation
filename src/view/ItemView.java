package view;

import Utils.FormatUtil;
import controller.Controller;
import controller.DoctorController;
import controller.ItemController;
import controller.SimpleListener;
import model.bean.ClinicRegisterBean;
import model.bean.ErrInfo;
import model.bean.PatientInfoBean;
import view.Doctor.AddMedicalCase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ItemView extends JComponent {
    private int hasVisited;
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
    private JProgressBar progressBar1;

    public PatientInfoBean patientInfoBean;
    public ClinicRegisterBean clinicRegisterBean;
    private Color color;
    private JPopupMenu popupMenu;
    private ItemController itemController;

    public ItemView(ClinicRegisterBean clinicRegisterBean, String... params) {
        this.clinicRegisterBean = clinicRegisterBean;
        if (params != null && params.length > 0) {
            lb6.setVisible(true);
            btnRecept.setVisible(false);
        } else {
            lb6.setVisible(false);
            btnRecept.setVisible(true);
        }
        progressBar1.setVisible(false);
        panelmain.setBorder(new EmptyBorder(5, 5, 5, 5));
        btnRecept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(null,"!!!","接诊",JOptionPane.INFORMATION_MESSAGE);
                AddMedicalCase.main(clinicRegisterBean, patientInfoBean);
            }
        });
        paneldetail.setBackground(null);
        try {
            patientInfoBean = Controller.findLocalPatient(clinicRegisterBean.getPatientID());
            if (patientInfoBean != null) {
                clinicRegisterBean.setPatientInfoBean(patientInfoBean);
                lbName.setText(patientInfoBean.getName());
                lb1.setText(patientInfoBean.getGender() == 0 ? "男" : "女");
                lb2.setText(clinicRegisterBean.getQueueNumber() + "号");
                lb3.setText((Integer.parseInt(Controller.getToday().substring(0, 4)) - Integer.parseInt(patientInfoBean.getBirth().substring(0, 4))) + "岁");
                lb4.setText(clinicRegisterBean.getDepartment() == 0 ? "内科" : "外科");
                if (lb6.isVisible()) {
                    lb5.setText(patientInfoBean.getIdCardNumber());
                    lb6.setText(patientInfoBean.getObjectId());
                } else {
                    lb5.setText(patientInfoBean.getObjectId());

                    lb6.setPreferredSize(new Dimension(0, 0));
                    lb6.setMinimumSize(new Dimension(0, 0));
                }
                if (clinicRegisterBean.getHasVisited() == 1) {
                    btnRecept.setText("继续接诊");
                }
                panelmain.setToolTipText(clinicRegisterBean.getCreatedAt());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //--
        JMenuItem item = new JMenuItem();
        popupMenu = new JPopupMenu();
        popupMenu.add(item);
        hasVisited = clinicRegisterBean.getHasVisited();

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progressBar1.setVisible(true);

                if (itemController == null) {
                    itemController = new ItemController();
                }

                if (hasVisited == 0) {
                    clinicRegisterBean.setHasVisited(1);
                } else {
                    clinicRegisterBean.setHasVisited(0);
                }
                itemController.putRegister(clinicRegisterBean, new SimpleListener() {
                    @Override
                    public void done(Object data) {
                        progressBar1.setVisible(false);
                        JOptionPane.showMessageDialog(null, "操作成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }

                    @Override
                    public void fail(ErrInfo errInfo) {
                        progressBar1.setVisible(false);
                        JOptionPane.showMessageDialog(null, errInfo.toString(), "操作失败", JOptionPane.INFORMATION_MESSAGE);

                    }

                    @Override
                    public void fail(String s) {
                        progressBar1.setVisible(false);
                        JOptionPane.showMessageDialog(null, s, "操作失败", JOptionPane.INFORMATION_MESSAGE);

                    }
                });
            }
        });

        panelmain.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String datastr = "";
                    datastr = FormatUtil.formatJsonPretty(clinicRegisterBean);
                    JOptionPane.showMessageDialog(null, datastr, "挂号详情", JOptionPane.INFORMATION_MESSAGE);
                }

                //判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键
                if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                    hasVisited = clinicRegisterBean.getHasVisited();
                    if (hasVisited == 0) {
                        item.setText("设为已就诊");
                    } else {
                        item.setText("设为未就诊");
                    }
                    popupMenu.show(panelmain, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                color = panelmain.getBackground();
                panelmain.setBackground(new Color(100, 183, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                panelmain.setBackground(color);
            }
        });


    }

    public void print(Object o) {
        System.out.println(getClass().getName()+"------------------");
        System.out.println(o);
    }
}
