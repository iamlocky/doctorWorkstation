package Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 界面效果工具
 */
public class ViewUtils {
    private static Font font = new Font("微软雅黑", Font.PLAIN, 12);
    public static JFrame currentFrame;

    public static void changeFont(JComponent jComponent) {
        int count = jComponent.getComponentCount();
        int size = 12;
        size = jComponent.getFont().getSize();
        size = size >= 12 ? size : 12;
        font = new Font("微软雅黑", Font.PLAIN, size);
        jComponent.setFont(font);

        for (int i = 0; i < count; i++) {
            Component component = jComponent.getComponent(i);

            component.setFont(font);
            if (component.getPreferredSize().height < 25) {
                component.setPreferredSize(new Dimension(component.getPreferredSize().width, 25));
            }
            try {
                JComponent jComponent1 = (JComponent) component;
                if (jComponent1.getComponentCount() > 0) {
                    changeFont(jComponent1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void changeFont(JComponent jComponent,int size) {
        font = new Font("微软雅黑", Font.PLAIN, size);
        jComponent.setFont(font);
        int count = jComponent.getComponentCount();
        for (int i = 0; i < count; i++) {
            Component component = jComponent.getComponent(i);
            component.setFont(font);
            if (component.getPreferredSize().height < 25) {
                component.setPreferredSize(new Dimension(component.getPreferredSize().width, 25));
            }
            try {
                JComponent jComponent1 = (JComponent) component;
                if (jComponent1.getComponentCount() > 0) {
                    changeFont(jComponent1,size);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void toCenter(JFrame frame) {
        currentFrame = frame;
        int windowWidth = frame.getWidth(); //获得窗口宽
        int windowHeight = frame.getHeight(); //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//设置窗口居中显示
    }

    public static JTextArea addTextAera(JPanel panel, int...widthAndHeight) {
        JTextArea textArea = new JTextArea(5, 25);
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem item = new JMenuItem("复制文字");
        popupMenu.add(item);
        if (widthAndHeight != null && widthAndHeight.length == 2) {
            textArea.setPreferredSize(new Dimension(widthAndHeight[0], widthAndHeight[1]));

        } else
            textArea.setPreferredSize(new Dimension(400, 150));
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.setBorder(new EmptyBorder(5, 0, 5, 0));

        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                    popupMenu.show(textArea, e.getX(), e.getY());
                }
            }
        });

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSysClipboardText(textArea.getText());
                if (currentFrame!=null){
                    new Toast(currentFrame, "已复制文字", 1500, Toast.success).start();
                }
            }
        });
        return textArea;
    }

    /**
     * 将字符串复制到剪切板。
     */
    public static void setSysClipboardText(String s) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(s);
        clip.setContents(tText, null);
    }
}
