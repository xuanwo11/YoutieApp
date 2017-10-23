package entity;

import com.hfp.youtie.entity.User;

import cn.bmob.v3.BmobObject;  
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;  
  
public class Person extends BmobObject{  
  
    private BmobFile icon;  
    private String title;//����
	private String describe;//����
	private String phone;//��ϵ�ֻ�
	private String author;///////////////////
	
	private BmobRelation cards;
    public BmobFile getIcon() {  
        return icon;  
    }  
  
    public void setIcon(BmobFile icon) {  
        this.icon = icon;  
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
	
}  