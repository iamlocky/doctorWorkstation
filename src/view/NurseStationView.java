package view;

import javax.swing.*;

public class NurseStationView {
    public static void main(String[] args) {
        JFrame frame = new JFrame("NurseStationView");
        frame.setContentPane(new NurseStationView().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        ViewUtils.toCenter(frame);
    }

    private JPanel panel1;

    public NurseStationView(){
    }
}
