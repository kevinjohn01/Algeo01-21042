package matriks.util;

import java.text.DecimalFormat;

public class Format{
    static DecimalFormat df;

    public static String floatFMT(float f){
        // Lazy initialization
        if(df == null){
            df = new DecimalFormat("0");
            df.setMaximumFractionDigits(2);
        }
        return df.format(f);
    }

    public static String matWidth(Object o){
        return String.format("%5s", o);
    }

    public static String matrixElmtFMT(float f){
        return matWidth(floatFMT(f));
    }
}
