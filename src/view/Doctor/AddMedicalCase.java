package view.Doctor;

import Utils.StringUtil;
import Utils.Toast;
import com.google.gson.JsonSyntaxException;
import controller.*;
import model.Model;
import model.bean.CaseDetail;
import model.bean.ClinicRegisterBean;
import model.bean.ErrInfo;
import model.bean.PatientInfoBean;
import view.ItemView;
import Utils.ViewUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddMedicalCase {
    private static JFrame frame;
    private JPanel panelContent1;
    private JPanel panelContent2;
    private JPanel panelContent3;
    private ClinicRegisterBean clinicRegisterBean;
    private PatientInfoBean patientInfoBean;
    private CaseDetail caseDetail=new CaseDetail();

    private JPanel panel1;
    private JPanel panelLeft;
    private JPanel panelmain;
    private JProgressBar progressBar1;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JTabbedPane tabbedPane;
    private DoctorController doctorController;
    private CaseView caseView;
    private PrescriptionView prescriptionView;
    private AdviceView adviceView;

    public static void main(ClinicRegisterBean clinicRegisterBean, PatientInfoBean patientInfoBean) {
        frame = new JFrame("医生工作站---当前工作人员: " + Controller.getUser().getName() + " " + Controller.getUser().getUsername());
        frame.setContentPane(new AddMedicalCase(clinicRegisterBean, patientInfoBean).panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        ViewUtils.toCenter(frame);
    }

    void initView() {
        progressBar1.setVisible(false);
        panel1.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelLeft.setBorder(new EmptyBorder(0, 0, 0, 10));
        panelContent1 = new JPanel(new BorderLayout());
        panelContent2 = new JPanel(new BorderLayout());
        panelContent3 = new JPanel(new BorderLayout());
        panelContent1.setMinimumSize(new Dimension(500,500));
        panelContent2.setMinimumSize(new Dimension(500,500));
        panelContent3.setMinimumSize(new Dimension(500,500));

        tabbedPane = new JTabbedPane();

        ItemView itemView = new ItemView(clinicRegisterBean, "detail");
        panelLeft.add(itemView.panel1);

        caseView = new CaseView(caseDetail);
        prescriptionView = new PrescriptionView(caseDetail);
        adviceView = new AdviceView(caseDetail);

        panelContent1.add(caseView.panelCase);
        panelContent2.add(prescriptionView.panelPrescription);
        panelContent3.add(adviceView.panelAdvice);

        tabbedPane.addTab("病历", panelContent1);
        tabbedPane.addTab("处方", panelContent2);
        tabbedPane.addTab("医嘱", panelContent3);
        panelmain.add(tabbedPane);

        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.callPrinter("hh");
            }
        });
    }

    public AddMedicalCase(ClinicRegisterBean clinicRegisterBean, PatientInfoBean patientInfoBean) {
        this.clinicRegisterBean = clinicRegisterBean;
        this.patientInfoBean = patientInfoBean;
        if (clinicRegisterBean.getHasVisited()==1){
            try {
                String s=clinicRegisterBean.getCaseDetail();
                if (!StringUtil.isEmpty(s)&&!s.equals("null")) {
                    caseDetail= Model.getGson().fromJson(clinicRegisterBean.getCaseDetail(),CaseDetail.class);
                }else {
                    caseDetail=new CaseDetail();
                }
            } catch (Exception e) {
                e.printStackTrace();
                caseDetail=new CaseDetail();
            }
        }
        initView();

        doctorController = new DoctorController(new IControllerListener() {
            @Override
            public void done(Object data) {

            }

            @Override
            public void doneRaw(String data) {

            }

            @Override
            public void showMessage(String msg) {

            }
        }, null);

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renewClinicRegister();
            }
        });
    }

    public void renewClinicRegister(){

        progressBar1.setVisible(true);
        clinicRegisterBean.setHasVisited(1);
        doctorController.putRegister(clinicRegisterBean, new SimpleListener() {
            @Override
            public void done(Object data) {
                progressBar1.setVisible(false);
                new Toast(ViewUtils.currentFrame, "操作成功", 1500, Toast.success).start();

            }

            @Override
            public void fail(ErrInfo errInfo) {
                progressBar1.setVisible(false);
                if (ViewUtils.currentFrame != null) {
                    new Toast(ViewUtils.currentFrame, "操作失败", 1500, Toast.error).start();
                }
            }

            @Override
            public void fail(String s) {
                progressBar1.setVisible(false);
                if (ViewUtils.currentFrame != null) {
                    new Toast(ViewUtils.currentFrame, "操作失败", 1500, Toast.error).start();
                }
            }
        });
    }

    public void save(){
        caseView.renewCaseDetail();
        prescriptionView.renewCaseDetail();
        adviceView.renewCaseDetail();
        clinicRegisterBean.setCaseDetail(Model.getGson().toJson(caseDetail));
        Controller.print(clinicRegisterBean.getCaseDetail());
        renewClinicRegister();

    }
}
