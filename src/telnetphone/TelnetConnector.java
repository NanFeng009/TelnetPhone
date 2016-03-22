package telnetphone;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.net.telnet.TelnetClient;

public class TelnetConnector {
	 
    public static final int PARAM_TELNET_PORT_DEFAULT = 23; 
    private static final String PROMPT = "#"; 

    
    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_CONNECTED = 1;
 
    private TelnetClient telnet = null; 
    private InputStream in = null; 
    private PrintStream out = null; 
    
    private int state = STATE_DISCONNECTED;
    private String ip;
    private String userName;
    
    public TelnetConnector(String ip, String userName) {
		this.ip = ip;
		this.userName = userName;
	}
    
    public void connect() { 
        // Connect to the specified server 
        try { 
            telnet = new TelnetClient(); 
 
            // connect 
            debug("Connecting to server: " + ip + " on port: " + 
            		PARAM_TELNET_PORT_DEFAULT); 
            telnet.connect(ip, PARAM_TELNET_PORT_DEFAULT); 
 
            // Get input and output stream references 
            in = telnet.getInputStream(); 
            out = new PrintStream(telnet.getOutputStream()); 
 
            // Login the user 
            debug("Logging in"); 
            readUntil("(none) login: "); 
            write(userName); 
 
            // Advance to a prompt 
            debug("Reading up to prompt."); 
            readUntil(PROMPT); 
            debug("Prompt found. Ready."); 
            
            state = STATE_CONNECTED;
        } catch (IOException ioe) { 
        } 
    } 

    public boolean sendCommand(String command, String[] expectResults) {
        try { 
            write(command); 
            readUntil("\n"); // read past echo  
            for(int i=0; i< expectResults.length;i++) {
            	if (expectResults[i] != null) {
            		readUntil(expectResults[i]);
            	}
            }
            return true;
        } catch (IOException e) { 
        	return false;
        } 
    } 
    
    public String sendCommand(String command, String prompt) {
        try { 
        	write(command); 
        	readUntil("\n"); // read past echo  
            String result = readUntil(prompt); 
            // drop trailing '\n' 
            return result.substring(0, result.length() - 1); 
        } catch (IOException e) { 
        	return null;
        } 
    
	}
 
    /**
     * Closes the connection. 
     */ 
    public void close(){ 
         try {
			telnet.disconnect();
			state = STATE_DISCONNECTED;
		} catch (IOException e) {
			e.printStackTrace();
		} 
    } 
 
    /**
     * Reads input stream until the given pattern is reached. The  
     * pattern is discarded and what was read up until the pattern is 
     * returned. 
     */ 
    protected String readUntil(String pattern) throws IOException { 
        char lastChar = pattern.charAt(pattern.length() - 1); 
        StringBuilder sb = new StringBuilder(); 
        int c; 
 
        while((c = in.read()) != -1) { 
            char ch = (char) c; 
            System.out.print(ch); 
            sb.append(ch); 
            if(ch == lastChar) { 
                String str = sb.toString(); 
                if(str.endsWith(pattern)) { 
                    return str.substring(0, str.length() - pattern.length()); 
                } 
            } 
        } 
 
        return null; 
    } 
 
    /**
     * Writes the value to the output stream. 
     */ 
    protected void write(String value) { 
        out.println(value); 
        out.flush(); 
    } 
    
    private void debug(String s){
    	System.out.println(s);
    }

    public int getState() {
    	return state;
    }
}
