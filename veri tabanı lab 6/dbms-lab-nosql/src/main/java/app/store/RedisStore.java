package app.store;

import redis.clients.jedis.Jedis;
import app.model.Student;
import com.google.gson.Gson;

public class RedisStore {
    static Jedis jedis;
    static Gson gson = new Gson();

    public static void init() {
        jedis = new Jedis("localhost", 6379); // WSL içindeki Redis'e erişim
        for (int i = 0; i < 10000; i++) {
            String id = "2025" + String.format("%06d", i);
            Student s = new Student(id, "Ad Soyad " + i, "Bilgisayar");
            jedis.set(id, gson.toJson(s));
        }
        System.out.println("Redis'e 10.000 öğrenci eklendi.");
    }

    public static Student get(String id) {
        String json = jedis.get(id);
        System.out.println("Redis'ten gelen veri: " + json);
        return json != null ? gson.fromJson(json, Student.class) : null;
}

}