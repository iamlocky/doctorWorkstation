package view.Doctor;

import view.Clinic.FindDrug;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PrescriptionView {
    public JPanel panelPrescription;
    private JPanel panel1;
    private JButton btnAddDrug;
    private JLabel lbDrugAllergy;
    private JButton 清空Button;
    private JPanel panel2;
    private JPanel panel3;

    public PrescriptionView() {
        panelPrescription.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }
        });
        btnAddDrug.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FindDrug.main(null);
            }
        });
    }
}
