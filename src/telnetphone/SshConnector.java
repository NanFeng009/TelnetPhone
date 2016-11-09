package telnetphone;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.jcraft.jsch.*;

public class SshConnector {

	public static final int PARAM_TELNET_PORT_DEFAULT = 22;
	private static final String PROMPT = ">";

	public static final int STATE_DISCONNECTED = 0;
	public static final int STATE_CONNECTED = 1;
	
	private final int back_gap = 100;
	private final int call_gap = 8000;

	private JSch ssh = null;
	private InputStream in = null;
	private OutputStream out = null;

	private int state = STATE_DISCONNECTED;
	private String ip;
	private String userName;
	private String passWord;

	public static void main(String[] args) {
		SshConnector connect = new SshConnector("10.74.39.179", "cisco", "cisco");
		try {
			connect.connect();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		connect.test2(200);
	}

	private void test2(int loops) {

		while (loops > 0) {

			write("phone key " + KeyEvent.KEYPAD_6);
			write("phone key " + KeyEvent.KEYPAD_6);
			write("phone key " + KeyEvent.NAV_DOWN);
			write("phone key " + KeyEvent.KEYPAD_1);
			write("phone key " + KeyEvent.KEYPAD_2);
			write("phone key " + KeyEvent.KEYPAD_3);
			write("phone key " + KeyEvent.KEYPAD_4);
			write("phone key " + KeyEvent.KEYPAD_5);
			write("phone key " + KeyEvent.SOFT2);
			debug("finish login EM  "+loops);
//			debug("Call the remote phone now " + currentTime());
//			// on call for 5 seconds
			sleep(call_gap );
			debug("Start to logout EM");
			write("phone key " + KeyEvent.APPLICATIONS);
			sleep(back_gap);
			write("phone key " + KeyEvent.KEYPAD_6);
			sleep(back_gap);
			write("phone key " + KeyEvent.SOFT1);
			sleep(call_gap );
			debug("finish logout EM");

			loops--;
		}

	}

	private static void sleep(long millsec) {
		try {
			Thread.sleep(millsec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public SshConnector(String ip, String userName, String passWord) {
		this.ip = ip;
		this.userName = userName;
		this.passWord = passWord;
	}

	public void connect() throws JSchException, IOException {
		// Connect to the specified server
		try {
			JSch jsch = new JSch();

			Session session = jsch.getSession(userName, ip, PARAM_TELNET_PORT_DEFAULT);
			session.setPassword(passWord);

			session.setConfig("StrictHostKeyChecking", "no");

			// connect
			debug("Connecting to server: " + ip + " on port: " + PARAM_TELNET_PORT_DEFAULT);
			session.connect(30000);
			debug("--Connect Ok");

			debug("--Open SSH channel");
			Channel channel = session.openChannel("shell");

			in = channel.getInputStream();
			out = channel.getOutputStream();

			channel.connect();
			debug("--Open SSH channel ok");

			readUntil("(none) login: ");
			write("debug");
			readUntil("Password: ");
			write("debug");

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
			for (int i = 0; i < expectResults.length; i++) {
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
	 * Reads input stream until the given pattern is reached. The pattern is
	 * discarded and what was read up until the pattern is returned.
	 */
	protected String readUntil(String pattern) throws IOException {
		char lastChar = pattern.charAt(pattern.length() - 1);
		StringBuilder sb = new StringBuilder();
		int c;

		while ((c = in.read()) != -1) {
			char ch = (char) c;
			System.out.print(ch);
			sb.append(ch);
			if (ch == lastChar) {
				String str = sb.toString();
				if (str.endsWith(pattern)) {
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
		try {
			out.write((value + "\n").getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void debug(String s) {
		System.out.println(s);
	}

	public int getState() {
		return state;
	}

	public String currentTime() {

	        Calendar cal = Calendar.getInstance();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z");
	        return sdf.format(cal.getTime());
	 

	}
	

}
