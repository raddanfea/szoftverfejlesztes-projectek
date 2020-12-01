package hu.szoftverprojekt.holdemfree.model;

public class MediaVolumeEvent {
    private int volume;


    public MediaVolumeEvent(int volume) {
        this.volume = volume;
    }

    public float getVolume() {
        float maxVolume=100;
        return (float) (1 - (Math.log(maxVolume - volume) / Math.log(maxVolume)));

    }
}