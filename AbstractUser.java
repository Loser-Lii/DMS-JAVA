package filesManageSystem;

import java.sql.SQLException;
import  java.io.BufferedInputStream;
import  java.io.BufferedOutputStream;
import  java.io.File;
import  java.io.FileInputStream;
import  java.io.FileOutputStream;
import  java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;

/**
 * TODO 抽象用户类，为各用户子类提供模板
 *
 * @author LiJinjie
 * &#064;date  2024/11/15
 */
public abstract class AbstractUser implements Serializable{
	private String name;
	private String password;
	private String role;

	public String uploadPath="D:\\OOP\\uploadFile\\";
	public String downloadPath="D:\\OOP\\downloadFile\\";

	public AbstractUser(String name, String password, String role) {
		this.name = name;
		this.password = password;
		this.role = role;
	}

	/**
	 * TODO 展示菜单，需子类加以覆盖
	 *
	 * @throws SQLException 如果数据库操作失败时抛出
	 *
	 */
	public abstract void showMenu() throws SQLException;

	/**
	 * TODO 展示档案文件列表
	 *
	 * @param
	 * @return void
	 * @throws SQLException 如果数据库操作失败时抛出
	 */
	public  void  showFileList() throws  SQLException{
		Enumeration<Doc> e= DataProcessing.listDoc();
		Doc doc;
		while ( e.hasMoreElements() ){
			doc=e.nextElement();
			System.out.println("ID:"+doc.getId()+"\t Creator:" +doc.getCreator()+"\t Time:" +doc.getTimestamp()+"\t Filename:"+doc.getFilename());
			System.out.println("Description:"+doc.getDescription());
		}

	}

	/**
	 * TODO 下载档案文件
	 *
	 * @param id 档案编号
	 * @return boolean 下载是否成功
	 * @throws SQLException 如果数据库操作失败时抛出
	 * @throws IOException 如果文件操作失败时抛出
	 */
	public  boolean  downloadFile(String id) throws  SQLException,IOException{
		//boolean result=false;
		byte [] buffer = new  byte [1024];
		Doc doc=DataProcessing.searchDoc(id);

		if  (doc==null) {
			return  false ;
		}

		File tempFile =new  File(uploadPath+doc.getFilename());
		String filename = tempFile.getName();

		BufferedInputStream infile = new  BufferedInputStream(new  FileInputStream(tempFile));
		BufferedOutputStream targetFile = new  BufferedOutputStream(new  FileOutputStream(downloadPath+filename));

		while  (true ) {
			//从文件读数据给字节数组
			int  byteRead=infile.read(buffer);
			if  (byteRead==-1) {
				break ;
			}
			//将读到的数据写入目标文件
			targetFile.write(buffer,0,byteRead);
		}
		infile.close();
		targetFile.close();

		return  true ;
	}

	/**
	 * TODO 修改用户自身信息
	 *
	 * @param password 口令
	 * @return boolean 修改是否成功
	 * @throws SQLException 如果数据库操作失败时抛出
	 */
	public boolean changeSelfInfo(String password) throws SQLException {
		if (DataProcessing.updateUser(name, password, role)) {
			this.password = password;
			System.out.println("修改成功");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * TODO 退出系统
	 *
	 * @param
	 * @return void
	 * @throws
	 */
	public void exitSystem() {
		System.exit(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
