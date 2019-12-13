import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class NBodyProb {

    private static List<Moon> moons = new ArrayList<>();
    public static double[] readProgram() throws Error {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Day12\\Input.txt"));
            String line = br.readLine().trim();

            while(line!= null){
                String[] data = line.substring(1,line.length()-1).split(", ");
                Moon m = new Moon(Integer.parseInt(data[0].substring(2)),Integer.parseInt(data[1].substring(2)),
                        Integer.parseInt(data[2].substring(2)));
                moons.add(m);
                line = br.readLine();
            }

        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args){
        readProgram();
        System.out.println(simulateSystem(100000000));
    }

    private static Long simulateSystem(int totalSteps) {
        Long step = 0L;
        Set<Integer> xStates = new HashSet<>();
        Set<Integer> yStates = new HashSet<>();
        Set<Integer> zStates = new HashSet<>();

        xStates.add(Objects.hash(moons.get(0).getxPos(),moons.get(0).getxVel(),moons.get(1).getxPos(),moons.get(1).getxVel(),
                moons.get(2).getxPos(),moons.get(2).getxVel()));
        yStates.add(Objects.hash(moons.get(0).getyPos(),moons.get(0).getyVel(),moons.get(1).getyPos(),moons.get(1).getyVel(),
                moons.get(2).getyPos(),moons.get(2).getyVel()));
        zStates.add(Objects.hash(moons.get(0).getzPos(),moons.get(0).getzVel(),moons.get(1).getzPos(),moons.get(1).getzVel(),
                moons.get(2).getzPos(),moons.get(2).getzVel()));

        boolean xFound = false;
        boolean yFound = false;
        boolean zFound = false;

        long xPeriod = 0L;
        long yPeriod = 0L;
        long zPeriod = 0L;

        while(!(xFound && yFound && zFound)){
            //update velocities
            for(Moon m:moons){
                for(Moon n:moons){
                    if(m.equals(n)) continue;
                    int dx,dy,dz;
                    if(n.getxPos() - m.getxPos() == 0) dx =0;
                    else dx = (n.getxPos() - m.getxPos())/Math.abs(m.getxPos() - n.getxPos());

                    if(n.getyPos() - m.getyPos() == 0) dy =0;
                    else dy = (n.getyPos() - m.getyPos())/Math.abs(n.getyPos() - m.getyPos());

                    if(n.getzPos() - m.getzPos() == 0) dz =0;
                    else dz = (n.getzPos() - m.getzPos())/Math.abs(n.getzPos() - m.getzPos());
                    m.increaseVelocity(dx,dy,dz);
                }
            }
            for(Moon m:moons) m.updatePos();
            step++;

            if(!xFound){
                int state = Objects.hash(moons.get(0).getxPos(),moons.get(0).getxVel(),moons.get(1).getxPos(),moons.get(1).getxVel(),
                        moons.get(2).getxPos(),moons.get(2).getxVel());
                if(xStates.contains(state)) {
                    xFound = true;
                    xPeriod = step;
                }
            }
            if(!yFound){
                int state = Objects.hash(moons.get(0).getyPos(),moons.get(0).getyVel(),moons.get(1).getyPos(),moons.get(1).getyVel(),
                        moons.get(2).getyPos(),moons.get(2).getyVel());
                if(yStates.contains(state)) {
                    yFound = true;
                    yPeriod = step;
                }
            }
            if(!zFound){
                int state = Objects.hash(moons.get(0).getzPos(),moons.get(0).getzVel(),moons.get(1).getzPos(),moons.get(1).getzVel(),
                        moons.get(2).getzPos(),moons.get(2).getzVel());
                if(zStates.contains(state)) {
                    zFound = true;
                    zPeriod = step;
                }
            }
        }
        return lcm(xPeriod,yPeriod,zPeriod);  
    }

    private static Long lcm(long xPeriod, long yPeriod, long zPeriod) {
        long x = xPeriod;
        long y = yPeriod;
        long z = zPeriod;

        while(!(x == y && y==z)){
            if(x < y || x < z) x+= xPeriod;
            if(y < z || y < x) y+= yPeriod;
            if(z < y || z < x) z+= zPeriod;
        }
        return x;
    }

    private static int calcEnergy() {
        int energy = 0;
        for(Moon m:moons){
            energy += (Math.abs(m.getxPos())+Math.abs(m.getyPos())+Math.abs(m.getzPos())) * (Math.abs(m.getxVel())+Math.abs(m.getyVel())+Math.abs(m.getzVel()));
        }
        return energy;
    }

}
