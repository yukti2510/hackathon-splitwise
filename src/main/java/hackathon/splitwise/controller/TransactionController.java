package hackathon.splitwise.controller;

import hackathon.splitwise.dto.TransactionDto;
import hackathon.splitwise.dto.request.TransactionRequestDto;
import hackathon.splitwise.dto.response.CreateGroupResponseDto;
import hackathon.splitwise.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gauravlikhar
 * @project hackathon-splitwise
 */

@RestController
@RequestMapping(value = "/v1/transaction", produces = "application/json")
@Log4j2
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/create")
    public TransactionDto createTransaction(@RequestBody TransactionRequestDto transactionRequestDto) {
        log.info("Request to add transaction {}", transactionRequestDto);
        return transactionService.createTransaction(transactionRequestDto);
    }
}
