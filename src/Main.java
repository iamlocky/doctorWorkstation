import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
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
        try {
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }

        testData=new String[]{"123","123"};
        LoginView.main(testData);
    }


}
