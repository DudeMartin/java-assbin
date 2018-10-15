package assbin;

import java.nio.ByteBuffer;

public class AiScene {

    public final int flags;
    public final int meshCount;
    public final int materialCount;
    public final int animationCount;
    public final int textureCount;
    public final int lightCount;
    public final int cameraCount;
    public final AiNode rootNode;
    public final AiMesh[] meshes;
    public final AiMaterial[] materials;
    public final AiAnimation[] animations;
    public final AiTexture[] textures;
    public final AiLight[] lights;
    public final AiCamera[] cameras;

    AiScene(AssbinChunk chunk) {
        AssbinChunk.requireChunkId(chunk, AssbinChunk.CHUNK_AISCENE, "Not a scene chunk.");
        ByteBuffer chunkBuffer = chunk.data;
        flags = chunkBuffer.getInt();
        meshCount = chunkBuffer.getInt();
        materialCount = chunkBuffer.getInt();
        animationCount = chunkBuffer.getInt();
        textureCount = chunkBuffer.getInt();
        lightCount = chunkBuffer.getInt();
        cameraCount = chunkBuffer.getInt();
        rootNode = new AiNode(new AssbinChunk(chunkBuffer));
        meshes = new AiMesh[meshCount];
        for (int i = 0; i < meshes.length; i++) {
            meshes[i] = new AiMesh(new AssbinChunk(chunkBuffer));
        }
        materials = new AiMaterial[materialCount];
        for (int i = 0; i < materialCount; i++) {
            materials[i] = new AiMaterial(new AssbinChunk(chunkBuffer));
        }
        animations = new AiAnimation[animationCount];
        for (int i = 0; i < animationCount; i++) {
            animations[i] = new AiAnimation(new AssbinChunk(chunkBuffer));
        }
        textures = new AiTexture[textureCount];
        for (int i = 0; i < textureCount; i++) {
            textures[i] = new AiTexture(new AssbinChunk(chunkBuffer));
        }
        lights = new AiLight[lightCount];
        for (int i = 0; i < lightCount; i++) {
            lights[i] = new AiLight(new AssbinChunk(chunkBuffer));
        }
        cameras = new AiCamera[cameraCount];
        for (int i = 0; i < cameraCount; i++) {
            cameras[i] = new AiCamera(new AssbinChunk(chunkBuffer));
        }
    }
}