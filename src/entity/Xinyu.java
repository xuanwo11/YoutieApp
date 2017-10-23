package entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class Xinyu extends BmobObject {

	/**
	 * qiang yu entity,每个列表item内容
	 * 2014/4/27
	 */

	
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
}

