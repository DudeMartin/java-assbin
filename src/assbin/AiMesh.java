package assbin;

import java.awt.Color;
import java.nio.ByteBuffer;

public class AiMesh {

    public static final int MESH_HAS_POSITIONS = 0x1;
    public static final int MESH_HAS_NORMALS = 0x2;
    public static final int MESH_HAS_TANGENTS_AND_BITANGENTS = 0x4;
    public static final int MESH_HAS_TEXCOORD_BASE = 0x100;
    public static final int MESH_HAS_COLOR_BASE = 0x10000;
    public static final int MESH_MAX_NUMBER_OF_COLOR_SETS = 0x8;
    public static final int MESH_MAX_NUMBER_OF_TEXTURECOORDS = 0x8;

    public final int primitiveTypes;
    public final int vertexCount;
    public final int faceCount;
    public final int boneCount;
    public final int materialIndex;
    public final int flags;
    public final Vector4[] vertices;
    public final Vector3[] normals;
    public final Vector3[] tangents;
    public final Vector3[] bitangents;
    public final Color[][] colorSets;
    public final int[] textureCoordinateComponentCounts;
    public final Vector3[][] textureCoordinateSets;
    public final int[][] faceIndices;
    public final AiBone[] bones;

    AiMesh(AssbinChunk chunk) {
        AssbinChunk.requireChunkId(chunk, AssbinChunk.CHUNK_AIMESH, "Not a mesh chunk.");
        ByteBuffer chunkBuffer = chunk.data;
        primitiveTypes = chunkBuffer.getInt();
        vertexCount = chunkBuffer.getInt();
        faceCount = chunkBuffer.getInt();
        boneCount = chunkBuffer.getInt();
        materialIndex = chunkBuffer.getInt();
        flags = chunkBuffer.getInt();
        if ((flags & MESH_HAS_POSITIONS) == 0) {
            vertices = new Vector4[0];
        } else {
            vertices = new Vector4[vertexCount];
            for (int i = 0; i < vertexCount; i++) {
                vertices[i] = new Vector4(chunkBuffer.getFloat(), chunkBuffer.getFloat(), chunkBuffer.getFloat(), 1);
            }
        }
        if ((flags & MESH_HAS_NORMALS) == 0) {
            normals = new Vector3[0];
        } else {
            normals = getVector3Array(chunkBuffer, vertexCount);
        }
        if ((flags & MESH_HAS_TANGENTS_AND_BITANGENTS) == 0) {
            tangents = new Vector3[0];
            bitangents = new Vector3[0];
        } else {
            tangents = getVector3Array(chunkBuffer, vertexCount);
            bitangents = getVector3Array(chunkBuffer, vertexCount);
        }
        int colorSetCount = 0;
        while (colorSetCount < MESH_MAX_NUMBER_OF_COLOR_SETS
                && (flags & (MESH_HAS_COLOR_BASE << colorSetCount)) != 0) {
            colorSetCount++;
        }
        colorSets = new Color[colorSetCount][vertexCount];
        for (int i = 0; i < colorSetCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                colorSets[i][j] = AssbinChunk.getColor(chunkBuffer, true);
            }
        }
        int textureCoordinateSetCount = 0;
        while (textureCoordinateSetCount < MESH_MAX_NUMBER_OF_TEXTURECOORDS
                && (flags & (MESH_HAS_TEXCOORD_BASE << textureCoordinateSetCount)) != 0) {
            textureCoordinateSetCount++;
        }
        textureCoordinateComponentCounts = new int[textureCoordinateSetCount];
        textureCoordinateSets = new Vector3[textureCoordinateSetCount][];
        for (int i = 0; i < textureCoordinateSetCount; i++) {
            textureCoordinateComponentCounts[i] = chunkBuffer.getInt();
            textureCoordinateSets[i] = getVector3Array(chunkBuffer, vertexCount);
        }
        faceIndices = new int[faceCount][];
        final boolean compactIndices = vertexCount < (1 << 16);
        for (int i = 0; i < faceCount; i++) {
            int indexCount = chunkBuffer.getShort();
            int[] indices = new int[indexCount];
            faceIndices[i] = indices;
            for (int j = 0; j < indexCount; j++) {
                indices[j] = compactIndices ? (chunkBuffer.getShort() & 0xffff) : chunkBuffer.getInt();
            }
        }
        bones = new AiBone[boneCount];
        for (int i = 0; i < boneCount; i++) {
            bones[i] = new AiBone(new AssbinChunk(chunkBuffer));
        }
    }

    private static Vector3[] getVector3Array(ByteBuffer buffer, int length) {
        Vector3[] array = new Vector3[length];
        for (int i = 0; i < length; i++) {
            array[i] = AssbinChunk.getVector3(buffer);
        }
        return array;
    }
}