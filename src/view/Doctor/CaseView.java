package view.Doctor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CaseView {
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    public JPanel panelCase;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;

    public CaseView() {
        textArea1=new JTextArea(5,25);
        textArea1.setPreferredSize(new Dimension(400,100));
        jScrollPane1=new JScrollPane(textArea1);
        panel1.add(jScrollPane1,BorderLayout.CENTER);
        panel1.setBorder(new EmptyBorder(10,10,10,10));

        textArea2=new JTextArea(5,25);
        textArea2.setPreferredSize(new Dimension(400,100));
        jScrollPane2=new JScrollPane(textArea2);
        panel2.add(jScrollPane2,BorderLayout.CENTER);
        panel2.setBorder(new EmptyBorder(10,10,10,10));

        textArea3=new JTextArea(5,25);
        textArea3.setPreferredSize(new Dimension(400,100));
        jScrollPane3=new JScrollPane(textArea3);
        panel3.add(jScrollPane3,BorderLayout.CENTER);
        panel3.setBorder(new EmptyBorder(10,10,10,10));
    }
}
