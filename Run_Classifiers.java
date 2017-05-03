package edu.classifier;

import edu.labelgeneration.CSVHandler;
import edu.labelgeneration.UseClassifier;
import edu.labelgeneration.UseClassifier.Model;

public class Run_Classifiers {

	public static void main(String[] args) {

		CSVHandler.writeCSV("mlp_iris.csv", UseClassifier.train("iris.csv", Model.MLP));
		CSVHandler.writeCSV("mlp_segment_challenge.csv", UseClassifier.train("segment_challenge.csv", Model.MLP));
		
		for(int i=10; i<100; i+=10){
			CSVHandler.writeCSV("mlp_iris_distorted" + i + ".csv", UseClassifier.train("iris_distorted" + i + ".csv", Model.MLP));
			CSVHandler.writeCSV("mlp_iris_final" + i + ".csv", UseClassifier.train("iris_final" + i + ".csv", Model.MLP));
			
			CSVHandler.writeCSV("mlp_segment_challenge_distorted" + i + ".csv", UseClassifier.train("segment_challenge_distorted" + i + ".csv", Model.MLP));
			CSVHandler.writeCSV("mlp_segment_challenge_final" + i + ".csv", UseClassifier.train("segment_challenge_final" + i + ".csv", Model.MLP));
		}
		
	}

}
