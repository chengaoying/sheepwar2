package sheepwar;

/**
 * ������
 * @author Administrator
 *
 */
public class Attacks implements Common {
	
	private SheepWarGameEngine engine;
	public Attacks(SheepWarGameEngine e){
		engine = e;
	}
	
	/*��ͨ���ڹ���*/
	public void bompAttack(Role wolf,Role own){
		//System.out.println("weapon.bombs.size()------->: "+engine.weapon.bombs.size());
//		System.out.println("CreateRole.bubles.size()***:"+CreateRole.bubles.size());
		if(engine.weapon.bombs.size()>=0){
			Weapon bomb	=	null;
			for (int i = 0; i < engine.weapon.bombs.size(); i++) {
				bomb = (Weapon) engine.weapon.bombs.elementAt(i);
				int bombY=bomb.mapy;
//				bombH = bomb.mapy+bomb.height/2+10;
				Role buble	=	null;     //����
				//System.out.println("bomb.direction: "+bomb.direction);
				//System.out.println("CreateRole.bubles.size(): "+CreateRole.bubles.size());
				
				if(bomb.direction	==	2){  //��������ķ�����ˮƽ����
					for (int j = 0; j < CreateRole.bubles.size(); j++) {
						buble = (Role) CreateRole.bubles.elementAt(j);
						
					System.out.println("buble.mapy�����������:"+buble.mapy);
					System.out.println("bombY:"+bombY);
						if(((bombY+bomb.height)>= buble.mapy && (bombY+bomb.height)<=(buble.height+buble.mapy)) 
								|| ((bombY)>= buble.mapy && (bombY)<=(buble.height+buble.mapy))){
							hitBuble(own,buble,bomb);//��������
							System.out.println("���������Ժ�Ĳ���...");
							
						}else{
							//System.out.println(bombH <=(buble.height+buble.mapy));
							//System.out.println("����û������*****");
						}
					}
//					System.out.println("�������"+buble);
//					System.out.println("�ӵ����ޣ�"+(buble.mapy+buble.height));
//					System.out.println("�ӵ����ޣ�"+(bombH+bomb.height));
				}
			}
		}
	}

	private void hitBuble(Role wolf,Role buble, Weapon w) {
//		if(buble.id == 1){
		CreateRole.npcs.removeElement(wolf);
		CreateRole.bubles.removeElement(buble);
		engine.weapon.bombs.removeElement(w);
//		}
	}

}
