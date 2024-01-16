package de.muenchen.oss.digiwf.akdb.integration.core.application;

import de.muenchen.oss.digiwf.akdb.integration.core.application.ports.in.CalcNumberInPort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CalcNumberUseCase implements CalcNumberInPort {

    @Override
    public int calc(final Integer number) {
        log.info("Calc value for: {}", number);
        return number * 2;
    }
}
