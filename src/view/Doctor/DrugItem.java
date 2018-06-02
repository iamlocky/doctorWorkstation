package view.Doctor;

import Utils.Toast;
import Utils.ViewUtils;
import controller.SimpleListener;
import javafx.scene.input.KeyCode;
import model.bean.DrugBean;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;

public class DrugItem extends JComponent {
    private SimpleListener simpleListener;
    public JPanel panel1;
    public JButton btnSub;
    public JButton btnAdd;
    public JButton btnDel;
    private JLabel lbname;
    private JLabel lbnum;
    private JLabel lbprice;
    private JTextField tfcount;
    private JPanel panelmain;
    public DrugBean drugBean;
    public Integer count = 1;
    public Integer index;
    public PrescriptionView.CustomJPanel parent;

    @Override
    public JPanel getParent() {
        return parent;
    }

    public void setParent(PrescriptionView.CustomJPanel parent) {
        this.parent = parent;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public DrugBean getDrugBean() {
        return drugBean;
    }

    public void setDrugBean(DrugBean drugBean) {
        this.drugBean = drugBean;
    }

    public DrugItem(DrugBean drugBean, PrescriptionView.CustomJPanel parent, SimpleListener simpleListener) {
        this.simpleListener = simpleListener;
        this.drugBean = drugBean;
        count = drugBean.getCount();
        simpleListener.done("done");
        try {
            tfcount.setText(count.toString());
            lbname.setText(drugBean.getName());
            lbname.setToolTipText(drugBean.getName());
            lbnum.setText(drugBean.getSpecification());
            lbnum.setToolTipText(drugBean.getSpecification());
            lbprice.setText(String.format("%.2f", drugBean.getTotalPrice()) + "元");
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.remove(panel1, drugBean);
                simpleListener.done("done");
            }
        });
        btnSub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count = drugBean.getCount();
                if (count > 1) {
                    drugBean.setCount(--count);
                    tfcount.setText(count.toString());
                    lbprice.setText(String.format("%.2f", drugBean.getTotalPrice()) + "元");
                    simpleListener.done("done");
                } else {
                    if (ViewUtils.currentFrame != null) {
                        new Toast(ViewUtils.currentFrame, "数量最小为1", 1500, Toast.error).start();
                    }
                }
            }
        });
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drugBean.setCount(++count);
                tfcount.setText(count.toString());
                lbprice.setText(String.format("%.2f", drugBean.getTotalPrice()) + "元");
                simpleListener.done("done");
            }
        });

        tfcount.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // 只能输入数字
                char keyChar = e.getKeyChar();
                if ((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) || (keyChar == KeyEvent.VK_BACK_SPACE || keyChar == KeyEvent.VK_DELETE)) {
                } else {
                    if (keyChar == KeyEvent.VK_ENTER) {
                        tfcount.transferFocus();
                    } else
                        new Toast(ViewUtils.currentFrame, "只能输入数字", 1500, Toast.error).start();
                    e.consume();
                }
            }
        });
        tfcount.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!renewCount(tfcount.getText().trim())) {
                    tfcount.setText(count.toString());
                }

            }
        });

//        tfcount.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//            }
//        });

        panelmain.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (tfcount.hasFocus()) {
                    tfcount.transferFocus();
                }
            }
        });
    }

    public boolean renewCount(String s) {
        if (s.equals("")) {
            return false;
        }
        Integer count = Integer.parseInt(s);
        if (count < 1) {
            new Toast(ViewUtils.currentFrame, "数量需大于1", 1500, Toast.error).start();
            return false;
        }
        this.count = count;
        drugBean.setCount(count);
        if (!s.equals(count.toString())) {
            tfcount.setText(count.toString());
        }
        lbprice.setText(String.format("%.2f", drugBean.getTotalPrice()) + "元");
        simpleListener.done("done");
        return true;
    }

}
