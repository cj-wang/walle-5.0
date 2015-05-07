package cn.walle.framework.tools.testclient;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.FileToDownload;
import cn.walle.framework.core.support.SystemConfig;
import cn.walle.framework.core.support.springsecurity.AcegiDefaultUserDetailsService;
import cn.walle.framework.core.support.springsecurity.SessionContext;
import cn.walle.framework.core.util.ContextUtils;
import cn.walle.framework.core.util.JSONDataUtils;
import cn.walle.framework.tools.util.UIUtils;

public class TestClientPanel extends cn.walle.framework.tools.base.BasePanel {

	protected final Log log = LogFactory.getLog(getClass());
	private JLabel jLabelUser;
	private JTextField jTextFieldUser;
	private JLabel jLabelLocale;
	private JComboBox jComboBoxLocale;
	private JButton jButtonLogin;

	private JPanel jPanelMain;
	private JTextArea jTextAreaResult;
	private JTextArea jTextAreaParameters;
	private JScrollPane jScrollPaneResult;
	private JScrollPane jScrollPaneParameters;
	private JPanel jPanelResult;
	private JPanel jPanelParameters;
	private JTabbedPane jTabbedPane;
	private JPanel jPanelXml;
	private JPanel jPanelButtons;
	private JButton jButtonInvoke;
	private JButton jButtonFormat;
	
	private Class<? extends BaseManager> interfaceClass;
	private Method method;
	private String[] parameterNames;
	
	private File loginUserFile = new File(SystemConfig.USER_HOME_DIR + "/." + SystemConfig.PROJECT_NAME + "/ToolsLoginUser");

	public TestClientPanel() {
		initGUI();
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(517, 223));
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
						jButtonInvoke = new JButton();
						jPanelButtons.add(jButtonInvoke);
						jButtonInvoke.setText("Invoke");
						jButtonInvoke.setMnemonic(java.awt.event.KeyEvent.VK_I);
						jButtonInvoke.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jButtonInvokeActionPerformed(evt);
							}
						});
					}
					{
						jButtonFormat = new JButton();
						jPanelButtons.add(jButtonFormat);
						jButtonFormat.setText("Format");
						jButtonFormat.setMnemonic(java.awt.event.KeyEvent.VK_F);
						jButtonFormat.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jButtonFormatActionPerformed(evt);
							}
						});
					}
					{
						jLabelUser = new JLabel();
						jPanelButtons.add(jLabelUser);
						jLabelUser.setText("User:");
					}
					{
						jTextFieldUser = new JTextField();
						jPanelButtons.add(jTextFieldUser);
						jTextFieldUser.setText("admin");
						jTextFieldUser.setColumns(10);
					}
					{
						jButtonLogin = new JButton();
						jPanelButtons.add(jButtonLogin);
						jButtonLogin.setText("Login");
						jButtonLogin.setMnemonic(java.awt.event.KeyEvent.VK_L);
						jButtonLogin.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jButtonLoginActionPerformed(evt);
							}
						});
					}
					{
						jLabelLocale = new JLabel();
						jPanelButtons.add(jLabelLocale);
						jLabelLocale.setText("Locale:");
					}
					{
						jComboBoxLocale = new JComboBox();
						jPanelButtons.add(jComboBoxLocale);
						jComboBoxLocale.setPreferredSize(new java.awt.Dimension(100, 20));
					}
				}
				{
					jPanelXml = new JPanel();
					BorderLayout jPanelXmlLayout = new BorderLayout();
					jPanelXml.setLayout(jPanelXmlLayout);
					jPanelMain.add(jPanelXml, BorderLayout.CENTER);
					{
						jTabbedPane = new JTabbedPane();
						jPanelXml.add(jTabbedPane, BorderLayout.CENTER);
						{
							jPanelParameters = new JPanel();
							BorderLayout jPanelParametersLayout = new BorderLayout();
							jTabbedPane.addTab(
								"Parameters",
								null,
								jPanelParameters,
								null);
							jPanelParameters.setLayout(jPanelParametersLayout);
							{
								jScrollPaneParameters = new JScrollPane();
								jPanelParameters.add(
									jScrollPaneParameters,
									BorderLayout.CENTER);
								{
									jTextAreaParameters = new JTextArea();
									jTextAreaParameters.setTabSize(2);
									jScrollPaneParameters
										.setViewportView(jTextAreaParameters);
								}
							}
						}
						{
							jPanelResult = new JPanel();
							BorderLayout jPanelResultLayout = new BorderLayout();
							jTabbedPane.addTab(
								"Result",
								null,
								jPanelResult,
								null);
							jPanelResult.setLayout(jPanelResultLayout);
							{
								jScrollPaneResult = new JScrollPane();
								jPanelResult.add(
									jScrollPaneResult,
									BorderLayout.CENTER);
								{
									jTextAreaResult = new JTextArea();
									jTextAreaResult.setTabSize(2);
									jScrollPaneResult
										.setViewportView(jTextAreaResult);
								}
							}
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
		jButtonInvoke.setEnabled(enabled);
		if (enabled) {
			jButtonInvoke.requestFocus();
		} else {
			jTextAreaParameters.requestFocus();
		}
	}
	
	public void requestFocus() {
		super.requestFocus();
		jButtonInvoke.requestFocus();
	}
	
	public void setParameters(Object... parameters) {
		if (parameters.length == 3
				&& parameters[0] instanceof Class
				&& parameters[1] instanceof Method
				&& parameters[2] instanceof String[]) {
			super.setParameters(parameters);
			interfaceClass = (Class<? extends BaseManager>) parameters[0];
			method = (Method) parameters[1];
			parameterNames = (String[]) parameters[2];
			
		} else {
			throw new IllegalArgumentException("Parameters for TestClientPanel must be of type Object[]{Class<?>, Method, String[]}");
		}
	}
	
	public boolean init() {
		new Thread() {
			public void run() {
				try {
					if (loginUserFile.exists()) {
						jTextFieldUser.setText(FileUtils.readFileToString(loginUserFile));
					}

					Map<String, Locale> locales = new TreeMap<String, Locale>();
					for (Locale locale : Locale.getAvailableLocales()) {
						locales.put(locale.toString(), locale);
					}
					ComboBoxModel jComboBoxLocaleModel = new DefaultComboBoxModel(locales.values().toArray());
					jComboBoxLocale.setModel(jComboBoxLocaleModel);
					
					jComboBoxLocale.setSelectedItem(Locale.getDefault());
				} catch (Exception ex) {
					log.error("Test client init error", ex);
					UIUtils.showError("Test client init error", ex);
				}
			}
		}.start();
		return true;
	}

	public boolean refresh() {
		try {
//			Element element = XmlDataUtils.buildParametersXmlElement(interfaceClass, method, parameterNames);
//			Format format = Format.getPrettyFormat();
//			format.setExpandEmptyElements(true);
//			format.setIndent("\t");
//			XMLOutputter outputter = new XMLOutputter(format);
//			StringWriter stringWriter = new StringWriter();
//			outputter.output(element, stringWriter);
//			String requestData = stringWriter.toString();

			String requestData = JSONDataUtils.buildParametersJSONString(interfaceClass, method, parameterNames);

			jTextAreaParameters.setText(requestData);
			jTextAreaParameters.setCaretPosition(0);
			jTextAreaResult.setText(null);
			jTextAreaResult.setCaretPosition(0);
			jTabbedPane.setSelectedIndex(0);
		} catch (Exception ex) {
			log.error("Build request xml error", ex);
			UIUtils.showError("Build request xml error", ex);
		}
		return true;
	}

	private void login() throws Exception {
		String userName = jTextFieldUser.getText();
		UserDetailsService userDetailsService = ContextUtils.getBeanOfType(AcegiDefaultUserDetailsService.class);
		UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
		Authentication authentication = new TestingAuthenticationToken(userDetails, null);

		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		log.info("Login as user " + userName);

		FileUtils.writeStringToFile(loginUserFile, userName);
	}
	
	private void formatRequestData() throws Exception {
		try {
			jTextAreaParameters.setText(JSONDataUtils.format(jTextAreaParameters.getText()));
			jTextAreaParameters.setCaretPosition(0);
		} catch (JSONException jex) {
			try {
				String msg = jex.getMessage();
				int errorPosition = Integer.valueOf(msg.substring(msg.lastIndexOf(" ") + 1));
				jTextAreaParameters.select(errorPosition - 1, errorPosition);
				jTextAreaParameters.requestFocus();
			} catch (Exception ex) {
			}
			throw jex;
		}
	}
	
	private void jButtonInvokeActionPerformed(ActionEvent evt) {
		setEnabled(false);
		new Thread() {
			public void run() {
				try {
					formatRequestData();
					
					if (SessionContext.getUser() == null) {
						login();
					}
					if (! SessionContext.getUser().getUsername().equals(jTextFieldUser.getText())) {
						login();
					}
					
					LocaleContextHolder.setLocale((Locale) jComboBoxLocale.getSelectedItem());
					
					BaseManager bean = ContextUtils.getBeanOfType(interfaceClass);
					
//					SAXBuilder builder = new SAXBuilder();
//					Element parametersElement = builder.build(new StringReader(jTextAreaParameters.getText())).getRootElement();
//					Object[] parameters = XmlDataUtils.parseParametersXmlElement(interfaceClass, method, parametersElement);
					
					Object[] parameters = JSONDataUtils.parseParametersJSONString(interfaceClass, method, jTextAreaParameters.getText());
					
					Object result = method.invoke(bean, parameters);
					
					if (result instanceof FileToDownload) {
						FileToDownload fileToDownload = (FileToDownload) result;
						String fileName = System.getProperty("java.io.tmpdir") + "/" + fileToDownload.getFileName();
						FileOutputStream fos = new FileOutputStream(fileName);
						IOUtils.copy(fileToDownload.getContent(), fos);
						fos.close();
						Runtime.getRuntime().exec("cmd /E:ON /c start " + fileName);
					}
					
//					Element resultElement = XmlDataUtils.buildXmlElement("Result", result);
//					Format format = Format.getPrettyFormat();
//					format.setIndent("\t");
//					XMLOutputter outputter = new XMLOutputter(format);
//					StringWriter stringWriter = new StringWriter();
//					outputter.output(resultElement, stringWriter);
//					String resultData = stringWriter.toString();
					
					String resultData = JSONDataUtils.buildJSONString("result", result, true);
					
					jTextAreaResult.setText(resultData);
					jTabbedPane.setSelectedIndex(1);

				} catch (InvocationTargetException itex) {
					Throwable cause = ExceptionUtils.getRootCause(itex);
					log.error("Invoke error", cause);
					UIUtils.showError("Invoke error", cause);
				} catch (Exception ex) {
					log.error("Test Client error", ex);
					UIUtils.showError("Test Client error", ex);
				}
				setEnabled(true);
			}
		}.start();
	}

	private void jButtonFormatActionPerformed(ActionEvent evt) {
		try {
			formatRequestData();
		} catch (Exception ex) {
			log.error("Test Client error", ex);
			UIUtils.showError("Test Client error", ex);
		}
	}
	
	private void jButtonLoginActionPerformed(ActionEvent evt) {
		try {
			login();
		} catch (Exception ex) {
			log.error("Login error", ex);
			UIUtils.showError("Login error", ex);
		}
	}
	
}
