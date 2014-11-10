package dat.use.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class readFile {
	public BufferedReader buf=null; 
   public boolean read(String filename) throws FileNotFoundException{
		buf=new BufferedReader(new FileReader(filename));
		if(buf==null){
			System.out.println("Doc that bai!");
		    return false;
		}
		else
		{
			System.out.println("Doc thanh cong!");
			return true;
		}
	}
   public void closeFile() throws IOException
   {
	   if(buf != null)
		   buf.close();
   }
}