package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models;

import android.media.MediaPlayer;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class SoundFB {

    private String label;
    private String URL;

    public SoundFB(String label, String URL) {
        this.label = label;
        this.URL = URL;
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("label", label);
        result.put("URL", URL);


        return result;
    }

}
