package prog;
import java.util.Scanner;
import matriks.util.*;

public class tesIntPol {
    public static void main(String args[]) {
        int inp;
        Scanner input_menu = new Scanner (System.in);
        System.out.println("Studi Kasus Interpolasi\n1. Test Case 1\n2. Test Case 2\n3. Test Case 3");
                    inp = Integer.parseInt(input_menu.nextLine());
                    while(!(inp == 1 || inp == 2 || inp == 3)){
                        System.out.println("Masukkan pilihan menu: ");
                        inp = Integer.parseInt(input_menu.nextLine());;
                    }   

                    if (inp == 1){ // 1. Test Case 1
                        System.out.println("x = 0.4    f(x) = 0.043");
                        System.out.println("x = 0.7    f(x) = 0.005");
                        System.out.println("x = 0.11   f(x) = 0.058");
                        System.out.println("x = 0.14   f(x) = 0.072");
                        System.out.println("x = 0.17   f(x) = 0.1");
                        System.out.println("x = 0.2    f(x) = 0.13");
                        System.out.println("x = 0.23   f(x) = 0.147");
                        intPolinom.tabel();
                    }
                    else if (inp == 2){ // 2. Test Case 2
                        intPolinom.covid();
                    }
                    else if (inp == 3){ // 2. Test Case 3
                        intPolinom.simplification();
                    }     
    }
}
