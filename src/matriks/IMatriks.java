package matriks;

import matriks.util.*;
import static matriks.util.Constants.*;

public interface IMatriks{
    /* *** PROPERTI *** */
    public int BARIS();
    public int KOLOM();

    /* *** AKSESOR *** */
    public float get(int i, int j);
    public void set(int i, int j, float val);

    /* *** ITERATOR *** */
    // Iterator elemen matriks
    public default void each(MatIterator iter){
        for(int i = 0; i < BARIS(); i++){
            for(int j = 0; j < KOLOM(); j++){
                iter.get(i,j);
            }
        }
    }
    // Iterator baris dan kolom matriks
    public default void barisEach(int idxBaris, MatIterator iter){
        if(idxBaris < 0 || BARIS() <= idxBaris)return;
        for(int i = 0; i < KOLOM(); i++){
            iter.get(idxBaris, i);
        }
    }
    public default void kolomEach(int idxKolom, MatIterator iter){
        if(idxKolom < 0 || KOLOM() <= idxKolom)return;
        for(int i = 0; i < BARIS(); i++){
            iter.get(i, idxKolom);
        }
    }

    /* *** PREDIKAT *** */
    // idxInMatriks -- Mengembalikan true jika baris dan kolom terdapat dalam matriks, false sebaliknya
    public default boolean idxInMatriks(int baris, int kolom){
        return
            0 <= baris && baris < BARIS() &&
            0 <= kolom && kolom < KOLOM();
    }

    /* *** FUNGSI *** */
    public default int idxLead(int baris){
        for(int j = 0; j < KOLOM(); j++){
            if(!Mathf.zero(get(baris,j)))return j;
        }
        return IDX_UNDEF;
    }
    public default boolean isBarisNol(int baris){
        return idxLead(baris) == IDX_UNDEF;
    }
    public default int numBarNol(){
        int c = 0;
        for(int i = 0; i < BARIS(); i++){
            if(isBarisNol(i))c++;
        }
        return c;
    }
    public IMatriks toRowEchelon();
    public IMatriks toReducedRowEchelon();

    /* *** OPERASI BARIS ELEMENTER *** */
    // addBaris -- Menjumlahkan satu baris dengan hasil kali konstanta baris lain
    public default IMatriks addBaris(int baris, int toAdd, float scl){
        barisEach(baris, (i,j) -> set(i,j, get(i,j) + scl * get(toAdd, j)));
        return this;
    }
    // sclBaris -- Mengalikan satu baris dengan suatu konstanta
    public default IMatriks sclBaris(int baris, float scl){
        barisEach(baris, (i,j) -> set(i,j, scl * get(i,j)));
        return this;
    }
    // swapBaris -- Menukar satu baris dengan baris lainnya
    public default IMatriks swapBaris(int baris, int toSwap){
        barisEach(baris, (i,j) -> {
            float temp = get(i,j);
            set(i,j, get(toSwap, j));
            set(toSwap,j, temp);
        });
        return this;
    }

    /* *** UTILITAS *** */
    public IMatriks copy();
    public default float[][] toArray(){
        float[][] arr = new float[BARIS()][KOLOM()];
        each((i,j) -> arr[i][j] = get(i,j));
        return arr;
    }
}
