package view.Clinic;

import Utils.FormatUtil;
import Utils.StringUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.ClinicController;
import controller.Controller;
import controller.IControllerListener;
import controller.SimpleListener;
import model.Model;
import model.bean.*;
import view.ViewUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * 门诊挂号台界面
 */

public class ClinicStationView {
    static JFrame frame;
    private JPanel panel1;
    private JButton btn2;
    private JButton btn1;
    private JButton btn3;
    private JPanel panelmain;
    private JTextField tfFindByName;
    private JButton btnRefresh;
    private JProgressBar progressBar1;
    private JButton btnClear;
    private ClinicController clinicController;
    private Controller controller;
    private JTable table;
    private List<ClinicRegisterBean> datas;
    private List<DoctorBean> doctorBeanList;
    private Timer timer;
    private int index = -1;
    private String currentObjectID = "";
    private TableRowSorter<TableModel> sorter;

    public static void main(String[] args) {
        frame = new JFrame("挂号台---当前工作人员: " + Controller.getUser().getName() +" "+ Controller.getUser().getUsername());
        frame.setContentPane(new ClinicStationView().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        ViewUtils.toCenter(frame);

    }

    public void renewTable() {
        String[] columnNames = {"患者", "科室", "医生", "排队号", "流水号", "状态", "挂号时间"};
        Object[][] obj = new Object[datas.size()][columnNames.length];
        try {
            for (int i = 0; i < datas.size(); i++) {
                ClinicRegisterBean registerBean = datas.get(i);
                for (int j = 0; j < columnNames.length; j++) {
                    switch (j) {
                        case 0:
                            String name0=registerBean.getPatientID();
                            PatientInfoBean patientInfoBean=controller.findLocalPatient(name0);
                            if (patientInfoBean!=null){
                                if (registerBean.getPatientInfoBean()==null) {
                                    registerBean.setPatientInfoBean(patientInfoBean);
                                }
                                name0=patientInfoBean.getName();
                            }
                            obj[i][j] = name0;
                            break;
                        case 1:
                            obj[i][j] = registerBean.getDepartment() == 0 ? "内科" : "外科";
                            break;
                        case 2:
                            String name1=registerBean.getDoctorID();
                            DoctorBean doctorBean=controller.findLocalDoctor(name1);
                            if (doctorBean!=null){
                                if (registerBean.getDoctorBean()==null) {
                                    registerBean.setDoctorBean(doctorBean);
                                }
                                name1=doctorBean.getName();
                            }
                            obj[i][j] = name1;
                            break;
                        case 3:
                            obj[i][j] = registerBean.getQueueNumber();
                            break;
                        case 4:
                            obj[i][j] = registerBean.getObjectId();
                            break;
                        case 5:
                            obj[i][j] = registerBean.getHasVisited()==0?"未就诊":"已就诊";
                            break;
                        case 6:
                            obj[i][j] = registerBean.getCreatedAt();
                            break;
                    }
                }
            }
            DefaultTableModel tableModel = new DefaultTableModel(obj, columnNames) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table.clearSelection();
            table.setModel(tableModel);
            sorter = new TableRowSorter<TableModel>(tableModel);
            List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
            sortKeys.add(new RowSorter.SortKey(5, SortOrder.DESCENDING));
            sorter.setSortKeys(sortKeys);
            table.setRowSorter(sorter);
            renewFindFilter();
        } catch (Exception e) {
            e.printStackTrace();
        }

        progressBar1.setVisible(false);

    }

    public void initView() {
        panel1.setBorder(new EmptyBorder(10, 10, 10, 10));
        ViewUtils.changeFont(panel1);
        progressBar1.setVisible(false);

        controller = new Controller(new IControllerListener<ResultBean<DoctorBean>>() {
            @Override
            public void done(ResultBean<DoctorBean> data) {
                doctorBeanList = Controller.getDoctorList();

            }

            @Override
            public void doneRaw(String data) {

            }

            @Override
            public void showMessage(String msg) {

            }
        }, null);

        controller.getDoctors();
        clinicController = new ClinicController();
//        renewRegisterList();
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panelmain.add(scrollPane);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                index = table.getSelectedRow();
                if (e.getClickCount() == 2) {
                    currentObjectID = (String) table.getValueAt(index, 4);
                    try {
                        ClinicRegisterBean bean=clinicController.findLocalRegister(currentObjectID);
                        String datastr="";
                        if (bean!=null){
                            datastr= FormatUtil.formatJsonPretty(bean);
                        }
                        JOptionPane.showMessageDialog(null,datastr,"挂号详情",JOptionPane.INFORMATION_MESSAGE);
                    } catch (HeadlessException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        frame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                renewRegisterList();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });
    }

    public void renewRegisterList() {
        progressBar1.setVisible(true);

        clinicController.getRegisterList(new SimpleListener<List<ClinicRegisterBean>>() {
            @Override
            public void done(List<ClinicRegisterBean> data) {
                datas = data;
                renewTable();
            }

            @Override
            public void fail(ErrInfo errInfo) {
                JOptionPane.showMessageDialog(null, errInfo.getError() == null ? "" : ("错误码：" + errInfo.getCode() + "\n" +
                        "信息：" + errInfo.getError()), "获取数据失败", JOptionPane.ERROR_MESSAGE);
            }

            @Override
            public void fail(String s) {
                JOptionPane.showMessageDialog(null, s, "获取数据失败", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void renewFindFilter(){
        if (!StringUtil.isEmpty(tfFindByName.getText())) {
            if (sorter!=null){
                sorter.setRowFilter(RowFilter.regexFilter(tfFindByName.getText()));
            }
        }else {
            if (sorter!=null){
                sorter.setRowFilter(null);
            }
        }
    }

    public ClinicStationView() {
        initView();

        tfFindByName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyChar()==KeyEvent.VK_ENTER )
                {
                    renewFindFilter();
                }
            }
        });
        tfFindByName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                renewFindFilter();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                renewFindFilter();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                renewFindFilter();

            }
        });
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddPatient.main(null);
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
                if (table.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "请先选择一个挂号信息", "提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                try {
                    index=table.getSelectedRow();
                    currentObjectID = (String) table.getValueAt(index, 4);
                    if (!StringUtil.isEmpty(currentObjectID)) {
                        PatientInfoBean patientInfoBean=controller.findLocalPatient(clinicController.findLocalRegister(currentObjectID).getPatientID());
                        String msg="";
                        if (patientInfoBean!=null){
                            msg=patientInfoBean.getName();
                        }
                        int i = JOptionPane.showConfirmDialog(null, "确认取消当前"+msg+"的挂号？");
                        if (i != 0) {
                            return;
                        }
                        clinicController.delRegister(currentObjectID, new SimpleListener() {
                            @Override
                            public void done(Object data) {
                                renewRegisterList();
                                JOptionPane.showMessageDialog(null, "成功取消", "提示", JOptionPane.INFORMATION_MESSAGE);
                            }

                            @Override
                            public void fail(ErrInfo errInfo) {

                            }

                            @Override
                            public void fail(String s) {

                            }
                        });
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renewRegisterList();

            }
        });
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfFindByName.setText("");
            }
        });
    }

}
