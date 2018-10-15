package assbin;

import java.nio.ByteBuffer;

public class AiMaterialProperty {

    public final String key;
    public final int semantic;
    public final int index;
    public final int dataLength;
    public final int type;
    public final byte[] data;

    AiMaterialProperty(AssbinChunk chunk) {
        AssbinChunk.requireChunkId(chunk, AssbinChunk.CHUNK_AIMATERIALPROPERTY, "Not a material property chunk.");
        ByteBuffer chunkBuffer = chunk.data;
        key = AssbinChunk.getString(chunkBuffer);
        semantic = chunkBuffer.getInt();
        index = chunkBuffer.getInt();
        dataLength = chunkBuffer.getInt();
        type = chunkBuffer.getInt();
        data = new byte[dataLength];
        chunkBuffer.get(data);
    }
}