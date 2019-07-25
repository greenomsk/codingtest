package com.green.codingtest.srp.service;

import com.green.codingtest.srp.service.exception.ValidationException;
import com.green.codingtest.srp.service.model.Gesture;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * class ValidationServiceImpl
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public void validate(final MoveValidationContext moveValidationContext) {
        validateGesture(moveValidationContext.gesture());
    }

    private void validateGesture(@Nullable final String gesture) {

        if (isBlank(gesture)) {
            throw new ValidationException("Gesture should not be empty");
        }

        Stream.of(Gesture.values())
            .map(Enum::name)
            .filter(i -> StringUtils.equals(i, gesture))
            .findFirst()
            .orElseThrow(() -> new ValidationException("Gesture has illegal value '" + gesture + "'"));

    }
}
