package entity;

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
public class Dongtu extends BmobObject {

	 private BmobFile icon;  
	    private String title;//����
		private String describe;//����
		private String phone;//��ϵ�ֻ�
		private String author;///////////////////
		private BmobFile view;
		private BmobRelation cards;
		private BmobRelation relation;
		private String usernames;/////
		private String avatars;///////////////
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
	    public BmobFile getView() {  
	        return view;  
	    }  


		public void setView(BmobFile view) {
			this.view = view;
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
		public String getAuthor() {///////////////////////////////
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
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