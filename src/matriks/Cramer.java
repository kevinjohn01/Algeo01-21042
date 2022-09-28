package matriks;

public class Cramer {
    public static float[] cramerAug (float[][] M) {
        float[] x = new float[M.length]; //untuk menyimpan nilai x yang merupakan penyelesaian
        float[] k = new float[M.length]; // untuk menyimpan nilai konstanta
        float[][] MI = new float[M.length][M[0].length-1]; //untuk menyimpan koefisien x
        int i,j;
        float  maindet;

        //copy matrix
        for (i=0; i< M.length; i++ ){
            for (j=0; j< M[0].length-1; j++) {
                MI[i][j] = M[i][j];
            }
        }

        //mengambil nilai konstanta
        for (i=0;i<M.length;i++){
            k[i] = M[i][M[0].length-1];
        }

        //determinan utama
        maindet = determinan(MI);
        //System.out.println(maindet);
        //menghitung nilai x0,x1,x2..., xn
        for (j=0; j< MI[0].length; j++) {
            x[j] = determinan(shiftMatrix(MI,k,j))/maindet;
        }
        return x;
    }

    public static float[] cramer (float[][] M, float[] k) {
        float[] x = new float[M.length]; //untuk menyimpan nilai x yang merupakan penyelesaian
        float[][] MI = new float[M.length][M[0].length-1]; //untuk menyimpan koefisien x
        int i,j;
        float  maindet;

        //copy matrix
        for (i=0; i< M.length; i++ ){
            for (j=0; j< M[0].length-1; j++) {
                MI[i][j] = M[i][j];
            }
        }

        //determinan utama
        maindet = determinan(MI);
        //System.out.println(maindet);
        //menghitung nilai x0,x1,x2..., xn
        for (j=0; j< MI[0].length; j++) {
            x[j] = determinan(shiftMatrix(MI,k,j))/maindet;
        }
        return x;
    }

    public static float determinan (float[][] M) {
        int j,i, ii,l, n,count, faktorpengali;
        float det, r, temp;
        det = 1;
        float[][] Mnew = new float[M.length][M[0].length];

        //copy matrix ke matrix baru
        for (i=0; i< M.length; i++ ){
            for (j=0; j< M[0].length; j++) {
                Mnew[i][j] = M[i][j];
            }
        }
        count = 0;
        //buat matrix segitiga atas
        for (i=0; i< Mnew.length; i++ ){
            for (ii = i+1; ii < Mnew.length; ii++) {
                r = 0;
                if (Mnew[i][i] !=0) {
                    r = Mnew[ii][i]/Mnew[i][i];
                }
                else {
                    //tukar posisi baris jika ditemukan elemen yang dicek pada diagonal utama = 0
                    for (l=i; l< Mnew.length-1;l++){
                        for(n=i; n< Mnew[0].length; n++){
                            temp = Mnew[l+1][n];
                            Mnew[l+1][n] = Mnew[l][n];
                            Mnew[l][n] = temp;
                    }
                    //menghitung berapa kali pertukaran baris dilakukan
                    count = count+1;
                }
                }
                for (j=0; j< Mnew[ii].length; j++) {  
                    Mnew[ii][j] = Mnew[ii][j] - r*Mnew[i][j];
                }
            }
        }
        faktorpengali=1;
        //menyimpan nilai determinan matrix
        for(i=0; i< Mnew.length; i++) {
            det = det*Mnew[i][i];
        }
        while (count!=0){
            faktorpengali = faktorpengali*(-1);
            count = count-1;
        }
        return det*faktorpengali;
    }

    public static float[][] shiftMatrix (float[][] M, float[] k,int n) {
        int i, j, l;
        float[][] Mnew = new float[M.length][M[0].length];
        l=0;
        //menukar setiap nilai di kolom ke n
        for (i=0; i< M.length; i++ ){
            for (j=0; j<M[i].length;j++){
                if (j==n) {
                    Mnew[i][j] = k[l];
                    l = l+1;
                }
                else {
                    Mnew[i][j] = M[i][j];
                }
            }
        }
        //for (i=0;i<Mnew.length;i++){
         //   for(j=0;j<Mnew[0].length;j++){
        //        System.out.print(Mnew[i][j] + " ");
        //    }
        //    System.out.println();
        //}
        return Mnew;
    }

    
}

