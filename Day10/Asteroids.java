import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Asteroids {
    private static int [][] ast;
    private static List<String> order = new ArrayList<>();
    static int x;
    static int y;
    public static void readProgram() throws Error {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Day10\\Input.txt"));
            String line = br.readLine();
            ast = new int[27][line.length()];
            int row = 0;
            while(line != null){
                for(int i = 0; i < line.length(); i++){
                    ast[row][i] = line.charAt(i) == '#' ? 1:0;
                }
                row++;
                line = br.readLine();
            }

        } catch (Exception var1) {
            var1.printStackTrace();
        }
    }

    public static void inVision(){
        int maxAst = 0;
        for(int i = 0; i < ast.length;i++){
            for (int j = 0; j < ast[0].length; j++){
                if (ast[i][j] == 0) continue;
                int astCount = 0;
                for(int k = 0; k < ast.length;k++) {
                    for (int l = 0; l < ast[0].length; l++) {
                        if (ast[k][l] == 0 || (i == k) && (j == l)) continue;
                        double gradient = (double)(i-k)/(j-l);

                        int currentX = Math.min(j,l);
                        double currentY = j == currentX ? i:k;

                        while(currentX <= Math.max(j,l)){
                            if(j == l){//infinite
                                for(int st = Math.min(i,k) + 1;st < Math.max(i,k);st++){
                                    if(ast[st][currentX] == 1){
                                        astCount--;
                                        break;
                                    }
                                }
                                break;
                            }
                            currentY = currentY + gradient;
                            currentX++;
                            //System.out.println(currentY);
                            if(Math.abs(currentY - Math.round(currentY)) < 0.0000001){
                                if(currentX == Math.max(j,l) ) {
                                    break;
                                }
                                if(ast[(int)Math.round(currentY)][currentX] == 1) {
                                    astCount--;
                                    break;
                                }
                            }
                        }
                        astCount++;

                    }
                }
                //System.out.println(astCount);
                if(astCount > maxAst) {
                    maxAst=astCount;
                    System.out.println(maxAst);
                    y = i;
                    x = j;
                }
            }
        }
    }

    public static void main(String[] args){
        readProgram();
        inVision();
        calcOrder();
        int i =0;
        for(String s:order){
            System.out.println(i++ + ": " + s);
        }
    }

    private static void calcOrder() {
        int[] verticles = new int[ast.length];//stores asteroids directly above/below

        for(int i = 0; i < ast.length;i++){
            if(ast[i][x] == 1) verticles[i] = 1;
        }
        ast[y][x] = 0;
        int numOfAsteroids = (int)Arrays.stream(ast).flatMapToInt(Arrays::stream).filter(x -> x == 1).count();
        while(numOfAsteroids > order.size()){
            shootVerticle(false,verticles);
            double grad = 100;
            while(grad != - 100) {
                grad = shootHorizontal(true,grad);
            }
            grad = 100;
            while(grad != - 100) {
                grad = shootHorizontal(false,grad);
            }
            shootVerticle(true,verticles);

        }

    }

    private static double shootHorizontal(boolean positive,double initGrad) {

        int bestX = x;
        int bestY = y;
        double bestGrad = -100;

        if(positive){
            for(int i = 0; i < ast.length; i++){
                for(int j = x + 1; j < ast[0].length; j++){
                    double gradient = (double) (y-i) / (j - x);
                    if(ast[i][j] == 1 && gradient< initGrad) {
                        if (bestX == x && bestY == y) {//first asteroid found will be first potential solution
                            bestX = j;
                            bestY = i;
                            bestGrad = gradient;
                        } else {
                            if (gradient > bestGrad || (gradient == bestGrad && j < bestX)) {
                                bestX = j;
                                bestY = i;
                                bestGrad = gradient;
                            }
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < ast.length; i++) {
                for (int j = x - 1; j >= 0; j--) {
                    double gradient = (double) (y-i) / (j - x);
                    if (ast[i][j] == 1 && gradient < initGrad) {
                        if (bestX == x && bestY == y) {//first asteroid found will be first potential solution
                            bestX = j;
                            bestY = i;
                            bestGrad = gradient;
                        } else {
                            if (gradient > bestGrad || (gradient == bestGrad && j > bestX)) {
                                bestX = j;
                                bestY = i;
                                bestGrad = gradient;
                            }
                        }
                    }
                }
            }
        }
        ast[bestY][bestX] = 0;
        if(bestGrad!= -100) {
            order.add(bestX + "-" + bestY);
        }

        return bestGrad;
    }

    private static void shootVerticle(boolean positiveDir,int[] verts) {
        int z = y;
        if(positiveDir){
            for(int i = y + 1; i < verts.length; i++){
                if(verts[i] == 1){
                    z = i;
                    break;
                }
            }
        } else{
            for(int i = y - 1; i >= 0; i--){
                if(verts[i] == 1){
                    z = i;
                    break;
                }
            }
        }
        if(z != y){
            order.add(x + "-" + (z));
            ast[z][x] = 0;
            verts[z] = 0;
        }
    }
}
