import processing.core.PApplet;

import java.util.List;

public class TestClassifier {
    public static void main(String[] args) {
        Classifier classifier;
        String prediction = "";

        classifier = new Classifier(5);
        List<DataPoint> training = DataLoader.createDataSet("mnist_train.csv");
        List<DataPoint> test = DataLoader.createDataSet("mnist_test.csv");
        classifier.addTrainingData(training);

        classifier.test(test);
    }
}