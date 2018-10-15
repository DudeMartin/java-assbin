package assbin;

import java.io.File;
import java.net.URL;

public final class FaceCountDemo {

    public static void main(String[] args) throws Exception {
        URL url = new File(args.length > 0 ? args[0] : "scene.assbin").toURI().toURL();
        AssbinReader reader = new AssbinReader(url);
        int faceCount = 0;
        for (AiMesh mesh : reader.scene.meshes) {
            faceCount += mesh.faceCount;
        }
        System.out.println("The scene has " + faceCount + " faces.");
    }
}