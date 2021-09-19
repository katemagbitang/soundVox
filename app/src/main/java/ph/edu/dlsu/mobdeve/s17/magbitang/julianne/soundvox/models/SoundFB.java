package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models;

import android.media.MediaPlayer;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SoundFB implements Serializable {

<<<<<<< Updated upstream
    public String label;
    public String URL;
=======
    private String label;
    private String url;
>>>>>>> Stashed changes

    public SoundFB(String label, String URL) {
        this.label = label;
        this.url = URL;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String URL) {
        this.url = URL;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("label", label);
        result.put("url", url);


        return result;
    }

}
