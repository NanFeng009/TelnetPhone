package telnetphone;

public class Phone {

	private String ip;
	private Shell shell;
	
	public Phone(String ip) {
		this.ip = ip;
		shell = new Shell(ip, "root");
	}
	
	public void sendCommand(String command) {
		if (shell.getState() == TelnetConnector.STATE_DISCONNECTED) {
			shell.connect();
		}
		shell.sendCommand(command);
	}
	
	public void sendCommand(int mode, String command) {
		if (shell.getState() == TelnetConnector.STATE_DISCONNECTED) {
			shell.connect();
		}
		shell.sendCommand(mode, command);
	}
	
	public void dispose() {
		if (shell.getState() == TelnetConnector.STATE_CONNECTED) {
			shell.close();
		}
	}
}
