import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day18 {

    private static char[][] map;
    private static int curX;
    private static int curY;










    public static void main(String[] args){


        try {
            BufferedReader br = new BufferedReader(new FileReader("Day18\\Input.txt"));


            String inputString = br.readLine();
            int row = 0;
            map = new char[9][inputString.length()];
            while(inputString != null) {
                for (int i = 0; i < inputString.length(); i++) {
                    map[row][i] = inputString.charAt(i);
                    if(inputString.charAt(i) == '@'){
                        curX = i;
                        curY = row;
                        map[row][i] = '.';
                    }
                }
                inputString = br.readLine();
                row++;
            }
            int moves = collectKey();

            System.out.println(moves);


        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private static int collectKey() {
        char[][] tempMap = deepCopyIntMatrix(map);
        int tempX = curX;
        int tempY =curY;

        Set<String> found = findKeys(tempX,tempY,new HashSet<>(),new HashSet<>());
        int best = 1000000000;
        for(String s:found){
            map = deepCopyIntMatrix(tempMap);
            curX = tempX;
            curY = tempY;

            int x = testSol(s);
            if (x < best) best =x;
        }
        return best;

    }

    private static int testSol(String target) {

        int steps = 0;
        while(mapHasKeys()){

            steps += bfs(curX, curY,new HashSet<>(),target);
            Set<String> found  = findKeys(curX, curY,new HashSet<>(),new HashSet<>());

            int min = 10000000;

            char[][] tempMap = deepCopyIntMatrix(map);
            int tempX = curX;
            int tempY =curY;
            int x;
            for(String s:found){
                map = deepCopyIntMatrix(tempMap);
                curX = tempX;
                curY = tempY;

                x = testSol(s);

                if(x < min) min = x;
            }
            steps += found.size() == 0 ? 0:min;
        }
        return steps;

    }

    private static Set<String> findKeys(int tempX, int tempY,HashSet<String> visited,HashSet<String> found){
        visited.add(tempX + "-" + tempY);

        if(!visited.contains((tempX + 1)+"-" + tempY)){
            if((map[tempY][tempX+1] + "").matches("[a-z]")) {
                found.add(map[tempY][tempX+1]+"");
            }
            if(map[tempY][tempX+1] == '.'){
                found.addAll(findKeys(tempX+1, tempY,visited,found));
            }
        }
        if(!visited.contains((tempX - 1)+"-" + tempY)){
            if((map[tempY][tempX-1] + "").matches("[a-z]")) {
                found.add(map[tempY][tempX-1]+"");
            }
            if(map[tempY][tempX-1] == '.'){
                found.addAll(findKeys(tempX-1, tempY,visited,found));
            }
        }
        if(!visited.contains(tempX+"-" + (tempY+1))){
            if((map[tempY+1][tempX] + "").matches("[a-z]")) {
                found.add(map[tempY+1][tempX]+"");
            }
            if(map[tempY+1][tempX] == '.'){
                found.addAll(findKeys(tempX, tempY+1,visited,found));
            }
        }
        if(!visited.contains(tempX+"-" + (tempY-1))){
            if((map[tempY-1][tempX] + "").matches("[a-z]")) {
                found.add(map[tempY-1][tempX]+"");
            }
            if(map[tempY-1][tempX] == '.'){
                found.addAll(findKeys(tempX, tempY-1,visited,found));
            }
        }
        return found;

    }

    private static int bfs(int tempX, int tempY,HashSet<String> visited,String target) {
        visited.add(tempX + "-" + tempY);
        int distToKey = 0;
        if(!visited.contains((tempX + 1)+"-" + tempY)){
            if((map[tempY][tempX+1] + "").matches(target)) {
                deleteDoor((map[tempY][tempX+1]+"").toUpperCase());
                map[tempY][tempX+1] = '.';
                curX = tempX + 1;
                curY = tempY;
                return visited.size();
            }
            if(map[tempY][tempX+1] == '.'){
                distToKey = bfs(tempX+1,tempY, (HashSet<String>) visited.clone(),target);
                if(distToKey > 0) return distToKey;
            }
        }
        if(!visited.contains((tempX - 1)+"-" + tempY)){
            if((map[tempY][tempX-1] + "").matches(target)) {
                deleteDoor((map[tempY][tempX-1]+"").toUpperCase());
                map[tempY][tempX-1] = '.';
                curX = tempX - 1;
                curY = tempY;
                return visited.size();
            }
            if(map[tempY][tempX-1] == '.'){
                distToKey = bfs(tempX-1,tempY, (HashSet<String>) visited.clone(),target);
                if(distToKey > 0) return distToKey;
            }
        }
        if(!visited.contains(tempX+"-" + (tempY+1))){
            if((map[tempY+1][tempX] + "").matches(target)){
                deleteDoor((map[tempY+1][tempX]+"").toUpperCase());
                map[tempY+1][tempX] = '.';
                curX = tempX;
                curY = tempY + 1;
                return visited.size();
            }
            if(map[tempY+1][tempX] == '.'){
                distToKey = bfs(tempX,tempY+1, (HashSet<String>) visited.clone(),target);
                if(distToKey > 0) return distToKey;
            }
        }
        if(!visited.contains(tempX+"-" + (tempY-1))){
            if((map[tempY-1][tempX] + "").matches(target)){
                deleteDoor((map[tempY-1][tempX]+"").toUpperCase());
                map[tempY-1][tempX] = '.';
                curX = tempX;
                curY = tempY - 1;
                return visited.size() ;
            }
            if(map[tempY-1][tempX] == '.'){
                distToKey = bfs(tempX,tempY-1, (HashSet<String>) visited.clone(),target);
                if(distToKey > 0) return distToKey;
            }
        }
        return 0;

    }

    private static void deleteDoor(String charAt) {
        for(int i = 0; i < map.length;i ++){
            for(int j = 0;j < map[0].length; j++){
                if((map[i][j] + "").matches(charAt) ){
                    map[i][j] = '.';
                }
            }
        }
    }

    private static boolean mapHasKeys() {
        for(int i = 0; i < map.length;i ++){
            for(int j = 0;j < map[0].length; j++){
                if((map[i][j] + "").matches("[a-z]") )return true;
            }
        }
        return false;
    }

    public static char[][] deepCopyIntMatrix(char[][] input) {
        if (input == null)
            return null;
        char[][] result = new char[input.length][];
        for (int r = 0; r < input.length; r++) {
            result[r] = input[r].clone();
        }
        return result;
    }
}
