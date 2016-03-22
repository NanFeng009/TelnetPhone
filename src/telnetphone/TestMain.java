package telnetphone;

public class TestMain { 
    
    public static void main(String[] args) {
    	
//    	int loops = Integer.valueOf(args[0]).intValue();
    	
    	test1(300);
		
	}
    
    private static void test1(int loops) {
    	
    	// a and b have a share line
		Phone a = new Phone("10.29.9.48");
		Phone b = new Phone("10.35.148.35");
		
		
		// press speed dial to phone c, c will auto answer
		a.sendCommand(KeyEvent.getKeyCommand(KeyEvent.LINE_3));
		
		// sleep 3 sec for c to answer
		sleep(3000);

		
		while(loops > 0) {
			// a hold the call
			a.sendCommand(KeyEvent.getKeyCommand(KeyEvent.HOLD));
			
			// sleep 1 sec for b update to remote hold
			sleep(1500);
			
			// b offhook
			b.sendCommand(KeyEvent.getKeyCommand(KeyEvent.SPEAKER));
	
			// b press session 1 to resume remote hold call
			b.sendCommand(KeyEvent.getKeyCommand(KeyEvent.SESSION_1));
//			
//			// offhook and press session 1 immediately
//			b.sendCommand(ShellMode.MODE_DEBUG_SHELL, "show run test");
			
			// b hold the call
			b.sendCommand(KeyEvent.getKeyCommand(KeyEvent.HOLD));
	
			// sleep 1 sec for a update to remote hold
			sleep(1000);
	
			// a press session 1 to resume remote hold call
			a.sendCommand(KeyEvent.getKeyCommand(KeyEvent.SESSION_1));
			
			// sleep 1 sec for a update to connected
			sleep(1000);
		
			loops--;
		}
		
		
		// a end the call
		a.sendCommand(KeyEvent.getKeyCommand(KeyEvent.RELEASE));
		
		// disconnect telnet
		a.dispose();
		b.dispose();
		
	}
    
    private static void test2(int loops) {

		Phone b = new Phone("10.35.148.35");

		b.sendCommand(ShellMode.MODE_DEBUG_SHELL, "show run test");
		
		b.dispose();
		
    }
    
    private static void sleep(long millsec) {
		try {
			Thread.sleep(millsec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

}