package matriks;

public class matriksBalikan {
    /* Original code -- Manuella Ivana Uli Sianipar */
    public static Matriks Balikan(Matriks M){
        int i,j, ii;
        // Matriks MI = new Matriks(M.length,M[0].length+M[0].length);
        // copy matriks awal
        /*for(i = 0; i < M.length; i++){
            for(j = 0; j < M[i].length; j++){
                MI[i][j] = M[i][j];
            }
        }
        */
        Matriks MI = new Matriks(2*M.bar, 2*M.kol);

        // input nilai matriks awal
        for(i = 0; i < M.bar; i++){
            for(j = 0; j < M.kol; j++){
                MI.set(i,j,M.get(1,j));
            }
        }

        // tambah matriks identitas di kanan matriks
        for(i = 0; i < M.bar; i++){
            for(j = M.kol; j < MI.kol; j++){
                if (i == (j-M.bar)){
                    MI.set(i,j,1);              
                }
                else
                    MI.set(i,j,0);
            }
        }

        // looping ubah matriks kiri menjadi identitas

        // membuat matriks segitiga atas
        float r;
        for(i = 0; i < MI.bar; i++){
            // looping elemen di bawah MI[i][i] menjadi 0
            for(ii = i + 1; ii < MI.bar; ii++){
                r = MI.get(ii,i)/MI.get(i,i);
                for(j = 0; j < MI.kol; j++){
                    MI.set(ii,j,MI.get(ii,j) - r*MI.get(i,j));
                }
            }
        }

        float x;
        // menjadikan elemen diagonal menjadi 1
        for(i = 0; i < MI.bar; i++){
            if (MI.get(i,i) != 1){
                x = MI.get(i,i);
                for(j = 0; j < MI.kol; j++){
                    MI.set(i,j,MI.get(i,j)/x); 
                    if (MI.get(i,j) == -0.0){
                        MI.set(i,j,0); // biar -0 jadi 0 (?)
                    }
                }
            }
        }

        // menjadikan elemen selain diagonal menjadi 0
        for(i = MI.bar-1; i >= 0; i--){
            // looping elemen di atas MI[i][i] menjadi 0
            for(ii = i - 1; ii >= 0; ii--){
                r = MI.get(ii,i)/MI.get(i,i);
                for(j = 0; j < MI.kol; j++){
                    MI.set(ii,j,MI.get(ii,j) - r*MI.get(i,j));
                }
            }
        }

        // simpan matriks baru
        Matriks Mhasil = new Matriks(M.bar,M.kol);

        for(i = 0; i < MI.bar; i++){
            for(j = 0; j < M.kol; j++){
                Mhasil.set(i,j,MI.get(i,j + M.bar));
            }
        }

        return Mhasil;
    }
/*
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