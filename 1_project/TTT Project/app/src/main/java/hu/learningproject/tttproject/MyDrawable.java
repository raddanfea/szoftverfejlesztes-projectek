package hu.learningproject.tttproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.widget.ImageView;

public class MyDrawable{

    private final Paint paint;
    private Bitmap bm;
    private Canvas canv;
    private ImageView iv;

    private int bitmapWidht = 400;
    private int bitmapHeight = 700;

    public MyDrawable(ImageView imageView) {
        // Set up color and text size
        paint = new Paint();
        paint.setARGB(255, 230, 230, 230);

        bm = Bitmap.createBitmap(bitmapWidht, bitmapHeight, Bitmap.Config.ARGB_8888);
        canv = new Canvas(bm);
        iv = imageView;
        iv.setImageBitmap(bm);
        setUpPaintForStroke(2);
    }

    public void setUpPaintForStroke(int w) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(w);
        paint.setAntiAlias(true);
    }

    public void setUpPaintForRect() {
        paint.setStyle(Paint.Style.FILL);
    }

    public void fillBackground(int color) {
        canv.drawColor(color);
    }

    public void drawLine(int x, int y, int w, int h) {
        canv.drawLine(x, y, w, h, paint);
    }

    public void drawRect(int x, int y, int w, int h) {
        canv.drawRect(x+2, y+2, w-2, h-2, paint);
    }

    public void drawGid(int xOffset, int yOffset, int zoom) {
        Log.d("asd", "grid...");
//    for(int i = xOffset%zoom; i <= bitmapWidht; i += zoom) {
//      drawLine(i, 0, i, bitmapHeight);
//    }
//    for(int j = yOffset%zoom; j <= bitmapHeight; j += zoom) {
//      drawLine(0, j, bitmapWidht, j);
//    }
        setUpPaintForRect();
        for(int i = (xOffset%zoom)-zoom; i <= bitmapWidht; i += zoom)
            for(int j = (yOffset%zoom)-zoom; j <= bitmapHeight; j += zoom)
                drawRect(i, j, i+zoom, j+zoom);
    }

}

