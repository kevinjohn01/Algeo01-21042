package prog;
import java.util.Scanner;
import matriks.*;

public class menu5 {
    public static void BicubicFile(String filename){
        Matriks M = Matriks.readMatriksFile(filename);
        Matriks a = Bicubic.bicubic(M);

        System.out.print("Input nilai x,y yang ingin dicari (mis: 0.5 0.5): ");

        Scanner inputxy = new Scanner(System.in);
        String [] xy = inputxy.nextLine().split("\\s+");
        int[] numxy = new int[2];
        for (int i = 0; i < 2; i++){
            numxy[i] = Integer.parseInt(xy[i]);
        }
        inputxy.close();

        // hasil
        float hasil = Bicubic.hasilbic(a,numxy[0],numxy[1]);

        System.out.println(hasil);
    }

    public static void BicubicTerm(Matriks M){
        Matriks a = Bicubic.bicubic(M);

        System.out.print("Input nilai x,y yang ingin dicari (mis: 0.5 0.5): ");

        Scanner inputxy = new Scanner(System.in);
        String [] xy = inputxy.nextLine().split("\\s+");
        int[] numxy = new int[2];
        for (int i = 0; i < 2; i++){
            numxy[i] = Integer.parseInt(xy[i]);
        }
        inputxy.close();

        // hasil
        float hasil = Bicubic.hasilbic(a,numxy[0],numxy[1]);

        System.out.println(hasil);
    }
}
