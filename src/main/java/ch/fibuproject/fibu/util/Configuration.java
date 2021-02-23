package ch.fibuproject.fibu.util;

import com.github.jsixface.YamlConfig;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * @author Ciro Brodmann
 * @author Felix Reiniger
 *
 * Configuration class for config.yml. Reused from an older project.
 */

public class Configuration {

    private static String seperator = File.separator;
    private static YamlConfig config;
    private static String path = System.getProperty("user.dir");
    private static String savedConfig = path + seperator + "src" + seperator + "main" + seperator + "resources" + seperator + "config.yml";
    private static String usedConfig = path + seperator + ".." + seperator + "config.yml";

    /**
     * Initialises configuration
     */
    public static void init() {
        createIfNotExists();

        try {
            InputStream resource = new FileInputStream(usedConfig);
            System.out.println(resource.available());
            config = YamlConfig.load(resource);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Config konnte nicht gelesen werden.");
        }
        //TODO: Replace try catch with throw
    }

    /**
     * Creates config.yml if it doesn't exist
     */
    public static void createIfNotExists() {

        File configFile = new File(usedConfig);

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("The file could not be created at " + usedConfig);
            }
        }

        try {
            writeStandardConfig();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not write standard config information to config file");
        }
    }

    /**
     * Writes standardconfig
     * @throws IOException
     */

    //TODO: Change method to copy default config instead of writing it via code.
    private static void writeStandardConfig() throws IOException {
        Files.copy(new File(savedConfig).toPath(), new File(usedConfig).toPath(), StandardCopyOption.REPLACE_EXISTING);
    }


    /**
     * Returns configuration
     * @return config
     */
    public static YamlConfig getConfig() {
        return config;
    }
}