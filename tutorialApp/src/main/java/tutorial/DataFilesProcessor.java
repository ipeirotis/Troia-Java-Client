package tutorial;

import troiaClient.Label;
import troiaClient.GoldLabel;
import java.util.Collection;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Class used for processing data files.
 */
public class DataFilesProcessor {


    /**
     * This function generates labels from file.
     * File must be formatted as follows <worker><tab><object><tab><label>
     * @param filename Name of file containing worker assigned labels.
     * @return Collection of labels extracted from files.
     */    
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
		labels.add(new Label(inputData[0],inputData[1],inputData[2]));
	    }
	}
	System.out.println(labels);
	    return labels;
    }


}
