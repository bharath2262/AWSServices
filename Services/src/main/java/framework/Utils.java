package framework;

import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Random;

public class Utils {


    public static Properties loadConfig(String filePath) {
        Properties properties = new Properties();
        try {
            InputStream in = new FileInputStream(filePath);
            properties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading " + filePath + ".properties");
        }
        return properties;
    }

    public static String getRandomNumberString() {
        Random rand=new Random();
        int max=10000;
        int min=1000;
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return Integer.toString(randomNum);
    }

    public static String writeToFile(String fileName, String content, String folderPath) {
        String caller = Thread.currentThread().getStackTrace()[2].getMethodName();
        File requestFolder = new File(folderPath);
        if (!requestFolder.exists())
            requestFolder.mkdir();
        String requestFilePath = folderPath + fileName;
        return write(requestFilePath, content);
    }

    public static String write(String requestFilePath, String content) {
        java.io.FileWriter fw = null;
        try {
            fw = new FileWriter(requestFilePath);
            fw.write(content);
            fw.close();
            return requestFilePath;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static void statusCodeValidation(ClientResponse response,int status_code) {
        if (response.getStatus() != status_code) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
    }
}
