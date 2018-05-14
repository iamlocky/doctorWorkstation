import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static Font font = new Font("微软雅黑", Font.PLAIN, 12);

    public static void main(String[] args) {
        try {
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
        }
        UIManager.put("RootPane.setupButtonVisible", false);
        UIManager.put("OptionPane.font", font);
        UIManager.put("OptionPane.messageFont", font);
        UIManager.put("OptionPane.buttonFont", font);
        lLogin.main(null);
    }

    public static void changeFont(JComponent jComponent) {
        int count = jComponent.getComponentCount();
        for (int i = 0; i < count; i++) {
            Component component = jComponent.getComponent(i);
            component.setFont(font);
            component.setPreferredSize(new Dimension(-1, 25));
        }
    }
}
