package com.bmob.lostfound.bean;

import com.hfp.youtie.entity.User;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/** 招领
  * @ClassName: Found
  * @Description: TODO
  * @author smile
  * @date 2014-5-21 下午2:16:08
  */
public class Found extends BmobObject {

	 private BmobFile icon;  
	    private String title;//标题
		private String describe;//描述
		private String phone;//联系手机
		private String author;///////////////////
		private String usernames;/////
		private String avatars;///////////////
		private BmobRelation relation;
		private BmobRelation cards;
		private User user;
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
	    public BmobFile getIcon() {  
	        return icon;  
	    }  
	  
	    public void setIcon(BmobFile icon) {  
	        this.icon = icon;  
	    }  
	    public String getAuthor() {///////////////////////////////
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
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
		/*public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}*/
		public BmobRelation getCards() {
			return cards;
		}
		public void setCards(BmobRelation cards) {
			this.cards = cards;
		}
		public BmobRelation getRelation() {
			return relation;
		}
		public void setRelation(BmobRelation relation) {
			this.relation = relation;
		}
		
}
