package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Sound;

public class SoundDAOSqlImpl implements SoundDAO{

    private SQLiteDatabase database;
    private AppDatabase appDatabase;

    public SoundDAOSqlImpl(Context context) {
        appDatabase = new AppDatabase(context);
    }

    @Override
    public long addSoundToProfile(Sound sound) {
        ContentValues values = new ContentValues();
//
        values.put(AppDatabase.SOUND_ID, sound.getId());
        values.put(AppDatabase.SOUND_NAME,sound.getLabel());
        values.put(AppDatabase.SOUND_PATH, sound.getURL());
        values.put(AppDatabase.PROFILE_ID, sound.getProfileID());
//
        database = appDatabase.getWritableDatabase();
//
        long id = database.insert(AppDatabase.TABLE_SOUNDS, null,values);

        if (database != null){
            appDatabase.close();
        }

        return id;
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

    @Override
    public ArrayList<Sound> getSounds() {
        ArrayList<Sound> result = new ArrayList<>();
        String[] columns = {AppDatabase.SOUND_ID,
                AppDatabase.SOUND_NAME,
                AppDatabase.SOUND_PATH,
                AppDatabase.PROFILE_ID};
//                UserDatabase.USERS_EMAIL};

        database = appDatabase.getReadableDatabase();

        Cursor cursor = database.query(AppDatabase.TABLE_SOUNDS,
                columns, null, null,null,null,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Sound temp = new Sound();
            temp.setId(cursor.getInt(0));
            temp.setLabel(cursor.getString(1));
            temp.setURL(cursor.getString(2));
            temp.setProfileID(cursor.getInt(3));
            result.add(temp);
            cursor.moveToNext();
        }

        if (cursor != null){
            cursor.close();
        }

        if(database != null){
            appDatabase.close();
        }

        return result;
    }
}
