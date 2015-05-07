package cn.walle.framework.tools.generator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class PageGeneratorPanel extends cn.walle.framework.tools.base.BasePanel {
	private JPanel jPanelTop;
	private JSplitPane jSplitPaneCenter;
	private JScrollPane jScrollPaneCondition;
	private JTable jTableResultItem;
	private JPanel jPanelResultItem;
	private JComboBox jComboBoxQueryType;
	private JLabel jLabelQueryType;
	private JPanel jPanelCondtion;
	private JTable jTableCondtion;
	private JScrollPane jScrollPaneResultItem;
	private JPanel jPanelBottum;

	public PageGeneratorPanel() {
		initGUI();
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			{
				jPanelTop = new JPanel();
				FlowLayout jPanelTopLayout = new FlowLayout();
				jPanelTopLayout.setAlignment(FlowLayout.LEFT);
				this.add(jPanelTop, BorderLayout.NORTH);
				jPanelTop.setLayout(jPanelTopLayout);
				{
					jLabelQueryType = new JLabel();
					jPanelTop.add(jLabelQueryType);
					jLabelQueryType.setText("Query type:");
				}
				{
					ComboBoxModel jComboBoxQueryTypeModel = new DefaultComboBoxModel(
						new String[] { "Item One", "Item Two" });
					jComboBoxQueryType = new JComboBox();
					jPanelTop.add(jComboBoxQueryType);
					jComboBoxQueryType.setModel(jComboBoxQueryTypeModel);
					jComboBoxQueryType.setEditable(true);
					jComboBoxQueryType.setPreferredSize(new java.awt.Dimension(200, 20));
				}
			}
			{
				jSplitPaneCenter = new JSplitPane();
				this.add(jSplitPaneCenter, BorderLayout.CENTER);
				{
					jPanelCondtion = new JPanel();
					jSplitPaneCenter.add(jPanelCondtion, JSplitPane.LEFT);
					BorderLayout jPanelCondtionLayout = new BorderLayout();
					jPanelCondtion.setLayout(jPanelCondtionLayout);
					jPanelCondtion.setBorder(BorderFactory.createTitledBorder("Condtion fields"));
					{
						jScrollPaneCondition = new JScrollPane();
						jPanelCondtion.add(jScrollPaneCondition, BorderLayout.CENTER);
						{
							TableModel jTableCondtionModel = new DefaultTableModel(
								new String[][] { { "One", "Two" },
										{ "Three", "Four" } },
								new String[] { "Column 1", "Column 2" });
							jTableCondtion = new JTable();
							jScrollPaneCondition
								.setViewportView(jTableCondtion);
							jTableCondtion.setModel(jTableCondtionModel);
						}
					}
				}
				{
					jPanelResultItem = new JPanel();
					BorderLayout jPanelResultItemLayout = new BorderLayout();
					jSplitPaneCenter.add(jPanelResultItem, JSplitPane.RIGHT);
					jPanelResultItem.setLayout(jPanelResultItemLayout);
					jPanelResultItem.setBorder(BorderFactory.createTitledBorder("Result item fields"));
					{
						jScrollPaneResultItem = new JScrollPane();
						jPanelResultItem.add(jScrollPaneResultItem, BorderLayout.CENTER);
						{
							TableModel jTableResultItemModel = new DefaultTableModel(
								new String[][] { { "One", "Two" },
										{ "Three", "Four" } },
								new String[] { "Column 1", "Column 2" });
							jTableResultItem = new JTable();
							jScrollPaneResultItem
								.setViewportView(jTableResultItem);
							jTableResultItem.setModel(jTableResultItemModel);
						}
					}
				}
			}
			{
				jPanelBottum = new JPanel();
				this.add(jPanelBottum, BorderLayout.SOUTH);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
