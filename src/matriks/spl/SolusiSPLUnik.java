package matriks.spl;

import java.util.Arrays;

public class SolusiSPLUnik extends SolusiSPL{
    float[] solusi;

    public SolusiSPLUnik(int size){
        super(size);
        solusi = new float[size];
    }

    public void set(int i, float val){
        if(0 <= i && i < solusi.length)solusi[i] = val;
    }

    public float get(int i, float... arg){
        if(i < 0 || solusi.length <= i)return Float.NaN;
        return solusi[i];
    }

    @Override
    public String toString(){
        return Arrays.toString(solusi);
    }
}
