package Navie_process;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.StringTokenizer;
import dat.Character.Character;
import Token.Filter;
import dat.use.file.readFile;
//import dat.use.file.writeFile;
public class Navie_process {
	public HashTable hash_GEN;
	public HashTable hash_SPAM;
	public Navie_process(String pathToTRAIN_GEN,String pathToTRAIN_SPAM){
		hash_GEN=new HashTable();
		hash_SPAM=new HashTable();
		try {
			this.addHash(hash_GEN,pathToTRAIN_GEN);
			this.addHash(hash_SPAM,pathToTRAIN_SPAM);
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	}
	public void addHash(HashTable hastb,String filename)throws IOException{//
		try {
			 Character character =new Character();
			 readFile rf=new readFile();
			 Filter ft=new Filter();
			    if(rf.read(filename)==true){
				  System.out.println("Read file successfull!");
			    String line=null;
				while(true){
					    line=rf.buf.readLine();
					    if(line==null)
						break;
					
					    if(line.length()>2&&line.toCharArray().clone()[0]=='^'){
						StringTokenizer stk=new StringTokenizer(line," ");
						  while(stk.hasMoreTokens()){
							  String token=stk.nextToken();
							  if(token.length()>1)
								if(!ft.checkHaveSpecialChar(token))
									{
									token=token.toLowerCase();
									int k=character.CharToInt(token.charAt(0));
									hastb.add(k,token);
									}
						}		
					}
				}	
				rf.closeFile();
			}
			else
				System.out.println("File not found!");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
/*public static void main(String args[]) {
	long begin = Calendar.getInstance().getTimeInMillis();
	Navie_process navip=new Navie_process("GenSpam/train_GEN.ems",
			"GenSpam/train_SPAM.ems");
	long end = Calendar.getInstance().getTimeInMillis();
	System.out.println("Executed Time: " + (end - begin));//Tinh thoi gian chay
	for(int i = 0;i < 26;i ++)
		navip.hash_SPAM.DisplayHash(i);
}*/
}
