package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models;

public class Profile {
    private String name = "";
    private int id = -1;

    public Profile(String name) {
        this.name = name;
    }

    public Profile() {

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
}
