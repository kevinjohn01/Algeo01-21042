package matriks;

public class Bicubic {
    static float[][] X = {{1,-1,1,-1,-1,1,-1,1,1,-1,1,-1,-1,1,-1,1},{1,0,0,0,-1,0,0,0,1,0,0,0,-1,0,0,0},{1,1,1,1,-1,-1,-1,-1,1,1,1,1,-1,-1,-1,-1},{1,2,4,8,-1,-2,-4,-8,1,2,4,8,-1,-2,-4,-8},{1,-1,1,-1,0,0,0,0,0,0,0,0,0,0,0,0},{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},{1,2,4,8,0,0,0,0,0,0,0,0,0,0,0,0},{1,-1,1,-1,1,-1,1,-1,1,-1,1,-1,1,-1,1,-1},{1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0},{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},{1,2,4,8,1,2,4,8,1,2,4,8,1,2,4,8},{1,-1,1,-1,2,-2,2,-2,4,-4,4,-4,8,-8,8,-8},{1,0,0,0,2,0,0,0,4,0,0,0,8,0,0,0},{1,1,1,1,2,2,2,2,4,4,4,4,8,8,8,8},{1,2,4,8,2,4,8,16,4,8,16,32,8,16,32,64}};
    public static float[] bicubic(float[][] M){
        // ubah masukan menjadi array 1 dimensi (Y)
        float[] Y = new float[16];
        
        int k = 0;
        for(int j = 0; j < 4; j++){
            for(int i = 0; i < 4; i++){
                Y[k] = M[i][j];
                k++;
            }
        }

        // Y = Xa, a = X-1 Y
        // inverse X
        float[][] XI = matriksBalikan.Balikan(X);
    
        // kalikan dengan Y
        float[] a = new float[16];
        float sum = 0;

        for (int i = 0; i < 16; i++){
            sum = 0;
            for(int j = 0; j < 16; j++){
                sum = sum + XI[i][j]*Y[j];
            }
            a[i] = sum;
        }
        
        return a;
    }
}
