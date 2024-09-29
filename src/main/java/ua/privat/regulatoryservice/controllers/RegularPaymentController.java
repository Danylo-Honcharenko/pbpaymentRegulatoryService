package ua.privat.regulatoryservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.privat.regulatoryservice.dto.RegularPaymentDTO;
import ua.privat.regulatoryservice.service.RegularPaymentService;
import ua.privat.regulatoryservice.service.WiringService;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class RegularPaymentController {

    private final RegularPaymentService regularPaymentService;
    private final WiringService wiringService;

    @GetMapping("/start-regulator-service")
    public ResponseEntity<String> regularPayment() {
        List<RegularPaymentDTO> regularPaymentDtoS = regularPaymentService.checkPaymentsForTheNeedToWriteOff();
        wiringService.createWiring(regularPaymentDtoS);
        return ResponseEntity.ok("The write-off was successful!");
    }
}
