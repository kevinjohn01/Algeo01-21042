package matriks;
import java.util.Scanner;

public class tesBicubic {
    public static void main(String[] args){
        // baca input matriks
        Scanner inputln = new Scanner(System.in);
        Matriks M = Matriks.readMatriks(inputln);
/*
        Matriks Mb = matriksBalikan.Balikan(M);
        for(int i = 0; i < Mb.BARIS(); i++){
            for(int j = 0; j < Mb.KOLOM(); j++){
                System.out.print(Mb.get(i,j));
            }
            System.out.println();
        }*/
/* 
        // mencari hasil bicubic
        Matriks a = Bicubic.bicubic(M);

        for(int i = 0; i < 16; i++){
            System.out.print(a.get(i,0) + " ");
        }

        inputln.close(); */
    }
}
