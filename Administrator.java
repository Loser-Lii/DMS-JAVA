package filesManageSystem;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;

import frame.MainFrame;
/**
 * TODO 系统管理人员类,实现系统管理人员权限
 *
 * @author LiJinjie
 * &#064;date  2024/11/15
 */
public class Administrator extends AbstractUser implements Serializable {

	public Administrator(String name, String password, String role) {
		super(name, password, role);
	}

	public boolean changeUserInfo(String name, String password, String role) throws SQLException {
		if (DataProcessing.updateUser(name, password, role)) {
			System.out.println("修改成功!");
			return true;
		} else {
            return false;
        }
	}

	public boolean delUser(String name) throws SQLException {
		if (DataProcessing.deleteUser(name)) {
			System.out.println("删除成功!");
			return true;
		} else {
            return false;
        }
	}

	public boolean addUser(String name, String password, String role) throws SQLException {
		if (DataProcessing.insertUser(name, password, role)) {
			System.out.println("添加成功!");
			return true;
		} else {
            return false;
        }
	}

	public void listUser() throws SQLException {
		// 获取所有用户的枚举
		Enumeration<AbstractUser> allUsers = DataProcessing.listUser();

		// 遍历枚举中的所有用户并输出
		while (allUsers.hasMoreElements()) {
			AbstractUser user = allUsers.nextElement();
			System.out.println(
					"Name:" + user.getName() + "\tPassword:" + user.getPassword() + "\tRole:" + user.getRole());
		}

	}
	@Override
	public void showMenu(){
		
		//GUI
		MainFrame mainframe=new MainFrame();
		mainframe.setAdministratorFrame();
		mainframe.setVisible(true);
		
		/*
		String tipMenu = "请输入正确的数字!";
		String name, password, role;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("****欢迎进入管理人员菜单****");
			System.out.println("******************");
			System.out.println("\t1.新增用户");
			System.out.println("\t2.删除用户");
			System.out.println("\t3.修改用户");
			System.out.println("\t4.用户列表");
			System.out.println("\t5.下载档案");
			System.out.println("\t6.档案列表");
			System.out.println("\t7.修改个人密码");
			System.out.println("\t8.退出登录");
			System.out.println("******************");
			System.out.print("请选择菜单:");

			String input = scanner.next().trim();
			if (!(input).matches("[12345678]")) {
				System.out.println(tipMenu);
				continue;
			}
			int nextInt = Integer.parseInt(input);
			try {
				
				switch (nextInt) {
				case 1:
					System.out.println("**新增用户**");
					System.out.print("请输入用户名:");
					name = scanner.next();
					System.out.print("请输入密码:");
					password = scanner.next();
					System.out.print("请输入角色:");
					role = scanner.next();
					if (addUser(name, password, role)) {
						System.out.println("新增成功!");
					} else {
						System.out.println("新增失败!");
					}
					break;
				case 2:
					System.out.println("**删除用户**");
					System.out.print("请输入用户名:");
					name = scanner.next();
					if (delUser(name)) {
						System.out.println("删除成功!");
					} else {
						System.out.println("删除失败!");
					}
					break;
				case 3:
					System.out.println("**修改用户**");
					System.out.print("请输入用户名:");
					name = scanner.next();
					System.out.print("请输入密码:");
					password = scanner.next();
					System.out.print("请输入角色:");
					role = scanner.next();
					if (changeUserInfo(name, password, role)) {
						System.out.println("修改成功!");
					} else {
						System.out.println("修改失败!");
					}
					break;
				case 4:
					System.out.println("**用户列表**");
					listUser();
					break;
				case 5:
					System.out.println("**下载档案**");
					System.out.print("请输入档案号:");
					String id = scanner.next();
					if (downloadFile(id)) {
						System.out.println("下载成功!");
					} else {
						System.out.println("下载失败!");
					}
					break;
				case 6:
					System.out.println("**档案列表**");
					showFileList();
					break;
				case 7:
					System.out.println("**修改个人密码**");
					System.out.print("请输入新密码:");
					String newPassword = scanner.next();
					if (changeSelfInfo(newPassword)) {
						System.out.println("修改成功!");
					} else {
						System.out.println("修改失败!");
					}
					break;
				case 8:
					System.out.println("系统退出，谢谢使用!");
					System.exit(0);
					scanner.close();
					break;
				default:
				}
			} catch (IOException e) {
				System.out.println("文件操作失败：" + e.getMessage());
			} catch (SQLException e) {
				System.out.println("数据库操作失败：" + e.getMessage());
			} catch (Exception e) {
				System.out.println("发生未知错误：" + e.getMessage());
			}
		}*/
	}
}
