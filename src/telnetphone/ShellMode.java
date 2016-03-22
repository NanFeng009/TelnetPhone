package telnetphone;

import java.io.IOException;

public abstract class ShellMode {

	protected static final int MODE_SHELL = 0;
	protected static final int MODE_DEBUG_SHELL = 1;
	protected static final int MODE_DEBUG_SHELL_TEST = 2;
    
    protected int mode;
    
	protected TelnetConnector connector;
	
	protected ShellMode(TelnetConnector connector) {
		this.connector = connector;
	}

	public abstract boolean enter();
	
	public abstract boolean exit();
	
	public abstract String getPrompt();
	
	public String sendCommand(String command) {
        return connector.sendCommand(command, getPrompt());
	}
	
	public static ShellMode getShellMode(int mode, TelnetConnector connector) {
		if (mode == MODE_SHELL) {
			return new BasicShellMode(connector);
		}
		else if (mode == MODE_DEBUG_SHELL) {
			return new DebugShellMode(connector);
		}
		else if (mode == MODE_DEBUG_SHELL_TEST) {
			return new TestShellMode(connector);
		}
		return new BasicShellMode(connector);
	}

	public int getMode() {
		return mode;
	}
	
}


class BasicShellMode extends ShellMode {
	
	public BasicShellMode(TelnetConnector connector) {
		super(connector);
		this.mode = MODE_SHELL;
	}

	public boolean enter() {
		return true;
	}

	public boolean exit() {
		return true;
	}

	public String getPrompt() {
		return "#";
	}

}


class DebugShellMode extends ShellMode {

	public DebugShellMode(TelnetConnector connector) {
		super(connector);
		this.mode = MODE_DEBUG_SHELL;
	}
	
	public boolean enter() {
        try { 
        	connector.write("debugsh"); 
        	connector.readUntil("\n"); // read past echo  
            String result = connector.readUntil("full"); 
            
            if (result.equalsIgnoreCase("a ")) {
            	// a full-function debugsh may exist. Kill it? <n/y>
            	connector.readUntil("Kill it? <n/y> "); 
            	connector.write("y");
            	connector.readUntil("\n");
            	connector.readUntil("killing the previous debugsh"); 
            	connector.readUntil("enter full mode"); 
            	connector.readUntil(getPrompt()); 
            	return true;
            }
            else if(result.equalsIgnoreCase("enter ")) {
            	// enter full mode
            	connector.readUntil(getPrompt()); 
            	return true;
            }
            return false; 
        } catch (IOException e) { 
        	return false;
        } 
	}

	public boolean exit() {
		return connector.sendCommand("quit", new String[]{"Exiting shell...","Logging out...", "#"});
    }

	public String getPrompt() {
		return "DEBUG>";
	}
	
}


class TestShellMode extends ShellMode {

	public TestShellMode(TelnetConnector connector) {
		super(connector);
		this.mode = MODE_DEBUG_SHELL_TEST;
	}

	public boolean enter() {
		return connector.sendCommand("phone test open", new String[]{getPrompt()});
    }

	public boolean exit() {
		return connector.sendCommand("phone test close", new String[]{getPrompt()});
    }

	public String getPrompt() {
		return "DEBUG>";
	}
	
}