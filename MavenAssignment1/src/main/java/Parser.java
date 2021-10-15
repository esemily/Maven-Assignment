import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Parser {

    //Method to parse JSON
    public static Iterator parseJSON(String url) throws ParseException {

        Client client = Client.create();
        WebResource webResource = client.resource(url);

        ClientResponse clientResponse = webResource.accept("application/json").get(ClientResponse.class);
        if (clientResponse.getStatus() !=200) {
            throw new RuntimeException("Failed" + clientResponse.toString());
        }

        JSONArray jsonArray = (JSONArray) new JSONParser().parse(clientResponse.getEntity(String.class));

        Iterator<Object> it = jsonArray.iterator();

        return it;
    }

    //Method to change JSON to Array of Students
    public static Student[] filter(String uInput, Iterator list) {

        //Converting JSON to List filteredList
        List<Student> filteredList = new ArrayList<>();
        while (list.hasNext()){
          JSONObject jsonObject = (JSONObject) list.next();
          if(jsonObject.get("first_name").equals(uInput) || jsonObject.get("gender").equals(uInput)){
            Student s = new Student(
                    String.valueOf(jsonObject.get("first_name")),
                    String.valueOf(jsonObject.get("gender")),
                    String.valueOf(jsonObject.get("email")),
                    String.valueOf(jsonObject.get("gpa"))
            );
            filteredList.add(s);
            }
        }

        //Converting filteredList to Array studentFilter
        Student[] studentFilter = new Student[filteredList.size()];
        for(int u = 0; u<filteredList.size(); u++){
            studentFilter[u] = filteredList.get(u);
        }

        return studentFilter;
    }

    //Prints Array
    public static void printStudents(Student[] array){
        for(int i = 0; i < array.length; i++){
            array[i].PrintStudent();
        }
    }

    public static void main(String[] args) throws ParseException {
        Iterator<Object> full = parseJSON("https://hccs-advancejava.s3.amazonaws.com/student.json");

        System.out.println("Filter by name or gender.");
        Scanner studentscan = new Scanner(System.in);
        String search = studentscan.nextLine();
        Student[] filtered = filter(search, full);
        printStudents(filtered);

    }

}
