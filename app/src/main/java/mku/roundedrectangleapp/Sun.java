package mku.roundedrectangleapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class Sun extends View {
    private static final int DEFAULT_RADIUS = 100;
    private Paint paint;
    private Path path;
    private int mRadiusSun;
    private float coordX;
    private float coordY;

    public Sun(Context context) {
        super(context);
    }

    public Sun(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        path = new Path();
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Sun);
        mRadiusSun = a.getInteger(R.styleable.Sun_radius, DEFAULT_RADIUS);
        a.recycle();
        setWillNotDraw(false);
    }
    public int getmRadiusSun() {
        return mRadiusSun;
    }

    public void setmRadiusSun(int mRadiusSun) {
        this.mRadiusSun = mRadiusSun;
    }

    public float getCoordX() {
        return coordX;
    }

    public void setCoordX(float coordX) {
        this.coordX = coordX;
    }

    public float getCoordY() {
        return coordY;
    }

    public void setCoordY(float coordY) {
        this.coordY = coordY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAntiAlias(true);
        path.reset();
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(coordX, coordY, mRadiusSun, paint);
    }

}
