package matriks;

import matriks.spl.*;

public class MatriksAug extends Matriks {
    // Konstanta pembantu
    int IDX_MID;

    /* *** PROPERTI *** */
    Matriks left, right;
    public Matriks LEFT(){return left;}
    public Matriks RIGHT(){return right;}

    /* *** KONSTRUKTOR *** */
    private MatriksAug(int baris, int kolom){
        super(baris, kolom);
        left = null; right = null;
    }

    /* *** METODE *** */
    // from -- Membuat matriks augmented dengan menggabungkan dua buah matriks
    public static MatriksAug from(Matriks A, Matriks B) throws IllegalArgumentException{
        if(A.bar != B.bar)throw new IllegalArgumentException("Kolom matriks A dan B berbeda, tidak dapat membuat matriks augmented");
        MatriksAug M = new MatriksAug(A.bar, A.kol+B.kol);
        M.IDX_MID = A.kol;
        M.each((i,j) -> M.set(i,j, j < M.IDX_MID ? A.get(i,j) : B.get(i,0)));
        M.left = A; M.right = B;
        return M;
    }

    /* *** ARITMATIKA OBE *** */
    // addBaris -- Menjumlahkan baris dengan kelipatan konstan baris lain
    public Matriks addBaris(int baris, int toAdd, float scl){
        barisEach(baris, (i,j) -> set(i,j, get(i,j) + scl * get(toAdd, j)));
        return this;
    }

    // sclBaris -- Mengalikan satu baris dengan suatu konstanta
    public Matriks sclBaris(int baris, float scl){
        barisEach(baris, (i,j) -> set(i,j, scl * get(i,j)));
        return this;
    }

    // swapBaris -- Menukar satu baris dengan baris lainnya
    public Matriks swapBaris(int baris, int toSwap){
        barisEach(baris, (i,j) -> {
            float temp = get(i,j);
            set(i,j, get(toSwap, j));
            set(toSwap,j, temp);
        });
        return this;
    }

    /* *** OPERASI MATRIKS AUGMENTED *** */
    // toRowEchelon -- Mengubah matriks menjadi bentuk row echelon, mengembalikan true jika tidak ada baris inkonsisten
    public boolean toRowEchelon(){
        boolean inconsistent = false;
        for(int i = 0; i < bar; i++){
            int j = idxLead(i);
            // Jika ada baris inkonsisten, maka SPL tidak memiliki solusi
            if(!inconsistent && j == IDX_RIGHTMOST){
                inconsistent = true;
            }
            // Jika j != i, tukar baris dengan baris lain dengan leading elemen indeks terkecil
            if(j != i){
                int leadBar = IDX_UNDEF;
                j = kol;
                for(int k = i+1; k < bar; k++){
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
            if(j != IDX_RIGHTMOST && j != IDX_UNDEF){
                // Ubah menjadi leading 1
                if(get(i,j) != 1f)sclBaris(i, 1f/get(i,j));
                // Eliminasi elemen kolom j di bawahnya
                for(int k = i+1; k < bar; k++){
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
        for(int i = bar-1; i >= 0; i--){
            int j = idxLead(i);
            // Jika ada baris inkonsisten, maka SPL tidak memiliki solusi
            if(!inconsistent && j == IDX_RIGHTMOST){
                inconsistent = true;
            }
            for(int k = i-1; k >= 0; k--){
                // Lakukan operasi jika baris valid
                if(j != IDX_RIGHTMOST && j != IDX_UNDEF){
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

    // elimGauss -- Mengembalikan solusi SPL yang direpresentasikan oleh matriks ini, menggunakan metode eliminasi Gauss
    public SolusiSPL elimGauss(){
        // Ubah matriks augmented menjadi bentuk row echelon
        boolean inconsistent = !toRowEchelon();
        // Jika ada baris inkonsisten, maka SPL tidak memiliki solusi
        if(inconsistent){
            return new SolusiSPLNul(bar);
        }
        // Jika ada baris nol, maka SPL memiliki solusi banyak
        int barNol = numBarNol();
        if(barNol > 0){
            return new SolusiSPLBanyak(bar/*, barNol, this*/);
        }
        // Selain itu SPL memiliki solusi unik
        SolusiSPLUnik solusi = new SolusiSPLUnik(bar);
        for(int i = bar-1; i >= 0; i--){
            // Lakukan substitusi balik mulai dari baris terbawah
            float val = get(i,IDX_MID);
            for(int j = i+1; j < IDX_MID; j++){
                val -= get(i,j) * solusi.get(j);
            }
            solusi.set(i,val);
        }
        return solusi;
    }

    // elimGaussJordan -- Mengembalikan solusi SPL yang direpresentasikan oleh matriks ini, menggunakan metode eliminasi Gauss-Jordan
    public SolusiSPL elimGaussJordan(){
        // Ubah matriks augmented menjadi bentuk reduced row echelon
        boolean inconsistent = !toReducedRowEchelon();
        // Jika ada baris inkonsisten, maka SPL tidak memiliki solusi
        if(inconsistent){
            return new SolusiSPLNul(bar);
        }
        // Jika ada baris nol, maka SPL memiliki solusi banyak
        int barNol = numBarNol();
        if(barNol > 0){
            return new SolusiSPLBanyak(bar/*, barNol, this*/);
        }
        // Selain itu SPL memiliki solusi unik
        SolusiSPLUnik solusi = new SolusiSPLUnik(bar);
        for(int i = 0; i < bar; i++){
            solusi.set(i, get(i,IDX_MID));
        }
        return solusi;
    }

    /* *** TAMPILAN *** */
    // toString -- Mengembalikan representasi string dari matriks
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < bar; i++){
            for(int j = 0; j < kol; j++){
                str.append(" " + get(i,j));
                if(j == IDX_MID-1)str.append(" |");
            }
            if(i < bar-1)str.append('\n');
        }
        return str.toString();
    }
}
