package com.example.barcode;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BarCode extends Activity {
	private static final int REQUEST_ENABLE_BT = 1;
	private static BroadcastReceiver mReceiver = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bar_code); 
		 
		
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		    	//intent.setPackage("com.google.zxing.client.android");
				intent.putExtra("SCAN_MODE", "ONE_D_MODE");//for Qr code, its "QR_CODE_MODE" instead of "PRODUCT_MODE"
		    	startActivityForResult(intent, 0x0000c0de);
				
				
				// SCANNER NEW
				// IntentIntegrator_External integrator = new
				// IntentIntegrator_External(BarCode.this);
				// integrator.initiateScan();


				 
				 
				// SCANNER OLD
				// IntentIntegrator.initiateScan(this);
				
				
				
			}
			}
			);
		
		
		


	}
	
	
	

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case IntentIntegrator_External.REQUEST_CODE: {
			if (resultCode != RESULT_CANCELED) {
				IntentResult scanResult = IntentIntegrator_External
						.parseActivityResult(requestCode, resultCode, data);
				if (scanResult != null) {
					String upc = scanResult.getContents();

					// put whatever you want to do with the code here
					TextView tv0 = (TextView) findViewById(R.id.HelloWorld);
					tv0.setText(upc);
					//TextView tv = new TextView(this);
					//tv.setText(upc);
					//setContentView(tv);
					Log.v("BARCODE",upc);
				}
			}
			break;
		}
		}

		// if(requestCode == REQUEST_ENABLE_BT){
		// CheckBlueToothState();
		// }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_bar_code, menu);
		return true;
	}
}
