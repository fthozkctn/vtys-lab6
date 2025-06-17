package app.store;

import app.model.Student;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class HazelcastStore {
    private static HazelcastInstance client;
    private static IMap<String, Student> studentMap;

    public static void init() {
        client = HazelcastClient.newHazelcastClient();
        studentMap = client.getMap("students");

        // Eğer veriler önceden yüklendiyse tekrar yükleme
        if (studentMap.size() >= 10000) return;

        for (int i = 1; i <= 10000; i++) {
            String studentNo = String.format("202500%04d", i);
            String name = "Student " + i;
            String department = i % 2 == 0 ? "Classical Turkish Music" : "Turkish Folk Music";

            Student student = new Student(studentNo, name, department);
            studentMap.put(studentNo, student);
        }

        System.out.println("Hazelcast'e 10.000 öğrenci eklendi.");
    }

    public static Student get(String id) {
        return studentMap.get(id);
    }
}
