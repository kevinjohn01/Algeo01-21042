package matriks;

import matriks.spl.*;
import matriks.util.*;
import static matriks.util.Constants.*;

public class MatriksAug implements IMatriks{
    /* *** PROPERTI *** */
    Matriks left, right;

    public Matriks LEFT(){return left;}
    public Matriks RIGHT(){return right;}
    public int BARIS(){return left.BARIS();}
    public int KOLOM(){return left.KOLOM() + right.KOLOM();}
    public int IDXMID(){return left.KOLOM();}

    /* *** KONSTRUKTOR *** */
    private MatriksAug(Matriks L, Matriks R){
        left = L;
        right = R;
    }

    /* *** METODE INISIALISASI *** */
    // from -- Membuat matriks augmented dari dua buah matriks
    public static MatriksAug from(Matriks leftMat, Matriks rightMat){
        /// Tidak membuat matriks augmented jika banyak baris kedua matriks berbeda
        if(leftMat.BARIS() != rightMat.BARIS())return null;
        return new MatriksAug(leftMat, rightMat);
    }

    /* *** AKSESOR *** */
    // get -- Mengembalikan nilai elemen matriks augmented i,j
    public float get(int i, int j){
        return j < IDXMID() ? LEFT().get(i,j) : RIGHT().get(i,j-IDXMID());
    }
    // set -- Menugaskan nilai elemen matriks augmented i,j
    public void set(int i, int j, float val){
        if(j < IDXMID()){
            LEFT().set(i,j,val);
        }else{
            RIGHT().set(i,j-IDXMID(),val);
        }
    }

    /* PREDIKAT */
    // isMatriksSPL --  Mengembalikan true jika matriks menyatakan suatu SPL, false sebaliknya
    public boolean isMatriksSPL(){
        return /*LEFT().isSquare() &&*/ RIGHT().KOLOM() == 1;
    }
    // isBarisInkonsisten -- Mengembalikan true jika baris inkonsisten (ruas kiri 0, ruas kanan non-0)
    public boolean isBarisInkonsisten(int baris){
        return idxLead(baris) >= IDXMID();
    }
    // kolInkonsisten -- Mengembalikan true jika indeks kolom memenuhi kondisi inkonsistensi, INPUT ADALAH OUTPUT DARI idxLead(baris)
    public boolean kolInkonsisten(int j){
        return j >= IDXMID() && j != IDX_UNDEF;
    }
    // kolNol -- Mengembalikan true jika indeks kolom memenuhi kondisi indeterminansi, INPUT ADALAH OUTPUT DARI idxLead(baris)
    public boolean kolNol(int j){
        return j == IDX_UNDEF;
    }
    // kolValid -- Mengembalikan true jika indeks kolom memenuhi kondisi validitas, INPUT ADALAH OUTPUT DARI idxLead(baris)
    public boolean kolValid(int j){
        return 0 <= j && j < IDXMID();
    }
    // solUnik -- Mengembalikan true jika solusi unik
    public boolean solUnik(){
        if(!LEFT().isSquare())return false;
        boolean b = true;
        int i = 0;
        while(b && i < BARIS()){
            b = Mathf.one(get(i,i));
            i++;
        }
        return b;
    }
    // inkonsisten -- Mengembalikan true jika ada baris inkonsisten
    public boolean inkonsisten(){
        boolean b = false;
        int i = 0;
        while(!b && i < BARIS()){
            b = isBarisInkonsisten(i);
            i++;
        }
        return b;
    }

    /* *** FUNGSI *** */
    // idxLead -- Mengembalikan indeks elemen non-0 pertama di suatu baris, IDX_UNDEF jika tidak ada
    /*public int idxLead(int baris){
        int j = LEFT().idxLead(baris);
        if(j == IDX_UNDEF){
            j = RIGHT().idxLead(baris);
            if(j == IDX_UNDEF)return IDX_UNDEF;
            return IDXMID() + j;
        }
        return j;
    }*/

    /* *** OPERASI MATRIKS AUGMENTED *** */
    // toRowEchelon -- Mengubah matriks menjadi bentuk row echelon, mengembalikan true jika tidak ada baris inkonsisten
    public MatriksAug toRowEchelon(){
        for(int i = 0; i < BARIS(); i++){
            int leadCol = IDX_UNDEF, leadRow = IDX_UNDEF;
            // Tukar baris dengan baris lain yang leading elemennya paling kiri
            for(int row = i; row < BARIS(); row++){
                int currLeadCol = idxLead(row);
                if(leadRow == IDX_UNDEF || currLeadCol < leadCol){
                    leadCol = currLeadCol;
                    leadRow = row;
                }
                //System.out.format("%d/%d ", currLeadCol, leadCol);
            }
            if(leadRow != IDX_UNDEF && leadRow != i)swapBaris(i, leadRow);
            // Lakukan operasi jika baris valid
            if(kolValid(leadCol)){
                // Ubah menjadi leading 1
                if(!Mathf.one(get(i,leadCol)))sclBaris(i, 1f/get(i,leadCol));
                // Eliminasi elemen di kolom yang sama dengan leading 1 di bawahnya
                for(int row = i+1; row < BARIS(); row++){
                    float val = get(row, leadCol);
                    if(!Mathf.zero(val))addBaris(row, i, -val);
                    // Nolkan leading elemen untuk menghindari rounding error
                    set(row, leadCol, 0f);
                }
            }
        }
        return this;
    }
    // toReducedRowEchelon -- Mengubah matriks menjadi bentuk reduced row echelon, mengembalikan true jika tidak ada baris inkonsisten
    public MatriksAug toReducedRowEchelon(){
        toRowEchelon();
        for(int i = BARIS()-1; i >= 0; i--){
            int leadCol = idxLead(i);
            // Lakukan operasi jika baris valid
            if(kolValid(leadCol)){
                for(int row = i-1; row >= 0; row--){
                    // Eliminasi elemen di kolom yang sama dengan leading 1 di atasnya
                    float val = get(row, leadCol);
                    if(!Mathf.zero(val))addBaris(row, i, -val);
                    // Nolkan elemen untuk menghindari rounding error
                    set(row, leadCol, 0f);
                }
            }
        }
        return this;
    }

    /* *** SPL SOLVER *** */
    // solveInverse -- Mengembalikan solusi SPL yang direpresentasikan oleh matriks ini, menggunakan metode matriks invers
    public SolusiSPL solveInverse(){
        if(!LEFT().isInvertible())return new SolusiSPLInkonklusif(BARIS());
        Matriks X = LEFT().inverse().product(RIGHT());
        SolusiSPLUnik sol = new SolusiSPLUnik(BARIS());
        for(int i = 0; i < BARIS(); i++){
            sol.set(i, X.get(i,0));
        }
        return sol;
    }
    // elimGauss -- Mengembalikan solusi SPL yang direpresentasikan oleh matriks ini, menggunakan metode eliminasi Gauss
    public SolusiSPL elimGauss(){
        // Handle kasus matriks non-SPL
        if(!isMatriksSPL())return null;
        
        // Ubah matriks augmented menjadi bentuk row echelon
        toRowEchelon();
        // Jika ada baris inkonsisten, maka SPL tidak memiliki solusi
        boolean inc = inkonsisten();
        if(inc){
            return new SolusiSPLNul(BARIS());
        }
        // Jika ada baris nol, maka SPL memiliki solusi banyak
        if(!solUnik()){
            return new SolusiSPLBanyak(BARIS(), this);
        }
        // Selain itu SPL memiliki solusi unik
        SolusiSPLUnik solusi = new SolusiSPLUnik(BARIS());
        for(int i = BARIS()-1; i >= 0; i--){
            // Lakukan substitusi balik mulai dari baris terbawah
            float val = get(i,IDXMID());
            for(int j = i+1; j < IDXMID(); j++){
                val -= get(i,j) * solusi.get(j);
            }
            solusi.set(i,val);
        }
        return solusi;
    }

    // elimGaussJordan -- Mengembalikan solusi SPL yang direpresentasikan oleh matriks ini, menggunakan metode eliminasi Gauss-Jordan
    public SolusiSPL elimGaussJordan(){
        // Handle kasus matriks non-SPL
        if(!isMatriksSPL())return null;

        // Ubah matriks augmented menjadi bentuk reduced row echelon
        toReducedRowEchelon();
        // Jika ada baris inkonsisten, maka SPL tidak memiliki solusi
        boolean inc = inkonsisten();
        if(inc){
            return new SolusiSPLNul(BARIS());
        }
        // Jika ada baris nol, maka SPL memiliki solusi banyak
        if(!solUnik()){
            return new SolusiSPLBanyak(BARIS(), this);
        }
        // Selain itu SPL memiliki solusi unik
        SolusiSPLUnik solusi = new SolusiSPLUnik(BARIS());
        for(int i = 0; i < BARIS(); i++){
            solusi.set(i, get(i,IDXMID()));
        }
        return solusi;
    }

    /* UTILITAS */
    // copy -- Mengembalikan salinan dari matriks ini
    public MatriksAug copy(){
        return from(LEFT(), RIGHT());
    }

    /* *** TAMPILAN *** */
    // toString -- Mengembalikan representasi string dari matriks
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < BARIS(); i++){
            for(int j = 0; j < KOLOM(); j++){
                str.append(Format.matrixElmtFMT(get(i,j)));
                if(j == IDXMID()-1)str.append(" |");
                if(j < KOLOM()-1)str.append(' ');
            }
            if(i < BARIS()-1)str.append('\n');
        }
        return str.toString();
    }
}
