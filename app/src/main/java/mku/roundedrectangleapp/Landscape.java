package mku.roundedrectangleapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by user on 09.10.15.
 */
public class Landscape extends View {
    private static final int DEFAULT_WAVE_HEIGHT = 100;
    private static final int DEFAULT_HEADER_HEIGHT = 100;
    private int mWaveHeight;
    private int mHeadHeight;

    private Paint paint;
    private Path path;

    public Landscape(Context context) {
        super(context);
    }

    public Landscape(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        path = new Path();
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Landscape);
        mWaveHeight = a.getInteger(R.styleable.Landscape_waveHeight, DEFAULT_WAVE_HEIGHT);
        mHeadHeight = a.getInteger(R.styleable.Landscape_headHeight, DEFAULT_HEADER_HEIGHT);
        a.recycle();
        setWillNotDraw(false);
    }

    public int getmWaveHeight() {
        return mWaveHeight;
    }

    public void setmWaveHeight(int mWaveHeight) {
        this.mWaveHeight = mWaveHeight;
    }

    public int getmHeadHeight() {
        return mHeadHeight;
    }

    public void setmHeadHeight(int mHeadHeight) {
        this.mHeadHeight = mHeadHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAntiAlias(true);
        path.reset();
        paint.setColor(Color.BLUE);
        path.lineTo(0, mHeadHeight + mWaveHeight);
        path.quadTo(getMeasuredWidth() / 2, mWaveHeight, getMeasuredWidth(), mHeadHeight + mWaveHeight);
        path.lineTo(getMeasuredWidth(), 0);
        canvas.drawPath(path, paint);
    }
}
