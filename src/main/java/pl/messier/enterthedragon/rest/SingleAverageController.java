package pl.messier.enterthedragon.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.messier.enterthedragon.rest.model.SingleAverageRequest;
import pl.messier.enterthedragon.rest.model.SingleAverageResponse;
import pl.messier.enterthedragon.service.api.SingleAverage;
import pl.messier.enterthedragon.service.exceptions.EnterTheDragonException;

@RestController
public class SingleAverageController {

    @PostMapping("/singleAverage")
    SingleAverageResponse singleAverage(@RequestBody String json) throws JsonProcessingException {

        SingleAverageRequest req = new ObjectMapper().readValue(json, SingleAverageRequest.class);
        SingleAverage singleAverage = new SingleAverage();
        try {
            Double average = singleAverage.getAverageSharePrice(
                    req.getTickerSymbol(), req.getDayStart(), req.getInterval(), req.getNumOfPayments(), req.getMomentBuy());
            return new SingleAverageResponse(req,average);
        } catch (EnterTheDragonException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

}
