package view.Doctor;

import Utils.ViewUtils;
import com.mkk.swing.JCalendarChooser;
import model.bean.CaseDetail;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Calendar;

public class CaseView {

    public JPanel panelCase;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JButton btnAddTime;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JTextArea textArea4;
    private Calendar calendar;
    private CaseDetail caseDetail;

    public CaseView(CaseDetail caseDetail) {
        this.caseDetail = caseDetail;
        textArea1 = ViewUtils.addTextAera(panel1, 400, 120);
        textArea2 = ViewUtils.addTextAera(panel2, 400, 120);
        textArea3 = ViewUtils.addTextAera(panel3, 400, 120);
        textArea4 = ViewUtils.addTextAera(panel4, 400, 120);

        if (caseDetail != null) {
            try {
                textArea1.setText(caseDetail.getTime());
                textArea2.setText(caseDetail.getMain());
                textArea3.setText(caseDetail.getInspection());
                textArea4.setText(caseDetail.getDiagnosis());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        btnAddTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCalendarChooser calendarChooser = new JCalendarChooser(ViewUtils.currentFrame, "选择日期");
                calendar = calendarChooser.showCalendarDialog();
                textArea1.append(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1)
                        + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日\n");
            }
        });
    }

    public void renewCaseDetail() {
        if (caseDetail == null) {
            caseDetail=new CaseDetail();
        }
        caseDetail.setTime(textArea1.getText());
        caseDetail.setMain(textArea2.getText());
        caseDetail.setInspection(textArea3.getText());
        caseDetail.setDiagnosis(textArea4.getText());
    }
}
