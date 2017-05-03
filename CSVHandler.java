package edu.labelgeneration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVHandler {

	public static ArrayList<ArrayList<String>> readCSV(String filename){
		
		ArrayList<ArrayList<String>> contents = new ArrayList<ArrayList<String>>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String s;
			ArrayList<String> tmp;
			
			while((s = in.readLine()) != null) {
				tmp = new ArrayList<String>(Arrays.asList(s.split(",")));
				
				contents.add(tmp);
			}
			
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return contents;
	}
	
	public static void writeCSV(String filename, ArrayList<ArrayList<String>> contents) {
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filename));
			
			for(ArrayList<String> lst : contents){
				out.write(array2CSVLine(lst));
				out.newLine();
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeCSV(String filename, ArrayList<String> header, ArrayList<ArrayList<String>> contents) {
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filename));
			
			out.write(array2CSVLine(header));
			out.newLine();
			
			for(ArrayList<String> lst : contents){
				out.write(array2CSVLine(lst));
				out.newLine();
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String array2CSVLine(ArrayList<String> line){
		String s = line.get(0);
		
		for(int i=1; i<line.size(); i++){
			s = s + "," + line.get(i);
		}
		
		return s;
	}
}
