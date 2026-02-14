package health.camp.config;

import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MongoChecker implements CommandLineRunner {

    private final MongoTemplate mongoTemplate;

    @Override
    public void run(String... args) {
        MongoDatabase db = mongoTemplate.getDb();
        System.out.println("Connected to MongoDB database: " + db.getName());
    }
}
