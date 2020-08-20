package com.example.onsitetask3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class CustomView extends View {

    private Rect rect;
    private Region region;
    private Paint paint;
    private boolean isInit,canRectMove,isRight,isLeft,isTop,isBottom;
    private int left,right,bottom,top;
    private int width=50;
    private int rectWidth,rectHeight;
    float prevX,prevY;


    public CustomView(Context context) {
        super(context);

    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(){
        left=getWidth()/2-250;
        right=getWidth()/2+250;
        bottom=getHeight()/2+250;
        top=getHeight()/2-250;

        region = new Region();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(width);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);



        isInit = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!isInit){
            init();
        }
        canvas.drawColor(Color.YELLOW);
        rectHeight=bottom-top;
        rectWidth=right-left;
        region.set(left,top,right,bottom);
        canvas.drawRect(left,top,right,bottom,paint);


        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
         super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
         switch (event.getAction()){
             case MotionEvent.ACTION_DOWN:
                 if(region.contains((int)x,(int)y)){
                     prevX =x;
                     prevY=y;
                     canRectMove=true;

                 }else if(x>right-width&&x<right+width){
                     isRight=true;
                     prevX=x;
                 }else if(x>left-width && x<left+width){
                     isLeft=true;
                     prevX=x;
                 }else if(y>top-width && y < top+width){
                     isTop=true;
                     prevY=y;
                 }
                 else if(y>bottom-width && y < bottom+width){
                     isBottom=true;
                     prevY=y;
                 }

                 return true;
                 case MotionEvent.ACTION_MOVE:
                     if(canRectMove) {
                         float deltaX = x - prevX;
                         float deltaY = y - prevY;
                         if ((left + deltaX) > 0 && ((right + deltaX) < getWidth()) && (top + deltaY) > 0 && ((bottom + deltaY)) < getHeight()) {
                             left += deltaX;
                             top += deltaY;
                             right = left + rectWidth;
                             bottom = top + rectHeight;
                             prevY = y;
                             prevX = x;
                             //invalidate();
                         }
                     }else if(isRight){
                         float deltaX = x - prevX;


                              right += deltaX;
                              prevX = x;




                     }else if(isLeft){
                         float deltaX = x-prevX;

                             left+=deltaX;
                             prevX=x;

                     }else if(isTop){
                         float deltaY = y-prevY;

                             top+=deltaY;
                             prevY=y;

                     }else if(isBottom){
                         float deltaY = y-prevY;

                             bottom+=deltaY;
                             prevY=y;

                     }

                     break;
             case MotionEvent.ACTION_UP:
                 canRectMove=false;
                 isRight=false;
                 isLeft=false;
                 isTop=false;
                 isBottom=false;
                 return false;


         }
         postInvalidate();

         return true;
    }
}
