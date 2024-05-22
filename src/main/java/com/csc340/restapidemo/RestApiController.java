package com.csc340.restapidemo;

import com.fasterxml.jackson.annotation.JsonView;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
public class RestApiController {
    Map<Integer, Student> studentDatabase = new StudentDatabase().getStudentMap();

    public RestApiController() throws IOException, ParseException {
    }

    @JsonView(Views.Public.class)
    @GetMapping("/students/{id}")
    public Student getStudentByID(@PathVariable int id) throws IOException, ParseException {
        studentDatabase = new StudentDatabase().getStudentMap();
        return studentDatabase.get(id);
    }

    @JsonView(Views.Public.class)
    @GetMapping("/students/all")
    public Object getAllStudents() throws IOException, ParseException {
        studentDatabase = new StudentDatabase().getStudentMap();
        if (studentDatabase.isEmpty()) {
            studentDatabase.put(1, new Student(1, "sample", "csc", 3.86));
        }
        return studentDatabase.values();
    }

    @JsonView(Views.Public.class)
    @PostMapping("/students/create")
    public Object createStudent(@RequestBody Student student) throws IOException, ParseException {
        studentDatabase = new StudentDatabase().getStudentMap();
        studentDatabase.put(student.getId(), student);
        StudentDatabase.writeNewDatabase(studentDatabase);
        studentDatabase = new StudentDatabase().getStudentMap();
        return studentDatabase.values();
    }

    @JsonView(Views.Public.class)
    @GetMapping("/students/update/{id}")
    public Object updateStudent(@PathVariable int id, @RequestBody Student student) throws IOException, ParseException {
        studentDatabase = new StudentDatabase().getStudentMap();
        studentDatabase.replace(id, student);
        StudentDatabase.writeNewDatabase(studentDatabase);
        studentDatabase = new StudentDatabase().getStudentMap();
        return studentDatabase.values();
    }

    @JsonView(Views.Public.class)
    @GetMapping("/students/delete/{id}")
    public Object deleteStudentByID(@PathVariable int id) throws IOException, ParseException {
        studentDatabase = new StudentDatabase().getStudentMap();
        studentDatabase.remove(id);
        StudentDatabase.writeNewDatabase(studentDatabase);
        studentDatabase = new StudentDatabase().getStudentMap();
        return studentDatabase.values();
    }

    @GetMapping("/students/test")
    public String test() throws IOException, ParseException {
        studentDatabase = new StudentDatabase().getStudentMap();
        return studentDatabase.get(1).toString();
    }

    @GetMapping("/artworks")
    public Object thirdPartyAPITest() throws IOException {
        URL url = new URL("https://api.artic.edu/api/v1/artworks");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        int response = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content.toString();
    }

    private String formatStudentsOutput(Map<Integer, Student> database) {
        Iterator iter = database.entrySet().iterator();
        String output = "<ul><li>";
        while (iter.hasNext()) {
            output = output + iter.next().toString() + "</li><li>";
        }
        output += "</ul>";
        return output;
    }
}
