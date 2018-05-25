package view.Clinic;

import view.ViewUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClinicStationView {
    static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame("挂号台");
        frame.setContentPane(new ClinicStationView().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        ViewUtils.toCenter(frame);
    }

    private JPanel panel1;
    private JButton btn2;
    private JButton btn1;
    private JButton btn3;

    public void initView() {
        panel1.setBorder(new EmptyBorder(10, 10, 10, 10));
        ViewUtils.changeFont(panel1);
    }

    public ClinicStationView() {
        initView();
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddPatient.main(null);
            }
        });
    }
}
