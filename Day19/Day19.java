import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class Day19 {
    public static double[] readProgram() throws Error {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Day19\\Input.txt"));
            return Arrays.stream(br.readLine().split(",")).mapToDouble(Double::parseDouble).toArray();



        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args){
        double[]code = readProgram();
        int[][] map = new int[1600][1600];
        int sum = 0;
        try {

            for(int i = 1200; i < 1600; i++){
                for(int j =i/2;j < Math.min(i*2+1,1600);j++){
                    Amp2 robot = new Amp2(code.clone());
                    robot.giveInput(i);
                    robot.giveInput(j);
                    if(robot.getOutput()== 1) map[i][j] = 1;
                }
            }
            System.out.println("here");
            int startX = 0;
            int startY = 0;

            for(int i = 0;i < map.length;i++){
                int count = (int)Arrays.stream(map[i]).filter(x -> x == 1).count();
                System.out.println(count);
                if(count >= 100) {
                    startY = i;
                    for (int j = 0; j < map[0].length; j++) {
                        if(map[i][j] == 1){
                            startX = j;
                            break;
                        }
                    }
                    break;
                }
            }
            for(int i = startY;i < map.length-99; i++){
                for( int j = startX;j < map[0].length;j++){
                    if(map[i][j] == 1){
                        if(map[i][j+99] == 0 )break;
                        else if(map[i+99][j] == 1){
                            System.out.println(i+"-"+j);
                            return;
                        }
                    }
                }
            }



        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
