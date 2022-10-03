package prog;

import matriks.*;

import java.util.Scanner;

import matriks.InterpolasiPolinom;
import matriks.spl.SolusiSPL;

/*
 * Submenu dari interpolasi polinom untuk menyelesaikan test case yang diminta
 */

public class intPolinom {
    public static void tabel(){
        Scanner input = new Scanner(System.in);

        System.out.print("x = ");
        float n = input.nextFloat();
        float y = InterpolasiPolinom.nilaifungsi(InterpolasiPolinom.interpolasipolinom(), n);
        System.out.print("f(x) = ");
        System.out.print(y);
        input.close();
    }

    public static void covid() {
        Scanner input = new Scanner(System.in);
        int dd = input.nextInt();
        int mm = input.nextInt();
        int hari;
        if ((mm==1) || (mm==3) || (mm==5) || (mm==7) || (mm==8) || (mm==10) || (mm==12)){
            hari = 31;
        }
        else if (mm==2){
            hari = 29;
        }
        else {
            hari = 30;
        }
        float tanggal = mm + (dd /(hari));
        SolusiSPL k = InterpolasiPolinom.interpolasipolinom();
        float hasil = InterpolasiPolinom.nilaifungsi(k,tanggal);
        System.out.print(hasil);
        input.close();

    }

    public static void simplification() {
        float curr_elmt;
        SolusiSPL k;
        
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        Matriks MI = new Matriks(n,2);
        float dx = 2/n;
        float curr_x = 0;
        for ( int i = 0; i<=n; i++){
            curr_elmt = function(curr_x);
            MI.set(i,1,curr_elmt);
            MI.set(i,0,curr_x);
            curr_x = curr_x + dx;
        }
        k = InterpolasiPolinom.interpolasipolinom(MI);
        InterpolasiPolinom.printfungsi(k);
        input.close();
    }

    public static float function(float x) {
        double hasil;
        double e = 2.71828182845904523536028747135266249;
        hasil = (x*x + Math.pow(x,0.5))/(Math.pow(e,x) + x);
        return (float) hasil; //belum diimplementasikan fungsinya 
    }
}
