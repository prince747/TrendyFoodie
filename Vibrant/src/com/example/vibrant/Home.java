package com.example.vibrant;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class Home extends Activity implements OnClickListener {
	Button btn_about, btn_share, btn_start, btn_how, btn_contact;

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
							Intent callOne = new Intent(Intent.ACTION_DIAL);
							callOne.setData(Uri.parse("tel:00919047012372"));
							startActivity(callOne);
						}
					});
			contactDialog.setNegativeButton("0091-9047012374",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent callTwo = new Intent(Intent.ACTION_DIAL);
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

}
