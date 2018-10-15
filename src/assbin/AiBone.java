package assbin;

import java.nio.ByteBuffer;

public class AiBone {

    public final String name;
    public final int weightCount;
    public final float[][] transformMatrix;
    public final AiVertexWeight[] weights;

    AiBone(AssbinChunk chunk) {
        AssbinChunk.requireChunkId(chunk, AssbinChunk.CHUNK_AIBONE, "Not a bone chunk.");
        ByteBuffer chunkBuffer = chunk.data;
        name = AssbinChunk.getString(chunkBuffer);
        weightCount = chunkBuffer.getInt();
        transformMatrix = AssbinChunk.getMatrix(chunkBuffer);
        weights = new AiVertexWeight[weightCount];
        for (int i = 0; i < weightCount; i++) {
            weights[i] = new AiVertexWeight(chunkBuffer);
        }
    }

    public static class AiVertexWeight {

        public final int id;
        public final float weight;

        private AiVertexWeight(ByteBuffer buffer) {
            id = buffer.getInt();
            weight = buffer.getFloat();
        }
    }
}