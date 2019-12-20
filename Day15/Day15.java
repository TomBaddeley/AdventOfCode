import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Stream;

public class Day15 {

    static int[][] map = new int[50][50];

    public static double[] readProgram() throws Error {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Day15\\Input.txt"));
            return Arrays.stream(br.readLine().split(",")).mapToDouble(Double::parseDouble).toArray();



        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static int bfs(double[] code,int[] instructions){
        Amp2 robot = new Amp2(code);
        int i = 0;
        int output = -1;

        int x = 25;
        int y = 25;

        while(instructions[i] != 0){
            if(instructions[i] == 1) y -= 1;
            if(instructions[i] == 2) y += 1;
            if(instructions[i] == 3) x -= 1;
            if(instructions[i] == 4) x += 1;
            robot.giveInput(instructions[i]);
            output = (int)robot.getOutput();

            map[x][y] = output;
            i++;
        }
        return output;
    }

    public static int[] addDir(int dir,int[] instructions){
        if (instructions[0] == 0){
            int[] res = instructions.clone();
            res[0] = dir;
            return res;
        }

        int x = 50;
        int y = 50;
        HashSet<String> visited = new HashSet<>();
        int count = 0;
        for(int i:instructions){
            visited.add(x+"-"+y);
            if(i == 1) y -= 1;
            if(i == 2) y += 1;
            if(i == 3) x -= 1;
            if(i == 4) x += 1;
            if(i == 0) break;
            count++;
        }
        if(dir == 1) y -= 1;
        if(dir == 2) y += 1;
        if(dir == 3) x -= 1;
        if(dir == 4) x += 1;
        if(visited.contains(x+"-"+y)) return null; // dont revisit tiles

        int[] res = instructions.clone();
        res[count] = dir;
        return res;

    }


    public static void main(String[] args){
        Queue<int[]> paths = new ArrayDeque<>();
        double[] code =readProgram();

        int[] instruct = new int[1000];
        paths.add(addDir(1,instruct));
        paths.add(addDir(2,instruct.clone()));
        paths.add(addDir(3,instruct.clone()));
        paths.add(addDir(4,instruct.clone()));

        int result = -1;
        while(result != 3){
            instruct = paths.poll();
            if(instruct== null) break;
            result = bfs(code.clone(),instruct);
            //not a wall
            if(result == 1){
                for(int i =1; i < 5;i++) {
                    int[] res = addDir(i, instruct.clone());
                    if (res != null) paths.add(res);
                }
            }

        }

        int count = 0;
        int EmptyLeft = (int)Arrays.stream(map).flatMapToInt(Arrays::stream).filter(x -> x == 1).count();
        while(EmptyLeft > 0){
            for(int i = 0; i < map.length; i ++){
                for(int j = 0; j < map[0].length; j++){
                    if(map[i][j] == 2){
                        if(map[i+1][j] == 1){
                            map[i+1][j] = 3; //3 is a place holder to avoid this change effecting other tiles
                            EmptyLeft--;
                        }
                        if(map[i-1][j] == 1){
                            map[i-1][j] = 3;
                            EmptyLeft--;
                        }
                        if(map[i][j+1] == 1){
                            map[i][j+1] = 3;
                            EmptyLeft--;
                        }
                        if(map[i][j-1] == 1){
                            map[i][j-1] = 3;
                            EmptyLeft--;
                        }
                    }
                }
            }
            for(int i = 0; i < map.length; i ++) {
                for (int j = 0; j < map[0].length; j++) {
                    if (map[i][j] == 3) map[i][j] = 2;
                }
            }
            System.out.println(count++);

        }
        System.out.println(count);

    }
}
