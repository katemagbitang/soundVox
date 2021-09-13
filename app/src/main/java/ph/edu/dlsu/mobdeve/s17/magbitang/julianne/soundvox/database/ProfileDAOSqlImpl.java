package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;

public class ProfileDAOSqlImpl implements ProfileDAO {

    private SQLiteDatabase database;
    private AppDatabase appDatabase;

    public ProfileDAOSqlImpl(Context context){
        appDatabase = new AppDatabase(context);
    }

    @Override
    public long createProfile(Profile profile) {
        ContentValues values = new ContentValues();

        values.put(AppDatabase.PROFILE_ID, profile.getId());
        values.put(AppDatabase.PROFILE_NAME, profile.getName());
//        values.put(UserDatabase.USERS_EMAIL, user.getEmail());

        database = appDatabase.getWritableDatabase();

        long id = database.insert(AppDatabase.TABLE_PROFILES, null,values);

        if (database != null){
            appDatabase.close();
        }

        return id;
    }

    @Override
    public ArrayList<Profile> getProfiles() {
        ArrayList<Profile> result = new ArrayList<>();
        String[] columns = {AppDatabase.PROFILE_ID,
                AppDatabase.PROFILE_NAME};
//                UserDatabase.USERS_EMAIL};

        database = appDatabase.getReadableDatabase();

        Cursor cursor = database.query(AppDatabase.TABLE_PROFILES,
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
            appDatabase.close();
        }

        return result;
    }

    @Override
    public Profile getProfile(String profileName) {
        Profile profile = null;

        String query = "SELECT * from " + AppDatabase.TABLE_PROFILES
                + " where " + AppDatabase.PROFILE_NAME + " = " + profileName;

        Cursor cursor = null;

        database = appDatabase.getReadableDatabase();

        try{
            cursor = database.rawQuery(query,null);

            cursor.moveToFirst();

            while(!cursor.isAfterLast()){
                profile = new Profile();
                profile.setId(cursor.getInt(cursor.getColumnIndex(AppDatabase.PROFILE_ID)));
                profile.setName(cursor.getString(cursor.getColumnIndex(AppDatabase.PROFILE_NAME)));
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
            appDatabase.close();
        }

        return profile;
    }


    @Override
    public int updateProfile(Profile profile) {
        ContentValues values = new ContentValues();

        values.put(AppDatabase.PROFILE_ID, profile.getId());
        values.put(AppDatabase.PROFILE_NAME, profile.getName());
//        values.put(UserDatabase.USERS_EMAIL, user.getEmail());

        database = appDatabase.getWritableDatabase();

        int records = database.update(AppDatabase.TABLE_PROFILES, values,
                AppDatabase.PROFILE_ID + " = " + profile.getId(),null);

        if (database != null){
            appDatabase.close();
        }

        return records;
    }

    @Override
    public int deleteProfile(int profileid) {
        database = appDatabase.getWritableDatabase();
        int records = database.delete(AppDatabase.TABLE_PROFILES, AppDatabase.PROFILE_ID + " = " + profileid, null);

        if (database != null){
            appDatabase.close();
        }

        return records;
    }
}
