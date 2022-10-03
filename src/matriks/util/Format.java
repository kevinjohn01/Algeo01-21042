package matriks.util;

import java.text.DecimalFormat;

public class Format{
    static DecimalFormat df;

    public static String floatFMT(float f){
        return f % 1f != 0f ? String.format("%f", f) : String.format("%.0f", f);
    }

    public static String matWidth(Object o){
        return String.format("%5s", o);
    }

    public static String matrixElmtFMT(float f){
        // Lazy initialization
        if(df == null){
            df = new DecimalFormat("0");
            df.setMaximumFractionDigits(2);
        }
        return matWidth(df.format(f));
    }
}
