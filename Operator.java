package filesManageSystem;

import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

import javax.swing.JOptionPane;

import frame.MainFrame;
/**
 * TODO 档案录入人员类,实现档案录入人员权限
 *
 * @author LiJinjie
 * &#064;date  2024/11/15
 */
public class Operator extends AbstractUser implements Serializable {

	public Operator(String name, String password, String role) {
		super(name, password, role);
	}
	@Override
	public void showMenu(){
		//GUI
		MainFrame mainframe=new MainFrame();
		mainframe.setOperatorFrame();
		mainframe.setVisible(true);
		/*
	    String tipMenu = "请输入正确的数字!";
	    Scanner scanner = new Scanner(System.in);
	    while (true) {
	        System.out.println("****欢迎进入档案录入人员菜单****");
	        System.out.println("******************");
	        System.out.println("\t1.上传档案");
	        System.out.println("\t2.下载档案");
	        System.out.println("\t3.档案列表");
	        System.out.println("\t4.修改个人密码");
	        System.out.println("\t5.退出登录");
	        System.out.println("******************");
	        System.out.print("请选择菜单:");

	        String input = scanner.next().trim();
	        if (!(input).matches("[12345]")) {
	            System.out.println(tipMenu);
	            continue;
	        }
	        int nextInt = Integer.parseInt(input);
	        try {
				String id;
	            switch (nextInt) {
	                case 1:
	                    System.out.println("**上传档案**");
	                    System.out.print("请输入源文件名:");
	                    String sourceFilename = scanner.next();
	                    System.out.print("请输入档案号:");
	                    id = scanner.next();
	                    System.out.print("请输入档案描述:");
	                    String description = scanner.next();
	                    if (uploadFile(id, sourceFilename, description)) {
	                        System.out.println("上传成功!");
	                    } else {
	                        System.out.println("上传失败!");
	                    }
	                    break;
	                case 2:
	                    System.out.println("**下载档案**");
	                    System.out.print("请输入档案号:");
						id = scanner.next();
	                    if (downloadFile(id)) {
	                        System.out.println("下载成功!");
	                    } else {
	                        System.out.println("下载失败!");
	                    }
	                    break;
	                case 3:
	                    System.out.println("**档案列表**");
	                    showFileList();
	                    break;
	                case 4:
	                    System.out.println("**修改个人密码**");
	                    System.out.print("请输入新密码:");
	                    String newPassword = scanner.next();
	                    if (changeSelfInfo(newPassword)) {
	                        System.out.println("修改成功!");
	                    } else {
	                        System.out.println("修改失败!");
	                    }
	                    break;
	                case 5:
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
	        }*/
}

	/**
	 * TODO 上传档案文件
	 *
	 * @param id 档案编号
	 * @param sourceFilename 源文件名
	 * @param description 档案描述
	 * @return boolean 上传是否成功
	 * @throws SQLException 如果数据库操作失败时抛出
	 * @throws IOException 如果文件操作失败时抛出
	 */
	public boolean uploadFile(String fileID, String filepath, String filedescription) {

		// 输入文件对象
		File input_file = new File(filepath);
		// 获取文件名
		String filename = input_file.getName();
		// 获取当前时间
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		try {

			if (DataProcessing.insertDoc(fileID, this.getName(), timestamp, filedescription, filename)) {
				
				// 输入过滤器流,建立在文件流上
				BufferedInputStream input = new BufferedInputStream(new FileInputStream(input_file));

				// 输出文件对象
				File output_file = new File(uploadPath + input_file.getName());
				// 创建文件
				output_file.createNewFile();
				// 输出过滤器流,建立在文件流上
				BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(output_file));

				// 用字节数组存取数据
				byte[] bytes = new byte[1024];
				// 文件写入操作
				int length = 0;
				while ((length = input.read(bytes)) != -1) {
					output.write(bytes, 0, length);
				}

				// 关闭流
				input.close();
				output.close();

				return true;
			} else
				return false;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
		}

		return false;
	}

}
