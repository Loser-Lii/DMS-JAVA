package frame;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.*;
import filesManageSystem.*;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	
	private JMenuBar menuBar;
	private JMenu menuUser,menuFile,menuSelf;
	private JMenuItem menuItemAddUser,menuItemModUser,menuItemDelUser,menuItemListUser,menuItemUpDoc,menuItemDownDoc,menuItemListDoc,menuItemModSelf;
	private UserFrame userframe=null;
	private SelfFrame selfframe=null;
	private FileFrame fileframe=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		initFrame();
		
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 添加窗口监听器来断开连接
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DocClient.closeConnection();
            }
        });
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuUser = new JMenu("用户管理");
		menuBar.add(menuUser);
		
		menuItemAddUser = new JMenuItem("新增用户");
		menuItemAddUser.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemAddUserActionPerformed(e);
			}
		});
		menuUser.add(menuItemAddUser);
		
		menuItemModUser = new JMenuItem("修改用户");
		menuItemModUser.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemModUserActionPerformed(e);
			}
		});
		menuUser.add(menuItemModUser);
		
		menuItemDelUser = new JMenuItem("删除用户");
		menuItemDelUser.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemDelUserActionPerformed(e);
			}
		});
		menuUser.add(menuItemDelUser);
		/*
		menuItemListUser = new JMenuItem("用户列表");
		menuItemListUser.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemListUserActionPerformed(e);
			}
		});
		menuUser.add(menuItemListUser);
		*/
		menuFile = new JMenu("档案管理");
		menuBar.add(menuFile);
		
		menuItemUpDoc = new JMenuItem("档案上传");
		menuItemUpDoc.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemUpDocActionPerformed(e);
			}
		});
		menuFile.add(menuItemUpDoc);
		
		menuItemDownDoc = new JMenuItem("档案下载");
		menuItemDownDoc.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemDownDocActionPerformed(e);
			}
		});
		menuFile.add(menuItemDownDoc);
	/*	
		menuItemListDoc = new JMenuItem("档案列表");
		menuItemListDoc.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemListDocActionPerformed(e);
			}
		});
		menuFile.add(menuItemListDoc);
		*/
		menuSelf = new JMenu("个人信息管理");
		menuBar.add(menuSelf);
		
		menuItemModSelf = new JMenuItem("信息修改");
		menuItemModSelf.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemModSelfActionPerformed(e);
			}
		});
		menuSelf.add(menuItemModSelf);
		
		setVisible(true);
	}
	
	int choice;
	//新增用户
	private void menuItemAddUserActionPerformed(ActionEvent evt) {
		choice=0;
		UserFrame userframe=new UserFrame(choice);
		userframe.setVisible(true);
	}
	
	//删除用户
	private void menuItemDelUserActionPerformed(ActionEvent evt) {
		choice=1;
		UserFrame userframe=new UserFrame(choice);
		userframe.setVisible(true);
	}
	
	//修改用户
	private void menuItemModUserActionPerformed(ActionEvent evt) {
		choice=2;
		AbstractUser user = null;
		UserFrame userframe=new UserFrame(choice);
		userframe.setVisible(true);
	}
	
	//档案上传
	private void menuItemUpDocActionPerformed(ActionEvent evt) {
		choice=0;
		FileFrame fileframe = new FileFrame(choice);
		fileframe.setVisible(true);
	}
	//档案下载
	private void menuItemDownDocActionPerformed(ActionEvent evt) {
		choice=1;
		FileFrame fileframe = new FileFrame(choice);
		fileframe.setVisible(true);
	}
	
	/*//档案列表
	private void menuItemListDcActionPerformed(ActionEvent evt) {
		FileFrame fileframe = new FileFrame();
		fileframe.setVisible(true);
	}*/
	//信息修改
	private void menuItemModSelfActionPerformed(ActionEvent evt) {
		SelfFrame selfframe = new SelfFrame();
		selfframe.setVisible(true);
	}
	private void initFrame() {
		//获得Toolkit对象
		Toolkit toolkit=getToolkit();
		//获得Dimension对象
		Dimension dimension = toolkit.getScreenSize();
		//获得屏幕高度
		int screenHeight=dimension.height;
		//获得屏幕宽度
		int screenWidth=dimension.width;
		//获得窗体高度
		this.setSize(screenWidth,screenHeight);
		
		this.setLocation(0,0);
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		panel=new JPanel();
		getContentPane().add(panel,BorderLayout.CENTER);
		panel.setLayout(null);
	}
	
	public void setAdministratorFrame() {
		setTitle("系统管理人员界面");
		menuItemUpDoc.setEnabled(false);
	}
	
	public void setOperatorFrame() {
		setTitle("档案录入人员界面");
		menuUser.setEnabled(false);
	}
	
	public void setBrowserFrame() {
		setTitle("档案浏览人员界面");
		menuUser.setEnabled(false);
		menuItemUpDoc.setEnabled(false);
	}
	

}
