package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models;

import java.util.ArrayList;

public class Profile {
    private String name = "";
    private int id = -1;
    private ArrayList<Sound> sounds;

    public Profile(String name) {
        this.name = name;
    }

    public Profile() {
//        this.id = id;
//        this.name = name;
//        this.sounds = new ArrayList<Sound>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Sound> getSounds() {
        return sounds;
    }

    public void setSounds(ArrayList<Sound> sounds) {
        this.sounds = sounds;
    }
}
