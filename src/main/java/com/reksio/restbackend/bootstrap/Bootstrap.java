package com.reksio.restbackend.bootstrap;

import com.reksio.restbackend.collection.advertisement.*;
import com.reksio.restbackend.collection.advertisement.pets.Gender;
import com.reksio.restbackend.collection.advertisement.pets.Pet;
import com.reksio.restbackend.collection.advertisement.pets.Type;
import com.reksio.restbackend.collection.user.Role;
import com.reksio.restbackend.collection.user.User;
import com.reksio.restbackend.collection.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class Bootstrap {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public AdvertisementRepository advertisementRepository;

    @Autowired
    public BCryptPasswordEncoder encoder;

    @PostConstruct
    public void insertUser(){
        userRepository.deleteAll();
        advertisementRepository.deleteAll();

        User user = User.builder()
                .email("michal@gmail.com")
                .isActive(true)
                .password(encoder.encode("password"))
                .roles(List.of(Role.ADMIN))
                .build();

        User someuser = User.builder()
                .email("someuser@gmail.com")
                .isActive(true)
                .password(encoder.encode("P@ssword123"))
                .roles(List.of(Role.USER, Role.BLOGGER))
                .build();

        userRepository.save(user);
        userRepository.save(someuser);
        advertisementRepository.saveAll(initAdvertisements());
    }

    public List<Advertisement> initAdvertisements(){
        Advertisement ad1 = Advertisement.builder()
                .address(Address.builder()
                        .city("Warszawa")
                        .postCode("00-020")
                        .build())
                .category(Category.DOGS)
                .contact(Contact.builder()
                        .facebookUrl("https://www.facebook.com/profile.php?id=100004649430032")
                        .phone("123456789")
                        .details("Call to me at 8 pm - 9 pm")
                        .build())
                .images(List.of("path/to/image", "path/to/second/image"))
                .pet(Pet.builder()
                        .gender(Gender.MALE)
                        .name("Devil")
                        .type(Type.Bulldogs)
                        .ageInDays(100)
                        .build())
                .price(245)
                .title("This is angry dog.")
                .description("Dog desc.")
                .expirationDate(LocalDateTime.now().plusDays(30))
                .priority(0)
                .createdBy("someuser@gmail.com")
                .uuid(UUID.randomUUID())
                .youtubeUrl("https://www.youtube.com/watch?v=MLtPdXR-eFw")
                .build();

        Advertisement ad2 = Advertisement.builder()
                .address(Address.builder()
                        .city("Warszawa")
                        .postCode("00-020")
                        .build())
                .category(Category.DOGS)
                .contact(Contact.builder()
                        .facebookUrl("https://www.facebook.com/profile.php?id=100004649430032")
                        .phone("123456789")
                        .details("Call to me at 8 pm - 9 pm")
                        .build())
                .images(List.of("path/to/image", "path/to/second/image"))
                .pet(Pet.builder()
                        .gender(Gender.MALE)
                        .name("Rex")
                        .type(Type.Beagles)
                        .ageInDays(1024)
                        .build())
                .price(20)
                .title("This is beagles.")
                .description("Dog beagles..")
                .expirationDate(LocalDateTime.now().plusDays(30))
                .priority(0)
                .createdBy("someuser@gmail.com")
                .uuid(UUID.randomUUID())
                .youtubeUrl("https://www.youtube.com/watch?v=MLtPdXR-eFw")
                .build();

        Advertisement ad3 = Advertisement.builder()
                .address(Address.builder()
                        .city("Warszawa")
                        .postCode("00-020")
                        .build())
                .category(Category.EXOTIC)
                .contact(Contact.builder()
                        .facebookUrl("https://www.facebook.com/profile.php?id=100004649430032")
                        .phone("123456789")
                        .details("Call to me at 8 pm - 9 pm")
                        .build())
                .images(List.of("path/to/image", "path/to/second/image"))
                .pet(Pet.builder()
                        .gender(Gender.FEMALE)
                        .name("Chimchar")
                        .type(Type.Chimpanzee)
                        .ageInDays(365)
                        .build())
                .price(400)
                .title("Chimchar is my best frend")
                .description("Chimpanzee desc.")
                .expirationDate(LocalDateTime.now().plusDays(45))
                .priority(1)
                .createdBy("someuser@gmail.com")
                .uuid(UUID.randomUUID())
                .youtubeUrl("https://www.youtube.com/watch?v=MLtPdXR-eFw")
                .build();

        return List.of(ad1, ad2, ad3);
    }
}
