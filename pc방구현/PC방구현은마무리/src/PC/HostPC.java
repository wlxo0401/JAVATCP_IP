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
//������ �����ؾߵ� Host�� seaton���� ��ġ�ٲ�� �ϰ� 
//��Ƽ������� �����Ҵ��ϼžߴ�	
	public class HostSocket implements Runnable{

		public void run() {

			ServerSocket serverSocket = null;
			Socket socket = null;

			try {
				serverSocket = new ServerSocket(7777);
				System.out.println("ȣ��Ʈ �ǽ� : " + "PC�� ȣ��Ʈ�� ���۵˴ϴ�");

				// ������ ��� �޾Ƴ��� ������~
				while (true) {
					socket = serverSocket.accept();
					System.out.println("ȣ��Ʈ �ǽ� : " + "[" + socket.getInetAddress()
							+ "]���� �����Ͽ���!");

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
		
		

		// �����ڿ����� ���������� �޾Ƽ� ��ǲ�ƿ�ǲ ��Ʈ���� �ϳ� ����� �����Ѵ�~
		ServerReceiver(Socket socket) {
			this.socket = socket;

			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
				System.out.println("ȣ��Ʈ �ǽ� : �ξƿ� �����Ϸ�!");
			} catch (IOException e) {
				System.out.println("ȣ��Ʈ �ǽ� : " + "���� ���ù� ���� ����� ����");
			}
		}// ������ ��

		// ���ù� ��ŸƮ~
		public void run() {
			int num = 0;
			String name = "";

			try {
				num = in.readInt();
				name = in.readUTF();
			
				
				//price.start();
				System.out.println("ȣ��Ʈ �ǽ� : " + "�ڸ� ��ȣ�� " + num);
				System.out.println("ȣ��Ʈ �ǽ� : " + "�� ����� �̸��� " + name + "�Դϴ�.");
				
				
				
			
				
				while(in != null) {
				
				String s = in.readUTF();
					if(s.equals("�α׾ƿ�")) {
						Seatoff[num-1].label[0].setText(num+".���ڸ�");
						Seatoff[num-1].label[1].setText("");
						Seatoff[num-1].img("gameoff");
						socket.close();
						//�ݾ׽����� ����
						Price[num].interrupt();
						Seatoff[num-1].label[3].setText("");
					}
					if(s.equals("�޼���")) {
						String message = in.readUTF();

						if (chatClient[num] == null)
							chatClient[num] = new MsgToCustomer(num,socket);
						chatClient[num].setVisible(true);
						chatClient[num].ta.append(message);
					}
					if(s.equals("�α���")) {
						Seatoff[num-1].label[0].setText(num+".�α���");
						Seatoff[num-1].label[1].setText(name+"��");
						Seatoff[num-1].img("gameon");
						//�ݾ� ������ ����
						Price[num] = new Price(Seatoff[num-1].label[3]);
						Price[num].start();
					}
					
				}

			} catch (IOException e) {
				System.out.println("ȣ��Ʈ�ǽ�: " + "Ŭ���̾�Ʈ���� ������ ���� : �����ų�..");
				
			} finally {
				// ��ǻ�� ������

			
				System.out.println("ȣ��Ʈ�ǽ�: " + "��ǻ�Ͱ� ����~");

			}
		}// ����
	}
	
	
	
	}

	
	

