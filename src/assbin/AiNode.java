package assbin;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class AiNode {

    private static final int METADATA_BOOL = 0;
    private static final int METADATA_INT32 = 1;
    private static final int METADATA_UINT64 = 2;
    private static final int METADATA_FLOAT = 3;
    private static final int METADATA_DOUBLE = 4;
    private static final int METADATA_AISTRING = 5;
    private static final int METADATA_AIVECTOR3D = 6;

    public final String name;
    public final float[][] transformMatrix;
    public final int childCount;
    public final int meshCount;
    public final int propertyCount;
    public final int[] meshIndices;
    public final AiNode[] children;
    public final Map<String, Object> properties;

    AiNode(AssbinChunk chunk) {
        AssbinChunk.requireChunkId(chunk, AssbinChunk.CHUNK_AINODE, "Not a node chunk.");
        ByteBuffer chunkBuffer = chunk.data;
        name = AssbinChunk.getString(chunkBuffer);
        transformMatrix = AssbinChunk.getMatrix(chunkBuffer);
        childCount = chunkBuffer.getInt();
        meshCount = chunkBuffer.getInt();
        propertyCount = chunkBuffer.getInt();
        meshIndices = new int[meshCount];
        for (int i = 0; i < meshCount; i++) {
            meshIndices[i] = chunkBuffer.getInt();
        }
        children = new AiNode[childCount];
        for (int i = 0; i < childCount; i++) {
            children[i] = new AiNode(new AssbinChunk(chunkBuffer));
        }
        properties = new HashMap<String, Object>();
        for (int i = 0; i < propertyCount; i++) {
            String key = AssbinChunk.getString(chunkBuffer);
            Object value;
            switch (chunkBuffer.getShort()) {
                case METADATA_BOOL:
                    value = chunkBuffer.get() == 1;
                    break;
                case METADATA_INT32:
                    value = chunkBuffer.getInt();
                    break;
                case METADATA_UINT64:
                    value = chunkBuffer.getLong();
                    break;
                case METADATA_FLOAT:
                    value = chunkBuffer.getFloat();
                    break;
                case METADATA_DOUBLE:
                    value = chunkBuffer.getDouble();
                    break;
                case METADATA_AISTRING:
                    value = AssbinChunk.getString(chunkBuffer);
                    break;
                case METADATA_AIVECTOR3D:
                    value = AssbinChunk.getVector3(chunkBuffer);
                    break;
                default:
                    value = null;
                    break;
            }
            properties.put(key, value);
        }
    }
}