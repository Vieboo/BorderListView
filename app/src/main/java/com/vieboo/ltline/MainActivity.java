package com.vieboo.ltline;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vieboo.ltline.lineview.LineCanvasView;

public class MainActivity extends AppCompatActivity {

    RelativeLayout layout_main;
    LinearLayout layout_title;
    ListView list_view;
    LineCanvasView lineCanvasView;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        layout_main = findViewById(R.id.layout_main);
        layout_title = findViewById(R.id.layout_title);
        list_view = findViewById(R.id.list_view);
        lineCanvasView = new LineCanvasView(mContext, layout_title, list_view);
        layout_main.addView(lineCanvasView, 1);

        list_view.setAdapter(new MainAdapter());
        //当listview数据超过整页可以显示的数据时，需要动态绘制line
        list_view.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(null != lineCanvasView) lineCanvasView.invalidate();
            }
        });
    }

    class MainAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 11;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if(null == view) {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_list_item, null, false);
                holder = new ViewHolder();
                holder.tv_a = view.findViewById(R.id.tv_a);
                holder.tv_b = view.findViewById(R.id.tv_b);
                holder.tv_c = view.findViewById(R.id.tv_c);
                holder.tv_d = view.findViewById(R.id.tv_d);
                view.setTag(holder);
            }else {
                holder = (ViewHolder) view.getTag();
            }
            holder.tv_a.setText("A_" + i);
            holder.tv_b.setText("B_" + i);
            holder.tv_c.setText("C_" + i);
            holder.tv_d.setText("D_" + i);
            return view;
        }
    }

    class ViewHolder {
        TextView tv_a, tv_b, tv_c, tv_d;
    }
}
