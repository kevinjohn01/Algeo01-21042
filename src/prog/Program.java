package prog;

import java.util.Scanner;
import matriks.*;
import matriks.spl.*;

public class Program{
    /* *** MAIN *** */
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int SIZE = Integer.parseInt(in.nextLine());

        Matriks
            M = new Matriks(SIZE, SIZE),
            N = new Matriks(SIZE, 1);
        
        for(int i = 0; i < SIZE; i++){
            String[] inp = in.nextLine().split("\\s+");
            for(int j = 0; j < Math.min(SIZE, inp.length); j++){
                M.set(i,j, Float.parseFloat(inp[j]));
            }
        }
        String[] inp = in.nextLine().split("\\s+");
        float[] inpf = new float[inp.length];
        for(int i = 0; i < inpf.length; i++){
            inpf[i] = Float.parseFloat(inp[i]);
        }
        N.setRange(inpf);

        System.out.println(M.toString());
        System.out.println(N.toString());
        System.out.println();

        MatriksAug X = MatriksAug.from(M, N);
        System.out.println(X.toString());
        System.out.println();

        int OPT = Integer.parseInt(in.nextLine());
        SolusiSPL s = OPT == 0 ? X.elimGauss() : X.elimGaussJordan();
        System.out.println(s.toString());
        System.out.println(X.toString());

        in.close();
    }
}
