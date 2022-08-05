package io.muenchendigital.digiwf.process.api;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * Kafka Event for starting a process
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StartProcessEvent implements StartProcess {

    @NotBlank
    private String key;

    private Map<String, Object> data;

}
