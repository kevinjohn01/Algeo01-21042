package prog;

import java.util.Scanner;

import matriks.*;
import matriks.spl.SolusiSPL;

public class SPLTest{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        //Matriks A = Matriks.readMatriks(in).squarifyRow();
        //Matriks B = Matriks.readMatriks(in).matchRow(A);
        Matriks A = new Matriks(n);
        A.each((i,j) -> A.set(i,j, 1f/(i+j+1)));
        Matriks B = new Matriks(n, 1);
        B.set(0,0, 1f);


        MatriksAug M = MatriksAug.from(A, B);
        if(M != null){
            System.out.println(M.toString() + "\n");
            SolusiSPL sol = M.elimGauss();
            System.out.println(sol.toString());
        }else{
            System.out.println("Matriks tidak kompatibel");
        }
    }
}
