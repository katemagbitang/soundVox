package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;

public interface ProfileDAO {

    long createProfile(Profile profile);
    ArrayList<Profile> getProfiles();
    Profile getProfile(int profileid);
    int updateProfile(Profile profile);
    int deleteProfile(int profileid);
}
