import java.lang.reflect.Array;
import java.util.*;

public class Classifier {
    private ArrayList<DataPoint> trainingData;
    private int k;

    public Classifier(int n) {
        this.k = n;
        trainingData = new ArrayList<DataPoint>();
    }

    public void addTrainingData(List<DataPoint> points) {
        // TODO: add all points from input to the training data
        for (DataPoint p : points) {
            trainingData.add(p);
        }
    }

    public void addTrainingData(DataPoint point) {
        // TODO: add all points from input to the training data
        trainingData.add(point);
    }

    public void addTrainingData(String label, DImage img) {
        addTrainingData(new DataPoint(label, img));
    }

    public String classify(short[][] pixels) {
        if (trainingData.size() == 0) return "no training data";

        // TODO: write a k-nearest-neighbor classifier.  Return its prediction of "0" to "9"

        ArrayList <DataPoint> nearestPoints = new ArrayList<>();

        String prediction = "";
        double best = Double.MAX_VALUE;
        for (DataPoint point : trainingData) {
            DImage img = point.getData();

            double dist = distance(pixels, img.getBWPixelGrid());

            if (dist < best) {
                best = dist;
                prediction = point.getLabel();
            }
        }
        return mostCommonLabel(nearestPoints);
    }

    private String mostCommonLabel(ArrayList<DataPoint> nearestPoints) {
        //TODO: Need to implement
        return "";
    }

    public String classify(DImage img) {
        return classify(img.getBWPixelGrid());
    }

    public double distance(short[][] d1, short[][] d2) {
        // TODO:  Use the n-dimensional Euclidean distance formula to find the distance between d1 and d2
        double dist = 0;

        for (int i = 0; i < d1.length; i++) {
            for (int j = 0; j < d1[i].length; j++) {
                dist += Math.pow(d1[i][j] - d2[i][j], 2);
            }
        }

        return Math.sqrt(dist);
    }

    public void test(List<DataPoint> test) {
        ArrayList<DataPoint> correct = new ArrayList<>();
        ArrayList<DataPoint> wrong = new ArrayList<>();

        int i = 0;
        for (DataPoint p : test) {
            String predict = classify(p.getData());
            System.out.print("#" + i + " REAL:\t" + p.getLabel() + " predicted:\t" + predict);
            if (predict.equals(p.getLabel())) {
                correct.add(p);
                System.out.print(" Correct ");
            } else {
                wrong.add(p);
                System.out.print(" WRONG ");
            }

            i++;
            System.out.println(" % correct: " + ((double) correct.size() / i));
        }

        System.out.println(correct.size() + " correct out of " + test.size());
        System.out.println(wrong.size() + " incorrect out of " + test.size());
        System.out.println("% Error: " + (double) wrong.size() / test.size());
    }
}

