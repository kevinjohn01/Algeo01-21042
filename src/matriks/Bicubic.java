package matriks;
import java.lang.Math;

public class Bicubic {
    static float[][] X = {{1,-1,1,-1,-1,1,-1,1,1,-1,1,-1,-1,1,-1,1},{1,0,0,0,-1,0,0,0,1,0,0,0,-1,0,0,0},{1,1,1,1,-1,-1,-1,-1,1,1,1,1,-1,-1,-1,-1},{1,2,4,8,-1,-2,-4,-8,1,2,4,8,-1,-2,-4,-8},{1,-1,1,-1,0,0,0,0,0,0,0,0,0,0,0,0},{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},{1,2,4,8,0,0,0,0,0,0,0,0,0,0,0,0},{1,-1,1,-1,1,-1,1,-1,1,-1,1,-1,1,-1,1,-1},{1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0},{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},{1,2,4,8,1,2,4,8,1,2,4,8,1,2,4,8},{1,-1,1,-1,2,-2,2,-2,4,-4,4,-4,8,-8,8,-8},{1,0,0,0,2,0,0,0,4,0,0,0,8,0,0,0},{1,1,1,1,2,2,2,2,4,4,4,4,8,8,8,8},{1,2,4,8,2,4,8,16,4,8,16,32,8,16,32,64}};
    static Matriks XI = (Matriks.from(X)).inverseWRed();
    public static Matriks bicubic(Matriks M){
        // ubah masukan menjadi array 1 dimensi (Y)
        float[] Y = new float[16];
        
        int k = 0;
        for(int j = 0; j < 4; j++){
            for(int i = 0; i < 4; i++){
                Y[k] = M.get(i,j);
                k++;
            }
        }

        // Y = Xa, a = X-1 Y
    
        // kalikan dengan Y
        Matriks a = new Matriks(16,1);
        float sum = 0;

        for (int i = 0; i < 16; i++){
            sum = 0;
            for(int j = 0; j < 16; j++){
                sum = sum + XI.get(i,j)*Y[j];
            }
            a.set(i,0,sum);
        }
        
        return a;
    }

    public static float hasilbic(Matriks a, double x, double y){
        float sum = 0;
        int k = 0;
        for(int j = 0; j < 4; j++){
            for(int i = 0; i < 4; i++){
                sum = sum + a.get(k,0) * (float)Math.pow(x,i) * (float)Math.pow(y,j);
                k++;
            }
        }
        return sum;
    }
}
