package telnetphone;

import java.util.ArrayList;
import java.util.List;

public class Shell {

	private ShellMode shellMode;
	private List shellList = new ArrayList();
	private TelnetConnector connector;
	
	public Shell(String ip, String userName) {
		connector = new TelnetConnector(ip, userName);
		
		ShellMode basicShellMode = ShellMode.getShellMode(ShellMode.MODE_SHELL, connector);
		
		shellList.add(basicShellMode);
		shellList.add(ShellMode.getShellMode(ShellMode.MODE_DEBUG_SHELL, connector));
		shellList.add(ShellMode.getShellMode(ShellMode.MODE_DEBUG_SHELL_TEST, connector));
		
		shellMode = basicShellMode;
	}
	
	public String sendCommand(String command) {
		enterMode(ShellMode.MODE_DEBUG_SHELL_TEST);
		return shellMode.sendCommand(command);
	}
	
	public String sendCommand(int mode, String command) {
		enterMode(mode);
		return shellMode.sendCommand(command);
	}
	
	private void enterMode(int mode) {
		if (mode != shellMode.getMode()) {
			if (mode > shellMode.getMode()) {
				for (int i=shellMode.getMode()+1; i <= mode;i++) {
					shellMode = (ShellMode)shellList.get(i);
					shellMode.enter();
				}
			}
			else {
				for (int i=shellMode.getMode(); i > mode;i--) {
					shellMode = (ShellMode)shellList.get(i);
					shellMode.exit();
				}
				shellMode = (ShellMode)shellList.get(mode);
			}
		}
	}
	
	public void connect() {
		this.connector.connect();
	}
	
	public void close() {
		enterMode(ShellMode.MODE_SHELL);
		this.connector.close();
	}
	
	public int getState() {
		return connector.getState();
	}
}
