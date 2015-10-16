package mku.roundedrectangleapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by user on 09.10.15.
 */
public class RoundedRectangle extends View {
    private static final int DEFAULT_WAVE_HEIGHT = 100;
    private static final int DEFAULT_HEADER_HEIGHT = 100;
    private int mWaveHeight = 100;
    private int mHeadHeight = 200;
    private Paint paint;
    private Path path;

    public RoundedRectangle(Context context) {
        super(context);
        path = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
    }
    public RoundedRectangle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundedRectangle);
        mWaveHeight = a.getInteger(R.styleable.RoundedRectangle_waveHeight, DEFAULT_WAVE_HEIGHT);
        mHeadHeight = a.getInteger(R.styleable.RoundedRectangle_headHeight, DEFAULT_HEADER_HEIGHT);
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

        path.reset();
        path.lineTo(0, mHeadHeight);
        path.quadTo(getMeasuredWidth() / 2, mHeadHeight + mWaveHeight, getMeasuredWidth(), mHeadHeight);
        path.lineTo(getMeasuredWidth(), 0);
        canvas.drawPath(path, paint);
    }
}
