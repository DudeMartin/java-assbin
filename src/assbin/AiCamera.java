package assbin;

import java.nio.ByteBuffer;

public class AiCamera {

    public final String name;
    public final Vector3 position;
    public final Vector3 targetPosition;
    public final Vector3 upDirection;
    public final float fov;
    public final float zNear;
    public final float zFar;
    public final float aspectRatio;

    AiCamera(AssbinChunk chunk) {
        AssbinChunk.requireChunkId(chunk, AssbinChunk.CHUNK_AICAMERA, "Not a camera chunk.");
        ByteBuffer chunkBuffer = chunk.data;
        name = AssbinChunk.getString(chunkBuffer);
        position = AssbinChunk.getVector3(chunkBuffer);
        targetPosition = AssbinChunk.getVector3(chunkBuffer);
        upDirection = AssbinChunk.getVector3(chunkBuffer);
        fov = chunkBuffer.getFloat();
        zNear = chunkBuffer.getFloat();
        zFar = chunkBuffer.getFloat();
        aspectRatio = chunkBuffer.getFloat();
    }
}