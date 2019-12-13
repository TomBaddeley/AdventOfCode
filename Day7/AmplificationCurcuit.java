import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class AmplificationCurcuit {
    static  HashSet<String> perms = new HashSet<>();


    public static int[] readProgram() throws Error {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Day7\\IntCode.txt"));
            return Arrays.stream(br.readLine().split(",")).mapToInt(Integer::parseInt).toArray();
        } catch (Exception var1) {
            return null;
        }
    }


    public static void main(String[] args) {
        int[] code = readProgram();
        if(code == null) return;

        getPermutations("56789","");
        String bestPerm = "";
        double maxOut = 0;

        for(String s: perms){
            int out = 0;//originally we feed AMP A 0. Variable stores outputs of amps that get sent as input to the next amp

            List<Amp> amps = new ArrayList<>();
            for (int i = 0; i < s.length(); i++) {
                Amp amp = new Amp(code.clone(), Character.getNumericValue(s.charAt(i)));
                amps.add(amp);
            }
            while(out != -1) {
                for(Amp a:amps) {
                    out = a.getOutput(out);
                    if (out > maxOut) {
                        maxOut = out;
                        bestPerm = s;
                    }
                }
            }
        }

        System.out.println(maxOut);
        System.out.println(bestPerm);
    }

    private static void getPermutations(String values, String soFar) {
        if (values.equals("")) {
            perms.add(soFar);
            return;
        }
        for (int i =0; i < values.length();i++){
            getPermutations(values.substring(0,i)+values.substring(i+1),soFar + values.charAt(i));
        }

    }


}

