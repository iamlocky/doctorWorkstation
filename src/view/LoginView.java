package view;

import com.google.gson.Gson;
import controller.Controller;
import controller.IControllerListener;
import model.Model;
import Utils.StringUtil;
import model.bean.ErrInfo;
import model.bean.User;
import view.Doctor.DoctorStationView;
import view.Clinic.ClinicStationView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView implements IControllerListener<User> {
    private static JFrame frame;
    private static String[] args1;
    private JPanel panel1;
    private JButton buttonLogin;
    private JTextField name;
    private JPasswordField passwordField1;
    private JProgressBar progressBar1;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private ButtonGroup buttonGroup;
    private Gson gson = Model.getGson();
    private Controller controller = new Controller(this, User.class);

    private int type = 0;

    public static void main(String[] args) {
        args1=args;
        frame = new JFrame("登录门诊医生工作站");
        frame.setContentPane(new LoginView().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        ViewUtils.toCenter(frame);
    }

    void initView() {
        if (args1!=null&&args1.length==2){
            name.setText(args1[0]);
            passwordField1.setText(args1[1]);
        }
        panel1.setBorder(new EmptyBorder(10, 10, 10, 10));
        progressBar1.setVisible(false);
        ViewUtils.changeFont(panel1);
        buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);

        radioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type = 0;
            }
        });
        radioButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type = 1;
            }
        });
        radioButton1.setSelected(true);
        buttonLogin.setVerticalTextPosition(SwingConstants.CENTER);
    }


    public LoginView() {
        initView();

        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (StringUtil.isEmpty(name.getText()) || passwordField1.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(null, "不能为空", "登录失败", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                progressBar1.setVisible(true);
                controller.login(name.getText(), new String(passwordField1.getPassword()));
            }
        });

    }

    @Override
    public void done(User data) {
        if (data == null)
            return;
        progressBar1.setVisible(false);

        try {
            switch (type) {
                case 0: {
                    ClinicStationView.main(null);
                    break;
                }
                case 1: {
                    DoctorStationView.main(null);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame.dispose();
        JOptionPane.showMessageDialog(null, "员工 " + data.getUsername() + "登录成功！", "欢迎", JOptionPane.INFORMATION_MESSAGE);

    }

    @Override
    public void done(String data) {
        ErrInfo errInfo = new ErrInfo();
        try {
            errInfo = gson.fromJson(data, ErrInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, errInfo.getError() == null ? data : "错误码" + errInfo.getCode() + "\n" +
                "信息：" + errInfo.getError(), "登录失败", JOptionPane.ERROR_MESSAGE);
        progressBar1.setVisible(false);

    }

    @Override
    public void showMessage(String msg) {
        JOptionPane.showConfirmDialog(null, msg, "title", JOptionPane.YES_NO_OPTION);
    }


}

