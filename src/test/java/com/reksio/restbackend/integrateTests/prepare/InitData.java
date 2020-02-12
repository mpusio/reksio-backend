package com.reksio.restbackend.integrateTests.prepare;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.reksio.restbackend.collection.user.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class InitData {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    BCryptPasswordEncoder encoder;

    @BeforeAll
    public void setup() {
        destroyData();
        initializeTestUsers();
    }

    public void initializeTestUsers() {
        DBObject admin = BasicDBObjectBuilder.start()
                .add("email", "admin@gmail.com")
                .add("password", encoder.encode("password"))
                .add("roles", List.of(Role.ADMIN))
                .add("isActive", true)
                .get();

        DBObject user = BasicDBObjectBuilder.start()
                .add("email", "user@gmail.com")
                .add("password", encoder.encode("password"))
                .add("roles", List.of(Role.USER))
                .add("isActive", true)
                .get();

        mongoTemplate.insert(admin, "user");
        mongoTemplate.insert(user, "user");
    }

    public void destroyData(){
        mongoTemplate.dropCollection("user");
    }
}