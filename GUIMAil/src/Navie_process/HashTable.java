package Navie_process;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import dat.Character.Character;

public class HashTable {
@SuppressWarnings("rawtypes")
public Hashtable hash[];
public int numberElements = 0;
private Character character =new Character();
@SuppressWarnings("rawtypes")
public HashTable(){
	hash=new Hashtable[26];
	for(int i=0;i<26;i++){
		hash[i]=new Hashtable();
	}
}
@SuppressWarnings("rawtypes")
public void DisplayHash(int i){
	if(i>=0&&i<26){
		int length=hash[i].size();
		System.out.println("Size is"+length);
		Set set=hash[i].keySet();
		Iterator itr= set.iterator();
		while(itr.hasNext()){
			String str=itr.next().toString();
			System.out.println(str +":"+hash[i].get(str));
		}
	}
	else
		System.out.printf("Value you using is fasle!");
}
@SuppressWarnings("unchecked")
public void add(int i,String str){
	//System.out.println(i+":"+str);
	if(i>=0&&i<26){
	//System.out.println(this.hash[i].containsKey(str));
	    if(hash[i].containsKey(str)==true){
		     int count=((Integer)hash[i].get(str)).intValue();
		     this.hash[i].put(str,new Integer(count+1));
	    }
	    else
		     this.hash[i].put(str,1);
	    numberElements += 1;
	}
}
public void showNuberElements()
{
	int number = 0;
	for(int i = 0;i < 26;i ++)
		number += hash[i].size();
	System.out.println("So luong phan tu la "+number);
}
public int freq(String token){
	int i=character.CharToInt(token.charAt(0));
	if(i<26&&i>=0){
		if(this.hash[i].containsKey(token))
			return ((Integer)this.hash[i].get(token)).intValue();
		else
			return 0;
	}
	else
	{
		System.out.println("token is error!");
		return -1;
	}
}
}
