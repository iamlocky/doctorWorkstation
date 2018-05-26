
import org.pushingpixels.substance.api.skin.*;
import view.LoginView;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static Font font = new Font("微软雅黑", Font.PLAIN, 12);
    private static String[] testData;
    public static void main(String[] args) {
        UIManager.put("RootPane.setupButtonVisible", false);
        UIManager.put("OptionPane.font", font);
        UIManager.put("OptionPane.messageFont", font);
        UIManager.put("OptionPane.buttonFont", font);
        JFrame.setDefaultLookAndFeelDecorated(true);//设置窗口
        JDialog.setDefaultLookAndFeelDecorated(true);//设置对话框
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(new SubstanceGraphiteAquaLookAndFeel());
                    testData=new String[]{"张三","123"};
                    LoginView.main(testData);
                } catch (Exception e) {
                    System.out.println("Substance Graphite failed to initialize");
                }
            }
        });


    }


}
