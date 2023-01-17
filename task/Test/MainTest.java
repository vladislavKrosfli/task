import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.stream.XMLStreamException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainTest {
    private ArrayList<String> list, res, listEnc;
    private String input = "";
    private String output = "";

    @Test
    public void ReadWriteXML() throws IOException, XMLStreamException, ParseException {
        input = "testIn.xml";
        output = "testOut.xml";

        list = new ArrayList<>();
        ReadWriteFile rw = new ReadWriteXML();
        ArrayList<String> list = rw.read(input);
        rw.write(list, output);

        rw = new ReadWriteXML();
        res = rw.read(output);

        Assert.assertEquals(res.get(0), "9.5");
    }

    @Test
    public void ReadAndWriteTXT() throws IOException, XMLStreamException, ParseException {
        input = "testIn.txt";
        output = "testOut.txt";

        list = new ArrayList<>();
        ReadWriteFile rw = new ReadWriteTXT();
        ArrayList<String> list = rw.read(input);
        rw.write(list, output);

        rw = new ReadWriteTXT();
        res = rw.read(output);

        Assert.assertEquals(res.get(0), "4.0");
    }

    @Test
    public void ReadWriteJSON() throws IOException, XMLStreamException, ParseException {
        input = "testIn.json";
        output = "testOut.json";

        list = new ArrayList<>();
        ReadWriteFile rw = new ReadWriteJSON();
        ArrayList<String> list = rw.read(input);
        rw.write(list, output);

        rw = new ReadWriteJSON();
        res = rw.read(output);

        Assert.assertEquals(res.get(0), "75.33333333333333");
    }

    @Test
    public void ArchiveUnarchiveXML() throws IOException, XMLStreamException {
        input = "test1";
        list = new ArrayList<>();
        list.add("124");

        ReadWriteXML rwXML = new ReadWriteXML();
        rwXML.write(list, input + ".xml");

        Archive rw = new ArchiveZIP();

        rw.archive(input + ".xml");
        rw.unarchive(input + ".zip");

        ArrayList<String> res = rwXML.read(input + ".xml");

        Assert.assertEquals(res.get(0),"124.0");
    }

    @Test
    public void EncryptDecryptFILE() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException, XMLStreamException, ParseException {
        input = "test1.txt";

        list = new ArrayList<>();
        listEnc = new ArrayList<>();

        list.add("1235");

        ReadWriteFile rw = new ReadWriteTXT();
        EncryptionDecryption ed = new EncryptionDecryption();
        listEnc = ed.encrypt(list);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(input));

        for(String s: listEnc){
            bufferedWriter.write(s);
        }
        bufferedWriter.close();

        list = new ArrayList<>();
        list = rw.read(input);

        res = new ArrayList<>();
        res = ed.decrypt(list);

        Assert.assertEquals(res.get(0), "1235");
    }
}