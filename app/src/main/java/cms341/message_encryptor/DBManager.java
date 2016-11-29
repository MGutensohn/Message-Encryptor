package cms341.message_encryptor;

        import android.content.ContentValues;
        import android.content.Context;
        import net.sqlcipher.Cursor;
        import net.sqlcipher.DatabaseUtils;
        import net.sqlcipher.SQLException;
        import net.sqlcipher.database.SQLiteDatabase;
        import net.sqlcipher.database.SQLiteOpenHelper;
        import android.util.Log;
        import android.widget.ArrayAdapter;
        import android.widget.Toast;

        import java.util.ArrayList;


public class DBManager extends SQLiteOpenHelper {
    public static final String DB_NAME = "Notes";
    public static final int DB_VERSION = 1;
    public static final String STOREDKEYS = "keys";
    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String CONVERSATION = "conversation";
    public static final String KEY = "key";
    private Context context;
    private String dbkey;


    public DBManager(Context context, String key) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        this.dbkey = key;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i( "Noted", "onCreate called");
        String sqlCreate = "create table " + STOREDKEYS + " ( "
                +  ID + " integer primary key autoincrement, "
                +  DATE + " text, "
                + CONVERSATION + " text, "
                + KEY + " text "
                + ")";
        try {
            db.execSQL( sqlCreate);
        }
        catch ( SQLException se ) {
            Toast.makeText( context, se.getMessage(), Toast.LENGTH_LONG).show( );
        }

    }

    public long insert( String date, String convo, String key ) {
        long newId = -1;
        try {
            SQLiteDatabase db = this.getWritableDatabase(dbkey);

            ContentValues vals = new ContentValues();
            vals.put( DATE, date);
            vals.put(CONVERSATION, convo );
            vals.put( KEY, key);

            newId = db.insert( STOREDKEYS, null, vals);
            db.close( );

        }
        catch ( SQLException se ) {
            Toast.makeText( context, se.getMessage( ), Toast.LENGTH_LONG).show();
        }
        return newId;
    }

    public ArrayList<String> selectAll( ) {

        ArrayList<String> noteList = new ArrayList<String>( );
        try {
            SQLiteDatabase db = this.getReadableDatabase(dbkey);

            String query = "Select * from " + STOREDKEYS;
            Cursor cursor = db.rawQuery( query, null);

            cursor.moveToFirst();
            while ( !cursor.isAfterLast()) {
                String record = "";

                for ( int i = 0; i < cursor.getColumnCount(); i++) {
                    record = cursor.getString(i);
                    noteList.add(record);
                }
                cursor.moveToNext();
            }
        }
        catch ( SQLException se ) {
            Toast.makeText( context, se.getMessage( ), Toast.LENGTH_LONG).show();
        }

        return noteList;
    }

    public ArrayList<String> selectByColumn( String colName, String colValue ) {

        ArrayList<String> noteList = new ArrayList<String>( );
        try {
            SQLiteDatabase db = this.getReadableDatabase(dbkey);

            Cursor cursor = db.query( STOREDKEYS, null, colName + " LIKE ?",
                    new String[] {"%" + colValue + "%"}, null, null, colName );

            cursor.moveToFirst();
            while ( !cursor.isAfterLast()) {
                String record = "";

                for ( int i = 0; i < cursor.getColumnCount(); i++) {
                    record = cursor.getString(i);
                    noteList.add(record);
                }
                cursor.moveToNext();
            }
        }
        catch ( SQLException se ) {
            Toast.makeText( context, se.getMessage( ), Toast.LENGTH_LONG).show();
        }

        return noteList;
    }

    public ArrayAdapter<String> fillAutoCompleteTextFields(Context context, String column) {
        ArrayAdapter<String> adapter = null;

        try {
            SQLiteDatabase db = this.getReadableDatabase(dbkey);

            // select distinct values in column
            Cursor cursor = db.query(true, STOREDKEYS, new String[]{column},
                    null, null, null, null, column, null);

            int numRecs = cursor.getCount();

            if (numRecs > 0) {
                cursor.moveToFirst();

                String[] autoTextOptions = new String[numRecs];
                for (int i = 0; i < numRecs; i++) {
                    autoTextOptions[i] = cursor.getString(cursor.getColumnIndex(column));
                    cursor.moveToNext();
                }

                adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_dropdown_item_1line,
                        autoTextOptions);
                db.close();
            }
        }

        catch ( SQLException se ) {
            Toast.makeText( context, se.getMessage( ), Toast.LENGTH_LONG).show();
        }
        return adapter;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText( context, "onUpgrade called", Toast.LENGTH_LONG).show( );
    }
}