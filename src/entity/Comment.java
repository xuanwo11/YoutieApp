package entity;

import com.bmob.lostfound.bean.Found;

import cn.bmob.v3.BmobObject;


/**
 * @author kingofglory
 *         email: kingofglory@yeah.net
 *         blog:  http:www.google.com
 * @date 2014-4-2
 * TODO
 */

public class Comment extends BmobObject{
	
	public static final String TAG = "Comment";

	private String title;//user//cardNumber
	private String describe; //commentContent;//bankName
	private Person user;
	private Found user1;
	private Shipin user3;
	private Dongtu user4;
	private Ciyuanjie user5;
	private Ciyuanjie1 user6;
	private com.hfp.youtie.entity.User user2;
	private String usernames;/////
	private String avatars;///////////////
	  public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescribe() {
			return describe;
		}
		public void setDescribe(String describe) {
			this.describe = describe;
		}
		public Person getUser() {
			return user;
		}
		public void setUser(Person user) {
			this.user = user;
		}
		public Found getUser1() {
			return user1;
		}
		public void setUser1(Found user1) {
			this.user1 = user1;
		}
		public Shipin getUser3() {
			return user3;
		}
		public void setUser3(Shipin user3) {
			this.user3 = user3;
		}
		public Dongtu getUser4() {
			return user4;
		}
		public void setUser4(Dongtu user4) {
			this.user4 = user4;
		}
		public Ciyuanjie getUser5() {
			return user5;
		}
		public void setUser5(Ciyuanjie user5) {
			this.user5 = user5;
		}
		public Ciyuanjie1 getUser6() {
			return user6;
		}
		public void setUser6(Ciyuanjie1 user6) {
			this.user6 = user6;
		}
		public com.hfp.youtie.entity.User getUser2() {
			return user2;
		}
		public void setUser2(com.hfp.youtie.entity.User user2) {
			this.user2 = user2;
		}
		
		 public String getUsernames() {///////////////////////////////
				return usernames;
			}
			public void setUsernames(String usernames) {
				this.usernames = usernames;
			}
			 public String getAtavars() {///////////////////////////////
					return avatars;
				}
				public void setAtavars(String avatars) {
					this.avatars = avatars;
				}
}

