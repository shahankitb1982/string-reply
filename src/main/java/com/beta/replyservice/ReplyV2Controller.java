package com.beta.replyservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/v2")
public class ReplyV2Controller {

    @Autowired
    MessageServices messageServices;

    @GetMapping("/reply")
    public ReplyMessage replying() {
        return new ReplyMessage("Message is empty");
    }

    @RequestMapping(value = "/reply/{message}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> replying(@PathVariable String message) {
        Boolean isValid = messageServices.validate(message);

        if (isValid) {
            String resultData = messageServices.getData(message);
            String resultMessage = new ReplyMessage(resultData).getMessage();
            if (resultMessage.equals("Invalid input")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        Collections.singletonMap("error", resultMessage));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(
                        Collections.singletonMap("data", resultMessage));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Collections.singletonMap("error", "Invalid format. Please use valid format [eg. XX-dswewew]. XX is number."));
        }
    }
}