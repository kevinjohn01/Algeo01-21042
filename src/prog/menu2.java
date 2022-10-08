package prog;
import java.util.Scanner;
import matriks.*;
import matriks.spl.*;
import matriks.util.Format;


// 1. DETERMINAN
public class menu2 {
    // 1. Metode Reduksi Baris
    // 1.1. File
    public static void ReduksiBarisFile(String filename){
        Matriks M = Matriks.readMatriksFile("test\\" + filename);
        float det = M.determinantWRed();
        System.out.format("det(M) = %s%n", Format.floatFMT(det));
    }
    // 1.2. Terminal
    public static void ReduksiBarisTerm(Matriks M){
        float det = M.determinantWRed();
        System.out.format("det(M) = %s%n", Format.floatFMT(det));
    }

    // 2. Metode Ekspansi Kofaktor
    // 2.1. File
    public static void EkspansiFile(String filename){
        Matriks M = Matriks.readMatriksFile("test\\" + filename);
        float det = M.determinantWExp();
        System.out.format("det(M) = %s%n", Format.floatFMT(det));
    }
    // 2.2. Terminal
    public static void EkspansiTerm(Matriks M){
        float det = M.determinantWExp();
        System.out.format("det(M) = %s%n", Format.floatFMT(det));
    }
}
