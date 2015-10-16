package mku.roundedrectangleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    LinearLayout ll_iv;
    ListView lvClock;
    ImageView ivSun;
    RoundedRectangle roundedRectangle;
    float currentX, currentY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll_iv = (LinearLayout) findViewById(R.id.ll_iv);
        roundedRectangle = new RoundedRectangle(MainActivity.this);
        roundedRectangle.setmHeadHeight(0);
        roundedRectangle.setmWaveHeight(200);
        ll_iv.addView(roundedRectangle);
        ivSun = (ImageView) findViewById(R.id.ivSun);
        lvClock = (ListView) findViewById(R.id.lvClock);
        String[] hours = {"6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "22"};
        lvClock.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hours));
        lvClock.setOnScrollListener(onScrollListener);


    }

    AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            int width = roundedRectangle.getMeasuredWidth() - ivSun.getMeasuredWidth();
            float coordX = (float) width * firstVisibleItem / 12;
            if (coordX < 0) {
                coordX = 0;
            }
            float ratioY;
            if (firstVisibleItem <= 6) {
                ratioY = (float) firstVisibleItem / 6;
            } else {
                ratioY = (float) 1 - (float) (firstVisibleItem - 6) / 6;
            }
            float coordY = (float) 300 * ratioY;
            TranslateAnimation translateAnimation = new TranslateAnimation(currentX, coordX, currentY, coordY);
            translateAnimation.setDuration(1000);
            translateAnimation.setFillAfter(true);
            ivSun.startAnimation(translateAnimation);
            currentX = coordX;
            currentY = coordY;
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
