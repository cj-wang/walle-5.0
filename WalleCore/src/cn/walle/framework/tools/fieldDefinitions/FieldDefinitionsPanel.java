package cn.walle.framework.tools.fieldDefinitions;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import cn.walle.framework.common.support.FieldDefinition;
import cn.walle.framework.core.support.SystemConfig;
import cn.walle.framework.core.util.EntityUtils;
import cn.walle.framework.tools.generator.CrudGenerator;
import cn.walle.framework.tools.util.UIUtils;

public class FieldDefinitionsPanel extends cn.walle.framework.tools.base.BasePanel {
	
	protected final Log log = LogFactory.getLog(getClass());
	private JPanel jPanelFieldsControlButtons;
	private JPanel jPanelCenter;
	private JButton jButtonAdd;
	private JButton jButtonDelete;
	private JButton jButtonDown;
	private JButton jButtonUp;

	private JPanel jPanelMain;
	private JList jList;
	private JScrollPane jScrollPaneList;
	private FieldDefinitionEditPanel fieldDefinitionEditPanel;
	private JButton jButtonSave;
	private JPanel jPanelEdit;
	private JPanel jPanelButtons;
	private DefaultComboBoxModel jListModel;
	
	private String packageName;
	private File file;
	private String entityName;
	private LinkedHashMap<String, FieldDefinition> fieldDefinitions;
	private int newIndex = 0;

	public FieldDefinitionsPanel() {
		initGUI();
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			{
				jPanelMain = new JPanel();
				BorderLayout jPanelMainLayout = new BorderLayout();
				this.add(jPanelMain, BorderLayout.CENTER);
				jPanelMain.setLayout(jPanelMainLayout);
				{
					jPanelButtons = new JPanel();
					FlowLayout jPanelButtonsLayout = new FlowLayout();
					jPanelButtonsLayout.setAlignment(FlowLayout.LEFT);
					jPanelButtons.setLayout(jPanelButtonsLayout);
					jPanelMain.add(jPanelButtons, BorderLayout.NORTH);
					{
						jButtonSave = new JButton();
						jPanelButtons.add(jButtonSave);
						jButtonSave.setText("Save");
						jButtonSave.setMnemonic(java.awt.event.KeyEvent.VK_S);
						jButtonSave.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jButtonSaveActionPerformed(evt);
							}
						});
					}
				}
				{
					jPanelCenter = new JPanel();
					BorderLayout jPanelCenterLayout = new BorderLayout();
					jPanelMain.add(jPanelCenter, BorderLayout.CENTER);
					jPanelCenter.setLayout(jPanelCenterLayout);
					{
						jPanelEdit = new JPanel();
						jPanelCenter.add(jPanelEdit, BorderLayout.WEST);
						BorderLayout jPanelEditLayout = new BorderLayout();
						jPanelEdit.setLayout(jPanelEditLayout);
						jPanelEdit.setBorder(BorderFactory
							.createTitledBorder("Fields"));
						{
							jScrollPaneList = new JScrollPane();
							jPanelEdit.add(jScrollPaneList, BorderLayout.WEST);
							jScrollPaneList
								.setPreferredSize(new java.awt.Dimension(150, 3));
							{
								jList = new JList();
								jScrollPaneList.setViewportView(jList);
								jList
									.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
								jList
									.addListSelectionListener(new ListSelectionListener() {
										public void valueChanged(
											ListSelectionEvent evt) {
											jListValueChanged(evt);
										}
									});
							}
						}
						{
							jPanelFieldsControlButtons = new JPanel();
							jPanelEdit.add(
								jPanelFieldsControlButtons,
								BorderLayout.EAST);
							jPanelFieldsControlButtons.setLayout(null);
							jPanelFieldsControlButtons.setPreferredSize(new java.awt.Dimension(21, 392));
							{
								jButtonAdd = new JButton();
								jPanelFieldsControlButtons.add(jButtonAdd);
								jButtonAdd.setText("＋");
								jButtonAdd.setBorder(BorderFactory
									.createCompoundBorder(null,null));
								jButtonAdd.setBounds(0, 0, 21, 21);
								jButtonAdd
									.addActionListener(new ActionListener() {
										public void actionPerformed(
											ActionEvent evt) {
											jButtonAddActionPerformed(evt);
										}
									});
							}
							{
								jButtonDelete = new JButton();
								jPanelFieldsControlButtons.add(jButtonDelete);
								jButtonDelete.setText("－");
								jButtonDelete.setBorder(BorderFactory
									.createCompoundBorder(null, null));
								jButtonDelete.setBounds(0, 21, 21, 21);
								jButtonDelete
									.addActionListener(new ActionListener() {
										public void actionPerformed(
											ActionEvent evt) {
											jButtonDeleteActionPerformed(evt);
										}
									});
							}
							{
								jButtonUp = new JButton();
								jPanelFieldsControlButtons.add(jButtonUp);
								jButtonUp.setText("∧");
								jButtonUp.setBorder(BorderFactory
									.createCompoundBorder(null, null));
								jButtonUp.setBounds(0, 42, 21, 21);
								jButtonUp
									.addActionListener(new ActionListener() {
										public void actionPerformed(
											ActionEvent evt) {
											jButtonUpActionPerformed(evt);
										}
									});
							}
							{
								jButtonDown = new JButton();
								jPanelFieldsControlButtons.add(jButtonDown);
								jButtonDown.setText("∨");
								jButtonDown.setBorder(BorderFactory
									.createCompoundBorder(null, null));
								jButtonDown.setBounds(0, 63, 21, 21);
								jButtonDown
									.addActionListener(new ActionListener() {
										public void actionPerformed(
											ActionEvent evt) {
											jButtonDownActionPerformed(evt);
										}
									});
							}
						}
					}
					{
						fieldDefinitionEditPanel = new FieldDefinitionEditPanel();
						jPanelCenter.add(fieldDefinitionEditPanel, BorderLayout.CENTER);
						fieldDefinitionEditPanel.setBorder(BorderFactory.createTitledBorder("Field Definition"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean init() {
		fieldDefinitionEditPanel.init();
		return true;
	}

	public void setParameters(Object... parameters) {
		if (parameters.length == 2
				&& parameters[0] instanceof String
				&& parameters[1] instanceof File) {
			super.setParameters(parameters);
			packageName = (String) parameters[0];
			if (packageName.endsWith(".model")) {
				packageName = packageName.substring(0, packageName.length() - 6);
			}
			file = (File) parameters[1];
			String fileName = file.getName();
			if (fileName.endsWith("Fields.xml")) {
				entityName = fileName.substring(0, fileName.length() - "Fields.xml".length());
			} else {
				throw new IllegalArgumentException("File must be *Fields.xml");
			}
		} else {
			throw new IllegalArgumentException("Parameters for FieldDefinitionsPanel must be of type Object[]{String, File}");
		}
	}

	public boolean refresh() {
		try {
			fieldDefinitions = new LinkedHashMap<String, FieldDefinition>();
			jList.setModel(new DefaultComboBoxModel(new Object[] {"loading..."}));
			
			Document xmlDocument = new SAXBuilder().build(file);
			for (Element beanElement : (List<Element>) xmlDocument.getRootElement().getChildren("bean", xmlDocument.getRootElement().getNamespace())) {
				if ("fieldDefinition".equals(beanElement.getAttributeValue("parent"))) {
					String id = beanElement.getAttributeValue("id");
					if (id.startsWith(entityName + ".")) {
						FieldDefinition fieldDefinition = new FieldDefinition();
						fieldDefinition.setBeanName(id);
						String fieldName = id.substring(entityName.length() + 1);
						for (Element element : (List<Element>) beanElement.getChildren("property", beanElement.getNamespace())) {
							if ("fieldName".equals(element.getAttributeValue("name"))) {
								fieldDefinition.setFieldName(element.getAttributeValue("value"));
							} else if ("label".equals(element.getAttributeValue("name"))) {
								fieldDefinition.setLabel(element.getAttributeValue("value"));
							} else if ("fieldType".equals(element.getAttributeValue("name"))) {
								fieldDefinition.setFieldType(element.getAttributeValue("value"));
							} else if ("sortable".equals(element.getAttributeValue("name"))) {
								fieldDefinition.setSortable(Boolean.valueOf(element.getAttributeValue("value")));
							} else if ("nullable".equals(element.getAttributeValue("name"))) {
								fieldDefinition.setNullable(Boolean.valueOf(element.getAttributeValue("value")));
							} else if ("length".equals(element.getAttributeValue("name"))) {
								fieldDefinition.setLength(Integer.valueOf(element.getAttributeValue("value")));
							} else if ("precision".equals(element.getAttributeValue("name"))) {
								fieldDefinition.setPrecision(Integer.valueOf(element.getAttributeValue("value")));
							} else if ("scale".equals(element.getAttributeValue("name"))) {
								fieldDefinition.setScale(Integer.valueOf(element.getAttributeValue("value")));
							} else if ("width".equals(element.getAttributeValue("name"))) {
								fieldDefinition.setWidth(Integer.valueOf(element.getAttributeValue("value")));
							}
						}
						fieldDefinitions.put(fieldName, fieldDefinition);
					}
				}
			}
			
			jListModel = new DefaultComboBoxModel(fieldDefinitions.keySet().toArray());
			jList.setModel(jListModel);
			jList.setSelectedIndex(0);
			jList.ensureIndexIsVisible(0);
			
			newIndex = 0;
		} catch (Exception ex) {
			log.error("FieldDefinitionsPanel error", ex);
			UIUtils.showError("FieldDefinitionsPanel error", ex);
		}
		return true;
	}
	
	private void commitEdit() {
		FieldDefinition fieldDefinition = fieldDefinitionEditPanel.getFieldDefinition();
		if (fieldDefinition != null) {
			fieldDefinitionEditPanel.endEdit();
			String oldFieldName = fieldDefinitionEditPanel.getOldFieldName();
			String newFieldName = fieldDefinitionEditPanel.getNewFieldName();
			fieldDefinitionEditPanel.reset();
			
			if (! oldFieldName.equals(newFieldName)) {
				fieldDefinitions.remove(oldFieldName);
				fieldDefinitions.put(newFieldName, fieldDefinition);
				
				int index = jListModel.getIndexOf(oldFieldName);
				int selectedIndex = jList.getSelectedIndex();
				jListModel.removeElementAt(index);
				jListModel.insertElementAt(newFieldName, index);
				if (index == selectedIndex) {
					jList.setSelectedIndex(index);
					jList.ensureIndexIsVisible(index);
				}
			}
		}
	}
	
	private void jListValueChanged(ListSelectionEvent evt) {
		if (evt.getValueIsAdjusting()) {
			commitEdit();
		} else {
			String currentField = (String) jList.getSelectedValue();
			if (currentField != null) {
				fieldDefinitionEditPanel.edit(fieldDefinitions.get(currentField));
			}
		}
	}
	
	private void jButtonSaveActionPerformed(ActionEvent evt) {
		commitEdit();
		try {
			Map<String, Object> dataModel = new HashMap<String, Object>();
			dataModel.put("className", entityName);
			dataModel.put("tableName", entityName);
			String pkFieldName = EntityUtils.getIdFieldName(EntityUtils.getEntityClass(entityName));
			List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < jListModel.getSize(); i++) {
				String fieldName = (String) jListModel.getElementAt(i);
				FieldDefinition fieldDefinition = fieldDefinitions.get(fieldName);
				Map<String, Object> column = new HashMap<String, Object>();
				column.put("fieldName", fieldName);
				column.put("isPK", pkFieldName != null && pkFieldName.equals(fieldName));
				column.put("label", fieldDefinition.getLabel());
				column.put("uiFieldType", fieldDefinition.getFieldType());
				column.put("sortable", fieldDefinition.isSortable() ? "true" : "false");
				column.put("nullable", fieldDefinition.isNullable() ? "true" : "false");
				column.put("length", Integer.toString(fieldDefinition.getLength()));
				column.put("precision", Integer.toString(fieldDefinition.getPrecision()));
				column.put("scale", Integer.toString(fieldDefinition.getScale()));
				column.put("width", Integer.toString(fieldDefinition.getWidth()));
				columns.add(column);
			}
			dataModel.put("columns", columns);
			
			CrudGenerator crudGenerator = new CrudGenerator();
			crudGenerator.setDataModel(dataModel);
			crudGenerator.setPromptOverwrite(false);
			//to be modified...
			//add project select
			crudGenerator.generateFieldDef(SystemConfig.PROJECT_NAME, packageName, entityName);
						
			UIUtils.showInformation("Info", "Saved successfully.");
		} catch (Exception ex) {
			log.error("FieldDefinitionsPanel error", ex);
			UIUtils.showError("FieldDefinitionsPanel error", ex);
		}
	}
	
	private void jButtonAddActionPerformed(ActionEvent evt) {
		commitEdit();
		
		String newFieldName = "new_" + newIndex++;
		while (fieldDefinitions.containsKey(newFieldName)) {
			newFieldName = "new_" + newIndex++;
		}
		
		FieldDefinition fieldDefinition = new FieldDefinition();
		fieldDefinition.setBeanName(entityName + "." + newFieldName);
		fieldDefinition.setFieldName(newFieldName);
		fieldDefinition.setLabel(newFieldName);
		fieldDefinition.setNullable(true);
		fieldDefinition.setSortable(true);
		fieldDefinition.setFieldType(FieldDefinition.FIELD_TYPE_STRING);
		fieldDefinition.setLength(20);
		fieldDefinition.setWidth(20);

		fieldDefinitions.put(newFieldName, fieldDefinition);

		int selectedIndex = jList.getSelectedIndex();
		if (selectedIndex >= 0 && selectedIndex < jListModel.getSize()) {
			jListModel.insertElementAt(newFieldName, selectedIndex + 1);
			jList.setSelectedIndex(selectedIndex + 1);
		} else {
			jListModel.addElement(newFieldName);
			jList.setSelectedIndex(jListModel.getSize() - 1);
		}
		jList.ensureIndexIsVisible(jList.getSelectedIndex());
	}

	private void jButtonDeleteActionPerformed(ActionEvent evt) {
		commitEdit();
		int selectedIndex = jList.getSelectedIndex();
		if (selectedIndex == -1) {
			return;
		}
		jListModel.removeElementAt(selectedIndex);
		if (selectedIndex == jListModel.getSize()) {
			selectedIndex--;
		}
		jList.setSelectedIndex(selectedIndex);
		jList.ensureIndexIsVisible(selectedIndex);
	}
	
	private void jButtonUpActionPerformed(ActionEvent evt) {
		commitEdit();
		int selectedIndex = jList.getSelectedIndex();
		if (selectedIndex == -1 || selectedIndex == 0) {
			return;
		}
		Object selectedValue = jListModel.getElementAt(selectedIndex);
		jListModel.removeElementAt(selectedIndex);
		jListModel.insertElementAt(selectedValue, selectedIndex - 1);
		jList.setSelectedIndex(selectedIndex - 1);
		jList.ensureIndexIsVisible(selectedIndex - 1);
	}
	
	private void jButtonDownActionPerformed(ActionEvent evt) {
		commitEdit();
		int selectedIndex = jList.getSelectedIndex();
		if (selectedIndex == -1 || selectedIndex == jListModel.getSize() - 1) {
			return;
		}
		Object selectedValue = jListModel.getElementAt(selectedIndex);
		jListModel.removeElementAt(selectedIndex);
		jListModel.insertElementAt(selectedValue, selectedIndex + 1);
		jList.setSelectedIndex(selectedIndex + 1);
		jList.ensureIndexIsVisible(selectedIndex + 1);
	}
	
}
