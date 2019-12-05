import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class CrossedWires {
    private static String[] wire1;
    private static String[] wire2;

    public static void readProgram(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("src\\Input.txt"));
            wire1 = br.readLine().trim().split(",");
            wire2 = br.readLine().trim().split(",");

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static List<Point> getPoints(String[] wire){
        List<Point> points = new ArrayList<>();
        points.add(new Point(0,0));
        for (int i = 0; i < wire.length;i++){
            int mag = Integer.parseInt(wire[i].substring(1));
            for (int j = 0; j < mag; j++) {
                switch (wire[i].charAt(0)) {
                    case 'U':
                        points.add(new Point(points.get(points.size()-1), 0, 1));
                        break;
                    case 'D':
                        points.add(new Point(points.get(points.size()-1), 0, -1));
                        break;
                    case 'L':
                        points.add(new Point(points.get(points.size()-1), -1, 0));
                        break;
                    case 'R':
                        points.add(new Point(points.get(points.size()-1), 1, 0));
                        break;
                }
            }
        }
        return points;
    }

    public static int getIntersections(List<Point> points1,List<Point> points2){
        points1.remove(0);
        points2.remove(0);
        points1.sort(Comparator.comparing(Point::getSteps));
        points2.sort(Comparator.comparing(Point::getSteps));

        int min = points1.size();

        for (int i = 0; i < min; i++){
            for (int j = 0; j < min - i; j++){
                if(points1.get(i).equals(points2.get(j))) {
                    int newMin = points1.get(i).getSteps() + points2.get(j).getSteps();
                    min = Math.min(min, newMin);
                }
            }
        }
        return min;

    }





    public static void main(String[] args){
        readProgram();
        List<Point> p1 = getPoints(wire1);
        List<Point> p2 = getPoints(wire2);

        System.out.println(getIntersections(p1,p2));
    }
}
