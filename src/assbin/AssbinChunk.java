package assbin;

import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

class AssbinChunk {

    static final int CHUNK_AICAMERA = 0x1234;
    static final int CHUNK_AILIGHT = 0x1235;
    static final int CHUNK_AITEXTURE = 0x1236;
    static final int CHUNK_AIMESH = 0x1237;
    static final int CHUNK_AINODEANIM = 0x1238;
    static final int CHUNK_AISCENE = 0x1239;
    static final int CHUNK_AIBONE = 0x123a;
    static final int CHUNK_AIANIMATION = 0x123b;
    static final int CHUNK_AINODE = 0x123c;
    static final int CHUNK_AIMATERIAL = 0x123d;
    static final int CHUNK_AIMATERIALPROPERTY = 0x123e;

    final int id;
    final ByteBuffer data;

    AssbinChunk(ByteBuffer chunkBuffer) {
        id = chunkBuffer.getInt();
        int length = chunkBuffer.getInt();
        int newPosition = chunkBuffer.position() + length;
        int originalLimit = chunkBuffer.limit();
        chunkBuffer.limit(newPosition);
        data = chunkBuffer.slice().order(ByteOrder.LITTLE_ENDIAN);
        chunkBuffer.limit(originalLimit);
        chunkBuffer.position(newPosition);
    }

    static void requireChunkId(AssbinChunk chunk, int id, String mismatchMessage) {
        if (chunk.id != id) {
            throw new IllegalArgumentException(new AssbinException(mismatchMessage));
        }
    }

    static String getString(ByteBuffer buffer) {
        byte[] stringBytes = new byte[buffer.getInt()];
        buffer.get(stringBytes);
        try {
            return new String(stringBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new Error("The UTF-8 charset is not supported!", e);
        }
    }

    static float[][] getMatrix(ByteBuffer buffer) {
        float[][] elements = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                elements[i][j] = buffer.getFloat();
            }
        }
        return elements;
    }

    static Vector3 getVector3(ByteBuffer buffer) {
        return new Vector3(buffer.getFloat(), buffer.getFloat(), buffer.getFloat());
    }

    static Color getColor(ByteBuffer buffer, boolean alpha) {
        return new Color(
                buffer.getFloat(),
                buffer.getFloat(),
                buffer.getFloat(),
                alpha ? buffer.getFloat() : 1);
    }
}