package assbin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class AssbinReader {

    public final int majorVersion;
    public final int minorVersion;
    public final int revision;
    public final int compileFlags;
    public final String sourceFileName;
    public final String commandParameters;
    public final AiScene scene;

    public AssbinReader(URL assbinAddress) throws IOException, AssbinException {
        ByteBuffer assbinBuffer;
        InputStream assbinStream = assbinAddress.openStream();
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = assbinStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }
            assbinBuffer = ByteBuffer.wrap(outputStream.toByteArray()).order(ByteOrder.LITTLE_ENDIAN);
        } finally {
            assbinStream.close();
        }
        if (!AssbinChunk.getString(assbinBuffer, 44, "US-ASCII").startsWith("ASSIMP.binary")) {
            throw new AssbinException("Not a .assbin file.");
        }
        majorVersion = assbinBuffer.getInt();
        minorVersion = assbinBuffer.getInt();
        revision = assbinBuffer.getInt();
        compileFlags = assbinBuffer.getInt();
        if (assbinBuffer.getShort() != 0) {
            throw new AssbinException("Not a normal .assbin file.");
        }
        if (assbinBuffer.getShort() == 1) {
            throw new AssbinException("Compressed files are not supported.");
        }
        byte[] stringBuffer = new byte[256];
        assbinBuffer.get(stringBuffer);
        sourceFileName = AssbinChunk.getString(stringBuffer, indexOfFirstZero(stringBuffer, 256), "UTF-8");
        assbinBuffer.get(stringBuffer, 0, 128);
        commandParameters = AssbinChunk.getString(stringBuffer, indexOfFirstZero(stringBuffer, 128), "UTF-8");
        assbinBuffer.position(assbinBuffer.position() + 64);
        scene = new AiScene(new AssbinChunk(assbinBuffer));
    }

    private static int indexOfFirstZero(byte[] array, int limit) {
        for (int i = 0; i < limit; i++) {
            if (array[i] == 0) {
                return i;
            }
        }
        return -1;
    }
}