package prog;

import java.util.Scanner;
import matriks.*;

public class Program{
    /* *** MAIN *** */
    public static void main(String[] args){
        boolean exit = false;
        Scanner input_menu = new Scanner(System.in);
        Scanner f,in;
        int inp;
        String file;

        //while(!exit){
            // menu utama
            System.out.println("MENU\n1. Sistem Persamaan Linier\n2. Determinan\n3. Matriks Balikan\n4. Interpolasi Polinom\n5. Interpolasi Bicubic\n6. Regresi Linier Berganda\n7. Keluar");
            
            // input menu
            System.out.print("Masukkan pilihan menu: ");
            
            //input_menu = new Scanner(System.in);
            inp = Integer.parseInt(input_menu.nextLine());
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
                    System.out.println("Pilih masukan\n1. File\n2. Terminal");
                    inp = Integer.parseInt(input_menu.nextLine());
                    while(!(inp == 1 || inp == 2)){
                        System.out.println("Pilih masukan\n1. File\n2. Terminal");
                        inp = Integer.parseInt(input_menu.nextLine());
                    }
                    if (inp == 1){ // 1. File
                        System.out.print("Masukkan nama file: ");
                        file = input_menu.nextLine();
                        menu2.ReduksiBarisFile(file);
                        menu1.ElimGaussFile(file);
                    }
                    else{ // 2. Terminal
                        in = new Scanner(System.in);
                        System.out.println("Ax = b");
                        System.out.println("Input matriks A: ");
                        Matriks M = Matriks.readMatriks(in);
                        System.out.println("Input matriks b: ");
                        Matriks N = Matriks.readMatriks(in);
                        menu1.ElimGaussTerm(MatriksAug.from(M, N));
                        in.close();}

                }

                else if (inp == 2){ // 2. Metode Eliminasi Gauss-Jordan
                    System.out.println("Pilih masukan\n1. File\n2. Terminal");
                    inp = Integer.parseInt(input_menu.nextLine());
                    while(!(inp == 1 || inp == 2)){
                        System.out.println("Pilih masukan\n1. File\n2. Terminal");
                        inp = Integer.parseInt(input_menu.nextLine());
                    }
                    if (inp == 1){ // 1. File
                        System.out.print("Masukkan nama file: ");
                        file = input_menu.nextLine();
                        menu2.ReduksiBarisFile(file);
                        menu1.ElimGaussJordanFile(file);
                    }
                    else{ // 2. Terminal
                        in = new Scanner(System.in);
                        System.out.println("Ax = b");
                        System.out.println("Input matriks A: ");
                        Matriks M = Matriks.readMatriks(in);
                        System.out.println("Input matriks b: ");
                        Matriks N = Matriks.readMatriks(in);
                        menu1.ElimGaussJordanTerm(MatriksAug.from(M, N));
                        in.close();}


                }

                else if (inp == 3){ // 3. Metode matriks balikan
                    System.out.println("Pilih masukan\n1. File\n2. Terminal");
                    inp = Integer.parseInt(input_menu.nextLine());
                    while(!(inp == 1 || inp == 2)){
                        System.out.println("Pilih masukan\n1. File\n2. Terminal");
                        inp = Integer.parseInt(input_menu.nextLine());
                    }
                    if (inp == 1){ // 1. File
                        System.out.print("Masukkan nama file: ");
                        file = input_menu.nextLine();
                        menu2.ReduksiBarisFile(file);
                        menu1.MatriksBalikanFile(file);
                    }
                    else{ // 2. Terminal
                        in = new Scanner(System.in);
                        System.out.println("Ax = b");
                        System.out.println("Input matriks A: ");
                        Matriks M = Matriks.readMatriks(in);
                        System.out.println("Input matriks b: ");
                        Matriks N = Matriks.readMatriks(in);
                        menu1.MatriksBalikanTerm(MatriksAug.from(M, N));
                        in.close();}

                }
                
                else if (inp == 4){ // 4. Kaidah Cramer
                    System.out.println("Pilih masukan\n1. File\n2. Terminal");
                    inp = Integer.parseInt(input_menu.nextLine());
                    while(!(inp == 1 || inp == 2)){
                        System.out.println("Pilih masukan\n1. File\n2. Terminal");
                        inp = Integer.parseInt(input_menu.nextLine());
                    }
                    if (inp == 1){ // 1. File
                        System.out.print("Masukkan nama file: ");
                        file = input_menu.nextLine();
                        menu2.ReduksiBarisFile(file);
                        menu1.KaidahCramerFile(file);
                    }
                    else{ // 2. Terminal
                        in = new Scanner(System.in);
                        System.out.println("Ax = b");
                        System.out.println("Input matriks A: ");
                        Matriks M = Matriks.readMatriks(in);
                        System.out.println("Input matriks b: ");
                        Matriks N = Matriks.readMatriks(in);
                        menu1.KaidahCramerTerm(MatriksAug.from(M, N));
                        in.close();}
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
                    System.out.println("Pilih masukan\n1. File\n2. Terminal");
                    inp = Integer.parseInt(input_menu.nextLine());
                    while(!(inp == 1 || inp == 2)){
                        System.out.println("Pilih masukan\n1. File\n2. Terminal");
                        inp = Integer.parseInt(input_menu.nextLine());
                    }
                    if (inp == 1){ // 1. File
                        System.out.print("Masukkan nama file: ");
                        file = input_menu.nextLine();
                        menu2.ReduksiBarisFile(file);
                    }
                    else{ // 2. Terminal
                        in = new Scanner(System.in);
                        System.out.println("Input matriks: ");
                        Matriks M = Matriks.readMatriksSquare(in);
                        menu2.ReduksiBarisTerm(M);
                        in.close();}

                }

                else if (inp == 2){ // 2. Metode Ekspansi Kofaktor
                    System.out.println("Pilih masukan\n1. File\n2. Terminal");
                    inp = Integer.parseInt(input_menu.nextLine());
                    while(!(inp == 1 || inp == 2)){
                        System.out.println("Pilih masukan\n1. File\n2. Terminal");
                        inp = Integer.parseInt(input_menu.nextLine());
                    }
                    if (inp == 1){ // 1. File
                        System.out.print("Masukkan nama file: ");
                        file = input_menu.nextLine();
                        menu2.EkspansiFile(file);
                    }
                    else{ // 2. Terminal
                        in = new Scanner(System.in);
                        System.out.println("Input matriks: ");
                        Matriks M = Matriks.readMatriksSquare(in);
                        menu2.EkspansiTerm(M);
                        in.close();}

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
                    System.out.println("Pilih masukan\n1. File\n2. Terminal");
                    inp = Integer.parseInt(input_menu.nextLine());
                    while(!(inp == 1 || inp == 2)){
                        System.out.println("Pilih masukan\n1. File\n2. Terminal");
                        inp = Integer.parseInt(input_menu.nextLine());
                    }
                    if (inp == 1){ // 1. File
                        System.out.print("Masukkan nama file: ");
                        file = input_menu.nextLine();
                        menu3.ReduksiBarisFile(file);
                    }
                    else{ // 2. Terminal
                        in = new Scanner(System.in);
                        System.out.println("Input matriks: ");
                        Matriks M = Matriks.readMatriksSquare(in);
                        menu3.ReduksiBarisTerm(M);
                        in.close();}

                }
                else if (inp == 2){ // 2. Metode adjoin
                    System.out.println("Pilih masukan\n1. File\n2. Terminal");
                    inp = Integer.parseInt(input_menu.nextLine());
                    while(!(inp == 1 || inp == 2)){
                        System.out.println("Pilih masukan\n1. File\n2. Terminal");
                        inp = Integer.parseInt(input_menu.nextLine());
                    }
                    if (inp == 1){ // 1. File
                        System.out.print("Masukkan nama file: ");
                        file = input_menu.nextLine();
                        menu3.AdjoinFile(file);
                    }
                    else{ // 2. Terminal
                        in = new Scanner(System.in);
                        System.out.println("Input matriks: ");
                        Matriks M = Matriks.readMatriksSquare(in);
                        menu3.AdjoinTerm(M);
                        in.close();}

                }
            }

            else if (inp == 4){ // 4. Interpolasi Polinom
                System.out.println("Pilih masukan\n1. File\n2. Terminal");
                inp = Integer.parseInt(input_menu.nextLine());
                while(!(inp == 1 || inp == 2)){
                    System.out.println("Pilih masukan\n1. File\n2. Terminal");
                    inp = Integer.parseInt(input_menu.nextLine());
                }
                if (inp == 1){ // 1. File
                    System.out.print("Masukkan nama file: ");
                        file = input_menu.nextLine();
                        menu4.IntPolFile(file);
                }
                else{// 2. Terminal
                    in = new Scanner(System.in);
                        System.out.println("Input matriks: ");
                        Matriks M = Matriks.readMatriks(in);
                        menu4.IntPolTerm(M);
                } 
                    // pilih submenu
                    /*
                    System.out.println("1. Test Case 1\n2. Test Case 2\n3. Test Case 3");
                    inp = Integer.parseInt(input_menu.nextLine());
                    while(!(inp == 1 || inp == 2 || inp == 3)){
                        System.out.println("Masukkan pilihan menu: ");
                        inp = Integer.parseInt(input_menu.nextLine());;
                    }   

                    if (inp == 1){ // 1. Test Case 1
                        intPolinom.tabel();
                    }
                    else if (inp == 2){ // 2. Test Case 2
                        intPolinom.covid();
                    }
                    else if (inp == 3){ // 2. Test Case 3
                        intPolinom.simplification();
                    }                                
                    */
            }

            else if (inp == 5){ // 5. Interpolasi Bicubic
                System.out.println("Pilih masukan\n1. File\n2. Terminal");
                    inp = Integer.parseInt(input_menu.nextLine());
                    while(!(inp == 1 || inp == 2)){
                        System.out.println("Pilih masukan\n1. File\n2. Terminal");
                        inp = Integer.parseInt(input_menu.nextLine());
                    }
                    if (inp == 1){ // 1. File
                        System.out.print("Masukkan nama file: ");
                        file = input_menu.nextLine();
                        menu5.BicubicFile(file);
                    }
                    else{ // 2. Terminal
                        in = new Scanner(System.in);
                        System.out.println("Input matriks: ");
                        Matriks M = Matriks.readMatriks(in);
                        menu5.BicubicTerm(M);
                        in.close();
                    } 

            }

            else if (inp == 6){ // 6. Regresi Linier Berganda
                System.out.println("Pilih masukan\n1. File\n2. Terminal");
                    inp = Integer.parseInt(input_menu.nextLine());
                    while(!(inp == 1 || inp == 2)){
                        System.out.println("Pilih masukan\n1. File\n2. Terminal");
                        inp = Integer.parseInt(input_menu.nextLine());
                    }
                    if (inp == 1){ // 1. File
                        System.out.print("Masukkan nama file: ");
                        file = input_menu.nextLine();
                        RegresiLinearBerganda.MultLinearRegressionFile(file);
                    }
                    else{ // 2. Terminal
                        in = new Scanner(System.in);
                        System.out.print("Banyak peubah X: "); int N = Integer.parseInt(in.nextLine());
                        System.out.print("Banyak sampel: "); int M = Integer.parseInt(in.nextLine());
                        Matriks EXPL = new Matriks(M, N);
                        Matriks RESP = new Matriks(M, 1);
                        String inpStr[];
                        /// Input titik sampel: nilai eksplanatorial
                        for(int sampel = 0; sampel < M; sampel++){
                            System.out.format("Titik sampel ke-%d (format \"x1i x2i ... xni\"): ", sampel+1);
                            inpStr = in.nextLine().trim().split("\\s+");
                            for(int var = 0; var < N; var++){
                                EXPL.set(sampel, var, Float.parseFloat(inpStr[var]));
                            }
                        }
                        /// Input titik sampel: nilai respons/observasi
                        for(int sampel = 0; sampel < M; sampel++){
                            System.out.format("Nilai respons ke-%d: ", sampel+1);
                            RESP.set(sampel, 0, Float.parseFloat(in.nextLine()));
                        }
                        /// Input nilai-nilai tes x_k
                        System.out.print("Nilai X uji (format \"x1 x2 ... xn\"): ");
                        inpStr = in.nextLine().trim().split("\\s+");
                        float UJI[] = new float[inpStr.length];
                        for(int i = 0; i < UJI.length; i++)UJI[i] = Float.parseFloat(inpStr[i]);
                        /// Proses
                        RegresiLinearBerganda.MultLinearRegression(EXPL, RESP, UJI);
                        in.close();
                    }

            }

            else if (inp == 7){ // 7. Keluar
                exit = true;
            }

            
        //}

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
    }}

