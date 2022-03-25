package com.httpServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ServerProperties {

   public static Properties getServerProperties(){
       Properties properties = null;
       try {
           properties.load(new FileInputStream("config.properties"));
           String port = properties.getProperty("port");
           String rootDirectory = properties.getProperty("rootDirectory");

//           System.out.println(properties.getProperty("port"));
//           System.out.println(properties);

        } catch (NullPointerException | IOException e) {
            if(properties == null){
                System.out.println("Program crashed");
                Runtime.getRuntime().exit(0);
//                System.exit(0);

            }
       }
       return properties;
   }

}


