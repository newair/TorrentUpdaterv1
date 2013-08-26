package newair.org.torrentupdaterandfilter.RSS;

import android.content.Context;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/*import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;*/

public class RSSReaderEx {

    private static RSSReaderEx instance = null;
    private static String title = null;
    private Context context;
    private String category;
    private String description;
    private String link;
    private static NodeList nodes;
    private boolean firstTime = true;
    private static InputStream input = null;
    private HashMap<String, String> map;



    public static RSSReaderEx getInstance() {
        if (instance == null) {
            instance = new RSSReaderEx();
        }
        return instance;
    }

    /**
     *
     * @return NodeList
     * description : Reads the input stream from url and creates nodes
     */

    public static NodeList getStoredNodes(){

        while(true){
        if(nodes != null)
           break;

        else continue;
        }

        return  nodes;

    }

    public static NodeList getNodes() {

        DocumentBuilder builder = null;


        Document doc = null;



         openConnection("http://torrentz.eu/feedA?q=");
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }




        if (builder != null) {
            try {
                doc = builder.parse(input);
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
              /*  try {
                    input.close();
                } catch (IOException e) {
                    System.out.println("Error reading input : "+e.getMessage());
                }*/
            }
        }
         nodes = doc.getElementsByTagName("item"); // get the items which has name item from RSS

        closeConnection();
        return nodes;
    }

    public static void  openConnection(String RSSurl){

        URL u = null; // your feed url
        try {
            u = new URL(RSSurl); // You can even get it from somewhere else
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        if (u != null) {
            try {
                input = u.openStream();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private HashMap<String, String> Readfeed() {
        try {

            String newTitle = null;
            String newFirstTitle = null;

            nodes = getNodes();
            Element Firstelement = (Element) nodes.item(0);

            if (firstTime) {
                title = getElementValue(Firstelement, "title");
                category = getElementValue(Firstelement, "category");
                description = getElementValue(Firstelement, "description");
                link = getElementValue(Firstelement, "link");
                firstTime = false;
                System.out.println("first title :" + title);
                return createMapForTorrents(title, category, description, link);

            } else {

                for (int i = 0; i < nodes.getLength(); i++) {
                    Element element = (Element) nodes.item(i);
                    newTitle = getElementValue(element, "title");
                    if(i ==0) newFirstTitle = newTitle; // There can be more than 1 torrents loaded

                    if (title.equalsIgnoreCase(newTitle)) {

                        break;
                    }
                    category = getElementValue(element, "category");
                    description = getElementValue(Firstelement, "description");
                    link = getElementValue(Firstelement, "link");
                    return createMapForTorrents(newTitle, category, description, link);
                }
                title = newFirstTitle;

                return createMapForTorrents(null, category, description, link);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    private String getCharacterDataFromElement(Element e) {
        try {
            Node child = e.getFirstChild();
            if (child instanceof CharacterData) {
                CharacterData cd = (CharacterData) child;
                return cd.getData();
            }
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
        return "";
    }

    protected float getFloat(String value) {
        if (value != null && !value.equals("")) {
            return Float.parseFloat(value);
        }
        return 0;
    }

    protected String getElementValue(Element parent, String label) {
        return getCharacterDataFromElement((Element) parent.getElementsByTagName(label).item(0));
    }

    public static void main(String[] args) {
        RSSReaderEx reader = RSSReaderEx.getInstance();
        reader.Readfeed();
    }


    /**
     * @param title
     * @param category
     * @param description
     * @param link
     * @return HashMap
     *         description: Creates a hash map for every torrent
     */

    private HashMap<String, String> createMapForTorrents(String title, String category, String description, String link) {

       /* if (!(category.toLowerCase().contains("porn") || category.toLowerCase().contains("xxx")) && category.toLowerCase().contains("video")) {

            System.out.println("Title: " + title);

            System.out.println("Category: " + category);

            System.out.println(description.split("Hash:")[0]);

        } else {

            System.out.println("got porn site");
        }

        Bundle sendBundle = new Bundle();
        sendBundle.putString("title", title);
        sendBundle.putString("category",category);
        sendBundle.putString("description",description.split("Hash:")[0]);
        sendBundle.putString("link",link);

        Intent i = new Intent(context, CreateNotificationActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtras(sendBundle);
        context.startActivity(i);*/
        HashMap<String, String> torrentMap = new HashMap<String, String>(4);
        torrentMap.put("title", title);
        torrentMap.put("category", category);
        torrentMap.put("description", description);
        torrentMap.put("link", link);

        return torrentMap;

    }

    /**
     * @return HashMap
     *         description: This method will call ReadRss method and send the result BackgroundTorrentUpdate.
     *         Method is created for future modifications
     *         task
     */

    public HashMap<String, String> fetchResult() {

       map = Readfeed();

      if(map != null){
        if(map.get("title") !=null )
        {return map;}
        else
        {return null;}

      } else
      {
          return null;
      }

    }

    public static void closeConnection() {
        try {

            if(input !=null)
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
