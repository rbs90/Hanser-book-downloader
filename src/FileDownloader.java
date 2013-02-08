import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created with IntelliJ IDEA.
 * User: rbs
 * Date: 08.02.13
 * Time: 19:37
 * To change this template use File | Settings | File Templates.
 */
public class FileDownloader {

    public static void get(String href, String target_name){
        try {
            // create CookieManager
            CookieManager manager = new CookieManager();
            manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(manager);
            CookieStore cookieJar = manager.getCookieStore();

            URLConnection website = (new URL(href)).openConnection();
            ReadableByteChannel rbc = Channels.newChannel(website.getInputStream());
            File out = new File("tmp" + File.separator + target_name);
            (new File("tmp")).mkdirs();
            FileOutputStream fos = new FileOutputStream(out);
            fos.getChannel().transferFrom(rbc, 0, 1 << 24);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
