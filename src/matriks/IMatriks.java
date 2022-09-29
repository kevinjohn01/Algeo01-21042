package matriks;

public interface IMatriks{
    /* *** PROPERTI *** */
    public int BARIS();
    public int KOLOM();

    /* *** AKSESOR *** */
    public float get(int i, int j);
    public void set(int i, int j, float val);

    /* *** ITERATOR *** */
    /*public void each(MatIterator iter);
    public void barisEach(int i, MatIterator iter);
    public void kolomEach(int j, MatIterator iter);*/

    /* *** UTILITAS *** */
    public IMatriks copy();
}
