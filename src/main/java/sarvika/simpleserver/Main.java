package sarvika.simpleserver;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private final static Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {

        logger.info("Entering Application");
        String configFileLocation = "config.properties";
        if (args.length > 0) {
            logger.info("Value for args.length >>>>>"+args.length);
            if (StringUtils.isNotBlank(args[0])) {
                configFileLocation = args[0];
            }
        }

        // TODO: say what configuration file is being loaded
        logger.info("I got Configuration File >>>>>> "+configFileLocation);
        final ServerProperties properties = new ServerProperties(configFileLocation);
        final Server sv = new Server(properties);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                sv.stop();
            } catch (IOException e) {
                logger.error("Exception while closing server");
                e.printStackTrace();
            }
        }));
        sv.start();
    }
}

