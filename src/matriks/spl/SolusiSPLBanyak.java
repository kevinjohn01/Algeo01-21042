package matriks.spl;

import matriks.*;
import static matriks.util.Constants.*;
import static matriks.util.Format.*;

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

        for(int i = 0; i < mat.BARIS(); i++){
            int bar = 0, toSwap = IDX_UNDEF;
            while(toSwap == IDX_UNDEF && bar < mat.BARIS()){
                if(mat.LEFT().idxLead(bar) == i)toSwap = bar;
                bar++;
            }
            if(toSwap != IDX_UNDEF)mat.swapBaris(i, toSwap);
        }

        // Variabel bebas adalah [x_a, x_b, ...] di mana a,b,... adalah indeks kolom nol
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
            }*/
            if(j != IDX_UNDEF){
                for(int k = 0; k < kolVarBebas.length; k++){
                    coef.set(j,k, mat.LEFT().get(i, kolVarBebas[k]));
                }
            }
        }
        int iVarBebas = 0;
        for(int i = 0; i < kolVarBebas.length; i++){
            coef.set(kolVarBebas[i], iVarBebas++, -1f);
        }

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

        for(int i = 0; i < coef.BARIS(); i++){
            // Print nama variabel
            str.append(String.format("x%s = ", i+1));
            int numTerm = 0;
            // Print konstanta
            float r = konst.get(i,0);
            if(r != 0){
                str.append(floatFMT(r));
                numTerm++;
            }
            for(int j = 0; j < coef.KOLOM(); j++){
                float c = -coef.get(i,j);
                if(c != 0){
                    // Print c dan tanda
                    char sgn = c < 0f ? '-' : '+';
                    if(numTerm == 0){
                        if(sgn == '-')str.append(sgn);
                    }else{
                        str.append(" " + sgn + " ");
                    }
                    c *= c < 0f ? -1f : 1f;
                    if(c != 1f)str.append(floatFMT(c));

                    // Print nama parameter
                    char v = Character.toChars(Character.valueOf('a')+j)[0];
                    str.append(v);

                    numTerm++;
                }
            }
            if(i < coef.BARIS()-1)str.append('\n');
        }

        return str.toString();
    }
}
