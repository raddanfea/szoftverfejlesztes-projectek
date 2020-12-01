package hu.szoftverprojekt.holdemfree.model;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import hu.szoftverprojekt.holdemfree.R;

public class PlaySound extends Service {
    MediaPlayer musicPlayer;
    Binder mBind = new Binder();
    @Override
    public IBinder onBind(Intent intent) {
        return mBind;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        musicPlayer = MediaPlayer.create(this, R.raw.dreams);
        musicPlayer.setLooping(false);
        EventBus.getDefault().register(this);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        musicPlayer.start();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

        musicPlayer.stop();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MediaVolumeEvent event){
        musicPlayer.setVolume(event.getVolume(), event.getVolume());
    }
}