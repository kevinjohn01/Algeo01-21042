package matriks;

import matriks.spl.*;
import static matriks.util.Constants.*;
import static matriks.util.Format.*;

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

    /* *** FUNGSI *** */
    // idxLead -- Mengembalikan indeks elemen non-0 pertama di suatu baris, IDX_UNDEF jika tidak ada
    public int idxLead(int baris){
        int j = LEFT().idxLead(baris);
        if(j == IDX_UNDEF){
            j = RIGHT().idxLead(baris);
            if(j == IDX_UNDEF)return IDX_UNDEF;
            return IDXMID() + j;
        }
        return j;
    }

    /* *** OPERASI MATRIKS AUGMENTED *** */
    // toRowEchelon -- Mengubah matriks menjadi bentuk row echelon, mengembalikan true jika tidak ada baris inkonsisten
    public boolean toRowEchelon(){
        boolean inconsistent = false;
        for(int i = 0; i < BARIS(); i++){
            int j = idxLead(i);
            // Jika ada baris inkonsisten, maka SPL tidak memiliki solusi
            inconsistent = kolInkonsisten(j);
            // Jika j != i, tukar baris dengan baris lain dengan leading elemen indeks terkecil
            if(j != i){
                int leadBar = IDX_UNDEF;
                j = KOLOM();
                for(int k = i; k < BARIS(); k++){
                    int currLeadIdx = idxLead(k);
                    if(currLeadIdx < j){
                        leadBar = k;
                        j = currLeadIdx;
                    }
                }
                if(leadBar != IDX_UNDEF){
                    swapBaris(i, leadBar);
                }
            }
            j = idxLead(i);
            // Lakukan operasi jika baris valid
            if(kolValid(j)){
                // Ubah menjadi leading 1
                if(get(i,j) != 1f)sclBaris(i, 1f/get(i,j));
                // Eliminasi elemen kolom j di bawahnya
                for(int k = i+1; k < BARIS(); k++){
                    int l = idxLead(k);
                    if(j==l){
                        addBaris(k, i, -get(k,l));
                    }
                }
            }
        }
        return !inconsistent;
    }
    // toReducedRowEchelon -- Mengubah matriks menjadi bentuk reduced row echelon, mengembalikan true jika tidak ada baris inkonsisten
    public boolean toReducedRowEchelon(){
        boolean inconsistent = !toRowEchelon();
        for(int i = BARIS()-1; i >= 0; i--){
            int j = idxLead(i);
            // Jika ada baris inkonsisten, maka SPL tidak memiliki solusi
            inconsistent = kolInkonsisten(j);
            for(int k = i-1; k >= 0; k--){
                // Lakukan operasi jika baris valid
                if(kolValid(j)){
                    // Eliminasi elemen kolom j di atasnya
                    float x = get(k,j);
                    if(x != 0f){
                        addBaris(k, i, -x);
                    }
                }
            }
        }
        return !inconsistent;
    }

    /* *** SPL SOLVER *** */
    // elimGauss -- Mengembalikan solusi SPL yang direpresentasikan oleh matriks ini, menggunakan metode eliminasi Gauss
    public SolusiSPL elimGauss(){
        // Handle kasus matriks non-SPL
        if(!isMatriksSPL())return null;
        
        // Ubah matriks augmented menjadi bentuk row echelon
        boolean inconsistent = !toRowEchelon();
        // Jika ada baris inkonsisten, maka SPL tidak memiliki solusi
        if(inconsistent){
            return new SolusiSPLNul(BARIS());
        }
        // Jika ada baris nol, maka SPL memiliki solusi banyak
        int barNol = numBarNol();
        if(barNol > 0){
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
        boolean inconsistent = !toReducedRowEchelon();
        // Jika ada baris inkonsisten, maka SPL tidak memiliki solusi
        if(inconsistent){
            return new SolusiSPLNul(BARIS());
        }
        // Jika ada baris nol, maka SPL memiliki solusi banyak
        int barNol = numBarNol();
        if(barNol > 0){
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
                str.append(matrixElmtFMT(get(i,j)));
                if(j == IDXMID()-1)str.append(" |");
                if(j < KOLOM()-1)str.append(' ');
            }
            if(i < BARIS()-1)str.append('\n');
        }
        return str.toString();
    }
}
