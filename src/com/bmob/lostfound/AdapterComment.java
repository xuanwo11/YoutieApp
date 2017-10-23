package com.bmob.lostfound;

import java.util.List;

import com.hfp.youtie.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import entity.Comment;

/**
 * Created by yyp on 2016/8/10.
 */
public class AdapterComment extends BaseAdapter {

    Context context;
    List<Comment> data;

    public AdapterComment(Context c, List<Comment> data){
        this.context = c;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        // 闁插秶鏁onvertView
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
            holder.comment_name = (TextView) convertView.findViewById(R.id.comment_name);
            holder.comment_content = (TextView) convertView.findViewById(R.id.comment_content);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        // 闁倿鍘ら弫鐗堝祦
        holder.comment_name.setText(data.get(i).getTitle());
        holder.comment_content.setText(data.get(i).getDescribe());

        return convertView;
    }

    /**
     * 濞ｈ濮炴稉锟介弶陇鐦庣拋锟�,閸掗攱鏌婇崚妤勩��
     * @param comment
     */
    public void addComment(Comment comment){
        data.add(comment);
        notifyDataSetChanged();
    }

    /**
     * 闂堟瑦锟戒胶琚敍灞肩┒娴滃定C閸ョ偞鏁�
     */
    public static class ViewHolder{
        TextView comment_name;
        TextView comment_content;
    }
}
