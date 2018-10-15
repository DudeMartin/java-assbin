package assbin;

import java.awt.Color;
import java.nio.ByteBuffer;

public class AiLight {

    public static final int LIGHT_UNDEFINED = 0x0;
    public static final int LIGHT_DIRECTIONAL = 0x1;
    public static final int LIGHT_POINT = 0x2;
    public static final int LIGHT_SPOT = 0x3;
    public static final int LIGHT_AMBIENT = 0x4;
    public static final int LIGHT_AREA = 0x5;

    public final String name;
    public final int type;
    public final float attenuationConstant;
    public final float attenuationLinear;
    public final float attenuationQuadratic;
    public final Color colorDiffuse;
    public final Color colorSpecular;
    public final Color colorAmbient;
    public final float angleInnerCone;
    public final float angleOuterCone;

    AiLight(AssbinChunk chunk) {
        AssbinChunk.requireChunkId(chunk, AssbinChunk.CHUNK_AILIGHT, "Not a light chunk.");
        ByteBuffer chunkBuffer = chunk.data;
        name = AssbinChunk.getString(chunkBuffer);
        type = chunkBuffer.getInt();
        if (type == LIGHT_DIRECTIONAL) {
            attenuationConstant = 0;
            attenuationLinear = 0;
            attenuationQuadratic = 0;
        } else {
            attenuationConstant = chunkBuffer.getFloat();
            attenuationLinear = chunkBuffer.getFloat();
            attenuationQuadratic = chunkBuffer.getFloat();
        }
        colorDiffuse = AssbinChunk.getColor(chunkBuffer, false);
        colorSpecular = AssbinChunk.getColor(chunkBuffer, false);
        colorAmbient = AssbinChunk.getColor(chunkBuffer, false);
        if (type == LIGHT_SPOT) {
            angleInnerCone = chunkBuffer.getFloat();
            angleOuterCone = chunkBuffer.getFloat();
        } else {
            angleInnerCone = 0;
            angleOuterCone = 0;
        }
    }
}