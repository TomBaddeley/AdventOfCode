import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class day13 {


    public static double[] readProgram() throws Error {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Day13\\Input.txt"));
            return Arrays.stream(br.readLine().split(",")).mapToDouble(Double::parseDouble).toArray();



        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args){
        double[] code = readProgram();
        Amp2 a = new Amp2(code,-1);
        double output1= 0;
        double output2= 0;
        double output3= 0;
        int score = 0;

        int ballx = 0;
        int padx = 0;

        int[][] map = new int[50][50];

        int input = 0;


        double[] code2 = readProgram();
        code2[0] = 1;
        a = new Amp2(code2,0);
        output1 = 0;
        while(true){

            while(a.isInputNeeded()){
                input = Integer.compare(ballx, padx);
                a.giveInput(input);
                output1  = a.getOutput();
            }
            output2  = a.getOutput();
            output3  = a.getOutput();
            if(a.isTerminated()) break;
            if(output1 != -1 && output2 != -1){
                map[(int)output2][(int)output1] = (int)output3;
            }
            if(output1 == -1 && output2 == 0) score = (int)output3;
            if(output3 == 4) ballx = (int)output3;
            if(output3 == 3) padx = (int)output3;
        }


        System.out.println(score);
        for(int i = 0; i <map.length;i++){
            for(int j =0;j < map[0].length; j++){
                if(map[i][j] == 2 ) System.out.print("*");
                else if(map[i][j] == 1 || map[i][j] == 3) System.out.print("#");
                else if(map[i][j] == 4) System.out.print("o");
                else System.out.print(" ");
            }
            System.out.println();
        }

    }
}
