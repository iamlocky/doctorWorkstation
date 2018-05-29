package view.Doctor;

import controller.Controller;
import controller.DoctorController;
import controller.IControllerListener;
import controller.SimpleListener;
import model.bean.ClinicRegisterBean;
import model.bean.ErrInfo;
import model.bean.ResultBean;
import view.ItemView;
import Utils.ViewUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.List;

/**
 * 门诊医生工作台界面
 */

public class DoctorStationView {
    private static JFrame frame;
    private JPanel panel1;
    private JButton btnrefresh;
    private JProgressBar progressBar1;
    JPanel panelContent1;
    private Controller controller;
    private DoctorController doctorController;
    private List<ClinicRegisterBean> registerBeanList;
    private JScrollPane scrollPane;
    private JTabbedPane tabbedPane;
    private JPanel panelContent2;

    public static void main(String[] args) {
        if (frame!=null){
            frame.dispose();
        }
        frame = new JFrame("医生工作站---当前工作人员: " + Controller.getUser().getName() +" "+ Controller.getUser().getUsername());
        frame.setContentPane(new DoctorStationView().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        ViewUtils.toCenter(frame);
    }


    public void initView(){
        panelContent1 =new JPanel();
        panelContent1.setPreferredSize(new Dimension(400,400));
        panelContent2 =new JPanel();
        panelContent2.setPreferredSize(new Dimension(400,400));
        tabbedPane=new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(400,400));
        tabbedPane.addTab("未就诊", panelContent1);
        tabbedPane.addTab("已就诊", panelContent2);
        scrollPane=new JScrollPane(tabbedPane);
        scrollPane.setBorder(new LineBorder(new Color(0,0,0),1,true));

        panel1.add(scrollPane,BorderLayout.CENTER);

        frame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                renewRegisters();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });}

    public DoctorStationView() {
        initView();

        controller=new Controller(new IControllerListener() {
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


        doctorController=new DoctorController();

        renewRegisters();




    }

    public void renewRegisters(){
        progressBar1.setVisible(true);
        doctorController.getRegister(new SimpleListener<ResultBean<ClinicRegisterBean>>() {
            @Override
            public void done(ResultBean<ClinicRegisterBean> data) {
                panelContent1.removeAll();
                panelContent2.removeAll();
                progressBar1.setVisible(false);

                registerBeanList=data.getResults();
                if (registerBeanList!=null){
                    for (int i = 0; i <registerBeanList.size() ; i++) {
                        ClinicRegisterBean clinicRegisterBean=registerBeanList.get(i);
                        ItemView itemView=new ItemView(clinicRegisterBean);
                        if (clinicRegisterBean.getHasVisited()==0) {
                            panelContent1.add(itemView.panel1);
                        }else {
                            panelContent2.add(itemView.panel1);
                        }
                    }
                    scrollPane.setViewportView(tabbedPane);

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
    public void print(Object o){
        System.out.println(o);
    }


}
