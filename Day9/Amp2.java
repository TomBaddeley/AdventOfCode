import java.util.Arrays;

public class Amp2 {
    private int codePointer;
    private double[] code;

    private int inputPointer = 0;
    private int inputSize = 0;
    private int[] input = new int[100000];
    private int basePointer = 0;
    private boolean terminated = false;
    private boolean inputNeeded = false;

    public Amp2(double[] code) {
        this.codePointer = 0;
        this.code = Arrays.copyOf(code,5000);
    }

    public boolean isInputNeeded() {
        return inputNeeded;
    }

    public void giveInput(int i){
        input[inputSize] = i;
        inputSize++;
        inputNeeded = false;
    }

    public double getOutput() {


        for(; codePointer < code.length && (code[codePointer] != 99);) {

            String pCode = ((int)code[codePointer] +"");
            while(pCode.length() < 5){
                pCode = "0" + pCode;
            }

            String opCode = pCode.substring(pCode.length()-2);
            String par1 = pCode.substring(2,3);
            String par2 = pCode.substring(1,2);
            String par3 = pCode.substring(0,1);



            if (opCode.equals("01")) {
                double val1 = getVal(par1,1);
                double val2 = getVal(par2,2);

                if(par3.equals("0")) {
                    code[(int) code[codePointer + 3]] = val1 + val2;
                } else {
                    code[(int) code[codePointer + 3]+ basePointer] = val1 + val2;
                }
                codePointer = codePointer+4;
            } else if (opCode.equals("02")) {
                double val1 = getVal(par1,1);
                double val2 = getVal(par2,2);

                if(par3.equals("0")) {
                    code[(int) code[codePointer + 3]] = val1 * val2;
                } else {
                    code[(int) code[codePointer + 3]+ basePointer] = val1 * val2;
                }
                codePointer = codePointer+4;
            } else if (opCode.equals("03")) {
                if(inputPointer >= inputSize) {
                    inputNeeded = true;
                    return -1;
                }
                if(par1.equals("0")) {
                    code[(int) code[codePointer + 1]] = input[inputPointer++];
                } else{
                    code[(int) code[codePointer + 1] + basePointer] = input[inputPointer++];
            }

                codePointer = codePointer + 2;
            } else if (opCode.equals("04")) {

                double output = getVal(par1,1);
                codePointer = codePointer +2;
                return output;

            } else if (opCode.equals("05")){
                double val1 = getVal(par1,1);

                if (val1 != 0){
                    codePointer = (int)getVal(par2,2);
                } else {
                    codePointer = codePointer + 3;
                }
            } else if (opCode.equals("06")){
                double val1 = getVal(par1,1);

                if (val1 == 0){
                    codePointer = (int)getVal(par2,2);
                } else {
                    codePointer = codePointer + 3;
                }
            } else if (opCode.equals("07")){
                double val1 = getVal(par1,1);
                double val2 = getVal(par2,2);
                if(par3.equals("0")) {
                    if (val1 < val2) code[(int) code[codePointer + 3]] = 1;
                    else code[(int) code[codePointer + 3]] = 0;
                } else {
                    if (val1 < val2) code[(int) code[codePointer + 3]+basePointer] = 1;
                    else code[(int) code[codePointer + 3]+basePointer] = 0;
                }

                codePointer = codePointer +4;
            }else if (opCode.equals("08")){
                double val1 = getVal(par1,1);
                double val2 = getVal(par2,2);

                if(par3.equals("0")) {
                    if (val1 == val2) code[(int) code[codePointer + 3]] = 1;
                    else code[(int) code[codePointer + 3]] = 0;
                } else {
                    if (val1 == val2) code[(int) code[codePointer + 3]+basePointer] = 1;
                    else code[(int) code[codePointer + 3]+basePointer] = 0;
                }

                codePointer = codePointer +4;
            } else if (opCode.equals("09")){
                basePointer += getVal(par1,1);
                codePointer = codePointer + 2;
            }
        }
        terminated = true;
        return -1;
    }

    public boolean isTerminated() {
        return terminated;
    }

    private double getVal(String par, int valPos) {
        if (par.equals("0")) return code[(int)code[codePointer + valPos]];
        else if (par.equals("1")) return code[codePointer + valPos];
        else return code[(int)code[codePointer + valPos] + basePointer];
    }


}
