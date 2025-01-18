package filesManageSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.io.Serializable;
import java.sql.*;

/**
 * TODO 数据处理类
 *
 * @author LiJinjie &#064;date 2024/11/15
 */
public class DataProcessing implements Serializable{
	/*
	 * private static boolean connectToDB = false;
	 * 
	 * static Hashtable<String, AbstractUser > users; static Hashtable<String, Doc>
	 * docs;
	 * 
	 * static enum ROLE_ENUM {
	 *//**
		 * administrator
		 */
	/*
	 * administrator("administrator"),
	 *//**
		 * operator
		 */
	/*
	 * operator("operator"),
	 *//**
		 * browser
		 */
	/*
	 * browser("browser");
	 * 
	 * private final String role;
	 * 
	 * ROLE_ENUM(String role) { this.role = role; }
	 * 
	 * public String getRole() { return role; } }
	 * 
	 * static { users = new Hashtable<String, AbstractUser>(); users.put("jack", new
	 * Operator("jack", "123", "operator")); users.put("rose", new Browser("rose",
	 * "123", "browser")); users.put("kate", new Administrator("kate", "123",
	 * "administrator")); init();
	 * 
	 * Timestamp timestamp = new Timestamp(System.currentTimeMillis()); docs = new
	 * Hashtable<String,Doc>(); docs.put("0001",new
	 * Doc("0001","jack",timestamp,"Doc Source Java","Doc.java")); }
	 * 
	 *//**
		 * TODO 初始化，连接数据库
		 *
		 * @param
		 * @return void
		 * @throws
		 */
	/*
	 * public static void init() { // update database connection status connectToDB
	 * = true ; }
	 * 
	 *//**
		 * TODO 按档案编号搜索档案信息，返回null时表明未找到
		 *
		 * @param id 用户名
		 * @return Doc
		 * @throws SQLException 如果数据库操作失败时抛出
		 */
	/*
	 * public static Doc searchDoc(String id) throws SQLException { if
	 * (!connectToDB) { throw new SQLException("Not Connected to Database"); } if
	 * (docs.containsKey(id)) { Doc temp =docs.get(id); return temp; } return null;
	 * }
	 * 
	 *//**
		 * TODO 列出所有档案信息
		 *
		 * @param
		 * @return Enumeration<Doc>
		 * @throws SQLException 如果数据库操作失败时抛出
		 */
	/*
	 * public static Enumeration<Doc> listDoc() throws SQLException{ if
	 * (!connectToDB) { throw new SQLException("Not Connected to Database"); }
	 * 
	 * Enumeration<Doc> e = docs.elements(); return e; }
	 * 
	 *//**
		 * TODO 插入新的档案
		 *
		 * @param id          档案编码
		 * @param creator     创建者
		 * @param timestamp   创建时间
		 * @param description 档案描述
		 * @param filename    档案名
		 * @return boolean
		 * @throws SQLException 如果数据库操作失败时抛出
		 */
	/*
	 * public static boolean insertDoc(String id, String creator, Timestamp
	 * timestamp, String description, String filename) throws SQLException{ Doc doc;
	 * 
	 * if (!connectToDB) { throw new SQLException("Not Connected to Database"); }
	 * 
	 * if (docs.containsKey(id)) return false ; else { doc = new
	 * Doc(id,creator,timestamp,description,filename); docs.put(id, doc); return
	 * true ; } }
	 * 
	 *//**
		 * TODO 按用户名搜索用户，返回null时表明未找到符合条件的用户
		 *
		 * @param name 用户名
		 * @return AbstractUser
		 * @throws SQLException 如果数据库操作失败时抛出
		 */
	/*
	 * public static AbstractUser searchUser(String name) throws SQLException{ if
	 * (!connectToDB) { throw new SQLException("Not Connected to Database"); }
	 * 
	 * if (users.containsKey(name)) { return users.get(name); } return null; }
	 * 
	 *//**
		 * TODO 按用户名、密码搜索用户，返回null时表明未找到符合条件的用户
		 *
		 * @param name     用户名
		 * @param password 密码
		 * @return AbstractUser
		 * @throws SQLException 如果数据库操作失败时抛出
		 */
	/*
	 * public static AbstractUser searchUser(String name, String password) throws
	 * SQLException { if (!connectToDB) { throw new
	 * SQLException("Not Connected to Database"); }
	 * 
	 * if (users.containsKey(name)) { AbstractUser temp =users.get(name); if
	 * ((temp.getPassword()).equals(password)) { return temp; } } return null; }
	 * 
	 *//**
		 * TODO 取出所有的用户
		 *
		 * @param
		 * @return Enumeration<AbstractUser>
		 * @throws SQLException 如果数据库操作失败时抛出
		 */
	/*
	 * public static Enumeration<AbstractUser> listUser() throws SQLException{ if
	 * (!connectToDB) { throw new SQLException("Not Connected to Database"); }
	 * 
	 * Enumeration<AbstractUser> e = users.elements(); return e; }
	 * 
	 *//**
		 * TODO 修改用户信息
		 *
		 * @param name     用户名
		 * @param password 密码
		 * @param role     角色
		 * @return boolean
		 * @throws SQLException 如果数据库操作失败时抛出
		 */
	/*
	 * public static boolean updateUser(String name, String password, String role)
	 * throws SQLException{ AbstractUser user; if (users.containsKey(name)) { switch
	 * (ROLE_ENUM.valueOf(role.toLowerCase())) { case administrator: user = new
	 * Administrator(name,password, role); break ; case operator: user = new
	 * Operator(name,password, role); break ; default : user = new
	 * Browser(name,password, role); } users.put(name, user); return true ; }else {
	 * return false ; } }
	 *//**
		 * TODO 插入新用户
		 *
		 * @param name     用户名
		 * @param password 密码
		 * @param role     角色
		 * @return boolean
		 * @throws SQLException 如果数据库操作失败时抛出
		 */
	/*
	 * public static boolean insertUser(String name, String password, String role)
	 * throws SQLException { AbstractUser user; if (users.containsKey(name)) {
	 * return false; } else { switch (ROLE_ENUM.valueOf(role.toLowerCase())) { case
	 * administrator: user = new Administrator(name, password, role); break; case
	 * operator: user = new Operator(name, password, role); break; default: user =
	 * new Browser(name, password, role); } users.put(name, user); return true; } }
	 * 
	 *//**
		 * TODO 删除指定用户
		 *
		 * @param name 用户名
		 * @return boolean
		 * @throws SQLException 如果数据库操作失败时抛出
		 */
	/*
	 * public static boolean deleteUser(String name) throws SQLException{ if
	 * (users.containsKey(name)){ users.remove(name); return true ; }else { return
	 * false ; } }
	 * 
	 *//**
		 * TODO 关闭数据库连接
		 *
		 * @param
		 * @return void
		 * @throws
		 *//*
			 * public static void disconnectFromDataBase() { if (connectToDB){ // close
			 * Statement and Connection try { }finally { connectToDB = false ; } } }
			 */

	private static Connection connection;
	private static Statement statement;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;
	private static boolean connectedToDatabase = false;
	
	private static String url = "jdbc:mysql://localhost:3306/document?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT"; // 声明数据库的URL
	
	public static void connectToDatabase(String user, String password) throws SQLException {
		// Class.forName(driveName); //加载数据库驱动类
		connection = DriverManager.getConnection(url, user, password);
		connectedToDatabase = true;
	}

	public static void disconnectFromDatabase() {
		if (connectedToDatabase) {
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			} finally {
				connectedToDatabase = false;
			}
		}
	}

	public static Doc searchDoc(String DocID) throws SQLException {
		Doc temp = null;
		if (!connectedToDatabase)
			throw new SQLException("Not Conneted to Database");

		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		String query = "SELECT * FROM doc_info WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setString(1, DocID);
	        
	        // Execute the query and fetch the result
	        resultSet = statement.executeQuery();
	        
	        // Check if a matching document was found
	        if (resultSet.next()) {
	            String id = resultSet.getString("id");
	            String creator = resultSet.getString("creator");
	            Timestamp timestamp = resultSet.getTimestamp("timestamp");
	            String description = resultSet.getString("description");
	            String filename = resultSet.getString("filename");

	            // Create and return the Doc object
	            temp = new Doc(id, creator, timestamp, description, filename);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new SQLException("Error while searching document with ID: " + DocID, e);
	    } finally {
	        // Ensure that resultSet is properly closed
	        if (resultSet != null) {
	            try {
	                resultSet.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return temp;
	}
	
	public static boolean insertDoc(String id, String creator, Timestamp timestamp, String description, String filename) throws SQLException {
        if (!connectedToDatabase) {
            throw new SQLException("Not Connected to Database");
        }
        String query = "INSERT INTO doc_info (id, creator, timestamp, description, filename) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setString(1, id);
        	statement.setString(2, creator);
        	statement.setTimestamp(3, timestamp);
        	statement.setString(4, description);
        	statement.setString(5, filename);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
	
	public static Enumeration<Doc> listDoc() throws SQLException {
        if (!connectedToDatabase) {
            throw new SQLException("Not Connected to Database");
        }

        List<Doc> docs = new ArrayList<>();
        String query = "SELECT * FROM doc_info";

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String creator = resultSet.getString("creator");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");
                String description = resultSet.getString("description");
                String filename = resultSet.getString("filename");

                docs.add(new Doc(id, creator, timestamp, description, filename));
            }
        }
        return Collections.enumeration(docs);
    }
	
	public static AbstractUser searchUser(String name) throws SQLException {
        if (!connectedToDatabase) {
            throw new SQLException("Not Connected to Database");
        }

        String query = "SELECT * FROM user_info WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                /*AbstractUser user=null;
                user.setName(name);
                user.setPassword(password);
                user.setRole(role);*/
                return createUser(name,password,role);          
            }
        }
        return null;
    }
	
	public static AbstractUser searchUser(String name, String password) throws SQLException {
        if (!connectedToDatabase) {
            throw new SQLException("Not Connected to Database");
        }

        String query = "SELECT * FROM user_info WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                /*AbstractUser user=null;
                user.setName(name);
                user.setPassword(password);
                user.setRole(role);*/
                return createUser(name,password,role);
            }
        }
        return null;
    }
	
	public static Enumeration<AbstractUser> listUser() throws SQLException {
	    if (!connectedToDatabase) {
	        throw new SQLException("Not Connected to Database");
	    }

	    String query = "SELECT username, password, role FROM user_info";  // 假设 `users` 表包含 `username`, `password`, 和 `role` 列
	    preparedStatement = connection.prepareStatement(query);

	    // 执行查询
	    resultSet = preparedStatement.executeQuery();

	    // 使用 Hashtable 来存储查询结果
	    Hashtable<String, AbstractUser> userTable = new Hashtable<>();

	    while (resultSet.next()) {
	        String username = resultSet.getString("username");
	        String password = resultSet.getString("password");
	        String role = resultSet.getString("role");

	        // 根据角色创建相应的用户对象
	        AbstractUser user;
	        switch (role.toLowerCase()) {
	            case "administrator":
	                user = new Administrator(username, password, role);
	                break;
	            case "operator":
	                user = new Operator(username, password, role);
	                break;
	            default:
	                user = new Browser(username, password, role);
	        }

	        // 将用户添加到 Hashtable
	        userTable.put(username, user);
	    }

	    // 将 Hashtable 转换为 Enumeration 并返回
	    return userTable.elements();
	}

	
	public static boolean insertUser(String name, String password, String role) throws SQLException {
        if (!connectedToDatabase) {
            throw new SQLException("Not Connected to Database");
        }

        String query = "INSERT INTO user_info (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setString(1, name);
        	statement.setString(2, password);
        	statement.setString(3, role);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }

	public static boolean updateUser(String name, String password, String role) throws SQLException {
        if (!connectedToDatabase) {
            throw new SQLException("Not Connected to Database");
        }

        String query = "UPDATE user_info SET password = ?, role = ? WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setString(1, password);
        	statement.setString(2, role);
        	statement.setString(3, name);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
	
	public static boolean deleteUser(String name) throws SQLException {
        if (!connectedToDatabase) {
            throw new SQLException("Not Connected to Database");
        }

        String query = "DELETE FROM user_info WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setString(1, name);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
	
    private static AbstractUser createUser(String name, String password, String role) {
        switch (role.toLowerCase()) {
            case "administrator":
                return new Administrator(name, password, role);
            case "operator":
                return new Operator(name, password, role);
            default:
                return new Browser(name, password, role);
        }
    }
            
          
	
	public static void main(String[] args) {
	}

}