package org.zs;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String sPATH = getRootPath(Main.class);
        String SEPARATOR = FileSystems.getDefault().getSeparator();
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        String sMonth = currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN);

        /* ... (4) TAGESTOUR\Tagestour 2025\2025 Tagestour_10 Oktober */
        String fileNameOld = sPATH + SEPARATOR + "(4) TAGESTOUR" + SEPARATOR
                + "Tagestour " + year + SEPARATOR + year + " Tagestour_" + month + " " + sMonth + ".xlsx";

        //-- check directory for year
        String dirTo = sPATH + SEPARATOR + "(4) TAGESTOUR" + SEPARATOR + "Tagestour taegliche Sicherung" + SEPARATOR + year;
        File dirToNew = new File(dirTo);
        if (!dirToNew.exists() && !dirToNew.mkdir()) {
            return;
        }

        //-- check directory for month
        dirTo = dirTo + SEPARATOR + sMonth;
        dirToNew = new File(dirTo);
        if (!dirToNew.exists() && !dirToNew.mkdir()) {
            return;
        }

        String fileNameNew = dirToNew + SEPARATOR + "Tagestour" + "_" + currentDate.format(myFormat).replace('-', '_') + ".xlsx";
        Files.copy(Paths.get(fileNameOld), Paths.get(fileNameNew), StandardCopyOption.REPLACE_EXISTING);
    }

    public static String getRootPath(Class clazz) throws URISyntaxException {
        URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
        String sURI = Paths.get(url.toURI()).toString();
        sURI = sURI.substring(0, sURI.indexOf("Batch"));
        return sURI;
    }
}