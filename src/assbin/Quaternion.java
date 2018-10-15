package assbin;

public final class Quaternion extends Vector4 {

    public static final Quaternion IDENTITY = new Quaternion(0, 0, 0, 1);

    public Quaternion(float x, float y, float z, float w) {
        super(x, y, z, w);
    }

    public Quaternion(Vector3 axis, float angle) {
        this(axis, angle / 2, (float) Math.sin(angle / 2));
    }

    private Quaternion(Vector3 axis, float halfAngle, float sinHalfAngle) {
        super(axis.x * sinHalfAngle, axis.y * sinHalfAngle, axis.z * sinHalfAngle, (float) Math.cos(halfAngle));
    }
}