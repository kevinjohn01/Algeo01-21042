package prog;

import java.util.Scanner;
import matriks.*;

public class Program{
    /* *** MAIN *** */
    public static void main(String[] args){
        boolean exit = false;
        Scanner input_menu = new Scanner(System.in);

        while(!exit){
            // menu utama
            System.out.println("MENU\n1. Sistem Persamaan Linier\n2. Determinan\n3. Matriks Balikan\n4. Interpolasi Polinom\n5. Interpolasi Bicubic\n6. Regresi Linier Berganda\n7. Keluar");
            
            // input menu
            System.out.print("Masukkan pilihan menu: ");
            
            int inp = Integer.parseInt(input_menu.nextLine());
            while (!(inp == 1 || inp == 2 || inp == 3 || inp == 4 || inp == 5 || inp == 6 || inp == 7)){
                System.out.print("Masukkan pilihan menu: ");
                inp = Integer.parseInt(input_menu.nextLine());
            }

            if (inp == 1){ // 1. Sistem Persamaan Linier
                // pilih submenu
                System.out.println("1. Metode Eliminasi Gauss\n2. Metode Eliminasi Gauss-Jordan\n3. Metode matriks balikan\n4. Kaidah Cramer");
                inp = Integer.parseInt(input_menu.nextLine());
                while(!(inp == 1 || inp == 2 || inp == 3 || inp == 4)){
                    System.out.print("Masukkan pilihan menu: ");
                    inp = Integer.parseInt(input_menu.nextLine());
                }

                if (inp == 1){ // 1. Metode Eliminasi Gauss

                }

                else if (inp == 2){ // 2. Metode Eliminasi Gauss-Jordan

                }

                else if (inp == 3){ // 3. Metode matriks balikan
                    

                }
                
                else if (inp == 4){ // 4. Kaidah Cramer

                }

            }

            else if (inp == 2){ // 2. Determinan
                // pilih submenu
                System.out.println("1. Metode reduksi baris\n2. Metode ekspansi kofaktor");
                inp = Integer.parseInt(input_menu.nextLine());
                while(!(inp == 1 || inp == 2)){
                    System.out.print("Masukkan pilihan menu: ");
                    inp = Integer.parseInt(input_menu.nextLine());
                }

                if (inp == 1){ // 1. Metode reduksi baris

                }

                else if (inp == 2){ // 2. Metode Ekspansi Kofaktor

                }
            }

            else if (inp == 3){ // 3. Matriks Balikan
                // pilih submenu
                System.out.println("1. Metode reduksi baris\n2. Metode adjoin");
                inp = Integer.parseInt(input_menu.nextLine());
                while(!(inp == 1 || inp == 2)){
                    System.out.print("Masukkan pilihan menu: ");
                    inp = Integer.parseInt(input_menu.nextLine());
                }

                if (inp == 1){ // 1. Metode reduksi baris

                }
                else if (inp == 2){ // 2. Metode adjoin

                }
            }

            else if (inp == 4){ // 4. Interpolasi Polinom

            }

            else if (inp == 5){ // 5. Interpolasi Bicubic

            }

            else if (inp == 6){ // 6. Regresi Linier Berganda

            }

            else if (inp == 7){ // 7. Keluar
                exit = true;
            }

            
        }
        input_menu.close();


        /*
        Scanner in = new Scanner(System.in);
        int SIZE = Integer.parseInt(in.nextLine());

        Matriks
            M = new Matriks(SIZE, SIZE),
            N = new Matriks(SIZE, 1);
        
        for(int i = 0; i < SIZE; i++){
            String[] inp = in.nextLine().split("\\s+");
            for(int j = 0; j < Math.min(SIZE, inp.length); j++){
                M.set(i,j, Float.parseFloat(inp[j]));
            }
        }
        String[] inp = in.nextLine().split("\\s+");
        float[] inpf = new float[inp.length];
        for(int i = 0; i < inpf.length; i++){
            inpf[i] = Float.parseFloat(inp[i]);
        }
        N.setRange(inpf);

        System.out.format("detexp: %f%n", M.determinantWExp());
        System.out.format("detcrm: %f%n", Cramer.determinan(M.toArray()));
        System.out.format("detred: %f%n", M.determinantWRed());
        System.out.println(M.inverseWAdj().toString());
        System.out.println(M.inverseWRed().toString());

        in.close();
        */
    }
}
