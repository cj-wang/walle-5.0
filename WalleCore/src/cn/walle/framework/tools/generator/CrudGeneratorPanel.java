package cn.walle.framework.tools.generator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.walle.framework.core.support.SystemConfig;
import cn.walle.framework.core.util.ClassUtils;
import cn.walle.framework.tools.util.ConnectionUtils;
import cn.walle.framework.tools.util.UIUtils;

public class CrudGeneratorPanel extends cn.walle.framework.tools.base.BasePanel {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private JPanel jPanelTables;
	private JCheckBox jCheckBoxModel;
	private JPanel jPanelGenerate;
	private JTable jTableTables;
	private JButton jButtonFind;
	private JTextField jTextFieldFind;
	private JLabel jLabelFind;
	private JPanel jPanelFind;
	private JButton jButtonGenerate;
	private JScrollPane jScrollPaneTables;
	private JCheckBox jCheckBoxFieldDef;
	private JCheckBox jCheckBoxManager;
	private JPanel jPanelButtons;
	private JPanel jPanelMain;
	private JLabel jLabelTypes;
	private JComboBox jComboBoxPackageName;
	private JLabel jLabelPackageName;
	private JCheckBox jCheckBoxJsp;
	private JPanel jPanelControl;
	private JPanel jPanelPackageName;
	private JPanel jPanelProjectName;
	private JTextField jTextFieldProjectName;
	private JLabel jLabelProjectName;
	
	private DefaultTableModel tableModel = new DefaultTableModel(
			new String[][] { { "loading..." } },
			new String[] {"Table Name", "Table Type", "Remarks"}) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
	};
	
	private CrudGenerator crudGenerator;
	
	public CrudGeneratorPanel() {
		initGUI();
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(640, 480));
			this.setTitle("CRUD Generator");
			{
				jPanelMain = new JPanel();
				BorderLayout jPanelMainLayout = new BorderLayout();
				this.add(jPanelMain, BorderLayout.CENTER);
				jPanelMain.setLayout(jPanelMainLayout);
				{
					jPanelTables = new JPanel();
					jPanelMain.add(jPanelTables, BorderLayout.CENTER);
					BorderLayout jPanelTablesLayout = new BorderLayout();
					jPanelTables.setLayout(jPanelTablesLayout);
					jPanelTables.setBorder(BorderFactory
						.createTitledBorder("Tables"));
					{
						jScrollPaneTables = new JScrollPane();
						jPanelTables.add(jScrollPaneTables);
						jScrollPaneTables.setEnabled(false);
						{
							jTableTables = new JTable();
							jScrollPaneTables.setViewportView(jTableTables);
							jTableTables.setModel(tableModel);
						}
					}
					{
						jPanelFind = new JPanel();
						jPanelTables.add(jPanelFind, BorderLayout.NORTH);
						FlowLayout jPanelFindLayout = new FlowLayout();
						jPanelFindLayout.setAlignment(FlowLayout.LEFT);
						jPanelFind.setLayout(jPanelFindLayout);
						{
							jLabelFind = new JLabel();
							jPanelFind.add(jLabelFind);
							jLabelFind.setText("Find:");
						}
						{
							jTextFieldFind = new JTextField();
							jPanelFind.add(jTextFieldFind);
							jTextFieldFind
								.setPreferredSize(new java.awt.Dimension(
									300,
									20));
							jTextFieldFind
								.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent evt) {
										jTextFieldFindActionPerformed(evt);
									}
								});
						}
						{
							jButtonFind = new JButton();
							jPanelFind.add(jButtonFind);
							jButtonFind.setText(">>");
							jButtonFind.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									jButtonFindActionPerformed(evt);
								}
							});
						}
					}
				}
				{
					jPanelGenerate = new JPanel();
					jPanelMain.add(jPanelGenerate, BorderLayout.SOUTH);
					BoxLayout jPanelGenerateLayout = new BoxLayout(
						jPanelGenerate,
						javax.swing.BoxLayout.Y_AXIS);
					jPanelGenerate.setLayout(jPanelGenerateLayout);
					{
						jPanelProjectName = new JPanel();
						FlowLayout jPanelProjectNameLayout = new FlowLayout();
						jPanelProjectNameLayout.setAlignment(FlowLayout.LEFT);
						jPanelGenerate.add(jPanelProjectName);
						jPanelProjectName.setLayout(jPanelProjectNameLayout);
						{
							jLabelProjectName = new JLabel();
							jPanelProjectName.add(jLabelProjectName);
							jLabelProjectName.setText("Project name:");
							jLabelProjectName.setPreferredSize(new java.awt.Dimension(99, 15));
							jLabelProjectName.setHorizontalAlignment(SwingConstants.TRAILING);
						}
						{
							jTextFieldProjectName = new JTextField();
							jPanelProjectName.add(jTextFieldProjectName);
							jTextFieldProjectName.setText(SystemConfig.PROJECT_NAME);
							jTextFieldProjectName.setPreferredSize(new java.awt.Dimension(300, 20));
						}
					}
					{
						jPanelPackageName = new JPanel();
						jPanelGenerate.add(jPanelPackageName);
						FlowLayout jPanelPackageNameLayout = new FlowLayout();
						jPanelPackageNameLayout.setAlignment(FlowLayout.LEFT);
						jPanelPackageName.setLayout(jPanelPackageNameLayout);
						{
							jLabelPackageName = new JLabel();
							jPanelPackageName.add(jLabelPackageName);
							jLabelPackageName.setText("Package name:");
							jLabelPackageName
								.setPreferredSize(new java.awt.Dimension(99, 15));
							jLabelPackageName
								.setHorizontalAlignment(SwingConstants.TRAILING);
						}
						{
							ComboBoxModel jComboBoxPackageNameModel = new DefaultComboBoxModel();
							jComboBoxPackageName = new JComboBox();
							jPanelPackageName.add(jComboBoxPackageName);
							jComboBoxPackageName.setModel(jComboBoxPackageNameModel);
							jComboBoxPackageName.setEditable(true);
							jComboBoxPackageName.setPreferredSize(new java.awt.Dimension(300, 20));
						}
					}
					{
						jPanelControl = new JPanel();
						jPanelGenerate.add(jPanelControl);
						FlowLayout jPanelControlLayout = new FlowLayout();
						jPanelControlLayout.setAlignment(FlowLayout.LEFT);
						jPanelControl.setLayout(jPanelControlLayout);
						{
							jLabelTypes = new JLabel();
							jPanelControl.add(jLabelTypes);
							jLabelTypes.setText("Generate types:");
							jLabelTypes
								.setPreferredSize(new java.awt.Dimension(99, 15));
							jLabelTypes
								.setHorizontalAlignment(SwingConstants.TRAILING);
						}
						{
							jCheckBoxModel = new JCheckBox();
							jPanelControl.add(jCheckBoxModel);
							jCheckBoxModel.setText("Model");
							jCheckBoxModel.setSelected(true);
							jCheckBoxModel.setMnemonic(java.awt.event.KeyEvent.VK_M);
						}
						{
							jCheckBoxManager = new JCheckBox();
							jPanelControl.add(jCheckBoxManager);
							jCheckBoxManager.setText("Manager");
							jCheckBoxManager
								.setMnemonic(java.awt.event.KeyEvent.VK_N);
						}
						{
							jCheckBoxJsp = new JCheckBox();
							jPanelControl.add(jCheckBoxJsp);
							jCheckBoxJsp.setText("Jsp");
							jCheckBoxJsp
								.setMnemonic(java.awt.event.KeyEvent.VK_J);
							jCheckBoxJsp.setEnabled(false);
						}
						{
							jCheckBoxFieldDef = new JCheckBox();
							jPanelControl.add(jCheckBoxFieldDef);
							jCheckBoxFieldDef.setText("FieldDef");
							jCheckBoxFieldDef
								.setMnemonic(java.awt.event.KeyEvent.VK_F);
						}
					}
					{
						jPanelButtons = new JPanel();
						FlowLayout jPanelButtonsLayout = new FlowLayout();
						jPanelButtonsLayout.setAlignment(FlowLayout.LEFT);
						jPanelGenerate.add(jPanelButtons);
						jPanelButtons.setLayout(jPanelButtonsLayout);
						{
							jPanelButtons.add(Box.createRigidArea(new java.awt.Dimension(99, 15)));
						}
						{
							jButtonGenerate = new JButton();
							jPanelButtons.add(jButtonGenerate);
							jButtonGenerate.setText("Generate");
							jButtonGenerate
								.setMnemonic(java.awt.event.KeyEvent.VK_G);
							jButtonGenerate
								.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent evt) {
										jButtonGenerateActionPerformed(evt);
									}
								});
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		jTableTables.setEnabled(enabled);
		jTextFieldFind.setEnabled(enabled);
		jButtonFind.setEnabled(enabled);
		jTextFieldProjectName.setEnabled(enabled);
		jComboBoxPackageName.setEnabled(enabled);
		jCheckBoxModel.setEnabled(enabled);
		jCheckBoxManager.setEnabled(enabled);
		jCheckBoxFieldDef.setEnabled(enabled);
//		jCheckBoxJsp.setEnabled(enabled);
		jButtonGenerate.setEnabled(enabled);
	}
	
	public void requestFocus() {
		super.requestFocus();
		jTextFieldFind.requestFocus();
	}
	
	public boolean init() {
		new Thread() {
			public void run() {
				try {
					loadTables();
					loadPackageNames();
					crudGenerator = new CrudGenerator();
				} catch (Exception ex) {
					log.error("CRUD Generator init error", ex);
					UIUtils.showError("CRUD Generator init error", ex);
				}
			}
		}.start();
		return true;
	}
	
	private void loadTables() throws Exception {
		Connection conn = ConnectionUtils.getConnection();
        DatabaseMetaData meta = conn.getMetaData();
        String userName = meta.getUserName().toUpperCase();
    	tableModel.setRowCount(0);
        ResultSet rs = null;
        try {
        	rs = meta.getTables(null, userName, null, null);
        	while (rs.next()) {
        		tableModel.addRow(new String[] {
        				rs.getString("TABLE_NAME"),
        				rs.getString("TABLE_TYPE"),
        				rs.getString("REMARKS")
        		});
        	}
        	if (meta.getDriverName().contains("SQL Server")) {
        		rs.close();
            	rs = meta.getTables(null, "dbo", null, null);
            	while (rs.next()) {
            		tableModel.addRow(new String[] {
            				"dbo." + rs.getString("TABLE_NAME"),
            				rs.getString("TABLE_TYPE"),
            				rs.getString("REMARKS")
            		});
            	}
        	}
        } finally {
        	try {
        		rs.close();
        	} catch (Exception ex) {
        	}
        }
	}
	
	private void loadPackageNames() {
		jComboBoxPackageName.setModel(new DefaultComboBoxModel(ClassUtils.getProjectPackageNames().toArray()));
	}
	
	private void jTextFieldFindActionPerformed(ActionEvent evt) {
		jButtonFindActionPerformed(evt);
	}

	private void jButtonFindActionPerformed(ActionEvent evt) {
		String toFind = jTextFieldFind.getText().trim().toUpperCase();
		if (toFind.length() == 0) {
			return;
		}
		int currentRow = jTableTables.getSelectedRow();
		for (int i = 0; i < jTableTables.getRowCount(); i++) {
			currentRow++;
			if (currentRow == jTableTables.getRowCount()) {
				currentRow = 0;
			}
			for (int j = 0; j < jTableTables.getColumnCount(); j++) {
				Object value = jTableTables.getValueAt(currentRow, j);
				if (value == null) {
					continue;
				}
				if (value.toString().toUpperCase().indexOf(toFind) >= 0) {
					jTableTables.getSelectionModel().setSelectionInterval(currentRow, currentRow);
					jTableTables.scrollRectToVisible(jTableTables.getCellRect(currentRow, 0, true));
					return;
				}
			}
		}
	}

	private void jButtonGenerateActionPerformed(ActionEvent evt) {
		if (crudGenerator == null) {
			return;
		}
		final String projectName = jTextFieldProjectName.getText().trim();
		if (projectName == null || projectName.length() == 0) {
			return;
		}
		Object packageNameObject = jComboBoxPackageName.getSelectedItem();
		if (packageNameObject == null) {
			return;
		}
		final String packageName = packageNameObject.toString().trim();
		if (packageName.length() == 0) {
			return;
		}
		final int[] rows = jTableTables.getSelectedRows();
		if (rows == null || rows.length == 0) {
			return;
		}
		setEnabled(false);
		new Thread() {
			public void run() {
				try {
					crudGenerator.reset();
					for (int i : rows) {
						String tableName = jTableTables.getModel().getValueAt(i, 0).toString();
						if (jCheckBoxModel.isSelected()) {
							crudGenerator.generateModel(projectName, packageName, tableName);
						}
						if (jCheckBoxManager.isSelected()) {
							crudGenerator.generateManager(projectName, packageName, tableName);
						}
						if (jCheckBoxFieldDef.isSelected()) {
							crudGenerator.generateFieldDef(projectName, packageName, tableName);
						}
						if (jCheckBoxJsp.isSelected()) {
							
						}
					}
				} catch (Exception ex) {
					log.error("CRUD Generator error", ex);
					UIUtils.showError("CRUD Generator error", ex);
				}
				int count = crudGenerator.getCount();
				if (count > 0) {
					log.info(count + " file(s) generated. Refresh the project.");
					UIUtils.showInformation("Info", count + " file(s) generated. \nRefresh the project.");
				}
				setEnabled(true);
			}
		}.start();
	}
	
}
