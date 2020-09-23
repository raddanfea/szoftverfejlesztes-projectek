package hu.learningproject.tttproject.view;

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

    private int bitmapWidht;
    private int bitmapHeight;
    private Bitmap.Config bitmapConfiguration = Bitmap.Config.ARGB_8888;
    
    private int rectPadding = 4;

    public MyDrawable(ImageView imageView, int width, int height) {
        // Set up color and text size
        paint = new Paint();
        paint.setARGB(255, 230, 230, 230);

        bitmapWidht = width;
        bitmapHeight = height;
        
        Log.d("image", "imageView size: " + width + ", " + height);
        
        iv = imageView;
        recreateBitmap();
    }
    
    private void recreateBitmap() {
        bm = Bitmap.createBitmap(bitmapWidht, bitmapHeight, bitmapConfiguration);
        canv = new Canvas(bm);
        iv.setImageBitmap(bm);
    }
    
    public void resize(int w, int h) {
        bitmapWidht = w;
        bitmapHeight = h;
        recreateBitmap();
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
        canv.drawRect(x+rectPadding, y+rectPadding, w-rectPadding, h-rectPadding, paint);
    }

    public void drawGid(int xOffset, int yOffset, int zoom, int matrixWidth, int matrixHeight) {

        setUpPaintForRect();
        for(int i = (xOffset%zoom)-zoom; i <= bitmapWidht; i += zoom) {
            if ((i > xOffset) && (i <= xOffset + (matrixWidth * zoom))) {
                for(int j = (yOffset%zoom)-zoom; j <= bitmapHeight; j += zoom) {
                    if ((j > yOffset) && (j <= yOffset + (matrixHeight * zoom)))
                        drawRect(i, j, i+zoom, j+zoom);
                }
            }
            
        }
        
    }

}

