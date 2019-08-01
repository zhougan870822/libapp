package com.zhoug.widget.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

/**
 * Bitmap 处理工具
 */
public class BitmapUtil {
    private static final String TAG = "BitmapUtil";

    /**
     * 在给定的bitmap上写文字
     * @param bitmap
     * @param texts
     * @param textSize
     * @param textColor
     * @param startX
     * @param startY
     * @return
     */
    public static boolean drawTextToBitmap( Bitmap bitmap, List<String>  texts, int textSize,int textColor, int startX, int startY) {
        if (bitmap == null) return false;
        if(textSize<=0){
            textSize=30;
        }
        if(textColor==0){
            textColor=Color.WHITE;
        }
        if(startX<0){
            startX=50;
        }
        if(startY<0){
            startY=bitmap.getHeight()-50;
        }

        Canvas canvas = new Canvas(bitmap);
        Paint paint =new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setColor(textColor);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(textSize);
        paint.setStyle(Paint.Style.FILL);

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        startY-=fontMetrics.descent;

        for(int i=texts.size()-1;i>=0;i--){
            String text=texts.get(i);
            startY-=(fontMetrics.bottom-fontMetrics.top);
            canvas.drawText(text,startX , startY, paint);

        }


        return true;
    }

    /**
     * 在给定的bitmap上写文字
     * @param bitmap
     * @param texts
     * @return
     */
    public static boolean drawTextToBitmap( Bitmap bitmap, List<String> texts){
        return drawTextToBitmap(bitmap,texts,0,0,-1,-1);
    }

    /**
     * 在给定的bitmap上写文字
     * @param bitmap
     * @param texts
     * @param textSize
     * @return
     */
    public static boolean drawTextToBitmap( Bitmap bitmap, List<String>  texts,int textSize){
        return drawTextToBitmap(bitmap,texts,textSize,0,-1,-1);
    }


}
