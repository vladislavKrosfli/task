
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;

public class ReadWriteXML<Item> implements ReadWriteFile {
    @Override
    public ArrayList<String> read(String input) throws IOException, XMLStreamException {
        ArrayList<String> list = new ArrayList<>();

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileReader(input));

            while (reader.hasNext()) {
                if (reader.next() == XMLStreamConstants.CHARACTERS) {
                    String str = reader.getText();
                    str = str.replaceAll("[\\n\\s]", "");
                    if(!str.equals("")){
                        list.add(str);
                    }
                }
            }
        return list;
    }

    @Override
    public void write(ArrayList<String> result, String output) throws IOException {
        try {
            ArrayList<String> newResult = new ArrayList<>();

            for(String list: result) {
                list = list.replaceAll("[A-Za-z]", "");
                Parsing p = new Parsing(list);
                newResult.add(Double.toString(p.parseAnswer()));
            }

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document dom = builder.newDocument();

            Element root = dom.createElement("Result");
            dom.appendChild(root);

            for(int i = 0; i < result.size(); i++) {
                Element res = dom.createElement("line" + i);
                res.setTextContent(newResult.get(i));

                root.appendChild(res);
            }

            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.transform(new DOMSource(dom), new StreamResult(new File(output)));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}