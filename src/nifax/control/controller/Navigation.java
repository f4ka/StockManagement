package nifax.control.controller;

import javax.swing.tree.TreePath;
import nifax.control.util.Table;
import nifax.control.view.FrameMain;
import nifax.control.view.panel.PanelGeneralAdmin;
import nifax.control.view.panel.PanelPresentation;
import nifax.control.view.panel.PanelProductsAdmin;
import nifax.control.view.panel.PanelReportProductStock;
import nifax.control.view.panel.PanelSalesTicket;

/**
 *
 * @author NB
 */
public class Navigation {

    private static Navigation instance = null;

    static FrameMain frameMain = null;

    private static TreePath lastSelected = null;

    protected Navigation() {
    }

    public static Navigation getInstance() {
        if (instance == null) {
            instance = new Navigation();
        }
        return instance;
    }

    public static void setFrameMain(FrameMain frameMain) {
        Navigation.frameMain = frameMain;
    }

    public void showPanel(TreePath tp) {
        lastSelected = tp;
        if (tp != null) {

            PanelGeneralAdmin panelGeneralAdmin = new PanelGeneralAdmin();
            frameMain.getScp_container().setViewportView(panelGeneralAdmin);
            String tab = "";
            String panelName = null;
            switch (tp.toString()) {
                case "[NiFax, Administracion, Productos, Gestion]":
                    PanelProductsAdmin panelProductsAdmin = new PanelProductsAdmin();
                    frameMain.getScp_container().setViewportView(panelProductsAdmin);
                    break;
                case "[NiFax, Administracion, Categorias, Gestion]":
                    tab = "Categoria";
                    panelName = "Category";
                    break;
                case "[NiFax, Administracion, Depositos, Gestion]":
                    tab = "Deposito";
                    panelName = "Store";
                    break;
                case "[NiFax, Administracion, Medidas, Gestion]":
                    tab = "Medida";
                    panelName = "Measure";
                    break;
                case "[NiFax, Administracion, Lista de precios, Gestion]":
                    tab = "Lista de precio";
                    panelName = "Price";
                    panelGeneralAdmin.getLbl_profitGral().setVisible(true);
                    panelGeneralAdmin.getTxf_profitGral().setVisible(true);
                    break;
                case "[NiFax, Operacion, Ventas, Ticket]":
                    PanelSalesTicket panelSalesTicket = new PanelSalesTicket();
                    frameMain.getScp_container().setViewportView(panelSalesTicket);
                    break;
                case "[NiFax, Reportes, Productos, Stock]":
                    PanelReportProductStock panelReportProductStock = new PanelReportProductStock();
                    frameMain.getScp_container().setViewportView(panelReportProductStock);
                    break;
                default:
                    PanelPresentation jPanelPresentation = new PanelPresentation();
                    frameMain.getScp_container().setViewportView(jPanelPresentation);
                    break;
            }

            panelGeneralAdmin.getPnl_loadGral().setName(panelName);
            panelGeneralAdmin.getTbp_gral().setTitleAt(0, tab);

            if (!Administration.Price.equals(panelName)) {
                int[] columnsHides = {3};
                Table.hiddenColumns(panelGeneralAdmin.getTbl_gral(), columnsHides);
            }
        }
    }

    public FrameMain getFrameMain() {
        return frameMain;
    }

    public TreePath getLastSelected() {
        return lastSelected;
    }

}
