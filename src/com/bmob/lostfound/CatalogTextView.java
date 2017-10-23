package com.bmob.lostfound;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.widget.TextView;
 
/**
 * ����Ի�����Ŀ��ʾ
 * <a href="http://my.oschina.net/arthor" class="referer" target="_blank">@author</a> Winter Lau
 * @date 2012-1-10 ����12:56:59
 */
public class CatalogTextView extends TextView {
 
    Paint pcPaint;
     
    public CatalogTextView(Context context) {
        this(context, null);
    }
 
    public CatalogTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
 
    public CatalogTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
 
        pcPaint = new Paint();
        pcPaint.setColor(Color.DKGRAY);//DKGRAY
        pcPaint.setTextSize(20);
        pcPaint.setTypeface(this.getPaint().getTypeface());
        pcPaint.setAntiAlias(true);
        pcPaint.setTextAlign(Align.RIGHT);
    }
 
    @Override
    protected void onDraw(Canvas canvas) {
        String txt = this.getText().toString();
        int idx = txt.lastIndexOf(':');
        String catalog = null;
        int pc = 0;
        if(idx > 0){
            catalog = txt.substring(0, idx);
            try{
                pc = Integer.parseInt(txt.substring(idx+1));
            }catch(Exception e){
                catalog = txt;
            }
        }
        else
            catalog = txt;
        int left = this.getPaddingLeft();
        int top = 38;
        canvas.drawText(catalog, left, top, this.getPaint());//���Ʒ�����
         
        //����������
        if(pc > 0) {
            String spc = String.format("%3d", pc);
            if(pc > 99)
                spc = "99+";
            int pcLeft = (int)(this.getWidth() - 20);
            canvas.drawText(spc, pcLeft, top, pcPaint);
        }
        canvas.restore();
    }
 
}
