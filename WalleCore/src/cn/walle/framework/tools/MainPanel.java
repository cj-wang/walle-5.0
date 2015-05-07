package cn.walle.framework.tools;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import cn.walle.framework.core.support.SystemConfig;
import cn.walle.framework.core.util.ContextUtils;
import cn.walle.framework.tools.base.TextAreaPrintStream;

public class MainPanel extends cn.walle.framework.tools.base.BasePanel {
	
	private JPanel jPanelMain;
	private JPanel jPanelButtons;
	private JPanel jPanelMenu;
	private JButton jButtonRefresh;
	private JPanel jPanelOperate;
	private JSplitPane jSplitPaneMain;
	private JSplitPane jSplitPaneOperate;
	private JScrollPane jScrollPaneTreeMenu;
	private JScrollPane jScrollPaneMsg;
	private JTextArea jTextAreaMsg;

	private MainMenu mainMenu;
	
	private PrintStream stdSysOut = System.out;
	private PrintStream stdSysErr = System.err;

	public MainPanel() {
		initGUI();
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(900, 650));
			this.setTitle("Walle Tools - " + SystemConfig.PROJECT_NAME);
			this.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent evt) {
					rootComponentResized(evt);
				}
			});
			{
				jPanelMain = new JPanel();
				BorderLayout jPanelMainLayout = new BorderLayout();
				this.add(jPanelMain, BorderLayout.CENTER);
				jPanelMain.setLayout(jPanelMainLayout);
				{
					jSplitPaneMain = new JSplitPane();
					{
						jSplitPaneOperate = new JSplitPane();
						jSplitPaneMain.add(jSplitPaneOperate, JSplitPane.TOP);
						jSplitPaneOperate.setDividerLocation(300);
						{
							jPanelOperate = new JPanel();
							BorderLayout jPanelOperateLayout = new BorderLayout();
							jPanelOperate.setLayout(jPanelOperateLayout);
							jSplitPaneOperate.add(jPanelOperate,
									JSplitPane.RIGHT);
						}
						{
							jPanelMenu = new JPanel();
							BorderLayout jPanelMenuLayout = new BorderLayout();
							jSplitPaneOperate.add(jPanelMenu, JSplitPane.LEFT);
							jPanelMenu.setLayout(jPanelMenuLayout);
							{
								jPanelButtons = new JPanel();
								jPanelMenu.add(jPanelButtons, BorderLayout.NORTH);
								FlowLayout jPanelButtonsLayout = new FlowLayout();
								jPanelButtonsLayout
									.setAlignment(FlowLayout.LEFT);
								jPanelButtons.setLayout(jPanelButtonsLayout);
								{
									jButtonRefresh = new JButton();
									jPanelButtons.add(jButtonRefresh);
									jButtonRefresh.setText("Refresh");
									jButtonRefresh
										.addActionListener(new ActionListener() {
											public void actionPerformed(
												ActionEvent evt) {
												jButtonRefreshActionPerformed(evt);
											}
										});
								}
							}
							{
								jScrollPaneTreeMenu = new JScrollPane();
								jPanelMenu.add(jScrollPaneTreeMenu, BorderLayout.CENTER);
								{
									mainMenu = new MainMenu();
									jScrollPaneTreeMenu
										.setViewportView(mainMenu);
								}
							}
						}
					}
					jPanelMain.add(jSplitPaneMain, BorderLayout.CENTER);
					jSplitPaneMain.setOrientation(JSplitPane.VERTICAL_SPLIT);
				}
				{
					jScrollPaneMsg = new JScrollPane();
					jSplitPaneMain.add(jScrollPaneMsg, JSplitPane.BOTTOM);
					{
						jTextAreaMsg = new JTextArea();
						jScrollPaneMsg.setViewportView(jTextAreaMsg);
						jTextAreaMsg.setEditable(false);
						jTextAreaMsg.setTabSize(4);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean init() {
		TextAreaPrintStream sysout = new TextAreaPrintStream(jTextAreaMsg);
		System.setOut(sysout);
		System.setErr(sysout);

		mainMenu.setOperatePanel(jPanelOperate);
		mainMenu.setPathFileName(SystemConfig.USER_HOME_DIR + "/." + SystemConfig.PROJECT_NAME
				+ "/ToolsMainMenuPath");
		mainMenu.requestFocus();
		
		new Thread() {
			public void run() {
				Log log = LogFactory.getLog(MainPanel.class);
				log.info("Initializing Spring ApplicationContext...");
				
				String contextConfigLocation = null;
				try {
					File webXmlFile = new File(SystemConfig.WEB_DIR + "/WEB-INF/web.xml");
					SAXBuilder builder = new SAXBuilder();
					Namespace namespace = Namespace.getNamespace("http://java.sun.com/xml/ns/j2ee");
					Element webInfRootElement = builder.build(webXmlFile).getRootElement();
					List<Element> contextParamElements = webInfRootElement.getChildren("context-param", namespace);
					for (Element contextParamElement : contextParamElements) {
						if ("contextConfigLocation".equals(contextParamElement.getChildTextTrim("param-name", namespace))) {
							contextConfigLocation = contextParamElement.getChildTextTrim("param-value", namespace);
							break;
						}
					}
					if (contextConfigLocation == null) {
						namespace = Namespace.getNamespace("http://java.sun.com/xml/ns/javaee");
						webInfRootElement = builder.build(webXmlFile).getRootElement();
						contextParamElements = webInfRootElement.getChildren("context-param", namespace);
						for (Element contextParamElement : contextParamElements) {
							if ("contextConfigLocation".equals(contextParamElement.getChildTextTrim("param-name", namespace))) {
								contextConfigLocation = contextParamElement.getChildTextTrim("param-value", namespace);
								break;
							}
						}
					}
				} catch (Exception ex) {
					log.error("Error getting contextConfigLocation", ex);
				}

				if (contextConfigLocation == null) {
					log.error("contextConfigLocation not found, use default classpath:/applicationContext*.xml");
					contextConfigLocation = "classpath:/applicationContext*.xml";
				}
				
				ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
						StringUtils.tokenizeToStringArray(contextConfigLocation, ",; \t\n"));
				applicationContext.registerShutdownHook();
				
				log.info("Spring ApplicationContext initialized");
			}
		}.start();
		
		new Thread() {
			public void run() {
				try {
					mainMenu.loadTreePath();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}.start();
		
		return true;
	}

	public boolean close() {
		System.setOut(stdSysOut);
		System.setErr(stdSysErr);

		try {
			mainMenu.saveTreePath();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}
	
	private void jButtonRefreshActionPerformed(ActionEvent evt) {
		new Thread() {
			public void run() {
				try {
					synchronized (MainPanel.class) {
						Log log = LogFactory.getLog(MainPanel.class);
						log.info("Restarting Spring ApplicationContext...");
						ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) ContextUtils.getApplicationContext();
						ContextUtils.setApplicationContext(null);
						applicationContext.refresh();
						ContextUtils.setApplicationContext(applicationContext);
						log.info("Spring ApplicationContext restarted");

						mainMenu.saveTreePath();
						mainMenu.refreshPanels();
						mainMenu.refresh();
						mainMenu.loadTreePath();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}.start();
	}
	
	private void rootComponentResized(ComponentEvent evt) {
		jSplitPaneMain.setDividerLocation(this.getHeight() - 200);
	}

}


