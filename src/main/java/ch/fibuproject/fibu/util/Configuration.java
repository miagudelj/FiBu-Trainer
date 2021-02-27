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

    private static String separator = File.separator;
    private static YamlConfig config;
    private static String path = System.getProperty("user.dir");
    private static String savedConfig = path + separator + "src" + separator + "main" + separator + "resources" + separator + "config.yml";
    private static String usedConfig = path + separator + ".." + separator + "config.yml";

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
    }

    /**
     * Creates config.yml if it doesn't exist
     */
    public static void createIfNotExists() {

        File configFile = new File(usedConfig);

        if (!configFile.exists()) {
            try {
                if (configFile.createNewFile()){
                    System.out.println("File creation successful at " + usedConfig);
                }
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
     * @throws IOException for Files.copy
     */
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