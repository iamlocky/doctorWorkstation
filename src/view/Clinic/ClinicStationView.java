package view.Clinic;

import view.ViewUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 门诊挂号台界面
 */

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
    private JPanel panelmain;
    private JTextField tfFindByName;

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

        String[] columnNames ={"姓名", "身份证", "性别"};
        Object[][] obj = new Object[2][3];
        for (int i = 0; i < 2; i++)
        {
            for (int j = 0; j < 6; j++)
            {
                switch (j)
                {
                    case 0:
                        obj[i][j] = "赵匡义";
                        break;
                    case 1:
                        obj[i][j] = "123215";
                        break;
                    case 2:
                        obj[i][j] = "男";
                        break;
                }
            }
        }
        JTable table=new JTable();
        panelmain.add(table);
        DefaultTableModel tableModel=new DefaultTableModel(obj,columnNames){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(tableModel);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount()==2){
                    int row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //获得行位置

//                    JOptionPane.showMessageDialog(null,row," ",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddRegister.main(null);
            }
        });
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow()==-1){
                    JOptionPane.showMessageDialog(null,"请先选择一个挂号信息","提示",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

}
