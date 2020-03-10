package PC;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

public class ClientFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	int Number;
	String ID;
	public ClientChat chat;
	public Socket socket;
	public DataInputStream in;
	public DataOutputStream out;
	private JFrame ClientFrame;
	private JButton ExitButton;
	private Thread CConnector;
	private JButton btnMessage;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public ClientFrame(int Number,String ID) {
		setLocationRelativeTo(null);

		this.Number=Number;
		this.ID=ID;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 227, 115);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(255, 255, 255, 255));
		
		
		JLabel lblNum2 = new JLabel("자 리 번 호");
		lblNum2.setBounds(2, 0, 70, 18);
		contentPane.add(lblNum2);
		
		JLabel lblNum3 = new JLabel("사용자 ID");
		lblNum3.setBounds(70, 0, 72, 18);
		contentPane.add(lblNum3);
		
		JLabel lblNum4 = new JLabel("이 용 요 금");
		lblNum4.setBounds(142, 0, 70, 18);
		contentPane.add(lblNum4);
		
		
		JLabel lblNum = new JLabel("New label");
		lblNum.setBounds(25, 25, 62, 18);
		contentPane.add(lblNum);
		lblNum.setText(Integer.toString(Number));
		
		JLabel lblName = new JLabel("New label");
		lblName.setBounds(70, 25, 72, 18);
		contentPane.add(lblName);
		lblName.setText(ID);
		
		ExitButton = new JButton("\uC885\uB8CC");
		ExitButton.setBounds(106, 50, 105, 27);
		ExitButton.addActionListener(this);
		contentPane.add(ExitButton);
		
		JLabel ThreadPrice = new JLabel("New label");
		ThreadPrice.setBounds(140, 25, 62, 18);
		contentPane.add(ThreadPrice);
		
		btnMessage = new JButton("\uBA54\uC2DC\uC9C0");
		btnMessage.setBounds(0, 50, 105, 27);
		btnMessage.addActionListener(this);
		contentPane.add(btnMessage);
		
		setVisible(true);
		Runnable A = new ClientConnector();
		CConnector = new Thread(A);
		CConnector.start();
		Price price = new Price(ThreadPrice);
		price.start();
	}
	
	// 서버와 연결을 위한 클라이언트 커넥터
	private class ClientConnector implements Runnable {

		@Override
		public void run() {
			try {
				String serverIp = "127.0.0.1";// "172.168.0.80";
				socket = new Socket(InetAddress.getByName(serverIp), 7777);
				System.out.println("연결성공");
				in = new DataInputStream(new BufferedInputStream(
						socket.getInputStream()));
				out = new DataOutputStream(new BufferedOutputStream(
						socket.getOutputStream()));

				
				out.writeInt(Number);
				out.writeUTF(ID);
				
				out.writeUTF("로그인");
				out.flush();
				while(true) {
					String str =in.readUTF();
					if (str.equals("메시지")) {
						String msg = in.readUTF();
						System.out.println(msg);
						if (chat == null) {
							chat = new ClientChat(out, Number);
						}
						chat.chatFrameVisible();
						chat.addChat(msg);

					}
				}
				


			} catch (IOException e) {// 서버와 연결이 끊어질시 창이 변함

				if (chat != null) {
					chat.closeFrame();
				}


			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

	}// 클라이언트 커넥터종료 

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object obj = arg0.getSource();
		
		if(obj==ExitButton) {
		

			try {
				out = new DataOutputStream(new BufferedOutputStream(
						socket.getOutputStream()));
				out.writeUTF("로그아웃");
				out.flush();
				socket.close();
				System.exit(0);
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(obj==btnMessage) {
			chat = new ClientChat(out,Number);
			chat.chatFrameVisible();
		}
	}
	class Price extends Thread{
		JLabel A ;
		public Price(JLabel A) {
			this.A = A;
			
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int money = 0;
			while(true) {
			try {

				Thread.sleep(1000);
				money = money +600;
				A.setText(money + "원");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
	}
	
}

