package filesManageSystem;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Vector;
import java.util.concurrent.*;

public class ServerThread implements Runnable{
	private static ObjectOutputStream output;
	private static ObjectInputStream input;
	private static FileInputStream fis;
	private static FileOutputStream fos;
	private static Socket connection;
	private static int counter=1;
	private static Message client_message,server_message;
	
	private static String uploadpath = "D:\\OOP\\uploadFile\\";
	
    public ServerThread(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
        	
        	getStreams();
            // 处理客户端请求
            processConnection();

        } catch (EOFException eofException) {
			displayMessage("\nTerminating connection\n");
		} catch(IOException ioException) {
			ioException.printStackTrace();
		}finally {
			closeConnection();
			counter++;
		}
    }

	
	private static  void getStreams() throws IOException {
		// set up output stream for objects 对象流来包装输入输出流
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush(); // flush output buffer to send header information

		// set up input stream for objects
		input = new ObjectInputStream(connection.getInputStream());

		displayMessage("\nGot I/O streams\n");
	}
	
	private static void processConnection() throws IOException {
		Vector server_content;
		Vector client_content;
		
		//enable enterField so server user can send message
		do {
			try // read message and display it
			{
				client_message = (Message) input.readObject(); // read new message
				displayMessage("\n" + client_message.getType()); // display message
				client_content = client_message.getContent();
				// 用户登录
				if (client_message.getType().equals("CLIENT_LOGIN")) {
					String name=(String)client_content.elementAt(0);
					String password=(String)client_content.elementAt(1);
					AbstractUser user =DataProcessing.searchUser(name,password);
					server_content = new Vector();
					server_content.addElement(user);
					if (user == null)
						displayMessage("\n用户名口令错误");//UserName Password is not correct
					else
						displayMessage("\n"+user.getName());
					server_message=new Message("SERVER_LOGIN",server_content);
					sendData(server_message);
				}
				
				//用户查询
				if(client_message.getType().equals("CLIENT_USER_SEARCH")) {
					String name=(String)client_content.elementAt(0);
					AbstractUser user = DataProcessing.searchUser(name);
					server_content= new Vector();
					server_content.addElement(user);
					displayMessage("\n"+user.getName());
					server_message = new Message("SERVER_USER_SEARCH",server_content);
					sendData(server_message);
				}
				// 用户修改
				if (client_message.getType().equals("CLIENT_USER_MOD")) {
					String name=(String)client_content.elementAt(0);
					String password= (String) client_content.elementAt(1);
					String role=(String)client_content.elementAt(2);
					server_content= new Vector();
					if(DataProcessing.updateUser(name, password, role))
						server_content.addElement(true);
					else
						server_content.addElement(false);
					server_message = new Message("SERVER_USER_MOD",server_content);
					sendData(server_message);
				}
				// 用户增加
				if (client_message.getType().equals("CLIENT_USER_ADD")) {
					String name=(String)client_content.elementAt(0);
					String password= (String) client_content.elementAt(1);
					String role=(String)client_content.elementAt(2);
					server_content= new Vector();
					if(DataProcessing.insertUser(name, password, role))
						server_content.addElement(true);
					else
						server_content.addElement(false);
					server_message = new Message("SERVER_USER_ADD",server_content);
					sendData(server_message);
				}
				// 用户删除
				if (client_message.getType().equals("CLIENT_USER_DEL")) {
					String name=(String)client_content.elementAt(0);
					server_content= new Vector();
					if(DataProcessing.deleteUser(name))
						server_content.addElement(true);
					else
						server_content.addElement(false);
					server_message = new Message("SERVER_USER_DEL",server_content);
					sendData(server_message);
				}
				// 用户列表
				if (client_message.getType().equals("CLIENT_USER_LIST")) {
					Enumeration<AbstractUser> e=DataProcessing.listUser();
					server_content= new Vector();
					while(e.hasMoreElements()) {
						server_content.addElement(e.nextElement());
					}
					server_message = new Message("SERVER_USER_LIST",server_content);
					sendData(server_message);
				}
				// 个人管理
				if (client_message.getType().equals("CLIENT_SELF_MOD")) {
					String name=(String)client_content.elementAt(0);
					String password= (String) client_content.elementAt(1);
					String role=(String)client_content.elementAt(2);
					server_content= new Vector();
					if(DataProcessing.updateUser(name, password, role))
						server_content.addElement(true);
					else
						server_content.addElement(false);
					server_message = new Message("SERVER_SELF_MOD",server_content);
					sendData(server_message);
				}

				// 文件列表
				if (client_message.getType().equals("CLIENT_FILE_LIST")) {
					Enumeration<Doc> e=DataProcessing.listDoc();
					server_content= new Vector();
					while(e.hasMoreElements()) {
						server_content.addElement(e.nextElement());
					}
					server_message = new Message("SERVER_FILE_LIST",server_content);
					sendData(server_message);
				}
				// 文件上传
				if (client_message.getType().equals("CLIENT_FILE_UP")) {
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					String ID=(String)client_content.elementAt(0);
					String creator=(String)client_content.elementAt(1);
					String description= (String) client_content.elementAt(2);
					String filename=(String)client_content.elementAt(3);
					
					server_content = new Vector();
					if(DataProcessing.searchDoc(ID)!=null) {
						server_content.addElement(false);
						server_message = new Message("SERVER_ID_ALREADY_EXISTS",server_content);
					}else {
						if(DataProcessing.insertDoc(ID, creator, timestamp, description, filename)) {
							receiveFile(filename);	
							server_content.addElement(true);
						}
						else server_content.addElement(false);
						server_message = new Message("SERVER_FILE_UP",server_content);
					}
					sendData(server_message);
				}
				// 文件下载
				if (client_message.getType().equals("CLIENT_FILE_DOWN")) {
					String ID=(String)client_content.elementAt(0);

					server_content = new Vector();
					
					Doc doc=DataProcessing.searchDoc(ID);
					if(doc==null) {
						server_content.addElement("未找到相应档案文件");
						server_message = new Message("SERVER_ERROR",server_content);
					}else {
						server_content.addElement(doc);
						server_message = new Message("SERVER_FILE_DOWN",server_content);
						sendData(server_message);
						System.out.println(uploadpath+doc.getFilename());
						File file = new File(uploadpath+doc.getFilename());
						sendFile(file);
					}
				}

			} catch (ClassNotFoundException classNotFoundException) {
				displayMessage("\nUnknown object type received");
				server_content = new Vector();
				server_content.addElement(classNotFoundException);
				server_message= new Message("SERVER_ERROR",server_content);
			}catch(SQLException exception) {
				displayMessage("数据库错误"+exception.getMessage());
				server_content = new Vector();
				server_content.addElement(exception);
				server_message= new Message("SERVER_ERROR",server_content);
			}
		} while (!client_message.getType().equals("CLIENT_LOGOUT"));
		
	} 
	
	public static void closeConnection() {


		try {
			if (fis != null)
				fis.close();
			output.close(); // close output stream
			input.close(); // close input stream
			connection.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	private static void sendData(Message message) {
		try {
			output.writeObject(message);
			output.flush();
			displayMessage("\nSERVER>>> " + message.getType());
		} catch (IOException ioException) {
			displayMessage("\nError writing object");
		}
	}

	private static void displayMessage(final String messageToDisplay) {
		System.out.println(messageToDisplay);
	}
	
	private static void sendFile(File file) throws IOException{
		fis=new FileInputStream(file);
		
		//文件长度
		output.writeLong(file.length());
		output.flush();
		
		//传输文件
		byte[] buffer=new byte[1024];
		while(true) {
			int read=0;
			if(fis!=null) {
				read=fis.read(buffer);
			}
			if(read==-1)
				break;
			output.write(buffer,0,read);
			output.flush();
		}
		fis.close();
	}
	
	private static void receiveFile(String filename)throws IOException{
		 // 创建接收文件的目录
        String filePath = uploadpath + "\\" + filename;
        File file = new File(filePath);
		long fileLength=input.readLong();
		fos=new FileOutputStream(file);
		
		byte[] buffer = new byte[1024];
		int transLen=0;
		System.out.println("----开始接收文件<"+filename+">,文件大小为<"+fileLength+">----");
		while(transLen<fileLength) {
			int read=0;
			read=input.read(buffer);
			System.out.println(read);
			if(read==-1)
				break;
			transLen+=read;
			System.out.println("接收文件进度"+100*transLen/fileLength+"%...");
			fos.write(buffer,0,read);
			fos.flush();
		}
		fos.close();
		System.out.println("----接收文件<"+filename+">成功----");
	}
}
