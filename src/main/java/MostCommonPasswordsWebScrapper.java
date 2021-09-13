import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MostCommonPasswordsWebScrapper {
    static String nordpassMostCommonPasswordsURL =
            "https://nordpass.com/de/most-common-passwords-list/";

    //TODO: delete
    public static void main(String[] args) {
        try {
            String[] x = getMostCommonPasswords();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO: develop
    public static String[] getMostCommonPasswords() throws IOException {
        Document doc = Jsoup.connect(nordpassMostCommonPasswordsURL).get();
        String title = doc.title();
        Elements links = doc.select("a[href]");

        for (Element link: links){
            System.out.println(link.attr("href"));
            System.out.println(link.text());
        }

        return new String[] {"not","ready","yet"};
    }
}
