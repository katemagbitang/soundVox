package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Sound;

public class SoundDAOSqlImpl implements SoundDAO {

    private SQLiteDatabase database;
    private SoundDatabase soundDatabase;

    public SoundDAOSqlImpl(Context context){
        soundDatabase = new SoundDatabase(context);
    }

    @Override
    public long addSound(Sound sound) {
        ContentValues values = new ContentValues();

        values.put(SoundDatabase.SOUND_ID, sound.getId());
        values.put(ProfileDatabase.PROFILE_ID, sound.getProfile().getId());
        values.put(SoundDatabase.SOUND_LABEL, sound.getLabel());

        database = soundDatabase.getWritableDatabase();

        long id = database.insert(SoundDatabase.TABLE_SOUNDS, null,values);

        if (database != null){
            soundDatabase.close();
        }

        return id;
    }

    @Override
    public ArrayList<Sound> getSounds() {
        ArrayList<Sound> result = new ArrayList<>();
        String[] columns = {SoundDatabase.SOUND_ID,ProfileDatabase.PROFILE_ID,
                SoundDatabase.SOUND_LABEL};
//                UserDatabase.USERS_EMAIL};

        database = soundDatabase.getReadableDatabase();

        Cursor cursor = database.query(SoundDatabase.TABLE_SOUNDS,
                columns, null, null,null,null,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Sound temp = new Sound();
            temp.setId(cursor.getInt(0));
            temp.getProfile().setId(cursor.getInt(1));
            temp.setLabel(cursor.getString(2));
//            temp.setEmail(cursor.getString(2));
            result.add(temp);
            cursor.moveToNext();
        }

        if (cursor != null){
            cursor.close();
        }

        if(database != null){
            soundDatabase.close();
        }

        return result;
    }

    @Override
    public Sound getSoundById(int soundid) {
        Sound sound = null;

        String query = "SELECT * from " + SoundDatabase.TABLE_SOUNDS
                + " where " + SoundDatabase.SOUND_ID + " = " + soundid;

        Cursor cursor = null;

        database = soundDatabase.getReadableDatabase();

        try{
            cursor = database.rawQuery(query,null);

            cursor.moveToFirst();

            while(!cursor.isAfterLast()){
                sound = new Sound();
                sound.setId(cursor.getInt(cursor.getColumnIndex(SoundDatabase.SOUND_ID)));
                sound.getProfile().setId(cursor.getInt(cursor.getColumnIndex(ProfileDatabase.PROFILE_ID)));
                sound.setLabel(cursor.getString(cursor.getColumnIndex(SoundDatabase.SOUND_LABEL)));
//                user.setEmail(cursor.getString(cursor.getColumnIndex(UserDatabase.USERS_EMAIL)));
                cursor.moveToNext();
            }
        }catch (SQLException e){
            return null;
        }

        if(cursor != null){
            cursor.close();
        }

        if (database != null){
            soundDatabase.close();
        }

        return sound;
    }

    @Override
    public ArrayList<Sound> getSoundsByProfile(int profileid) {
        ArrayList<Sound> result = new ArrayList<>();
        String[] columns = {SoundDatabase.SOUND_ID,ProfileDatabase.PROFILE_ID,
                SoundDatabase.SOUND_LABEL};
//                UserDatabase.USERS_EMAIL};

        String query = "SELECT * from " + ProfileDatabase.TABLE_PROFILES
                + " where " + ProfileDatabase.PROFILE_ID + " = " + profileid;

        Cursor cursor = null;

        database = soundDatabase.getReadableDatabase();

        try{
            cursor = database.rawQuery(query, null);

            cursor.moveToFirst();

            while(!cursor.isAfterLast()){
                Sound temp = new Sound();
                temp.setId(cursor.getInt(0));
                temp.getProfile().setId(cursor.getInt(1));
                temp.setLabel(cursor.getString(2));
//            temp.setEmail(cursor.getString(2));
                result.add(temp);
                cursor.moveToNext();
            }
        }catch (SQLException e){
            return null;
        }

        if (cursor != null){
            cursor.close();
        }

        if(database != null){
            soundDatabase.close();
        }

        return result;
    }


    @Override
    public int updateSound(Sound sound) {
        ContentValues values = new ContentValues();

        values.put(SoundDatabase.SOUND_ID, sound.getId());
        values.put(ProfileDatabase.PROFILE_ID, sound.getProfile().getId());
        values.put(SoundDatabase.SOUND_LABEL, sound.getLabel());
//        values.put(UserDatabase.USERS_EMAIL, user.getEmail());

        database = soundDatabase.getWritableDatabase();

        int records = database.update(SoundDatabase.TABLE_SOUNDS, values,
                SoundDatabase.SOUND_ID + " = " + sound.getId(),null);

        if (database != null){
            soundDatabase.close();
        }

        return records;
    }

    @Override
    public int deleteSound(int soundid) {
        database = soundDatabase.getWritableDatabase();
        int records = database.delete(SoundDatabase.TABLE_SOUNDS,SoundDatabase.SOUND_ID + " = " + soundid, null);

        if (database != null){
            soundDatabase.close();
        }
        return records;
    }
}
