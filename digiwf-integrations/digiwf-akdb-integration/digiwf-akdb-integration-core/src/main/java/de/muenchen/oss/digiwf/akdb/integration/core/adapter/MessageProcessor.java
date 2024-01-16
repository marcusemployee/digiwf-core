package de.muenchen.oss.digiwf.akdb.integration.core.adapter;

import de.muenchen.oss.digiwf.akdb.integration.core.application.ports.in.CalcNumberInPort;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.function.Consumer;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_MESSAGE_NAME;
import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;

@Configuration
@RequiredArgsConstructor
public class MessageProcessor {

    private final ProcessApi processApi;
    private final ErrorApi errorApi;
    private final CalcNumberInPort calcNumberUseCase;

    @Bean
    public Consumer<Message<CalcNumberDto>> calcNumber() {
        return message -> {
            try {
                final CalcNumberDto payload = message.getPayload();
                final int erg = this.calcNumberUseCase.calc(payload.getNumber());

                this.processApi.correlateMessage(
                        message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID).toString(),
                        message.getHeaders().get(DIGIWF_MESSAGE_NAME).toString(),
                        Map.of("erg", erg));

            } catch (final BpmnError bpmnError) {
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }


}
