package newair.org.torrentupdaterandfilter;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by newair on 7/3/13.
 */
public class TorrentListManager {


    public static String[] formatTorrents(NodeList nodes) {


        String[] torrents = new String[nodes.getLength()];

        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            // System.out.println("element :"+ element.getTagName());

            String title = getElementValue(element, "title");

            String category = getElementValue(element, "category");

            String description = getElementValue(element, "description").split("Hash:")[0];

            torrents[i] = title + "\n" + category + "\n" + description;
        }
        return torrents;
    }

    private static String getCharacterDataFromElement(Element e) {
        try {
            Node child = e.getFirstChild();
            if (child instanceof CharacterData) {
                CharacterData cd = (CharacterData) child;
                return cd.getData();
            }
        } catch (Exception ex) {
        }
        return "";
    }


    public static String getElementValue(Element parent, String label) {
        return getCharacterDataFromElement((Element) parent.getElementsByTagName(label).item(0));
    }


}
