package PC;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;



@SuppressWarnings("serial")
public class MsgToCustomer extends JFrame implements ActionListener {


	public JTextArea ta = new JTextArea(25, 25);
	JTextField tf = new JTextField(15);
	public boolean flag;

	int num;
	Socket socket;
	DataInputStream in;
	DataOutputStream out;

	public MsgToCustomer(int num,Socket socket) {
		this.num = num;
		this.socket = socket;
		flag = true;
		setSize(300, 300);

		// ������ ȭ�� �߾� �迭
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) ,
				(screenSize.height - frameSize.height) );

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(num + "�մ԰��� �޽���������â");
		setLayout(new BorderLayout());

		JPanel downPanel = new JPanel();
		JLabel label = new JLabel("������ �޽��� : ");
		downPanel.add(label);
		downPanel.add(tf);

		tf.addActionListener(this);
		add(ta, BorderLayout.CENTER);
		add(downPanel, BorderLayout.SOUTH);
		setVisible(true);

		// ������ �޾ƿ´�.

		try {
			System.out.println(num + "�ڸ�.Ŭ���̾�Ʈ���� ���Ͼ�����!");
			/** �߿� ���⼭ ��������.. vc���� �޾ƿö��� vc.pcseat */
			
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("�޼������մԲ� : �ƿ� ��Ʈ�� �޾ƿ��� ����");
		}

		Thread receiver = new ChatReceiver();
		receiver.start();

	}

	public static void main(String[] args) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tf) {
			// �̰Ŵ� �׳� gui �� ������ �ϱ� ���� ���̴�~
			// ta.append(nick + " : "+tf.getText()+"\n");
			try {

				out.writeUTF("�޽���");
				out.writeUTF("PC������ : " + tf.getText());
				ta.append("PC������ : " + tf.getText() + "\n");
				tf.setText("");

			} catch (IOException e1) {
				System.out.println("gui  �󿡼� �޽��� �����°� ����");
			}
		}

	}// �׼ǳ�

	// ���ù� ����
	class ChatReceiver extends Thread {
		public void run() {
			while (in != null) {
				try {
					String s = in.readUTF();
					System.out.println(s);
					ta.append(s + "\n");
				} catch (IOException e) {
					System.out.println("ä�� ���ù� �޼ҵ� ������ ����� ����");
				}
			}
		}
	}// Ŭ���̾�Ʈ ���ù� ��

}
