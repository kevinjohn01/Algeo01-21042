package matriks;

import java.util.Scanner;

import matriks.spl.SolusiSPL;

public class InterpolasiPolinom {
    public static SolusiSPL interpolasipolinom() {
        float val, curr_val;
        Scanner input = new Scanner(System.in);

        //input jumlah titik
        int n = input.nextInt();

        //menyimpan titik ke matriks
        Matriks MI = new Matriks(n,2);
        Matriks M = new Matriks(n,n); //menyimpan nilai yang akan dihitung spl nya

        for (int i=0; i<n; i++){
            for(int j=0; j<2; j++) {
                val = input.nextFloat();
                MI.set(i,j,val);

            }
        }

        //menyimpan nilai x dari titik untuk dimasukkan ke dalam matriks
        for (int i=0; i<n; i++){
            curr_val = 1;
            val = MI.get(i,0);
            int jj=0;
            for (int ii=0; ii<n; ii++) {
                M.set(ii,jj,curr_val);
                curr_val = curr_val*val;
                jj = jj+1;
            }
        }
        
    
        //mengambil nilai konstanta
        float[] l= new float[n];
        for (int i=0; i<n; i++) {
            l[i] = MI.get(i,1);
        }

        //menghitung spl dari matriks aug
        SolusiSPL k;
        k = Cramer.cramer(M, l);
        input.close();
        return k;
    }

    public static void printfungsi (SolusiSPL k){       
        //inisialisasi nilai pangkat
        int pangkat = k.numVariable()-1;

        //print fungsi
        System.out.print("f(x) = ");
        for(int i=k.numVariable()-1; i>0 ; i--) {
            System.out.print(k.get(i)); //koefisien
            System.out.print("x"  ); //variabel
            if (pangkat >1) {
                System.out.print("^" + pangkat); //eksponen x
            }
            System.out.print(" ");
            pangkat --;
        }
        System.out.print(k.get(0)); //konstanta
    }

    public static float nilaifungsi(SolusiSPL k){
        Scanner input = new Scanner(System.in);
        // input x
        float x = input.nextFloat();
        float curr_x = 1;
        float sum=0;
        for (int i=0; i< k.numVariable() ; i++) {
            sum += k.get(i)*curr_x;
            curr_x *= x;
        }
        input.close();
        return sum;
    }
}
