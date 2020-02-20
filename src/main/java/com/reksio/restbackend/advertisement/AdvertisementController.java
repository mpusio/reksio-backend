package com.reksio.restbackend.advertisement;

import com.reksio.restbackend.advertisement.dto.AdvertisementResponse;
import com.reksio.restbackend.advertisement.dto.AdvertisementSaveRequest;
import com.reksio.restbackend.advertisement.dto.AdvertisementUpdateRequest;
import com.reksio.restbackend.exception.advertisement.AdvertisementInvalidFieldException;
import com.reksio.restbackend.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @PostMapping("/advertisement")
    @ResponseStatus(HttpStatus.CREATED)
    public AdvertisementResponse addAdvertisementAsUser(@Valid @RequestBody AdvertisementSaveRequest advertisementSaveRequest, BindingResult result, HttpServletRequest servletRequest){
        String token = servletRequest.getHeader("Authorization");
        String email = JwtUtil.fetchEmail(token);

        handleBindingResult(result);

        return advertisementService.addAdvertisementForUser(email, advertisementSaveRequest);
    }

    @GetMapping("/advertisement/{uuid}")
    public AdvertisementResponse getAdvertisement(@PathVariable UUID uuid){
        return advertisementService.getAdvertisement(uuid);
    }

    @GetMapping("/advertisement")
    public List<AdvertisementResponse> getAllUserAdvertisement(HttpServletRequest servletRequest){
        String token = servletRequest.getHeader("Authorization");
        String email = JwtUtil.fetchEmail(token);

        return advertisementService.getAllAdvertisementsBelongToUser(email);
    }

    @PutMapping("/advertisement")
    public AdvertisementResponse updateAdvertisementAsUser(@Valid @RequestBody AdvertisementUpdateRequest advertisementUpdateRequest, BindingResult result, HttpServletRequest servletRequest){
        String token = servletRequest.getHeader("Authorization");
        String email = JwtUtil.fetchEmail(token);

        handleBindingResult(result);

        return advertisementService.updateAdvertisement(email, advertisementUpdateRequest);
    }

    @DeleteMapping("/advertisement/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAdvertisementAsUser(@PathVariable UUID uuid, HttpServletRequest servletRequest){
        String token = servletRequest.getHeader("Authorization");
        String email = JwtUtil.fetchEmail(token);

        advertisementService.deleteAdvertisement(email, uuid);
    }

    private void handleBindingResult(BindingResult result){
        if(result.hasErrors()) {
            throw new AdvertisementInvalidFieldException(
                    result
                            .getFieldErrors()
                            .stream()
                            .map(f -> f.getField() + ": " + f.getDefaultMessage())
                            .reduce((a, b) -> a + ", " + b)
                            .toString());
        }
    }
}
