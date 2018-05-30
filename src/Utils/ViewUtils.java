package Utils;

import javax.swing.*;
import java.awt.*;
/**
 * 界面效果工具
 */
public class ViewUtils {
    private static Font font = new Font("微软雅黑", Font.PLAIN, 12);
    public static JFrame currentFrame;

    public static void changeFont(JComponent jComponent) {
        int count = jComponent.getComponentCount();
        int size=12;
        for (int i = 0; i < count; i++) {
            Component component = jComponent.getComponent(i);
            size=component.getFont().getSize();
            size=size>=12?size:12;
            font= new Font("微软雅黑", Font.PLAIN, size);
            component.setFont(font);
            if (component.getPreferredSize().height<25) {
                component.setPreferredSize(new Dimension(component.getPreferredSize().width, 25));
            }
            try {
                JComponent jComponent1=(JComponent)component;
                if (jComponent1.getComponentCount()!=0){
                    changeFont(jComponent1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void toCenter(JFrame frame){
        currentFrame=frame;
        int windowWidth = frame.getWidth(); //获得窗口宽
        int windowHeight = frame.getHeight(); //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//设置窗口居中显示
    }
}
