package view.Clinic;

import com.google.gson.reflect.TypeToken;
import controller.Controller;
import controller.IControllerListener;
import controller.SimpleListener;
import model.bean.DrugBean;
import model.bean.MedicalCase;
import model.bean.PatientInfoBean;
import model.bean.ResultBean;
import view.ViewUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查找界面
 */

public class FindDrug{
    private static JFrame frame;
    private static String title;
    private JPanel panel1;
    private JButton btnOK;
    private Map<String, String> data = new HashMap<>();
    private List<DrugBean> dataInfo;
    private SimpleListener simpleListener;
    private Controller controller;

    private JTable table;


    public void initView() {
        lb.setText(title);
        progressBar1.setIndeterminate(true);
        progressBar1.setVisible(false);
        panel1.setBorder(new EmptyBorder(10, 10, 10, 10));
        ViewUtils.changeFont(panel1);
        table = new JTable();
        panelmain.add(new JScrollPane(table), BorderLayout.CENTER);

//        panelmain.add(table);
    }

    public void setSimpleListener(SimpleListener simpleListener) {
        this.simpleListener = simpleListener;
    }

    public void initContrller() {
        controller = new Controller(new IControllerListener<ResultBean<DrugBean>>() {
            @Override
            public void done(ResultBean<DrugBean> data) {
                dataInfo = data.getResults();
                initTableModelInfo();
                progressBar1.setVisible(false);
                table.setRowSelectionInterval(0,0);
            }

            @Override
            public void doneRaw(String data) {
                progressBar1.setVisible(false);

            }

            @Override
            public void showMessage(String msg) {
                progressBar1.setVisible(false);

            }
        }, new TypeToken<ResultBean<PatientInfoBean>>() {
        }.getType());
    }

    public void initTableModelInfo() {

        String[] columnNames = {"药品名", "剂量", "类型", "编号","药厂"};
        Object[][] obj = new Object[dataInfo.size()][4];
        for (int i = 0; i < dataInfo.size(); i++) {
            DrugBean drugBean = dataInfo.get(i);
            for (int j = 0; j <5; j++) {
                switch (j) {
                    case 0:
                        obj[i][j] = drugBean.getName();
                        break;
                    case 1:
                        obj[i][j] = drugBean.getSpecification();
                        break;
                    case 2:
                        obj[i][j] = drugBean.getForm();
                        break;
                    case 3:
                        obj[i][j] = drugBean.getNum();
                        break;
                    case 4:
                        obj[i][j] = drugBean.getAddress();
                        break;
                }
            }
        }

        DefaultTableModel tableModel = new DefaultTableModel(obj, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(tableModel);
    }

    public Map<String, String> renewNameData(String s) {
        progressBar1.setVisible(true);

        if (data == null) {
            data = new HashMap<>();
        } else {
            data.clear();
        }
        data.put("name", s);

        return data;
    }

    public FindDrug(Map<String, String> data) {
        this.data = data;
        initContrller();
        initView();
        if (data != null) {
            controller.findPatient(data);
        }

        tfName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyChar()==KeyEvent.VK_ENTER )
                {
                    btnFind.doClick();
                }
            }
        });
        btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.findPatient(renewNameData(tfName.getText().trim()));

            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint()); //获得行位置
                    simpleListener.done(dataInfo.get(row));
                    frame.dispose();
                }
            }
        });

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpleListener.done(dataInfo.get(table.getSelectedRow()));
                frame.dispose();

            }
        });

    }

    public static FindDrug main(Map<String, String> data) {
        title ="查找药品";
        if (frame!=null){
            frame.dispose();
        }
        frame = new JFrame(title);
        findDrug = new FindDrug(data);
        frame.setContentPane(findDrug.panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        ViewUtils.toCenter(frame);
        return findDrug;
    }
    private static FindDrug findDrug;
    private JPanel panelmain;
    private JLabel lb;
    private JTextField tfName;
    private JProgressBar progressBar1;
    private JButton btnFind;
}