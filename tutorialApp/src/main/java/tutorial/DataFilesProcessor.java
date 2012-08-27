package tutorial;

import troiaClient.Label;
import troiaClient.GoldLabel;
import java.util.Collection;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * This part of code generates data from 
 * test files.
 *
 * @author piot.gnys@10clouds.com
 */
public class DataFilesProcessor {
    
    public Collection<Label> parseLabels(String filename) throws IOException{
	File dataFile = new File(filename);
	Scanner scanner = new Scanner(dataFile);
	String line;
	String[] inputData;
	Collection<Label> labels = new ArrayList<Label>();
	while(scanner.hasNextLine()){
	    line = scanner.nextLine();
	    inputData = line.split("\t");
	    if(inputData.length!=3){
		throw new IOException("Incorrect file format \""+line+"\"");
	    }else{
		labels.add(new Label(inputData[0],inputData[1],inputData[3]));
	    }
	}
	    return labels;
    }


    public Collection<GoldLabel> parseGoldLabels(String filename) throws IOException{
	File dataFile = new File(filename);
	Scanner scanner = new Scanner(dataFile);
	String line;
	String[] inputData;
	Collection<GoldLabel> labels = new ArrayList<GoldLabel>();
	while(scanner.hasNextLine()){
	    line = scanner.nextLine();
	    inputData = line.split("\t");
	    if(inputData.length!=2){
		throw new IOException("Incorrect file format \""+line+"\"");
	    }else{
		labels.add(new GoldLabel(inputData[0],inputData[1]));
	    }
	}
	    return labels;
    }


}
