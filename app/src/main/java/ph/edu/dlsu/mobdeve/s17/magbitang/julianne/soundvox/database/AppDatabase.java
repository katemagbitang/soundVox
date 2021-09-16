package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "soundvox.db";
    private static final int DATABASE_VERSION = 1;

    //column names
    public static final String TABLE_PROFILES = "profiles";
    public static final String PROFILE_ID = "profile_id";
    public static final String PROFILE_NAME = "profile_name";
//    public static final String USERS_EMAIL = "email";

    public static final String TABLE_SOUNDS = "sounds";
    public static final String SOUND_ID = "sound_id";
    public static final String SOUND_NAME = "sound_name";
    public static final String SOUND_PATH = "sound_path";

    public static final String CREATE_PROFILE_TABLE = "create table " + TABLE_PROFILES + " ( "
            + PROFILE_ID + " integer primary key autoincrement, "
            + PROFILE_NAME + " text); ";

    public static final String CREATE_SOUND_TABLE = "create table " + TABLE_SOUNDS + " ( "
            + SOUND_ID + " integer primary key autoincrement, "
            + SOUND_NAME + " text not null , "
            + SOUND_PATH + " text not null , "
            + PROFILE_ID + " integer not null , "
            + "FOREIGN KEY ("+PROFILE_ID+") REFERENCES " +TABLE_PROFILES + " ("+PROFILE_ID+"))";


    public AppDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROFILE_TABLE);
        db.execSQL(CREATE_SOUND_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SOUNDS);
        onCreate(db);
    }
}
