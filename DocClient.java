package filesManageSystem;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Vector;

import filesManageSystem.Message;

public class DocClient {
	private static ObjectOutputStream output;
	private static ObjectInputStream input;
	private static FileInputStream fis;
	private static FileOutputStream fos;
	private static Message client_message, server_message;
	private static String serveraddress = "localhost";
	private static int serverport = 12345;
	private static Socket client;
	private static String downloadpath = "D:\\OOP\\downloadFile\\";
	public static String selfdownloadPath=null;

	public DocClient(String host, int port) {
		serveraddress = host;
		serverport = port;
	}

	public static void initClient(String host, int port) {
		serveraddress = host;
		serverport = port;
	}

	public static void runClient() throws IOException {
		connectToServer();
		getStreams();
	}

	public static void connectToServer() throws IOException {
		displayMessage("Attempting connection\n");

		client = new Socket(InetAddress.getByName(serveraddress), serverport);

		displayMessage("Connected to:" + client.getInetAddress().getHostAddress());
	}

	public static void getStreams() throws IOException {
		// set up output stream for objects 对象流来包装输入输出流
		output = new ObjectOutputStream(client.getOutputStream());
		output.flush(); // flush output buffer to send header information

		// set up input stream for objects
		input = new ObjectInputStream(client.getInputStream());

		displayMessage("\nGot I/O streams\n");
	}

	// process connection with server
	/**
	 * @throws IOException
	 */
	public static void processConnection() throws IOException {
		Vector server_content;

		try // read message and display it
		{
			server_message = (Message) input.readObject(); // read new message
			displayMessage("\n" + server_message.getType()); // display message
			server_content = server_message.getContent();
			// 用户登录
			if (server_message.getType().equals("SERVER_LOGIN")) {
				AbstractUser user = (AbstractUser) server_content.elementAt(0);
				if (user == null)
					displayMessage("\n用户名口令错误");
				else
					displayMessage("\n登陆成功");

			}
			// 用户修改
			if (server_message.getType().equals("SERVER_USER_MOD")) {
				Boolean result = (Boolean) server_content.elementAt(0);
				if (result.booleanValue())
					displayMessage("\n用户修改成功");
				else
					displayMessage("\n用户修改失败");
			}
			// 用户增加
			if (server_message.getType().equals("SERVER_USER_ADD")) {
				Boolean result = (Boolean) server_content.elementAt(0);
				if (result.booleanValue())
					displayMessage("\n用户增加成功");
				else
					displayMessage("\n用户增加失败");
			}
			// 用户删除
			if (server_message.getType().equals("SERVER_USER_DEL")) {
				Boolean result = (Boolean) server_content.elementAt(0);
				if (result.booleanValue())
					displayMessage("\n用户删除成功");
				else
					displayMessage("\n用户删除失败");
			}
			// 用户列表
			if (server_message.getType().equals("SERVER_USER_LIST")) {
				Enumeration<AbstractUser> e = (Enumeration<AbstractUser>) server_content.elementAt(0);
				AbstractUser user;
				while (e.hasMoreElements()) {
					user = e.nextElement();
					displayMessage("Name:" + user.getName() + "\t Password:" + user.getPassword() + "\t Role:"
							+ user.getRole());
				}
			}
			// 个人管理
			if (server_message.getType().equals("SERVER_SELF_MOD")) {
				Boolean result = (Boolean) server_content.elementAt(0);
				if (result.booleanValue())
					displayMessage("\n个人信息修改成功");
				else
					displayMessage("\n个人信息修改失败");
			}

			// 文件列表
			if (server_message.getType().equals("SERVER_FILE_LIST")) {
				Enumeration<Doc> e = (Enumeration<Doc>) server_content.elementAt(0);
				Doc doc;
				while (e.hasMoreElements()) {
					doc = e.nextElement();
					displayMessage("ID:" + doc.getId() + "\t Creator:" + doc.getCreator() + "\t Filename:"
							+ doc.getFilename());
					displayMessage("Description:" + doc.getDescription());
				}
			}
			// 文件上传
			if (server_message.getType().equals("SERVER_FILE_UP")) {
				Boolean result = (Boolean) server_content.elementAt(0);
				if (result.booleanValue())
					displayMessage("\n文件上传成功");
				else
					displayMessage("\n文件上传失败");
			}
			// 文件下载
			if (server_message.getType().equals("SERVER_FILE_DOWN")) {
				Doc doc = (Doc) server_content.elementAt(0);
				if (doc == null)
					displayMessage("\n文件下载失败");
				else
					displayMessage("\\n文件下载成功");
			}

		} catch (ClassNotFoundException classNotFoundException) {
			displayMessage("\nUnknown object type received");
		}
	} // end catch
		// end method processConnection

	public static void closeConnection() {
		displayMessage("\nClosing connection");

		Message message = new Message("CLIENT_LOGOUT", null);
		sendData(message);

		try {
			if (fis != null)
				fis.close();
			output.close(); // close output stream
			input.close(); // close input stream
			client.close(); // close socket
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public static void sendData(Message message) {
		try {
			output.writeObject(message);
			output.flush();
			displayMessage("\nCLIENT>>> " + message.getType());
		} catch (IOException ioException) {
			displayMessage("\nError writing object");
		}
	}

	private static void displayMessage(final String messageToDisplay) {
		System.out.println(messageToDisplay);
	}

	private static void sendFile(File file) throws IOException {
		fis = new FileInputStream(file);

		// 文件长度
		output.writeLong(file.length());
		output.flush();

		// 传输文件
		byte[] buffer = new byte[1024];
		while (true) {
			int read = 0;
			if (fis != null) {
				read = fis.read(buffer);
			}
			if (read == -1)
				break;
			output.write(buffer, 0, read);
			output.flush();
		}
		fis.close();
	}

	private static void receiveFile(File file) throws IOException {
		long fileLength = input.readLong();
		fos = new FileOutputStream(selfdownloadPath+file);
		String filename = file.getName();

		byte[] buffer = new byte[1024];
		int transLen = 0;
		System.out.println("----开始接收文件<" + filename+">,文件大小为<"+fileLength+">----");
		while (transLen < fileLength) {
			int read = 0;
			read = input.read(buffer);
			System.out.println(read);
			if (read == -1)
				break;
			transLen += read;
			System.out.println("接收文件进度" + 100 * transLen / fileLength + "%...");
			fos.write(buffer, 0, read);
			fos.flush();
		}
		fos.close();
		System.out.println("----接收文件<" + filename + ">成功----");
	}

	public static boolean file_up(String ID, String creator, String description, File file)
			throws ClassNotFoundException, IOException {
		Vector content = new Vector();
		content.addElement(ID);
		content.addElement(creator);
		content.addElement(description);
		content.addElement(file.getName());
		Message message = new Message("CLIENT_FILE_UP", content);
		sendData(message);
		sendFile(file);
		// processConnection();
		server_message = (Message) input.readObject();
		Vector server_content = server_message.getContent();
		// 文件上传
		if (server_message.getType().equals("SERVER_FILE_UP")) {
			Boolean result = (Boolean) server_content.elementAt(0);
			return result.booleanValue();
		} else if (server_message.getType().equals("SERVER_ID_ALREADY_EXISTS")){
			return false;
		}else return false;
	}

	public static boolean file_down(String ID, File file) throws ClassNotFoundException, IOException {
		Vector content = new Vector();
		content.addElement(ID);
		Message message = new Message("CLIENT_FILE_DOWN", content);
		sendData(message);
		// processConnection();
		server_message = (Message) input.readObject();
		Vector server_content = server_message.getContent();
		if (server_message.getType().equals("SERVER_FILE_DOWN")) {
			Doc doc = (Doc) server_content.elementAt(0);
			if (doc == null)
				return false;
			else {
				receiveFile(file);
				return true;
			}
		} else {
			System.out.println((String)server_content.elementAt(0));
			return false;
		}
	}

	public static Enumeration<Doc> file_list() throws IOException, ClassNotFoundException {
		Vector content = new Vector();
		Message message = new Message("CLIENT_FILE_LIST", content);
		sendData(message);
		server_message = (Message) input.readObject();
		Vector server_content = server_message.getContent();
		if (server_message.getType().equals("SERVER_FILE_LIST")) {
			return (Enumeration<Doc>) server_content.elements();
		} else {
			return null;
		}

	}

	public static boolean self_mod(String username, String password, String role)
			throws ClassNotFoundException, IOException {
		Vector content = new Vector();
		content.addElement(username);
		content.addElement(password);
		content.addElement(role);
		Message message = new Message("CLIENT_SELF_MOD", content);
		sendData(message);
		server_message = (Message) input.readObject();
		Vector server_content = server_message.getContent();
		if (server_message.getType().equals("SERVER_SELF_MOD")) {
			Boolean result = (Boolean) server_content.elementAt(0);
			return result.booleanValue();
		} else {
			return false;
		}
	}

	public static boolean user_add(String username, String password, String role)
			throws ClassNotFoundException, IOException {
		Vector content = new Vector();
		content.addElement(username);
		content.addElement(password);
		content.addElement(role);
		Message message = new Message("CLIENT_USER_ADD", content);
		sendData(message);

		server_message = (Message) input.readObject();
		Vector server_content = server_message.getContent();
		if (server_message.getType().equals("SERVER_USER_ADD")) {
			Boolean result = (Boolean) server_content.elementAt(0);
			return result.booleanValue();
		} else {
			return false;
		}
	}

	public static boolean user_del(String username) throws ClassNotFoundException, IOException {
		Vector content = new Vector();
		content.addElement(username);
		Message message = new Message("CLIENT_USER_DEL", content);
		sendData(message);

		server_message = (Message) input.readObject();
		Vector server_content = server_message.getContent();
		if (server_message.getType().equals("SERVER_USER_DEL")) {
			Boolean result = (Boolean) server_content.elementAt(0);
			return result.booleanValue();
		} else {
			return false;
		}
	}

	public static boolean user_mod(String username, String password, String role)
			throws ClassNotFoundException, IOException {
		Vector content = new Vector();
		content.addElement(username);
		content.addElement(password);
		content.addElement(role);
		Message message = new Message("CLIENT_USER_MOD", content);
		sendData(message);
		server_message = (Message) input.readObject();
		Vector server_content = server_message.getContent();
		if (server_message.getType().equals("SERVER_USER_MOD")) {
			Boolean result = (Boolean) server_content.elementAt(0);
			return result.booleanValue();
		} else {
			return false;
		}
	}

	public static Enumeration<AbstractUser> user_list() throws ClassNotFoundException, IOException {
		Vector content = new Vector();
		Message message = new Message("CLIENT_USER_LIST", content);
		sendData(message);
		server_message = (Message) input.readObject();
		Vector server_content = server_message.getContent();
		if (server_message.getType().equals("SERVER_USER_LIST")) {
			return (Enumeration<AbstractUser>) server_content.elementAt(0);
		} else {
			return null;
		}
	}

	public static AbstractUser user_login(String username, String password) throws ClassNotFoundException, IOException {
		Vector content = new Vector();
		content.addElement(username);
		content.addElement(password);
		Message message = new Message("CLIENT_LOGIN", content);
		sendData(message);
		// processConnection();
		server_message = (Message) input.readObject();
		Vector server_content = server_message.getContent();

		if (server_message.getType().equals("SERVER_LOGIN")) {
			return (AbstractUser) server_content.elementAt(0);
		} else {
			return null;
		}
	}

	public static AbstractUser user_search(String username) throws ClassNotFoundException, IOException {
		Vector content = new Vector();
		content.addElement(username);
		Message message = new Message("CLIENT_USER_SEARCH", content);
		sendData(message);
		server_message = (Message) input.readObject();
		Vector server_content = server_message.getContent();
		if (server_message.getType().equals("SERVER_USER_SEARCH")) {
			return (AbstractUser) server_content.elementAt(0);
		} else {
			return null;
		}
	}

	/*public static void main(String[] args) {
		try {
			client=new Socket(serveraddress,serverport);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		try {
			client.setSoTimeout(60000);
		} catch (SocketException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		while(true) {
			try {
				runClient();
				processConnection();
			}catch (IOException eofException) {
				displayMessage("\nTerminating connection\n");
			}finally {
				closeConnection();
			}
		}
		
	}*/
	

}