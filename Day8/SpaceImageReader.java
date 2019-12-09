import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

public class SpaceImageReader {
    private static int[][][] readProgram(int width, int height)  {
        try {
            BufferedReader br = new BufferedReader(new FileReader("day8\\ImageData.txt"));
            String line = br.readLine();
            int layers = line.length() / (width * height);
            int stringPointer = 0;
            int[][][] image = new int[layers][height][width];
            for (int i = 0; i < layers; i++) {
                for (int j = 0; j < height; j++) {
                    for (int k = 0; k < width; k++) {
                        image[i][j][k] = Character.getNumericValue(line.charAt(stringPointer++));
                    }
                }
            }

            return image;
        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }



    public static void main(String[] args){
        int[][][] image = readProgram(25,6);
        int minZero= 1000;
        int index = 0;
        if (image == null) return;

        for(int i = 0; i < image.length; i++){
            //counts number of zeros in the layer
            int zeroCount = (int)Arrays.stream(image[i]).flatMapToInt(Arrays::stream).filter(x -> x == 0).count();

            if (zeroCount < minZero){
                minZero = zeroCount;
                index = i;
            }
        }

        int twoCount = (int)Arrays.stream(image[index]).flatMapToInt(Arrays::stream).filter(x -> x == 2).count();
        int oneCount = (int)Arrays.stream(image[index]).flatMapToInt(Arrays::stream).filter(x -> x == 1).count();

        System.out.println(oneCount * twoCount);

        int[][] output = new int[6][25];

        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                for (int k = 0; k < image[0][0].length; k++) {
                    if(i == 0){//array uninitialized on first traversal of outer most loop
                        output[j][k] = image[i][j][k];
                    } else {
                        if(output[j][k] == 2){
                            output[j][k] = image[i][j][k];
                        }
                    }

                }
            }
        }

        for(int i = 0;i < output.length ; i++){
            for(int j = 0;j < output[0].length; j++){
                System.out.print(output[i][j] == 1 ? "x":" ");
            }
            System.out.println();
        }


    }


}
