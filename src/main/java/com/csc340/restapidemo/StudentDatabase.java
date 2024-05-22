package com.csc340.restapidemo;

import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.json.JsonParser;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class StudentDatabase {
    private static final String DATABASE_LOCATION = "C:\\Users\\ethan\\OneDrive - UNCG\\CSC 340\\CSC340assignment-crudapi\\src\\main\\java\\com\\csc340\\restapidemo\\student_database.json";
    private Object obj;
    private static JSONObject jo;

    public StudentDatabase() throws IOException, ParseException {
        this.pullDatabase();
    }

    public void pullDatabase() throws IOException, ParseException {
        this.obj = new JSONParser().parse(new FileReader(DATABASE_LOCATION));
        this.jo = (JSONObject) obj;
    }

    public Map<Integer, Student> getStudentMap() {
        JSONArray studentArr = (JSONArray) this.jo.get("students");
        Iterator itr = studentArr.iterator();
        Map<Integer, Student> studentMap = new HashMap<>();

        while (itr.hasNext()) {
            JSONObject jsonStudent = (JSONObject) itr.next();
            Integer id = (Integer) ((Long) jsonStudent.get("id")).intValue();
            String name = (String) jsonStudent.get("name");
            String major = (String) jsonStudent.get("major");
            Double gpa = (Double) (jsonStudent.get("gpa"));
            Student student = new Student(id, name, major, gpa);
            studentMap.put(id, student);
        }

        return studentMap;
    }

    public static void writeNewDatabase(Map<Integer, Student> databaseObjects) throws FileNotFoundException {
        JSONArray ja = new JSONArray();
        ja.add(databaseObjects);
        jo.put("students", ja);

        PrintWriter pw = new PrintWriter(DATABASE_LOCATION);
        pw.write(jo.toJSONString());
        pw.flush();
        pw.close();
    }
}
