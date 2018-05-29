package view.Doctor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CaseView {

    public JPanel panelCase;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JTextArea textArea4;
    public CaseView() {
        textArea1=addTextAera(panel1);
        textArea2=addTextAera(panel2);
        textArea3=addTextAera(panel3);
        textArea4=addTextAera(panel4);
}

    public JTextArea addTextAera(JPanel panel){
        JTextArea textArea=new JTextArea(5,25);
        textArea.setPreferredSize(new Dimension(400,120));
        panel.add(new JScrollPane(textArea),BorderLayout.CENTER);
        panel.setBorder(new EmptyBorder(5,0,5,0));
        return textArea;
    }
}
