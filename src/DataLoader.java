import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    private static String normalizeLineBreaks(String s) {
        return s.replace("\r\n", "\n").replace('\r', '\n');
    }

    public static String readFileAsString(String filepath) {
        ClassLoader classLoader = DataLoader.class.getClassLoader();
        File file = new File(classLoader.getResource(filepath).getFile());

        // Read File Content
        String content = "";
        try {
            content = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            System.err.println("FILE NOT FOUND: " + filepath);
            e.printStackTrace();
        }

        return content;
    }

    public static List<DataPoint> createDataSet(String filepath) {
        String data = normalizeLineBreaks(readFileAsString(filepath));
        String[] lines = data.split("\n");

        // create storage for data
        ArrayList<DataPoint> dataset = new ArrayList<>();

        for (int a = 0; a < lines.length; a++) {
            String[] arr = lines[a].split(",");

            short[][] pixels = new short[28][28];

            for (int i = 0; i < 28; i++) {
                for (int j = 0; j < 28; j++) {
                    pixels[i][j] = (short)(Integer.parseInt(arr[i*28+j+1]));

                    if (pixels[i][j] == 0) {
                        pixels[i][j] = 255;
                    }
                    else {
                        pixels[i][j] = 0;
                    }
                }
            }

            DImage img = new DImage(28, 28);
            img.setPixels(pixels);

            DataPoint point = new DataPoint(arr[0], img);
            dataset.add(point);
        }

             /* TODO:
                For each line in the lines array:
                    split line by , to get all coordinates

                    get the correct label for this data point (the first number in the array).

                    create a 28 x 28 2d array of short

                    fill the 2d array.  First 28 shorts starting at index 1 are row 0, next 28 shorts are row 1, etc.

                    Run the DImage constructor to create a new DImage that's 28 by 28
                    Run the .setPixels method to set its pixels.

                    Run the DataPoint constructor, giving it the correct label and the DImage

                    Add the DataPoint to your dataset list.
         */


        return dataset;
    }
}