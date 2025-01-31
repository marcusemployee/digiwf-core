package de.muenchen.oss.digiwf.s3.integration.client.service;

import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import de.muenchen.oss.digiwf.s3.integration.client.ApiClient;
import de.muenchen.oss.digiwf.s3.integration.client.api.FileApiApi;
import de.muenchen.oss.digiwf.s3.integration.client.api.FolderApiApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Slf4j
public class ApiClientFactory {

    private final String defaultDocumentStorageUrl;

    private final WebClient webClient;

    public String getDefaultDocumentStorageUrl() throws PropertyNotSetException {
        if (StringUtils.isNotBlank(this.defaultDocumentStorageUrl)) {
            return this.defaultDocumentStorageUrl;
        }
        final String message = "Default document storage is not set. Make sure the property io.muenchendigital.digiwf.s3.client.documentStorageUrl is set.";
        log.error(message);
        throw new PropertyNotSetException(message);
    }

    public FileApiApi getFileApiForDocumentStorageUrl(final String documentStorageUrl) {
        return new FileApiApi(this.getApiClientForDocumentStorageUrl(documentStorageUrl));
    }

    public FolderApiApi getFolderApiForDocumentStorageUrl(final String documentStorageUrl) {
        return new FolderApiApi(this.getApiClientForDocumentStorageUrl(documentStorageUrl));
    }

    private ApiClient getApiClientForDocumentStorageUrl(final String documentStorageUrl) {
        final ApiClient apiClient = new ApiClient(this.webClient);
        apiClient.setBasePath(documentStorageUrl);
        return apiClient;
    }

}
