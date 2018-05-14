import com.google.gson.Gson;
import controller.Controller;
import controller.IControllerListener;
import model.Model;
import model.StringUtil;
import model.bean.ErrInfo;
import model.bean.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class lLogin implements IControllerListener<User> {
    private static JFrame frame;
    private JPanel panel1;
    private JButton buttonLogin;
    private JTextField name;
    private JPasswordField passwordField1;
    private JProgressBar progressBar1;
    private Gson gson = Model.getGson();

    private Controller controller = new Controller(this, User.class);

    public static void main(String[] args) {
        frame = new JFrame("登录门诊医生工作站");
        frame.setContentPane(new lLogin().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        int windowWidth = frame.getWidth(); //获得窗口宽
        int windowHeight = frame.getHeight(); //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//设置窗口居中显示
    }

    void initView() {
        progressBar1.setVisible(false);
        Main.changeFont(panel1);
        panel1.setBorder(new EmptyBorder(10,10,10,10));
    }



    public lLogin() {
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

        JOptionPane.showMessageDialog(null, "员工 " + data.getUsername() + "登录成功！", "欢迎", JOptionPane.INFORMATION_MESSAGE);
        frame.dispose();
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

