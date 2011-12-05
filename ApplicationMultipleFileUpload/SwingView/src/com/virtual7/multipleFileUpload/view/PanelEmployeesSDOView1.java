package com.virtual7.multipleFileUpload.view;

import com.jgoodies.forms.layout.*;

import java.awt.*;
import java.awt.event.*;

import java.io.File;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.table.*;
import javax.swing.text.*;

import oracle.adf.model.*;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.*;

import oracle.jbo.uicli.binding.*;
import oracle.jbo.uicli.controls.*;
import oracle.jbo.uicli.jui.*;

import oracle.jdeveloper.layout.*;

public class PanelEmployeesSDOView1 extends JPanel implements JUPanel {

    //PageDefinition File	
    private JUPanelBinding panelBinding =
        new JUPanelBinding("com_virtual7_multipleFileUpload_view_PanelEmployeesSDOView1PageDef");
    private JPanel dataPanel = new JPanel();
    private BorderLayout borderLayout = new BorderLayout();

    private FormLayout panelLayout =
        new FormLayout("3dlu,d:g,3dlu,d:g,3dlu", "3dlu,d,3dlu,d,3dlu,d,3dlu,d,3dlu,d,3dlu,d,3dlu,d,3dlu,d,3dlu,d,3dlu,d,3dlu,d,3dlu,d");
    private JTextField mEmployeeId = new JTextField();
    private JLabel labelEmployeeId = new JLabel();
    private JTextField mFirstName = new JTextField();
    private JLabel labelFirstName = new JLabel();
    private JTextField mLastName = new JTextField();
    private JLabel labelLastName = new JLabel();
    private JTextField mEmail = new JTextField();
    private JLabel labelEmail = new JLabel();
    private JTextField mPhoneNumber = new JTextField();
    private JLabel labelPhoneNumber = new JLabel();
    private JTextField mHireDate = new JTextField();
    private JLabel labelHireDate = new JLabel();
    private JTextField mJobId = new JTextField();
    private JLabel labelJobId = new JLabel();
    private JTextField mSalary = new JTextField();
    private JLabel labelSalary = new JLabel();
    private JTextField mCommissionPct = new JTextField();
    private JLabel labelCommissionPct = new JLabel();
    private JTextField mManagerId = new JTextField();
    private JLabel labelManagerId = new JLabel();
    private JTextField mDepartmentId = new JTextField();
    private JLabel labelDepartmentId = new JLabel();
    private JUNavigationBar navBar = new JUNavigationBar();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JButton jButton2 = new JButton();

    /**The default constructor for panel
     */
    public PanelEmployeesSDOView1() {
    }

    /**the JbInit method
     */
    public void jbInit() throws Exception {
        dataPanel.setLayout(panelLayout);
        dataPanel.setMinimumSize(new Dimension(100, 100));
        dataPanel.setSize(new Dimension(400, 400));
        dataPanel.setPreferredSize(new Dimension(400, 400));
        jScrollPane1.setMinimumSize(new Dimension(100, 100));
        jScrollPane1.setEnabled(true);
        jButton2.setText("File Upload");
        jButton2.setAlignmentX((float)0.5);
        jButton2.setSize(new Dimension(168, 20));
        jButton2.setBounds(new Rectangle(20, 264, 168, 20));
        jButton2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFileChooser jfc = new JFileChooser();
                    jfc.setMultiSelectionEnabled(true); // added line
                    int result = jfc.showOpenDialog(PanelEmployeesSDOView1.this);
                    if (result == JFileChooser.CANCEL_OPTION)
                        return;
                    try {
                        ArrayList<String> FileData = new ArrayList<String>();
                        File[] file = jfc.getSelectedFiles();
                    } catch (Exception exc) {
                        JOptionPane.showMessageDialog(PanelEmployeesSDOView1.this, exc.getMessage(), "File error",
                                                      JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        jScrollPane1.setViewportView(dataPanel);
        this.setLayout(borderLayout);
        //Add the controls to the layout
        this.setSize(new Dimension(400, 400));
        dataPanel.add(labelEmployeeId, new CellConstraints("2 , 2 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(mEmployeeId, new CellConstraints("4, 2 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(labelFirstName, new CellConstraints("2 , 4 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(mFirstName, new CellConstraints("4, 4 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(labelLastName, new CellConstraints("2 , 6 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(mLastName, new CellConstraints("4, 6 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(labelEmail, new CellConstraints("2 , 8 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(mEmail, new CellConstraints("4, 8 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(labelPhoneNumber, new CellConstraints("2 , 10 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(mPhoneNumber, new CellConstraints("4, 10 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(labelHireDate, new CellConstraints("2 , 12 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(mHireDate, new CellConstraints("4, 12 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(labelJobId, new CellConstraints("2 , 14 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(mJobId, new CellConstraints("4, 14 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(labelSalary, new CellConstraints("2 , 16 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(mSalary, new CellConstraints("4, 16 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(labelCommissionPct, new CellConstraints("2 , 18 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(mCommissionPct, new CellConstraints("4, 18 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(labelManagerId, new CellConstraints("2 , 20 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(mManagerId, new CellConstraints("4, 20 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(labelDepartmentId, new CellConstraints("2 , 22 ,1,1,default," + CellConstraints.FILL));
        dataPanel.add(mDepartmentId, new CellConstraints("4, 22 ,1,1,default," + CellConstraints.FILL));

        dataPanel.add(jButton2, new CellConstraints(2, 24, 1, 1, CellConstraints.DEFAULT, CellConstraints.DEFAULT));
        labelEmployeeId.setLabelFor(mEmployeeId);
        labelEmployeeId.setText("<html><B>" + panelBinding.findCtrlValueBinding("EmployeeId").getLabel() +
                                "</B></html>");
        mEmployeeId.setToolTipText(panelBinding.findCtrlValueBinding("EmployeeId").getTooltip());
        mEmployeeId.setColumns(22);
        mEmployeeId.setColumns(panelBinding.findCtrlValueBinding("EmployeeId").getDisplayWidth());

        labelFirstName.setLabelFor(mFirstName);
        labelFirstName.setText(panelBinding.findCtrlValueBinding("FirstName").getLabel());
        mFirstName.setToolTipText(panelBinding.findCtrlValueBinding("FirstName").getTooltip());
        mFirstName.setColumns(20);
        mFirstName.setColumns(panelBinding.findCtrlValueBinding("FirstName").getDisplayWidth());

        labelLastName.setLabelFor(mLastName);
        labelLastName.setText("<html><B>" + panelBinding.findCtrlValueBinding("LastName").getLabel() + "</B></html>");
        mLastName.setToolTipText(panelBinding.findCtrlValueBinding("LastName").getTooltip());
        mLastName.setColumns(25);
        mLastName.setColumns(panelBinding.findCtrlValueBinding("LastName").getDisplayWidth());

        labelEmail.setLabelFor(mEmail);
        labelEmail.setText("<html><B>" + panelBinding.findCtrlValueBinding("Email").getLabel() + "</B></html>");
        mEmail.setToolTipText(panelBinding.findCtrlValueBinding("Email").getTooltip());
        mEmail.setColumns(25);
        mEmail.setColumns(panelBinding.findCtrlValueBinding("Email").getDisplayWidth());

        labelPhoneNumber.setLabelFor(mPhoneNumber);
        labelPhoneNumber.setText(panelBinding.findCtrlValueBinding("PhoneNumber").getLabel());
        mPhoneNumber.setToolTipText(panelBinding.findCtrlValueBinding("PhoneNumber").getTooltip());
        mPhoneNumber.setColumns(20);
        mPhoneNumber.setColumns(panelBinding.findCtrlValueBinding("PhoneNumber").getDisplayWidth());

        labelHireDate.setLabelFor(mHireDate);
        labelHireDate.setText("<html><B>" + panelBinding.findCtrlValueBinding("HireDate").getLabel() + "</B></html>");
        mHireDate.setToolTipText(panelBinding.findCtrlValueBinding("HireDate").getTooltip());
        mHireDate.setColumns(7);
        mHireDate.setColumns(panelBinding.findCtrlValueBinding("HireDate").getDisplayWidth());

        labelJobId.setLabelFor(mJobId);
        labelJobId.setText("<html><B>" + panelBinding.findCtrlValueBinding("JobId").getLabel() + "</B></html>");
        mJobId.setToolTipText(panelBinding.findCtrlValueBinding("JobId").getTooltip());
        mJobId.setColumns(10);
        mJobId.setColumns(panelBinding.findCtrlValueBinding("JobId").getDisplayWidth());

        labelSalary.setLabelFor(mSalary);
        labelSalary.setText(panelBinding.findCtrlValueBinding("Salary").getLabel());
        mSalary.setToolTipText(panelBinding.findCtrlValueBinding("Salary").getTooltip());
        mSalary.setColumns(22);
        mSalary.setColumns(panelBinding.findCtrlValueBinding("Salary").getDisplayWidth());

        labelCommissionPct.setLabelFor(mCommissionPct);
        labelCommissionPct.setText(panelBinding.findCtrlValueBinding("CommissionPct").getLabel());
        mCommissionPct.setToolTipText(panelBinding.findCtrlValueBinding("CommissionPct").getTooltip());
        mCommissionPct.setColumns(22);
        mCommissionPct.setColumns(panelBinding.findCtrlValueBinding("CommissionPct").getDisplayWidth());

        labelManagerId.setLabelFor(mManagerId);
        labelManagerId.setText(panelBinding.findCtrlValueBinding("ManagerId").getLabel());
        mManagerId.setToolTipText(panelBinding.findCtrlValueBinding("ManagerId").getTooltip());
        mManagerId.setColumns(22);
        mManagerId.setColumns(panelBinding.findCtrlValueBinding("ManagerId").getDisplayWidth());

        labelDepartmentId.setLabelFor(mDepartmentId);
        labelDepartmentId.setText(panelBinding.findCtrlValueBinding("DepartmentId").getLabel());
        mDepartmentId.setToolTipText(panelBinding.findCtrlValueBinding("DepartmentId").getTooltip());
        mDepartmentId.setColumns(22);
        mDepartmentId.setColumns(panelBinding.findCtrlValueBinding("DepartmentId").getDisplayWidth());
        navBar.setModel(JUNavigationBar.createViewBinding(panelBinding, navBar, "EmployeesSDOView1", null,
                                                          "EmployeesSDOView1Iter"));
        add(navBar, BorderLayout.NORTH);
        add(jScrollPane1, BorderLayout.CENTER);
        //BindingCodes
        mEmployeeId.setDocument((Document)panelBinding.bindUIControl("EmployeeId", mEmployeeId));
        mFirstName.setDocument((Document)panelBinding.bindUIControl("FirstName", mFirstName));
        mLastName.setDocument((Document)panelBinding.bindUIControl("LastName", mLastName));
        mEmail.setDocument((Document)panelBinding.bindUIControl("Email", mEmail));
        mPhoneNumber.setDocument((Document)panelBinding.bindUIControl("PhoneNumber", mPhoneNumber));
        mHireDate.setDocument((Document)panelBinding.bindUIControl("HireDate", mHireDate));
        mJobId.setDocument((Document)panelBinding.bindUIControl("JobId", mJobId));
        mSalary.setDocument((Document)panelBinding.bindUIControl("Salary", mSalary));
        mCommissionPct.setDocument((Document)panelBinding.bindUIControl("CommissionPct", mCommissionPct));
        mManagerId.setDocument((Document)panelBinding.bindUIControl("ManagerId", mManagerId));
        mDepartmentId.setDocument((Document)panelBinding.bindUIControl("DepartmentId", mDepartmentId));

        if ("Hide".equalsIgnoreCase(panelBinding.getDisplayHint("EmployeesSDOView1", "EmployeeId", null))) {
            dataPanel.remove(mEmployeeId);
            dataPanel.remove(labelEmployeeId);
        }
        if ("Hide".equalsIgnoreCase(panelBinding.getDisplayHint("EmployeesSDOView1", "FirstName", null))) {
            dataPanel.remove(mFirstName);
            dataPanel.remove(labelFirstName);
        }
        if ("Hide".equalsIgnoreCase(panelBinding.getDisplayHint("EmployeesSDOView1", "LastName", null))) {
            dataPanel.remove(mLastName);
            dataPanel.remove(labelLastName);
        }
        if ("Hide".equalsIgnoreCase(panelBinding.getDisplayHint("EmployeesSDOView1", "Email", null))) {
            dataPanel.remove(mEmail);
            dataPanel.remove(labelEmail);
        }
        if ("Hide".equalsIgnoreCase(panelBinding.getDisplayHint("EmployeesSDOView1", "PhoneNumber", null))) {
            dataPanel.remove(mPhoneNumber);
            dataPanel.remove(labelPhoneNumber);
        }
        if ("Hide".equalsIgnoreCase(panelBinding.getDisplayHint("EmployeesSDOView1", "HireDate", null))) {
            dataPanel.remove(mHireDate);
            dataPanel.remove(labelHireDate);
        }
        if ("Hide".equalsIgnoreCase(panelBinding.getDisplayHint("EmployeesSDOView1", "JobId", null))) {
            dataPanel.remove(mJobId);
            dataPanel.remove(labelJobId);
        }
        if ("Hide".equalsIgnoreCase(panelBinding.getDisplayHint("EmployeesSDOView1", "Salary", null))) {
            dataPanel.remove(mSalary);
            dataPanel.remove(labelSalary);
        }
        if ("Hide".equalsIgnoreCase(panelBinding.getDisplayHint("EmployeesSDOView1", "CommissionPct", null))) {
            dataPanel.remove(mCommissionPct);
            dataPanel.remove(labelCommissionPct);
        }
        if ("Hide".equalsIgnoreCase(panelBinding.getDisplayHint("EmployeesSDOView1", "ManagerId", null))) {
            dataPanel.remove(mManagerId);
            dataPanel.remove(labelManagerId);
        }
        if ("Hide".equalsIgnoreCase(panelBinding.getDisplayHint("EmployeesSDOView1", "DepartmentId", null))) {
            dataPanel.remove(mDepartmentId);
            dataPanel.remove(labelDepartmentId);
        }

    }


    /**JUPanel implementation
     */
    public JUPanelBinding getPanelBinding() {
        return panelBinding;
    }

    public void bindNestedContainer(JUPanelBinding ctr) {
        if (panelBinding.getPanel() == null) {
            ctr.setPanel(this);
            panelBinding.release(DCDataControl.REL_VIEW_REFS);
            panelBinding = ctr;
            registerProjectGlobalVariables(panelBinding.getBindingContext());
            try {
                jbInit();
            } catch (Exception ex) {
                ex.printStackTrace();
                ctr.reportException(ex);
            }
        }
    }

    private void registerProjectGlobalVariables(BindingContext bindCtx) {
        JUUtil.registerNavigationBarInterface(panelBinding, bindCtx);
    }

    private void unRegisterProjectGlobalVariables(BindingContext bindCtx) {
        JUUtil.unRegisterNavigationBarInterface(panelBinding, bindCtx);
    }

    public void setBindingContext(BindingContext bindCtx) {
        if (panelBinding.getPanel() == null) {
            panelBinding = panelBinding.setup(bindCtx, this);
            registerProjectGlobalVariables(bindCtx);
            panelBinding.refreshControl();
            try {
                jbInit();
                panelBinding.refreshControl();
            } catch (Exception ex) {
                panelBinding.reportException(ex);
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exemp) {
            exemp.printStackTrace();
        }

        PanelEmployeesSDOView1 panel = new PanelEmployeesSDOView1();
        panel.setBindingContext(JUTestFrame.startTestFrame("com.virtual7.multipleFileUpload.view.DataBindings.cpx",
                                                           "null", panel, panel.getPanelBinding(),
                                                           new Dimension(400, 400)));
        panel.revalidate();
    }
}
