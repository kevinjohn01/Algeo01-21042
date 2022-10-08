package matriks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import matriks.spl.*;

public class RegresiLinearBerganda{
    public static void MultLinearRegression(Matriks EXPL, Matriks RESP, float[] UJI){
        int M = EXPL.BARIS();
        int N = EXPL.KOLOM();

        Matriks A = new Matriks(N+1, N+1);
        Matriks B = new Matriks(N+1, 1);

        for(int row = 0; row < A.BARIS(); row++){
            for(int col = 0; col < A.KOLOM(); col++){
                if(row == 0 && col == 0){
                    A.set(row,col, M);
                    continue;
                }
                float sum = 0f;
                for(int i = 0; i < M; i++){
                    float term = 1f;
                    if(row > 0)term *= EXPL.get(i, row-1);
                    if(col > 0)term *= EXPL.get(i, col-1);
                    sum += term;
                }
                A.set(row,col, sum);
            }
        }
        for(int row = 0; row < B.BARIS(); row++){
            float sum = 0f;
            for(int i = 0; i < M; i++){
                float term = RESP.get(i,0);
                if(row > 0)term *= EXPL.get(i,row-1);
                sum += term;
            }
            B.set(row,0, sum);
        }

        MatriksAug X = MatriksAug.from(A, B);
        SolusiSPL sol = X.elimGaussJordan();
        if(sol instanceof SolusiSPLUnik){
            /// Solusi
            Matriks res = new Matriks(1, N+1);
            for(int i = 0; i < sol.numVariable(); i++){
                res.set(0,i, sol.get(i));
            }
            /// Tampilan
            StringBuilder str = new StringBuilder();
            str.append("y = ");
            for(int i = 0; i < res.KOLOM(); i++){
                float r = res.get(0,i);
                char sgn = r < 0f ? '-' : '+';
                if(i == 0){
                    if(sgn == '-')str.append(sgn);
                }else{
                    str.append(" " + sgn + " ");
                }
                r = Math.abs(r);
                str.append(r);
                if(i > 0)str.append(String.format("x%d", i));
            }
            System.out.println(str.toString());

            if(UJI != null){
                Matriks R = new Matriks(sol.numVariable(), 1);
                R.set(0,0, 1f);
                for(int i = 1; i < R.BARIS(); i++){
                    R.set(i,0, UJI[i-1]);
                }
                System.out.format("Nilai taksiran dengan titik %s: %f%n", Arrays.toString(UJI), res.product(R).get(0,0));
            }
        }
    }

    public static void MultLinearRegressionFile(String filename){
        /// Baca matriks dari file
        Matriks EXPL, RESP;
        float UJI[] = null;

        /// Hitung banyak titik sampel
        int M = 0, N = 0;
        try{
            File f = new File(filename);
            Scanner scan = new Scanner(f);

            while(scan.hasNextLine()){
                String s = scan.nextLine();
                if(scan.hasNextLine()){
                    M++;
                    if(N == 0)N = s.trim().split("\\s+").length-1;
                }
            }
            scan.close();

            if(M > 0 && N > 0){
                scan = new Scanner(f);
                EXPL = new Matriks(M, N);
                RESP = new Matriks(M, 1);

                int sampel = 0;
                while(scan.hasNextLine()){
                    String inpStr[] = scan.nextLine().trim().split("\\s+");
                    /// Baris terakhir adalah titik uji
                    if(!scan.hasNextLine()){
                        UJI = new float[inpStr.length];
                        for(int i = 0; i < UJI.length; i++)UJI[i] = Float.parseFloat(inpStr[i]);
                    }else{
                        /// Input titik sampel: nilai eksplanatorial
                        for(int var = 0; var < N; var++){
                            EXPL.set(sampel, var, Float.parseFloat(inpStr[var+1]));
                        }
                        /// Input titik sampel: nilai respons/observasi
                        RESP.set(sampel, 0, Float.parseFloat(inpStr[0]));
                        sampel++;
                    }
                }
                scan.close();
                MultLinearRegression(EXPL, RESP, UJI);
            }
        }catch(FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
