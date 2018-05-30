package view.Clinic;

import com.google.gson.reflect.TypeToken;
import controller.Controller;
import controller.IControllerListener;
import controller.SimpleListener;
import model.bean.MedicalCase;
import model.bean.PatientInfoBean;
import model.bean.ResultBean;
import Utils.ViewUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 查找界面
 */

public class FindInfo {
    private static JFrame frame;
    private static String title;
    private int type;//0 patientInfo，1 RegisterBean
    private JPanel panel1;
    private JButton btnOK;
    private LinkedHashMap<String, String> data = new LinkedHashMap<>();
    private List<PatientInfoBean> dataInfo;
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
        switch (type) {
            case 0: {
                controller = new Controller(new IControllerListener<ResultBean<PatientInfoBean>>() {
                    @Override
                    public void done(ResultBean<PatientInfoBean> data) {
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
                break;
            }
            case 1: {
                controller = new Controller(new IControllerListener<ResultBean<MedicalCase>>() {
                    @Override
                    public void done(ResultBean<MedicalCase> data) {
                        progressBar1.setVisible(false);

                    }

                    @Override
                    public void doneRaw(String data) {
                        progressBar1.setVisible(false);

                    }

                    @Override
                    public void showMessage(String msg) {
                        progressBar1.setVisible(false);

                    }
                }, new TypeToken<ResultBean<MedicalCase>>() {
                }.getType());
                break;
            }
        }
    }

    public void initTableModelInfo() {
        lbcount.setText(dataInfo.size()+"条数据");
        String[] columnNames = {"姓名", "性别", "身份证", "建卡时间"};
        Object[][] obj = new Object[dataInfo.size()][columnNames.length];
        for (int i = 0; i < dataInfo.size(); i++) {
            PatientInfoBean patientInfo = dataInfo.get(i);
            for (int j = 0; j <= 3; j++) {
                switch (j) {
                    case 0:
                        obj[i][j] = patientInfo.getName();
                        break;
                    case 1:
                        obj[i][j] = patientInfo.getGender() == 0 ? "男" : "女";
                        break;
                    case 2:
                        obj[i][j] = patientInfo.getIdCardNumber();
                        break;
                    case 3:
                        obj[i][j] = patientInfo.getCreatedAt();
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

    public LinkedHashMap<String, String> renewNameData(String s) {
        progressBar1.setVisible(true);

        if (data == null) {
            data = new LinkedHashMap<>();
        } else {
            data.clear();
        }
        data.put("name", s);

        return data;
    }

    public FindInfo(LinkedHashMap<String, String> data, int type) {
        this.data = data;
        this.type = type;
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

    public static FindInfo main(LinkedHashMap<String, String> data, int type) {
        title = type == 0 ? "查找病人信息" : "查找挂号信息";
        if (frame!=null){
            frame.dispose();
        }
        frame = new JFrame(title);
        findInfo = new FindInfo(data, type);
        frame.setContentPane(findInfo.panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        ViewUtils.toCenter(frame);
        return findInfo;
    }
    private static FindInfo findInfo;
    private JPanel panelmain;
    private JLabel lb;
    private JTextField tfName;
    private JProgressBar progressBar1;
    private JButton btnFind;
    private JLabel lbcount;
}
