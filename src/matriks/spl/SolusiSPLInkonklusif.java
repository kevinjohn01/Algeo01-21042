package matriks.spl;

public class SolusiSPLInkonklusif extends SolusiSPL{
    public SolusiSPLInkonklusif(int size){
        super(size);
    }

    public float get(int i, float... arg){
        return Float.NaN;
    }

    @Override
    public String toString(){
        return "Tidak dapat menarik kesimpulan";
    }
}
