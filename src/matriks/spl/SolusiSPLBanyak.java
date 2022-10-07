package matriks.spl;

import matriks.*;
import matriks.util.*;
import static matriks.util.Constants.*;

public class SolusiSPLBanyak extends SolusiSPL{
    protected int numVarBebas;
    protected Matriks coef, inp;//, konst;

    public SolusiSPLBanyak(int size, MatriksAug mat){
        super(size);
        init(mat.copy());
    }

    private void init(MatriksAug mat){
        // Reduksi matriks hingga mencapai bentuk reduced row echelon
        // agar persamaan hanya dinyatakan dalam parameter-parameternya
        mat.toReducedRowEchelon();
        //System.out.println(mat.toString() + '\n');

        // Variabel bebas dalam SPL adalah variabel yang tidak memiliki baris dengan leading 1 bersesuaian
        numVarBebas = mat.LEFT().KOLOM();
        boolean[] isVarIkat = new boolean[mat.LEFT().KOLOM()];
        for(int row = 0; row < mat.BARIS(); row++){
            int col = mat.idxLead(row);
            if(mat.kolValid(col)){
                isVarIkat[col] = true;
                numVarBebas--;
            }
        }

        // Inisialisasi matriks buffer
        coef = new Matriks(mat.LEFT().KOLOM(), numVarBebas+1);
        inp = new Matriks(numVarBebas+1, 1);
        inp.set(0,0, 1f);
        int idxVarBebas[] = new int[numVarBebas];
        int idx = 0;
        for(int i = 0; i < isVarIkat.length; i++){
            if(!isVarIkat[i]){
                idxVarBebas[idx] = i;
                idx++;
            }
        }
        //konst = mat.RIGHT();

        // Isi matriks buffer dengan koef untuk variabel bebas
        int idxFree = 1;
        for(int i = 0; i < coef.BARIS(); i++){
            if(isVarIkat[i])continue;
            coef.set(i,idxFree, 1f);
            idxFree++;
        }
        
        // Isi matriks buffer dengan koef dan konstanta untuk variabel terikat
        for(int row = 0; row < mat.BARIS(); row++){
            int col = mat.idxLead(row);
            if(mat.kolValid(col)){
                // Simpan konstanta
                coef.set(col,0, mat.get(row, mat.IDXMID()));
                // Simpan koef variabel bebas
                for(int nVar = 0; nVar < idxVarBebas.length; nVar++){
                    float val = mat.get(row, idxVarBebas[nVar]);
                    if(!Mathf.zero(val))coef.set(col,nVar+1, -val);
                }
            }
        }
        //System.out.println(coef.toString());

        // Variabel bebas adalah [x_a, x_b, ...] di mana a,b,... adalah indeks kolom nol
        /*
        numVarBebas = 0;
        boolean[] isVarBebas = new boolean[mat.IDXMID()];
        for(int i = 0; i < mat.LEFT().KOLOM(); i++){
            isVarBebas[i] = true;
            for(int k = 0; k < mat.BARIS(); k++){
                int lead = mat.LEFT().idxLead(k);
                if(lead == i){
                    isVarBebas[i] = false;
                }
                if(lead > i)break;
            }
            if(isVarBebas[i])numVarBebas++;
        }

        // Inisialisasi matriks buffer
        coef = new Matriks(mat.BARIS(), numVarBebas);
        konst = mat.RIGHT();
        int[] kolVarBebas = new int[numVarBebas];
        int idx = 0;
        for(int i = 0; i < isVarBebas.length; i++){
            if(isVarBebas[i]){
                kolVarBebas[idx++] = i;
            }
        }
        // Koefisien parameter diambil dari baris matriks
        for(int i = 0; i < mat.BARIS(); i++){
            int j = mat.idxLead(i);
            /*if(mat.(i)){
                // Kasus variabel bebas
                coef.set(i, iVarBebas++, -1f);
            }else{
                // Kasus variabel terikat parameter
                for(int k = 0; k < kolVarBebas.length; k++){
                    coef.set(i,j, mat.LEFT().get(i, kolVarBebas[j]));
                }
            }
            if(j != IDX_UNDEF){
                for(int k = 0; k < kolVarBebas.length; k++){
                    coef.set(j,k, mat.LEFT().get(i, kolVarBebas[k]));
                }
            }
        }
        int iVarBebas = 0;
        for(int i = 0; i < kolVarBebas.length; i++){
            coef.set(kolVarBebas[i], iVarBebas++, -1f);
        }*/

        /*
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
        }*/
    }

    public float get(int i, float... arg){
        // Tidak menghitung jika input tidak sesuai
        if(i<0 || size<=i || arg.length != numVarBebas)return Float.NaN;
        float x = coef.get(i,0);
        for(int j = 1; j < coef.KOLOM(); j++){
            x += coef.get(i,j) * arg[j-1];
        }
        return x;
    }

    public int numVarBebas(){
        return numVarBebas;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();

        for(int i = 0; i < coef.BARIS(); i++){
            // Print nama variabel
            str.append(String.format("x%s = ", i+1));
            int numTerm = 0;
            // Print konstanta
            float r = coef.get(i,0);
            if(r != 0){
                str.append(Format.floatFMT(r));
                numTerm++;
            }
            for(int j = 1; j < coef.KOLOM(); j++){
                float c = coef.get(i,j);
                if(c != 0){
                    // Print c dan tanda
                    char sgn = c < 0f ? '-' : '+';
                    if(numTerm == 0){
                        if(sgn == '-')str.append(sgn);
                    }else{
                        str.append(" " + sgn + " ");
                    }
                    c *= c < 0f ? -1f : 1f;
                    if(c != 1f)str.append(Format.floatFMT(c));

                    // Print nama parameter
                    char v = Character.toChars(Character.valueOf('a')+j-1)[0];
                    str.append(v);

                    numTerm++;
                }
            }
            if(i < coef.BARIS()-1)str.append('\n');
        }

        return str.toString();
    }
}
