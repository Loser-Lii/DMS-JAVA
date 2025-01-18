package filesManageSystem;

public class LoginUser {
	private static String username;
	private static String password;
	private static String role;
	AbstractUser user;
	
	public static void setName(String name) {
		username=name;
	}
	
	public static void setPassword(String p) {
		password = p;
	}
	
	public static void setRole(String r) {
		role = r;
	}
	
	public static String getName() {
		return username;
	}
	
	public static String getPassword() {
		return password;
	}
	
	public static String getRole() {
		return role;
	}
	
}
