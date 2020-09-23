package com.ex.scrollmonth;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MonthView3 extends View {
   List<String> months=new ArrayList<>();
   Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
   Paint subPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
   Paint rectPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
      List<Rect> rectList=new ArrayList<>();
      Rect bounds=new Rect();
      int clickIndex=-1;
    Callback callback;
    boolean mScrolling=false;
    public int year;
    public MonthView3(Context context) {
        super(context);
        init();
    }
    public MonthView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    void init(){
        paint.setTextSize(sp2px(15));
        paint.setColor(Color.parseColor("#565566"));
        subPaint.setTextSize(sp2px(22));
        subPaint.setColor(Color.parseColor("#333333"));
        rectPaint.setColor(Color.parseColor("#1D5EEA"));
        rectPaint.setStyle(Paint.Style.FILL);
    }

    public void addData() {
        secondHalfYear();

    }

    public void secondHalfYear(){
        months.clear();
        for (int i = 7; i <=12; i++) {
            months.add(i+"月");
        }
    }
    public void firstHalfYear(){
        months.clear();
        for (int i = 1; i <=6; i++) {
            months.add(i+"月");
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


      int y=0;
        paint.setTextSize(sp2px(15));
        paint.setColor(Color.parseColor("#565566"));
       int w= getWidth()/6;
       int h=dp2px(43);
        int x=0;
        int right=w;
        Rect rect;

        for (int i = 0; i < months.size(); i++) {
            String month=months.get(i);

            if (rectList.size()!=months.size()){
                 rect=new Rect();
                rect.set(x,y,right,y+h);
                rectList.add(rect);
            }else {
                rect=rectList.get(i);
            }

//            canvas.drawRect(rect,rectPaint);
            paint.getTextBounds(month,0,month.length(),bounds);
            int txtx=rect.centerX()-bounds.centerX();
            int txty=rect.centerY()-bounds.centerY();
            if (clickIndex==i){
//                monthSubTitle="2020年8月";
//                canvas.drawText(monthSubTitle,marignLeft,marignTop,subPaint);
                canvas.drawCircle(rect.centerX(),rect.centerY(),rect.height()/2,rectPaint);
                paint.setColor(Color.parseColor("#FFFFFF"));
            }else {
                paint.setColor(Color.parseColor("#565566"));
            }
            canvas.drawText(month,txtx,txty,paint);
            x+=w;
            right+=w;
        }
    }

    private   int sp2px(float spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, Resources.getSystem().getDisplayMetrics());
    }

    private  int dp2px(float dpVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, Resources.getSystem().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w= MeasureSpec.getSize(widthMeasureSpec);
            setMeasuredDimension(w,dp2px(44));

    }

//    onInterceptTouchEvent

    float downx,diff;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int rawy=  (int) event.getY();
                int x=  (int) event.getRawX();
                if (isClick(x,rawy)){
                    invalidate();
                    return true;
                }
                break;

        }
        return true;
    }

    public void resetData(boolean firstHalfYear){
        if (firstHalfYear){
            for (int i = 7; i <=12 ; i++) {
                months.add(i+"月");
            }
        }else {
            for (int i = 1; i <=6 ; i++) {
                months.add(i+"月");
            }
        }
    }


    boolean isClick(int x,int y){
        for (int i = 0; i < rectList.size(); i++) {
            Rect rect=rectList.get(i);
            boolean isx=x>=rect.left&&x<rect.right;
//            boolean isy=y>=rect.top&&x<rect.bottom;
            boolean isy=true;
            Log.d("","rect.left=="+rect.left+"rect.right="+rect.right);
            if (isx&&isy){
                clickIndex=i;
                if (callback!=null){
                    callback.onItemClick(i);
                }
                return true;
            }
        }


        return false;
    }




    public interface  Callback {
        void   onItemClick(int index);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
