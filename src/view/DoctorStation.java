package view;

import javax.swing.*;
import java.awt.*;

public class DoctorStation{
    public static void main(String[] args) {
        frame = new JFrame("医生工作站");
        new DoctorStation();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        ViewUtils.toCenter(frame);
    }
    private static JFrame frame;
    private JPanel panel1;
    private JButton button1;

    public DoctorStation() {
        Container container=frame.getContentPane();//创建一个容器

        //创建文本区域组件
        JTextArea ta=new JTextArea(20,50);

        //创建JScrollPane()面板对象,并将文本域对象添加到面板中
        JScrollPane sp=new JScrollPane(panel1);

        //将该面板添加到该容器中
        container.add(sp);
        //设置容器的外部特性
        frame.setSize(400,400);//设置窗口的大小
    }

}
