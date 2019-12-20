import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Day14 {
    private static HashMap<String, String> reactions = new HashMap<>();
    private static HashMap<String, Long> resources = new HashMap<>();
    private static long oreNeeded = 0;


    public static void readProgram() throws Error {


        try {
            BufferedReader br = new BufferedReader(new FileReader("Day14\\Input.txt"));
            String line = br.readLine();

            while(line!=null){
                line = line.replace(",","");
                String rhs = line.substring(line.indexOf('>')+1).stripLeading();
                reactions.put(rhs.substring(rhs.indexOf(" ") + 1),line);

                line = br.readLine();

            }



        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void calcOreNeeded(){

        String react = reactions.get("FUEL");
        Scanner sc = new Scanner(react.substring(0, react.indexOf('=')));
        resources.put("FUEL", 0L);
        long target = 1000000000000L;
        int end = 3700000;
        int start = 350000;

        while(start<end) {
            calcCost("FUEL", (start+end)/2);
            if(oreNeeded > target) end = (start+end)/2 -1;
            else start = (start+end)/2 + 1;
            oreNeeded=0;
        }
        System.out.println(start);
        System.out.println(end);
    }



    private static void calcCost(String resource, long needed) {
        //use available resources first
        long resAvailable = resources.get(resource);
        if(resAvailable > 0){
            if(resAvailable >= needed){
                resources.put(resource,resAvailable-needed);
                return;
            } else{
                needed -= resAvailable;
                resources.put(resource,0L);
            }
        }
        //create new resources if needed
        String react  = reactions.get(resource);
        Scanner sc = new Scanner(react.substring(0,react.indexOf('=')));
        String productAmount = react.substring(react.indexOf(">")+2);
        int prodAmount = Integer.parseInt(productAmount.substring(0,productAmount.indexOf(" ")));
        while(sc.hasNext()) {
            long amount = sc.nextInt();
            String res = sc.next();
            if (!resources.containsKey(res)) resources.put(res, 0L);
            if(!res.equals("ORE")){
                long excess = (long)Math.ceil((double)needed/prodAmount) *prodAmount - needed;
                resources.put(resource,excess);
                calcCost(res,(long)Math.ceil((double)needed/prodAmount)*amount);
            }
            else{
                while(needed > 0){
                    oreNeeded += amount;
                    needed -= prodAmount;

                }
                resources.put(resource,-1*needed);
            }
        }
    }


    public static void main(String[] args) {
        readProgram();
        resources.put("FUEL",0L);
        calcCost("FUEL",3412429);
        //calcOreNeeded();
        System.out.println(oreNeeded);

    }

}
