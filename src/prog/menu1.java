package prog;
import java.util.Scanner;
import matriks.*;
import matriks.spl.*;


// 1. SISTEM PERSAMAAN LINIER
public class menu1 {
    // 1. Metode Eliminasi Gauss
    // 1.1. File
    public static void ElimGaussFile(String filename){
        /*
        Matriks M = Matriks.readMatriksFile(filename);
        SolusiSPL sol = M.elimGauss();
        sol.toString();
        */
    }
    // 1.2. Terminal
    public static void ElimGaussTerm(MatriksAug M){
        SolusiSPL sol = M.elimGauss();
        System.out.println(sol.toString());
    }

    // 2. Metode Eliminasi Gauss-Jordan
    // 2.1. File
    public static void ElimGaussJordanFile(String filename){
        /*
        Matriks M = Matriks.readMatriksFile(filename);
        SolusiSPL sol = M.elimGauss();
        sol.toString();
        */
    }
    // 2.2. Terminal
    public static void ElimGaussJordanTerm(MatriksAug M){
        SolusiSPL sol = M.elimGaussJordan();
        System.out.println(sol.toString());
    }

    // 3. Metode Matriks Balikan
    // 3.1 File
    public static void MatriksBalikanFile(String filename){
        /*
         
         */
    }
    // 3.2 Terminal
    public static void MatriksBalikanTerm(MatriksAug M){
        SolusiSPL sol = M.solveInverse();
        System.out.println(sol.toString());
    }

    // 4. Kaidah Cramer
    // 4.1 File
    public static void KaidahCramerFile(String filename){
        /*
         
         */
    }
    // 4.2 Terminal
    public static void KaidahCramerTerm(MatriksAug M){
        SolusiSPL sol = Cramer.cramerAug(M);
        System.out.println(sol.toString());
    }
}
