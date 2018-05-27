package view.Doctor;

import controller.Controller;
import controller.DoctorController;
import controller.IControllerListener;
import controller.SimpleListener;
import model.bean.ClinicRegisterBean;
import model.bean.ErrInfo;
import model.bean.ResultBean;
import view.ItemView;
import view.ViewUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * 门诊医生工作台界面
 */

public class DoctorStationView {
    private static JFrame frame;
    private JPanel panel1;
    private JButton button1;
    JPanel panelContent;
    private Controller controller;
    private DoctorController doctorController;
    private List<ClinicRegisterBean> registerBeanList;
    private JScrollPane scrollPane;

    public static void main(String[] args) {
        frame = new JFrame("医生工作站---当前工作人员: " + Controller.getUser().getName() +" "+ Controller.getUser().getUsername());
        frame.setContentPane(new DoctorStationView().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        ViewUtils.toCenter(frame);
    }


    public void initView(){
        panel1.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelContent=new JPanel();
        panelContent.setPreferredSize(new Dimension(400,400));

        scrollPane=new JScrollPane(panelContent);
        panel1.add(scrollPane,BorderLayout.CENTER);}

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
        doctorController.getRegister(new SimpleListener<ResultBean<ClinicRegisterBean>>() {
            @Override
            public void done(ResultBean<ClinicRegisterBean> data) {
                registerBeanList=data.getResults();
                if (registerBeanList!=null){
                    for (int i = 0; i <registerBeanList.size() ; i++) {
                        print(i);
                        ItemView itemView=new ItemView(registerBeanList.get(i));
                        panelContent.add(itemView.panel1);
                    }
                    scrollPane.setViewportView(panelContent);

                }

            }

            @Override
            public void fail(ErrInfo errInfo) {

            }

            @Override
            public void fail(String s) {

            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doctorController.print(null);
            }
        });


    }
    public void print(Object o){
        System.out.println(o);
    }
}
