package frame;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;
import filesManageSystem.*;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame {
	private static String SQLusername = "root"; // 数据库用户
	private static String SQLpassword = "20041123a";

	private JPanel Panel;
	private JLabel UsernameLabel;
	private JLabel PasswordLabel;
	private JTextField UsernameTxtField;
	private JPasswordField PasswordTxtField;
	private JButton ConfirmButton;
	private JButton CancelButton;
	private JLabel Title;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
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
	public LoginFrame() {
				// 框架
				setTitle("系统登录");
				setResizable(false);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setBounds(480, 240, 565, 372);

				// 中间容器
				Panel = new JPanel();
				Panel.setBorder(new EmptyBorder(5, 5, 5, 5));
				setContentPane(Panel);
				
				Title = new JLabel(" 档案管理系统");
				Title.setFont(new Font("黑体", Font.PLAIN, 28));
				Title.setBounds(165, 39, 213, 64);
				Panel.add(Title);

				// 用户名标签
				UsernameLabel = new JLabel("用户名：");
				UsernameLabel.setBounds(128, 132, 80, 40);
				UsernameLabel.setFont(new Font("黑体", Font.PLAIN, 20));

				// 密码标签
				PasswordLabel = new JLabel("密码：");
				PasswordLabel.setBounds(149, 182, 60, 37);
				PasswordLabel.setFont(new Font("黑体", Font.PLAIN, 20));

				// 用户名文本域
				UsernameTxtField = new JTextField();
				UsernameTxtField.setBounds(218, 143, 176, 24);
				UsernameTxtField.setColumns(10);

				// 密码文本域
				PasswordTxtField = new JPasswordField();
				PasswordTxtField.setBounds(218, 191, 176, 24);

				// 确认按钮
				ConfirmButton = new JButton("登录");
				ConfirmButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// 登录事件
						LoginInActionPerformed(e);	
					}
				});
				ConfirmButton.setFont(new Font("黑体", Font.PLAIN, 20));
				ConfirmButton.setBounds(166, 250, 85, 27);

				// 取消按钮
				CancelButton = new JButton("取消");
				CancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// 取消事件
						CancelButtonActionPerformed(e);
					}
				});
				CancelButton.setFont(new Font("黑体", Font.PLAIN, 20));
				CancelButton.setBounds(313, 250, 85, 27);

				// 设置布局与添加部件
				// 绝对布局
				Panel.setLayout(null);
				Panel.add(UsernameLabel);
				Panel.add(PasswordLabel);
				Panel.add(UsernameTxtField);
				Panel.add(PasswordTxtField);
				Panel.add(ConfirmButton,null);
				Panel.add(CancelButton,null);
				// 连接服务器，发送登录消息
				try {
					DocClient.runClient();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
	}
	
	// 登录
		private void LoginInActionPerformed(ActionEvent evt) {
			String username = this.UsernameTxtField.getText();
			String password = new String(this.PasswordTxtField.getPassword()); // 获取输入内容

			if (username.isEmpty()) {
				JOptionPane.showMessageDialog(null, "未输入用户名!"); // 显示对话框
				return;
			}
			if (password.isEmpty()) {
				JOptionPane.showMessageDialog(null, "未输入密码!"); // 显示对话框
				return;
			}
			try {
				DataProcessing.connectToDatabase(SQLusername, SQLpassword);
				AbstractUser user = DocClient.user_login(username, password);
				if(user==null) {
					//System.out.println("用户名口令错误");
					JOptionPane.showMessageDialog(null, "用户名或密码错误,请重新输入","输入反馈",JOptionPane.YES_NO_OPTION); // 显示对话框
				}else {
					LoginUser.setName(username);
					LoginUser.setPassword(password);
					LoginUser.setRole(user.getRole());
					this.dispose();
					user.showMenu();
					//DocClient.closeConnection();
				}
				/*AbstractUser user=DataProcessing.searchUser(username, password);
				if(user==null) {
					//System.out.println("用户名口令错误");
					JOptionPane.showMessageDialog(null, "用户名或密码错误,请重新输入","输入反馈",JOptionPane.YES_NO_OPTION); // 显示对话框
				}else {

					LoginUser.setName(username);
					LoginUser.setPassword(password);
					LoginUser.setRole(user.getRole());
					this.dispose();
					user.showMenu();
				}*/
			} catch (SQLException e) {
				//System.out.println("数据库错误"+e.getLocalizedMessage());
				JOptionPane.showMessageDialog(null,"数据库错误","输入反馈",JOptionPane.YES_NO_OPTION);
			} catch( IOException e ) {
				JOptionPane.showMessageDialog(null,"服务器连接错误，请检查网络","输入反馈",JOptionPane.YES_NO_OPTION);
			} catch (ClassNotFoundException e) {
				System.out.println("Class not found: " + e.getMessage());
				e.printStackTrace();
			}

		}
		
		// 重置文本域
		private void CancelButtonActionPerformed(ActionEvent evt) {
			// 设置为空
			UsernameTxtField.setText(null);
			PasswordTxtField.setText(null);
		}
	
}
