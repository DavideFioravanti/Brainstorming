import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class BarCode_PC {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub

		String[] cmd = { "adb.exe", " logcat -s \"BARCODE\"" };
		
	    Process p = Runtime.getRuntime().exec(cmd);
	    	    
	    String line;
	    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    while ((line = input.readLine()) != null) {
	      System.out.println(line);
	    }
	    input.close();
	    	    
	    	    p.waitFor();
	}

}
