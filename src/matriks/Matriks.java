package matriks;

import matriks.util.*;

public class Matriks{
    // Konstanta pembantu
    static int
        IDX_UNDEF = -1;
    int IDX_RIGHTMOST;
    
    // Konstanta temporer untuk mencegah instansiasi ulang
    static Matriks
        ZERO = null,
        IDENT = null;
    
    /* *** KONSTANTA *** */
    // zero -- Mengembalikan matriks nol, yaitu matriks di mana semua elemennya adalah 0
    public static Matriks zero(int baris, int kolom){
        if(ZERO == null || ZERO.bar != baris || ZERO.kol != kolom){
            ZERO = new Matriks(baris, kolom);
            ZERO.each((i,j) -> ZERO.set(i, j, 0f));
        }
        return ZERO;
    }
    // identity -- Mengembalikan matriks identitas, yaitu matriks berelemen 1 di diagonal utama, dan 0 di tempat lainnya
    public static Matriks identity(int len){
        if(IDENT == null || IDENT.bar != len){
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

        mat = new float[bar][kol];

        IDX_RIGHTMOST = kol-1;
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
    public int numBaris(){return bar;}
    public int numKolom(){return kol;}
    
    // Aksesor elemen matriks
    public float get(int baris, int kolom) throws IndexOutOfBoundsException{
        try{
            return mat[baris][kolom];
        }catch(IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException("Gagal mengakses elemen matriks: Indeks di luar batas matriks");
        }
    }
    public void set(int baris, int kolom, float val) throws IndexOutOfBoundsException{
        try{
            mat[baris][kolom] = val;
        }catch(IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException("Gagal mengakses elemen matriks: Indeks di luar batas matriks");
        }
    }
    public void setRange(float... val){
        int k = 0, p = 0, q = 0;
        while(k < val.length && k < bar * kol){
            set(p,q, val[k]);
            k++; q++;
            if(q >= kol){
                q -= kol;
                p++;
            }
        }
    }
    public void setAll(MatFunc func){
        each((i,j) -> set(i,j, func.get(i,j)));
    }

    /* *** ITERATOR *** */
    // Iterator elemen matriks
    public void each(MatIterator iter){
        for(int i = 0; i < bar; i++){
            for(int j = 0; j < kol; j++){
                iter.get(i, j);
            }
        }
    }
    // Iterator baris dan kolom matriks
    public void barisEach(int idxBaris, MatIterator iter){
        if(idxBaris < 0 || bar <= idxBaris)return;
        for(int i = 0; i < kol; i++){
            iter.get(idxBaris, i);
        }
    }
    public void kolomEach(int idxKolom, MatIterator iter){
        if(idxKolom < 0 || kol <= idxKolom)return;
        for(int i = 0; i < bar; i++){
            iter.get(i, idxKolom);
        }
    }

    /* *** PREDIKAT *** */
    // isSquare -- Mengembalikan true jika matriks persegi, false sebaliknya
    public boolean isSquare(){
        return bar == kol;
    }
    // isEquivalent -- Mengembalikan true jika dimensi kedua matriks sama, false sebaliknya
    public boolean isEquivalent(Matriks other){
        return bar == other.bar && kol == other.kol;
    }
    // isSingular -- Mengembalikan true jika matriks singular (tidak memiliki invers), false sebaliknya
    public boolean isSingular(){
        return determinant() == 0f;
    }
    // isInvertible -- Mengembalikan true jika matriks dapat diinvers, false sebaliknya
    public boolean isInvertible(){
        return isSquare() && !isSingular();
    }
    // idxInMatriks -- Mengembalikan true jika baris dan kolom terdapat dalam matriks, false sebaliknya
    public boolean idxInMatriks(int baris, int kolom){
        return
            0 <= baris && baris < bar &&
            0 <= kolom && kolom < kol;
    }
    // canMultiply -- Mengembalikan true jika matriks dapat mengalikan matriks other, false sebaliknya
    public boolean canMultiply(Matriks other){
        return kol == other.bar;
    }
    // isRowEchelon -- Mengembalikan true jika matriks dalam bentuk eselon baris, false sebaliknya
    public boolean isRowEchelon(){
        int depth = 0;
        for(int i = 0; i < bar; i++){
            int leading = 0;
            while(leading < kol && get(i, leading) == 0f)leading++;
            if(leading <= depth)return false;
            depth = leading;
        }
        return true;
    }
    // isBarisNol -- Mengembalikan true jika suatu baris bernilai nol, false sebaliknya
    public boolean isBarisNol(int baris){
        return idxLead(baris) == IDX_UNDEF;
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
        Matriks R = zero(bar, other.kol);
        R.each((i,j) ->
            barisEach(i, (a,b) -> {
                R.set(i,j, R.get(i,j) + get(i,b) * other.get(b,j));
            })
        );
        return R;
    }
    // transpose -- Mengembalikan matriks transpose, matriks dicerminkan terhadap diagonal utama
    public Matriks transpose(){
        Matriks T = new Matriks(kol, bar);
        each((i,j) -> T.set(j,i, get(i,j)));
        return T;
    }
    // determinant -- Mengembalikan nilai determinan matriks jika matriks persegi, NaN sebaliknya
    public float determinant(){
        /// BASIS: Determinan matriks satu elemen adalah elemen itu sendiri
        if(!isSquare())return Float.NaN;
        if(bar == 1)return get(0,0);
        /// REKURENS: Determinan matriks didefinisikan menurut ekspansi Laplace sebagai jumlah dari elemen * kofaktor sepanjang salah satu baris
        float det = 0f;
        for(int i = 0; i < kol; i++)det += get(0, i) * cofactor(0, i);
        return det;
    }
    // minor -- Mengembalikan minor, yaitu determinan dari suatu matriks hasil penghapusan salah satu baris dan kolom
    public float minor(int baris, int kolom){
        if(!isSquare())return Float.NaN;
        Matriks m = new Matriks(bar-1, kol-1);
        m.each((i,j) -> m.set(i,j, get(i<baris ? i : i+1, j<kolom ? j : j+1)));
        return m.determinant();
    }
    // cofactor -- Mengembalikan kofaktor matriks di suatu baris dan kolom
    public float cofactor(int baris, int kolom){
        return (float)Math.pow(-1f, baris+kolom) * minor(baris, kolom);
    }
    // adjugate -- Mengembalikan matriks adjoin (transpose dari matriks kofaktor) dari suatu matriks jika matriks persegi, null sebaliknya
    public Matriks adjugate(){
        if(!isSquare())return null;
        Matriks A = new Matriks(bar);
        A.each((i,j) -> A.set(j,i, cofactor(i,j)));
        return A;
    }
    // inverse -- Mengembalikan invers dari suatu matriks jika matriks dapat diinvers, null sebaliknya
    public Matriks inverse(){
        if(!isInvertible())return null;
        // Hitung invers sebagai 1/det * adj
        // Implementasi ini lambat, sebaiknya menggunakan metode eliminasi Gauss
        return adjugate().scale(1f / determinant());
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
        if(K.kol != 1 || bar != K.bar)return null;

        Matriks S = copy();
        // Ganti elemen kolom j dengan elemen K
        for(int i = 0; i < bar; i++){
            S.set(i,j, K.get(i,0));
        }
        return S;
    }
    // idxLead -- Mengembalikan indeks elemen non-0 pertama di suatu baris, IDX_UNDEF jika tidak ada
    public int idxLead(int baris){
        for(int j = 0; j < kol; j++){
            if(get(baris,j) != 0f)return j;
        }
        return IDX_UNDEF;
    }
    // numBarNol -- Mengembalikan banyak baris nol dalam matriks
    public int numBarNol(){
        int c = 0;
        for(int i = 0; i < bar; i++){
            if(isBarisNol(i))c++;
        }
        return c;
    }

    /* *** LAINNYA *** */
    // copy -- Mengembalikan salinan dari matriks
    public Matriks copy(){
        Matriks M = new Matriks(bar, kol);
        each((i,j) -> M.set(i,j, get(i,j)));
        return M;
    }

    /* *** TAMPILAN *** */
    // toString -- Mengembalikan representasi string dari matriks
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < bar; i++){
            for(int j = 0; j < kol; j++){
                str.append(get(i,j));
                if(j < kol-1)str.append(' ');
            }
            if(i < bar-1)str.append('\n');
        }
        return str.toString();
    }
}
