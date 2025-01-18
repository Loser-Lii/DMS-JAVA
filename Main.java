package filesManageSystem;

import java.util.Scanner;
/**
 * TODO 执行主函数
 *
 * @author LiJinjie
 * &#064;date  2024/11/15
 */
public class Main {
	public static void main(String[] args) {
        try {
            // 初始化数据库连接
           // DataProcessing.connect();
            
    		Scanner scanner = new Scanner(System.in);
    		System.out.println("""
                    ****欢迎进入档案系统****
                        \t1.登录
                        \t2.退出
                    ***********************""");
    		String tipMenu="请输入正确的数字!";

    		String input;

    		// 菜单选择验证
    		while (true) {
    			System.out.print("请选择菜单:");
    			input=scanner.next().trim();
    			if(!(input).matches("[12]")){
    				System.out.print(tipMenu);
    			}else {
    				int nextInt=Integer.parseInt(input);
    				switch(nextInt) {
						/*用户名和密码验证*/
    				case 1:
    					while (true) {
    						System.out.print("请输入用户名:");
    						String username;
    						username=scanner.next().trim();
    						System.out.print("请输入口令:");
    						String password;
    						password=scanner.next().trim();

    						AbstractUser user = DataProcessing.searchUser(username, password);

    						if (user == null) {
    							System.out.println("输入的用户名或口令有误，请重新输入!");	
    						} else {
    							user.showMenu();
    							DataProcessing.disconnectFromDatabase();
								return;
							}
    					}
						/*如果选择2，则退出系统*/
    				case 2:
    					System.out.println("**系统退出，谢谢使用!**");
    					DataProcessing.disconnectFromDatabase();
    					scanner.close();
    					return;
    				}
    			}
    		}

        } catch (Exception e) {
            System.out.println("数据库连接失败：" + e.getMessage());
        }
	}
}	
