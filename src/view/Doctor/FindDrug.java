package view.Doctor;

import Utils.StringUtil;
import Utils.Toast;
import com.google.gson.reflect.TypeToken;
import controller.Controller;
import controller.DoctorController;
import controller.IControllerListener;
import controller.SimpleListener;
import model.Model;
import model.bean.DrugBean;
import model.bean.ErrInfo;
import model.bean.PatientInfoBean;
import model.bean.ResultBean;
import Utils.ViewUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 查找界面
 */

public class FindDrug {
    private static JFrame frame;
    private static String title;
    private JPanel panel1;
    private JButton btnOK;
    private LinkedHashMap<String, String> data = new LinkedHashMap<>();
    private List<DrugBean> dataInfo;
    private SimpleListener simpleListener;
    private Controller controller;
    private TableRowSorter<TableModel> sorter;

    private JTable table;
    private int index;
    private String currentObjectID;
    private Thread thread;


    public void initView() {
        lb.setText(title);
        progressBar1.setIndeterminate(true);
        progressBar1.setVisible(false);
        panel1.setBorder(new EmptyBorder(10, 10, 10, 10));
        ViewUtils.changeFont(panel1);
        table = new JTable();
        panelmain.add(new JScrollPane(table), BorderLayout.CENTER);
        initContrller();
        dorenewDrugData();
    }


    public void initContrller() {
        controller = new Controller(new IControllerListener<ResultBean<DrugBean>>() {
            @Override
            public void done(ResultBean<DrugBean> data) {
                dataInfo = data.getResults();
                initTableModelInfo();
                progressBar1.setVisible(false);
                table.setRowSelectionInterval(0, 0);
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

    public Runnable findDrugData = new Runnable() {
        @Override
        public void run() {
            dataInfo = DoctorController.getDrugDatabase();
            if (dataInfo == null || dataInfo.size() == 0) {
                SwingUtilities.invokeLater(() -> {
                    new Toast(ViewUtils.currentFrame, "未找到药品数据库，正在开始下载", 1500, Toast.error).start();
                    progressBar1.setVisible(true);
                });
                controller.getDrugDatabase(new SimpleListener() {
                    @Override
                    public void done(Object data) {
                        dataInfo = DoctorController.getDrugDatabase();

                        SwingUtilities.invokeLater(() -> {
                            initTableModelInfo();
                        });
                    }

                    @Override
                    public void fail(ErrInfo errInfo) {
                        progressBar1.setVisible(false);
                        SwingUtilities.invokeLater(() -> {
                            new Toast(ViewUtils.currentFrame, errInfo.getError(), 1500, Toast.error).start();
                        });

                    }

                    @Override
                    public void fail(String s) {
                        progressBar1.setVisible(false);
                        Model.log(s);
                        SwingUtilities.invokeLater(() -> {
                            new Toast(ViewUtils.currentFrame, s + "", 1500, Toast.error).start();
                        });

                    }
                });
            } else
                SwingUtilities.invokeLater(() -> {
                    initTableModelInfo();
                });
        }
    };

    public void dorenewDrugData() {
        if (thread != null && thread.isAlive()) {
            return;
        }
        progressBar1.setVisible(true);
        thread = new Thread(findDrugData);
        thread.start();
    }

    public void initTableModelInfo() {
        lbcount.setText(dataInfo.size() + "条数据");
        String[] columnNames = {"药品名", "剂量", "规格", "批准文号", "生产企业", "仓库编号", "价格"};
        Object[][] obj = new Object[dataInfo.size()][columnNames.length];
        for (int i = 0; i < dataInfo.size(); i++) {
            DrugBean drugBean = dataInfo.get(i);
            for (int j = 0; j < columnNames.length; j++) {
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
                    case 5:
                        obj[i][j] = drugBean.getObjectId();
                        break;
                    case 6:
                        obj[i][j] = drugBean.getPrice();
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
        sorter = new TableRowSorter<TableModel>(tableModel);
        Comparator<Double> numberComparator = new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                if (o1 == null) {
                    return -1;
                }
                if (o2 == null) {
                    return 1;
                }
                if (o1 < o2) {
                    return -1;
                }
                if (o1 > o2) {
                    return 1;
                }
                return 0;
            }
        };
        sorter.setComparator(6, numberComparator);
        table.setRowSorter(sorter);

        progressBar1.setVisible(false);
    }

    public void renewFindFilter() {
        if (!StringUtil.isEmpty(tfName.getText())) {
            if (sorter != null) {
                sorter.setRowFilter(RowFilter.regexFilter(tfName.getText()));
            }
        } else {
            if (sorter != null) {
                sorter.setRowFilter(null);
            }
        }
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

    public FindDrug(LinkedHashMap<String, String> data, SimpleListener simpleListener) {
        this.simpleListener = simpleListener;
        this.data = data;
        initView();


        tfName.getDocument().addDocumentListener(new DocumentListener() {
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

        tfName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    renewFindFilter();
                }
            }
        });


        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    done();
                }
            }
        });

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                done();
            }
        });
    }

    void done() {
        index = table.getSelectedRow();
        if (index == -1) {
            return;
        }
        currentObjectID = (String) table.getValueAt(index, 5);
        simpleListener.done(controller.findLocalDrug(currentObjectID));
        frame.dispose();
    }

    public static FindDrug main(LinkedHashMap<String, String> data, SimpleListener simpleListener) {
        title = "查找药品";
        if (frame != null) {
            frame.dispose();
        }
        frame = new JFrame(title);
        findDrug = new FindDrug(data, simpleListener);
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
    private JLabel lbcount;
}
