package com.virtual7.multipleFileUpload.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import oracle.adf.model.BindingContext;
import oracle.adf.model.DataControlFactory;
import oracle.adf.model.binding.DCDataControl;

import oracle.jbo.common.DefLocaleContext;
import oracle.jbo.uicli.binding.JUUtil;
import oracle.jbo.uicli.controls.JUErrorHandlerDlg;
import oracle.jbo.uicli.controls.JUNavigationBar;
import oracle.jbo.uicli.controls.JUStatusBar;
import oracle.jbo.uicli.jui.JUEnvInfoProvider;
import oracle.jbo.uicli.jui.JUPanelBinding;
import oracle.jbo.uicli.mom.JUMetaObjectManager;


public class AppletEmployeesSDOView1 extends JApplet {

    //PageDefinition File	
    private JUPanelBinding panelBinding =
        new JUPanelBinding("com_virtual7_multipleFileUpload_view_PanelEmployeesSDOView1PageDef");
    private GridLayout gridLayout = new GridLayout();
    private BorderLayout borderLayout = new BorderLayout();
    private BindingContext ctx = new BindingContext();

    private JUStatusBar statusBar = new JUStatusBar();
    private JPanel topPanel = new JPanel();

    private PanelEmployeesSDOView1 dataPanel = new PanelEmployeesSDOView1();

    private JMenuBar menubarFrame = new JMenuBar();
    JUNavigationBar hiddenNavBar = new JUNavigationBar() {
        protected void _updateButtonStates() {
            super._updateButtonStates();
            menuItemsUpdate();
        }
    };

    private JMenu menuFile = new JMenu();
    private JMenuItem itemFileExit = new JMenuItem();
    private JMenu menuDatabase = new JMenu();
    private JMenuItem itemDatabaseFirst = new JMenuItem();
    private JMenuItem itemDatabasePrevious = new JMenuItem();
    private JMenuItem itemDatabaseNext = new JMenuItem();
    private JMenuItem itemDatabaseLast = new JMenuItem();
    private JMenuItem itemDatabaseInsert = new JMenuItem();
    private JMenuItem itemDatabaseDelete = new JMenuItem();
    private JMenuItem itemDatabaseCommit = new JMenuItem();
    private JMenuItem itemDatabaseRollback = new JMenuItem();
    private JMenuItem itemFindMode = new JMenuItem();
    private JMenuItem itemExecute = new JMenuItem();
    private JMenu menuHelp = new JMenu();
    private JMenuItem itemHelpAbout = new JMenuItem();
    private String aboutMessage = "about message";
    private String aboutTitle = "about title ";

    private boolean runningInBrowser = true;

    public AppletEmployeesSDOView1(boolean runningInBrowser) {
        this.runningInBrowser = runningInBrowser;
    }

    /**The default constructor for form
     */

    public AppletEmployeesSDOView1() {
    }


    /**the JbInit method
     */
    public void jbInit() throws Exception {
        BindingContext _bctx = panelBinding.getBindingContext();
        ArrayList varList = new ArrayList(1);
        varList.add(hiddenNavBar);
        _bctx.put(JUUtil.PROJECT_GLOBAL_VARIABLES, varList);
        dataPanel.setBindingContext(_bctx);
        this.getContentPane().setLayout(gridLayout);
        topPanel.setLayout(borderLayout);
        this.getContentPane().add(topPanel);
        JScrollPane scPane = new JScrollPane(dataPanel);
        topPanel.add(scPane, BorderLayout.CENTER);
        topPanel.add(statusBar, BorderLayout.SOUTH);


        setJMenuBar(menubarFrame);
        this.setSize(new Dimension(400, 424));
        itemFileExit.setText("Exit");
        itemFileExit.setMnemonic('X');
        itemFileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, Event.ALT_MASK, false));
        itemFileExit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    file_exit_action(e);
                }
            });
        menuFile.add(itemFileExit);
        menuFile.setText("File");
        menuFile.setMnemonic('F');
        menubarFrame.add(menuFile);
        menuDatabase.setText("Database");
        menuDatabase.setMnemonic('D');
        itemDatabaseFirst.setText("First");
        itemDatabaseFirst.setMnemonic('F');
        itemDatabaseFirst.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_HOME, Event.ALT_MASK, false));
        itemDatabaseFirst.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    first_action(e);
                }
            });
        menuDatabase.add(itemDatabaseFirst);
        itemDatabasePrevious.setText("Previous");
        itemDatabasePrevious.setMnemonic('P');
        itemDatabasePrevious.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, Event.ALT_MASK, false));
        itemDatabasePrevious.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    previous_action(e);
                }
            });
        menuDatabase.add(itemDatabasePrevious);
        itemDatabaseNext.setText("Next");
        itemDatabaseNext.setMnemonic('N');
        itemDatabaseNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, Event.ALT_MASK, false));

        itemDatabaseNext.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    next_action(e);
                }
            });

        menuDatabase.add(itemDatabaseNext);
        itemDatabaseLast.setText("Last");
        itemDatabaseLast.setMnemonic('L');
        itemDatabaseLast.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_END, Event.ALT_MASK, false));
        itemDatabaseLast.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    last_action(e);
                }
            });
        menuDatabase.add(itemDatabaseLast);
        menuDatabase.addSeparator();
        itemDatabaseInsert.setText("Insert");
        itemDatabaseInsert.setMnemonic('I');
        itemDatabaseInsert.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, Event.ALT_MASK, false));

        itemDatabaseInsert.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    insert_action(e);
                }
            });

        menuDatabase.add(itemDatabaseInsert);
        itemDatabaseDelete.setText("Delete");
        itemDatabaseDelete.setMnemonic('D');
        itemDatabaseDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, Event.ALT_MASK, false));

        itemDatabaseDelete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    delete_action(e);
                }
            });
        menuDatabase.add(itemDatabaseDelete);
        menuDatabase.addSeparator();
        itemDatabaseCommit.setText("Commit");
        itemDatabaseCommit.setMnemonic('C');
        itemDatabaseCommit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.ALT_MASK, false));

        itemDatabaseCommit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    commit_action(e);
                }
            });
        menuDatabase.add(itemDatabaseCommit);
        itemDatabaseRollback.setText("Rollback");
        itemDatabaseRollback.setMnemonic('R');
        itemDatabaseRollback.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.ALT_MASK, false));

        itemDatabaseRollback.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    rollback_action(e);
                }
            });
        menuDatabase.add(itemDatabaseRollback);
        menuDatabase.addSeparator();
        itemFindMode.setText("Query Mode");
        itemFindMode.setMnemonic('Q');
        itemFindMode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.ALT_MASK, false));

        itemFindMode.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    find_action(e);
                }
            });
        menuDatabase.add(itemFindMode);
        itemExecute.setText("Execute Query");
        itemExecute.setMnemonic('E');
        itemExecute.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.ALT_MASK, false));

        itemExecute.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    execute_action(e);
                }
            });
        menuDatabase.add(itemExecute);
        menubarFrame.add(menuDatabase);
        menuHelp.setText("Help");
        menuHelp.setMnemonic('H');
        itemHelpAbout.setText("About");
        itemHelpAbout.setMnemonic('A');

        itemHelpAbout.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    help_about_action(e);
                }
            });
        menuHelp.add(itemHelpAbout);
        menubarFrame.add(menuHelp);

        hiddenNavBar.setModel(JUNavigationBar.createPanelBinding(panelBinding, hiddenNavBar));
        statusBar.setModel(JUStatusBar.createPanelBinding(panelBinding, statusBar));
    }


    private void first_action(ActionEvent e) {
        hiddenNavBar.doAction(JUNavigationBar.BUTTON_FIRST);
    }

    private void previous_action(ActionEvent e) {
        hiddenNavBar.doAction(JUNavigationBar.BUTTON_PREV);
    }

    private void next_action(ActionEvent e) {
        hiddenNavBar.doAction(JUNavigationBar.BUTTON_NEXT);
    }

    private void last_action(ActionEvent e) {
        hiddenNavBar.doAction(JUNavigationBar.BUTTON_LAST);
    }

    private void insert_action(ActionEvent e) {
        hiddenNavBar.doAction(JUNavigationBar.BUTTON_INSERT);
    }

    private void delete_action(ActionEvent e) {
        hiddenNavBar.doAction(JUNavigationBar.BUTTON_DELETE);
    }

    private void commit_action(ActionEvent e) {
        hiddenNavBar.doAction(JUNavigationBar.BUTTON_COMMIT);
    }

    private void rollback_action(ActionEvent e) {
        hiddenNavBar.doAction(JUNavigationBar.BUTTON_ROLLBACK);
    }

    private void find_action(ActionEvent e) {
        hiddenNavBar.doAction(JUNavigationBar.BUTTON_FIND);
    }

    private void execute_action(ActionEvent e) {
        hiddenNavBar.doAction(JUNavigationBar.BUTTON_EXECUTE);
    }

    private void file_exit_action(ActionEvent e) {
        int action = _popupTransactionDialog();
        if (action != JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }
    }

    private void help_about_action(ActionEvent e) {
        JOptionPane.showMessageDialog(this, aboutMessage, aboutTitle, JOptionPane.INFORMATION_MESSAGE);
    }

    private void menuItemsUpdate() {
        itemDatabaseFirst.setEnabled(hiddenNavBar.isButtonActive(JUNavigationBar.BUTTON_FIRST));
        itemDatabasePrevious.setEnabled(hiddenNavBar.isButtonActive(JUNavigationBar.BUTTON_PREV));
        itemDatabaseLast.setEnabled(hiddenNavBar.isButtonActive(JUNavigationBar.BUTTON_LAST));
        itemDatabaseNext.setEnabled(hiddenNavBar.isButtonActive(JUNavigationBar.BUTTON_NEXT));
        itemDatabaseInsert.setEnabled(hiddenNavBar.isButtonActive(JUNavigationBar.BUTTON_INSERT));
        itemDatabaseDelete.setEnabled(hiddenNavBar.isButtonActive(JUNavigationBar.BUTTON_DELETE));
        itemDatabaseCommit.setEnabled(hiddenNavBar.isButtonActive(JUNavigationBar.BUTTON_COMMIT));
        itemDatabaseRollback.setEnabled(hiddenNavBar.isButtonActive(JUNavigationBar.BUTTON_ROLLBACK));
        itemFindMode.setEnabled(hiddenNavBar.isButtonActive(JUNavigationBar.BUTTON_FIND));
        itemExecute.setEnabled(hiddenNavBar.isButtonActive(JUNavigationBar.BUTTON_EXECUTE));
    }


    private int _popupTransactionDialog() {
        if (panelBinding == null || panelBinding.getPanel() == null) {
            return JOptionPane.NO_OPTION;
        }
        if (panelBinding.isTransactionDirty()) {
            JButton commitButton = new JButton("Commit");
            commitButton.setMnemonic('C');
            JButton rollBackButton = new JButton("Rollback");
            rollBackButton.setMnemonic('R');
            Object[] options = { commitButton, rollBackButton };
            final JOptionPane optionPane =
                new JOptionPane("How do you want to close the transaction?", JOptionPane.QUESTION_MESSAGE,
                                JOptionPane.YES_NO_OPTION, null, options, options[0]);
            commitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        optionPane.setValue(new Integer(JOptionPane.YES_OPTION));
                    }
                });
            rollBackButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        optionPane.setValue(new Integer(JOptionPane.NO_OPTION));
                    }
                });

            optionPane.createDialog(AppletEmployeesSDOView1.this, "Transaction open").setVisible(true);

            int action = JOptionPane.CLOSED_OPTION;
            Object obj = optionPane.getValue();
            if (obj != null) {
                action = ((Integer)optionPane.getValue()).intValue();
            }
            switch (action) {
            case JOptionPane.NO_OPTION:
                hiddenNavBar.doAction(JUNavigationBar.BUTTON_ROLLBACK);
                break;
            case JOptionPane.CLOSED_OPTION:
                break;
            case JOptionPane.YES_OPTION:
            default:
                hiddenNavBar.doAction(JUNavigationBar.BUTTON_COMMIT);
                break;
            }
            return action;
        }
        return JOptionPane.NO_OPTION;
    }

    public JUPanelBinding getPanelBinding() {
        return panelBinding;
    }

    public void setPanelBinding(JUPanelBinding binding) {
        if (binding.getPanel() == null) {
            binding.setPanel(topPanel);
        }
        if (panelBinding == null || panelBinding.getPanel() == null) {
            try {
                panelBinding = binding;
                jbInit();
            } catch (Exception ex) {
                panelBinding.reportException(ex);
            }
        }
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

    public void init() {
        try {
            JUMetaObjectManager.setErrorHandler(new JUErrorHandlerDlg());
            JUMetaObjectManager mgr = JUMetaObjectManager.getJUMom();
            mgr.setJClientDefFactory(null);
            ctx = new BindingContext();
            ctx.put(DataControlFactory.APP_PARAM_ENV_INFO, new JUEnvInfoProvider());
            ctx.setLocaleContext(new DefLocaleContext(null));
            HashMap map = new HashMap(4);
            map.put(DataControlFactory.APP_PARAMS_BINDING_CONTEXT, ctx);
            mgr.loadCpx("com.virtual7.multipleFileUpload.view.DataBindings.cpx", map);
            DCDataControl app = (DCDataControl)ctx.get("AppModuleSDODataControl");
            app.setClientApp(DCDataControl.JCLIENT);
            app.getApplicationModule().fetchAttributeProperties(new String[] { "EmployeesSDOView1" },
                                                                new String[][] { { "EmployeeId", "FirstName",
                                                                                   "LastName", "Email", "PhoneNumber",
                                                                                   "HireDate", "JobId", "Salary",
                                                                                   "CommissionPct", "ManagerId",
                                                                                   "DepartmentId" } }, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            JUMetaObjectManager.reportException(null, ex);
        }
    }

    public synchronized void start() {
        try {
            setBindingContext(ctx);
            panelBinding.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            JUMetaObjectManager.reportException(null, ex);
        }
    }

    public void stop() {
        _popupTransactionDialog();
        panelBinding.getApplication().release();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exemp) {
            exemp.printStackTrace();
        }

        try {
            JFrame frame = new JFrame();
            frame.setSize(400, 400);
            final AppletEmployeesSDOView1 applet = new AppletEmployeesSDOView1(false);
            frame.getContentPane().add(applet);
            applet.init();
            applet.start();
            frame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        int action = applet._popupTransactionDialog();
                        if (action != JOptionPane.CLOSED_OPTION) {
                            System.exit(0);
                        }
                    }
                });

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = frame.getSize();
            if (frameSize.height > screenSize.height) {
                frameSize.height = screenSize.height;
            }
            if (frameSize.width > screenSize.width) {
                frameSize.width = screenSize.width;
            }
            frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
            frame.setVisible(true);
        } catch (Exception ex) {
            JUMetaObjectManager.reportException(null, ex, true);
            System.exit(1);
        }
    }
}
