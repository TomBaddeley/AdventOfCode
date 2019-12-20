import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Day20 {

    private static char[][] map;
    private static HashMap<Point,Point> portals = new HashMap<>();
    private static Point start;
    private static Point end;


    public static void main(String[] args){
        try {
            BufferedReader br = new BufferedReader(new FileReader("Day20\\Input.txt"));
            String inputString = br.readLine();

            //figure out number of lines for allocating array size
            int lineCount = 0;
            int maxWidth = 0;
            while(inputString != null){
                lineCount++;
                if(inputString.length() > maxWidth) maxWidth = inputString.length();
                inputString = br.readLine();
            }

            br = new BufferedReader(new FileReader("Day20\\Input.txt"));
            inputString = br.readLine();

            //read the text file into the array
            map = new char[lineCount][maxWidth];
            for(int row = 0; inputString != null; row++) {
                for (int i = 0; i < inputString.length(); i++) {
                    map[row][i] = inputString.charAt(i);
                }
                inputString = br.readLine();
            }

            storePortals();
            System.out.println(bfs());

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static int bfs() {
        Map<Point,Integer> visited = new HashMap<>();
        visited.put(start,0);

        Queue<Point> queue = new ArrayDeque<>();
        queue.add(start);

        while(!visited.containsKey(end)){
            Point p = queue.poll();
            assert p != null;
            Set<Point> neighbors = p.getNeighbors();
            for(Point n:neighbors){
                if(!visited.containsKey(n)) {
                    visited.put(n,visited.get(p)+1);
                    queue.add(n);
                }
            }
        }
        return visited.get(end);

    }

    private static void storePortals() {
        HashMap<String,Point> found = new HashMap<>();
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if((map[i][j]+"").matches("[A-Z]")){
                    Point neighbor = findNeighbor(i,j,"[A-Z]");
                    assert neighbor != null;
                    String portCode = map[i][j]+""+map[neighbor.getY()][neighbor.getX()];
                    String portCode2 = portCode.charAt(1) + "" + portCode.charAt(0);

                    Point passage = findNeighbor(i, j, "\\.");
                    if (passage == null) passage = findNeighbor(neighbor.getY(), neighbor.getX(), "\\.");
                    assert passage != null;

                    if(passage.equals(found.get(portCode))|| passage.equals(found.get(portCode2))){
                        continue;
                    }
                    if(!(found.containsKey(portCode)||found.containsKey(portCode2))) {//we havent found the other side of the portal yet
                        if(portCode.equals("AA")) start = passage;
                        else if(portCode.equals("ZZ")) end = passage;
                        else found.put(portCode, passage);
                    } else{
                        Point coord = found.get(portCode);
                        if(coord == null) coord = found.get(portCode2);

                        portals.put(coord,passage);
                        portals.put(passage,coord);

                    }
                }
            }
        }
    }



    private static Point findNeighbor(int y, int x,String regex) {
        for(int i = Math.max(0,y-1); i < Math.min(y+2,map.length);i++){
            for(int j = Math.max(0,x-1); j < Math.min(x+2,map[0].length);j++){
                if(i == y && j == x) continue;
                if(i == y || j == x) {
                    if ((map[i][j] + "").matches(regex)) return new Point(i, j);
                }
            }
        }
        return null;
    }


    public static class Point{
        private int x;
        private int y;

        public Point(int y,int x){
            this.y = y;
            this.x = x;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Set<Point> getNeighbors(){
            Set<Point> neighbors = new HashSet<>();
            for(int i = Math.max(0,y-1); i < Math.min(y+2,map.length);i++){
                for(int j = Math.max(0,x-1); j < Math.min(x+2,map[0].length);j++){
                    if(i == y && j == x) continue;
                    if(i == y || j ==x) {
                        if ((map[i][j] + "").matches("\\.")) neighbors.add(new Point(i, j));
                    }
                }
            }
            if(portals.containsKey(this)) neighbors.add(portals.get(this));
            return neighbors;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
