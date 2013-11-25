package com.growcontrol.gcClient.frames.Login;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

import com.growcontrol.gcClient.guiUtils;
import com.growcontrol.gcClient.frames.gcFrameInterface;
import com.growcontrol.gcCommon.pxnLogger.pxnLog;


public class LoginFrame extends JFrame implements gcFrameInterface {
	private static final long serialVersionUID = 1L;
	private static final String logName = LoginHandler.logName;
	private final LoginHandler handler;

	private static final String IMAGE_LOADING = "images/icon-loading-animated.gif";

	protected CardLayout cardLayout = new CardLayout();
	protected HashMap<String, JPanel> panels = new HashMap<String, JPanel>();

//	public static final String LOGIN_WINDOW_NAME = "login";
//	public static final String CONNECTING_WINDOW_NAME = "connecting";
//	protected KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
//	protected InputMap inputMap;

//	protected JPanel panelLogin = new JPanel();
//	protected JPanel panelConnecting = new JPanel();

	// input fields
	protected JTextField textHost;
	protected JTextField textPort;
	protected JTextField textUsername;
	protected JPasswordField textPassword;
	// button
	protected JButton buttonConnect = null;


	public LoginFrame(LoginHandler handler) {
		super("Connect to server..");
		this.handler = handler;
//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		enableEvents(java.awt.AWTEvent.WINDOW_EVENT_MASK);
		setResizable(false);
		setLayout(cardLayout);
		// key listener
		KeyboardFocusManager keyboard = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		keyboard.addKeyEventDispatcher(handler);
		// create panels
		createLoginPanel();
		createConnectingPanel();
		// create cards
		for(Entry<String, JPanel> entry : panels.entrySet())
			add(entry.getValue(), entry.getKey());
//		add(panelLogin, LOGIN_WINDOW_NAME);
//		add(panelConnecting, CONNECTING_WINDOW_NAME);
		pack();
		// set window width
		setSize(280, getHeight());
		// display window
		setLocationRelativeTo(null);
		setVisible(true);
	}


	// close event
	protected void processWindowEvent(WindowEvent event) {
		if(event.getID() == WindowEvent.WINDOW_CLOSING) {
			handler.Close();
		}
	}


	// new card panel
	private JPanel newPanel(LoginHandler.CONN window) {
		if(panels.containsKey(window)) {
			return panels.get(window);
		} else {
			JPanel panel = new JPanel();
			panels.put(window.toString(), panel);
			return panel;
		}
	}


	// login panel
	private void createLoginPanel() {
		JPanel panel = newPanel(LoginHandler.CONN.WAITING);
		MigLayout migLayout = new MigLayout();
		migLayout.setLayoutConstraints("");
		migLayout.setColumnConstraints("[]10[]");
		migLayout.setRowConstraints   ("[]20[]");
		panel.setLayout(migLayout);

//		// separator - saved servers
//		JLabel labelServersList = new JLabel("Saved Servers");
//		labelServersList.setToolTipText("<html>" +
//				"</html>");
//		panelLogin.add(labelServersList, "split 2, span");
//		JSeparator separatorServersList = new JSeparator();
//		separatorServersList.setPreferredSize(new Dimension(200, 2));
//		panelLogin.add(separatorServersList, "growx, wrap");
		// servers list
		JComboBox<String> comboSavedServers = new JComboBox<String>();
		handler.populateSavedServers(comboSavedServers);
//comboSavedServers.setEnabled(false);
		panel.add(comboSavedServers, "growx, span 2, gapleft 10, gapright 10, center, wrap");
		// item listener
		comboSavedServers.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() != ItemEvent.SELECTED)
					return;
				String selectedStr = event.getItem().toString();
				handler.ComboChanged(event, selectedStr);
			}
		});
		// separator - Server Address
		JLabel labelLocation = new JLabel("Server Location");
		labelLocation.setToolTipText("<html>" +
				"The address for your Grow Control Server. This can<br>" +
				"be an IP address or a hostname.<br>" +
				"Example:<br>" +
				"192.168.1.120  or<br>" +
				"gcserver.mydomain.com" +
				"</html>");
		panel.add(labelLocation, "split 2, span");
		JSeparator separatorLocation = new JSeparator();
//		separatorLocation.setPreferredSize(new Dimension(200, 2));
		panel.add(separatorLocation, "growx, wrap");
		// hostname / ip
		JLabel labelHost = new JLabel("Hostname/IP:");
		panel.add(labelHost, "");
		textHost = new JTextField();
		textHost.setText("127.0.0.1");
		panel.add(textHost, "growx, wrap");
		// port
		JLabel labelPort = new JLabel("Port:");
		panel.add(labelPort, "");
		textPort = new JTextField();
		textPort.setText("1142");
		panel.add(textPort, "growx, wrap");

		// separator - Username / Password
		JLabel labelLogin = new JLabel("Login");
		labelLogin.setToolTipText("<html>" +
				"</html>");
		panel.add(labelLogin, "split 2, span");
		JSeparator separatorLogin = new JSeparator();
		separatorLogin.setPreferredSize(new Dimension(200, 2));
		panel.add(separatorLogin, "growx, wrap");
		// username
		JLabel labelUsername = new JLabel("Username:");
		panel.add(labelUsername, "");
		textUsername = new JTextField();
		panel.add(textUsername, "growx, wrap");
		// password
		JLabel labelPassword = new JLabel("Password:");
		panel.add(labelPassword, "");
		textPassword = new JPasswordField();
		panel.add(textPassword, "growx, wrap");

		// connect button
		buttonConnect = new JButton("Connect");
		buttonConnect.setDefaultCapable(true);
		panel.add(buttonConnect, "span 2, center, gapbottom 100px, tag ok");
		// action listener
		buttonConnect.addActionListener(new ActionListener() {
//TODO:
//btn.setOnAction(new EventHandler<ActionEvent>() {
//@Override
//public void handle(ActionEvent event) {
//System.out.println("Hello World!");
//}
//}); 
			@Override
			public void actionPerformed(ActionEvent event) {
				Object obj = event.getSource();
				if(!(obj instanceof JButton))
					return;
				String buttonName = ((JButton) obj).getActionCommand();
				handler.ButtonClicked(event, buttonName);
			}
		});
	}
	// set field values
	public void setHost(String host) {
		textHost.setText(host);
	}
	public void setPort(int port) {
		textPort.setText(Integer.toString(port));
	}
	public void setUsername(String user) {
		textUsername.setText(user);
	}
	public void setPassword(String pass) {
		textPassword.setText(pass);
	}
	// set button text
	public void setButtonName(String name) {
		if(!SwingUtilities.isEventDispatchThread()) throw new ConcurrentModificationException("Cannot call this function directly!");
		buttonConnect.setName(name);
	}


	// connecting.. panel
	protected JLabel labelStatus = new JLabel();
	public void setMessage(String message) {
		if(!SwingUtilities.isEventDispatchThread()) throw new ConcurrentModificationException("Cannot call this function directly!");
		this.labelStatus.setText(message);
	}
	private void createConnectingPanel() {
		JPanel panel = newPanel(LoginHandler.CONN.CONNECT);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.setBackground(Color.DARK_GRAY);
		ImageIcon loading = guiUtils.loadImageResource(IMAGE_LOADING);
		JLabel labelAnimation = new JLabel();
		labelAnimation.setIcon(loading);
		panel.add(labelAnimation);
		// cancel button
		JButton buttonCancel = new JButton("Cancel");
		panel.add(buttonCancel);
		// action listener
		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Object obj = event.getSource();
				if(!(obj instanceof JButton))
					return;
				String buttonName = ((JButton) obj).getActionCommand();
				handler.ButtonClicked(event, buttonName);
			}
		});
		// status
		labelStatus.setHorizontalAlignment(SwingConstants.CENTER);
		labelStatus.setPreferredSize(new Dimension(180, 35));
		labelStatus.setForeground(Color.WHITE);
		panel.add(labelStatus);
//		// progress bar
//		JProgressBar progress = new JProgressBar();
//		label.add(progress);
	}


	public void DisplayCard(LoginHandler.CONN display) {
		if(!SwingUtilities.isEventDispatchThread()) throw new ConcurrentModificationException("Cannot call this function directly!");
		if(display == null) throw new NullPointerException("displayCard can't be null");
		pxnLog.get(logName).debug("Displaying card: "+display.toString());
		try {
			cardLayout.show(this.getContentPane(), display.toString());
//			cardLayout.show(panelConnecting.getParent(), cardName);
		} catch(Exception ignore) {}
	}


}
