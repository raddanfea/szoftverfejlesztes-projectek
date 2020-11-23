package hu.szoftverprojekt.holdemfree.model;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import java.lang.reflect.Field;

import hu.szoftverprojekt.holdemfree.R;


/*
A class to call for sounds using filename, such as "test".
Takes a bool to enable looping.
RUN ONLY ONE LOOP AT A TIME!!!
*/
public class PlaySound {

    private final static int MAX_VOLUME = 100;
    MediaPlayer mp;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mp.seekTo(0);
        }
    };

    public final void play(Context cont, String name, Boolean loop){

        mp = MediaPlayer.create(cont, getResId(name, R.raw.class));
        mp.start();

        if (loop){
            handler.postDelayed(runnable,mp.getDuration()-80);
        }
    }

    public final void setVolume(int setVolume){
        final float volume = (float) (1 - (Math.log(MAX_VOLUME - setVolume) / Math.log(MAX_VOLUME)));
        mp.setVolume(volume, volume);
    }

    public final void seekTo(int startTime){
        mp.seekTo(startTime);
    }

    //stops currently running audio. Use true to kill loop safely!
    public final void stop(boolean loop){
        if(loop){
            handler.removeCallbacks(runnable);
        }
        mp.release();

    }

    public final void resume(){
        mp.start();
        handler.postDelayed(runnable,mp.getDuration()-80);
    }

    private static int getResId(String resName, Class<R.raw> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


}