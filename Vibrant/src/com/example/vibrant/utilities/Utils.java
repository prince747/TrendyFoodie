package com.example.vibrant.utilities;

/**
 * @author Karthik.k
 *
 */
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.vibrant.database.DataBaseConstants;

public class Utils {
	final static String TAG="Utils Class";
	public static String FILE_NAME="book.xls";
	//public static String FILE_URL ="https://dl.dropboxusercontent.com/s/rw9yxrf2ural93e/"; //DropBox
	public static String FILE_URL="http://demo.mysamplecode.com/Servlets_JSP/demoFiles/";
	public static String FILE_NAME1= "ExcelOrderFile.xls";
	public static Context ctx;
	public static File file = new File(Environment.
			           getExternalStoragePublicDirectory(
			        		 Environment.DIRECTORY_DOWNLOADS), FILE_NAME);	
	//public static File testFile = new File(ctx.getExternalFilesDir(null),FILE_NAME1);
	         
	
public static  String[] table_names={DataBaseConstants.TABLENAME_RESTAURANT,
    		 DataBaseConstants.TABLENAME_CATEGORY,
    		 DataBaseConstants.TABLENAME_ITEMS};
public static String[][] table_colums={{DataBaseConstants.REST_ID,DataBaseConstants.REST_NAME,DataBaseConstants.REST_TYPE},
    		 				  {DataBaseConstants.CATE_ID,DataBaseConstants.CATE_NAME,DataBaseConstants.CATE_REST_ID,},
    		 				  {DataBaseConstants.ITEM_ID,DataBaseConstants.ITEM_NAME,DataBaseConstants.ITEM_PRICE,
    		 				   DataBaseConstants.ITEM_CATE_ID}
    		 				  };

/* @SuppressWarnings("unused")
private static boolean saveExcelFile(Context context, String fileName) { 
	 
     // check if available and not read only 
     if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) { 
         Log.e(TAG, "Storage not available or read only"); 
         return false; 
     } 
	   // Creating Input Stream 
	// File file = new File(context.getExternalFilesDir(null), FILE_NAME);
     boolean success=false;

     //New Workbook
    Workbook wb = new HSSFWorkbook();
    //New Sheet
    Sheet sheet1 = null;;
     HSSFWorkbook hwb;
	try {
		hwb = new HSSFWorkbook(new FileInputStream(file));
		if(hwb!=null){		
			wb=hwb;			 
		    sheet1 = wb.getSheet("myOrder");		   
		}
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		 //New Sheet	   
	    sheet1 = wb.createSheet("myOrder");
		//return false;
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
     
 
     Cell c = null;

     //Cell style for header row
     CellStyle cs = wb.createCellStyle();
     cs.setFillForegroundColor(HSSFColor.LIME.index);
     cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
     
    

     // Generate column headings
     Row row=null;
     int pos;
    
     if(sheet1.getLastRowNum()==0 && !isFirst)
     {
    	 pos=0;
    	 isFirst=true;
     }
     else
     {
    	 pos=sheet1.getLastRowNum();
    	 pos++;
     }
     Log.d(TAG," position"+ pos);
     row= sheet1.createRow(pos);
      
     

     c = row.createCell(0);
     c.setCellValue("Item Number");
     c.setCellStyle(cs);

     c = row.createCell(1);
     c.setCellValue("Quantity");
     c.setCellStyle(cs);

     c = row.createCell(2);
     c.setCellValue("Price");
     c.setCellStyle(cs);

     sheet1.setColumnWidth(0, (15 * 500));
     sheet1.setColumnWidth(1, (15 * 500));
     sheet1.setColumnWidth(2, (15 * 500));

     // Create a path where we will place our List of objects on external storage 
    // File file = new File(context.getExternalFilesDir(null), fileName); 
     FileOutputStream os = null; 

     try { 
         os = new FileOutputStream(file);
         
        Log.d(TAG," IsFile "+ file.isFile());
         wb.write(os);
         Log.d("FileUtils", "Writing file" + file); 
         success = true; 
     } catch (IOException e) { 
         Log.d("FileUtils", "Error writing " + file, e); 
     } catch (Exception e) { 
         Log.d("FileUtils", "Failed to save file", e); 
     } finally { 
         try { 
             if (null != os) 
                 os.close(); 
         } catch (Exception ex) { 
         } 
     } 
     return success; 
 } 
*/

// public static void readExcelFile(Context context, String filename) { 
 public static void insertDataToDB(SQLiteDatabase db,Context context) {
     /*if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) 
     { 
         Log.e(TAG, "Storage not available or read only"); 
         return; 
     } */
	 SQLiteDatabase sdb=db;
	ctx=context;
Log.d(TAG, "Compling readExcel()");
     try{
         // Creating Input Stream 
        // File file = new File(context.getExternalFilesDir(null),FILE_NAME); 
    	// File testFile = new File(ctx.getExternalFilesDir(null),FILE_NAME1);
         FileInputStream myInput = new FileInputStream(file);

         // Create a POIFSFileSystem object 
         POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

         // Create a workbook using the File System 
         HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);        
     for(int i=0;i<myWorkBook.getNumberOfSheets();i++)      
         {

         // Get the first sheet from workbook 
         HSSFSheet mySheet = myWorkBook.getSheetAt(i);
         /** We now need something to iterate through the cells.**/
         Iterator rowIter = mySheet.rowIterator();         
          int j=0;
         while(rowIter.hasNext()){
             HSSFRow myRow = (HSSFRow) rowIter.next();
             Iterator cellIter = myRow.cellIterator();
           StringBuilder builder=new StringBuilder();
             ContentValues cv=new ContentValues();
             int k=0;
             while(cellIter.hasNext()){            	 
                 HSSFCell myCell = (HSSFCell) cellIter.next();
               // builder=builder.append(" ").append(myCell.toString()); 
                cv.put(table_colums[i][k],myCell.toString().trim());
               // builder=builder.append(" ").append(table_colums[i][k]);
                 // Log.d(TAG," Values"+ cv.get(table_colums[i][k]));
                 k++;
             }
          //  Log.d(TAG," Values  "+ builder.toString());
             if(j>0)
             {
            	  sdb.insert(table_names[i], null, cv);
            	// cv.put(DataBaseConstants.REST_ID,""+j);
            	// cv.put(DataBaseConstants.REST_NAME,"Testing" + j);
            	// cv.put(DataBaseConstants.REST_TYPE," kkkkkk");
                
            	//Log.d(TAG," Enter for Insertion into " + table_names[i] + " Index "+ j +"\n status"+ l);
            	 
             }
            j++;
         }
        // Log.d(TAG,"------------------------------------");
         }
     }catch (Exception e){e.printStackTrace(); }

     return;
 } 

 public static boolean isExternalStorageReadOnly() { 
     String extStorageState = Environment.getExternalStorageState(); 
     if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) { 
         return true; 
     } 
     return false; 
 } 

 public static boolean isExternalStorageAvailable() { 
     String extStorageState = Environment.getExternalStorageState(); 
     if (Environment.MEDIA_MOUNTED.equals(extStorageState)) { 
         return true; 
     } 
     return false; 
 } 
}
