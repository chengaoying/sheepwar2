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
			if(npc.status == ROLE_DEATH){				//结果只有第一个被击中的狼才有文字提示
				scoreY = player.mapy;
				if((npc.mapy-scoreY)>40){				/*暂时让显示静止于死亡的地方*/
					tempY = scoreY;
					g.setColor(0xffffff);
					g.drawString(/*"显示气球的 分数："*/String.valueOf(npc.scores), npc.mapx, tempY, 20);
//					scoreY -= 5;
				}
			}
		}
	}
	
	/*显示击中子弹的分数*/
	public void showAttackBoom(SGraphics g,Weapon weapon,Role player) {
		Weapon boom = null;
		for (int i = weapon.booms.size()-1;i>=0;i--){
			System.out.println(i);
			boom = (Weapon)weapon.booms.elementAt(i);
			System.out.println(boom);
			if(boom.status == BOOM_HIT){
				scoreY = player.mapy;
				System.out.println("射中子弹的得分："+boom.scores);
				System.out.println("子弹的横坐标："+boom.mapx);
				g.setColor(0xffffff);
				g.drawString(/*"显示气球的 分数："*/String.valueOf(boom.scores), boom.mapx, tempY, 20);
			}
			
		}

	}
}
