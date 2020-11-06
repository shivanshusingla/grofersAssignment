package common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <h1>Base Utils Class</h1> <B>BaseUtils Class is working as a Base utils Class
 * for all classes in all common classes. to get different OS name, takeSnapShot on failure test
 * case.</B>
 *
 * @author Shivanshu Singla
 * @version 1.0
 * @since 05-11-2020
 */

public class BaseUtils {

  public static Date getCurrentDateTime() {
    DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
    Date date = new Date();
    String currentDate = dateFormat.format(date);
    Date dateFromString = null;
    try {
      dateFromString = dateFormat.parse(currentDate);
    } catch (ParseException e) {
    }
    return dateFromString;
  }

  public static Date getCurrentDateTime(String pattern) { // for eg.-pattern="dd-MM-yyyy HH:mm:ss"
    DateFormat dateFormat = new SimpleDateFormat(pattern);
    Date date = new Date();
    String currentDate = dateFormat.format(date);
    Date dateFromString = null;
    try {
      dateFromString = dateFormat.parse(currentDate);
    } catch (ParseException e) {
    }
    return dateFromString;
  }

  public static boolean netIsAvailable() {
    try {
      final URL url = new URL("http://www.google.com");
      final URLConnection conn = url.openConnection();
      conn.connect();
      return true;
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      return false;
    }
  }
}
