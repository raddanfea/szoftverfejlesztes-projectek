package hu.learningproject.tttproject.model;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;

import java.io.IOException;
import java.lang.reflect.Field;
import hu.learningproject.tttproject.R;


/*
A class to call for sounds using filename, such as "test".
Takes a bool to enable looping.
WARNING, LOOP CANNOT BE STOPPED!
*/
public class PlaySound {

    MediaPlayer mp;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mp.seekTo(0);
        }
    };

     public final void play(Context cont, String name, Boolean loop){

        mp = MediaPlayer.create(cont, getResId(name,R.raw.class));
        mp.setLooping(loop);
        mp.start();

        if (loop){
            handler.postDelayed(runnable,mp.getDuration()-80);
        }
     }

     //stops currently running audio. Use true to kill loop safely!
    public final void stop(boolean loop){
        if(loop){
            handler.removeCallbacks(runnable);
        }
         mp.release();

     }


    private static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


}
