package mku.roundedrectangleapp;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    private LinearLayout llClock;
    private ListView lvClock;
    private Sun mSun;
    private Landscape mLandscape;
    private float startX, startY;
    private float currentX, currentY;
    private int width;
    private boolean onStart = true;
    private Point point1;
    private Point point2;
    private Point point3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = (FrameLayout) findViewById(R.id.frame_image);
        llClock = (LinearLayout) findViewById(R.id.ll_clock);
        mLandscape = (Landscape) findViewById(R.id.landscape);
        mSun = (Sun) findViewById(R.id.sun);
        startX = (float) (mSun.getmRadiusSun() * 1.2);
        startY = (mLandscape.getmWaveHeight() + mLandscape.getmHeadHeight() - mSun.getmRadiusSun());
        ViewTreeObserver vto = mLandscape.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(listener);
        mSun.setCoordX(startX);
        mSun.setCoordY(startY);
        lvClock = (ListView) findViewById(R.id.lv_clock);
        String[] hours = {"6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"};
        lvClock.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hours));
        lvClock.setOnScrollListener(onScrollListener);
    }

    ViewTreeObserver.OnGlobalLayoutListener listener = new ViewTreeObserver.OnGlobalLayoutListener()

    {
        @Override
        public void onGlobalLayout() {
//                                              width = (int) (mLandscape.getMeasuredWidth() - 2 * startX);
            point1 = new Point(0, 0);
            point2 = new Point((int) (startX + mLandscape.getMeasuredWidth() / 2), mLandscape.getmWaveHeight());
            point3 = new Point((int) (mLandscape.getMeasuredWidth() - 2 * startX), 0);
            // handle viewWidth here...
            if (Build.VERSION.SDK_INT < 16) {
                mLandscape.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } else {
                mLandscape.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        }
    };

    private int getRelativeLeft(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getLeft();
        else
            return myView.getLeft() + getRelativeLeft((View) myView.getParent());
    }

    private int getRelativeTop(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getTop();
        else
            return myView.getTop() + getRelativeTop((View) myView.getParent());
    }

    AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (point1 != null) {
                float t = (float) firstVisibleItem / 12;
                float x = (1 - t) * (1 - t) * point1.x + (1 - t) * t * point2.x + t * t * point3.x;
                float y = (1 - t) * (1 - t) * point1.y + (1 - t) * t * point2.y + t * t * point3.y;
                ViewCompat.animate(mSun).setDuration(1000).translationX(x).translationY(-y).start();
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
