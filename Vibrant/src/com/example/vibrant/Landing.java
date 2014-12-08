package com.example.vibrant;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Landing extends Activity implements OnClickListener {

	Button btn_veg, btn_nonveg, btn_sweets, btn_bakery;
	LinearLayout lay_veg, lay_nonveg, lay_bakery, lay_sweets;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.landing);

		lay_veg = (LinearLayout) findViewById(R.id.landing_layout_veg);
		lay_nonveg = (LinearLayout) findViewById(R.id.landing_layout_nonveg);
		lay_bakery = (LinearLayout) findViewById(R.id.landing_layout_bakery);
		lay_sweets = (LinearLayout) findViewById(R.id.landing_layout_sweets);

		lay_veg.setOnClickListener(this);
		lay_bakery.setOnClickListener(this);
		lay_sweets.setOnClickListener(this);
		lay_nonveg.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.landing_layout_veg:

			Toast.makeText(Landing.this, "Vegetarian", Toast.LENGTH_SHORT)
					.show();

			break;

		case R.id.landing_layout_nonveg:
			break;
		case R.id.landing_layout_bakery:
			break;
		case R.id.landing_layout_sweets:
			break;
		default:
			break;
		}
	}
}
