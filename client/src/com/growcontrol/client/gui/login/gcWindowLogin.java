package com.growcontrol.client.gui.login;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.growcontrol.client.gcClientDefines;
import com.growcontrol.client.gcClientVars;
import com.growcontrol.client.configs.ProfilesConfig;
import com.growcontrol.client.configs.SavedProfileConfig;
import com.growcontrol.client.gui.guiManager;
import com.growcontrol.common.gcDefines;
import com.poixson.commonapp.gui.guiUtils;
import com.poixson.commonapp.gui.xFont;
import com.poixson.commonapp.gui.xWindow;
import com.poixson.commonapp.gui.annotations.xWindowProperties;
import com.poixson.commonapp.gui.remapped.RemappedActionListener;
import com.poixson.commonapp.gui.remapped.RemappedItemListener;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.utilsNumbers;


@xWindowProperties(
		resizable = false
)
public class gcWindowLogin extends xWindow {
	private static final long serialVersionUID = 1L;
	private static final boolean DEBUGGING = false;


	private static final String WINDOW_TITLE = "Connect to..";
	private static final int WINDOW_WIDTH = 320;

	private static final String IMAGE_LOADING = "images/icon-loading-animated.gif";

	// saved profiles
	protected final Map<String, SavedProfileConfig> profiles = new LinkedHashMap<String, SavedProfileConfig>();

	// card panels
	protected final CardLayout cardLayout;
	protected volatile String currentCard = null;
	protected static final String CARD_LOGIN      = "login";
	protected static final String CARD_CONNECTING = "connecting";

	// login form
	protected JComboBox<String> lstProfiles = null;
	protected JTextField        txtboxHost  = null;
	protected JTextField        txtboxPort  = null;
	protected JTextField        txtboxUser  = null;
	protected JPasswordField    txtboxPass  = null;
	protected JButton           btnConnect  = null;
	// profiles menu
	protected JPopupMenu        menuProfile = null;
	protected JButton           btnMenu     = null;

	// connecting animation
	protected JLabel       txtStatus = null;
	protected JProgressBar progress  = null;
	protected JButton      btnCancel = null;

	// socket
//	protected volatile xSocket socket = null;



	// new window instance
	public gcWindowLogin() throws HeadlessException {
		super();
		this.setTitle(WINDOW_TITLE);
		this.cardLayout = new CardLayout();
		this.setLayout(this.cardLayout);
		// login form
		this.add(
			this.initFrame_login(),
			CARD_LOGIN
		);
		// connecting animation
		this.add(
			this.initFrame_connecting(),
			CARD_CONNECTING
		);
		// resize and prepare
		this.cardLayout.show(this.getContentPane(), CARD_LOGIN);
		this.currentCard = CARD_LOGIN;
		this.autoHeight(WINDOW_WIDTH);
		this.setLocationRelativeTo(null);
		// load profiles.yml
		this.loadProfilesConfig();
		// show window
		this.Show();
		this.registerCloseHook();
	}



	private JPanel initFrame_login() {
		// login panel
		final JPanel panel = new JPanel();
		{
			final MigLayout migLayout = new MigLayout(
				(DEBUGGING ? "debug" : ""),
				"10[]10[]10",
				"15[]15[]10"
			);
			panel.setLayout(migLayout);
		}
		// fonts
		final xFont labelFont   = new xFont("bold");
		final xFont textboxFont = new xFont("fam:Arial,size=+2");
		final Insets textboxInsets = new Insets(2, 2, 2, 2);
		// top panel
		{
			final JPanel pan = new JPanel();
			pan.setLayout(new BorderLayout());
			// profiles dropdown list
			{
				this.lstProfiles = new JComboBox<String>();
				// event listener
				this.lstProfiles.addItemListener(
						RemappedItemListener.get(
								this,
								"onListClickEvent"
						)
				);
				pan.add(this.lstProfiles, BorderLayout.CENTER);
			}
			// >> profiles menu button
			{
				this.btnMenu = new JButton(">>");
				this.btnMenu.setEnabled(false);
				this.btnMenu.setFocusable(false);
				this.btnMenu.setMargin(new Insets(0, 3, 0, 3));
				// menu
				this.menuProfile = new JPopupMenu();
				// new profile
				final JMenuItem itemNew = new JMenuItem("New Profile..");
				this.menuProfile.add(itemNew);
				// save profile
				final JMenuItem itemSave = new JMenuItem("Save Profile..");
				this.menuProfile.add(itemSave);
				// delete profile
				final JMenuItem itemDel = new JMenuItem("Remove Profile..");
				this.menuProfile.add(itemDel);
				// event listener
				this.btnMenu.addActionListener(
						RemappedActionListener.get(
								this,
								"onClickProfileMenuButton"
						)
				);
				pan.add(this.btnMenu, BorderLayout.EAST);
			}
			panel.add(pan, "span, growx, gapleft 10px, gapright 10px, wrap");
		}
		// Server Location ----------
		{
			final JLabel label = new JLabel("Server Location");
			label.setToolTipText("<html>" +
				"The address for your Grow Control Server. This can<br>" +
				"be an IP address or a hostname.<br>" +
				"Example:<br>" +
				"192.168.1.120  or<br>" +
				"gcserver.mydomain.com" +
				"</html>");
			label.setFont(labelFont.getFont());
			panel.add(label, "span, split 2");
			final JSeparator separator = new JSeparator();
			separator.setPreferredSize(new Dimension(200, 2));
			panel.add(separator, "growx, wrap");
		}
		// host / ip
		{
			final JLabel label = new JLabel("Host / IP:");
			label.setFont(labelFont.getFont());
			panel.add(label, "");
			this.txtboxHost = new JTextField();
			this.txtboxHost.setFont(textboxFont.getFont());
			this.txtboxHost.setMargin(textboxInsets);
			panel.add(this.txtboxHost, "growx, wrap");
		}
		// port
		{
			final JLabel label = new JLabel("Port:");
			label.setFont(labelFont.getFont());
			panel.add(label, "");
			this.txtboxPort = new JTextField();
			this.txtboxPort.setFont(textboxFont.getFont());
			this.txtboxPort.setMargin(textboxInsets);
			panel.add(this.txtboxPort, "growx, wrap");
		}
		// use ssl/tls
		{
			final JPanel pan = new JPanel();
			final JCheckBox checkbox = new JCheckBox();
			checkbox.setEnabled(false);
			pan.add(checkbox);
			final JLabel label = new JLabel("SSL/TLS");
			label.setEnabled(false);
			pan.add(label);
			panel.add(pan, "span, growx, gaptop -50px, wrap");
		}
		// Login ----------
		{
			final JLabel label = new JLabel("Login");
			label.setFont(labelFont.getFont());
			panel.add(label, "span, split 2");
			final JSeparator separator = new JSeparator();
			separator.setPreferredSize(new Dimension(200, 2));
			panel.add(separator, "growx, wrap");
		}
		// username
		{
			final JLabel label = new JLabel("Username:");
			label.setFont(labelFont.getFont());
			panel.add(label, "");
			this.txtboxUser = new JTextField();
			this.txtboxUser.setFont(textboxFont.getFont());
			this.txtboxUser.setMargin(textboxInsets);
			panel.add(this.txtboxUser, "growx, wrap");
		}
		// password
		{
			final JLabel label = new JLabel("Password:");
			label.setFont(labelFont.getFont());
			panel.add(label, "");
			this.txtboxPass = new JPasswordField();
			this.txtboxPass.setFont(textboxFont.getFont());
			this.txtboxPass.setMargin(textboxInsets);
			panel.add(this.txtboxPass, "growx, wrap");
		}
		// ----------
		{
			final JSeparator separator = new JSeparator();
			separator.setPreferredSize(new Dimension(400, 2));
			panel.add(separator, "span, growx, wrap");
		}
		// auto connect
		{
			final JPanel pan = new JPanel();
			final JCheckBox checkbox = new JCheckBox();
			checkbox.setEnabled(false);
			pan.add(checkbox);
			final JLabel label = new JLabel("Auto-Connect");
			label.setEnabled(false);
			pan.add(label);
			panel.add(pan, "span, growx, gaptop -50px, wrap");
		}
		// connect button
		{
			this.btnConnect = new JButton("Connect");
			this.btnConnect.setDefaultCapable(true);
			panel.add(this.btnConnect, "span, growx, gapleft 10px, gapright 10px, tag ok, wrap");
			// event listener
			this.btnConnect.addActionListener(
					RemappedActionListener.get(
							this,
							"onClickConnectButton"
					)
			);
		}
		return panel;
	}



	private JPanel initFrame_connecting() {
		// connecting panel
		final JPanel panel = new JPanel();
		{
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBackground(java.awt.Color.DARK_GRAY);
		}
		// fonts
		final xFont labelFont = new xFont("bold,size=+2");
		// animation
		{
			final ImageIcon image = guiUtils.loadImageResource(IMAGE_LOADING);
			final JLabel animation = new JLabel();
			animation.setIcon(image);
			animation.setAlignmentX(Component.CENTER_ALIGNMENT);
			panel.add(animation);
		}
		// status
		{
			this.txtStatus = new JLabel();
//			this.txtStatus.setPreferredSize(new Dimension(180, 35));
			this.txtStatus.setForeground(java.awt.Color.WHITE);
			this.txtStatus.setFont(labelFont.getFont());
			this.txtStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
			panel.add(this.txtStatus);
		}
		// spacer
		panel.add(Box.createVerticalStrut(20));
		// progress bar
		{
			this.progress = new JProgressBar();
			this.progress.setMaximumSize(new Dimension(240, 20));
			this.progress.setMaximum(100);
			this.progress.setValue(30);
			panel.add(this.progress);
		}
		// spacer
		panel.add(Box.createVerticalStrut(25));
		// cancel button
		{
			this.btnCancel = new JButton("Cancel");
			this.btnCancel.setDefaultCapable(true);
			this.btnCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
			panel.add(this.btnCancel);
			// event listener
			this.btnCancel.addActionListener(
				RemappedActionListener.get(
					this,
					"onClickCancelButton"
				)
			);
		}
		return panel;
	}



	/**
	 * Displays a card in the login window.
	 * @param card Card name to display.
	 * @thread-safe
	 */
	public void Update(final String state) {
		if(utils.isEmpty(state)) throw new NullPointerException("state argument is required!");
		if(guiUtils.forceDispatchThread(this, "ShowCard", state)) return;
		switch(state.toLowerCase()) {
		case "login":
//			if(this.socket != null) {
//				utils.safeClose(this.socket);
//				this.socket = null;
//			}
			if(!this.currentCard.equals(CARD_LOGIN)) {
				log().fine("Showing card: "+CARD_LOGIN);
				this.cardLayout.show(this.getContentPane(), CARD_LOGIN);
				this.currentCard = CARD_LOGIN;
			}
			return;
		case "connecting":
		case "connect":
		case "conn":
		case "auth":
			if(!this.currentCard.equals(CARD_CONNECTING)) {
				log().fine("Showing card: "+CARD_CONNECTING);
				this.cardLayout.show(this.getContentPane(), CARD_CONNECTING);
				this.currentCard = CARD_CONNECTING;
			}
			return;
		case "success":
			this.close();
			return;
		}
		throw new IllegalArgumentException("Unknown card: "+state);
	}



	// populate saved servers
	public void loadProfilesConfig() {
		if(guiUtils.forceDispatchThread(this, "loadProfilesConfig")) return;
		final ProfilesConfig profilesConfig = gcClientVars.getProfilesConfig();
		final Map<String, SavedProfileConfig> profiles = profilesConfig.getProfiles();
		// populate dropdown list
//		this.lstProfiles.addItem(gcClientDefines.PROFILE_NEW);
//		this.lstProfiles.addItem(SAVEDSERVERS_Unsaved);
//		this.lstProfiles.addItem(SAVEDSERVERS_InternalServer);
		for(final SavedProfileConfig profile : profiles.values()) {
			final String title = FormatProfile(profile);
			this.lstProfiles.addItem(title);
			this.profiles.put(title, profile);
		}
		// select last used
		this.lstProfiles.setSelectedItem(profilesConfig.getLastUsedProfile());
	}



	public static String FormatProfile(final SavedProfileConfig profile) {
		if(profile == null) throw new NullPointerException("profile argument is required!");
		final StringBuilder str = new StringBuilder();
		// profile name
		str.append(profile.name);
		if(gcClientDefines.PROFILE_HOST_INTERNAL.equalsIgnoreCase(profile.host)) {
			str.append(" ").append(gcClientDefines.PROFILE_HOST_INTERNAL);
			return str.toString();
		}
		// ssl
//TODO:
		final boolean ssl = false;
		// host
		str.append("  ( ");
		if(utils.isEmpty(profile.host))
			str.append("");
		else
		if("localhost".equalsIgnoreCase(profile.host))
			str.append("localhost");
		else
			str.append(profile.host);
		// port
		if(profile.port != gcDefines.DEFAULT_SOCKET_PORT(ssl))
			str.append(":").append(profile.port);
		str.append(" )");
		return str.toString();
	}



	// dropdown list event
	public void onListClickEvent(final ItemEvent event) {
		if(event.getStateChange() == ItemEvent.SELECTED) {
			final String selected = event.getItem().toString();
			String host = "";
			String port = "";
			String user = "";
			String pass = "";
			final SavedProfileConfig profile = this.profiles.get(selected);
			if(profile != null) {
				log().fine("Selected profile: "+profile.name);
				host = profile.host;
				port = Integer.toString(profile.port);
				user = profile.user;
				pass = profile.pass;
			}
			// internal
			if(gcClientDefines.PROFILE_HOST_INTERNAL.equalsIgnoreCase(host)) {
				port = "N/A";
				this.txtboxPort.setEnabled(false);
			} else {
				this.txtboxPort.setEnabled(true);
			}
			this.txtboxHost.setText(host);
			this.txtboxPort.setText(port);
			this.txtboxUser.setText(user);
			this.txtboxPass.setText(pass);
		}
//		final String selected = event.getItem().toString();
//		if(event.getStateChange() == ItemEvent.SELECTED) {
//			boolean isinternal = false;
//			if(SAVEDSERVERS_Unsaved.equals(selected)) {
//				if("- Internal -".equals(this.txtboxHost.getText()))
//					this.txtboxHost.setText("localhost");
//				if(utils.isEmpty(this.txtboxPort.getText()))
//					this.txtboxPort.setText(Integer.toString(gcClientDefines.DEFAULT_SOCKET_PORT));
//				xApp.log().fine("Selected -unsaved- profile");
//			} else if(SAVEDSERVERS_InternalServer.equals(selected)) {
//				isinternal = true;
//				this.txtboxHost.setText("- Internal -");
//				this.txtboxPort.setText("");
//				this.txtboxUser.setText("");
//				this.txtboxPass.setText("");
//				xApp.log().fine("Selected -internal- profile");
//			} else {
//				final SavedProfileConfig profile = this.profiles.get(selected);
//				this.txtboxHost.setText(profile.host);
//				this.txtboxPort.setText(Integer.toString(profile.port));
//				this.txtboxUser.setText(profile.user);
//				this.txtboxPass.setText(profile.pass);
//				xApp.log().fine("Selected server profile: "+selected);
//			}
//			this.txtboxHost.setEnabled(!isinternal);
//			this.txtboxPort.setEnabled(!isinternal);
//			this.txtboxUser.setEnabled(!isinternal);
//			this.txtboxPass.setEnabled(!isinternal);
//		}
	}



	// profile menu button click
	public void onClickProfileMenuButton(final ActionEvent event) {
		this.menuProfile.show(
				this.btnMenu,
				this.btnMenu.getWidth(),
				0
		);
		log().fine("Showing profiles menu");
	}



	// connect button click
	public void onClickConnectButton(final ActionEvent event) {
		final String buttonName = ((JButton) event.getSource()).getActionCommand();
		log().fine("Clicked '"+buttonName+"' button");
		// show connecting card
		this.Update(CARD_CONNECTING);
		this.txtStatus.setText("Connecting..");
		// connect to server
@SuppressWarnings("unused")
		final String hostStr = this.txtboxHost.getText();
@SuppressWarnings("unused")
		final Integer portInt = utilsNumbers.toInteger(this.txtboxPort.getText());
//		if(portInt == null || !utilsNumbers.isMinMax(portInt.intValue(), 1, xSocket.MAX_PORT_NUMBER)) {
//			xApp.log().warning("Invalid port: "+this.txtboxPort.getText());
//			JOptionPane.showMessageDialog(this, "Invalid port number provided: "+
//					this.txtboxPort.getText()+"\n\n"+
//					"Valid port numbers are between: 1 and "+
//					Integer.toString(xSocket.MAX_PORT_NUMBER)+"  \n"+
//					"Default: "+Integer.toString(gcClientConfig.DEFAULT_LISTEN_PORT),
//					"Invalid port..",
//					JOptionPane.ERROR_MESSAGE);
//			return;
//		}
//TODO: this will be changed
//		this.socket = new xSocketTCP();
//		this.socket.setHost(hostStr);
//		this.socket.setPort(portInt.intValue());
//		this.socket.connect();
	}
	public void onClickCancelButton(final ActionEvent event) {
		final String buttonName = ((JButton) event.getSource()).getActionCommand();
		log().fine("Clicked '"+buttonName+"' button");
		// show login card
		this.Update(CARD_LOGIN);
	}



	// close window
	@Override
	public void close() {
		if(guiUtils.forceDispatchThread(this, "close")) return;
		if(!this.closing.compareAndSet(false, true)) return;
		// disconnect if attempting
		this.Update(CARD_LOGIN);
		this.closing.set(false);
		super.close();
		guiManager.get().doLoginWindowClosed();
	}



}
