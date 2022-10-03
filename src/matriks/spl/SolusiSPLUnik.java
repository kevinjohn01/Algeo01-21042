package matriks.spl;

import static matriks.util.Format.*;

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
        StringBuilder str = new StringBuilder();

        for(int i = 0; i < solusi.length; i++){
            // Print nama dan nilai variabel dalam format x{i} = {f}
            str.append(String.format("x%s = %s", i+1, floatFMT(solusi[i])));
            if(i < solusi.length-1)str.append('\n');
        }

        return str.toString();
    }
}
