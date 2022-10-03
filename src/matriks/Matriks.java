package matriks;

import java.util.Scanner;

import matriks.util.*;
import static matriks.util.Constants.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import static matriks.util.Format.*;

public class Matriks implements IMatriks{
    // Konstanta pembantu
    int IDX_RIGHTMOST;
    
    /* *** KONSTANTA *** */
    // zero -- Mengembalikan matriks nol, yaitu matriks di mana semua elemennya adalah 0
    public static Matriks zero(int baris, int kolom){
        if(ZERO == null || ZERO.BARIS() != baris || ZERO.KOLOM() != kolom){
            ZERO = new Matriks(baris, kolom);
            ZERO.each((i,j) -> ZERO.set(i, j, 0f));
        }
        return ZERO;
    }
    // identity -- Mengembalikan matriks identitas, yaitu matriks berelemen 1 di diagonal utama, dan 0 di tempat lainnya
    public static Matriks identity(int len){
        if(IDENT == null || IDENT.BARIS() != len){
            IDENT = new Matriks(len);
            IDENT.each((i,j) -> IDENT.set(i, j, i==j ? 1f : 0f));
        }
        return IDENT;
    }

    /* *** PROPERTI *** */
    final float[][] mat;
    final int bar, kol;

    /* *** KONSTRUKTOR *** */
    public Matriks(int len) throws IllegalArgumentException{
        if(len <= 0)throw new IllegalArgumentException("Dimensi matriks harus positif");
        bar = len;
        kol = len;

        mat = new float[len][len];

        IDX_RIGHTMOST = len-1;
    }
    public Matriks(int baris, int kolom) throws IllegalArgumentException{
        if(baris <= 0 || kolom <= 0)throw new IllegalArgumentException("Dimensi matriks harus positif");
        bar = baris;
        kol = kolom;

        mat = new float[BARIS()][KOLOM()];

        IDX_RIGHTMOST = KOLOM()-1;
    }

    /* *** METODE INISIALISASI *** */
    // from -- Membuat matriks dari array satu dimensi, ukuran diinputkan
    public static Matriks from(float[] arr, int baris, int kolom){
        Matriks M = new Matriks(baris, kolom);
        M.setRange(arr);
        return M;
    }
    // from -- Membuat matriks dari array dua dimensi, ukuran inferred
    public static Matriks from(float[][] arr){
        /// Handle kasus array kosong
        int baris = arr.length;
        if(baris == 0)return null;
        int kolom = arr[0].length;
        if(kolom == 0)return null;
        
        Matriks M = new Matriks(baris, kolom);
        M.each((i,j) -> M.set(i,j, arr[i][j]));
        return M;
    }
    // rowMat -- Membuat matriks baris dari array satu dimensi
    public static Matriks rowMat(float[] arr){
        return from(arr, 1, arr.length);
    }
    // colMat -- Membuat matriks kolom dari array satu dimensi
    public static Matriks colMat(float[] arr){
        return from(arr, arr.length, 1);
    }

    /* *** AKSESOR *** */
    // Aksesor dimensi matriks
    public int BARIS(){return bar;}
    public int KOLOM(){return kol;}
    
    // Aksesor elemen matriks
    public float get(int baris, int kolom){
        return mat[baris][kolom];
    }
    public void set(int baris, int kolom, float val){
        mat[baris][kolom] = val;
    }
    public void setRange(float... val){
        int k = 0, p = 0, q = 0;
        while(k < val.length && k < BARIS() * KOLOM()){
            set(p,q, val[k]);
            k++; q++;
            if(q >= KOLOM()){
                q -= KOLOM();
                p++;
            }
        }
    }
    public void setAll(MatFunc func){
        each((i,j) -> set(i,j, func.get(i,j)));
    }

    /* *** PREDIKAT *** */
    // isSquare -- Mengembalikan true jika matriks persegi, false sebaliknya
    public boolean isSquare(){
        return BARIS() == KOLOM();
    }
    // isEquivalent -- Mengembalikan true jika dimensi kedua matriks sama, false sebaliknya
    public boolean isEquivalent(Matriks other){
        return BARIS() == other.BARIS() && KOLOM() == other.KOLOM();
    }
    // isSingular -- Mengembalikan true jika matriks singular (tidak memiliki invers), false sebaliknya
    public boolean isSingular(){
        return determinant() == 0f;
    }
    // isInvertible -- Mengembalikan true jika matriks dapat diinvers, false sebaliknya
    public boolean isInvertible(){
        return isSquare() && !isSingular();
    }
    // canMultiply -- Mengembalikan true jika matriks dapat mengalikan matriks other, false sebaliknya
    public boolean canMultiply(Matriks other){
        return KOLOM() == other.BARIS();
    }
    // isRowEchelon -- Mengembalikan true jika matriks dalam bentuk eselon baris, false sebaliknya
    public boolean isRowEchelon(){
        int depth = 0;
        for(int i = 0; i < BARIS(); i++){
            int leading = idxLead(i);
            if(leading <= depth)return false;
            depth = leading;
        }
        return true;
    }
    // isIdentity -- Mengembalikan true jika matriks adalah matriks identitas, false sebaliknya
    public boolean isIdentity(){
        if(!isSquare())return false;
        for(int i = 0; i < BARIS(); i++){
            for(int j = 0; j < KOLOM(); j++){
                if(i==j ? get(i,j) != 1f : get(i,j) != 0f)return false;
            }
        }
        return true;
    }

    /* *** ARITMATIKA *** */
    // sum -- Mengembalikan jumlah dua matriks jika dimensi kedua matriks sama, null sebaliknya
    public Matriks sum(Matriks other){
        if(!isEquivalent(other))return null;
        Matriks R = copy();
        each((i,j) -> R.set(i,j, get(i,j) + other.get(i,j)));
        return R;
    }
    // diff -- Mengembalikan selisih dua matriks jika dimensi kedua matriks sama, null sebaliknya
    public Matriks diff(Matriks other){
        if(!isEquivalent(other))return null;
        Matriks R = copy();
        each((i,j) -> R.set(i,j, get(i,j) - other.get(i,j)));
        return R;
    }
    // scale -- Mengembalikan hasil kali matriks dengan suatu bilangan skalar
    public Matriks scale(float s){
        Matriks R = copy();
        R.each((i,j) -> R.set(i,j, R.get(i,j)*s));
        return R;
    }
    // product -- Mengembalikan hasil kali dua matriks jika dimensi valid, null sebaliknya
    public Matriks product(Matriks other){
        if(!canMultiply(other))return null;
        Matriks R = zero(BARIS(), other.KOLOM());
        R.each((i,j) ->
            barisEach(i, (a,b) -> {
                R.set(i,j, R.get(i,j) + get(i,b) * other.get(b,j));
            })
        );
        return R;
    }
    // diagProd -- Mengembalikan hasil kali diagonal utama matriks jika matriks persegi, NaN sebaliknya
    public float diagProd(){
        if(!isSquare())return Float.NaN;
        float prod = 1f;
        for(int i = 0; i < BARIS(); i++){
            prod *= get(i,i);
        }
        return prod;
    }
    // transpose -- Mengembalikan matriks transpose, matriks dicerminkan terhadap diagonal utama
    public Matriks transpose(){
        Matriks T = new Matriks(KOLOM(), BARIS());
        each((i,j) -> T.set(j,i, get(i,j)));
        return T;
    }
    // getSub -- Mengembalikan submatriks dari elemen (bar1, kol1) hingga (bar2, kol2) jika bar1, bar2, kol1, kol2 valid, null sebaliknya
    public Matriks getSub(int bar1, int kol1, int bar2, int kol2){
        if(!idxInMatriks(bar1, kol1) || !idxInMatriks(bar2, kol2))return null;
        if(bar2 < bar1){int temp = bar2; bar2 = bar1; bar1 = temp;}
        if(kol2 < kol1){int temp = kol2; kol2 = kol1; kol1 = temp;}

        Matriks S = new Matriks(bar2 - bar1 + 1, kol2 - kol1 + 1);
        for(int i = bar1; i <= bar2; i++){
            for(int j = kol1; j <= kol2; j++){
                S.set(i - bar1, j - kol1, get(i,j));
            }
        }
        return S;
    }
    // shift -- Mengembalikan matriks dengan kolom ke-j digantikan dengan elemen matriks kolom K
    public Matriks shift(Matriks K, int j){
        // Tidak melakukan shift jika K bukan matriks kolom dan dimensi tidak sesuai
        if(K.KOLOM() != 1 || BARIS() != K.BARIS())return null;

        Matriks S = copy();
        // Ganti elemen kolom j dengan elemen K
        for(int i = 0; i < BARIS(); i++){
            S.set(i,j, K.get(i,0));
        }
        return S;
    }
    // toRowEchelon -- Mengubah matriks menjadi bentuk row echelon (leading tidak harus 1), mengembalikan true jika tidak ada baris inkonsisten
    public boolean toRowEchelon(){
        boolean singular = false;
        for(int i = 0; i < BARIS(); i++){
            int j = idxLead(i);
            // Matriks singular jika ada baris nol
            singular = j == IDX_UNDEF;
            // Jika j != i, tukar baris dengan baris lain dengan leading elemen indeks terkecil, balik tanda untuk mempreservasi determinan
            if(j != i){
                int leadBar = IDX_UNDEF;
                j = KOLOM();
                for(int k = i+1; k < BARIS(); k++){
                    int currLeadIdx = idxLead(k);
                    if(currLeadIdx < j){
                        leadBar = k;
                        j = currLeadIdx;
                    }
                }
                if(leadBar != IDX_UNDEF){
                    swapBaris(i, leadBar);
                    sclBaris(i, -1f);
                }
            }
            j = idxLead(i);
            // Lakukan operasi jika baris non-0
            if(j != IDX_UNDEF){
                // Eliminasi elemen kolom j di bawahnya
                for(int k = i+1; k < BARIS(); k++){
                    int l = idxLead(k);
                    if(j==l){
                        addBaris(k, i, -get(k,l)/get(i,l));
                    }
                }
            }
        }
        return !singular;
    }
    // toReducedRowEchelon -- Mengubah matriks menjadi bentuk reduced row echelon (leading tidak harus 1), mengembalikan true jika tidak ada baris inkonsisten
    public boolean toReducedRowEchelon(){
        boolean singular = !toRowEchelon();
        for(int i = BARIS()-1; i >= 0; i--){
            int j = idxLead(i);
            // Jika ada baris inkonsisten, maka SPL tidak memiliki solusi
            singular = j != IDX_UNDEF;
            for(int k = i-1; k >= 0; k--){
                // Lakukan operasi jika baris valid
                if(j != IDX_UNDEF){
                    // Eliminasi elemen kolom j di atasnya
                    float x = get(k,j)/get(i,j);
                    if(x != 0f){
                        addBaris(k, i, -x);
                    }
                }
            }
        }
        return !singular;
    }
    // idxLead -- Mengembalikan indeks elemen non-0 pertama di suatu baris, IDX_UNDEF jika tidak ada
    public int idxLead(int baris){
        for(int j = 0; j < KOLOM(); j++){
            if(get(baris,j) != 0f)return j;
        }
        return IDX_UNDEF;
    }
    // numBarNol -- Mengembalikan banyak baris nol dalam matriks
    public int numBarNol(){
        int c = 0;
        for(int i = 0; i < BARIS(); i++){
            if(isBarisNol(i))c++;
        }
        return c;
    }

    /* *** DETERMINAN *** */
    // determinant -- Mengembalikan nilai determinan matriks jika matriks persegi, NaN sebaliknya, menggunakan metode default yang ditentukan
    public float determinant(){
        return determinantWRed();
    }
    // determinantWExp -- Mengembalikan nilai determinan matriks jika matriks persegi, NaN sebaliknya, menggunakan definisi rekursif ekspansi Laplace
    public float determinantWExp(){
        /// BASIS: Determinan matriks satu elemen adalah elemen itu sendiri
        if(!isSquare())return Float.NaN;
        if(BARIS() == 1)return get(0,0);
        /// REKURENS: Determinan matriks didefinisikan menurut ekspansi Laplace sebagai jumlah dari elemen * kofaktor sepanjang salah satu baris
        float det = 0f;
        for(int i = 0; i < KOLOM(); i++)det += get(0, i) * cofactor(0, i);
        return det;
    }
    // determinantWRed -- Mengembalikan nilai determinan matriks jika matriks persegi, NaN sebaliknya, menggunakan metode reduksi baris
    public float determinantWRed(){
        Matriks M = copy();
        M.toRowEchelon();
        return M.diagProd();
    }
    // minor -- Mengembalikan minor, yaitu determinan dari suatu matriks hasil penghapusan salah satu baris dan kolom
    public float minor(int baris, int kolom){
        if(!isSquare())return Float.NaN;
        Matriks m = new Matriks(BARIS()-1, KOLOM()-1);
        m.each((i,j) -> m.set(i,j, get(i<baris ? i : i+1, j<kolom ? j : j+1)));
        return m.determinantWExp();
    }
    // cofactor -- Mengembalikan kofaktor matriks di suatu baris dan kolom
    public float cofactor(int baris, int kolom){
        return (float)Math.pow(-1f, baris+kolom) * minor(baris, kolom);
    }
    // adjugate -- Mengembalikan matriks adjoin (transpose dari matriks kofaktor) dari suatu matriks jika matriks persegi, null sebaliknya
    public Matriks adjugate(){
        if(!isSquare())return null;
        Matriks A = new Matriks(BARIS());
        A.each((i,j) -> A.set(j,i, cofactor(i,j)));
        return A;
    }

    /* *** INVERS *** */
    // inverse -- Mengembalikan invers dari suatu matriks jika matriks memiliki invers, null sebaliknya, dihitung dengan metode default yang ditentukan
    public Matriks inverse(){
        return inverseWRed();
    }
    // inverseWAdj -- Mengembalikan invers dari suatu matriks jika matriks memiliki invers, null sebaliknya, dihitung dengan matriks adjugat
    public Matriks inverseWAdj(){
        if(!isInvertible())return null;
        // Hitung invers sebagai 1/det * adj
        // Implementasi ini lambat, sebaiknya menggunakan metode eliminasi Gauss
        return adjugate().scale(1f / determinantWExp());
    }
    // inverseWReduct -- Mengembalikan invers dari suatu matriks jika matriks dapat diinvers, null sebaliknya, dihitung dengan metode reduksi baris
    public Matriks inverseWRed(){
        MatriksAug X = MatriksAug.from(copy(), identity(BARIS()));
        X.toReducedRowEchelon();
        if(X.LEFT().isIdentity())return X.RIGHT();
        return null;
        
    }

    /* *** UTILITAS *** */
    // copy -- Mengembalikan salinan dari matriks
    public Matriks copy(){
        Matriks M = new Matriks(BARIS(), KOLOM());
        each((i,j) -> M.set(i,j, get(i,j)));
        return M;
    }

    public static Matriks readMatriks(Scanner inputln){
        // input size
        String line = inputln.nextLine();
        String[] line_arr = line.split("\\s+");
        int SIZE_bar = Integer.parseInt(line_arr[0]);
        int SIZE_col = Integer.parseInt(line_arr[1]);

        // inisialisasi matriks
        Matriks m = new Matriks(SIZE_bar,SIZE_col);

        // input nilai matriks
        for(int i = 0; i < SIZE_bar; i++){
            line = inputln.nextLine();
            line_arr = line.split("\\s+");
            for(int j = 0; j < SIZE_col; j++){
                m.set(i,j, Float.parseFloat(line_arr[j]));
            }
        }
        return m;
    }

    public Matriks readMatriksFile(String filename){
        Matriks M = null;
        try{
            int bar,kol;
            // baca file
            File f = new File (filename);
            Scanner line = new Scanner (f);

            // hitung kolom dengan baca baris pertama, baris = kolom - 1
            String[] dataln = line.nextLine().split("\\s+");
            kol = dataln.length;
            bar = kol - 1;
            M = new Matriks(bar,kol);

            // pindahkan baris 1 yang sudah terbaca
            for(int j = 0; j < kol; j++){
                M.set(0,j,Float.parseFloat(dataln[j]));
            }

            // baca file, pindahkan ke matriks
            int i = 1;
            while(line.hasNextLine()){
                dataln = line.nextLine().split("\\s+");
                for (int j = 0; j < kol; j++){
                    M.set(i,j,Float.parseFloat(dataln[j]));
                }
                i++;
            }
            line.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }

        return M;
    }

    /* *** TAMPILAN *** */
    // toString -- Mengembalikan representasi string dari matriks
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < BARIS(); i++){
            for(int j = 0; j < KOLOM(); j++){
                str.append(matrixElmtFMT(get(i,j)));
                if(j < KOLOM()-1)str.append(' ');
            }
            if(i < BARIS()-1)str.append('\n');
        }
        return str.toString();
    }
}
