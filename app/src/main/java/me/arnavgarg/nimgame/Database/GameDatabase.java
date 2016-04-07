package me.arnavgarg.nimgame.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Arnav on 4/7/2016.
 */
public class GameDatabase {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_DIFFICULTY = "difficulty_level";
    public static final String KEY_FIRST_TURN = "first_turn";
    public static final String KEY_NUMBER_OF_STICKS = "number_of_sticks";

    private static final String LOG_TAG = GameDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "GSDatabase";
    private static final String DATABASE_TABLE = "settingsTable";
    private static final int DATABASE_VERSION = 1;

    private final Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public GameDatabase(Context context) {
        this.context = context;
    }

    public GameDatabase open() {

        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    public long createEntry(int difficultyLevel, int firstTurn, int numberOfSticks) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_DIFFICULTY, difficultyLevel);
        contentValues.put(KEY_FIRST_TURN, firstTurn);
        contentValues.put(KEY_NUMBER_OF_STICKS, numberOfSticks);
        return sqLiteDatabase.insert(DATABASE_TABLE, null, contentValues);
    }

    public String getData() {

        String[] columns = new String[]{KEY_ROWID, KEY_DIFFICULTY, KEY_FIRST_TURN, KEY_NUMBER_OF_STICKS};
        Cursor c = sqLiteDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        String result = "";

        int iRow = c.getColumnIndex(KEY_ROWID);
        int iDifficulty = c.getColumnIndex(KEY_DIFFICULTY);
        int iFirstTurn= c.getColumnIndex(KEY_FIRST_TURN);
        int iNumberSticks = c.getColumnIndex(KEY_NUMBER_OF_STICKS);

        try {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                result = result + c.getString(iRow) + " " + c.getString(iDifficulty)
                        + " " + c.getString(iFirstTurn) + " " + c.getString(iNumberSticks);
            }
            return result;
        } catch(NullPointerException e) {
            Log.v(LOG_TAG, "No Data in the database: NULLPOINTEXCEPTION");
        }

        return null;
    }

    public boolean checkEmpty() {

        Log.d(LOG_TAG, "CHECKING");
        Cursor mCursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE, null);
        Boolean rowExists;

        if(mCursor.getCount() == 0) {

            Log.d(LOG_TAG, "NOTHING IN THE DATABASE");
            return true;
        }

        return false;
//        if (mCursor.moveToFirst())
//        {
//            Log.d(LOG_TAG, "Has some data in it");
//            return false;
//        }
//
//        Log.d(LOG_TAG, "WORKED SUCCESSFULLY");
//        return true;
//        try {
//
//            String count = "SELECT count(*) FROM " + DATABASE_TABLE;
//            Cursor cursor = sqLiteDatabase.rawQuery(count, null);
//            cursor.moveToFirst();
//            int iCount = cursor.getInt(0);
//
//            if (iCount == 0) {
//                return true;
//            }
//        } catch(Exception e) {
//            Log.v(LOG_TAG, "Error in finding empty databse" + e.getMessage());
//
//        }
//
//        return false;
    }
    public void deleteAll()
    {
        sqLiteDatabase.delete(DATABASE_TABLE, null, null);
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
                    + KEY_DIFFICULTY + " INTEGER, " + KEY_FIRST_TURN + " INTEGER, "
                    + KEY_NUMBER_OF_STICKS + " INTEGER);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        }
    }


}
