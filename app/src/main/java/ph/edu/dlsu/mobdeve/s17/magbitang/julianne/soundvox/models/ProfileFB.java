package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileFB implements Serializable {
    private String name = "";
    private ArrayList<SoundFB> sounds;

    public ProfileFB(String name) {
        this.name = name;
        this.sounds = new ArrayList<>();
    }

    public ProfileFB(String name, ArrayList<SoundFB> sounds) {
        this.name = name;
        this.sounds = sounds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<SoundFB> getSounds() {
        return sounds;
    }

    public void addSound(SoundFB sound) {
        this.sounds.add(sound);
    }

    public void setSounds(ArrayList<SoundFB> sounds) {
        this.sounds = sounds;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("sounds", sounds);

        return result;
    }
}