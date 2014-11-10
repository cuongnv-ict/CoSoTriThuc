package dat.use.file;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class writeFile {
	public BufferedWriter bw=null; 
		
	   public void write(String filename) throws IOException{
		   File file = new File(filename);
		   if (!file.exists()) {
				file.createNewFile();
			}
		   FileWriter fw = new FileWriter(file.getAbsoluteFile());
		   bw = new BufferedWriter(fw);
	}
}
