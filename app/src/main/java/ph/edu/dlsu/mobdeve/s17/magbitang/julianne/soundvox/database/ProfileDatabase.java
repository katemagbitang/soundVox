package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProfileDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "profile.db";
    private static final int DATABASE_VERSION = 1;

    //column names
    public static final String TABLE_PROFILES = "profiles";
    public static final String PROFILE_ID = "id";
    public static final String PROFILE_NAME = "name";
//    public static final String USERS_EMAIL = "email";

    public static final String CREATE_PROFILE_TABLE = "create table " + TABLE_PROFILES + " ( "
            + PROFILE_ID + " integer primary key , "
            + PROFILE_NAME + " text); ";
//            + USERS_EMAIL + " text );";

    public ProfileDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROFILE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
        onCreate(db);
    }
}
