package view.Doctor;

import Utils.ViewUtils;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class AdviceView {
    public JPanel panelAdvice;
    private JPanel panel1;
    private JTextArea textArea;

    public AdviceView() {
        panelAdvice.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
            }
        });
        textArea= ViewUtils.addTextAera(panel1);
        ViewUtils.changeFont(textArea,16);
    }
}
