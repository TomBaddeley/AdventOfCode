public class Amp {
    private int codePointer;
    private int[] code;
    private int param;
    private boolean paramTaken = false;

    public Amp(int[] code,int param) {
        this.codePointer = 0;
        this.code = code;
        this.param = param;
    }

    public int getOutput( int input) {


        int inputPointer = 0;

        for(; codePointer < code.length && code[codePointer] != 99;) {
            String pCode = (code[codePointer] +"");
            while(pCode.length() < 5){
                pCode = "0" + pCode;
            }

            String opCode = pCode.substring(pCode.length()-2);
            String par1 = pCode.substring(2,3);
            String par2 = pCode.substring(1,2);



            if (opCode.equals("01")) {
                int val1 = par1.equals("0") ? code[code[codePointer + 1]]:code[codePointer + 1];
                int val2 = par2.equals("0") ? code[code[codePointer + 2]]:code[codePointer + 2];

                code[code[codePointer + 3]] = val1+val2;
                codePointer = codePointer+4;
            } else if (opCode.equals("02")) {
                int val1 = par1.equals("0") ? code[code[codePointer + 1]]:code[codePointer + 1];
                int val2 = par2.equals("0") ? code[code[codePointer + 2]]:code[codePointer + 2];

                code[code[codePointer + 3]] = val1 * val2;
                codePointer = codePointer+4;
            } else if (opCode.equals("03")) {
                if(!paramTaken) {
                    code[code[codePointer + 1]] = param;
                    paramTaken = true;
                } else {
                    code[code[codePointer + 1]] = input;
                }

                codePointer = codePointer + 2;
            } else if (opCode.equals("04")) {

                int output =  par1.equals("0") ? code[code[codePointer + 1]]:code[codePointer + 1];
                codePointer = codePointer +2;
                return output;

            } else if (opCode.equals("05")){
                int val1 = par1.equals("0") ? code[code[codePointer + 1]]:code[codePointer + 1];

                if (val1 != 0){
                    codePointer = par2.equals("0") ? code[code[codePointer + 2]]:code[codePointer + 2];
                } else {
                    codePointer = codePointer + 3;
                }
            } else if (opCode.equals("06")){
                int val1 = par1.equals("0") ? code[code[codePointer + 1]]:code[codePointer + 1];

                if (val1 == 0){
                    codePointer = par2.equals("0") ? code[code[codePointer + 2]]:code[codePointer + 2];
                } else {
                    codePointer = codePointer + 3;
                }
            } else if (opCode.equals("07")){
                int val1 = par1.equals("0") ? code[code[codePointer + 1]]:code[codePointer + 1];
                int val2 = par2.equals("0") ? code[code[codePointer + 2]]:code[codePointer + 2];

                if(val1 < val2) code[code[codePointer + 3]] = 1;
                else code[code[codePointer + 3]] = 0;

                codePointer = codePointer +4;
            }else if (opCode.equals("08")){
                int val1 = par1.equals("0") ? code[code[codePointer + 1]]:code[codePointer + 1];
                int val2 = par2.equals("0") ? code[code[codePointer + 2]]:code[codePointer + 2];

                if(val1 == val2) code[code[codePointer + 3]] = 1;
                else code[code[codePointer + 3]] = 0;

                codePointer = codePointer +4;
            }
        }
        return -1;

    }


}
