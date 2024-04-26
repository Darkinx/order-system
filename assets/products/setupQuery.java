import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

class setupQuery{
    public static void main(String[] args) {
        String configFile = "config.txt";
        String table = "product";
        String outputFile = "query.txt";
        String imgPath = "assets/products";
        ArrayList<String[]> tmp= readUsingNIO(configFile);

        insertFile(tmp, table, outputFile, imgPath);
        
    }

    //need to test
    private String readFromInputStream(InputStream inputStream) 
    throws IOException {
    StringBuilder resultStringBuilder = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
        String line;
        while ((line = br.readLine()) != null) {
            resultStringBuilder.append(line).append("\n");
        }
    }
  return resultStringBuilder.toString();
}

    //https://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java
    private static ArrayList<String[]> readUsingNIO(String file){
        String content = "";
        ArrayList<String[]> line = new ArrayList<String[]>();
        try{
            content = Files.readString(Paths.get(file));
            // System.out.print(content);

            //https://stackoverflow.com/questions/1921181/arraylist-of-string-arrays
            // split("\\s*\\|*\\s")
            for (String tmp : content.split("\n")) {
                // System.out.println(tmp.toString());
                String[] tmpArr = tmp.split("\\s*\\|+\\s");
                line.add(tmpArr);
                
            }
        } catch (IOException e) {
            System.err.println(e);
            // TODO: handle exception
        }
        return line;
        
    }

    private static void insertFile(ArrayList<String[]> ParsedData, String table, String fileName, String imgPath){
        String initQuery = "INSERT INTO `" + table +"` (`name`, `description`, `category`, `price`, `image_path`, `stock`)\nVALUES";
        String values = "";
        for (String[] tmp : ParsedData) {
            values += String.format("\n(\"%s\", \"%s\", \"%s\", %s, \"%s/%s/%s.png\", %s),", tmp[0], tmp[1], tmp[2], tmp[3], imgPath, tmp[2].toLowerCase(), tmp[4], tmp[5]);
        }

        String finalQuery = initQuery + values.substring(0, values.length()-1) + ";";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(finalQuery);
            
            writer.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}