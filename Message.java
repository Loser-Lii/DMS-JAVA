package filesManageSystem;
import java.io.Serializable;
import java.util.Vector;

/**
 * TODO 消息类，用于传递客户机和服务器之间的消息
 *
 * @author LiJinjie
 * &#064;date  2024/12/13
 */
public class Message implements Serializable{
	String type;
	Vector content;
	
	public Message(String type,Vector content) {
		super();
		this.type=type;
		this.content=content;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type=type;
	}
	
	public Vector getContent() {
		return content;
	}
	
	public void setContent(Vector content) {
		this.content=content;
	}
}
