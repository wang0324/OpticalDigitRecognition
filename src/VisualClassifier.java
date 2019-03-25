import processing.core.PApplet;

import java.util.List;

import static jdk.nashorn.internal.objects.Global.load;

public class VisualClassifier extends PApplet {
    private static final int DISPLAY_WIDTH = 600;
    private static final int DISPLAY_HEIGHT = 600;
    private static final int IMAGE_WIDTH = 28;
    private static final int IMAGE_HEIGHT = 28;

    private short[][] pixels = new short[IMAGE_HEIGHT][IMAGE_WIDTH];
    private float dx, dy;
    private Classifier classifier;
    private String prediction = "";
    private List<DataPoint> test;

    public void setup() {
        size(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        dx = (float) DISPLAY_WIDTH / IMAGE_WIDTH;
        dy = (float) DISPLAY_HEIGHT / IMAGE_HEIGHT;
        fillWithColor(pixels, (short) 255);

        classifier = new Classifier(10);
        List<DataPoint> training = DataLoader.createDataSet("mnist_train.csv");
        test = DataLoader.createDataSet("mnist_test.csv");
        classifier.addTrainingData(training);

        DataPoint frame = test.remove((int)Math.random()*test.size());
        load(pixels, frame);
    }

    private void load(short[][] pixels, DataPoint frame) {
        short[][] toLoad = frame.getData().getBWPixelGrid();

        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[0].length; c++) {
                pixels[r][c] = toLoad[r][c];
            }
        }

        prediction = classifier.classify(pixels);
    }

    private void fillWithColor(short[][] pixels, short val) {
        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[r].length; c++) {
                pixels[r][c] = val;
            }
        }
    }

    public void draw() {
        drawImage(pixels);

        fill(255,0,0);
        stroke(0);
        text("Classifier predicts: " + prediction, 30, DISPLAY_HEIGHT - 30);
    }

    private void drawImage(short[][] pixels) {
        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[r].length; c++) {
                float y = map(r, 0, IMAGE_HEIGHT, 0, DISPLAY_HEIGHT);
                float x = map(c, 0, IMAGE_WIDTH, 0, DISPLAY_WIDTH);

                fill(pixels[r][c]);
                rect(x, y, dx, dy);
            }
        }
    }

    public void addPixels() {
        int c = (int) map(mouseX, 0, DISPLAY_WIDTH, 0, IMAGE_WIDTH);
        int r = (int) map(mouseY, 0, DISPLAY_HEIGHT, 0, IMAGE_HEIGHT);

        if (inBounds(r, c, pixels)) {
            pixels[r][c] = 0;
        }
    }

    private boolean inBounds(int r, int c, short[][] pixels) {
        return (0 <= r && r < pixels.length) && (0 <= c && c < pixels[0].length);
    }

   public void mouseReleased() {
       DataPoint frame = test.remove((int)Math.random()*test.size());
       load(pixels, frame);
   }

    private void clearPixels(short[][] pixels) {
        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[r].length; c++) {
                pixels[r][c] = 255;
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("VisualClassifier");
    }
}