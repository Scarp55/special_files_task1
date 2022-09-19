package special_files_task1;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;


public class Main {
    public static void main(String[] args){
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName ="data.csv";
        List<Employee> list = parseCSV(columnMapping, fileName);

        String json = (String) listToJson(list);
        writeString(json);
    }

    private static void writeString(String json) {
        try(FileWriter fileWriter =new FileWriter("data.json")){
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static <T> Object listToJson(List<T> list) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<T>>() {}.getType();

        return gson.toJson(list, listType);
    }

    private static List<Employee> parseCSV(String[] columnMapping, String fileName) {

        try(CSVReader reader = new CSVReader(new FileReader(fileName))){
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);

            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader).withMappingStrategy(strategy).build();
            return csv.parse();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
