package cn.walle.framework.tools.fieldDefinitions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeSet;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cn.walle.framework.common.service.SelectCodeManager;
import cn.walle.framework.common.support.FieldDefinition;
import cn.walle.framework.common.support.SelectCodeDefinition;
import cn.walle.framework.core.util.ContextUtils;

public class FieldDefinitionEditPanel extends cn.walle.framework.tools.base.BasePanel {
	private JLabel jLabelFieldType;
	private JTextField jTextFieldEntity;
	private JTextField jTextFieldLength;
	private JTextField jTextFieldWidth;
	private JLabel jLabelCodeType;
	private JComboBox jComboBoxSelectCodeType;
	private JComboBox jComboBoxFieldType;
	private JCheckBox jCheckBoxSortable;
	private JCheckBox jCheckBoxNullbale;
	private JTextField jTextFieldScale;
	private JTextField jTextFieldPrecision;
	private JLabel jLabelSortable;
	private JLabel jLabelPrecision;
	private JLabel jLabelWidth;
	private JLabel jLabelScale;
	private JLabel jLabelLength;
	private JLabel jLabelLabel;
	private JTextField jTextFieldLabel;
	private JTextField jTextFieldField;
	private JLabel jLabelEntity;
	private JLabel jLabelNullable;
	private JLabel jLabelField;

	private FieldDefinition fieldDefinition;
	private String oldFieldName;
	private String newFieldName;
	
	public FieldDefinitionEditPanel() {
		initGUI();
	}
	
	private void initGUI() {
		try {
			{
				this.setPreferredSize(new java.awt.Dimension(441, 420));
				this.setLayout(null);
				{
					jLabelEntity = new JLabel();
					this.add(jLabelEntity);
					jLabelEntity.setText("Entity:");
					jLabelEntity.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabelEntity.setBounds(7, 35, 77, 28);
				}
				{
					jLabelField = new JLabel();
					this.add(jLabelField);
					jLabelField.setText("Field:");
					jLabelField.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabelField.setBounds(7, 70, 77, 28);
				}
				{
					jLabelLabel = new JLabel();
					this.add(jLabelLabel);
					jLabelLabel.setText("Label:");
					jLabelLabel.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabelLabel.setBounds(7, 105, 77, 28);
				}
				{
					jLabelNullable = new JLabel();
					this.add(jLabelNullable);
					jLabelNullable.setText("Nullable:");
					jLabelNullable.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabelNullable.setBounds(7, 140, 77, 28);
				}
				{
					jLabelSortable = new JLabel();
					this.add(jLabelSortable);
					jLabelSortable.setText("Sortable:");
					jLabelSortable.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabelSortable.setBounds(112, 140, 77, 28);
				}
				{
					jLabelFieldType = new JLabel();
					this.add(jLabelFieldType);
					jLabelFieldType.setText("Field Type:");
					jLabelFieldType.setBounds(7, 175, 77, 28);
					jLabelFieldType
						.setHorizontalAlignment(SwingConstants.TRAILING);
				}
				{
					jLabelCodeType = new JLabel();
					this.add(jLabelCodeType);
					jLabelCodeType.setText("Code Type:");
					jLabelCodeType
						.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabelCodeType.setBounds(7, 210, 77, 28);
				}
				{
					jLabelLength = new JLabel();
					this.add(jLabelLength);
					jLabelLength.setText("Length:");
					jLabelLength.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabelLength.setBounds(7, 245, 77, 28);
				}
				{
					jLabelPrecision = new JLabel();
					this.add(jLabelPrecision);
					jLabelPrecision.setText("Precision:");
					jLabelPrecision.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabelPrecision.setBounds(7, 280, 77, 28);
				}
				{
					jLabelScale = new JLabel();
					this.add(jLabelScale);
					jLabelScale.setText("Scale:");
					jLabelScale.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabelScale.setBounds(175, 280, 35, 28);
				}
				{
					jLabelWidth = new JLabel();
					this.add(jLabelWidth);
					jLabelWidth.setText("Width:");
					jLabelWidth.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabelWidth.setBounds(7, 315, 77, 28);
				}
				{
					jTextFieldEntity = new JTextField();
					this.add(jTextFieldEntity);
					jTextFieldEntity.setBounds(91, 35, 210, 28);
					jTextFieldEntity.setEnabled(false);
				}
				{
					jTextFieldField = new JTextField();
					this.add(jTextFieldField);
					jTextFieldField.setBounds(91, 70, 210, 28);
				}
				{
					jTextFieldLabel = new JTextField();
					this.add(jTextFieldLabel);
					jTextFieldLabel.setBounds(91, 105, 210, 28);
				}
				{
					jTextFieldLength = new JTextField();
					this.add(jTextFieldLength);
					jTextFieldLength.setBounds(91, 245, 210, 28);
				}
				{
					jTextFieldPrecision = new JTextField();
					this.add(jTextFieldPrecision);
					jTextFieldPrecision.setBounds(91, 280, 84, 28);
				}
				{
					jTextFieldScale = new JTextField();
					this.add(jTextFieldScale);
					jTextFieldScale.setBounds(217, 280, 84, 28);
				}
				{
					jTextFieldWidth = new JTextField();
					this.add(jTextFieldWidth);
					jTextFieldWidth.setBounds(91, 315, 210, 28);
				}
				{
					jCheckBoxNullbale = new JCheckBox();
					this.add(jCheckBoxNullbale);
					jCheckBoxNullbale.setBounds(91, 140, 28, 28);
				}
				{
					jCheckBoxSortable = new JCheckBox();
					this.add(jCheckBoxSortable);
					jCheckBoxSortable.setBounds(196, 140, 28, 28);
				}
				{
					jComboBoxFieldType = new JComboBox();
					this.add(jComboBoxFieldType);
					jComboBoxFieldType.setBounds(91, 175, 210, 28);
					jComboBoxFieldType.setEditable(true);
					jComboBoxFieldType.setMaximumRowCount(10);
					jComboBoxFieldType.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jComboBoxFieldTypeActionPerformed(evt);
						}
					});
				}
				{
					jComboBoxSelectCodeType = new JComboBox();
					this.add(jComboBoxSelectCodeType);
					jComboBoxSelectCodeType.setBounds(91, 210, 210, 28);
					jComboBoxSelectCodeType.setEditable(true);
					jComboBoxSelectCodeType.setEnabled(false);
					jComboBoxSelectCodeType.setMaximumRowCount(10);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean init() {
		new Thread() {
			public void run() {
				ComboBoxModel jComboBoxFieldTypeModel = new DefaultComboBoxModel(
						new String[] {
								FieldDefinition.FIELD_TYPE_STRING,
								FieldDefinition.FIELD_TYPE_TEXT,
								FieldDefinition.FIELD_TYPE_BYTES,
								FieldDefinition.FIELD_TYPE_INT,
								FieldDefinition.FIELD_TYPE_DOUBLE,
								FieldDefinition.FIELD_TYPE_DATE,
								FieldDefinition.FIELD_TYPE_DATETIME,
								FieldDefinition.FIELD_TYPE_TIME,
								FieldDefinition.FIELD_TYPE_MONTH,
								FieldDefinition.FIELD_TYPE_SELECTCODE
						});
				jComboBoxFieldType.setModel(jComboBoxFieldTypeModel);
				
				TreeSet<String> codeTypes = new TreeSet<String>();
				for (String codeType : ContextUtils.getBeansOfType(SelectCodeDefinition.class).keySet()) {
					codeType = codeType.substring(SelectCodeDefinition.DEFINITION_BEAN_ID_PREFIX.length());
					codeTypes.add(codeType);
				}
				codeTypes.addAll(ContextUtils.getBeanOfType(SelectCodeManager.class).getSystemCodeTypes());
				ComboBoxModel jComboBoxSelectCodeTypeModel = new DefaultComboBoxModel(codeTypes.toArray());
				jComboBoxSelectCodeType.setModel(jComboBoxSelectCodeTypeModel);
				jComboBoxSelectCodeType.setSelectedIndex(-1);
			}
		}.start();
		
		return true;
	}
	
	public void reset() {
		this.fieldDefinition = null;
		this.oldFieldName = null;
		this.newFieldName = null;
	}

	public void edit(FieldDefinition fieldDefinition) {
		reset();

		String beanName = fieldDefinition.getBeanName();
		String entityName = beanName.substring(0, beanName.indexOf("."));
		String fieldName = fieldDefinition.getFieldName();
		jTextFieldEntity.setText(entityName);
		jTextFieldField.setText(fieldName);
		jTextFieldLabel.setText(fieldDefinition.getLabel());
		jCheckBoxNullbale.setSelected(fieldDefinition.isNullable());
		jCheckBoxSortable.setSelected(fieldDefinition.isSortable());
		String fieldType = fieldDefinition.getFieldType();
		if (fieldType != null && fieldType.startsWith(SelectCodeDefinition.DEFINITION_BEAN_ID_PREFIX)) {
			jComboBoxFieldType.setSelectedItem(SelectCodeDefinition.DEFINITION_BEAN_ID_PREFIX);
			jComboBoxSelectCodeType.setSelectedItem(fieldType.substring(SelectCodeDefinition.DEFINITION_BEAN_ID_PREFIX.length()));
		} else {
			jComboBoxFieldType.setSelectedItem(fieldType);
		}
		jTextFieldLength.setText(Integer.toString(fieldDefinition.getLength()));
		jTextFieldPrecision.setText(Integer.toString(fieldDefinition.getPrecision()));
		jTextFieldScale.setText(Integer.toString(fieldDefinition.getScale()));
		jTextFieldWidth.setText(Integer.toString(fieldDefinition.getWidth()));
		
		jTextFieldField.requestFocus();
		
		this.fieldDefinition = fieldDefinition;
		this.oldFieldName = fieldName;
	}
	
	public void endEdit() {
		if (fieldDefinition == null) {
			return;
		}
		
		this.newFieldName = jTextFieldField.getText();
		
		fieldDefinition.setBeanName(jTextFieldEntity.getText() + "." + jTextFieldField.getText());
		fieldDefinition.setFieldName(jTextFieldField.getText());
		fieldDefinition.setLabel(jTextFieldLabel.getText());
		fieldDefinition.setNullable(jCheckBoxNullbale.isSelected());
		fieldDefinition.setSortable(jCheckBoxSortable.isSelected());
		String fieldType = (String) jComboBoxFieldType.getSelectedItem();
		fieldType = fieldType == null ? "" : fieldType;
		if (SelectCodeDefinition.DEFINITION_BEAN_ID_PREFIX.equals(fieldType)) {
			fieldDefinition.setFieldType(fieldType + jComboBoxSelectCodeType.getSelectedItem());
		} else {
			fieldDefinition.setFieldType(fieldType);
		}
		if (FieldDefinition.FIELD_TYPE_STRING.equals(fieldType)) {
			fieldDefinition.setLength(Integer.parseInt(jTextFieldLength.getText()));
		} else if (FieldDefinition.FIELD_TYPE_INT.equals(fieldType)) {
			fieldDefinition.setPrecision(Integer.parseInt(jTextFieldPrecision.getText()));
		} else if (FieldDefinition.FIELD_TYPE_DOUBLE.equals(fieldType)) {
			fieldDefinition.setPrecision(Integer.parseInt(jTextFieldPrecision.getText()));
			fieldDefinition.setScale(Integer.parseInt(jTextFieldScale.getText()));
		}
		fieldDefinition.setWidth(Integer.parseInt(jTextFieldWidth.getText()));
	}
	
	private void jComboBoxFieldTypeActionPerformed(ActionEvent evt) {
		String fieldType = (String) jComboBoxFieldType.getSelectedItem();
		if (FieldDefinition.FIELD_TYPE_STRING.equals(fieldType)) {
			jComboBoxSelectCodeType.setEnabled(false);
			jComboBoxSelectCodeType.setSelectedIndex(-1);
			jTextFieldLength.setEditable(true);
			jTextFieldPrecision.setEditable(false);
			jTextFieldScale.setEditable(false);
		} else if (FieldDefinition.FIELD_TYPE_INT.equals(fieldType)) {
			jComboBoxSelectCodeType.setEnabled(false);
			jComboBoxSelectCodeType.setSelectedIndex(-1);
			jTextFieldLength.setEditable(false);
			jTextFieldPrecision.setEditable(true);
			jTextFieldScale.setEditable(false);
		} else if (FieldDefinition.FIELD_TYPE_DOUBLE.equals(fieldType)) {
			jComboBoxSelectCodeType.setEnabled(false);
			jComboBoxSelectCodeType.setSelectedIndex(-1);
			jTextFieldLength.setEditable(false);
			jTextFieldPrecision.setEditable(true);
			jTextFieldScale.setEditable(true);
		} else if (FieldDefinition.FIELD_TYPE_TEXT.equals(fieldType)
				|| FieldDefinition.FIELD_TYPE_BYTES.equals(fieldType)
				|| FieldDefinition.FIELD_TYPE_DATE.equals(fieldType)
				|| FieldDefinition.FIELD_TYPE_DATETIME.equals(fieldType)
				|| FieldDefinition.FIELD_TYPE_TIME.equals(fieldType)
				|| FieldDefinition.FIELD_TYPE_MONTH.equals(fieldType)) {
			jComboBoxSelectCodeType.setEnabled(false);
			jComboBoxSelectCodeType.setSelectedIndex(-1);
			jTextFieldLength.setEditable(false);
			jTextFieldPrecision.setEditable(false);
			jTextFieldScale.setEditable(false);
		} else if (FieldDefinition.FIELD_TYPE_SELECTCODE.equals(fieldType)) {
			jComboBoxSelectCodeType.setEnabled(true);
			if (jComboBoxSelectCodeType.getModel().getSize() > 0) {
				jComboBoxSelectCodeType.setSelectedIndex(0);
			} else {
				jComboBoxSelectCodeType.setSelectedIndex(-1);
			}
			jTextFieldLength.setEditable(false);
			jTextFieldPrecision.setEditable(false);
			jTextFieldScale.setEditable(false);
		} else {
			jComboBoxSelectCodeType.setEnabled(false);
			jComboBoxSelectCodeType.setSelectedIndex(-1);
			jTextFieldLength.setEditable(false);
			jTextFieldPrecision.setEditable(false);
			jTextFieldScale.setEditable(false);
		}
	}

	public FieldDefinition getFieldDefinition() {
		return fieldDefinition;
	}

	public String getOldFieldName() {
		return oldFieldName;
	}

	public String getNewFieldName() {
		return newFieldName;
	}

}
