package com.reksio.restbackend.advertisement;

import com.reksio.restbackend.advertisement.dto.AdvertisementResponse;
import com.reksio.restbackend.advertisement.dto.AdvertisementSaveRequest;
import com.reksio.restbackend.advertisement.dto.AdvertisementUpdateRequest;
import com.reksio.restbackend.advertisement.filter.FilterChain;
import com.reksio.restbackend.advertisement.filter.FilterFactory;
import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.exception.advertisement.AdvertisementInvalidFieldException;
import com.reksio.restbackend.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public List<AdvertisementResponse> getFilteredAdvertisements(@RequestParam Map<String,String> allParams){

        List<Advertisement> advertisements = advertisementService.getAllAdvertisements();

        FilterChain filterChain = new FilterChain(advertisements);
        allParams.forEach((k, v) -> filterChain.addFilter(FilterFactory.getFilter(k, v)));

        return filterChain
                .runFilters().getAdvertisements().stream()
                .sorted(Comparator.comparingInt(Advertisement::getPriority)
                        .thenComparing(Advertisement::getExpirationDate).reversed())
                .map(AdvertisementResponse::convertToAdvertisementResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/advertisement")
    public List<AdvertisementResponse> getUserAdvertisements(HttpServletRequest servletRequest){
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
