package Navie_process;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import Token.Filter;
import dat.use.file.readFile;

public class processMes {

	protected Filter ft = new Filter();
	protected readFile rf;
	protected int message_count=0;
	ArrayList<String> array;
	public processMes(String path){
		rf=new readFile();
		try {
			this.rf.read(path);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	public ArrayList<String> newMessage()throws IOException{//
		try {		
			String line=null;
			array = new ArrayList<String>();
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
								array.add(token);
							}
					}		
				}

				if(line.equalsIgnoreCase("</MESSAGE>")){
					this.message_count++;
					break;
				}
			}	
			if(line == null)
			{
				rf.closeFile();
				return null;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}
	public void displayNowMessage(){// Hiển thị tin nhắn hiện tại chứa những từ nào
		for(String token:array){
			System.out.println(token);
		}

	}
	public int countMessage(){
		return this.message_count;
	}

}
