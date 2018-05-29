package view.Doctor;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class AdviceView {
    public JPanel panelAdvice;
    private JPanel panel1;

    public AdviceView() {
        panelAdvice.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
            }
        });
    }
}
