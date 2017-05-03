package edu.labelgeneration;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.neural.common.RandomWrapper;
import weka.classifiers.neural.common.learning.LearningRateKernel;
import weka.classifiers.neural.common.learning.StaticLearningRate;
import weka.classifiers.neural.lvq.MultipassLvq;
import weka.classifiers.neural.lvq.MultipassSom;
import weka.classifiers.neural.lvq.algorithm.SomAlgorithm;
import weka.classifiers.neural.lvq.model.CommonModel;
import weka.classifiers.neural.lvq.model.SomModel;
import weka.classifiers.neural.lvq.neighborhood.GaussianNeighbourhood;
import weka.classifiers.neural.lvq.neighborhood.NeighbourhoodKernel;
import weka.classifiers.neural.lvq.topology.HexagonalNeighbourhoodDistance;
import weka.classifiers.neural.singlelayerperceptron.Perceptron;
import weka.classifiers.neural.multilayerperceptron.BackPropagation;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class UseClassifier {

	private static Classifier hostNet;
	
	public enum Model{
		MLP,
		LVQ,
		SLP,
		SOM
	};
	
	public static ArrayList<ArrayList<String>> train(String filename, Model m) {
		File trainingDataFile = new File(filename);
		
		if (trainingDataFile == null || !trainingDataFile.exists()) {
			System.out.println("Cannot train on empty file");
			return null;
		}
		
		DataSource source = null;
		try {
			//source = new DataSource(new FileInputStream(trainingDataFile));
			source = new DataSource(filename);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		Instances trainingSet = null;
		try {
			trainingSet = source.getDataSet();
			
			if (trainingSet.classIndex() == -1){
				trainingSet.setClassIndex(trainingSet.numAttributes() - 1);
			}
			//trainingSet.setClassIndex(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//trainingSet.setClass(trainingSet.attribute(0));
		String mdl = "";
		switch(m){
		case MLP:
			mdl = "MLP";
			hostNet = new BackPropagation();
			break;
		case LVQ:
			mdl = "LVQ";
			hostNet = new MultipassLvq();
			break;
		case SLP:
			mdl = "SLP";
			hostNet = new Perceptron();
			break;
		case SOM:
			mdl = "SOM";
			hostNet = new MultipassSom();
			break;
		}
		
		//String [] options = {"-I 100 -L 0.1 -B 1.0 -R 0 -F 1 -N 1 -A 0.2 -D 0.0 -X 0 -Y 0 -Z 0 -M 3"};
		
		/*hostNet = new MultipassSom();
		
		String [] options = {"-A",
				"weka.classifiers.neural.lvq.Som -W 8 -H 6 -M 2 -R 1 -I 1 -N 2 -L 1 -K 8 -P 0.3 -F 9600 -S false -V false",
				"-B",
				"weka.classifiers.neural.lvq.Som -W 8 -H 6 -M 2 -R 1 -I 1 -N 2 -L 1 -K 8 -P 0.05 -F 9600 -S false -V false"};*/

		
		/*hostNet = new MultipassLvq();
		String [] options = {"-A",
				"weka.classifiers.neural.lvq.Olvq1 -M 1 -C 20 -I 10000 -L 1 -R 0.1 -S 1 -G false",
				"-B",
				"weka.classifiers.neural.lvq.Lvq3 -M 1 -C 20 -I 10000 -L 1 -R 0.05 -S 1 -G false -W 0.2 -E 0.05"};*/
		
		/*try {
			hostNet.setOptions(options);
		} catch (Exception e1) {
			e1.printStackTrace();
		}*/
		
		try {
			Evaluation eval = new Evaluation(trainingSet);
			Random rand = new Random(1);
			int folds = 10;
			System.out.println(trainingSet.classAttribute().name());
			eval.crossValidateModel(hostNet, trainingSet, folds, rand);
			
			System.out.println(mdl + " - " + trainingDataFile.getName());
			System.out.println(eval.toSummaryString());
			double [][] cm = eval.confusionMatrix();
			ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();
			
			for(int i=0; i<cm.length; i++){
				ret.add(new ArrayList<String>());
				for(int j=0; j<cm.length; j++){
					ret.get(i).add(String.valueOf(cm[i][j]));
				}
			}

			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ArrayList<ArrayList<String>> getInstanceDistributions(String filename) {
		File trainingDataFile = new File(filename);
		
		if (trainingDataFile == null || !trainingDataFile.exists()) {
			System.out.println("Cannot train on empty file");
			return null;
		}
		
		DataSource source = null;
		try {
			//source = new DataSource(new FileInputStream(trainingDataFile));
			source = new DataSource(filename);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		Instances trainingSet = null;
		try {
			trainingSet = source.getDataSet();
			
			
			if (trainingSet.classIndex() == -1){
				trainingSet.setClassIndex(trainingSet.numAttributes() - 1);
			}
			//trainingSet.setClassIndex(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//SomAlgorithm asom = new SomAlgorithm();
		
		//SomAlgorithm som = new SomAlgorithm(new StaticLearningRate(0.3,960), new GaussianNeighbourhood(8.0,960), new SomModel(new HexagonalNeighbourhoodDistance(),6,8), new RandomWrapper(),  false);

		//som.trainModel(trainingSet, 960);
		
		
		//trainingSet.setClass(trainingSet.attribute(0));
		hostNet = new MultipassSom();
		
		
		try {
			hostNet.buildClassifier(trainingSet);
			

			Evaluation eval = new Evaluation(trainingSet);
			Random rand = new Random(1);
			int folds = 10;
			System.out.println(trainingSet.classAttribute().name());
			eval.crossValidateModel(hostNet, trainingSet, folds, rand);
						
			HashMap<Double,double []> instanceDist = new HashMap<Double,double []>();
			
			/*BufferedWriter out = new BufferedWriter(new FileWriter(trainingDataFile.getName() + ".results"));
			
			for(int i = 0; i < cm.length; i++){
				for(int j = 0; j < cm[i].length; j++){
					out.write(cm[i][j] + ",");
				}
				out.newLine();
			}
			out.close();
			
			return cm;*/
			double [] d;
			double d2;
			
			ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();
			
			for(int i=0; i<trainingSet.numInstances(); i++){
				d = hostNet.distributionForInstance(trainingSet.instance(i));
				d2 = trainingSet.instance(i).classValue();
				instanceDist.put(d2, d);
				
				ret.add(new ArrayList<String>());
				
				for(int j=0; j<trainingSet.numClasses(); j++){
					ret.get(i).add(String.valueOf(d[j]));
					if((int)d2 == j){
						ret.get(i).add("1");
					}
					else{
						ret.get(i).add("0");
					}
				}
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
