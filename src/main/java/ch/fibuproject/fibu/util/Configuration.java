package ch.fibuproject.fibu.util;

import com.github.jsixface.YamlConfig;

import java.io.*;

/**
 * Vinicius
 * 05.Feb 2021
 * Version: 1.0
 * Project description:
 * Configuration class for config.yml this was code given to us by a friend - https://github.com/nighty34
 */

public class Configuration {

    private static YamlConfig config;
    private static String path = (new File(Configuration.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getParentFile().getParentFile().toString() + File.separator + "config.yml").replace("file:", "");

    /**
     * Initialises configuration
     */
    public static void init() {
        System.out.println(path);
        createIfNotExists();

        try{
            InputStream resource = new FileInputStream(path);
            System.out.println(resource.available());
            config = YamlConfig.load(resource);
        }catch (IOException ex){
            ex.printStackTrace();
            System.out.println("Config konnte nicht gelesen werden.");
        }
        //TODO: Replace try catch with throws
    }


    /**
     * Creates config.yml if it doesn't exist
     */
    public static void createIfNotExists() {

        File configFile = new File(path);

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("The file could not be created at " + path);
            }
        }

        try {
            writeStandardConfig(configFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not write standard config information to config file");
        }
    }

    /**
     * Writes standardconfig
     * @param configFile config File
     * @throws IOException
     */

    //TODO: Change method to copy default config instead of writing it via code.
    private static void writeStandardConfig(File configFile) throws IOException {

        FileWriter fileWriter = new FileWriter(configFile);

        //TODO add database
        String contents = "MySQL:\n" +
                "  database: fibudb\n" +
                "  host: localhost\n" +
                "  port: 3306\n" +
                "  username: root\n" +
                "  password: \"\"\n" +
                "  poolsize: 100";

        fileWriter.write(contents);
        fileWriter.close();
    }


    /**
     * Returns configuration
     * @return config
     */
    public static YamlConfig getConfig() {
        return config;
    }
}