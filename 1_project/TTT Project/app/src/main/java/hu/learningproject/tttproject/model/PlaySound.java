package hu.learningproject.tttproject.model;


import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;

import hu.learningproject.tttproject.MainActivity;
import hu.learningproject.tttproject.R;

public class PlaySound {

     public final void Play(Context cont, String name, Boolean loop){

        Uri myUri = Uri.parse("android.resource://hu.learningproject.tttproject/" + R.raw.test);

        final MediaPlayer mp = MediaPlayer.create(cont, myUri);
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


}
