package com.example.newMock.Controller;

import com.example.newMock.Model.RequestDto;
import com.example.newMock.Model.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

@RestController
public class MainController {
    private Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/")
    public String getMainPage() {
        return "Hello, user!";
    }

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances (@RequestBody RequestDto requestDto) {
        try {
            String clientId = requestDto.getClientId();
            char firstDigit = clientId.charAt(0);
            Integer maxLimit;

            String currency = "RU";
            if (firstDigit == '8') {
                maxLimit = 2000;
                currency = "US";
            } else if (firstDigit == '9') {
                maxLimit = 1000;
                currency = "EU";
            } else {
                maxLimit = 50000;
            }

            ResponseDto responseDto = new ResponseDto();

            String rqUID = requestDto.getRqUID();

            responseDto.setRqUID(rqUID);
            responseDto.setClientId(clientId);
            responseDto.setAccount(requestDto.getAccount());
            responseDto.setCurrency(currency);

            Random random = new Random();
            Integer randomBalance = random.nextInt(maxLimit);

            responseDto.setBalance(new BigDecimal(randomBalance));
            responseDto.setMaxLimit(new BigDecimal(maxLimit));

            log.error("********* RequestDTO *********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDto));
            log.error("********* ResponseDTO *********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDto));

            //return response2;
            return responseDto;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
