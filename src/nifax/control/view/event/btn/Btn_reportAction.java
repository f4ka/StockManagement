package nifax.control.view.event.btn;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import nifax.control.controller.Reporting;
import nifax.control.util.Message;
import nifax.control.view.util.Tbp;

/**
 *
 * @author NB
 */
public class Btn_reportAction extends AbstractAction {

    private final JPanel panel;
    private final int action;

    public Btn_reportAction(JPanel panel, int action, String btnTitle) {
        super(btnTitle);
        this.action = action;
        this.panel = panel;

        DescAndKey();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        Reporting reporting = Reporting.getInstance();
        String panelName = null;

        for (int i = 0; i < panel.getComponentCount(); i++) {
            panelName = panel.getComponent(i).getName();

            if (panelName != null) {
                if (panelName.equals("Container")) {
                    Tbp tbp = (Tbp) panel.getComponent(i);
                    panelName = tbp.getSelectedComponent().getName();
                    break;
                }
            }
        }
        if (panelName != null) {
            if (!reporting.operate(panel, panelName, action)) {
                JOptionPane.showMessageDialog(null, Message.FailuredOperation, Message.FailuredOperationTitle, JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private void DescAndKey() {
        String text = null;
        switch (action) {
            case Reporting.GENERATE:
                text = Message.ShorDescriptionGenerate;
                putValue(MNEMONIC_KEY, KeyEvent.VK_G);
                break;
        }
        putValue(SHORT_DESCRIPTION, text);

    }
}
