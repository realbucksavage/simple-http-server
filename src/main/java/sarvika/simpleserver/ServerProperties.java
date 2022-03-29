package sarvika.simpleserver;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ServerProperties {

    private Integer port;
    private String workingDir;

    private Properties properties;

    public ServerProperties(String configurationFile) throws IOException {
        File file = new File(configurationFile);
        Properties properties = new Properties();
        properties.load(new FileReader(file));

        this.properties = properties;
    }

    public int getPort() {
        if (this.port == null) {
            String portValue = this.properties.getProperty("port", "8080");
            try {
                this.port = Integer.parseInt(portValue);
            } catch (NumberFormatException ex) {
                // TODO: handle bad port value case

            }
        }

        return this.port;
    }

    public String getWorkingDir() {
        if (this.workingDir == null) {
            this.workingDir = this.properties.getProperty("working-directory");
            if (StringUtils.isBlank(this.workingDir)) {
                throw new IllegalArgumentException("working-directory is a required property");
            }
        }

        return workingDir;
    }
}

