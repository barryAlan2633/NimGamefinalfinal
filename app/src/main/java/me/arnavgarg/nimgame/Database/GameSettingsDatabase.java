package me.arnavgarg.nimgame.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Arnav on 4/7/2016.
 */
public class GameSettingsDatabase {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_DIFFICULTY = "difficulty_level";
    public static final String KEY_FIRST_TURN = "first_turn";
    public static final String KEY_NUMBER_OF_STICKS = "number_of_sticks";

    private static final String DATABASE_NAME = "GSDatabase";
    private static final String DATABASE_TABLE = "settingsTable";
    private static final int DATABASE_VERSION = 1;

    private final Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public GameSettingsDatabase(Context context) {
        this.context = context;
    }

    public GameSettingsDatabase open() {

        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {

        dbHelper.close();
    }

    private static class DBHelper extends SQLiteOpenHelper {


        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(
                    "CREATE TABLE " + DATABASE_TABLE + " ("
                            + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_DIFFICULTY + " TEXT NOT NULL, " + KEY_FIRST_TURN + " TEXT NOT NULL, "
                    + KEY_NUMBER_OF_STICKS + " TEXT NOT NULL);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


}
