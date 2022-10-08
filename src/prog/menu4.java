package prog;
import java.util.Scanner;
import matriks.*;
import matriks.spl.*;
import java.io.File;
import java.io.FileNotFoundException;

public class menu4 {
    public static void IntPolFile(String filename, float X){
        SolusiSPL k;
        Matriks M = Matriks.readMatriksFile("test\\" + filename);
        k = InterpolasiPolinom.interpolasipolinom(M);
        InterpolasiPolinom.printfungsi(k);
        System.out.format("f(%f) = %f%n", X, InterpolasiPolinom.nilaifungsi(k, X));
    }

    public static void IntPolTerm(Matriks M, float X){
        SolusiSPL k;
        k = InterpolasiPolinom.interpolasipolinom(M);
        InterpolasiPolinom.printfungsi(k);
        System.out.format("f(%f) = %f%n", X, InterpolasiPolinom.nilaifungsi(k, X));
    }
}
