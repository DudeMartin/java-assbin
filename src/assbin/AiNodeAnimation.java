package assbin;

import java.nio.ByteBuffer;

public class AiNodeAnimation {

    public final String name;
    public final int positionKeyCount;
    public final int rotationKeyCount;
    public final int scaleKeyCount;
    public final int preState;
    public final int postState;
    public final VectorKey[] positionKeys;
    public final QuaternionKey[] rotationKeys;
    public final VectorKey[] scaleKeys;

    AiNodeAnimation(AssbinChunk chunk) {
        AssbinChunk.requireChunkId(chunk, AssbinChunk.CHUNK_AINODEANIM, "Not a node animation chunk.");
        ByteBuffer chunkBuffer = chunk.data;
        name = AssbinChunk.getString(chunkBuffer);
        positionKeyCount = chunkBuffer.getInt();
        rotationKeyCount = chunkBuffer.getInt();
        scaleKeyCount = chunkBuffer.getInt();
        preState = chunkBuffer.getInt();
        postState = chunkBuffer.getInt();
        positionKeys = new VectorKey[positionKeyCount];
        for (int i = 0; i < positionKeyCount; i++) {
            positionKeys[i] = new VectorKey(chunkBuffer);
        }
        rotationKeys = new QuaternionKey[rotationKeyCount];
        for (int i = 0; i < rotationKeyCount; i++) {
            rotationKeys[i] = new QuaternionKey(chunkBuffer);
        }
        scaleKeys = new VectorKey[scaleKeyCount];
        for (int i = 0; i < scaleKeyCount; i++) {
            scaleKeys[i] = new VectorKey(chunkBuffer);
        }
    }

    public static class VectorKey extends Key<Vector3> {

        private VectorKey(ByteBuffer buffer) {
            super(buffer);
        }

        @Override
        Vector3 getValue(ByteBuffer buffer) {
            return AssbinChunk.getVector3(buffer);
        }
    }

    public static class QuaternionKey extends Key<Quaternion> {

        private QuaternionKey(ByteBuffer buffer) {
            super(buffer);
        }

        @Override
        Quaternion getValue(ByteBuffer buffer) {
            float w = buffer.getFloat();
            return new Quaternion(buffer.getFloat(), buffer.getFloat(), buffer.getFloat(), w);
        }
    }

    public static abstract class Key<T> {

        public final double time;
        public final T value;

        private Key(ByteBuffer buffer) {
            time = buffer.getDouble();
            value = getValue(buffer);
        }

        abstract T getValue(ByteBuffer buffer);
    }
}