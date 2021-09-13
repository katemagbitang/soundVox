package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database;

import android.content.ContentValues;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Sound;

public class SoundDAOSqlImpl implements SoundDAO{
    @Override
    public long addSoundToProfile(Sound sound) {
//        ContentValues values = new ContentValues();
//
//        values.put(AppDatabase.SOUND_ID, profile.getId());
//        values.put(AppDatabase.PROFILE_NAME, profile.getName());
////        values.put(UserDatabase.USERS_EMAIL, user.getEmail());
//
//        database = appDatabase.getWritableDatabase();
//
//        long id = database.insert(AppDatabase.TABLE_PROFILES, null,values);
//
//        if (database != null){
//            appDatabase.close();
//        }
//
//        return id;
        return 0;
    }

    @Override
    public ArrayList<Sound> getSoundsOfProfile() {
        return null;
    }

    @Override
    public Sound getSound(int sound_id) {
        return null;
    }

    @Override
    public int deleteSound(int sound_id) {
        return 0;
    }
}
