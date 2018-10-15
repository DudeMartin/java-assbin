package assbin;

public class Vector4 extends Vector3 {

    public static final Vector4 ZERO = new Vector4(0, 0, 0, 0);
    public final float w;

    public Vector4(float x, float y, float z, float w) {
        super(x, y, z);
        this.w = w;
    }
}