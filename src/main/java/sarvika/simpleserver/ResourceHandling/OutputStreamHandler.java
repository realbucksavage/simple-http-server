package sarvika.simpleserver.ResourceHandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sarvika.simpleserver.Main;

import java.io.*;
import java.util.Objects;

class OutputStreamHandler {
    static Logger log = LogManager.getLogger(Main.class.getName());

    public void sendData(OutputStream socketOutputStream, File file){
        log.info("OutputStreamHandler Class has been initialzed");


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
                log.info("Couldn't locate/get the file/inputstream of the resource or couldn't able to write into client outputStream socket"+e);
                e.printStackTrace();
            }
        }
        else {
            File[] list = file.listFiles();
            for (File temp : Objects.requireNonNull(list)) {
                try {
                    socketOutputStream.write((temp.getName() + "\n").getBytes());
                } catch (IOException e) {
                    log.info("Couldn't locate/get the file/inputstream of the resource or couldn't able to write into client outputStream socket"+e);
                    e.printStackTrace();
                }
            }
        }
    }
}
