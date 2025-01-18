package filesManageSystem;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Vector;
import java.util.concurrent.*;

import javax.swing.JOptionPane;

import filesManageSystem.Message;

public class DocServer {
	// 网络字节输出流(输出至不同用户)
    private static ExecutorService pool;  // 线程池，用于管理客户端连接
	private static ServerSocket server;
	
	private static String user="root";
	private static String password="20041123a";

	private static String port="12345";   //端口号
	

	public static void runServer() {
        try {
            int portnum = Integer.parseInt(port);
            server = new ServerSocket(portnum, 8888); // 设置服务器监听端口

            // 连接数据库
            try {
                DataProcessing.connectToDatabase(user, password);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "数据库用户名或密码错误,请重新输入", "输入反馈", JOptionPane.YES_NO_OPTION);  // 显示对话框
                System.exit(0);
            }

            // 创建线程池，最大并发处理 5 个客户端连接
            pool = Executors.newFixedThreadPool(5);

        	System.out.println("Waiting for connection\n");
            while (true) {
                // 等待客户端连接
                Socket connection = server.accept();
                //接收到客户端连接
                System.out.println("Received connection from the client:" + connection.getInetAddress().getHostName());

                // 为每个客户端连接创建一个新的线程来处理请求
                pool.execute(new ServerThread(connection));  // 使用线程池来处理每个连接
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }	
    }

	public static void main(String[] args) {
		runServer();
	}
	
	/*private static String driverName="com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/document?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT"; // 声明数据库的URL*/
	
	
	/*public void init() {
		try {
			BufferedReader infile=new BufferedReader(new InputStreamReader(new FileInputStream("server.txt")));
			
			driverName=infile.readLine();
			url=infile.readLine();
			user=infile.readLine();
			password=infile.readLine();
			uploadpath=infile.readLine();
			port=infile.readLine();
			infile.close();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "配置文件错误!");
			System.exit(0);
		}
	}*/
	
	
	/*
	 * public static void waitForConnection() throws IOException{
	 * displayMessage("Waiting for connection\n"); connection=server.accept();
	 * displayMessage("Connection "+counter+" received from:"+connection.
	 * getInetAddress().getHostName()); ///还要补东西 }
	 * 
	 * private static void getStreams() throws IOException { // set up output stream
	 * for objects 对象流来包装输入输出流 output = new
	 * ObjectOutputStream(connection.getOutputStream()); output.flush(); // flush
	 * output buffer to send header information
	 * 
	 * // set up input stream for objects input = new
	 * ObjectInputStream(connection.getInputStream());
	 * 
	 * displayMessage("\nGot I/O streams\n"); }
	 * 
	 * private static void processConnection() throws IOException { Vector
	 * server_content; Vector client_content;
	 * 
	 * //enable enterField so server user can send message do { try // read message
	 * and display it { client_message = (Message) input.readObject(); // read new
	 * message displayMessage("\n" + client_message.getType()); // display message
	 * client_content = client_message.getContent(); // 用户登录 if
	 * (client_message.getType().equals("CLIENT_LOGIN")) { String
	 * name=(String)client_content.elementAt(0); String
	 * password=(String)client_content.elementAt(1); AbstractUser user
	 * =DataProcessing.searchUser(name,password); server_content = new Vector();
	 * server_content.addElement(user); if (user == null)
	 * displayMessage("\n用户名口令错误");//UserName Password is not correct else
	 * displayMessage("\n"+user.getName()); server_message=new
	 * Message("SERVER_LOGIN",server_content); sendData(server_message); }
	 * 
	 * //用户查询 if(client_message.getType().equals("CLIENT_USER_SEARCH")) { String
	 * name=(String)client_content.elementAt(0); AbstractUser user =
	 * DataProcessing.searchUser(name); server_content= new Vector();
	 * server_content.addElement(user); displayMessage("\n"+user.getName());
	 * server_message = new Message("SERVER_USER_SEARCH",server_content);
	 * sendData(server_message); } // 用户修改 if
	 * (client_message.getType().equals("CLIENT_USER_MOD")) { String
	 * name=(String)client_content.elementAt(0); String password= (String)
	 * client_content.elementAt(1); String role=(String)client_content.elementAt(2);
	 * server_content= new Vector(); if(DataProcessing.updateUser(name, password,
	 * role)) server_content.addElement(true); else
	 * server_content.addElement(false); server_message = new
	 * Message("SERVER_USER_MOD",server_content); sendData(server_message); } //
	 * 用户增加 if (client_message.getType().equals("CLIENT_USER_ADD")) { String
	 * name=(String)client_content.elementAt(0); String password= (String)
	 * client_content.elementAt(1); String role=(String)client_content.elementAt(2);
	 * server_content= new Vector(); if(DataProcessing.insertUser(name, password,
	 * role)) server_content.addElement(true); else
	 * server_content.addElement(false); server_message = new
	 * Message("SERVER_USER_ADD",server_content); sendData(server_message); } //
	 * 用户删除 if (client_message.getType().equals("CLIENT_USER_DEL")) { String
	 * name=(String)client_content.elementAt(0); server_content= new Vector();
	 * if(DataProcessing.deleteUser(name)) server_content.addElement(true); else
	 * server_content.addElement(false); server_message = new
	 * Message("SERVER_USER_DEL",server_content); sendData(server_message); } //
	 * 用户列表 if (client_message.getType().equals("CLIENT_USER_LIST")) {
	 * Enumeration<AbstractUser> e=DataProcessing.listUser(); server_content= new
	 * Vector(); while(e.hasMoreElements()) {
	 * server_content.addElement(e.nextElement()); } server_message = new
	 * Message("SERVER_USER_LIST",server_content); sendData(server_message); } //
	 * 个人管理 if (client_message.getType().equals("CLIENT_SELF_MOD")) { String
	 * name=(String)client_content.elementAt(0); String password= (String)
	 * client_content.elementAt(1); String role=(String)client_content.elementAt(2);
	 * server_content= new Vector(); if(DataProcessing.updateUser(name, password,
	 * role)) server_content.addElement(true); else
	 * server_content.addElement(false); server_message = new
	 * Message("SERVER_SELF_MOD",server_content); sendData(server_message); }
	 * 
	 * // 文件列表 if (client_message.getType().equals("CLIENT_FILE_LIST")) {
	 * Enumeration<Doc> e=DataProcessing.listDoc(); server_content= new Vector();
	 * while(e.hasMoreElements()) { server_content.addElement(e.nextElement()); }
	 * server_message = new Message("SERVER_FILE_LIST",server_content);
	 * sendData(server_message); } // 文件上传 if
	 * (client_message.getType().equals("CLIENT_FILE_UP")) { Timestamp timestamp =
	 * new Timestamp(System.currentTimeMillis()); String
	 * ID=(String)client_content.elementAt(0); String
	 * creator=(String)client_content.elementAt(1); String description= (String)
	 * client_content.elementAt(2); String
	 * filename=(String)client_content.elementAt(3);
	 * 
	 * server_content = new Vector();
	 * 
	 * if(DataProcessing.insertDoc(ID, creator, timestamp, description, filename)) {
	 * receiveFile(filename); server_content.addElement(true); } else
	 * server_content.addElement(false); server_message = new
	 * Message("SERVER_FILE_UP",server_content); sendData(server_message); } // 文件下载
	 * if (client_message.getType().equals("CLIENT_FILE_DOWN")) { String
	 * ID=(String)client_content.elementAt(0);
	 * 
	 * server_content = new Vector();
	 * 
	 * Doc doc=DataProcessing.searchDoc(ID); if(doc==null) {
	 * server_content.addElement("未找到相应档案文件"); server_message = new
	 * Message("SERVER_ERROR",server_content); }else {
	 * server_content.addElement(doc); server_message = new
	 * Message("SERVER_FILE_DOWN",server_content); sendData(server_message);
	 * System.out.println(uploadpath+doc.getFilename()); File file = new
	 * File(uploadpath+doc.getFilename()); sendFile(file); } }
	 * 
	 * } catch (ClassNotFoundException classNotFoundException) {
	 * displayMessage("\nUnknown object type received"); server_content = new
	 * Vector(); server_content.addElement(classNotFoundException); server_message=
	 * new Message("SERVER_ERROR",server_content); }catch(SQLException exception) {
	 * displayMessage("数据库错误"+exception.getMessage()); server_content = new
	 * Vector(); server_content.addElement(exception); server_message= new
	 * Message("SERVER_ERROR",server_content); } } while
	 * (!client_message.getType().equals("CLIENT_LOGOUT"));
	 * 
	 * }
	 * 
	 * public static void closeConnection() {
	 * 
	 * 
	 * try { if (fis != null) fis.close(); output.close(); // close output stream
	 * input.close(); // close input stream connection.close(); } catch (IOException
	 * ioException) { ioException.printStackTrace(); } }
	 * 
	 * private static void sendData(Message message) { try {
	 * output.writeObject(message); output.flush(); displayMessage("\nSERVER>>> " +
	 * message.getType()); } catch (IOException ioException) {
	 * displayMessage("\nError writing object"); } }
	 * 
	 * private static void displayMessage(final String messageToDisplay) {
	 * System.out.println(messageToDisplay); }
	 * 
	 * private static void sendFile(File file) throws IOException{ fis=new
	 * FileInputStream(file);
	 * 
	 * //文件长度 output.writeLong(file.length()); output.flush();
	 * 
	 * //传输文件 byte[] buffer=new byte[1024]; while(true) { int read=0; if(fis!=null)
	 * { read=fis.read(buffer); } if(read==-1) break; output.write(buffer,0,read);
	 * output.flush(); } fis.close(); }
	 * 
	 * private static void receiveFile(String filename)throws IOException{ //
	 * 创建接收文件的目录 String filePath = uploadpath + "\\" + filename; File file = new
	 * File(filePath); long fileLength=input.readLong(); fos=new
	 * FileOutputStream(file);
	 * 
	 * byte[] buffer = new byte[1024]; int transLen=0;
	 * System.out.println("----开始接收文件<"+filename+">,文件大小为<"+fileLength+">----");
	 * while(transLen<fileLength) { int read=0; read=input.read(buffer);
	 * System.out.println(read); if(read==-1) break; transLen+=read;
	 * System.out.println("接收文件进度"+100*transLen/fileLength+"%..."+fileLength);
	 * fos.write(buffer,0,read); fos.flush(); } fos.close();
	 * System.out.println("----接收文件<"+filename+">成功----"); }
	 */
	
	// 服务器主函数
	
}
