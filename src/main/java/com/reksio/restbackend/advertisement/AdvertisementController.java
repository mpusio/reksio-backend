package com.reksio.restbackend.advertisement;

import com.reksio.restbackend.advertisement.dto.AdvertisementResponse;
import com.reksio.restbackend.advertisement.dto.AdvertisementSaveRequest;
import com.reksio.restbackend.advertisement.dto.AdvertisementUpdateRequest;
import com.reksio.restbackend.exception.user.UserInvalidFieldException;
import com.reksio.restbackend.security.JwtUtil;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @PostMapping("/advertisement")
    public AdvertisementResponse addAdvertisementAsUser(@Valid @RequestBody AdvertisementSaveRequest advertisementSaveRequest, BindingResult result, HttpServletRequest servletRequest){
        String token = servletRequest.getHeader("Authorization");
        String email = JwtUtil.fetchEmail(token);

        if(result.hasErrors()) {
            throw new UserInvalidFieldException(
                    result
                            .getFieldErrors()
                            .stream()
                            .map(f -> f.getField() + ": " + f.getDefaultMessage())
                            .reduce((a, b) -> a + ", " + b)
                            .toString());
        }
        return advertisementService.addAdvertisementForUser(email, advertisementSaveRequest);
    }

    @GetMapping("/advertisement")
    public AdvertisementResponse getAdvertisement(@RequestParam UUID uuid){
        return advertisementService.getAdvertisement(uuid);
    }

    @PutMapping("/advertisement")
    public AdvertisementResponse updateAdvertisementAsUser(@Valid @RequestBody AdvertisementUpdateRequest advertisementUpdateRequest, BindingResult result, HttpServletRequest servletRequest){
        String token = servletRequest.getHeader("Authorization");
        String email = JwtUtil.fetchEmail(token);

        if(result.hasErrors()) {
            throw new UserInvalidFieldException(
                    result
                            .getFieldErrors()
                            .stream()
                            .map(f -> f.getField() + ": " + f.getDefaultMessage())
                            .reduce((a, b) -> a + ", " + b)
                            .toString());
        }
        return advertisementService.updateAdvertisement(email, advertisementUpdateRequest);
    }

    @DeleteMapping("/advertisement")
    public void deleteAdvertisementAsUser(@RequestParam UUID uuid, HttpServletRequest servletRequest){
        String token = servletRequest.getHeader("Authorization");
        String email = JwtUtil.fetchEmail(token);

        advertisementService.deleteAdvertisement(email, uuid);
    }
}
