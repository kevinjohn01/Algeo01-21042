package matriks;
import java.util.Scanner;

public class tesBicubic {
    public static void main(String[] args){
        // baca input matriks
        Scanner inputln = new Scanner(System.in);
        Matriks M = Matriks.readMatriks(inputln);

        // mencari hasil bicubic
        Matriks a = Bicubic.bicubic(M);

        System.out.println("f(0,0) = " + Bicubic.hasilbic(a,0,0));
        System.out.println("f(0.5,0.5) = " + Bicubic.hasilbic(a,0.5,0.5));
        System.out.println("f(0.25,0.75) = " + Bicubic.hasilbic(a,0.25,0.75));
        System.out.println("f(0.1,0.9) = " + Bicubic.hasilbic(a,0.1,0.9));

        inputln.close();
    }
}
