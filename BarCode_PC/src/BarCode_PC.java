import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class BarCode_PC {
 
	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub

		
//		try{
		//String[] cmd = { "adb.exe", " logcat -s \"BARCODE\"" };
//		String[] cmd = { "adb.exe", " logcat " };
		
//	    Process p = Runtime.getRuntime().exec(cmd);
	   /* 	    
	    String line;
	    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    while ((line = input.readLine()) != null) {
	      System.out.println(line);
	    }
	   // input.close();
	    	    
	    	  //  p.waitFor();
	    	   
	    	   */
	    
/*	   String s;
	    BufferedReader stdInput = new BufferedReader(new 
	             InputStreamReader(p.getInputStream()));

	        BufferedReader stdError = new BufferedReader(new 
	             InputStreamReader(p.getErrorStream()));

	        // read the output from the command
	        System.out.println("Here is the standard output of the command:\n");
	        while ((s = stdInput.readLine()) != null) {
	            System.out.println(s);
	        }

	        // read any errors from the attempted command
	        System.out.println("Here is the standard error of the command (if any):\n");
	        while ((s = stdError.readLine()) != null) {
	            System.out.println(s);
	        }
	        stdInput.close();
	        stdError.close();
		p.waitFor();
	        System.out.println("Done.");
	
	
	
	
	
	
	}   
	catch (Exception err) {   
	    err.printStackTrace();   
	}   
	*/
		
		
		   List<String> command = new ArrayList<String>();
		    command.add("adb");
		    command.add("logcat");
		    command.add("s \"BARCODE\"");
		    //command.add("\"BARCODE\"");
		    
		
		
		
		//ProcessBuilder pb = new ProcessBuilder(command);
		    ProcessBuilder pb0 = new ProcessBuilder("adb","logcat","-c");
			pb0.redirectErrorStream(true);
			Process proc0 = pb0.start();
		    
		    
		    ProcessBuilder pb = new ProcessBuilder("adb","logcat","-s", "BARCODE","-s", "goggles");
		pb.redirectErrorStream(true);
		Process proc = pb.start();

		InputStream is = proc.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		String line;
		int exit = -1;

		while (true) {
			//while ((line = br.readLine()) != null) {
			// Outputs your process execution
		    
			if ((line = br.readLine()) != null)
			System.out.println(line);
			if (line.startsWith("V/BARCODE")) line=line.substring(line.indexOf(":")+2);
			else if(line.startsWith("I/goggles")){
				//System.out.println(line);
				if (line.indexOf("Add")==-1) line="";
				else
					
				line=line.substring(line.indexOf("Add")+4);

				
			}
			else line="";
			System.out.println(line);
			
			try {
			    Robot robot = new Robot();
			    
			    // Simulate a mouse click
			   // robot.mousePress(InputEvent.BUTTON1_MASK);
			   // robot.mouseRelease(InputEvent.BUTTON1_MASK);
			    
			    // Simulate a key press
			   // robot.keyPress(KeyEvent.VK_A);
			   // robot.keyRelease(KeyEvent.VK_A);
			Keyboard key = new Keyboard();
			if (line!="")key.type(line+"\n");
			//key.type("\n");
			
			
			} catch (AWTException e) {
			    e.printStackTrace();
			}
	
		}

	
		/*
		Process p = pb.start();
		InputStream errorOutput = new BufferedInputStream(p.getErrorStream(), 1000);
		InputStream consoleOutput = new BufferedInputStream(p.getInputStream(), 1000);

		//int exitCode = p.waitFor();

		int ch;

		System.out.println("Errors:");
		while ((ch = errorOutput.read()) != -1) {
		    System.out.print((char) ch);
		}

		System.out.println("Output:");
		while ((ch = consoleOutput.read()) != -1) {
		    System.out.print((char) ch);
		}

		//System.out.println("Exit code: " + exitCode);
	*/
	
	}
	
	
	
	

}



