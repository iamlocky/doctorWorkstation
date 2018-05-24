package view.Nurse;

import view.ViewUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AddPatient {
    static JFrame frame;
    private JPanel panel1;
    private JTextField tfName;
    private JRadioButton rb1;
    private JRadioButton rb2;
    private JTextField tfADrug;
    private JTextField tfAdress;
    private JTextField tfInsuranceNumber;

    public static void main(String[] args) {
        frame = new JFrame("新建患者信息表");
        frame.setContentPane(new AddPatient().panel1);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        ViewUtils.toCenter(frame);
    }

    private JTextField tfIDNumber;

    public void initView() {
        panel1.setBorder(new EmptyBorder(10, 10, 10, 10));
        ViewUtils.changeFont(panel1);
    }

    public AddPatient() {
        initView();
    }
}
