import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day16 {

    static int patternLength =0;


    public static void main(String[] args){
        List<Integer> input = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("Day16\\Input.txt"));
            String inputString = br.readLine();
            patternLength = inputString.length();
            for(int j =0; j < 10000; j++) {
                for (int i = 0; i < inputString.length(); i++) {
                    input.add(Integer.parseInt(inputString.charAt(i) + ""));
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        List<Integer> basePattern = new ArrayList<>();
        basePattern.add(0);
        basePattern.add(1);
        basePattern.add(0);
        basePattern.add(-1);
        int index = Integer.parseInt(input.get(0)+""+input.get(1)+""+input.get(2)+""+input.get(3)+""+input.get(4)+""+input.get(5)+""+input.get(6)+"");
        List<Integer> res = applyFTF(input,100,basePattern);

        System.out.println(res.get(index));
    }

    private static List<Integer> applyFTF(List<Integer> input, int times,List<Integer> basePattern) {

        for(int i =0; i < times;i++){
            List<Integer> signal = new ArrayList<>();
            for(int j =0; j < input.size();j++){
                int sum = 0;
                int prevSum = 0;
                int[] dx = new int[j+1];
                for(int k =j; k < Math.min((j +1) * patternLength,input.size()); k++){


                    if((k+1)%patternLength  ==0) {
                        dx[(k+1)/patternLength-1] = sum- prevSum;
                        prevSum = sum;
                    }
                }
                if((j +1) * patternLength < input.size()) {
                    sum = 0;
                    int tot = Arrays.stream(dx).sum();
                    int extra = 0;
                    int rem = 10000 % dx.length;
                    for (int k = 0; k < rem; k++) {
                        extra += dx[k];
                    }
                    sum += 10000 / dx.length * tot + extra;
                }
                signal.add(Math.abs(sum)%10);
            }
            input = signal;
            System.out.println("==================================");
        }

        return input;
    }
}
