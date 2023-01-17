import java.io.*;
import java.util.ArrayList;

public class ReadWriteTXT implements ReadWriteFile {
    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;

    @Override
    public ArrayList<String> read(String input) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        bufferedReader = new BufferedReader(new FileReader(input));
        String str;
        while((str = bufferedReader.readLine()) != null){
            list.add(str);
        }
        bufferedReader.close();
        return list;
    }

    @Override
    public void write(ArrayList<String> result, String output) throws IOException {
        bufferedWriter = new BufferedWriter(new FileWriter(output));

        for(String s: result){
            s = s.replaceAll("[A-Za-z]", "");
            Parsing p = new Parsing(s);
            bufferedWriter.write(Double.toString(p.parseAnswer()) + "\n");
        }
        bufferedWriter.close();
    }
}
