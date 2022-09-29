package matriks;

public class MatriksAug2 implements IMatriks{
    /* *** PROPERTI *** */
    Matriks left, right;

    public Matriks LEFT(){return left;}
    public Matriks RIGHT(){return right;}
    public int BARIS(){return left.BARIS();}
    public int KOLOM(){return left.KOLOM() + right.KOLOM();}
    public int IDXMID(){return left.KOLOM();}

    /* *** KONSTRUKTOR *** */
    private MatriksAug2(Matriks L, Matriks R){
        left = L.copy();
        right = R.copy();
    }

    /* *** METODE INISIALISASI *** */
    public static MatriksAug2 from(Matriks leftMat, Matriks rightMat){
        /// Tidak membuat matriks augmented jika banyak baris kedua matriks berbeda
        if(leftMat.BARIS() != rightMat.BARIS())return null;
        return new MatriksAug2(leftMat, rightMat);
    }

    /* *** AKSESOR *** */
    public float get(int i, int j){
        return j < IDXMID() ? LEFT().get(i,j) : RIGHT().get(i,j-IDXMID());
    }
    public void set(int i, int j, float val){
        if(j < IDXMID()){
            LEFT().set(i,j,val);
        }else{
            RIGHT().set(i,j-IDXMID(),val);
        }
    }

    /* UTILITAS */
    public MatriksAug2 copy(){
        return from(LEFT(), RIGHT());
    }
}
