package frame;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import filesManageSystem.*;

@SuppressWarnings("serial")
public class FileFrame extends JFrame {

		// 中间容器
		private JPanel contentPane;
		// 多页面容器
		private JTabbedPane tabbedPane;

		// 上传文件页面及组件
		private JPanel Upload_Panel;
		private JLabel FileID_Label;
		private JLabel Filedescription_Label;
		private JLabel Filename_Label;
		private JTextField FileID_Txt;
		private JTextArea Filedescription_Txt;
		private JTextField Filepath_Txt;
		private JButton Upload_Button;
		private JButton OpenFile_Button;
		private JButton Return_Button1;

		// 下载文件页面及组件
		private JPanel Download_Panel;
		private JScrollPane scrollPane;
		private JTable Files_table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileFrame frame = new FileFrame(0);
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
	public FileFrame(int choice) {
		// 传入用户及页面选项: 0上传文件 1下载文件
		// 框架
		setTitle("文件管理界面");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(360, 150, 802, 581);

		// 中间容器
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// 多页面容器
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);

		// 上传页面
		Upload_Panel = new JPanel();
		tabbedPane.addTab("文件上传", null, Upload_Panel, null);
		Upload_Panel.setLayout(null);

		// 档案号标签
		FileID_Label = new JLabel("\u6863\u6848\u53F7");
		FileID_Label.setFont(new Font("黑体", Font.PLAIN, 20));
		FileID_Label.setBounds(85, 33, 60, 36);
		Upload_Panel.add(FileID_Label);

		// 文件描述标签
		Filedescription_Label = new JLabel("\u6863\u6848\u63CF\u8FF0");
		Filedescription_Label.setFont(new Font("黑体", Font.PLAIN, 20));
		Filedescription_Label.setBounds(85, 96, 80, 36);
		Upload_Panel.add(Filedescription_Label);

		// 文件名标签
		Filename_Label = new JLabel("\u6863\u6848\u6587\u4EF6\u540D");
		Filename_Label.setFont(new Font("黑体", Font.PLAIN, 20));
		Filename_Label.setBounds(85, 314, 100, 36);
		Upload_Panel.add(Filename_Label);

		// 档案号文本域
		FileID_Txt = new JTextField();
		FileID_Txt.setBounds(230, 40, 272, 27);
		Upload_Panel.add(FileID_Txt);
		FileID_Txt.setColumns(10);

		// 文件描述文本域
		Filedescription_Txt = new JTextArea();
		Filedescription_Txt.setBounds(230, 104, 272, 199);
		Upload_Panel.add(Filedescription_Txt);
		Filedescription_Txt.setColumns(10);

		// 文件名文本域
		Filepath_Txt = new JTextField();
		Filepath_Txt.setColumns(10);
		Filepath_Txt.setBounds(230, 321, 272, 27);
		Upload_Panel.add(Filepath_Txt);

		// 上传按钮
		Upload_Button = new JButton("\u4E0A\u4F20");
		Upload_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 上传文件事件
				UploadActionPerformed(e);
			}
		});
		Upload_Button.setBounds(215, 380, 95, 27);
		Upload_Button.setFont(new Font("黑体", Font.PLAIN, 20));
		Upload_Panel.add(Upload_Button);

		// 返回按钮
		Return_Button1 = new JButton("\u8FD4\u56DE");
		Return_Button1.setBounds(422, 380, 95, 27);
		Return_Button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 返回事件
				ReturnActionPerformed(e);
			}
		});

		// 打开按钮
		OpenFile_Button = new JButton("\u6253\u5F00");
		OpenFile_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 打开文件事件
				OpenFileActionPerformed(e);
			}
		});
		OpenFile_Button.setFont(new Font("黑体", Font.PLAIN, 18));
		OpenFile_Button.setBounds(546, 320, 95, 27);
		Upload_Panel.add(OpenFile_Button);
		Return_Button1.setFont(new Font("黑体", Font.PLAIN, 20));
		Upload_Panel.add(Return_Button1);

		// 下载页面
		Download_Panel = new JPanel();
		tabbedPane.addTab("文件下载", null, Download_Panel, null);
		tabbedPane.setEnabledAt(1, true);
		Download_Panel.setLayout(null);

		// 可下拉容器
		scrollPane = new JScrollPane();
		scrollPane.setBounds(116, 41, 512, 291);
		Download_Panel.add(scrollPane);

		// 下载文件列表
		Files_table = new JTable();
		// 构造表格
		ConstructFileTable();
		// 加入可下拉区域
		scrollPane.setViewportView(Files_table);
		
		JButton Download_Button = new JButton("下载");
		Download_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//下载文件
				DownloadActionPerformed(e);
			}
		});
		Download_Button.setFont(new Font("黑体", Font.PLAIN, 20));
		Download_Button.setBounds(215, 380, 95, 27);
		Download_Panel.add(Download_Button);
		
		JButton Return_Button2 = new JButton("返回");
		Return_Button2.setFont(new Font("黑体", Font.PLAIN, 20));
		Return_Button2.setBounds(422, 380, 95, 27);
		Return_Button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 返回事件
				ReturnActionPerformed(e);
			}
		});
		Download_Panel.add(Return_Button2);

		// 设置权限及页面
		setPane( choice);
	}
	// 表格构造
		private void ConstructFileTable() {

			// 表头数据
			String[] columnNames = { "\u6863\u6848\u53F7", "\u521B\u5EFA\u8005", "\u65F6\u95F4", "\u6587\u4EF6\u540D",
					"\u6587\u4EF6\u63CF\u8FF0" };
			// 表格数据
			String[][] rowData = new String[20][5];

			Enumeration<Doc> f;
			try {
				// 获取哈希表信息
				f = DataProcessing.listDoc();

				// 行数
				int row = 0;
				// 将哈希表信息导入至表格
				while (f.hasMoreElements()) {
					Doc doc = f.nextElement();
					rowData[row][0] = doc.getId();
					rowData[row][1] = doc.getCreator();
					rowData[row][2] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(doc.getTimestamp()); // Time转String
					rowData[row][3] = doc.getFilename();
					rowData[row][4] = doc.getDescription();
					row++;
				}

			}catch (SQLException e) {
				JOptionPane.showMessageDialog(null,"数据库错误","输入反馈",JOptionPane.YES_NO_OPTION);
			}

			// 构造表格
			Files_table.setModel(new DefaultTableModel(rowData, columnNames) {

				boolean[] columnEditables = new boolean[] { false, false, false, false, false };

				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}

			});

		}
		
		// 打开文件
		private void OpenFileActionPerformed(ActionEvent evt) {

			// 弹出文件选择框
			FileDialog OpenFileDialog = new FileDialog(this, "选择上传文件");
			OpenFileDialog.setVisible(true);

			// 获取文件路径
			String filepath = OpenFileDialog.getDirectory() + OpenFileDialog.getFile();
			Filepath_Txt.setText(filepath);

		}

		// 上传文件

		@SuppressWarnings("null")
		private void UploadActionPerformed(ActionEvent evt) {
			String filepath = Filepath_Txt.getText();
			String fileID = FileID_Txt.getText();
			String filedescription = Filedescription_Txt.getText();

			if (filepath.isEmpty()) {
				JOptionPane.showMessageDialog(null, "未选择文件！");
				return;
			}
			if (fileID.isEmpty()) {
				JOptionPane.showMessageDialog(null, "未输入档案号！");
				return;
			}
			if (filedescription.isEmpty()) {
				JOptionPane.showMessageDialog(null, "未输入文件描述！");
				return;
			}

			try {
				File file=new File(filepath);
				if (DocClient.file_up(fileID,LoginUser.getName(),filedescription,file)) {

					ConstructFileTable(); // 更新表格数据
					JOptionPane.showMessageDialog(null, "上传成功!");
					return;

				} else {
					JOptionPane.showMessageDialog(null, "上传失败!档案号已存在");
					return;
				}
			} catch( IOException e ) {
				JOptionPane.showMessageDialog(null,"服务器连接错误，请检查网络","输入反馈",JOptionPane.YES_NO_OPTION);
			} catch (ClassNotFoundException e) {
				System.out.println("Class not found: " + e.getMessage());
				e.printStackTrace();
			}

		}

		// 下载文件
		private void DownloadActionPerformed(ActionEvent evt) {
			// 弹出文件选择框
			FileDialog OpenFileDialog = new FileDialog(this, "选择保存位置", FileDialog.SAVE);
			OpenFileDialog.setVisible(true);

			// 获取文件路径
			DocClient.selfdownloadPath = OpenFileDialog.getDirectory();
			String filename=OpenFileDialog.getFile();


			// 获取所选行序号, 若未选择其值为-1
			int selectedrow = Files_table.getSelectedRow();

			if (selectedrow == -1) { // 未选择文件的情况
				JOptionPane.showMessageDialog(null, "未选择文件！");
				return;
			} else {

				// 获取档案号
				String fileID = (String) Files_table.getValueAt(selectedrow, 0);
				// 若选择空行
				if (fileID.isEmpty()) {
					return;
				}

				// 显示确认界面: 信息, 标题, 选项个数
				int value = JOptionPane.showConfirmDialog(null, "确定要下载文件吗？", "文件下载确认界面", 2);
				// Yes=0 No=1
				if (value == 0) {

					try {
						File file=new File(filename);
						if (DocClient.file_down(fileID,file)) {
							JOptionPane.showMessageDialog(null, "下载成功！");
							return;
						} else {
							JOptionPane.showMessageDialog(null, "下载失败！");
							return;
						}
					} catch (HeadlessException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
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

		// 设置页面
		private void setPane( int choice) {

			if (!LoginUser.getRole().equalsIgnoreCase("operator")) {
				FileID_Txt.setEditable(false);
				Filedescription_Txt.setEditable(false);
				Filepath_Txt.setEditable(false);
				Upload_Button.setEnabled(false);
				OpenFile_Button.setEnabled(false);
			}

			if (choice == 0) {
				tabbedPane.setSelectedComponent(Upload_Panel);
			} else if (choice == 1) {
				tabbedPane.setSelectedComponent(Download_Panel);
			}

		}

		// 返回
		private void ReturnActionPerformed(ActionEvent evt) {
			this.dispose();
		}
}
