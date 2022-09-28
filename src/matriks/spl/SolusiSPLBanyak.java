package matriks.spl;

import matriks.*;

public class SolusiSPLBanyak extends SolusiSPL{
    protected int numVarBebas;
    // Unimplemented
    // protected Matriks coef, var;

    public SolusiSPLBanyak(int size, int numVarBebas, MatriksAug mat){
        super(size);
        this.numVarBebas = numVarBebas;

        init(mat);
    }

    private void init(Matriks mat){
        // Unimplemented
    }

    public float get(int i, float... arg){
        // Unimplemented
        return Float.NaN;
    }

    @Override
    public String toString(){
        return "Solusi banyak";
    }
}
