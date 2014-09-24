package com.growcontrol.client.gui.login;

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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.growcontrol.client.configs.SavedServerProfile;
import com.growcontrol.client.configs.SavedServersConfig;
import com.growcontrol.client.configs.gcClientConfig;
import com.growcontrol.client.gui.guiManager;
import com.poixson.commonapp.config.xConfigLoader;
import com.poixson.commonapp.gui.guiUtils;
import com.poixson.commonapp.gui.xFont;
import com.poixson.commonapp.gui.xWindow;
import com.poixson.commonapp.gui.annotations.xWindowProperties;
import com.poixson.commonapp.gui.remapped.RemappedActionListener;
import com.poixson.commonapp.gui.remapped.RemappedItemListener;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.xLogger.xLog;


@xWindowProperties(
		resizable = false)
public class gcWindowLogin extends xWindow {
	private static final long serialVersionUID = 1L;
	private static final boolean DEBUGGING = false;


	private static final String WINDOW_TITLE = "Connect to..";
	private static final int WINDOW_WIDTH = 320;

	private static final String SAVEDSERVERS_Unsaved        = "[ Unsaved ]";
	private static final String SAVEDSERVERS_InternalServer = "[ Standalone ]";

	private static final String IMAGE_LOADING = "images/icon-loading-animated.gif";

	// saved profiles
	protected final Map<String, SavedServerProfile> profiles = new LinkedHashMap<String, SavedServerProfile>();

	// card panels
	protected final CardLayout cardLayout;
	protected volatile String currentCard = null;
	protected static final String CARD_LOGIN      = "login";
	protected static final String CARD_CONNECTING = "connecting";

	// login form
	protected volatile JComboBox<String> lstProfiles = null;
	protected volatile JTextField        txtboxHost  = null;
	protected volatile JTextField        txtboxPort  = null;
	protected volatile JTextField        txtboxUser  = null;
	protected volatile JPasswordField    txtboxPass  = null;
	protected volatile JButton           btnConnect  = null;

	// connecting animation
	protected volatile JLabel       txtStatus = null;
	protected volatile JProgressBar progress  = null;
	protected volatile JButton      btnCancel = null;



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
		// load savedservers.yml
		this.loadSavedServersConfig();
		// show window
		this.Show();
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
		// profiles dropdown list
		{
			this.lstProfiles = new JComboBox<String>();
			panel.add(this.lstProfiles, "span, growx, gapleft 10px, gapright 10px, wrap");
			// event listener
			try {
				this.lstProfiles.addItemListener(
					new RemappedItemListener(
						this,
						"onSavedListEvent"
					)
				);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
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
			try {
				this.btnConnect.addActionListener(
					new RemappedActionListener(
						this,
						"onClickConnectButton"
					)
				);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
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
			try {
				this.btnCancel.addActionListener(
					new RemappedActionListener(
						this,
						"onClickCancelButton"
					)
				);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return panel;
	}



	/**
	 * Displays a card in the login window.
	 * @param card Card name to display.
	 * @thread-safe
	 */
	public void Update(final String state) {
		xLog.getRoot().fine("Showing card: "+card);
		if(utils.isEmpty(state)) throw new NullPointerException();
		// run in event dispatch thread
		if(guiUtils.forceDispatchThread(this, "ShowCard", state)) return;
		switch(state.toLowerCase()) {
		case "login":
			if(!this.currentCard.equals(CARD_LOGIN))
				this.cardLayout.show(this.getContentPane(), CARD_LOGIN);
			this.currentCard = CARD_LOGIN;
			return;
		case "connecting":
		case "connect":
		case "conn":
		case "auth":
			if(!this.currentCard.equals(CARD_CONNECTING))
				this.cardLayout.show(this.getContentPane(), CARD_CONNECTING);
			this.currentCard = CARD_CONNECTING;
			return;
		case "ready":
			this.close();
			return;
		}
		throw new IllegalArgumentException("Unknown card: "+card);
	}



	// populate saved servers
	public void loadSavedServersConfig() {
		final SavedServersConfig config = (SavedServersConfig) xConfigLoader.Load(
			SavedServersConfig.CONFIG_FILE,
			SavedServersConfig.class
		);
		// populate dropdown list
		this.lstProfiles.addItem(SAVEDSERVERS_Unsaved);
		this.lstProfiles.addItem(SAVEDSERVERS_InternalServer);
		for(final SavedServerProfile profile : config.getProfiles()) {
			final String title = profile.formatForList();
			this.lstProfiles.addItem(title);
			this.profiles.put(title, profile);
		}
		// select last used
		this.lstProfiles.setSelectedItem(config.getLastUsedProfile());
	}



	// dropdown list event
	public void onSavedListEvent(final ItemEvent event) {
		final String selected = event.getItem().toString();
		if(event.getStateChange() == ItemEvent.SELECTED) {
			boolean isinternal = false;
			if(SAVEDSERVERS_Unsaved.equals(selected)) {
				if("- Internal -".equals(this.txtboxHost.getText()))
					this.txtboxHost.setText("localhost");
				if(utils.isEmpty(this.txtboxPort.getText()))
					this.txtboxPort.setText(Integer.toString(gcClientConfig.DEFAULT_LISTEN_PORT));
				xLog.getRoot().fine("Selected -unsaved- profile");
			} else if(SAVEDSERVERS_InternalServer.equals(selected)) {
				isinternal = true;
				this.txtboxHost.setText("- Internal -");
				this.txtboxPort.setText("");
				this.txtboxUser.setText("");
				this.txtboxPass.setText("");
				xLog.getRoot().fine("Selected -internal- profile");
			} else {
				final SavedServerProfile profile = this.profiles.get(selected);
				this.txtboxHost.setText(profile.host);
				this.txtboxPort.setText(Integer.toString(profile.port));
				this.txtboxUser.setText(profile.user);
				this.txtboxPass.setText(profile.pass);
				xLog.getRoot().fine("Selected server profile: "+selected);
			}
			this.txtboxHost.setEnabled(!isinternal);
			this.txtboxPort.setEnabled(!isinternal);
			this.txtboxUser.setEnabled(!isinternal);
			this.txtboxPass.setEnabled(!isinternal);
		}
	}



	// button click event
	public void onClickConnectButton(final ActionEvent event) {
		final String buttonName = ((JButton) event.getSource()).getActionCommand();
		xLog.getRoot().fine("Clicked '"+buttonName+"' button");
		// show connecting card
		this.Update(CARD_CONNECTING);
		this.txtStatus.setText("Connecting..");
	}
	public void onClickCancelButton(final ActionEvent event) {
		final String buttonName = ((JButton) event.getSource()).getActionCommand();
		xLog.getRoot().fine("Clicked '"+buttonName+"' button");
		// show login card
		this.Update(CARD_LOGIN);
	}



	// close window
	@Override
	public void close() {
		// disconnect if attempting
		this.Update(CARD_LOGIN);
		super.close();
		guiManager.get().doLoginWindowClosed();
	}



}
