package assbin;

import java.nio.ByteBuffer;

public class AiAnimation {

    public final String name;
    public final double duration;
    public final double ticksPerSecond;
    public final int channelCount;
    public final AiNodeAnimation[] channels;

    AiAnimation(AssbinChunk chunk) {
        AssbinChunk.requireChunkId(chunk, AssbinChunk.CHUNK_AIANIMATION, "Not an animation chunk.");
        ByteBuffer chunkBuffer = chunk.data;
        name = AssbinChunk.getString(chunkBuffer);
        duration = chunkBuffer.getDouble();
        ticksPerSecond = chunkBuffer.getDouble();
        channelCount = chunkBuffer.getInt();
        channels = new AiNodeAnimation[channelCount];
        for (int i = 0; i < channelCount; i++) {
            channels[i] = new AiNodeAnimation(new AssbinChunk(chunkBuffer));
        }
    }
}