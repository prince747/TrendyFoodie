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

import com.example.vibrant.MainActivity.DownloadExcelWorkBookTask;
import com.example.vibrant.database.DbHandler;
import com.example.vibrant.utilities.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity implements OnClickListener {
	Button btn_about, btn_share, btn_start, btn_how, btn_contact;
	public Context context = this;
	final static String TAG = "MainActivity";
	final static String SHP_NAME = "savedate";
	boolean status=false;
	DbHandler dbHandler;	
	SharedPreferences sharePref;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		btn_about = (Button) findViewById(R.id.home_button_about);
		btn_share = (Button) findViewById(R.id.home_button_share);
		btn_how = (Button) findViewById(R.id.home_button_how);
		btn_start = (Button) findViewById(R.id.home_button_start);
		btn_contact = (Button) findViewById(R.id.home_button_contact);

		btn_about.setOnClickListener(this);
		btn_how.setOnClickListener(this);
		btn_share.setOnClickListener(this);
		btn_contact.setOnClickListener(this);
		btn_start.setOnClickListener(this);
		
		sharePref = getSharedPreferences(SHP_NAME, MODE_PRIVATE);
		if (dbHandler == null) {
			new DownloadExcelWorkBookTask().execute();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.home_button_start:
			Intent i = new Intent(Home.this, Landing.class);
			startActivity(i);
			break;

		case R.id.home_button_share:

			Builder shareDialog = new AlertDialog.Builder(Home.this);
			shareDialog.setTitle("Like our service?");
			shareDialog
					.setMessage("Spread the word and help share the joy. Thank you!");
			shareDialog.setPositiveButton("Share Now!",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent shareIntent = new Intent(Intent.ACTION_SEND);
							shareIntent.setType("text/plain");
							shareIntent
									.putExtra(
											Intent.EXTRA_TEXT,
											"Hey, check out TrendyFoodie's food delivery service."
													+ " They're just great. You should try them. http://trendyfoodie.com");
							startActivity(Intent.createChooser(shareIntent,
									"Share with.."));
						}
					});
			shareDialog.show();
			break;

		case R.id.home_button_contact:
			Builder contactDialog = new AlertDialog.Builder(Home.this);
			contactDialog.setTitle("Need Help?");
			contactDialog
					.setMessage("Don't see you're favorite items on the menus?"
							+ " Facing issues with your order? We're here to help. "
							+ " Call us on these numbers between 11am and 11pm");
			contactDialog.setPositiveButton("0091-9047012372",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent callOne = new Intent(Intent.ACTION_CALL);
							callOne.setData(Uri.parse("tel:00919047012372"));
							startActivity(callOne);
						}
					});
			contactDialog.setNegativeButton("0091-9047012374",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent callTwo = new Intent(Intent.ACTION_CALL);
							callTwo.setData(Uri.parse("tel:00919047012374"));
							startActivity(callTwo);
						}
					});
			contactDialog.show();
			break;

		case R.id.home_button_how:
			Builder dialog = new AlertDialog.Builder(Home.this);

			String str = (" - Select your favorite restaurant from the list"
					+ "\n" + " - Plan your meal & add items to your order."
					+ "\n" + " - Enter quantities. Check prices." + "\n"
					+ " - Finish your order" + "\n" + " - Sit and relax."
					+ "\n" + " - Pay on delivery at your doorstep" + "\n"
					+ "\n"
					+ " (Don't see the restaurant of your choice? No  problem."
					+ " Give us a call. We can work something out for you.)"
					+ "\n" + "\n" + "Pricing & Conditions:" + "\n" + "\n"
					+ " - If your order is above Rs. 1000 we will deliver "
					+ "your food for FREE." + "\n"
					+ " - Minimum Order value required: Rs. 200");

			ScrollView scroll_view = new ScrollView(getApplicationContext());
			TextView text_view = new TextView(getApplicationContext());
			text_view.setText(str);
			text_view.setPadding(3, 0, 0, 0);
			text_view.setTextColor(Color.GRAY);
			text_view.setTextSize(14);
			scroll_view.addView(text_view);

			dialog.setTitle("How does it work?");
			dialog.setView(scroll_view);
			dialog.setCancelable(false);
			dialog.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();

						}
					});

			dialog.show();

			break;

		default:
			break;
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
