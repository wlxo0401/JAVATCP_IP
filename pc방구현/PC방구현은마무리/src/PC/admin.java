package PC;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class admin extends JFrame implements ActionListener{
	private JPanel contentPane;
	private JButton btn;
	private JTextField tfID;

	public admin(String title) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(758, 473);
		setLocationRelativeTo(null);
		setBackground(Color.WHITE);
		setVisible(true);
		setBounds(100, 100, 280, 120);
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(255, 255, 255, 255));
		
		
		tfID = new JTextField();
		tfID.setBounds(10,30, 150, 27);
		contentPane.add(tfID);

		
		btn = new JButton("로그인");
		btn.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		btn.setBounds(165, 5, 95, 70);
		btn.addActionListener(this);
		contentPane.add(btn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub\
		
		Object obj = e.getSource();
		DB_Conn dc = new DB_Conn();
		
		if(obj==btn) {
			// TODO Auto-generated method stub
				String sql = "select id from admin where id = '" + tfID.getText() +"'";
				System.out.println(sql);
				ResultSet rs = dc.excuteQuery(sql);
				

				try {
					if(rs.next()) {
						String id = rs.getString("id"); //아이디를 가저온다.
						if(tfID.getText().equals(id)) {
		
							HostPC frame = new HostPC();
							frame.setVisible(true);
							this.setVisible(false);//창 끄기 완전하게 꺼지는건 아니고 화면에서 없어집니다.
							}
		
						
						
					}else {
						JOptionPane.showMessageDialog(null, "로그인 실패!!", "알림", JOptionPane.WARNING_MESSAGE);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		
	}
	

}
