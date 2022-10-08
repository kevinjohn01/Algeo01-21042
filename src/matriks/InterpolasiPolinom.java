package matriks;

import java.util.Scanner;

import matriks.spl.SolusiSPL;

public class InterpolasiPolinom {
    public static SolusiSPL interpolasipolinom() {
        float value, curr_val;
        Scanner input = new Scanner(System.in);

        //input jumlah titik
        String n = input.nextLine();
        int m = Integer.parseInt(n);
        //menyimpan titik ke matriks
        Matriks MI = new Matriks(m,2);
        Matriks M = new Matriks(m,m); //menyimpan nilai yang akan dihitung spl nya

        for (int i=0; i<m; i++){
            for(int j=0; j<2; j++) {
                String val = input.nextLine();
                value = Float.parseFloat(val);
                MI.set(i,j,value);
            }
        }

        //menyimpan nilai x dari titik untuk dimasukkan ke dalam matriks
        for (int i=0; i<m; i++){
            curr_val = 1;
            value = MI.get(i,0);
            for (int ii=0; ii<m; ii++) {
                //System.out.print(curr_val + " ");
                M.set(i,ii,curr_val);
                curr_val = curr_val*value;
            }
        }
        
        System.out.println(M.toString());

        //mengambil nilai konstanta
        float[] l= new float[m];
        for (int i=0; i<m; i++) {
            l[i] = MI.get(i,1);
        }

        //menghitung spl dari matriks aug
        SolusiSPL k;
        k = (MatriksAug.from(M, Matriks.colMat(l))).elimGaussJordan();
        //k = Cramer.cramer(M, l);
        input.close();
        return k;
    }

    public static SolusiSPL interpolasipolinom(Matriks MI) {
        float curr_val,val;
        Matriks M = new Matriks(MI.bar,MI.bar); //menyimpan nilai yang akan dihitung spl nya
        //menyimpan nilai x dari titik untuk dimasukkan ke dalam matriks
        for (int i=0; i<MI.bar; i++){
            curr_val = 1;
            val = MI.get(i,0);
            for (int ii=0; ii<MI.bar; ii++) {
                M.set(i,ii,curr_val);
                curr_val = curr_val*val;
            }
        }            
    
        //mengambil nilai konstanta
        float[] l= new float[MI.bar];
        for (int i=0; i<MI.bar; i++) {
            l[i] = MI.get(i,1);
        }
        //System.out.println(M.toString());
        //menghitung spl dari matriks aug
        SolusiSPL k;
        k = (MatriksAug.from(M, Matriks.colMat(l))).elimGaussJordan();
        //k = Cramer.cramer(MI, l);
        //System.out.println(k.toString());
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
        System.out.println(k.get(0)); //konstanta
    }

    public static float nilaifungsi(SolusiSPL k, float n){
        // input x
        float x = n;
        float curr_x = 1;
        float sum=0;
        for (int i=0; i< k.numVariable() ; i++) {
            sum += k.get(i)*curr_x;
            curr_x *= x;
        }
        return sum;
    }
}
