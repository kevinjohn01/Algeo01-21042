package prog;
import java.util.Scanner;
import matriks.*;
import matriks.spl.*;
import java.io.File;
import java.io.FileNotFoundException;

public class menu4 {
    public static void IntPolFile(String filename){
        SolusiSPL k;
        Matriks M = Matriks.readMatriksFile("test\\" + filename);
        k = InterpolasiPolinom.interpolasipolinom(M);
        InterpolasiPolinom.printfungsi(k);
    }

    public static void IntPolTerm(Matriks M){
        SolusiSPL k;
        k = InterpolasiPolinom.interpolasipolinom(M);
        InterpolasiPolinom.printfungsi(k);
    }
}
