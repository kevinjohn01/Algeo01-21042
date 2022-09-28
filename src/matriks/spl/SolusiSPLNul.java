package matriks.spl;

public class SolusiSPLNul extends SolusiSPL{
    public SolusiSPLNul(int size){
        super(size);
    }

    public float get(int i, float... arg){
        return Float.NaN;
    }

    @Override
    public String toString(){
        return "Tidak ada solusi";
    }
}
