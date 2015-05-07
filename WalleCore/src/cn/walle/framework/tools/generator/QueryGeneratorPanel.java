package cn.walle.framework.tools.generator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.walle.framework.core.support.SystemConfig;
import cn.walle.framework.core.util.ClassUtils;
import cn.walle.framework.core.util.SqlUtils;
import cn.walle.framework.tools.util.UIUtils;

public class QueryGeneratorPanel extends cn.walle.framework.tools.base.BasePanel {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private JTable jTableParameters;
	private JScrollPane jScrollPaneParameters;
	private JPanel jPanelParameters;
	private JTabbedPane jTabbedPaneMain;
	private JPanel jPanelProjectName;
	private JTextField jTextFieldProjectName;
	private JLabel jLabelProjectName;
	private JPanel jPanelQueryName;
	private JTextField jTextFieldQueryName;
	private JLabel jLabelQueryName;
	private JTextArea jTextAreaSql;
	private JScrollPane jScrollPaneSql;
	private JPanel jPanelSql;
	private JPanel jPanelGenerate;
	private JButton jButtonGenerate;
	private JPanel jPanelButtons;
	private JPanel jPanelMain;
	private JComboBox jComboBoxPackageName;
	private JLabel jLabelPackageName;
	private JPanel jPanelPackageName;
	
	private DefaultTableModel tableModel = new DefaultTableModel(
			new String[] {"Parameter Name", "Type", "Dynamic"}, 0) {
		public Class<?> getColumnClass(int columnIndex) {
        	switch (columnIndex) {
        	case 0:
				return String.class;
			case 1:
				return String.class;
			case 2:
				return Boolean.class;
			default:
				return super.getColumnClass(columnIndex);
        	}
		}
		public boolean isCellEditable(int row, int column) {
        	switch (column) {
        	case 0:
				return false;
			case 1:
				return true;
			case 2:
				return true;
			default:
				return super.isCellEditable(row, column);
			}
        }
	};
	
	private QueryGenerator queryGenerator;
	
	public QueryGeneratorPanel() {
		initGUI();
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(640, 480));
			this.setTitle("Query Generator");
			{
				jPanelMain = new JPanel();
				BorderLayout jPanelMainLayout = new BorderLayout();
				this.add(jPanelMain, BorderLayout.CENTER);
				jPanelMain.setLayout(jPanelMainLayout);
				{
					jTabbedPaneMain = new JTabbedPane();
					jPanelMain.add(jTabbedPaneMain, BorderLayout.CENTER);
					{
						jPanelSql = new JPanel();
						jTabbedPaneMain.addTab("Sql", null, jPanelSql, null);
						BorderLayout jPanelSqlLayout = new BorderLayout();
						jPanelSql.setLayout(jPanelSqlLayout);
						{
							jScrollPaneSql = new JScrollPane();
							jPanelSql.add(jScrollPaneSql, BorderLayout.CENTER);
							{
								jTextAreaSql = new JTextArea();
								jScrollPaneSql.setViewportView(jTextAreaSql);
								jTextAreaSql
									.setText("select t.c1, t.c2\n  from table1 t\n where t.c1 = :p1\n   and " + SqlUtils.SQL_EXTRA_CONDITIONS_MACRO);
							}
						}
					}
					{
						jPanelParameters = new JPanel();
						BorderLayout jPanelParametersLayout = new BorderLayout();
						jTabbedPaneMain.addTab(
							"Parameters",
							null,
							jPanelParameters,
							null);
						jPanelParameters.setLayout(jPanelParametersLayout);
						jPanelParameters
							.addComponentListener(new ComponentAdapter() {
							public void componentShown(ComponentEvent evt) {
								jPanelParametersComponentShown(evt);
							}
							});
						{
							jScrollPaneParameters = new JScrollPane();
							jPanelParameters.add(
								jScrollPaneParameters,
								BorderLayout.CENTER);
							{
								jTableParameters = new JTable();
								jScrollPaneParameters
									.setViewportView(jTableParameters);
								jTableParameters
									.setModel(tableModel);
								jTableParameters.getColumnModel().getColumn(1).setCellEditor(
										new DefaultCellEditor(new JComboBox(
												new String[] {"String", "Integer", "Long", "Double", "Date",
														"String[]", "Integer[]", "Long[]", "Double[]", "Date[]"})));
								jTableParameters.getModel().addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent evt) {
										jTableParameterDataChanged(evt);
									}
								});
							}
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
						jPanelQueryName = new JPanel();
						FlowLayout jPanelQueryNameLayout = new FlowLayout();
						jPanelQueryNameLayout.setAlignment(FlowLayout.LEFT);
						jPanelGenerate.add(jPanelQueryName);
						jPanelQueryName.setLayout(jPanelQueryNameLayout);
						{
							jLabelQueryName = new JLabel();
							jPanelQueryName.add(jLabelQueryName);
							jLabelQueryName.setText("Query name:");
							jLabelQueryName.setPreferredSize(new java.awt.Dimension(99, 15));
							jLabelQueryName.setHorizontalAlignment(SwingConstants.TRAILING);
						}
						{
							jTextFieldQueryName = new JTextField();
							jPanelQueryName.add(jTextFieldQueryName);
							jTextFieldQueryName.setText("XxxQuery");
							jTextFieldQueryName.setPreferredSize(new java.awt.Dimension(300, 20));
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
		jTextAreaSql.setEnabled(enabled);
		jTextFieldProjectName.setEnabled(enabled);
		jComboBoxPackageName.setEnabled(enabled);
		jTextFieldQueryName.setEnabled(enabled);
		jButtonGenerate.setEnabled(enabled);
	}
	
	public void requestFocus() {
		super.requestFocus();
		jTextAreaSql.requestFocus();
	}
	
	public boolean init() {
		new Thread() {
			public void run() {
				try {
					loadPackageNames();
					queryGenerator = new QueryGenerator();
				} catch (Exception ex) {
					log.error("Query Generator init error", ex);
					UIUtils.showError("Query Generator init error", ex);
				}
			}
		}.start();
		return true;
	}
	
	private void loadPackageNames() {
		Object[] packageNames = ClassUtils.getProjectPackageNames().toArray();
		for (int i = 0; i < packageNames.length; i++) {
			packageNames[i] = packageNames[i] + ".query";
		}
		jComboBoxPackageName.setModel(new DefaultComboBoxModel(packageNames));
	}
	
	private Map<String, String> paramTypes = new HashMap<String, String>();
	private Map<String, Boolean> paramDynamics = new HashMap<String, Boolean>();
	
	private void handleSqlParameters() {
		Vector<Vector<Object>> parameterRows = tableModel.getDataVector();
		for (Vector<Object> parameterRow : parameterRows) {
			paramTypes.put((String) parameterRow.get(0), (String) parameterRow.get(1));
			paramDynamics.put((String) parameterRow.get(0), (Boolean) parameterRow.get(2));
		}
		
		tableModel.setRowCount(0);
		String sql = jTextAreaSql.getText();
		if (sql == null || sql.trim().length() == 0) {
			return;
		}
		
		sql = SqlUtils.convertParametersToJavaStyle(sql);
		jTextAreaSql.setText(sql);

		String[] lines = sql.replace("\r", "").split("\n");
		for (String line : lines) {
			boolean currentLineIsDynamic = line.trim().matches("<<.*>>");
			for (String lineParameterName : SqlUtils.getSqlParameters(line).values()) {
				paramDynamics.put(lineParameterName, currentLineIsDynamic);
			}
		}
		
		TreeMap<Integer, String> parameters = SqlUtils.getSqlParameters(sql);
		
		for (String paramName : new LinkedHashSet<String>(parameters.values())) {
			if (paramTypes.containsKey(paramName)) {
				tableModel.addRow(new Object[] {
						paramName,
						paramTypes.get(paramName),
						paramDynamics.get(paramName)});
			} else {
				paramTypes.put(paramName, "String");
				tableModel.addRow(new Object[] {paramName, "String", paramDynamics.get(paramName)});
			}
		}
	}
	
	private void jButtonGenerateActionPerformed(ActionEvent evt) {
		if (queryGenerator == null) {
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
		final String queryName = jTextFieldQueryName.getText().trim();
		if (queryName == null || queryName.length() == 0) {
			return;
		}
		final String sql = jTextAreaSql.getText();
		if (sql == null || sql.trim().length() == 0) {
			return;
		}
		setEnabled(false);
		handleSqlParameters();
		new Thread() {
			public void run() {
				try {
					queryGenerator.reset();
					queryGenerator.generateQuery(projectName, packageName, queryName, sql, paramTypes, paramDynamics);
				} catch (Exception ex) {
					log.error("Query Generator error", ex);
					UIUtils.showError("Query Generator error", ex);
				}
				int count = queryGenerator.getCount();
				if (count > 0) {
					log.info(count + " file(s) generated. Refresh the project.");
					UIUtils.showInformation("Info", count + " file(s) generated. \nRefresh the project.");
				}
				setEnabled(true);
			}
		}.start();
	}
	
	private void jPanelParametersComponentShown(ComponentEvent evt) {
		handleSqlParameters();
	}
	
	private void jTableParameterDataChanged(TableModelEvent evt) {
		int column = evt.getColumn();
		if (column != 2) {
			return;
		}
		int row = evt.getFirstRow();
		TableModel model = (TableModel) evt.getSource();
		String parameterName = (String) model.getValueAt(row, 0);
		boolean isDynamic = (Boolean) model.getValueAt(row, 2);
		paramDynamics.put(parameterName, isDynamic);
		
		String sql = jTextAreaSql.getText();
		String[] lines = sql.replace("\r", "").split("\n");
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			boolean currentLineShouldBeDynamic = false;
			for (String lineParameterName : SqlUtils.getSqlParameters(line).values()) {
				if (paramDynamics.get(lineParameterName)) {
					currentLineShouldBeDynamic = true;
					break;
				}
			}
			boolean currentLineIsDynamic = line.trim().matches("<<.*>>");
			if (currentLineShouldBeDynamic && ! currentLineIsDynamic) {
				String trimedLine = line.trim();
				int firstCharIndex = line.indexOf(trimedLine);
				String head = line.substring(0, firstCharIndex);
				for (int j = 0; j < 2; j++) {
					if (head.endsWith(" ")) {
						head = head.substring(0, head.length() - 1);
					}
				}
				line = head + "<<" + trimedLine + ">>";
			}
			if (! currentLineShouldBeDynamic && currentLineIsDynamic) {
				line = line.replace("<<", "  ").replace(">>", "");
			}
			lines[i] = line;
		}
		sql = StringUtils.join(lines, "\r\n");
		jTextAreaSql.setText(sql);
	}

}
