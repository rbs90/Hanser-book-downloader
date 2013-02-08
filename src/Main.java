import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: rbs
 * Date: 08.02.13
 * Time: 18:30
 * To change this template use File | Settings | File Templates.
 */
public class Main {



    public static void main(String[] args) throws IOException {

        ArrayList<String> files = new ArrayList<String>();

        String url = JOptionPane.showInputDialog(null, "URL?", "Hanser book downloader", JOptionPane.QUESTION_MESSAGE);
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .get();

        String bookName = doc.getElementById("articleToolsHeading").text().replace("\"", "").trim();
        Elements doc_elems = doc.getElementsByClass("articleEntry");

        int i = 1;

        for(Element elem : doc_elems){
            String name = elem.getElementsByClass("art_title").text();
            String href = elem.getElementsByAttributeValueStarting("href", "/doi/pdf/").get(0).attr("abs:href");
            System.out.println("NAME: " + name + "\n href: " + href + "\n");
            String fileName = bookName + " " + i + ".pdf";
            FileDownloader.get(href, fileName);
            files.add(fileName);
            i++;
        }

        PDFMergerUtility ut = new PDFMergerUtility();
        for(String file : files){
            ut.addSource("tmp" + File.separator + file);
        }

        OutputStream finalStream = new BufferedOutputStream(new FileOutputStream(bookName + ".pdf"));
        ut.setDestinationStream(finalStream);

        try {
            ut.mergeDocuments();
        } catch (COSVisitorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        JOptionPane.showMessageDialog(null, "Book download was successfull.", "Hanser book downloader", JOptionPane.PLAIN_MESSAGE);

    }
}
