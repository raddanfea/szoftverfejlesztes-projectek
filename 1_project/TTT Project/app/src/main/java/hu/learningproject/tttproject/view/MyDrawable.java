package hu.learningproject.tttproject.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.core.content.res.ResourcesCompat;

import hu.learningproject.tttproject.R;

public class MyDrawable{
    
    private final Paint paint;
    private Bitmap bm;
    private Canvas canv;
    private ImageView iv;
    
    private int bitmapWidht;
    private int bitmapHeight;
    private Bitmap.Config bitmapConfiguration = Bitmap.Config.ARGB_8888;
    
    private Drawable xSign;
    private Drawable oSign;
    
    private int rectPadding = 4;
    
    public MyDrawable(Context context, ImageView imageView, int width, int height) {
        // Set up color and text size
        paint = new Paint();
        paint.setARGB(255, 230, 230, 230);
        
        bitmapWidht = width;
        bitmapHeight = height;
        
        Log.d("image", "imageView size: " + width + ", " + height);
        
        xSign = ResourcesCompat.getDrawable(context.getResources(), R.drawable.test_x, null);
        oSign = ResourcesCompat.getDrawable(context.getResources(), R.drawable.test_o, null);
        
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
    
    public void drawGid(int xOffset, int yOffset, int zoom, byte[][] matrix, int selectedRectX, int selectedRectY) {
        
        int matrixWidth = matrix.length;
        int matrixHeight = matrix[0].length;
        
        setUpPaintForRect();
        for(int i = (xOffset%zoom)-zoom; i <= bitmapWidht; i += zoom) {
            if ((i >= xOffset) && (i < xOffset + (matrixWidth * zoom))) {
                for(int j = (yOffset%zoom)-zoom; j <= bitmapHeight; j += zoom) {
                    if ((j >= yOffset) && (j < yOffset + (matrixHeight * zoom))) {
                        int posX = (i - xOffset)/zoom;
                        int posY = (j - yOffset)/zoom;
                        
                        if(((selectedRectX > -1) && (selectedRectY > -1)) && ((selectedRectX == posX) && (selectedRectY == posY))) {
                            paint.setARGB(255, 200, 200, 200);
                            drawRect(i, j, i+zoom, j+zoom);
                            paint.setARGB(255, 230, 230, 230);
                        } else {
                            drawRect(i, j, i+zoom, j+zoom);
                        }
                        
                        if((posX >= 0) && (posY >= 0)){
                            switch ( matrix[posX][posY] ) {
                                case 1:
                                    xSign.setBounds(i, j, i + zoom, j + zoom);
                                    xSign.draw(canv);
                                    break;
                                case 2:
                                    oSign.setBounds(i, j, i + zoom, j + zoom);
                                    oSign.draw(canv);
                                    break;
                            }
                        }
                    }
                }
            }
            
        }
        
    }
    
}
