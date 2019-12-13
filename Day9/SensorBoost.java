import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class SensorBoost {

    public static double[] readProgram() throws Error {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Day9\\IntCode.txt"));
            return Arrays.stream(br.readLine().split(",")).mapToDouble(Double::parseDouble).toArray();
        } catch (Exception var1) {
            return null;
        }
    }

    public static void main(String[] args){
        double[] code = readProgram();
        Amp2 a = new Amp2(code,2);
        System.out.println(a.getOutput());
    }
}
