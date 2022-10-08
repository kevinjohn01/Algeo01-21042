package prog;
import java.util.Scanner;
import matriks.*;
import java.io.File;
import java.io.FileNotFoundException;

public class menu5 {
    public static void BicubicFile(String filename){
        Matriks M = new Matriks(4,4);
        try{
            // baca file
            File f = new File ("test\\" + filename);
            Scanner line = new Scanner (f);
        
            String[] dataln;
            // baca file, pindahkan ke matriks
            int i = 1;
            for (i = 0; i < 4; i++){
                dataln = line.nextLine().trim().split("\\s+");
                for (int j = 0; j < 4; j++){
                    M.set(i,j,Float.parseFloat(dataln[j]));
                }
            }
            line.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        
        Matriks a = Bicubic.bicubic(M);

        System.out.print("Input nilai x,y yang ingin dicari (mis: 0.5 0.5): ");

        Scanner inputxy = new Scanner(System.in);
        String [] xy = inputxy.nextLine().split("\\s+");
        Float[] numxy = new Float[2];
        for (int i = 0; i < 2; i++){
            numxy[i] = Float.parseFloat(xy[i]);
        }
        inputxy.close();

        // hasil
        float hasil = Bicubic.hasilbic(a,numxy[0],numxy[1]);

        System.out.println(hasil);
    }

    public static void BicubicTerm(Matriks M){
        Matriks a = Bicubic.bicubic(M);

        System.out.println("Input nilai x,y yang ingin dicari (mis: 0.5 0.5): ");

        Scanner inputxy = new Scanner(System.in);

        String[] xy = inputxy.nextLine().split("\\s+");
        float[] numxy = new float[2];

        for(int i = 0; i < 2; i++){
            numxy[i] = Float.parseFloat(xy[i]);
        }
        inputxy.close();


        // hasil
        float hasil = Bicubic.hasilbic(a,numxy[0],numxy[1]);

        System.out.println(hasil);
    }
}
