package assbin;

import java.nio.ByteBuffer;

public class AiTexture {

    public final int width;
    public final int height;
    public final byte[] formatHint;
    public final byte[] data;

    AiTexture(AssbinChunk chunk) {
        AssbinChunk.requireChunkId(chunk, AssbinChunk.CHUNK_AITEXTURE, "Not a texture chunk.");
        ByteBuffer chunkBuffer = chunk.data;
        width = chunkBuffer.getInt();
        height = chunkBuffer.getInt();
        formatHint = new byte[4];
        chunkBuffer.get(formatHint);
        data = isCompressed() ? new byte[width] : new byte[width * height * 4];
        chunkBuffer.get(data);
    }

    public boolean isCompressed() {
        return height == 0;
    }
}