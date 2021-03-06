package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models;

import android.media.MediaPlayer;

import java.io.Serializable;

public class Sound implements Serializable {
    private String label;
    private String URL;
    private int id = -1;
    private int profileID;
    MediaPlayer soundref;

    public Sound(){}

    public Sound(String label) {
        this.label = label;
    }

    public Sound(String label, String URL) {
        this.label = label;
        this.URL = URL;
    }
    public Sound(String label, MediaPlayer soundref) {
        this.label = label;
        this.soundref = soundref;

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfileID() {
        return profileID;
    }

    public void setProfileID(int profileID) {
        this.profileID = profileID;
    }

    public MediaPlayer getSoundref() {
        return soundref;
    }

    public void setSoundref(MediaPlayer soundref) {
        this.soundref = soundref;
    }
}
