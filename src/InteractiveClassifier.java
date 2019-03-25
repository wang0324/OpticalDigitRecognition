import processing.core.PApplet;

import java.util.List;

public class InteractiveClassifier extends PApplet {
    private static final int DISPLAY_WIDTH = 600;
    private static final int DISPLAY_HEIGHT = 600;
    private static final int IMAGE_WIDTH = 28;
    private static final int IMAGE_HEIGHT = 28;

    private short[][] pixels = new short[IMAGE_HEIGHT][IMAGE_WIDTH];
    private float dx, dy;
    private Classifier classifier;
    private String prediction = "";

    public void setup() {
        size(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        dx = (float) DISPLAY_WIDTH / IMAGE_WIDTH;
        dy = (float) DISPLAY_HEIGHT / IMAGE_HEIGHT;
        fillWithColor(pixels, (short) 255);

        classifier = new Classifier(10);
        List<DataPoint> training = DataLoader.createDataSet("mnist_train.csv");
        List<DataPoint> test = DataLoader.createDataSet("mnist_test.csv");
        classifier.addTrainingData(training);
        classifier.addTrainingData(test);
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

        if (mousePressed) {
            addPixels(2);
        }

        fill(255,0,0);
        stroke(0);
        text("Classifier predicts: " + prediction, 30, DISPLAY_HEIGHT - 30);
    }

    public void mouseReleased() {
        prediction = classifier.classify(pixels);
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

    public void addPixels(int n) {
        int c = (int) map(mouseX, 0, DISPLAY_WIDTH, 0, IMAGE_WIDTH);
        int r = (int) map(mouseY, 0, DISPLAY_HEIGHT, 0, IMAGE_HEIGHT);

        for (int tr = r - n; tr <= r + n; tr++) {
            for (int tc = c - n ; tc <= c + n; tc++) {
                if (inBounds(tr, tc, pixels)) {
                    pixels[tr][tc] = 0;
                }
            }
        }

    }

    private boolean inBounds(int r, int c, short[][] pixels) {
        return (0 <= r && r < pixels.length) && (0 <= c && c < pixels[0].length);
    }

    public void keyReleased() {
        if (key == 'c' || key == 'C') {
            clearPixels(pixels);
        }
    }

    private void clearPixels(short[][] pixels) {
        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[r].length; c++) {
                pixels[r][c] = 255;
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("InteractiveClassifier");
    }
}