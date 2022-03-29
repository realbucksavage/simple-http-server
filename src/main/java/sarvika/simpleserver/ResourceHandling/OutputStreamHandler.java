package sarvika.simpleserver.ResourceHandling;

import java.io.*;
import java.util.Objects;

class OutputStreamHandler {

    public void sendData(OutputStream socketOutputStream, File file){

        if (file.isFile()) {
            int readLength;
            byte[] dataBytes = new byte[6015000];
            try {
                InputStream inputStream = new FileInputStream(file.getAbsoluteFile());
                while (-1 != (readLength = inputStream.read(dataBytes,0,6000000))) {
                    socketOutputStream.write(dataBytes, 0, readLength);
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            File[] list = file.listFiles();
            for (File temp : Objects.requireNonNull(list)) {
                try {
                    socketOutputStream.write((temp.getName() + "\n").getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
