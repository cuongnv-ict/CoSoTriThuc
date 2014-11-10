package Navie_Bayesian;

import Navie_process.*;
import com.edu.stopwords.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JTextField;
import modunrun.Stemmer;

public class Traning {
    /*
     * Co hai ham quan trong la ham Traning va ham TestProcess
     * Traning co hai vao la hai duong dan toi file train mail thuong va mail rac, ham nay
     * thuc hien viec dieu phoi cac ham khac de thu duoc hai bang bam chua xac xuat cua cac
     * tu khoa.
     * TestProcess co dau vao la duong dan toi file chua mail test, ham nay thuc hien viec
     * lay tung email trong file ra phan loai va tinh toan do chinh sai trong toan bo file
     * dau vao.
     * Author: Le Trung Kien
     */

    public Hashtable<String, Integer> tableSpamAll;
    public Hashtable<String, Integer> tableGenAll;
    public int numWordInSpam;
    public int numWordInGen;
    public Hashtable<String, Double> tableProbGen = new Hashtable<String, Double>();
    public Hashtable<String, Double> tableProbSpam = new Hashtable<String, Double>();
    public Hashtable<String, Double> aspectRatio = new Hashtable<String, Double>();
    public double betaCut;
    public double minCut;
    StopWord sWord = new StopWord();

    public Traning(String dataSetGen, String dataSetSpam) throws IOException {
        Navie_process input = new Navie_process(dataSetGen, dataSetSpam);
        HashTable fastCheck = input.hash_SPAM;
        HashTable fastGen = input.hash_GEN;
        numWordInSpam = fastCheck.numberElements;
        numWordInGen = fastGen.numberElements;
        tableSpamAll = getTable(input.hash_SPAM);
        tableGenAll = getTable(input.hash_GEN);
        //Stemmer.StemHashTable(tableSpamAll);
        //Stemmer.StemHashTable(tableGenAll);
        ArrayList<String> allOfKeys = new ArrayList<String>(tableSpamAll.keySet());
        Set<String> temp = tableGenAll.keySet();
        Iterator<String> vkeys = temp.iterator();
        while (vkeys.hasNext()) {
            String temp1 = vkeys.next();
            if (!tableSpamAll.containsKey(temp1)) {
                allOfKeys.add(temp1);
            }
        }
        System.out.println("All Of key size " + allOfKeys.size());
        ArrayList<String> stopword = sWord.letterStopWords(allOfKeys, true);
        int len = stopword.size();
        int i;
        //System.out.println("Stop word "+stopword.size());
        //System.out.print("\nSpam size "+tableSpamAll.size()+"\nGen size "+tableGenAll.size());
        for (i = 0; i < len; i++) {
            String temp1 = stopword.get(i);
            if (tableSpamAll.containsKey(temp1)) {
                numWordInSpam -= tableSpamAll.remove(temp1);
            }
            if (tableGenAll.containsKey(temp1)) {
                numWordInGen -= tableGenAll.remove(temp1);
            }
        }
        Stemmer.StemHashTable(tableSpamAll);
        Stemmer.StemHashTable(tableGenAll);
        allOfKeys.clear();
        allOfKeys = new ArrayList<String>(tableSpamAll.keySet());
        temp = tableGenAll.keySet();
        vkeys = temp.iterator();
        while (vkeys.hasNext()) {
            String temp1 = vkeys.next();
            if (!tableSpamAll.containsKey(temp1)) {
                allOfKeys.add(temp1);
            }
        }
        len = allOfKeys.size();
        minCut = 0.0000009;
        for (String key : allOfKeys) {
            int numSpam = 0;
            int numGen = 0;
            double probSpam = 0;
            double probGen = 0;
            if (tableSpamAll.containsKey(key)) {
                numSpam = tableSpamAll.remove(key);
            }
            if (tableGenAll.containsKey(key)) {
                numGen = tableGenAll.remove(key);
            }
            probSpam = (double) (numSpam + 1) / (numWordInSpam + len);
            probGen = (double) (numGen + 1) / (numWordInGen + len);
            double min = probSpam / (probSpam + probGen);
            if (probSpam > minCut && probGen > minCut) {
                tableProbGen.put(key, probGen);
                tableProbSpam.put(key, probSpam);
                aspectRatio.put(key, min);
            }
        }
        tableSpamAll.clear();
        tableGenAll.clear();
        //findBetaCut(allOfKeys,0.9,dataSetGen,dataSetSpam);
        //findMinCut(dataSetGen,dataSetSpam);
    }

    public void findMinCut(String dataGen, String dataSpam) throws IOException {
        Hashtable<String, Double> tSpam;
        Hashtable<String, Double> tGen;
        Set<String> keys = tableProbSpam.keySet();
        ArrayList<String> nkeys = new ArrayList<String>(keys);
        ArrayList<String> tkeys;
        double stop;
        double stop1;
        minCut += 0.0000001;
        do {
            tkeys = new ArrayList<String>();
            tSpam = new Hashtable<String, Double>();
            tGen = new Hashtable<String, Double>();
            for (String key : nkeys) {
                tkeys.add(0, key);
                double probSpam = tableProbSpam.get(key);
                if (probSpam < minCut) {
                    probSpam = tableProbGen.get(key);
                    if (probSpam < minCut) {
                        tGen.put(key, probSpam);
                        tableProbGen.remove(key);
                        probSpam = tableProbSpam.remove(key);
                        tSpam.put(key, probSpam);
                        tkeys.remove(0);
                    }
                }
            }
            stop = TestProcess(dataGen, true, null);
            stop1 = TestProcess(dataSpam, false, null);
            if (stop1 < stop) {
                stop = stop1;
            }
            minCut += 0.0000001;
            nkeys.clear();
            nkeys = tkeys;
        } while (stop >= 0.9);
        tableProbGen.putAll(tGen);
        tableProbSpam.putAll(tSpam);
        minCut -= 0.0000001;
    }

    public void findBetaCut(ArrayList<String> allOfKeys, double initValue, String dataGen, String dataSpam) throws IOException {
        Hashtable<String, Double> tSpam = new Hashtable<String, Double>(tableProbSpam);
        Hashtable<String, Double> tGen = new Hashtable<String, Double>(tableProbGen);
        Hashtable<String, Double> temp1;
        Hashtable<String, Double> temp2;
        Hashtable<String, Double> temp3;
        ArrayList<String> tempKey;
        tableProbSpam.clear();
        tableProbGen.clear();
        double stop = 0;
        double stop1;
        betaCut = initValue;
        double nBetaCut;
        do {
            nBetaCut = 1 - betaCut;
            tempKey = new ArrayList<String>();
            temp1 = new Hashtable<String, Double>();
            temp2 = new Hashtable<String, Double>();
            temp3 = new Hashtable<String, Double>();
            for (String key : allOfKeys) {
                double min = aspectRatio.remove(key);
                double probSpam = tSpam.remove(key);
                double probGen = tGen.remove(key);
                if (probSpam > minCut || probGen > minCut) {
                    if (min >= betaCut || min <= nBetaCut) {
                        tableProbSpam.put(key, probSpam);
                        tableProbGen.put(key, probGen);
                    } else {
                        tempKey.add(key);
                        temp1.put(key, min);
                        temp2.put(key, probSpam);
                        temp3.put(key, probGen);
                    }
                }
            }
            stop = TestProcess(dataGen, true, null);
            stop1 = TestProcess(dataSpam, false, null);
            if (stop1 < stop) {
                stop = stop1;
                break;
            }
            betaCut -= 0.01;
            allOfKeys.clear();
            aspectRatio = temp1;
            tSpam = temp2;
            tGen = temp3;
            allOfKeys = tempKey;
        } while (stop < 0.9);
        aspectRatio.clear();
    }
    /*
     * Dau vao la mot mang cac tu trong email muon test.
     * Dau ra tra lai true neu doan la email thuong va false neu doan la email rac.
     */

    public boolean TestOneMail(ArrayList<String> tokens) {
        int len;
        int i;
        Set<String> keys = tableProbSpam.keySet();
        String token;
        ArrayList<String> nowMess = new ArrayList<String>();
        double spam = Math.log(0.5);
        double gen = Math.log(0.5);
        tokens = sWord.letterStopWords(tokens, false);
        tokens = Stemmer.StemArrayList(tokens);
        len = tokens.size();
        for (i = 0; i < len; i++) {
            token = tokens.get(i);
            if (!(nowMess.contains(token)) && keys.contains(token)) {
                nowMess.add(token);
                spam += Math.log(tableProbSpam.get(token));
                gen += Math.log(tableProbGen.get(token));
            }
        }
        if (gen >= spam) {
            return true;
        }
        return false;
    }

    public double TestProcess(String nameData, boolean flags, Vector<JTextField> v) throws IOException {
        processMes message = new processMes(nameData);
        int numSpam = 0;
        int numGen = 0;
        int sum = 0;
        double percentCorrect = -1;
        String token;
        ArrayList<String> tokens = message.newMessage();
        ArrayList<String> nowMess = new ArrayList<String>();
        Set<String> keys = tableProbSpam.keySet();
        int len;
        int i;
        while (tokens != null) {
            sum += 1;
            tokens = sWord.letterStopWords(tokens, false);
            tokens = Stemmer.StemArrayList(tokens);
            len = tokens.size();
            nowMess.clear();
            double spam = Math.log(0.5);
            double gen = Math.log(0.5);
            for (i = 0; i < len; i++) {
                token = tokens.get(i);
                if (!(nowMess.contains(token)) && keys.contains(token)) {
                    nowMess.add(token);
                    spam += Math.log(tableProbSpam.get(token));
                    gen += Math.log(tableProbGen.get(token));
                }
            }
            if (gen >= spam) //if(TestOneMail(tokens))
            {
                numGen += 1;
            } else {
                numSpam += 1;
            }
            tokens = message.newMessage();
        }
        if (sum > 0) {
            percentCorrect = (double) numSpam / sum;
            if (flags) {
                percentCorrect = (double) numGen / sum;
            }
            if (v != null) {
                v.get(0).setText(String.valueOf(numGen + numSpam));
                v.get(1).setText(String.valueOf(numGen));
                v.get(2).setText(String.valueOf(numSpam));
                v.get(3).setText(String.valueOf(percentCorrect).substring(0, 13));
            }
            System.out.print("Ti le phan loai tap du lieu " + nameData + "\nHe thong dua ra " + numGen + " email thuong "
                    + numSpam + " email Spam\nTi le phan loai dung la " + percentCorrect + "\n");
        }
        return percentCorrect;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public Hashtable<String, Integer> getTable(HashTable input) {
        Hashtable data[] = input.hash;
        //input.showNuberElements();
        Hashtable<String, Integer> newData = new Hashtable<String, Integer>();
        int len = data.length;
        int i;
        for (i = 0; i < len; i++) {
            newData.putAll(data[i]);
        }
        return newData;
    }

    public void showData(Hashtable<String, Integer> input) {
        Set<String> keys = input.keySet();
        Iterator<String> vkeys = keys.iterator();
        System.out.print("\nDu lieu: ");
        String temp;
        while (vkeys.hasNext()) {
            temp = vkeys.next();
            System.out.print("\n" + temp + ":" + input.get(temp));
        }
        System.out.print("\nSo luong phan tu " + keys.size());
    }

    public void makeDataBase() throws FileNotFoundException {
        FileOutputStream temp = new FileOutputStream("probGen.data", false);
        FileOutputStream temp1 = new FileOutputStream("probSpam.data", false);
        PrintWriter pw = new PrintWriter(temp);
        PrintWriter pw1 = new PrintWriter(temp1);
        ArrayList<String> nkeys = new ArrayList<String>(tableProbGen.keySet());
        for (String key : nkeys) {
            double proSpam = tableProbSpam.remove(key);
            double proGen = tableProbGen.remove(key);
            pw.println(key + ":" + proGen);
            pw1.println(key + ":" + proSpam);
        }
        pw.close();
        pw1.close();
    }

    public void loadDataBase(String fileName, Hashtable<String, Double> temp) throws IOException {
        BufferedReader buf = new BufferedReader(new FileReader("fileName"));
        String line = buf.readLine();
        String key;
        double probability;
        while (line != null) {
            StringTokenizer stk = new StringTokenizer(line, ":");
            key = stk.nextToken();
            probability = Double.parseDouble(stk.nextToken());
            temp.put(key, probability);
        }
        buf.close();
    }

    public static void main(String args[]) throws IOException {
        Traning test = new Traning("GenSpam/train_GEN.ems", "GenSpam/train_SPAM.ems");
        test.TestProcess("GenSpam/test_GEN.ems", true, null);
        test.TestProcess("GenSpam/test_SPAM.ems", false, null);
    }
}
