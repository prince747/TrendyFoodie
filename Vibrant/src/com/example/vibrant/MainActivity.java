package com.example.vibrant;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.vibrant.database.DbHandler;
import com.example.vibrant.utilities.Utils;

public class MainActivity extends Activity {
	public Context context = this;
	final static String TAG = "MainActivity";
	final static String SHP_NAME = "savedate";
	boolean status=false;
	DbHandler dbHandler;	
	SharedPreferences sharePref;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		sharePref = getSharedPreferences(SHP_NAME, MODE_PRIVATE);

		if (dbHandler == null) {
			new DownloadExcelWorkBookTask().execute();
		}
	}

	
	 public void onClicked(View v) { 
		 if(v.getId()==R.id.read_btn) {
	        //Utils.readExcelFile(context, "sample.xls");
			 dbHandler.dropDB();
			 }
	     if(v.getId()==R.id.write_btn) { 
	    	 //Utils.saveExcelFile(context, "sample.xls"); 
	    	 } 
	     }
	 

	class DownloadExcelWorkBookTask extends AsyncTask<Object, Object, Object> {
		ProgressDialog progressBar;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressBar = new ProgressDialog(context);
			progressBar.setMessage("Loading");
			progressBar.setCancelable(false);
			progressBar.show();
		}

		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			downloadExcel(Utils.FILE_URL, Utils.FILE_NAME1, context);			
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (progressBar != null && progressBar.isShowing()) {
				progressBar.dismiss();
			}
			dbHandler=new DbHandler(context);
			if(status)
			{
				dbHandler.dropDB();
			}
		}

	}

	public void downloadExcel(String DownloadUrl, String fileName,
			Context context) {

		try {
			File root = context.getExternalFilesDir(null);

			File dir = new File(root.getAbsolutePath());
			if (dir.exists() == false) {
				dir.mkdirs();
			}

			/* For Downloading File from DropBox
			 * 
			 * try{
				File file = new File(dir, fileName);
			    URL download=new URL(DownloadUrl+fileName);
			    Log.d("DownloadManager", "download url:" + download
			    		   +" , User Info "+ download.getUserInfo()); 
			    ReadableByteChannel rbc=Channels.newChannel(download.openStream());
			    
			    FileOutputStream fileOut = new FileOutputStream(file);
			    fileOut.getChannel().transferFrom(rbc, 0, 1 << 24);
			    fileOut.flush();
			    fileOut.close();
			    rbc.close();
			}catch(Exception e){ e.printStackTrace(); }*/
			
			URL url = new URL(DownloadUrl + fileName); // you can write here any
														// link
			File file = new File(dir, fileName);
			long startTime = System.currentTimeMillis();
			Log.d("DownloadManager", "download begining");
			Log.d("DownloadManager", "download url:" + url);
			Log.d("DownloadManager", "downloaded file name:" + fileName);

			// Open a connection to that URL. 
			URLConnection ucon = url.openConnection();
			Log.d("DownloadManager", ucon.getHeaderField("Last-Modified"));

			@SuppressWarnings("deprecation")
			Date date = new Date(ucon.getHeaderField("Last-Modified"));
			
			/* Log.d(TAG," Created Date"+ date.toString());
			 * Log.d(TAG," Created Date from Shared "+ (new
			 * Date(sp.getLong("date", 0))).toString() + ", Status :::"+ ( (new
			 * Date(sp.getLong("date", 0))).equals(date)));-*/
			 

			if (!(new Date(sharePref.getLong("date", 0))).equals(date)) {				
				
				SharedPreferences.Editor editor = sharePref.edit();
				editor.putLong("date", date.getTime());
				editor.commit();

				
				// Define InputStreams to read from the URLConnection.
				 
				InputStream is = ucon.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);

				
				 /* Read bytes to the Buffer until there is nothing more to
				    read(-1).*/
				 
				ByteArrayBuffer baf = new ByteArrayBuffer(5000);
				int current = 0;
				while ((current = bis.read()) != -1) {
					
					baf.append((byte) current);
				}

				// Convert the Bytes read to a String. 
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(baf.toByteArray());
				fos.flush();
				fos.close();
				Log.d("DownloadManager",
						"download ready in"
								+ ((System.currentTimeMillis() - startTime) / 1000)
								+ " sec");
				status=true;

			} else {
				//Log.d(TAG, "Shared Pref date" + sp.getLong("date", 0));
				Log.d(TAG, "File not modified");
				status=false;
				
			}

		} catch (IOException e) {
			Log.d("DownloadManager", "Error: " + e);
			Toast.makeText(context, "Internet Error", Toast.LENGTH_LONG).show();
			status=false;
		}

	}
}
