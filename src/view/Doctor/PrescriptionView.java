package view.Doctor;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PrescriptionView {
    public JPanel panelPrescription;

    public PrescriptionView() {
        panelPrescription.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }
        });
    }
}
