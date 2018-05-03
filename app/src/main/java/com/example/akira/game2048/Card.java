package com.example.akira.game2048;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {

    private int num;
    private TextView label;


    public Card(@NonNull Context context) {
        super(context);

        label = new TextView(getContext());
        label.setTextSize(32);

        label.setBackgroundColor(0x33ffffff);
        label.setGravity(Gravity.CENTER);

        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10,10,0,0);
        addView(label, lp);

        setNum(0);
    }

    //重载判断两张数字牌相等的类
    public boolean equals(Card o){
        return getNum()==o.getNum();
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;

        if(num<=0){
            label.setText("");//设置显示数字
        }else{
            label.setText(num + "");//设置显示数字
        }
    }


}
