package sandbox.interview.charger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Charging session not found")
public class ChargingSessionNotFoundException extends RuntimeException {

}
