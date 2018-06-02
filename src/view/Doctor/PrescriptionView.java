package view.Doctor;

import Utils.LoggerUtil;
import Utils.Toast;
import Utils.ViewUtils;
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
import java.util.HashSet;
import java.util.Set;

public class PrescriptionView {
    public JPanel panelPrescription;
    private JPanel panel1;
    private JButton btnAddDrug;
    private JLabel lbDrugAllergy;
    private JButton btnClear;
    private JPanel panel2;
    private JPanel panel3;
    private JTextField lbSum;
    public CustomJPanel panelDrugs;
    private FindDrug findDrug;
    private Set<DrugBean> drugBeans = new HashSet<>();
    private Double totalCount = 0.0;

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
                findDrug = FindDrug.main(null, new SimpleListener<DrugBean>() {
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
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelDrugs.removeAll();
                drugBeans.clear();
                renewTotalPrice();
                try {
                    new Toast(ViewUtils.currentFrame,"已清空药品",1800,Toast.success).start();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    SimpleListener<String> renewPriceListener = new SimpleListener<String>() {
        @Override
        public void done(String data) {
            renewTotalPrice();
        }

        @Override
        public void fail(ErrInfo errInfo) {

        }

        @Override
        public void fail(String s) {

        }
    };

    public void addDrug2Panel(DrugBean drugBean) {
        if (drugBeans.add(drugBean)) {
            panelDrugs.add(new DrugItem(drugBean, panelDrugs, renewPriceListener).panel1);
        } else {
            SwingUtilities.invokeLater(() -> {
                if (ViewUtils.currentFrame != null) {
                    new Toast(ViewUtils.currentFrame, "已添加了该药品", 1500, Toast.error).start();
                }
            });
        }
    }

    void renewTotalPrice() {
        if (drugBeans == null || drugBeans.size() == 0) {
            drugBeans = new HashSet<>();
            lbSum.setText(String.format("%.2f", 0.0) + "元");
            return;
        }
        DrugBean[] drugBeans1 = new DrugBean[drugBeans.size()];
        drugBeans.toArray(drugBeans1);
        totalCount = 0.0;

        for (int i = 0; i < drugBeans1.length; i++) {
            totalCount += drugBeans1[i].getTotalPrice();
        }
        lbSum.setText(String.format("%.2f", totalCount) + "元");
    }

    public CustomJPanel addScrollPane(JPanel panel) {
        JScrollPane scrollPane = new JScrollPane();
        CustomJPanel jPanel = new CustomJPanel(new FlowLayout()) {
            @Override
            public Component add(Component component) {
                setPreferredSize(new Dimension(400, (80 + 11) * (getComponentCount() + 1) + 5));
                SwingUtilities.invokeLater(() -> {
                    scrollPane.validate();
                });
                return super.add(component);
            }


            public void remove(Component component, DrugBean drugBean) {
                super.remove(component);
                if (drugBeans != null) {
                    drugBeans.remove(drugBean);
                }
                setPreferredSize(new Dimension(400, (80 + 11) * getComponentCount() + 5));
                validate();
                SwingUtilities.invokeLater(() -> {
                    scrollPane.repaint();
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

        public void remove(Component component, DrugBean drugBean) {
            if (getComponentCount() <= 1) {
                LoggerUtil.log("removeAll");
                super.removeAll();
                repaint();
            } else {
                super.remove(component);
                super.validate();
            }
        }

        public void removeAll(){
            super.removeAll();
            repaint();
        }
    }
}
