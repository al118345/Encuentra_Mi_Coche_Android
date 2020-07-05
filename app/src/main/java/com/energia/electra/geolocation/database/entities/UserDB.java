package com.energia.electra.geolocation.database.entities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.energia.electra.geolocation.database.helper.UserElementHelper;


/**
 * Created by Ruben on 18/09/2015.
 */
public class UserDB {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";


    private Context _context;


    private UserElementHelper dbHelper;
    private SQLiteDatabase _database;

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public UserDB(Context context) {
        // Create new helper
        dbHelper = new UserElementHelper(context);
        _context=context;

    }



    /* Inner class that defines the table contents */
    public static abstract class UserElementEntry implements BaseColumns {
        private static final String _COLUMN_NAME_USER ="user" ;
        private static final String _COLUMN_NAME_PASS ="pass" ;
        private static final String _COLUMN_NAME_NAME ="name" ;

        private static final String _TABLE_NAME = "Users";

        public static String getColumnNameName() {
            return _COLUMN_NAME_NAME;
        }

        public static String getTableName() {
            return _TABLE_NAME;
        }

        public static String getColumnNamePass() {
            return _COLUMN_NAME_PASS;
        }

        public static String getColumnNameUser() {
            return _COLUMN_NAME_USER;
        }



        public static String getTable_Name()
        {
            return _TABLE_NAME;
        }


        public static final String CREATE_TABLE = "CREATE TABLE " +
                getTable_Name() + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                getColumnNameUser() + TEXT_TYPE + COMMA_SEP +
                getColumnNameName() + TEXT_TYPE + COMMA_SEP +
                getColumnNamePass() + TEXT_TYPE
               + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + getTable_Name();

        public static final String ITEMS_SELECT_QUERY= String.format("SELECT * FROM %s ", UserElementEntry.getTable_Name());

    }


    /**
     * Method to create new element in the database
     *
     * @param
     */
    public void insertElement(String username, String pass,  String user)
    {

        _database = dbHelper.getWritableDatabase();
        _database.beginTransaction();
        try {
        int resultado=-1;
        if(_database!=null){
              ContentValues values = new ContentValues();
              values.put(UserElementEntry.getColumnNameName(), username);
            values.put(UserElementEntry.getColumnNamePass(), pass);
            values.put(UserElementEntry.getColumnNameUser(), username);
              resultado= (int) _database.insert(UserElementEntry.getTable_Name(), null, values);
        }
         if(resultado==-1) {
             AlertDialog.Builder alertbox = new AlertDialog.Builder(_context);
             alertbox.setMessage("Error");
             alertbox.setMessage("Error insertar producto");
             alertbox.show();
         }
            _database.setTransactionSuccessful();
         } catch (Exception e) {
                Log.d("Error", "Error while trying to add post to database");
            } finally {
                _database.endTransaction();
            }
        _database.close();
        //TODO: add all the needed code to insert one item in database
    }





}
