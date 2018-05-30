package view.Doctor;

import Utils.LoggerUtil;
import controller.SimpleListener;
import model.bean.DrugBean;
import model.bean.ErrInfo;
import view.Clinic.FindDrug;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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
    public CustomJPanel panelDrugs;
    private FindDrug findDrug;

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
                findDrug=FindDrug.main(null, new SimpleListener<DrugBean>() {
                    @Override
                    public void done(DrugBean data) {
                        addDrug2Panel(data);
                    }

                    @Override
                    public void fail(ErrInfo errInfo) {

                    }

                    @Override
                    public void fail(String s) {

                    }
                });

            }
        });

        panelDrugs = addScrollPane(panel2);
//        for (int i = 0; i < 5; i++) {
//            DrugBean drugBean=new DrugBean();
//            drugBean.setCount(i);
//            addDrug2Panel(drugBean);
//        }
    }

    public void addDrug2Panel(DrugBean drugBean){
        panelDrugs.add(new DrugItem(drugBean, panelDrugs).panel1);
    }

    public JTextArea addTextAera(JPanel panel) {
        JTextArea textArea = new JTextArea(5, 25);
        textArea.setPreferredSize(new Dimension(400, 120));
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.setBorder(new EmptyBorder(5, 0, 5, 0));
        return textArea;
    }

    public CustomJPanel addScrollPane(JPanel panel) {
        JScrollPane scrollPane = new JScrollPane();
        CustomJPanel jPanel = new CustomJPanel(new FlowLayout()) {
            @Override
            public Component add(Component component) {
                setPreferredSize(new Dimension(400, (80 + 11) * (getComponentCount()+1) + 5));
                SwingUtilities.invokeLater(()->{
                    scrollPane.validate();
                });
                return super.add(component);
            }

            @Override
            public void remove(Component component) {
                super.remove(component);
                setPreferredSize(new Dimension(400, (80 + 11) * getComponentCount() + 5));
                validate();
                SwingUtilities.invokeLater(()->{
                    scrollPane.validate();
                });
            }
        };
        scrollPane.setViewportView(jPanel);
        jPanel.setPreferredSize(new Dimension(400, 120));
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.setBorder(new EmptyBorder(5, 0, 5, 0));
        return jPanel;
    }

    static class CustomJPanel extends JPanel {
        public CustomJPanel() {
            super();
        }

        public CustomJPanel(LayoutManager layout) {
            super(layout);
        }

        public void remove(Component component) {
            if (getComponentCount() <= 1) {
                LoggerUtil.log("removeAll");
                super.removeAll();
                repaint();
            } else {
                super.remove(component);
                super.validate();
            }
        }
    }
}
