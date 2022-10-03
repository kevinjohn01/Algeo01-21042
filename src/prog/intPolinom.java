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
        float[][] MI = new float[][] {{0.4f,0.043f},{0.7f,0.005f},{0.11f,0.058f},{0.14f,0.072f},{0.17f,0.1f},{0.2f,0.13f},{0.23f,0.147f}};
        //float[][] MI = new float[][] {{1,1},{2,2},{3,3},{4,4}};
        Matriks M = Matriks.from(MI);
        System.out.print("x = ");
        String m = input.nextLine();
        float n = Float.parseFloat(m);
        float y = InterpolasiPolinom.nilaifungsi(InterpolasiPolinom.interpolasipolinom(M), n);
        System.out.print("f(x) = ");
        System.out.print(y);
        input.close();
    }

    public static void covid() {
        Scanner input = new Scanner(System.in);
        String d = input.nextLine();
        String m = input.nextLine();
        int dd = Integer.parseInt(d);
        int mm = Integer.parseInt(m);
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
        float tanggal = mm + ((float) dd /(hari));
        //System.out.println(tanggal);
        float[][] MI = new float[][] {{6.567f,12624},{7f,21807},{7.258f,38391},{7.451f,54517},{7.548f,51952},{7.839f,28228},{8.161f,35764},{8.484f,20813},{8.709f,12408},{9f,10534}};
        Matriks M = Matriks.from(MI);
        SolusiSPL k = InterpolasiPolinom.interpolasipolinom(M);
        float hasil = InterpolasiPolinom.nilaifungsi(k,tanggal);
        System.out.print(hasil);
        input.close();

    }

    public static void simplification() {
        float curr_elmt;
        SolusiSPL k;
        
        Scanner input = new Scanner(System.in);
        String m = input.nextLine();
        int n = Integer.parseInt(m);
        Matriks MI = new Matriks(n,2);
        float dx = 2/n;
        float curr_x = 0;
        for ( int i = 0; i<n; i++){
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
        double hasil, pembilang, penyebut;
        double e = 2.71828182845904523536028747135266249;
        pembilang =((double)x*x) + Math.pow(x,0.5);
        penyebut = Math.pow(e,x) + ((double) x);
        //System.out.println(pembilang);
        //System.out.println(penyebut);
        hasil = (pembilang)/(penyebut);
        float hasilf = (float) hasil;
        //System.out.println(hasil);
        return (float) hasilf; 
    }
}
