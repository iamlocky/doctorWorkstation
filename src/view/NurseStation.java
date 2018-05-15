package view;

import javax.swing.*;

public class NurseStation {
    public static void main(String[] args) {
        JFrame frame = new JFrame("NurseStation");
        frame.setContentPane(new NurseStation().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        ViewUtils.toCenter(frame);
    }

    private JPanel panel1;

    public NurseStation(){
    }
}
