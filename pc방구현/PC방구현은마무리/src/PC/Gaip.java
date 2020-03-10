package PC;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Gaip extends JFrame implements ActionListener{

	
	private JPanel contentPane;
	private JTextField tfID;
	private JTextField tfNum;
	private JButton btngaip;
	private JButton btnNewmem;
	private JPasswordField tfPW;
	private JPasswordField retfPw;
	private DB_Conn dc;
	private JButton btnNumber;

	public Gaip(String title) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(758, 473);
		setLocationRelativeTo(null);
		setBackground(Color.WHITE);
		setVisible(true);
		setBounds(100, 100, 360, 450);
		contentPane = new JPanel();
		
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(255, 255, 255, 255));

		ImageIcon image1 = new ImageIcon("img/gaip.png");
		JLabel lb = new JLabel(image1);
		lb.setBounds(70, 20,220,60);
		contentPane.add(lb);
	     
		
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("HY견고딕", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(-50, 82, 140, 59);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setFont(new Font("HY견고딕", Font.BOLD, 20));
		lblPassword.setBounds(-8, 176, 140, 59);
		contentPane.add(lblPassword);
		
		
		tfID = new JTextField();
		tfID.setBounds(179, 93, 158, 40);
		contentPane.add(tfID);
		tfID.setColumns(10);
		
		tfPW = new JPasswordField();
		tfPW.setBounds(179, 187, 158, 40);
		contentPane.add(tfPW);
		
		
		retfPw = new JPasswordField();
		retfPw.setBounds(179, 232, 158, 40);
		contentPane.add(retfPw);
		
		btnNumber = new JButton("중복확인");
		btnNumber.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		btnNumber.setBounds(179, 138, 158, 40);
		btnNumber.addActionListener(this);
		contentPane.add(btnNumber);
		
		btngaip = new JButton("회원가입");
		btngaip.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		btngaip.setBounds(19, 367, 150, 27);
		btngaip.addActionListener(this);
		contentPane.add(btngaip);
		
		btnNewmem = new JButton("취소");
		btnNewmem.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		btnNewmem.setBounds(179, 365, 150, 27);
		btnNewmem.addActionListener(this);
		contentPane.add(btnNewmem);
		

	}



	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		DB_Conn dc = new DB_Conn();
		
		if(obj == btngaip) {

				if(tfID.getText().isEmpty() == false) {
					if(((JPasswordField) tfPW).getText().isEmpty() == false) {
						if(((JPasswordField) tfPW).getText().equals(retfPw.getText())) {
						String sql = "insert into userr (id, pw)" + "values('"+ tfID.getText() + "','"+ ((JPasswordField) tfPW).getText() +"')";
						JOptionPane.showMessageDialog(null, "회원가입 완료!");
						System.out.println(sql);
						dc.executeUpdate(sql);
						setVisible(false);
						}
						else {
							JOptionPane.showMessageDialog(null, "비밀번호를 확인해주세요","알림", JOptionPane.WARNING_MESSAGE);
						
						}
					}else {
						JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요!","알림", JOptionPane.WARNING_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "사원번호를 입력해주세요!","알림", JOptionPane.WARNING_MESSAGE);
				}
		}
		else if(obj == btnNumber)
        {

            String sql = "select * from userr where id = '" + tfID.getText() +"'";
            System.out.println(sql);
            ResultSet rs = dc.excuteQuery(sql);
            //rs에 내용이 있는지 본다. 
            try {
                if(rs.next()) {
                    String id = rs.getString("id"); //아이디를 가저온다.
                    System.out.println("아이디 가져왔다~");
                    if(tfID.getText().equals(id)) {
                        JOptionPane.showMessageDialog(null, "중복입니다.","알림", JOptionPane.WARNING_MESSAGE);
                        setVisible(false);
                    }


                }else {
                    JOptionPane.showMessageDialog(null, "사용가능합니다.","알림", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        }
		else if(obj == btnNewmem)
		{
			setVisible(false);
		}
		
		
		
		
		
		
	}

}