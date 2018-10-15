package assbin;

public class Vector3 {

    public static final Vector3 ZERO = new Vector3(0, 0, 0);
    public final float x;
    public final float y;
    public final float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}