package Token;

public class Filter {
	public boolean checkHaveSpecialChar(String str){
		if(str.contains("&")||
		   str.contains("^")||
		   str.contains("@")||
		   str.contains("#")||
		   str.contains("$")||
		   str.contains("%")||
		   str.contains("~")||
		   str.contains("&")||
		   str.contains("*")||
		   str.contains("(")||
		   str.contains(")")||
		   str.contains("_")||
		   str.contains("-")||
		   str.contains("+")||
		   str.contains("=")||
		   str.contains("/")||
		   str.contains("[")||
		   str.contains("]")||
		   str.contains("{")||
		   str.contains("}")||
		   str.contains(":")||
		   str.contains("'")||
		   str.contains(".")||
		   str.contains("0")||
		   str.contains("1")||
		   str.contains("2")||
		   str.contains("3")||
		   str.contains("4")||
		   str.contains("5")||
		   str.contains("6")||
		   str.contains("7")||
		   str.contains("8")||
		   str.contains("9")
		   )
			return true;
		else 
			return false;
	}
}
