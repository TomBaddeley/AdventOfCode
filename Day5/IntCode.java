import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class IntCode {

    public static int[] readProgram() throws Error {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Day5\\IntCode.txt"));
            return Arrays.stream(br.readLine().split(",")).mapToInt(Integer::parseInt).toArray();
        } catch (Exception var1) {
            return null;
        }
    }

    public static int[] fixProgram(int[] code, int input) {


        for(int i = 0; i < code.length && code[i] != 99;) {
            String pCode = (code[i] +"");
            while(pCode.length() < 5){
                pCode = "0" + pCode;
            }

            String opCode = pCode.substring(pCode.length()-2);
            String par1 = pCode.substring(2,3);
            String par2 = pCode.substring(1,2);
            String par3 = pCode.substring(0,1);


            if (opCode.equals("01")) {
                int val1 = par1.equals("0") ? code[code[i + 1]]:code[i + 1];
                int val2 = par2.equals("0") ? code[code[i + 2]]:code[i + 2];

                code[code[i + 3]] = val1+val2;
                i = i+4;
            } else if (opCode.equals("02")) {
                int val1 = par1.equals("0") ? code[code[i + 1]]:code[i + 1];
                int val2 = par2.equals("0") ? code[code[i + 2]]:code[i + 2];

                code[code[i + 3]] = val1 * val2;
                i = i+4;
            } else if (opCode.equals("03")) {
                code[code[i+1]] = input;
                i = i + 2;
            } else if (opCode.equals("04")) {
                int val1 = par1.equals("0") ? code[code[i + 1]]:code[i + 1];
                System.out.println(val1);
                i = i + 2;
            } else if (opCode.equals("05")){
                int val1 = par1.equals("0") ? code[code[i + 1]]:code[i + 1];

                if (val1 != 0){
                    i = par2.equals("0") ? code[code[i + 2]]:code[i + 2];
                } else {
                    i = i + 3;
                }
            } else if (opCode.equals("06")){
                int val1 = par1.equals("0") ? code[code[i + 1]]:code[i + 1];

                if (val1 == 0){
                    i = par2.equals("0") ? code[code[i + 2]]:code[i + 2];
                } else {
                    i = i + 3;
                }
            } else if (opCode.equals("07")){
                int val1 = par1.equals("0") ? code[code[i + 1]]:code[i + 1];
                int val2 = par2.equals("0") ? code[code[i + 2]]:code[i + 2];

                if(val1 < val2) code[code[i + 3]] = 1;
                else code[code[i + 3]] = 0;

                i = i +4;
            }else if (opCode.equals("08")){
                int val1 = par1.equals("0") ? code[code[i + 1]]:code[i + 1];
                int val2 = par2.equals("0") ? code[code[i + 2]]:code[i + 2];

                if(val1 == val2) code[code[i + 3]] = 1;
                else code[code[i + 3]] = 0;

                i = i +4;
            }
        }

        return code;
    }

    public static void main(String[] args) {
        int[] code = readProgram();
        if(code == null) return;
        fixProgram(code,5);
    }
}
