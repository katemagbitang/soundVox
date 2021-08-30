package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;

public class ProfileDAOSqlImpl implements ProfileDAO{

    private SQLiteDatabase database;
    private ProfileDatabase profileDatabase;

    public ProfileDAOSqlImpl(Context context){
        profileDatabase = new ProfileDatabase(context);
    }

    @Override
    public long createProfile(Profile profile) {
        ContentValues values = new ContentValues();

        values.put(ProfileDatabase.PROFILE_ID, profile.getId());
        values.put(ProfileDatabase.PROFILE_NAME, profile.getName());
//        values.put(UserDatabase.USERS_EMAIL, user.getEmail());

        database = profileDatabase.getWritableDatabase();

        long id = database.insert(ProfileDatabase.TABLE_PROFILES, null,values);

        if (database != null){
            profileDatabase.close();
        }

        return id;
    }

    @Override
    public ArrayList<Profile> getProfiles() {
        ArrayList<Profile> result = new ArrayList<>();
        String[] columns = {ProfileDatabase.PROFILE_ID,
                ProfileDatabase.PROFILE_NAME};
//                UserDatabase.USERS_EMAIL};

        database = profileDatabase.getReadableDatabase();

        Cursor cursor = database.query(ProfileDatabase.TABLE_PROFILES,
                columns, null, null,null,null,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Profile temp = new Profile();
            temp.setId(cursor.getInt(0));
            temp.setName(cursor.getString(1));
//            temp.setEmail(cursor.getString(2));
            result.add(temp);
            cursor.moveToNext();
        }

        if (cursor != null){
            cursor.close();
        }

        if(database != null){
            profileDatabase.close();
        }

        return result;
    }

    @Override
    public Profile getProfile(String profileName) {
        Profile profile = null;

        String query = "SELECT * from " + ProfileDatabase.TABLE_PROFILES
                + " where " + ProfileDatabase.PROFILE_NAME + " = " + profileName;

        Cursor cursor = null;

        database = profileDatabase.getReadableDatabase();

        try{
            cursor = database.rawQuery(query,null);

            cursor.moveToFirst();

            while(!cursor.isAfterLast()){
                profile = new Profile();
                profile.setId(cursor.getInt(cursor.getColumnIndex(ProfileDatabase.PROFILE_ID)));
                profile.setName(cursor.getString(cursor.getColumnIndex(ProfileDatabase.PROFILE_NAME)));
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
            profileDatabase.close();
        }

        return profile;
    }


    @Override
    public int updateProfile(Profile profile) {
        ContentValues values = new ContentValues();

        values.put(ProfileDatabase.PROFILE_ID, profile.getId());
        values.put(ProfileDatabase.PROFILE_NAME, profile.getName());
//        values.put(UserDatabase.USERS_EMAIL, user.getEmail());

        database = profileDatabase.getWritableDatabase();

        int records = database.update(ProfileDatabase.TABLE_PROFILES, values,
                ProfileDatabase.PROFILE_ID + " = " + profile.getId(),null);

        if (database != null){
            profileDatabase.close();
        }

        return records;
    }

    @Override
    public int deleteProfile(int profileid) {
        database = profileDatabase.getWritableDatabase();
        int records = database.delete(ProfileDatabase.TABLE_PROFILES,ProfileDatabase.PROFILE_ID + " = " + profileid, null);

        if (database != null){
            profileDatabase.close();
        }

        return records;
    }
}
