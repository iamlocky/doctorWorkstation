package view.Doctor;

import Utils.Toast;
import Utils.ViewUtils;
import model.bean.DrugBean;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrugItem extends JComponent{
    public JPanel panel1;
    public JButton btnSub;
    public JButton btnAdd;
    public JButton btnDel;
    private JLabel lbcount;
    private JLabel lbname;
    private JLabel lbnum;
    private JLabel lbprice;
    public DrugBean drugBean;
    public Integer count=1;
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

    public DrugItem(DrugBean drugBean,PrescriptionView.CustomJPanel parent) {
        this.drugBean=drugBean;
        count=drugBean.getCount();

        try {
            lbcount.setText(count.toString());
            lbname.setText(drugBean.getName());
            lbname.setToolTipText(drugBean.getName());
            lbnum.setText(drugBean.getSpecification());
            lbnum.setToolTipText(drugBean.getSpecification());
            lbprice.setText(String.format("%.2f",drugBean.getTotalPrice())+"元");
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.remove(panel1,drugBean);

            }
        });
        btnSub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count=drugBean.getCount();
                if (count>1){
                    drugBean.setCount(--count);
                    lbcount.setText(count.toString());
                    lbprice.setText(String.format("%.2f",drugBean.getTotalPrice())+"元");
                }else {
                    if (ViewUtils.currentFrame!=null) {
                        new Toast(ViewUtils.currentFrame,"数量最小为1",1500,Toast.error).start();
                    }
                }
            }
        });
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drugBean.setCount(++count);
                lbcount.setText(count.toString());
                lbprice.setText(String.format("%.2f",drugBean.getTotalPrice())+"元");
            }
        });
    }
}
