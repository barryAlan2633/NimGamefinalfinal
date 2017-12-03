package me.TheATeam.nimgame.Database;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class NimGameDatabase {

    //Db
    public static final String KEY_ROWID = "_id";
    public static final String LEVEL = "level";
    public static final String FIRST = "first";
    public static final String GIR_COUNT = "stick_count";
    private static final String LOG_TAG = NimGameDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "NimGameDataBase";
    private static final String DATABASE_TABLE = "settings";
    private static final int DATABASE_VERSION = 1;

    private final Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;


    public NimGameDatabase(Context context) {
        this.context = context;
    }

   //opens DB
    public NimGameDatabase open() {

        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    //creates entry
    public long fEntry(int levelofDifficulty, int firstTurn, int girCount) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(LEVEL, levelofDifficulty);
        contentValues.put(FIRST, firstTurn);
        contentValues.put(GIR_COUNT, girCount);
        return sqLiteDatabase.insert(DATABASE_TABLE, null, contentValues);
    }

    //gets data from DB
    public String getData() {

        String[] columns = new String[]{KEY_ROWID, LEVEL, FIRST, GIR_COUNT};
        Cursor c = sqLiteDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        String result = "";

        int Row1 = c.getColumnIndex(KEY_ROWID);
        int levelofDifficulty = c.getColumnIndex(LEVEL);
        int FirstTurn1= c.getColumnIndex(FIRST);
        int StickCount1 = c.getColumnIndex(GIR_COUNT);
        try {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                result = result + c.getString(Row1) + " " + c.getString(levelofDifficulty)
                        + " " + c.getString(FirstTurn1) + " " + c.getString(StickCount1);
            }
            return result;
        } catch(NullPointerException e) {
            Log.v(LOG_TAG, "No Data in the database: NULLPOINTEXCEPTION");
        }
        return null;
    }

    //is the DB empty?
    public boolean checkEmpty() {

        Log.d(LOG_TAG, "CHECKING");
        Cursor mCursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE, null);
        Boolean doesRowExist;

        if(mCursor.getCount() == 0) {

            //used to test
            //Log.d(LOG_TAG, "There is nothing here");
            return true;
        }
        return false;
    }

    //store a new user data and delete old ome
    public void deleteUser() {
        sqLiteDatabase.delete(DATABASE_TABLE, null, null);
    }

    //close DB
    public void close() {

        dbHelper.close();
    }

    //extend DB
    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(
                    "CREATE TABLE " + DATABASE_TABLE + " ("
                            + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + LEVEL + " INTEGER, " + FIRST + " INTEGER, "
                    + GIR_COUNT + " INTEGER);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        }
    }


}
