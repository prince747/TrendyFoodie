package com.example.vibrant.database;

/**
 * @author Karthik.k
 *
 */
public class DataBaseConstants {
	// declare database name 
final public static String DATABASE_NAME="restaurent.db";
    //declare table names
final public static String TABLENAME_RESTAURANT="tbl_rest";
final public static String TABLENAME_CATEGORY="tbl_cate";
final public static String TABLENAME_ITEMS="tbl_items";
   //declare Restaurant Fields 
final public static String REST_ID="rest_id";
final public static String REST_NAME="rest_name";
final public static String REST_TYPE="rest_type";

//declare Category Fields 
final public static String CATE_ID="cate_id";
final public static String CATE_NAME="cate_name";
final public static String CATE_REST_ID="rest_id";

//declare ITEMS Fields 
final public static String ITEM_ID="item_id";
final public static String ITEM_NAME="item_name";
final public static String ITEM_PRICE="item_price";
final public static String ITEM_CATE_ID="cate_id";

//Create Statements 
final public static String
         CREATETABLE_RESTARANT="CREATE TABLE IF NOT EXISTS "
                       +TABLENAME_RESTAURANT + " ( "+REST_ID +" INTEGER AUTOINCREATEMT PRIMARY KEY"
		               + ","+REST_NAME+ " VARCHAR(32) NOT NULL "
		               + ","+ REST_TYPE +" VARCHAR(32) NOT NULL " 
		               +");"; 

final public static String
CREATETABLE_CATEGORY="CREATE TABLE IF NOT EXISTS "
              +TABLENAME_CATEGORY + " ( "+CATE_ID +" INTEGER AUTOINCREATEMT PRIMARY KEY"
              + ","+CATE_NAME+ " VARCHAR(32) NOT NULL "
              + ","+CATE_REST_ID+" INTEGR NOT NULL "
              +","+" FOREIGN KEY("+CATE_REST_ID+") REFERENCES "+ TABLENAME_RESTAURANT+"("+REST_ID+")" 
              +");"; 

final public static String
CREATETABLE_ITEM="CREATE TABLE IF NOT EXISTS "
              +TABLENAME_ITEMS + " ( "+ITEM_ID +" INTEGER AUTOINCREATEMT PRIMARY KEY"
              + ","+ITEM_NAME+ " VARCHAR(32) NOT NULL "
              + ","+ ITEM_PRICE +" INTEGER NOT NULL "
              +","+ITEM_CATE_ID+" INTEGER NOT NULL "
              +","+" FOREIGN KEY("+ITEM_CATE_ID+") REFERENCES "+ TABLENAME_CATEGORY+"("+CATE_ID+")"
              +");"; 


}
