package sarvika.simpleserver.ResourceHandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sarvika.simpleserver.ResponseStatus.ResponseStatus;
import sarvika.simpleserver.ServerProperties;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResourceHandler {

    private  static Logger log = LogManager.getLogger(ResourceHandler.class.getName());
    private String databasePath;
    private ResponseStatus responseStatus;

    public ResourceHandler() {
        this.responseStatus = new ResponseStatus();
        log.info("Resource Class has been initialzed");
        try {
            ServerProperties serverProperties = new ServerProperties("config.properties");
            this.databasePath = serverProperties.getWorkingDir();
        } catch (IOException e) {
            log.debug("Couldn't locate the config.properties file"+e);
            e.printStackTrace();
        }
    }

    public String checkFileExtension(File filein){
        try {
            return Files.probeContentType(Path.of(filein.getPath()));
        } catch (IOException e) {
            log.info("Couldn't locate the requested resource file check file path"+e);
            e.printStackTrace();
        }
        return "Invalid";
    }

    public void getMeThisResource(OutputStream outputStream, String filepath){
        File file = new File(databasePath.concat(filepath));

        if(file.exists()){
                responseStatus.sendOkResponse(outputStream,checkFileExtension(file));
                new OutputStreamHandler().sendData(outputStream,file);

        }
        else{   responseStatus.sendNotFoundResponse(outputStream);  }
    }
    public void putThisResource(){}
}
