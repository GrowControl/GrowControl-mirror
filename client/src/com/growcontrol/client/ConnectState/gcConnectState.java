package com.growcontrol.gcClient.ConnectState;


public class gcConnectState extends ConnectState {

//	protected boolean authorized = false;

//	// windows
//	LoginHandler loginWindow = null;
//	DashboardHandler dashboardWindow = null;


	@Override
	protected void doChangedState(State lastState) {
//		// preload login window
//		if(state.equals(State.CLOSED)
//		|| state.equals(State.CONNECTING)
//		|| state.equals(State.CONNECTED) ) {
//			if(loginWindow == null)
//				loginWindow = new LoginHandler();
//			if(loginWindow == null) {
//System.out.println("Failed to load login window!");
//				return;
//			}
//		} else
//		// preload dashboard window
//		if(state.equals(State.READY)) {
//			if(dashboardWindow == null)
//				dashboardWindow = new DashboardHandler();
//			if(dashboardWindow == null) {
//System.out.println("Failed to load login window!");
//				return;
//			}
//		}
//
//		switch(state) {
//		case CLOSED:
//System.out.println("state: CLOSED");
//			// display login card
////			loginWindow.setDisplay(LoginWindows.LOGIN);
////			// close socket
////			pxnSocketClient socket = Main.getClient().getSocket();
////			if(socket != null) {
////				socket.close();
////				socket = null;
////			}
//			break;
//		case CONNECTING:
//System.out.println("state: CONNECTING");
//			// display connecting card
////			loginWindow.setDisplay(LoginWindows.CONNECTING);
//			break;
//		case CONNECTED:
//System.out.println("state: CONNECTED");
//			// display connecting card
////			loginWindow.setDisplay(LoginWindows.CONNECTING);
//			break;
//		case READY:
//System.out.println("state: READY");
////			if(loginWindow != null) {
////				loginWindow.close();
////				loginWindow = null;
////			}
////			// display dashboard window
////			dashboardWindow.show();
//			break;
//		}
	}


//	// get window frame
//	public JFrame getFrame(String frameName) {
//		if(frameName.equals("login"))
//			return loginWindow.getFrame();
//		if(frameName.equals("dashboard"))
//			return dashboardWindow.getFrame();
//		return null;
//	}


//	// is authorized
//	public boolean isAuthorized() {
//		return authorized;
//	}
//	public void setAuthorized(boolean b) {
//		this.authorized = b;
//	}


//	// closed
//	public void setStateClosed() {
//		setConnectState(State.CLOSED);
//	}

//	// connecting..
//	public void setStateConnecting() {
//		setStateConnecting("Connecting..");
//	}
//	public void setStateConnecting(String message) {
//		setConnectState(State.CONNECTING);
////		loginWindow.setMessage("Failed to connect!");
//	}

//	// ready!
//	public void setStateReady() {
//		setConnectState(State.READY);
//	}


}
