package matriks;

public class matriksBalikan {
    public static float[][] Balikan(float[][] M){
        int i,j, ii;
        float[][] MI = new float[M.length][M[0].length+M[0].length];

        // copy matriks awal
        for(i = 0; i < M.length; i++){
            for(j = 0; j < M[i].length; j++){
                MI[i][j] = M[i][j];
            }
        }

        // tambah matriks identitas di kanan matriks
        for(i = 0; i < M.length; i++){
            for(j = M.length; j < MI[i].length; j++){
                if (i == (j-M.length)){
                    MI[i][j] = 1;              
                }
                else
                    MI[i][j] = 0;
            }
        }

        // looping ubah matriks kiri menjadi identitas

        // membuat matriks segitiga atas
        float r;
        for(i = 0; i < MI.length; i++){
            // looping elemen di bawah MI[i][i] menjadi 0
            for(ii = i + 1; ii < MI.length; ii++){
                r = MI[ii][i]/MI[i][i];
                for(j = 0; j < MI[ii].length; j++){
                    MI[ii][j] = MI[ii][j] - r*MI[i][j];
                }
            }
        }

        float x;
        // menjadikan elemen diagonal menjadi 1
        for(i = 0; i < MI.length; i++){
            if (MI[i][i] != 1){
                x = MI[i][i];
                for(j = 0; j < MI[i].length; j++){
                    MI[i][j] = MI[i][j]/x; 
                    if (MI[i][j] == -0.0){
                        MI[i][j] = 0; // biar -0 jadi 0 (?)
                    }
                }
            }
        }

        // menjadikan elemen selain diagonal menjadi 0
        for(i = MI.length-1; i >= 0; i--){
            // looping elemen di atas MI[i][i] menjadi 0
            for(ii = i - 1; ii >= 0; ii--){
                r = MI[ii][i]/MI[i][i];
                for(j = 0; j < MI[ii].length; j++){
                    MI[ii][j] = MI[ii][j] - r*MI[i][j];
                }
            }
        }

        // simpan matriks baru
        float[][] Mhasil = new float[M.length][M[0].length];

        for(i = 0; i < MI.length; i++){
            for(j = 0; j < M[0].length; j++){
                Mhasil[i][j] = MI[i][j + M.length];
            }
        }

        return Mhasil;
    }

    public static float[] SPL(float[][] M, float[] b){
        float[] hasil = new float[M.length];
        float[][] Mbalikan = Balikan(M);

        float sum;
        for(int i = 0; i < M.length; i++){
            sum = 0;
            for(int j = 0; j < M.length; j++){
                sum = sum + Mbalikan[i][j]*b[j];
            }
            hasil[i] = sum;
        }

        return hasil;
    }
/*
    public static void main(String[] args){
        float M[][] = {{1,2,3},{2,5,3},{1,0,8}};
        float b[] = {5,3,1};
        float Mbalikan[][] = Balikan(M);

        for(int i = 0; i < Mbalikan.length; i++){
            for(int j = 0; j < Mbalikan[i].length; j++){
                System.out.print(Mbalikan[i][j] + " ");
            }
            System.out.println();
        }

        float hasil[] = SPL(M,b);

        for(int i = 0; i < Mbalikan.length; i++){
            System.out.print(hasil[i] + " ");
        }
    }
*/
}