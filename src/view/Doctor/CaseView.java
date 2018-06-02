package view.Doctor;

import Utils.ViewUtils;

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
        textArea1= ViewUtils.addTextAera(panel1,400,120);
        textArea2=ViewUtils.addTextAera(panel2,400,120);
        textArea3=ViewUtils.addTextAera(panel3,400,120);
        textArea4=ViewUtils.addTextAera(panel4,400,120);
}

}
