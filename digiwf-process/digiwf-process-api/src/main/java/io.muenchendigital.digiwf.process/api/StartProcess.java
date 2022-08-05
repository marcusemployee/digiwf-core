package io.muenchendigital.digiwf.process.api;

import java.util.Map;

/**
 * Interface of the object for starting a process
 */
public interface StartProcess {

    String getKey();

    Map<String, Object> getData();

}
