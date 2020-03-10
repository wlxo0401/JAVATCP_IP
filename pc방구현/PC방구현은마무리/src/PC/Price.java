package PC;
import javax.swing.JLabel;

import PC.HostPC;
public class Price extends Thread{
	int money;
	public int num=0;
	Seatoff Seatoff[] =new Seatoff[50];
	static Thread B;
	JLabel A;
	public Price(JLabel A) {
		this.A = A;
		
 }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		money=0;
		while(true) {
		try {	
			Thread.sleep(1000);
			money = money + 600;
			
			A.setText(money + " ø¯");
			
			//»£√‚«ÿ¡‡æﬂ¥Ô
				
		} catch (InterruptedException e) {
			// TODO: handle exception
			return;
		}
		}
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
}
