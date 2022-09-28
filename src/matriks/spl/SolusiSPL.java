package matriks.spl;

public abstract class SolusiSPL{
    protected int size;

    public SolusiSPL(int size){
        this.size = size;
    }

    public abstract float get(int i, float... arg);
    public int numVariable(){
        return size;
    }
}
