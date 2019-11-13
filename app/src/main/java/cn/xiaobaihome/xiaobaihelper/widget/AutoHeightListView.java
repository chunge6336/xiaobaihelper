package cn.xiaobaihome.xiaobaihelper.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class AutoHeightListView extends ListView {

    public AutoHeightListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public AutoHeightListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public AutoHeightListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int hms = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, hms);
    }

}
