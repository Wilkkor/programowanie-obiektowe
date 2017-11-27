import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class CSVReader {
    BufferedReader a;
    String delimiter;
    boolean hasHeader;
    List<String> columnLabels = new ArrayList<>();
    Map<String,Integer> columnLabelsToInt = new HashMap<>();
    public String[]current;
    public CSVReader(String filename,String delimiter,boolean hasHeader) throws IOException {
        this(new FileReader(filename),delimiter,true);
    }
    public CSVReader(String filename,String delimiter) throws IOException {
        this(filename,delimiter,true);
    }
    public CSVReader(String filename) throws IOException {
        this(filename,",",true);
    }
    public CSVReader(Reader read, String delimiter, boolean hasHeader) throws IOException {
        a = new BufferedReader(read);
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        if (hasHeader) {
            parseHeader();
        }
        next();
    }
    void parseHeader() throws IOException {
        String line  = a.readLine();
        if(line==null){
            return;
        }
        String[]header = line.split(delimiter);
        for(int i=0;i<header.length;i++){
            columnLabels.add(header[i]);
            columnLabelsToInt.put(header[i],i);
        }
    }
    boolean next() throws IOException {
        String line  = a.readLine();
        if(line==null){
            return false;
        }
        current = line.split(delimiter);
        return true;
    }
    String get(int i){
        return current[i];
    }
    String get(String label){
        return get(columnLabelsToInt.get(label));
    }
    int getInt(int i){
        String a=current[i];
        return  Integer.parseInt(a);
    }
    int getInt(String label){
        return getInt(columnLabelsToInt.get(label));
    }
    double getDouble(int i){
        String a=current[i];
        return  Double.parseDouble(a);
    }
    double getDouble(String label){
        return getDouble(columnLabelsToInt.get(label));
    }
    double getLong(int i){
        String a=current[i];
        return  Long.parseLong(a);
    }
    double getLong(String label){
        return getLong(columnLabelsToInt.get(label));
    }
    List<String> getColumnLabels(){
        return  columnLabels;
    }
    int getRecordLength(){
        return current.length;
    }
    boolean isMissing(int columnIndex){
        return current[columnIndex]=="";
    }
    boolean isMissing(String columnLabel){
        return  columnLabels.indexOf(columnLabel)==-1;
    }
    public static void main(String[] args) throws IOException {
        CSVReader a=new CSVReader("with-header.csv",";",true);
        while(a.next()){
            for(int i=0;i<a.getColumnLabels().size();i++){
                System.out.printf(a.get(i)+" ");
            }
            System.out.println();
        }
    }
}