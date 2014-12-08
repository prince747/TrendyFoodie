package com.example.vibrant.database;

/**
 * @author Karthik.k
 *
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.vibrant.utilities.Utils;

public class DbHelper extends SQLiteOpenHelper {   
   Context context;
	public DbHelper(Context context) {
		super(context,DataBaseConstants.DATABASE_NAME,null,1);	
		this.context=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DataBaseConstants.CREATETABLE_RESTARANT);
		db.execSQL(DataBaseConstants.CREATETABLE_CATEGORY);
		db.execSQL(DataBaseConstants.CREATETABLE_ITEM);
		Utils.insertDataToDB(db, context);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub		
	}

	public void reCreateDB(SQLiteDatabase db)
	{
		if(db!=null)
		{
		db.execSQL("DROP TABLE IF EXISTS "+ DataBaseConstants.TABLENAME_RESTAURANT);
		db.execSQL("DROP TABLE IF EXISTS "+ DataBaseConstants.TABLENAME_CATEGORY);
		db.execSQL("DROP TABLE IF EXISTS "+ DataBaseConstants.TABLENAME_ITEMS);
		
		onCreate(db);
		}
		else
		{
			Log.d("DB HELPER "," NO DB");
		}
	}
}
