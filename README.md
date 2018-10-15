# java-assbin
A fast and small Java library for reading `.assbin` files. It creates an immutable
representation of the scene described by the file, whose contents are exposed by
`public` fields in the classes corresponding to the various scene elements.

## Sample usage
Suppose you have a file called `scene.assbin` in your current directory and you
want to write a program that outputs the total number of faces in the scene.

```java
public class FaceCountDemo {

    public static void main(String[] args) throws Exception {
        URL url = new File("scene.assbin").toURI().toURL();
        AssbinReader reader = new AssbinReader(url);
        int faceCount = Arrays.stream(reader.scene.meshes).mapToInt(mesh -> mesh.faceCount).sum();
        System.out.println("The scene has " + faceCount + " faces.");
    }
}
``` 

