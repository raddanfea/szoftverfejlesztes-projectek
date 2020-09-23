package hu.learningproject.tttproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class MyImageView extends ImageView {
  
  private OnImageViewSizeChanged sizeCallback = null;
  
  public MyImageView(Context context) {
    super(context);
  }
  
  public MyImageView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }
  
  public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }
  
  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    if (w == 0 || h == 0) {
      return;
    }
    else{
      if(sizeCallback != null)
        sizeCallback.invoke(this, w, h);
    }
  }
  
  public void setOnImageViewSizeChanged(OnImageViewSizeChanged _callback){
    this.sizeCallback = _callback;
    
    if (getWidth() != 0 && getHeight() != 0) {
      _callback.invoke(this, getWidth(), getHeight());
    }
  }
  
  public interface OnImageViewSizeChanged{
    public void invoke(ImageView v, int w, int h);
  }
}
