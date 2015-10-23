package mku.roundedrectangleapp;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    private Sun mSun;
    private Landscape mLandscape;
    private Point point1;
    private Point point2;
    private Point point3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = (FrameLayout) findViewById(R.id.frame_image);
        mLandscape = (Landscape) findViewById(R.id.landscape);
        mSun = (Sun) findViewById(R.id.sun);
        float startX = (float) (mSun.getmRadiusSun() * 1.2);
        float startY = (mLandscape.getmWaveHeight() + mLandscape.getmHeadHeight() - 2 * mSun.getmRadiusSun());
        ViewTreeObserver vto = mLandscape.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(listener);
        mSun.setCoordX(startX);
        mSun.setCoordY(startY);
        ListView lvClock = (ListView) findViewById(R.id.lv_clock);
        String[] hours = {"6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"};
        lvClock.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hours));
        lvClock.setOnScrollListener(onScrollListener);
    }

    ViewTreeObserver.OnGlobalLayoutListener listener = new ViewTreeObserver.OnGlobalLayoutListener()

    {
        @Override
        public void onGlobalLayout() {
            int startX = (int) (mSun.getmRadiusSun() * 1.2);
            point1 = new Point(0, 0);
            point2 = new Point((2 * startX + mLandscape.getMeasuredWidth() / 2), mLandscape.getmWaveHeight());
            point3 = new Point((mLandscape.getMeasuredWidth() - 2 * startX), 0);
            if (Build.VERSION.SDK_INT < 16) {
                mLandscape.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } else {
                mLandscape.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        }
    };

    AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        AnimatorSet animatorSet = new AnimatorSet();
        int start, finish, countPositions, previousVisibleItem;
        List<Animator> animations = new ArrayList<Animator>();

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == SCROLL_STATE_IDLE) {
                finish = previousVisibleItem;
                int count = Math.abs(finish - start);
                Log.d("onScrollStateChanged", String.valueOf(count));

                if (count > 0) {
                    if (count > 1) {
                        int center = (finish + start) / 2;
                        float t1 = (float) center / countPositions;
                        float x1 = (1 - t1) * (1 - t1) * point1.x + (1 - t1) * t1 * point2.x + t1 * t1 * point3.x;
                        float y1 = (1 - t1) * (1 - t1) * point1.y + (1 - t1) * t1 * point2.y + t1 * t1 * point3.y;
                        float t2 = (float) finish / countPositions;
                        float x2 = (1 - t2) * (1 - t2) * point1.x + (1 - t2) * t2 * point2.x + t2 * t2 * point3.x;
                        float y2 = (1 - t2) * (1 - t2) * point1.y + (1 - t2) * t2 * point2.y + t2 * t2 * point3.y;
                        animations.add(ObjectAnimator.ofFloat(mSun, View.TRANSLATION_X, mSun.getTranslationX(), x1, x2));
                        animations.add(ObjectAnimator.ofFloat(mSun, View.TRANSLATION_Y, mSun.getTranslationY(), -y1, -y2));
//                        Animator animatorX1 = ObjectAnimator.ofFloat(mSun, View.TRANSLATION_X, mSun.getTranslationX(), x1);
//                        Animator animatorY1 = ObjectAnimator.ofFloat(mSun, View.TRANSLATION_Y, mSun.getTranslationY(), -y1);
//                        Animator animatorX2 = ObjectAnimator.ofFloat(mSun, View.TRANSLATION_X, x1, x2);
//                        Animator animatorY2 = ObjectAnimator.ofFloat(mSun, View.TRANSLATION_Y, mSun.getTranslationY()-y1, -y2);
//                        animatorSet.play(animatorX2).with(animatorY2).after(animatorX1).with(animatorY1);
                    } else if (count == 1) {
                        float t = (float) finish / countPositions;
                        float x = (1 - t) * (1 - t) * point1.x + (1 - t) * t * point2.x + t * t * point3.x;
                        float y = (1 - t) * (1 - t) * point1.y + (1 - t) * t * point2.y + t * t * point3.y;
                        animations.add(ObjectAnimator.ofFloat(mSun, View.TRANSLATION_X, mSun.getTranslationX(), x));
                        animations.add(ObjectAnimator.ofFloat(mSun, View.TRANSLATION_Y, mSun.getTranslationY(), -y));
                    }
                    animatorSet.setDuration(2000);
                    animatorSet.playTogether(animations);
                    animatorSet.start();
                    animations.clear();
                }
            } else if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                start = previousVisibleItem;
            }


        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem > 0 && countPositions == 0) {
                countPositions = totalItemCount - visibleItemCount;
            }
//            finish = previousVisibleItem;
//            int count = Math.abs(finish - start);
//            if (count > 0) {
//                if (count > 1) {
//                    int center = (finish + start) / 2;
//                    float t1 = (float) center / countPositions;
//                    float x1 = (1 - t1) * (1 - t1) * point1.x + (1 - t1) * t1 * point2.x + t1 * t1 * point3.x;
//                    float y1 = (1 - t1) * (1 - t1) * point1.y + (1 - t1) * t1 * point2.y + t1 * t1 * point3.y;
//                    float t2 = (float) finish / countPositions;
//                    float x2 = (1 - t2) * (1 - t2) * point1.x + (1 - t2) * t2 * point2.x + t2 * t2 * point3.x;
//                    float y2 = (1 - t2) * (1 - t2) * point1.y + (1 - t2) * t2 * point2.y + t2 * t2 * point3.y;
//                    animations.add(ObjectAnimator.ofFloat(mSun, View.TRANSLATION_X, mSun.getTranslationX(), x1, x2));
//                    animations.add(ObjectAnimator.ofFloat(mSun, View.TRANSLATION_Y, mSun.getTranslationY(), -y1, -y2));
//                } else if (count == 1) {
//                    float t = (float) finish / countPositions;
//                    float x = (1 - t) * (1 - t) * point1.x + (1 - t) * t * point2.x + t * t * point3.x;
//                    float y = (1 - t) * (1 - t) * point1.y + (1 - t) * t * point2.y + t * t * point3.y;
//                    animations.add(ObjectAnimator.ofFloat(mSun, View.TRANSLATION_X, mSun.getTranslationX(), x));
//                    animations.add(ObjectAnimator.ofFloat(mSun, View.TRANSLATION_Y, mSun.getTranslationY(), -y));
//                }
//                Log.d("onScroll", String.valueOf(count));
//                animatorSet.setDuration(2000);
//                animatorSet.playTogether(animations);
//                animatorSet.start();
//                animations.clear();
//            }
//            start = finish;
            previousVisibleItem = firstVisibleItem;
        }
    };

    public void animateSun(float t) {
//        ViewCompat.animate(mSun).setDuration(1000).translationY(-y).translationX(x).start();
    }

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
