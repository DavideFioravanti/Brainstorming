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
		// IntentIntegrator_External integrator = new
		// IntentIntegrator_External(BarCode.this);
		// integrator.initiateScan();

		// SCANNER OLD
		// IntentIntegrator.initiateScan(this);

		 BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		if (adapter == null) {
			// Device does not support Bluetooth
			finish(); // exit
		}
		if (adapter.isDiscovering()) {
			adapter.cancelDiscovery();
		}

		if (!adapter.isEnabled()) {
			// make sure the device's bluetooth is enabled
			
			
			Intent enableBluetooth = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBluetooth, REQUEST_ENABLE_BT);
		}

		adapter.startDiscovery();
		
		// Create a BroadcastReceiver for ACTION_FOUND
		final BroadcastReceiver mReceiver = new BroadcastReceiver() {
			
			public void onReceive(Context context, Intent intent) {

				String action = intent.getAction();
				// When discovery finds a device
				
				if (BluetoothDevice.ACTION_FOUND.equals(action)) {
					Log.v("TAG", "AAAAAAAA");
					// Get the BluetoothDevice object from the Intent
					BluetoothDevice device = intent
							.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					// Add the name and address to an array adapter to show in a
					// ListView
					// mArrayAdapter.add(device.getName() + "\n" +
					// device.getAddress());
					TextView txtHelloWorld = (TextView) findViewById(R.id.HelloWorld);
					txtHelloWorld.setText(txtHelloWorld.getText() + " "
							+ device.getName() + "\n" + device.getAddress());
					System.out.println(device.getName() + "\n"
							+ device.getAddress());
					

					

				}
			}
		};
		// Register the BroadcastReceiver
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter); // Don't forget to unregister
												// during onDestroy
		// unregisterReceiver(mReceiver);

		
        // Get the BLuetoothDevice object
        BluetoothDevice device1 = adapter.getRemoteDevice("00:02:72:02:DF:06");
        // Attempt to connect to the device
        try {
			//connect(device1);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	   
       // Prova(device1);
        try {
			ConnectThread(device1);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 

	}
	
	
	
	public void Prova(BluetoothDevice device){
				 		 //String mac = "00:15:83:3D:0A:57"; //my laptop's mac adress

		 //device = adapter.getRemoteDevice(mac); //get remote device by mac, we  assume these two devices are already paired
		  
		  
		  // Get a BluetoothSocket to connect with the given BluetoothDevice
		  BluetoothSocket socket = null;
		  OutputStream out = null; 
		  try { socket = device.createRfcommSocketToServiceRecord(SERIAL_UUID); }
		  catch
		  (IOException e) {}
		  
		  try { 
			  socket.connect();
		  out = socket.getOutputStream(); //now you can use out to send output via out.write 
		  } catch (IOException e) {}
		  
	}
	
	
	   public void ConnectThread(BluetoothDevice device) throws 
       SecurityException, NoSuchMethodException, IllegalArgumentException, 
         IllegalAccessException, InvocationTargetException {
           
           BluetoothSocket tmp = null;

           // Force a BluetoothSocket for a connection with the
           // given BluetoothDevice

           Method m = device.getClass().getMethod("createRfcommSocket", 
                          new Class[]{int.class});
           BluetoothSocket  mmSocket=null;
       mmSocket = (BluetoothSocket)m.invoke(device, Integer.valueOf(1));
   }
	
	
	
	
	final UUID SERIAL_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //UUID for serial connection 

	 public static final ParcelUuid HID = ParcelUuid.fromString("00001124-0000-1000-8000-00805f9b34fb"); //UUID for HID connection 


	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

	static BluetoothDevice m_Device;
	static BluetoothSocket m_Socket;
	static BluetoothAdapter m_BluetoothAdapter;
	
	public  void connect(BluetoothDevice device) throws Exception {
	    m_Device = device;
	    BluetoothSocket tmp = null;
 
	    Method m = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
		 tmp = (BluetoothSocket) m.invoke(device, 1);
	    m_Socket = tmp;

	    //m_BluetoothAdapter.cancelDiscovery();

	    try {
	        // This is a blocking call and will only return on a
	        // successful connection or an exception
	        m_Socket.connect();
	    }
	    catch (IOException e) {
	        try {
	            m_Socket.close();
	        }
	        catch (IOException e2) {
	        }
	        return;
	    }
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mReceiver);
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
