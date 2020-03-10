package PC;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class HostPC extends JFrame {
	ServerReceiver receiver = null;
	private JPanel contentPane;
	Seatoff[] Seatoff = new Seatoff[41];
	Seaton[] Seaton = new Seaton[41];
	HostPC HostPC;
	public static MsgToCustomer[] chatClient= new MsgToCustomer[50];
	public Price Price[] = new Price[50];	
	
	int posXpanSeat, posYpanSeat;
	/**
	 * 
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HostPC frame = new HostPC();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HostPC() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 1500, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setBackground(new Color(255, 255, 255, 255));

		posXpanSeat = 70;
		posYpanSeat = 35;
		
		for (int seat = 0; seat < 41; seat++) {
			
            Seatoff[seat] = new Seatoff(seat);
            if (seat % 10 == 0 && seat != 0) {
                posXpanSeat = 70;
                posYpanSeat += 140;
            }
            Seatoff[seat].setBounds(posXpanSeat, posYpanSeat, 99, 99);
            posXpanSeat += 135;
		add(Seatoff[seat]);
	}
		Seatoff[40].setVisible(false);
		
		new Thread(new HostSocket()).start();
}
//쓰레드 구현해야됨 Host꺼 seaton으로 배치바뀌게 하고 
//멀티쓰레드로 소켓할당하셔야댐	
	public class HostSocket implements Runnable{

		public void run() {

			ServerSocket serverSocket = null;
			Socket socket = null;

			try {
				serverSocket = new ServerSocket(7777);
				System.out.println("호스트 피시 : " + "PC방 호스트가 시작됩니다");

				// 접속을 계속 받아내는 쓰레드~
				while (true) {
					socket = serverSocket.accept();
					System.out.println("호스트 피시 : " + "[" + socket.getInetAddress()
							+ "]에서 접속하였다!");

					receiver = new ServerReceiver(socket);
					new Thread(receiver).start();

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public class ServerReceiver implements Runnable{
		Socket socket;
		DataInputStream in;
		DataOutputStream out;
		
		

		// 생성자에서는 서버소켓을 받아서 인풋아웃풋 스트림을 하나 만들고 연결한다~
		ServerReceiver(Socket socket) {
			this.socket = socket;

			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
				System.out.println("호스트 피시 : 인아웃 생성완료!");
			} catch (IOException e) {
				System.out.println("호스트 피시 : " + "서버 리시버 소켓 입출력 에러");
			}
		}// 생성자 끝

		// 리시버 스타트~
		public void run() {
			int num = 0;
			String name = "";

			try {
				num = in.readInt();
				name = in.readUTF();
			
				
				//price.start();
				System.out.println("호스트 피시 : " + "자리 번호는 " + num);
				System.out.println("호스트 피시 : " + "이 사람의 이름은 " + name + "입니다.");
				
				
				
			
				
				while(in != null) {
				
				String s = in.readUTF();
					if(s.equals("로그아웃")) {
						Seatoff[num-1].label[0].setText(num+".빈자리");
						Seatoff[num-1].label[1].setText("");
						Seatoff[num-1].img("gameoff");
						socket.close();
						//금액스레드 종료
						Price[num].interrupt();
						Seatoff[num-1].label[3].setText("");
					}
					if(s.equals("메세지")) {
						String message = in.readUTF();

						if (chatClient[num] == null)
							chatClient[num] = new MsgToCustomer(num,socket);
						chatClient[num].setVisible(true);
						chatClient[num].ta.append(message);
					}
					if(s.equals("로그인")) {
						Seatoff[num-1].label[0].setText(num+".로그인");
						Seatoff[num-1].label[1].setText(name+"님");
						Seatoff[num-1].img("gameon");
						//금액 스레드 시작
						Price[num] = new Price(Seatoff[num-1].label[3]);
						Price[num].start();
					}
					
				}

			} catch (IOException e) {
				System.out.println("호스트피시: " + "클라이언트와의 접속중 에러 : 나가거나..");
				
			} finally {
				// 컴퓨터 꺼지다

			
				System.out.println("호스트피시: " + "컴퓨터가 꺼짐~");

			}
		}// 런끝
	}
	
	
	
	}

	
	

