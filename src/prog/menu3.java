package prog;
import java.util.Scanner;
import matriks.*;
import matriks.spl.*;


// 1. INVERS
public class menu3 {
    // 1. Metode Reduksi Baris
    // 1.1. File
    public static void ReduksiBarisFile(String filename){
        Matriks M = Matriks.readMatriksFile("test\\" + filename);
        Matriks Minv = M.inverseWRed();
        if(Minv != null){
            System.out.println(Minv.toString());
        }else{
            System.out.println("Tidak dapat menginvers matriks");
        }
        
    }
    // 1.2. Terminal
    public static void ReduksiBarisTerm(Matriks M){
        Matriks Minv = M.inverseWRed();
        if(Minv != null){
            System.out.println(Minv.toString());
        }else{
            System.out.println("Tidak dapat menginvers matriks");
        }
    }

    // 2. Metode Ekspansi Kofaktor
    // 2.1. File
    public static void AdjoinFile(String filename){
        Matriks M = Matriks.readMatriksFile("test\\" + filename);
        Matriks Minv = M.inverseWAdj();
        if(Minv != null){
            System.out.println(Minv.toString());
        }else{
            System.out.println("Tidak dapat menginvers matriks");
        }
    }
    
    // 2.2. Terminal
    public static void AdjoinTerm(Matriks M){
        Matriks Minv = M.inverseWAdj();
        if(Minv != null){
            System.out.println(Minv.toString());
        }else{
            System.out.println("Tidak dapat menginvers matriks");
        }
    }
}
