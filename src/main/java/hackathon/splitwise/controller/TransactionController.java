package hackathon.splitwise.controller;

import lombok.extern.log4j.Log4j2;
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
}
