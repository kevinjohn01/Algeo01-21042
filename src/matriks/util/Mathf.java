package matriks.util;

public class Mathf{
    public static final float EPSILON = 0.000001f;

    public static boolean zero(float val){
        return Math.abs(val) <= EPSILON;
    }

    public static boolean one(float val){
        return equal(val, 1f);
    }

    public static boolean equal(float a, float b){
        return zero(a - b);
    }
}
