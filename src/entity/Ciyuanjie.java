package entity;

import java.io.Serializable;

import com.hfp.youtie.entity.User;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class Ciyuanjie extends BmobObject {

	/**
	 * qiang yu entity,每个列表item内容
	 * 2014/4/27
	 */
	
	private BmobFile icon;  
	private BmobFile load; 
    private String title;//����
	private String describe;//����
	private String content2;//����
	private String phone;//��ϵ�ֻ�
	private String author;///////////////////
	private BmobFile view;
	private BmobRelation cards;
	private BmobRelation relation;
	private String usernames;/////
	private String avatars;///////////////
	private User user;
	
	private String content;
	private BmobFile Contentfigureurl;
	public BmobFile getContentfigureurl() {  
        return Contentfigureurl;  
    }  


	public void setContentfigureurl(BmobFile contentfigureurl) {
		this.Contentfigureurl = contentfigureurl;
	}
	private String url;

   

    public void setUrl(String url) {
            this.url = url;
    }


	public String getUrl() {
		// TODO Auto-generated method stub
		  return url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent2() {
		return content2;
	}
	public void setContent2(String content2) {
		this.content2 = content2;
	}
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
    public BmobFile getLoad() {  
        return load;  
    }  
  
    public void setLoad(BmobFile load) {  
        this.load = load;  
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
