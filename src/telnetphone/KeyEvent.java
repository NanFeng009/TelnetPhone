package telnetphone;

public class KeyEvent {

	public static final String KEY_PREFIX = "phone key ";
	
	public static String getKeyCommand(int keyEvent) {
		return KEY_PREFIX + keyEvent;
	}

	public static final int KEYPAD_0 = 513;
	public static final int KEYPAD_1 = 514;
	public static final int KEYPAD_2 = 515;
	public static final int KEYPAD_3 = 516;
	public static final int KEYPAD_4 = 517;
	public static final int KEYPAD_5 = 518;
	public static final int KEYPAD_6 = 519;
	public static final int KEYPAD_7 = 520;
	public static final int KEYPAD_8 = 521;
	public static final int KEYPAD_9 = 522;
	public static final int KEYPAD_STAR = 523;
	public static final int KEYPAD_POUND = 524;
	

	public static final int LINE_1 = 530;
	public static final int LINE_2 = 531;
	public static final int LINE_3 = 532;
	public static final int LINE_4 = 533;
	public static final int LINE_5 = 534;
	public static final int LINE_6 = 535;
	

	public static final int SESSION_1 = 536;
	public static final int SESSION_2 = 537;
	public static final int SESSION_3 = 538;
	public static final int SESSION_4 = 539;
	public static final int SESSION_5 = 540;
	public static final int SESSION_6 = 541;
	
 	public static final int NAV_BACK = 525;
	public static final int NAV_UP = 544;
	public static final int NAV_DOWN = 546;
	public static final int NAV_SELECT = 545;
	public static final int NAV_LEFT = 547;
	public static final int NAV_RIGHT = 548;
    

 	public static final int MESSAGES = 549;
	public static final int CONTACTS = 550;
 	public static final int DIRECTORIES = 550;
 	public static final int APPLICATIONS = 551;
 	public static final int HEADSET = 552;
 	public static final int SPEAKER = 553;
 	public static final int MUTE = 554;
 	public static final int DISPLAY = 556;
	
 	
    // Hard Call Buttons
 	public static final int RELEASE = 555;
 	public static final int HOLD = 557;
 	public static final int TRANSFER = 558;
 	public static final int XFER = 558;
 	public static final int CONFERENCE = 559;
 	public static final int CONF = 559;
	

 	public static final int SOFT1 = 526;
 	public static final int SOFT2 = 527;
 	public static final int SOFT3 = 528;
 	public static final int SOFT4 = 529;
 	

  	public static final int VOLUME_UP = 542;
  	public static final int VOLUME_DOWN = 543;
 	
 	
}
