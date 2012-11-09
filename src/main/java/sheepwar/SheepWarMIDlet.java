package sheepwar;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class SheepWarMIDlet extends MIDlet {
	
	private static SheepWarMIDlet instance;
	private OnlineThread onlineThread;
	
	public SheepWarMIDlet(){
		instance = this;
	}
	
	public static SheepWarMIDlet getInstance() {
		return instance;
	}
	
	private SheepWarGameEngine engine;

	protected void destroyApp(boolean unconditional)throws MIDletStateChangeException {}

	protected void pauseApp() {}

	protected void startApp() throws MIDletStateChangeException {
		engine = SheepWarGameEngine.instance;
		//System.out.println("engnie:"+engine);
		Display.getDisplay(this).setCurrent(engine);
		new Thread(engine).start();
		
		onlineThread = new OnlineThread(engine);
		//System.out.println("onlineThread:"+onlineThread);
		onlineThread.t1 = System.currentTimeMillis()/1000;
		//System.out.println("t1:"+onlineThread.t1);
		new Thread(onlineThread).start();
	}

}
