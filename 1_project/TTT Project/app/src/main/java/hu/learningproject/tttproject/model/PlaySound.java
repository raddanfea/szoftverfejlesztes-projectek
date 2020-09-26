package hu.learningproject.tttproject.model;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;

import java.lang.reflect.Field;

import hu.learningproject.tttproject.MainActivity;
import hu.learningproject.tttproject.R;


/*
A class to call for sounds using filename. Takes a bool to enable looping.
WARNING, LOOP CANNOT BE STOPPED!
*/
public class PlaySound {

     public final void Play(Context cont, String name, Boolean loop){


        final MediaPlayer mp = MediaPlayer.create(cont, getResId(name,R.raw.class));
        mp.setLooping(loop);
        mp.start();

        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          mp.seekTo(0);
                                      }
                                  }
                ,mp.getDuration()-80);
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
