package com.example.akira.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏主类
 *
 */
public class GameView extends GridLayout {
    private Card[][] cards = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<>();

    public GameView(Context context) {
        super(context);

        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initGameView();
    }

    private void initGameView(){
        setColumnCount(4);
        setBackgroundColor(0xffbbada0);

        //处理手指滑动事件
        setOnTouchListener(new View.OnTouchListener() {

            float startX, startY, offsetX, offsetY;//起始位置与偏移量

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if(Math.abs(offsetX)>Math.abs(offsetY)){
                            if(offsetX < -5){
                                swipeLeft();
                            }else  if(offsetX > 5){
                                swipeRight();
                            }
                        }else{
                            if(offsetY < -5){
                                swipeUp();
                            }else  if(offsetY > 5){
                                swipeDown();
                            }
                        }
                        break;
                }

                return true;//返回ture为了让这个方法能处理送手指抬起等事件
            }
        });

    }

    private void swipeLeft(){
       // Log.e("Move", "left");

        boolean merge = false; //标记是否发生合并

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                //从当前位置之后开始向右寻找最近的非空的数字
                for (int k = j+1; k < 4; k++) {

                    //如果找到一个非空的数字
                    if(cards[i][k].getNum()>0){
                        //如果当前位置为空
                        if(cards[i][j].getNum()<=0) {
                            //将找到的数字放到当前位置
                            cards[i][j].setNum(cards[i][k].getNum());
                            //并清空之前位置上的数字
                            cards[i][k].setNum(0);
                            j--;//这里是为了使移动元素的操作不影响到合并操作

                            merge = true;

                        //若果当前位置不为空而且数字相同
                        }else if(cards[i][j].equals(cards[i][k])){
                            //当前位置数字扩大一倍
                            cards[i][j].setNum(cards[i][k].getNum()*2);
                            cards[i][k].setNum(0);

                            merge = true;
                            //加分
                            MainActivity.getMainActivity().addScore(cards[i][j].getNum());
                        }

                        break;
                    }
                }
            }
        }

        if(merge=true){
            addRandomNum();
            checkComplete();
        }

    }

    private void swipeRight(){
       // Log.e("Move", "right");

        boolean merge = false;

        for (int i = 0; i < 4; i++) {
            for (int j = 3; j >= 0; j--) {
                //从当前位置之前开始向左寻找最近的非空的数字
                for (int k = j-1; k >= 0; k--) {
                    if(cards[i][k].getNum()>0){
                        if(cards[i][j].getNum()<=0) {
                            cards[i][j].setNum(cards[i][k].getNum());
                            cards[i][k].setNum(0);
                            j++;

                            merge = true;
                        }else if(cards[i][j].equals(cards[i][k])){
                            cards[i][j].setNum(cards[i][k].getNum()*2);
                            cards[i][k].setNum(0);

                            merge = true;
                            MainActivity.getMainActivity().addScore(cards[i][j].getNum());
                        }

                        break;
                    }
                }
            }
        }

        if(merge=true){
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeUp(){
       // Log.e("Move", "up");

        boolean merge = false;

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                //从当前位置之后开始向下寻找最近的非空的数字
                for (int k = i+1; k < 4; k++) {
                    if(cards[k][j].getNum()>0){
                        if(cards[i][j].getNum()<=0) {
                            cards[i][j].setNum(cards[k][j].getNum());
                            cards[k][j].setNum(0);
                            i--;

                            merge = true;
                        }else if(cards[i][j].equals(cards[k][j])){
                            cards[i][j].setNum(cards[k][j].getNum()*2);
                            cards[k][j].setNum(0);

                            merge = true;
                            MainActivity.getMainActivity().addScore(cards[i][j].getNum());
                        }

                        break;
                    }
                }
            }
        }

        if(merge=true){
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeDown(){
        //Log.e("Move", "down");

        boolean merge = false;

        for (int j = 0; j < 4; j++) {
            for (int i = 3; i >= 0; i--) {
                //从当前位置之后开始向上寻找最近的非空的数字
                for (int k = i-1; k >= 0; k--) {
                    if(cards[k][j].getNum()>0){
                        if(cards[i][j].getNum()<=0) {
                            cards[i][j].setNum(cards[k][j].getNum());
                            cards[k][j].setNum(0);
                            i++;

                            merge = true;
                        }else if(cards[i][j].equals(cards[k][j])){
                            cards[i][j].setNum(cards[k][j].getNum()*2);
                            cards[k][j].setNum(0);

                            merge = true;
                            MainActivity.getMainActivity().addScore(cards[i][j].getNum());
                        }

                        break;
                    }
                }
            }
        }

        if(merge=true){
            addRandomNum();
            checkComplete();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w, h) - 10) / 4;
        addCards(cardWidth, cardWidth);

        startGame();
    }

    /**
     * 游戏从这里开始
     */
    private void startGame(){
        MainActivity.getMainActivity().clearScore();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cards[i][j].setNum(0);
            }
        }

        //添加两个随机数
        for (int i = 0; i < 2; i++) {
            addRandomNum();
        }
    }

    private void checkComplete(){
        boolean isComplete = true;

        outer:
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if((cards[i][j].getNum()==0)||
                        (i>0&&cards[i][j].equals(cards[i-1][j]))||
                        (i<3&&cards[i][j].equals(cards[i+1][j]))||
                        (j>0&&cards[i][j].equals(cards[i][j-1]))||
                        (j<3&&cards[i][j].equals(cards[i][j+1]))){
                    isComplete = false;
                    break outer;
                }
            }
        }
        if(isComplete){
            new AlertDialog.Builder(getContext()).setTitle("您好").setMessage("游戏结束！").setPositiveButton("再来一局", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }
    }

    private void addCards(int cardW, int cardH){
        Card c;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                c = new Card(getContext());
                c.setNum(0);
                addView(c, cardW, cardH);

                cards[i][j] = c;
            }
        }
    }

    private void addRandomNum(){
        emptyPoints.clear();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(cards[i][j].getNum() <= 0){
                    emptyPoints.add(new Point(i, j));
                }
            }
        }

        Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        cards[p.x][p.y].setNum(Math.random()>0.1?2:4);
    }
}
