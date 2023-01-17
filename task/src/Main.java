import org.json.simple.parser.ParseException;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileFormatException, IOException, XMLStreamException, ParseException {
        Scanner scan = new Scanner(System.in);

        System.out.println("Write the name of input file:");
        String input = scan.nextLine();

        ArrayList<String> list = new ArrayList<>();

        switch (getFileFormat(input)) {
            case "txt":
                ReadWriteTXT readTXT = new ReadWriteTXT();
                list = readTXT.read(input);
                break;
            case "xml":
                ReadWriteXML readXML = new ReadWriteXML();
                list = readXML.read(input);
                break;
            case "json":
                ReadWriteJSON readJSON = new ReadWriteJSON();
                list = readJSON.read(input);
                break;
            case "zip":
                ArchiveZIP unarchive = new ArchiveZIP();
                unarchive.unarchive(input);
                break;
            default:
                throw new FileFormatException ("Wrong filename!");
        }

        System.out.println("Write the name of output file: ");
        String output = scan.nextLine();

        switch (getFileFormat(output)) {
            case "txt":
                ReadWriteTXT writeTXT = new ReadWriteTXT();
                writeTXT.write(list, output);
                break;
            case "xml":
                ReadWriteXML writeXML = new ReadWriteXML();
                writeXML.write(list, output);
                break;
            case "json":
                ReadWriteJSON writeJSON = new ReadWriteJSON();
                writeJSON.write(list, output);
                break;
            default:
                throw new FileFormatException ("Wrong filename!");
        }
    }
    static String getFileFormat(String filename){
        String[] str = filename.split("\\.");
        return str[1];
    }
}