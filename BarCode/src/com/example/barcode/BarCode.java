package com.example.barcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class BarCode extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bar_code);
		IntentIntegrator.initiateScan(this);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case IntentIntegrator.REQUEST_CODE: {
			if (resultCode != RESULT_CANCELED) {
				IntentResult scanResult = IntentIntegrator.parseActivityResult(
						requestCode, resultCode, data);
				if (scanResult != null) {
					String upc = scanResult.getContents();

					// put whatever you want to do with the code here
					TextView tv = new TextView(this);
					tv.setText(upc);
					setContentView(tv);

				}
			}
			break;
		}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_bar_code, menu);
		return true;
	}
}
