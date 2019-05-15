package ca.freedommobile.api.framework.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {

    public static  String getReportFileName(){
        Path path = Paths.get("target","reports");
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmSS");

        return path.toString()+"/report"+format.format(new Date())+".html";
    }
}
