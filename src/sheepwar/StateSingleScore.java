package sheepwar;

import cn.ohyeah.stb.game.SGraphics;

/**
 *动态 弹出单个得分类
 * @author Administrator
 */
public class StateSingleScore implements Common {
	
	private int tempY,scoreY;
	public void showSingleScore(SGraphics g,Batches batches,Role player) {

		Role npc = null;
		for(int i = 0;i< batches.npcs.size()-1;i++){
			npc = (Role)batches.npcs.elementAt(i);
			if(npc.status == ROLE_DEATH){				
				tempY = scoreY = player.mapy-30;
				if((scoreY-tempY)<50){	
					tempY -= 10;
					g.setColor(0x000000);
					g.drawString("+"+String.valueOf(npc.scores), npc.mapx, tempY, 20);
				}
			}
		}
	}
	
	/*显示击中子弹的分数*/
	public void showAttackBoom(SGraphics g,Weapon weapon,Role player) {
		Weapon boom = null;
		for (int i = weapon.booms.size()-1;i>=0;i--){
			boom = (Weapon)weapon.booms.elementAt(i);
			if(boom.status == BOOM_HIT){
				scoreY = player.mapy;
				g.setColor(0x000000);
				g.drawString("+"+String.valueOf(boom.scores), boom.mapx, tempY, 20);
			}
			
		}

	}
}
