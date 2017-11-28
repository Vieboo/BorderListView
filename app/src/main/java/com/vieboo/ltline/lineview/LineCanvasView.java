package com.vieboo.ltline.lineview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/11/27.
 */

public class LineCanvasView extends View {

    //ListView的标题行
    LinearLayout layout_title;
    ListView listView;
    Paint mPaint;

    public LineCanvasView(Context context, LinearLayout layout_title, ListView listView) {
        super(context);
        this.layout_title = layout_title;
        this.listView = listView;
        mPaint = new Paint();
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.DKGRAY);
        //在xml布局文件里设置没有用，需要在代码里设置
        this.listView.setDividerHeight(0);
    }

    public LineCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        //listview的item的高度（标题行的高度）
        int childHeight = layout_title.getHeight();
        //listview加标题总高度
        int totalHeight = listView.getHeight() + childHeight;
        //listview宽度
        int width = listView.getWidth();
        //横向子view的数量
        int countHorizontal = layout_title.getChildCount();
        //纵向最大行数
        int countVerticalLine = totalHeight / childHeight;
        //listview当前显示列表数量
        int itemCount = listView.getChildCount();

        /**
         * listview数据不够填满整页时
         */
        if(itemCount < countVerticalLine) {
            totalHeight = countVerticalLine * childHeight;
            //竖线
            canvas.drawLine(0, 0, 1, totalHeight, mPaint);
            for(int i=0; i<countHorizontal; i++) {
                View childView = layout_title.getChildAt(i);
                float startX = childView.getX() + childView.getWidth();
                canvas.drawLine(startX - 1, 0, startX, totalHeight, mPaint);
            }
            //横线
            canvas.drawLine(0, 0, width, 1, mPaint);
            int tempHeight = 0;
            for(int i=0; i<countVerticalLine; i++) {
                tempHeight += childHeight;
                canvas.drawLine(0, tempHeight - 1, width, tempHeight, mPaint);
            }
        }
        /**
         * listview数据超过整页可以显示的数据时
         */
        else {
            //竖线
            canvas.drawLine(0, 0, 1, totalHeight, mPaint);
            for(int i=0; i<countHorizontal; i++) {
                View childView = layout_title.getChildAt(i);
                float startX = childView.getX() + childView.getWidth();
                canvas.drawLine(startX - 1, 0, startX, totalHeight, mPaint);
            }
            //横线
            canvas.drawLine(0, 0, width, 1, mPaint);
            canvas.drawLine(0, childHeight - 1, width, childHeight, mPaint);
            float draw_position = 0;
            int i=itemCount-countVerticalLine;
            for ( ; i < countVerticalLine; i++) {
                View itemView = listView.getChildAt(i);
                float startY = itemView.getY();
                int itemHeight = itemView.getHeight();
                draw_position = startY + itemHeight + childHeight - 1;
                canvas.drawLine(0, draw_position, width, draw_position, mPaint);
            }
        }
    }
}
