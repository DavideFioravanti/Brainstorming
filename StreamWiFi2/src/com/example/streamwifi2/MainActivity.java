package com.example.streamwifi2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.textView1);
		hand = new Handler();
		try {
			ss = new ServerSocket(9742);
			socket = new DatagramSocket(9742);// Inizializzo il ServerSocket una
											// volta per tutte
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Button btn1 = (Button) findViewById(R.id.button1);
		btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Server();
			}
		});
		
		
		Button btn2 = (Button) findViewById(R.id.button2);
		btn2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BroadCast();
			}
		});
		
		
		Button btn3 = (Button) findViewById(R.id.button3);
		btn3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BroadCastReceiver();
			}
		});
		
		
		 

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	Handler hand;
	ServerSocket ss;
	Socket s;
	TextView tv;

	public void Server() {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				System.out.println("Sono un server in attesa!");
				try {

					do {
						s = ss.accept();
						hand.post(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								tv.append("\n" + s.getLocalPort() + " "
										+ s.getInetAddress() + ":"
										+ s.getPort());

							}
						});
					} while (true);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};
		new Thread(runnable).start();

	}

	InetAddress getBroadcastAddress() throws IOException {
		WifiManager myWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
		DhcpInfo myDhcpInfo = myWifiManager.getDhcpInfo();
		if (myDhcpInfo == null) {
			System.out.println("Could not get broadcast address");
			return null;
		}
		int broadcast = (myDhcpInfo.ipAddress & myDhcpInfo.netmask)
				| ~myDhcpInfo.netmask;
		byte[] quads = new byte[4];
		for (int k = 0; k < 4; k++)
			quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
		System.out.println(InetAddress.getByAddress(quads).toString());
		return InetAddress.getByAddress(quads);
	}

	void BroadCast() {
		Runnable runnable = new Runnable() {
			public void run() {
 
				try {
					
					socket.setBroadcast(true);
					String data = "Ciao";
					DatagramPacket packet = new DatagramPacket(data.getBytes(),
							data.length(), getBroadcastAddress(), 9742);
					socket.send(packet);
					System.out.println("inviato: " +packet.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		new Thread(runnable).start();
	}
	byte[] buf;
	DatagramSocket socket;
	void BroadCastReceiver() {
		Runnable runnable = new Runnable() {
			public void run() {
				try {
					
					
					
					
					while (true) {
					 buf = new byte[1024];
					final DatagramPacket packet = new DatagramPacket(buf, buf.length);
					socket.receive(packet);
					System.out.println("ricevuto: " +packet.getData().toString());
					hand.post(new Runnable() {
						 
						@Override
						public void run() {
							// TODO Auto-generated method stub
							String received = new String(packet.getData(), 0, packet.getLength());
							tv.append("\nRicevuto: \""+received+"\" da: "+packet.getAddress().toString()+":"+packet.getPort());
						}
					});
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		new Thread(runnable).start();
	}

}
