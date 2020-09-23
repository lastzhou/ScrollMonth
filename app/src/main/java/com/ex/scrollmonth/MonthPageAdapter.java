package com.ex.scrollmonth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthPageAdapter extends PagerAdapter {

    Context context;

    View monthView;
    List<View> viewList=new ArrayList<>();
    public MonthPageAdapter(Context context) {
        this.context = context;
        //9
        boolean isFirst=true;

        int curyear=2020;
        MonthView3 monthView=new MonthView3(context);
        monthView.secondHalfYear();
        viewList.add(monthView);

       int [] two=new int [2];
        two[1]=1;

        for (int i = 0; i <3 ; i++) {
//            monthView= LayoutInflater.from(context).inflate(R.layout.month,null);
             monthView=new MonthView3(context);
            if (isFirst){
                monthView.firstHalfYear();
                isFirst=false;
                two[0]=1;
            }else {
                monthView.secondHalfYear();
                isFirst=true;
                two[1]=1;

            }
            monthView.year=curyear;
            if (two[0]==1&&two[1]==1){
                curyear--;
                two[0]=0;
                two[1]=0;
            }
            viewList.add(0,monthView);
        }

        isFirst=true;
        two[0]=0;
        two[1]=0;
        for (int i = 0; i <3 ; i++) {
//            monthView= LayoutInflater.from(context).inflate(R.layout.month,null);
             monthView=new MonthView3(context);
            if (isFirst){

                monthView.firstHalfYear();
                isFirst=false;
            }else {
                monthView.secondHalfYear();
                isFirst=true;
            }
            viewList.add(monthView);
        }

    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView(viewList.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }
}
