package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Sound;

public interface SoundDAO {

    long addSoundToProfile(Sound sound);
    ArrayList<Sound> getSoundsOfProfile();
    Sound getSound(int sound_id);
//    int updateProfile(Profile profile);
    int deleteSound(int sound_id);
}
