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

    /**
     * Key of the process
     */
    @NotBlank
    private String key;

    /**
     * Data that is set
     */
    private Map<String, Object> data;

}
