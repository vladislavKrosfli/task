import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class ReadWriteJSON implements ReadWriteFile {
    @Override
    public ArrayList<String> read(String input) throws IOException, ParseException {
        ArrayList<String> list = new ArrayList<>();

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(input));

        int i = 0;
        while(i != jsonObject.size()){
            list.add((String)jsonObject.get("line" + i));
            ++i;
        }
        return list;
    }

    @Override
    public void write(ArrayList<String> result, String output) throws IOException {
        ArrayList<String> newResult = new ArrayList<>();
        BufferedWriter writer = new BufferedWriter(new FileWriter(output));
        JSONObject resultJson = new JSONObject();

        for(String list: result){
            list = list.replaceAll("[A-Za-z]", "");
            Parsing p = new Parsing(list);
            newResult.add(Double.toString(p.parseAnswer()));
        }

        try {
            for (int i = 0; i < result.size(); ++i) {
                resultJson.put("line" + i, newResult.get(i));
            }

            writer.write(resultJson.toJSONString());
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}