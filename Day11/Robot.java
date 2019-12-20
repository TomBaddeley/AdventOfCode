import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Robot {

        public static double[] readProgram() throws Error {
            try {
                BufferedReader br = new BufferedReader(new FileReader("Day11\\Input.txt"));
                return Arrays.stream(br.readLine().split(",")).mapToDouble(Double::parseDouble).toArray();

            } catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }





        public static void main(String[] args){
            double[] code = readProgram();
            Amp2 robot = new Amp2(code);
            double output1 = robot.getOutput();
            double output2 = robot.getOutput();

            int[][] map = new int[100][100];
            int robX = 50;
            int robY = 50;
            map[50][50] = 1;
            int facing = 0;  //0 north 1 east 2 south 3 west

            Set<String> changed = new HashSet<>();
            while (output1 != -1){
                int col = map[robY][robX] ;
                if(col != output1){
                    map[robY][robX] = (int) output1;
                    changed.add(robX + "-" + robY);
                }
                if(output2 == 0){
                    if(facing == 0){
                        robX = robX -1;
                        facing = 3;
                    }
                    else if(facing == 1){
                        robY = robY -1;
                        facing = 0;
                    }
                    else if(facing == 2){
                        robX = robX + 1;
                        facing = 1;
                    }
                    else {
                        robY = robY + 1;
                        facing = 2;
                    }
                } else if(output2 == 1){
                    if(facing == 0){
                        robX = robX + 1;
                        facing = 1;
                    }
                    else if(facing == 1){
                        robY = robY + 1;
                        facing = 2;
                    }
                    else if(facing == 2){
                        robX = robX - 1;
                        facing = 3;
                    }
                    else {
                        robY = robY - 1;
                        facing = 0;
                    }
                }

                output1 = robot.getOutput( );
                output2 = robot.getOutput( );
            }
            for(int i = 0; i <map.length ; i++){
                for(int j = 0; j < map[0].length; j++){
                    System.out.print(map[i][j] == 0 ? " ":"X");
                }
                System.out.println();
            }
            System.out.println(changed.size());
        }
    }

