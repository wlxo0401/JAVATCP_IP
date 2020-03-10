package PC;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class Login extends JFrame implements ActionListener {
public static void main(String[] args) {
	Login frame = new Login();
    frame.setVisible(true);
}
	private JPanel contentPane;
	private JTextField tfID;
	private JTextField tfNum;
	private JPasswordField tfPW;
	public JButton btnLogin;
	public JButton btnNewmem;
	private JButton btnadmin;

	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(758, 473);
		setBounds(100, 100, 758, 473);
		setLocationRelativeTo(null); 
		contentPane = new JPanel();
		
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(255, 255, 255, 255));

		ImageIcon image1 = new ImageIcon("img/pc.png");
		JLabel lb = new JLabel(image1);
		lb.setBounds(480, 100,256,256);
		contentPane.add(lb);
	     
	     
		
	     
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("HY견고딕", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(50, 82, 140, 59);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setFont(new Font("HY견고딕", Font.BOLD, 20));
		lblPassword.setBounds(50, 176, 140, 59);
		contentPane.add(lblPassword);
		
		JLabel label = new JLabel("\uC790\uB9AC \uBC88\uD638");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("HY견고딕", Font.BOLD, 20));
		label.setBounds(50, 276, 140, 59);
		contentPane.add(label);
		
		tfID = new JTextField();
		tfID.setBounds(289, 93, 158, 40);
		contentPane.add(tfID);
		tfID.setColumns(10);
		
		tfNum = new JTextField();
		tfNum.setColumns(10);
		tfNum.setBounds(289, 287, 158, 40);
		contentPane.add(tfNum);
		
		btnLogin = new JButton("\uB85C\uADF8\uC778");
		btnLogin.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		btnLogin.setBounds(330, 365, 130, 27);
		btnLogin.addActionListener(this);
		contentPane.add(btnLogin);
		
		btnNewmem = new JButton("\uD68C\uC6D0 \uAC00\uC785");
		btnNewmem.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		btnNewmem.setBounds(180, 367, 130, 27);
		btnNewmem.addActionListener(this);
		contentPane.add(btnNewmem);
		
		tfPW = new JPasswordField();
		tfPW.setBounds(289, 187, 158, 40);
		contentPane.add(tfPW);
		
		btnadmin = new JButton("관리자");
		btnadmin.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		btnadmin.setBounds(0, 0, 150, 27);
		btnadmin.addActionListener(this);
		contentPane.add(btnadmin);
		
		
	}
	

	public JTextField getTfID() {
		return tfID;
	}

	public void setTfID(JTextField tfID) {
		this.tfID = tfID;
	}

	public JTextField getTfNum() {
		return tfNum;
	}

	public void setTfNum(JTextField tfNum) {
		this.tfNum = tfNum;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// TODO Auto-generated method stub
		Object obj= e.getSource();
		DB_Conn dc = new DB_Conn();
		
		if(obj ==btnLogin) {
			String sql = "select * from userr where id = '" + tfID.getText() +"' and pw = '" + tfPW.getText() + "'";
			System.out.println(sql);
			ResultSet rs = dc.excuteQuery(sql);
			

			try {
				if(rs.next()) {
					String id = rs.getString("id"); //아이디를 가저온다.
					String pw = rs.getString("pw"); //디비 순서는 1 과 2로 선택가능하고 글로 고를려면 ""
					
					if(tfID.getText().equals(id) && tfPW.getText().equals(pw)) {
	
							new ClientFrame(Integer.parseInt(tfNum.getText()),tfID.getText());
							this.setVisible(false);//창 끄기 완전하게 꺼지는건 아니고 화면에서 없어집니다.
						}
	
					
					
				}else {
					JOptionPane.showMessageDialog(null, "로그인 실패!!", "알림", JOptionPane.WARNING_MESSAGE);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}else if(obj == btnNewmem) {
			System.out.println("qjxms");
			new Gaip("회원가입");
		}else if(obj == btnadmin)
		{
			new admin("관리자 로그인");
			this.setVisible(false);
		}
		
		
		
		
		
		
		
	}
}
