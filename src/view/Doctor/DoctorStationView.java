package view.Doctor;

import controller.Controller;
import controller.DoctorController;
import controller.IControllerListener;
import controller.SimpleListener;
import model.bean.ClinicRegisterBean;
import model.bean.ErrInfo;
import model.bean.ResultBean;
import view.Clinic.ClinicStationView;
import view.ItemView;
import Utils.ViewUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.Date;
import java.util.List;

/**
 * 门诊医生工作台界面
 */

public class DoctorStationView {
    private static JFrame frame;
    private JPanel panel1;
    private JButton btnrefresh;
    private JProgressBar progressBar1;
    private JButton btnAddClinicRegister;
    private JLabel lbtime;
    private JLabel lbqueue;
    private JLabel lbtitle;
    JPanel panelContent1;
    private Controller controller;
    private DoctorController doctorController;
    private List<ClinicRegisterBean> registerBeanList;
    private JScrollPane scrollPane;
    private JTabbedPane tabbedPane;
    private JPanel panelContent2;
    private Timer timer;

    public static void main(String[] args) {
        if (frame != null) {
            frame.dispose();
        }
        frame = new JFrame("医生工作站---当前工作人员: " + Controller.getUser().getName() + " " + Controller.getUser().getUsername());
        frame.setContentPane(new DoctorStationView().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        ViewUtils.toCenter(frame);
    }


    public void initView() {
        lbtitle.setBorder(new EmptyBorder(0,10,0,0));
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lbtime.setText(new Date().toString());
            }
        });
        timer.start();
        panelContent1 = new JPanel();
        panelContent1.setPreferredSize(new Dimension(400, 400));
        panelContent2 = new JPanel();
        panelContent2.setPreferredSize(new Dimension(400, 400));
        tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(400, 400));
        tabbedPane.addTab("未就诊", panelContent1);
        tabbedPane.addTab("已就诊", panelContent2);
        scrollPane = new JScrollPane(tabbedPane);
        scrollPane.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));

        panel1.add(scrollPane, BorderLayout.CENTER);

        frame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                renewRegisters();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });
    }

    public DoctorStationView() {
        initView();

        controller = new Controller(new IControllerListener() {
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


        doctorController = new DoctorController();

        renewRegisters();


        btnAddClinicRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClinicStationView.main("false");
            }
        });
    }

    public void renewRegisters() {
        progressBar1.setVisible(true);
        doctorController.getRegister(new SimpleListener<ResultBean<ClinicRegisterBean>>() {
            @Override
            public void done(ResultBean<ClinicRegisterBean> data) {
                panelContent1.removeAll();
                panelContent2.removeAll();
                progressBar1.setVisible(false);
                if (data == null || data.getResults() == null) {
                    return;
                }
                try {
                    registerBeanList = data.getResults();
                    if (registerBeanList != null) {
                        for (int i = 0; i < registerBeanList.size(); i++) {
                            ClinicRegisterBean clinicRegisterBean = registerBeanList.get(i);
                            ItemView itemView = new ItemView(clinicRegisterBean);
                            if (clinicRegisterBean.getHasVisited() == 0) {
                                panelContent1.add(itemView.panel1);
                            } else {
                                panelContent2.add(itemView.panel1);
                            }
                        }
                        scrollPane.setViewportView(tabbedPane);

                    }
                    lbqueue.setText("今日已挂号" + registerBeanList.size() + "人，剩余可挂号数" + (10 - registerBeanList.size())+"");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void fail(ErrInfo errInfo) {
                progressBar1.setVisible(false);

            }

            @Override
            public void fail(String s) {
                progressBar1.setVisible(false);

            }
        });
        btnrefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                renewRegisters();
            }
        });
    }

    public void print(Object o) {
        System.out.println(getClass().getName()+"------------------");
        System.out.println(o);
    }


}
