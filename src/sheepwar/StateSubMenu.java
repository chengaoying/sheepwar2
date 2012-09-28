package sheepwar;

import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

public class StateSubMenu implements Common{

	
	private SheepWarGameEngine engine = SheepWarGameEngine.instance;
	private boolean running;
	private int menuIndex;
	
	public int processSubMenu(){
		running = true;
		try {
			KeyState keyState = engine.getKeyState();
			SGraphics g = engine.getSGraphics();
			while (running) {
				handleSubMenu(keyState);
				if (running) {
					long t1 = System.currentTimeMillis();
					showSubMenu(g);
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
		return menuIndex;
	}

	private void showSubMenu(SGraphics g) {
		Image submenu_bg = Resource.loadImage(Resource.id_sub_menu_bg);
		Image submenu = Resource.loadImage(Resource.id_sub_menu);
		
		int sx = gameW/2-submenu_bg.getWidth()/2;
		int sy = ScrH/2-submenu_bg.getHeight()/2;
		g.drawImage(submenu_bg, sx, sy, 20);
		
		int sw = submenu.getWidth() / 2, sh = submenu.getHeight() / 4;
		sx = gameW/2-sw/2;
		sy += 25;
		for (int i = 0; i < 4; ++i) {
			g.drawRegion(submenu,(menuIndex != i) ? sw : 0, i * sh, sw, sh, 0, sx, sy, 0);
			sy += sh+15;
		}
	}
	
	private void handleSubMenu(KeyState keyState) {
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			running = false;
			menuIndex = 0;
		}else if(keyState.containsAndRemove(KeyCode.UP)){
			menuIndex = (menuIndex+4-1)%4;
		}else if(keyState.containsAndRemove(KeyCode.DOWN)){
			menuIndex = (menuIndex+1)%4;
		}else if(keyState.containsAndRemove(KeyCode.OK)){
			running = false;
		}
	}

	private void clear() {
		Resource.freeImage(Resource.id_sub_menu);
		Resource.freeImage(Resource.id_sub_menu_bg);
	}
}
