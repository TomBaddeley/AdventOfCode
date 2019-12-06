import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class UniversalOrbitMap {

    private static HashMap<String,String> orbit = new HashMap<>();

    public static int readProgram() throws Error {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Day6\\OrbitData.txt"));
            String line = br.readLine();
            while(line != null){
                String[] orbData = line.split("\\)");
                orbit.put(orbData[1],orbData[0]);

                line = br.readLine();
            }

            int orbitCounter = 0;
            for(String s:orbit.keySet()){
                orbitCounter += orbitCounter(s);
            }

            return orbitCounter;
        } catch (Exception var1) {
            var1.printStackTrace();
            throw new Error();
        }

    }

    public static int orbitCounter(String s){
        if(orbit.containsKey(orbit.get(s))){
            return 1 + orbitCounter(orbit.get(s));
        } else return 1;
    }


    public static int findSanta(){
        HashMap<String,Integer> visited = new HashMap<>();
        Queue<String> unvisited = new ArrayDeque<>();
        String start = orbit.get("YOU");
        unvisited.add(start);
        visited.put(start,0);

        while(!unvisited.isEmpty()){
            String current = unvisited.poll();
            String other = orbit.get(current);
            if(other!= null && !visited.containsKey(other)){
                visited.put(other,visited.get(current)+1);
                unvisited.add(other);
            }
            for(String s:orbit.keySet()){
                if(orbit.get(s).equals(current) && !visited.containsKey(s)){
                    visited.put(s,visited.get(current)+1);
                    unvisited.add(s);
                }
            }
            if(visited.containsKey("SAN")) return visited.get("SAN") - 1;
        }
        return 0;
    }





    public static void main(String[] args){
        System.out.println(readProgram());
        System.out.println(findSanta());
    }
}
