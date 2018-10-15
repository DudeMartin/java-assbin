package assbin;

import java.nio.ByteBuffer;

public class AiMaterial {

    public final int propertyCount;
    public final AiMaterialProperty[] properties;

    AiMaterial(AssbinChunk chunk) {
        AssbinChunk.requireChunkId(chunk, AssbinChunk.CHUNK_AIMATERIAL, "Not a material chunk.");
        ByteBuffer chunkBuffer = chunk.data;
        propertyCount = chunkBuffer.getInt();
        properties = new AiMaterialProperty[propertyCount];
        for (int i = 0; i < propertyCount; i++) {
            properties[i] = new AiMaterialProperty(new AssbinChunk(chunkBuffer));
        }
    }
}