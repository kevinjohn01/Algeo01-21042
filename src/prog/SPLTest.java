package prog;

import java.util.Scanner;

import matriks.*;
import matriks.spl.SolusiSPL;

public class SPLTest{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        Matriks A = Matriks.readMatriks(in);
        Matriks B = Matriks.readMatriks(in);

        MatriksAug M = MatriksAug.from(A, B);
        if(M != null){
            System.out.println(M.toString() + "\n");
            SolusiSPL sol = M.elimGauss();
            System.out.println(M.toString() + "\n");
            System.out.println(M.elimGauss().toString());
        }else{
            System.out.println("Matriks tidak kompatibel");
        }
    }
}
