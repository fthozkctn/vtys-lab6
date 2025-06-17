package app.store;

import com.mongodb.client.*;
import org.bson.Document;
import app.model.Student;
import com.google.gson.Gson;

public class MongoStore {
    static MongoClient client;
    static MongoCollection<Document> collection;
    static Gson gson = new Gson();

    public static void init() {
        // 🔧 HATALIYDI: MongoClient mongoClient = ... 
        // ✅ DOĞRU: Static değişken olan client'e atıyoruz
        client = MongoClients.create("mongodb://admin:admin@localhost:27017/?authSource=admin");

        // MongoDB'ye bağlan
        collection = client.getDatabase("nosqllab").getCollection("ogrenciler");
        collection.drop(); // Tüm eski kayıtları temizle

        for (int i = 0; i < 10000; i++) {
            String id = "2025" + String.format("%06d", i);
            Student s = new Student(id, "Ad Soyad " + i, "Bilgisayar");
            collection.insertOne(Document.parse(gson.toJson(s)));
        }

        System.out.println("MongoDB'ye 10.000 öğrenci eklendi.");
    }

    public static Student get(String id) {
        Document doc = collection.find(new Document("ogrenciNo", id)).first();
        return doc != null ? gson.fromJson(doc.toJson(), Student.class) : null;
    }
}
