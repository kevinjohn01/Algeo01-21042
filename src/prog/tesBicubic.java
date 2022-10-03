package prog;
import java.util.Scanner;
import matriks.*;

public class tesBicubic {
    public static void main(String[] args){
        // baca input matriks
        Scanner inputln = new Scanner(System.in);
        Matriks M = Matriks.readMatriks(inputln);

        // mencari hasil bicubic
        Matriks a = Bicubic.bicubic(M);

        for(int i = 0; i < 16; i++){
            System.out.print(a.get(i,0) + " ");
        }

        inputln.close();
    }
}
