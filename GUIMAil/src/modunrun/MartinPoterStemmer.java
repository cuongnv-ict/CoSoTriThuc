/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modunrun;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
//import static modunrun.Stemmer.StemAllArrayList;

/**
 *
 * @author Venus
 */
public class MartinPoterStemmer {
    /* Step 1 remove 
    
    */
     public static void main(String[] args)
   {
       /*
       Graphs and charts are great because they communicate information visually. For this reason, graphs are often used in newspapers, magazines and businesses around the world.

       */
       Hashtable<String, Integer> hash [] =  new Hashtable [2] ;
       hash[0] = new Hashtable<String,Integer>();
       hash[0].put("does", 8);
       hash[0].put("Destructiveness",9);
       hash[0].put("peddler", 10);
       hash[0].put("peddling", 10);
       hash[0].put("done", 10);
       hash[0].put("doing", 10);
       hash[1]= new Hashtable<String,Integer>();
       hash[1].put("University",11);
       hash[1].put("universe",22);
       hash[1].put("Severing",23);
       hash[1].put("several",34);
       Stemmer.StemHashTable(hash[0]);
       Stemmer.StemHashTable(hash[1]);
       for(int i=0;i<hash.length;i++)
       {
                for(String temp:hash[i].keySet())
                {
                    System.out.println(temp+  " :" +String.valueOf(((Integer) hash[i].get(temp)).intValue()));


                }
       }
//       ArrayList<String> array = new ArrayList<>();
//       array.add("Semantically");
//       array.add("Destructiveness");
//       array.add("Recognizing");
//       array.add("Severing");
//       array.add("several");
//       
//       array.add("University");
//       array.add("universe");
//       array.add("Iron");
//       array.add("ironic");
//       array.add("Animal");
//       array.add("animated");
//       array.add("visually");
//       array.add("for");
//       array.add("this");
//       array.add("reason");
//       array.add("graphs");
//       array.add("are");
//       array.add("often");
//       array.add("used");
//       array.add("in");
//       array.add("newspapers");
//       array.add("magazines");
//       array.add("and");
//       array.add("businesses");
//       array.add("around");
//       array.add("the");
//       array.add("world");
//       Stemmer.StemAllArrayList(array);
//       ArrayList<StrucOfArray> myarray;
//       myarray=getArrayListForStemmer(array);
//       for(int i=0;i<myarray.size();i++)
//       {
//           System.out.println(myarray.get(i).getWord() + ":" + String.valueOf(myarray.get(i).getIndex()));
//       }
   }
//     
//     /**
//      * 
//      * @param ArrayList<String> 
//      * @return 
//      * sắp xếp và đồng nhất các từ và đưa vào một mảng arraylist<StrucOfArray>  với object StructOfArray là một đối tượng gồm 2 thuộc tính. tên xâu và số xâu trong inputArray.
//      */
//   public static Hashtable<String, Integer>[] getArrayListForStemmer(Hashtable <String,Integer> k[])
//   {
//       
//       try
//       {
//            Collections.sort(k[0], new Comparator<String>() {
//
//                @Override
//                public int compare(String o1, String o2) {
//                     return (o1.compareTo(o2));
//                }
//            });
//
//            StrucOfArray data = new StrucOfArray();
//            data.setWord(inputArray.get(0));
//            data.setIndex(1);
//            for(int i=1;i<inputArray.size();i++)
//            {
//                if(inputArray.get(i).equals(inputArray.get(i-1)))
//                {
//                    data.setIndex(data.getIndex()+1);
//                }
//                else
//                {
//                    myarray.add(data);
//                    data = new StrucOfArray();
//                    data.setWord(inputArray.get(i));
//                    data.setIndex(1);
//                }
//            }
//            myarray.add(data);
//       }
//       catch(Exception ex)
//       {
//           ex.printStackTrace();
//       }
//       return myarray;
//   }
}
