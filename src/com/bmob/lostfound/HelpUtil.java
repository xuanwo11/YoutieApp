package com.bmob.lostfound;

import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
  
import android.annotation.SuppressLint;  
import android.content.ContentResolver;  
import android.graphics.Bitmap;  
import android.graphics.BitmapFactory;  
import android.graphics.Matrix;  
import android.net.Uri;  
  
public class HelpUtil {  
    /**  
     * ����ͼƬ·����ȡ����ͼƬ��Bitmap  
     *   
     * @param url  
     * @return  
     */  
    public static Bitmap getBitmapByUrl(String url) {  
        FileInputStream fis = null;  
        Bitmap bitmap = null;  
        try {  
            fis = new FileInputStream(url);  
            bitmap = BitmapFactory.decodeStream(fis);  
  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            bitmap = null;  
        } finally {  
            if (fis != null) {  
                try {  
                    fis.close();  
                } catch (IOException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
                fis = null;  
            }  
        }  
  
        return bitmap;  
    }  
  
    /**  
     * bitmap��ת90��  
     *   
     * @param bitmap  
     * @return  
     */  
    public static  Bitmap createRotateBitmap(Bitmap bitmap) {  
        if (bitmap != null) {  
            Matrix m = new Matrix();  
            try {  
                m.setRotate(90, bitmap.getWidth() / 2, bitmap.getHeight() / 2);// 90����������Ҫѡ���90��  
                Bitmap bmp2 = Bitmap.createBitmap(bitmap, 0, 0,  
                        bitmap.getWidth(), bitmap.getHeight(), m, true);  
                bitmap.recycle();  
                bitmap = bmp2;  
            } catch (Exception ex) {  
                System.out.print("����ͼƬʧ�ܣ�" + ex);  
            }  
        }  
        return bitmap;  
    }  
      
    public static Bitmap getBitmapByUri(Uri uri,ContentResolver cr){  
        Bitmap bitmap = null;  
        try {  
            bitmap = BitmapFactory.decodeStream(cr  
                     .openInputStream(uri));  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            bitmap = null;  
        }  
        return bitmap;  
    }  
  
    /**  
     * ��ȡ��ʽ�������ַ���  
     * @param date  
     * @return  
     */  
    @SuppressLint("SimpleDateFormat")  
    public static String getDateFormatString(Date date) {  
        if (date == null)  
            date = new Date();  
        String formatStr = new String();  
        SimpleDateFormat matter = new SimpleDateFormat("yyyyMMdd_HHmmss");  
        formatStr = matter.format(date);  
        return formatStr;  
    }  
}  
