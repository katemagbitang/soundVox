package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDatabase.PROFILE_ID;

public class SoundDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sound.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_SOUNDS = "sounds";
    public static final String SOUND_ID = "id";
    public static final String SOUND_LABEL = "label";
//    public static final URL SOUND_URL;

    public static final String CREATE_SOUND_TABLE = "create table " + TABLE_SOUNDS + " ( "
            + SOUND_ID + " integer primary key , "
            + PROFILE_ID + " integer , "
            + SOUND_LABEL + " text); ";
//            + USERS_EMAIL + " text );";

    public SoundDatabase(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SOUND_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SOUNDS);
        onCreate(db);
    }
}
