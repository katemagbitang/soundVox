package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Sound;

public interface SoundDAO {

    long addSound(Sound sound);
    ArrayList<Sound> getSounds();
    Sound getSoundById(int soundid);
    ArrayList<Sound> getSoundsByProfile(int profileid);
    int updateSound(Sound sound);
    int deleteSound(int soundid);
}
