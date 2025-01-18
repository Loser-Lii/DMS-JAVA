package filesManageSystem;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Scanner;

import frame.MainFrame;
/**
 * TODO 档案浏览人员类,实现档案浏览人员权限
 *
 * @author LiJinjie
 * &#064;date  2024/11/15
 */
public class Browser extends AbstractUser implements Serializable {

	Browser(String name, String password, String role) {
		super(name, password, role);
	}
	@Override
	public void showMenu(){
		
		//GUI
		MainFrame mainframe=new MainFrame();
		mainframe.setBrowserFrame();
		mainframe.setVisible(true);
		
		/*
	    String tipMenu = "请输入正确的数字!";
	    Scanner scanner = new Scanner(System.in);
	    while (true) {
	        System.out.println("****欢迎进入档案浏览人员菜单****");
	        System.out.println("******************");
	        System.out.println("\t1.下载档案");
	        System.out.println("\t2.档案列表");
	        System.out.println("\t3.修改个人密码");
	        System.out.println("\t4.退出登录");
	        System.out.println("******************");
	        System.out.print("请选择菜单:");

	        String input = scanner.next().trim();
	        if (!(input).matches("[1234]")) {
	            System.out.println(tipMenu);
	            continue;
	        }
	        int nextInt = Integer.parseInt(input);
	        try {
	            switch (nextInt) {
	                case 1:
	                    System.out.println("**下载档案**");
	                    System.out.print("请输入档案号:");
	                    String id = scanner.next();
	                    if (downloadFile(id)) {
	                        System.out.println("下载成功!");
	                    } else {
	                        System.out.println("下载失败!");
	                    }
	                    break;
	                case 2:
	                    System.out.println("**档案列表**");
	                    showFileList();
	                    break;
	                case 3:
	                    System.out.println("**修改个人密码**");
	                    System.out.print("请输入新密码:");
	                    String newPassword = scanner.next();
	                    if (changeSelfInfo(newPassword)) {
	                        System.out.println("修改成功!");
	                    } else {
	                        System.out.println("修改失败!");
	                    }
	                    break;
	                case 4:
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
