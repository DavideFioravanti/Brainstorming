package com.example.streamwifi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		TextView txt_ip = (TextView) findViewById(R.id.textView2);
		
		WifiManager wim= (WifiManager) getSystemService(WIFI_SERVICE);
		
		txt_ip.setText(getLocalIpAddress2());
		txt_ip.append("\n"+ Formatter.formatIpAddress(wim.getConnectionInfo().getIpAddress()));
		
		
		
		
		
		 tv = (TextView) findViewById(R.id.testo);
		 tv.setMovementMethod(ScrollingMovementMethod.getInstance());

		 check = (CheckBox) findViewById(R.id.checkBox1);
		
		 hand = new Handler();
		 Button btn_host = (Button)findViewById(R.id.btn_host);
		 btn_host.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startServer();
			}
		});
		 
		 
		   et = (EditText) findViewById(R.id.editText1);
		   et2 = (EditText) findViewById(R.id.editText2);
		   et3 = (EditText) findViewById(R.id.editText3);
		 
		 Button btn_send = (Button)findViewById(R.id.button1);
		 btn_send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (s!=null) {
					if (s.isConnected()){
			    		
			    		try {
			    			bw.write(et.getText().toString());
							bw.newLine();
							bw.write("\r");
							bw.flush();
							tv.append("\n"+"IO: "+et.getText().toString());
							et.setText("");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					};
				}
				
			}
		});
		 
		 
		 Button btn_close = (Button)findViewById(R.id.btn_close);
		 btn_close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (s!=null) {
					if (s.isConnected()){
			    		
			    		try {
			    			s.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					};
				}
				
			}
		});
		 
		 
		 
		 
		 
		 Button btn_join = (Button)findViewById(R.id.btn_join);
		 btn_join.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (s==null) {
					
			    		
			    		
			    			startClient();
					
					
				}
				
			}
		});
		 
		 
		 

		 
	}

	static TextView tv;
	static Handler hand;
	static String str="";
	static String str_temp="";
	static Socket s; 
	boolean update = false;
	static ServerSocket ss;
	static CheckBox check;
	static ProgressDialog dialog;
	
	static BufferedWriter bw;
	static BufferedReader br;
	
	static EditText et;
	static  EditText et2;
	static  EditText et3; 
	
	
	
	
public void startClient() {
	    
	    Runnable runnable = new Runnable() {
	      @Override
	      public void run() {
	    	  
	    	  
	    
	System.out.println("AVVIO BROWSER");
	
		try {
			s = new Socket(et2.getText().toString(),Integer.parseInt(et3.getText().toString()));


			if (s.isConnected()){
				hand.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						check.setChecked(true);
						//System.out.println("CAMBIATO");
					}
				});}
	
	

	  do {
      	try {

				
      		
      		bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
      		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
	
  		
  		str_temp=br.readLine();
  		 if (!str.equalsIgnoreCase(str_temp)){
  			 str = str_temp;
  			 update=true;
  		 }
  		System.out.println(str);
  		
  		//bw.write(str.toUpperCase());
  		//bw.newLine();
  		//bw.write("\r");
  		//bw.flush();
  		
  		
  		 
  		//tv.setText(str.toUpperCase());
  		
  		} catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
      	if(update){
        hand.post(new Runnable() {
          @Override
          public void run() {
          	
          		//tv.append("\n"+str.toUpperCase());
        	  tv.append("\n"+"REMOTO: "+str);
          		update=false;
          		
          	}
        });
      }
     }while(1!=0);
	
	
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	  }
	
	    };


	    new Thread(runnable).start();
}

	
	
	public void startServer() {
	    
	    Runnable runnable = new Runnable() {
	      @Override
	      public void run() {
	 
  			System.out.println("Sono un server in attesa!");

  			
			try {
				hand.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						dialog = ProgressDialog.show(MainActivity.this, "Attesa di un client", "Attendere prego...", true);

					}
				});
								ss = new ServerSocket(9742);
				s = ss.accept();
				hand.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
				dialog.dismiss();
					}
					});
				

				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


			
			
			
			hand.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					check.setChecked(true);
					System.out.println("CAMBIATO");
				}
			});
			
						if (s.isConnected()){
			hand.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//check.setChecked(true);
					//System.out.println("CAMBIATO");
				}
			});}
	    	  
	    	  do {
	        	try {

					

	    		
	    		 bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
	    		 br = new BufferedReader(new InputStreamReader(s.getInputStream()));
	    		
	    		str_temp=br.readLine();
	    		 if (!str.equalsIgnoreCase(str_temp)){
	    			 str = str_temp;
	    			 update=true;
	    		 }
	    		System.out.println(str);
	    		
	    		//bw.write(str.toUpperCase());
	    		//bw.newLine();
	    		//bw.write("\r");
	    		//bw.flush();
	    		
	    		
	    		 
	    		//tv.setText(str.toUpperCase());
	    		
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	        	if(update){
	          hand.post(new Runnable() {
	            @Override
	            public void run() {
	            	
	          		//tv.append("\n"+str.toUpperCase());
	          	  tv.append("\n"+"REMOTO: "+str);
	            		update=false;
	            		
	            	}
	          });
	        }
	       }while(1!=0);
	      }
	    };
	    new Thread(runnable).start();
	  }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		try {
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onStop();
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	
	public String getLocalIpAddress() {
	    try {
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	            NetworkInterface intf = en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                InetAddress inetAddress = enumIpAddr.nextElement();
	                if (!inetAddress.isLoopbackAddress()) {
	                    return inetAddress.getHostAddress().toString();
	                }
	            }
	        }
	    } catch (SocketException ex) {
	        Log.e("LOG_TAG", ex.toString());
	    }
	    return null;
	}
	
	public String getLocalIpAddress2() {
	    try {
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	            NetworkInterface intf = en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                InetAddress inetAddress = enumIpAddr.nextElement();
	                if (!inetAddress.isLoopbackAddress()) {
	                    String ip = Formatter.formatIpAddress(inetAddress.hashCode());
	                    Log.i("TAG", "***** IP="+ ip);
	                    return ip;
	                }
	            }
	        }
	    } catch (SocketException ex) {
	        Log.e("TAG", ex.toString());
	    }
	    return null;
	}

}
