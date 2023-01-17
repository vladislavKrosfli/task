import org.json.simple.parser.ParseException;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;

public interface ReadWriteFile {
    ArrayList<String> read(String input) throws IOException, XMLStreamException, ParseException;
    void write(ArrayList<String> strings, String output) throws IOException;
}
