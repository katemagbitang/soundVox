package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models;

public class Sound {
    private String label;
    private String URL;
    private int id = -1;
    private Profile profile;

    public Sound(){}

    public Sound(String label) {
        this.label = label;
    }

    public Sound(String label, String URL) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
