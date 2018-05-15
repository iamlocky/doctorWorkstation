package view;

import javax.swing.*;
import java.awt.*;

public class ViewUtils {
    private static Font font = new Font("微软雅黑", Font.PLAIN, 12);

    public static void changeFont(JComponent jComponent) {
        int count = jComponent.getComponentCount();
        for (int i = 0; i < count; i++) {
            Component component = jComponent.getComponent(i);
            component.setFont(font);
            component.setPreferredSize(new Dimension(-1, 25));
        }
    }

    public static void toCenter(Frame frame){
        int windowWidth = frame.getWidth(); //获得窗口宽
        int windowHeight = frame.getHeight(); //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//设置窗口居中显示
    }
}
