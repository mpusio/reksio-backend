package com.reksio.restbackend.integrateTests.prepare;

import com.google.gson.Gson;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.reksio.restbackend.collection.advertisement.Address;
import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.collection.advertisement.Category;
import com.reksio.restbackend.collection.advertisement.Contact;
import com.reksio.restbackend.collection.advertisement.pets.Gender;
import com.reksio.restbackend.collection.advertisement.pets.Pet;
import com.reksio.restbackend.collection.advertisement.pets.Type;
import com.reksio.restbackend.collection.user.Role;
import com.reksio.restbackend.collection.user.Token;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
        initializeAdvertisement();
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
                .add("tokens", List.of(Token.BLUE))
                .get();

        mongoTemplate.insert(admin, "user");
        mongoTemplate.insert(user, "user");
    }

    public void initializeAdvertisement() {
        Advertisement advertisement = Advertisement.builder()
                .uuid(UUID.randomUUID())
                .address(Address.builder()
                        .city("Warsaw")
                        .postCode("00-020")
                        .build())
                .category(Category.CATS)
                .contact(Contact.builder()
                        .facebookUrl("https://www.facebook.com/profile.php?id=100004649430032")
                        .phone("123456789")
                        .details("Call to me at 8 pm - 9 pm")
                        .build())
                .images(List.of(new byte[1]))
                .pet(Pet.builder()
                        .gender(Gender.MALE)
                        .name("Cinamon")
                        .type(Type.American_Shorthair)
                        .ageInDays(365)
                        .build())
                .price(45)
                .title("My beauty cute cat! <3")
                .description("This is my cat. His name is Cinamon. He is so sweet, after sterilization. Just write for details.")
                .youtubeUrl("https://www.youtube.com/watch?v=MLtPdXR-eFw")
                .createdBy("user@gmail.com")
                .priority(0)
                .expirationDate(LocalDateTime.now())
                .build();

        mongoTemplate.insert(advertisement, "advertisement");
    }

    public void destroyData(){
        mongoTemplate.dropCollection("user");
        mongoTemplate.dropCollection("advertisement");
    }
}