package matriks.spl;

import matriks.*;

public class SolusiSPLBanyak extends SolusiSPL{
    protected int numVarBebas;
    protected Matriks coef, konst;

    public SolusiSPLBanyak(int size, MatriksAug mat){
        super(size);
        init(mat.copy());
    }

    private void init(MatriksAug mat){
        // Reduksi matriks hingga mencapai bentuk reduced row echelon
        // agar persamaan hanya dinyatakan dalam parameter-parameternya
        mat.toReducedRowEchelon();

        // Hitung banyak variabel bebas, inisialisasi matriks buffer
        numVarBebas = mat.numBarNol();
        coef = new Matriks(mat.BARIS(), numVarBebas);
        konst = mat.RIGHT().copy();

        // Semua baris nol ada di bawah matriks, loop dari atas menentukan
        // nilai koefisien fungsi variabel terikat
        for(int i = 0; i < coef.BARIS()-numVarBebas; i++){
            for(int k = 0; k < numVarBebas; k++){
                int j = k + mat.LEFT().KOLOM() - numVarBebas;
                coef.set(i,k, mat.get(i,j));
            }
        }
        // Baris untuk variabel bebas diisi parameter
        for(int k = 0; k < numVarBebas; k++){
            int i = coef.BARIS()-numVarBebas+k;
            coef.set(i,k+coef.KOLOM()-numVarBebas, -1f);
        }
    }

    public float get(int i, float... arg){
        // Tidak menghitung jika input tidak sesuai
        if(i<0 || size<=i || arg.length != numVarBebas)return Float.NaN;
        float res = konst.get(i,0);
        for(int j = 0; j < coef.KOLOM(); j++){
            res -= coef.get(i,j) * arg[j];
        }
        return res;
    }

    public int numVarBebas(){
        return numVarBebas;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append('[');

        for(int i = 0; i < coef.BARIS(); i++){
            int numTerm = 0;
            // Print konstanta
            float r = konst.get(i,0);
            if(r != 0){
                str.append(r);
                numTerm++;
            }
            for(int j = 0; j < coef.KOLOM(); j++){
                float c = -coef.get(i,j);
                if(c != 0){
                    // Print c dan tanda
                    char sgn = c < 0f ? '-' : '+';
                    if(sgn != '+' || numTerm != 0)str.append(sgn);
                    c *= c < 0f ? -1f : 1f;
                    if(c != 1f)str.append(c);

                    // Print nama parameter
                    char v = Character.toChars(Character.valueOf('a')+j)[0];
                    str.append(v);

                    numTerm++;
                }
            }
            if(i < coef.BARIS()-1)str.append(", ");
        }

        str.append(']');
        return str.toString();
    }
}
