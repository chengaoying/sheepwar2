package sheepwar;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupText;

public class StateGameSuccessOrFail {

	
	private SheepWarGameEngine engine = SheepWarGameEngine.instance;
	private boolean running;
	
	public void processGameSuccessOrFail(boolean isSuccess){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			SGraphics g = engine.getSGraphics();
			while (running) {
				handleGameSuccessOrFail(keyState);
				if (running) {
					long t1 = System.currentTimeMillis();
					showGameSuccessOrFail(g, isSuccess);
					engine.flushGraphics();
					System.gc();
					int sleepTime = (int)(125-(System.currentTimeMillis()-t1));
					if (sleepTime <= 0) {
						Thread.sleep(0);
					}
					else {
						Thread.sleep(sleepTime);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			clear();
		}
		
	}
	
	private void showGameSuccessOrFail(SGraphics g, boolean isSuccess) {
		Image bg = Resource.loadImage(Resource.id_game_bg);
		g.drawImage(bg, 0, 0, 20);
		System.out.println("��Ϸ�����"+isSuccess);
		if(isSuccess){
			PopupText pt = UIResource.getInstance().buildDefaultPopupText();
			pt.setText("��ϲ��ͨ�سɹ�����ȷ�ϼ��鿴���а�");
		}else{
			PopupText pt = UIResource.getInstance().buildDefaultPopupText();
			pt.setText("��Ϸʧ�ܣ���ȷ�ϼ��������˵�");
		}
		
	}
	
	private void handleGameSuccessOrFail(KeyState keyState) {
	    if(keyState.containsAndRemove(KeyCode.OK)){ 
	    	running = false;
		}
	}

	private void clear() {
		
	}

}
