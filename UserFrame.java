package frame;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.util.Enumeration;
import filesManageSystem.*;

@SuppressWarnings("serial")
public class UserFrame extends JFrame {

	// 中间容器
	private JPanel contentPane;
	// 多页面容器
	private JTabbedPane tabbedPane;

	// 增添用户页面及组件
	private JPanel AddUser_Panel;
	private JLabel Username_Label1;
	private JLabel Password_Label1;
	private JLabel Role_Label1;
	private JTextField Username_Txt1;
	private JPasswordField Password_Txt1;
	@SuppressWarnings("rawtypes")
	private JComboBox Role_ComboBox1;
	private JButton Confirm_Button1;
	private JButton Return_Button1;

	// 删除用户页面及组件
	private JPanel DelUser_Panel;
	private JScrollPane scrollPane;
	private JTable Users_table;
	private JButton Confirm_Button2;
	private JButton Return_Button2;

	// 修改用户页面及组件
	private JPanel UpdateUser_Panel;
	private JLabel Username_Label2;
	private JLabel Password_Label2;
	private JLabel Role_Label2;
	@SuppressWarnings("rawtypes")
	private JComboBox User_ComboBox;
	private DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
	private JPasswordField Password_Txt2;
	@SuppressWarnings("rawtypes")
	private JComboBox Role_ComboBox;
	private JButton Confirm_Button3;
	private JButton Return_Button3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserFrame frame = new UserFrame(0);
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UserFrame( int choice) {
		// 传入用户及页面选项: 0 增添用户 1 删除用户 2 修改用户
		// 框架
		setResizable(false);
		setTitle("用户管理界面");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 220, 500, 400);

		// 中间容器
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// 多页面容器
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("黑体", Font.PLAIN, 14));
		contentPane.add(tabbedPane);

		// 添加用户页面
		AddUser_Panel = new JPanel();
		// 增添选项卡
		tabbedPane.addTab("新增用户", null, AddUser_Panel, null);
		AddUser_Panel.setLayout(null);

		// 用户名标签
		Username_Label1 = new JLabel("用户名");
		Username_Label1.setFont(new Font("黑体", Font.PLAIN, 18));
		Username_Label1.setBounds(110, 66, 61, 32);
		AddUser_Panel.add(Username_Label1);

		// 密码标签
		Password_Label1 = new JLabel("密码");
		Password_Label1.setFont(new Font("黑体", Font.PLAIN, 18));
		Password_Label1.setBounds(110, 108, 43, 32);
		AddUser_Panel.add(Password_Label1);

		// 角色标签
		Role_Label1 = new JLabel("角色");
		Role_Label1.setFont(new Font("黑体", Font.PLAIN, 18));
		Role_Label1.setBounds(110, 150, 43, 32);
		AddUser_Panel.add(Role_Label1);

		// 用户名文本域
		Username_Txt1 = new JTextField();
		Username_Txt1.setBounds(174, 72, 181, 24);
		Username_Txt1.setColumns(10);
		AddUser_Panel.add(Username_Txt1);

		// 密码文本域
		Password_Txt1 = new JPasswordField();
		Password_Txt1.setBounds(174, 114, 181, 24);
		AddUser_Panel.add(Password_Txt1);

		// 角色选项栏
		Role_ComboBox1 = new JComboBox();
		Role_ComboBox1.setEditable(true);
		Role_ComboBox1.setModel(new DefaultComboBoxModel(new String[] { "", "administrator", "browser", "operator" }));
		Role_ComboBox1.setBounds(174, 156, 181, 24);
		AddUser_Panel.add(Role_ComboBox1);

		// 增加按钮
		Confirm_Button1 = new JButton("增加");
		Confirm_Button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 增添用户事件
				AddUserActionPerformed(e);
			}
		});
		Confirm_Button1.setFont(new Font("黑体", Font.PLAIN, 18));
		Confirm_Button1.setBounds(97, 222, 113, 27);
		AddUser_Panel.add(Confirm_Button1);

		// 返回按钮
		Return_Button1 = new JButton("返回");
		Return_Button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 返回事件
				ReturnActionPerformed(e);
			}
		});
		Return_Button1.setFont(new Font("黑体", Font.PLAIN, 18));
		Return_Button1.setBounds(251, 222, 113, 27);
		AddUser_Panel.add(Return_Button1);

		// 修改用户页面
		UpdateUser_Panel = new JPanel();
		UpdateUser_Panel.setLayout(null);
		tabbedPane.addTab("\u4FEE\u6539\u7528\u6237", null, UpdateUser_Panel, null);

		// 用户名标签
		Username_Label2 = new JLabel("\u7528\u6237\u540D");
		Username_Label2.setFont(new Font("黑体", Font.PLAIN, 18));
		Username_Label2.setBounds(110, 66, 61, 32);
		UpdateUser_Panel.add(Username_Label2);

		// 密码标签
		Password_Label2 = new JLabel("\u5BC6\u7801");
		Password_Label2.setFont(new Font("黑体", Font.PLAIN, 18));
		Password_Label2.setBounds(110, 108, 43, 32);
		UpdateUser_Panel.add(Password_Label2);

		// 角色标签
		Role_Label2 = new JLabel("\u89D2\u8272");
		Role_Label2.setFont(new Font("黑体", Font.PLAIN, 18));
		Role_Label2.setBounds(110, 150, 43, 32);
		UpdateUser_Panel.add(Role_Label2);

		//用户选项栏
		User_ComboBox = new JComboBox();
		ConstructUserList();

		// 密码文本域
		Password_Txt2 = new JPasswordField();
		Password_Txt2.setBounds(174, 114, 181, 24);
		UpdateUser_Panel.add(Password_Txt2);

		// 角色选项栏
		Role_ComboBox = new JComboBox();
		Role_ComboBox.setModel(new DefaultComboBoxModel(new String[] { "", "administrator", "browser", "operator" }));
		Role_ComboBox.setEditable(true);
		Role_ComboBox.setBounds(174, 156, 181, 24);
		UpdateUser_Panel.add(Role_ComboBox);

		// 修改按钮
		Confirm_Button3 = new JButton("\u4FEE\u6539");
		Confirm_Button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 修改用户事件
				UpdateUserActionPerformed(e);
			}
		});
		Confirm_Button3.setFont(new Font("黑体", Font.PLAIN, 18));
		Confirm_Button3.setBounds(97, 222, 113, 27);
		UpdateUser_Panel.add(Confirm_Button3);

		// 返回按钮
		Return_Button3 = new JButton("\u8FD4\u56DE");
		Return_Button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 返回事件
				ReturnActionPerformed(e);
			}
		});
		Return_Button3.setFont(new Font("黑体", Font.PLAIN, 18));
		Return_Button3.setBounds(251, 222, 113, 27);
		UpdateUser_Panel.add(Return_Button3);

		// 删除用户页面
		DelUser_Panel = new JPanel();
		tabbedPane.addTab("\u5220\u9664\u7528\u6237", null, DelUser_Panel, null);
		DelUser_Panel.setLayout(null);

		// 删除按钮
		Confirm_Button2 = new JButton("\u5220\u9664");
		Confirm_Button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 删除事件
				DelUserActionPerformed(e);
			}
		});
		Confirm_Button2.setBounds(105, 238, 113, 27);
		Confirm_Button2.setFont(new Font("黑体", Font.PLAIN, 18));
		DelUser_Panel.add(Confirm_Button2);

		// 返回按钮
		Return_Button2 = new JButton("\u8FD4\u56DE");
		Return_Button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 返回事件
				ReturnActionPerformed(e);
			}
		});
		Return_Button2.setBounds(256, 238, 113, 27);
		Return_Button2.setFont(new Font("黑体", Font.PLAIN, 18));
		DelUser_Panel.add(Return_Button2);

		// 可下拉容器域
		scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 38, 432, 159);
		DelUser_Panel.add(scrollPane);

		// 用户列表
		Users_table = new JTable();
		// 构造表格
		ConstructUserTable();
		// 加入可下拉区域
		scrollPane.setViewportView(Users_table);
		UpdateUser_Panel.add(User_ComboBox);
		// 设置页面
		SetPane(choice);
	}
	
	//用户选项栏构造
	private void ConstructUserList() {
		comboBoxModel.removeAllElements();
		comboBoxModel.addElement("");
		// 获取所有用户的枚举
		try {
			Enumeration<AbstractUser> allUsers = DataProcessing.listUser();

			// 遍历枚举中的所有用户并输出
			while (allUsers.hasMoreElements()) {
				AbstractUser user = allUsers.nextElement();
				comboBoxModel.addElement(user.getName());
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"数据库错误","输入反馈",JOptionPane.YES_NO_OPTION);
		}
		User_ComboBox.setModel(comboBoxModel);
		User_ComboBox.setEditable(true);
		User_ComboBox.setBounds(174, 72, 181, 24);
	}

	// 用户表格构造
	private void ConstructUserTable() {

		// 表头数据
		String[] columnNames = { "\u7528\u6237\u540D", "\u5BC6\u7801", "\u89D2\u8272" };
		// 表格数据
		String[][] rowData = new String[20][3];

		Enumeration<AbstractUser> u;
		try {
			// 获取哈希表信息
			u = DataProcessing.listUser();
			// 行数
			int row = 0;
			// 将哈希表信息导入至表格数据
			while (u.hasMoreElements()) {
				AbstractUser user = u.nextElement();
				rowData[row][0] = user.getName();
				rowData[row][1] = user.getPassword();
				rowData[row][2] = user.getRole();
				row++;
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"数据库错误","输入反馈",JOptionPane.YES_NO_OPTION);
		}

		// 构造表格
		Users_table.setModel(new DefaultTableModel(rowData, columnNames) {

			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

		});

	}

	// 增添
	private void AddUserActionPerformed(ActionEvent evt) {

		// 获取选项内容
		String username = this.Username_Txt1.getText();
		String password = new String(this.Password_Txt1.getPassword());
		String role = (String) this.Role_ComboBox1.getSelectedItem();

		if (username.isEmpty()) {
			JOptionPane.showMessageDialog(null, "未输入用户名！");
			return;
		}
		if (password.isEmpty()) {
			JOptionPane.showMessageDialog(null, "未输入密码！");
			return;
		}
		if (role.isEmpty()) {
			JOptionPane.showMessageDialog(null, "未选择身份！");
			return;
		}

		try {
			
			if (DocClient.user_add(username, password, role)) {
				ConstructUserList();
				ConstructUserTable(); // 更新表格数据
				JOptionPane.showMessageDialog(null, "添加成功！");
				return;
			} else {
				JOptionPane.showMessageDialog(null, "添加失败！用户名已存在！");
				return;
			}

		} catch( IOException e ) {
			JOptionPane.showMessageDialog(null,"服务器连接错误，请检查网络","输入反馈",JOptionPane.YES_NO_OPTION);
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found: " + e.getMessage());
			e.printStackTrace();
		}

	}

	// 删除
	private void DelUserActionPerformed(ActionEvent evt) {

		// 获取所选行序号,若未选择其值为-1
		int selectedrow = Users_table.getSelectedRow();

		if (selectedrow == -1) { // 未选择用户的情况
			JOptionPane.showMessageDialog(null, "未选择用户！");
			return;
		} else {

			// 获取所选行的用户名
			String username = (String) Users_table.getValueAt(selectedrow, 0);
			// 若选择空行
			if (username.isEmpty()) {
				return;
			}
			// 选择自身用户的情况
			if (username.equals(LoginUser.getName())) {
				JOptionPane.showMessageDialog(null, "不能删除自身用户！");
				return;
			}

			// 显示确认界面: 信息, 标题, 选项个数
			int value = JOptionPane.showConfirmDialog(null, "确定要删除用户吗？", "用户删除确认界面", 2);
			// Yes=0 No=1
			if (value == 0) {

				try {

					if (DocClient.user_del(username)) {
						ConstructUserList();
						ConstructUserTable(); // 更新表格数据
						JOptionPane.showMessageDialog(null, "删除成功！");
						return;
					} else {
						JOptionPane.showMessageDialog(null, "删除失败！");
						return;
					}

				} catch( IOException e ) {
					JOptionPane.showMessageDialog(null,"服务器连接错误，请检查网络","输入反馈",JOptionPane.YES_NO_OPTION);
				} catch (ClassNotFoundException e) {
					System.out.println("Class not found: " + e.getMessage());
					e.printStackTrace();
				}

			} else if (value == 1) {
				return;
			}
		}
	}

	// 修改
	private void UpdateUserActionPerformed(ActionEvent evt) {

		String username = (String)this.User_ComboBox.getSelectedItem();
		String password = new String(this.Password_Txt2.getPassword());
		String role = (String) this.Role_ComboBox.getSelectedItem();

		if (username.isEmpty()) {
			JOptionPane.showMessageDialog(null, "未输入用户名！");
			return;
		}
		if (password.isEmpty()) {
			JOptionPane.showMessageDialog(null, "未输入密码！");
			return;
		}
		if (role.isEmpty()) {
			JOptionPane.showMessageDialog(null, "未选择身份！");
			return;
		}

		try {
			if(!LoginUser.getPassword().equals(password)) {
				JOptionPane.showMessageDialog(null, "用户名与密码不匹配！");
				return;
			} else {

				// 显示确认界面：信息，标题，选项个数
				int value = JOptionPane.showConfirmDialog(null, "确定要修改信息吗？", "信息修改确认界面", 2);

				// Yes=0 No=1
				if (value == 0) {
					if (DocClient.user_mod(username, password, role)) {
						ConstructUserTable(); // 更新表格数据
						JOptionPane.showMessageDialog(null, "修改成功！");
						return;
					} else {
						JOptionPane.showMessageDialog(null, "修改失败！");
						return;
					}
				} else if (value == 1) {
					return;
				}
			}

		}  catch( IOException e ) {
			JOptionPane.showMessageDialog(null,"服务器连接错误，请检查网络","输入反馈",JOptionPane.YES_NO_OPTION);
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// 设置页面
	private void SetPane(int value) {
		if (value == 0) {
			tabbedPane.setSelectedComponent(AddUser_Panel);
		} else if (value == 1) {
			tabbedPane.setSelectedComponent(DelUser_Panel);
		} else if (value == 2) {
			tabbedPane.setSelectedComponent(UpdateUser_Panel);
		}
	}

	// 返回
	private void ReturnActionPerformed(ActionEvent evt) {
		this.dispose();
	}
}
