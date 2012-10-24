package com.example.barcode;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class BarCode extends Activity {
	private static final int REQUEST_ENABLE_BT = 1;
	private static BroadcastReceiver mReceiver = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bar_code); 

		// SCANNER NEW
		 IntentIntegrator_External integrator = new
		 IntentIntegrator_External(BarCode.this);
		 integrator.initiateScan();

		// SCANNER OLD
		// IntentIntegrator.initiateScan(this);


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
					TextView tv = new TextView(this);
					tv.setText(upc);
					setContentView(tv);
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
