package view.Doctor;

import Utils.StringUtil;
import Utils.ViewUtils;
import model.bean.CaseDetail;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class AdviceView {
    public JPanel panelAdvice;
    private JPanel panel1;
    private JTextArea textArea;
    private CaseDetail caseDetail;

    public AdviceView(CaseDetail caseDetail) {
        this.caseDetail=caseDetail;

        panelAdvice.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
            }
        });
        textArea= ViewUtils.addTextAera(panel1);
        ViewUtils.changeFont(textArea,16);

        if (!StringUtil.isEmpty(caseDetail.getAdvice())){
            textArea.setText(caseDetail.getAdvice());
        }
    }

    public void renewCaseDetail() {
        if (caseDetail == null) {
            caseDetail=new CaseDetail();
        }
        caseDetail.setAdvice(textArea.getText());
    }
}
