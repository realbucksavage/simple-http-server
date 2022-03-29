package sarvika.simpleserver;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {

        String configFileLocation = "config.properties";
        if (args.length > 0) {
            if (StringUtils.isNotBlank(args[0])) {
                configFileLocation = args[0];
            }
        }

        // TODO: say what configuration file is being loaded
        final ServerProperties properties = new ServerProperties(configFileLocation);
        final Server sv = new Server(properties);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                sv.stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        sv.start();
    }
}

