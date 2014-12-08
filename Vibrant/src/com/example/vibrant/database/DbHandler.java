package com.example.vibrant.database;

/**
 * @author Karthik.k
 *
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbHandler{
SQLiteDatabase sdb;
DbHelper helper;

    public DbHandler(Context context)
    { 
    	super();
    	helper=new DbHelper(context);
    	sdb=helper.getWritableDatabase();
    }
   
    public void dropDB()
    {
    	helper.reCreateDB(sdb);
    }
   public long InsertRecord(String tableName,ContentValues cv)
   {
	  long status=0; //Insertion Failed	   
	    status =  sdb.insert(tableName, null, cv);	   
	 return status;
   }
}
