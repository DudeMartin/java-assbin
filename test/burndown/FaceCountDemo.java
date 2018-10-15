package burndown;

import assbin.AssbinReader;

import java.io.File;
import java.net.URL;
import java.util.Arrays;

public final class FaceCountDemo {

    public static void main(String[] args) throws Exception {
        URL url = new File(args.length > 0 ? args[0] : "scene.assbin").toURI().toURL();
        AssbinReader reader = new AssbinReader(url);
        int faceCount = Arrays.stream(reader.scene.meshes).mapToInt(mesh -> mesh.faceCount).sum();
        System.out.println("The scene has " + faceCount + " faces.");
    }
}