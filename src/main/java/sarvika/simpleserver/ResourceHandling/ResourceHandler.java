package sarvika.simpleserver.ResourceHandling;

import sarvika.simpleserver.ResponseStatus.ResponseStatus;
import sarvika.simpleserver.ServerProperties;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResourceHandler {

    private String databasePath;
    private File file;
    private ResponseStatus responseStatus;

    public ResourceHandler() {
        this.responseStatus = new ResponseStatus();
        try {
            ServerProperties serverProperties = new ServerProperties("config.properties");
            this.databasePath = serverProperties.getWorkingDir();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String checkFileExtension(File filein){
        try {
            return Files.probeContentType(Path.of(filein.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Invalid";
    }

    public void getMeThisResource(OutputStream outputStream, String filepath){

        this.file = new File(databasePath.concat(filepath));

        if(file.exists()){
            try {
                responseStatus.sendOkResponse(outputStream,checkFileExtension(file));
                new OutputStreamHandler().sendData(outputStream,file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                responseStatus.sendNotFoundResponse(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void putThisResource(){}
}
