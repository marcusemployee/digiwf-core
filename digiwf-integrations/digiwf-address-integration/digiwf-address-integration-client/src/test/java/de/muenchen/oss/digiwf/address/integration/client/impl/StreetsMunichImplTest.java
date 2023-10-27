package de.muenchen.oss.digiwf.address.integration.client.impl;

import de.muenchen.oss.digiwf.address.integration.client.api.StreetsMunichApi;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.integration.client.gen.api.StraenMnchenApi;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.*;
import de.muenchen.oss.digiwf.address.integration.client.model.request.ListStreetsModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

import java.util.List;

class StreetsMunichImplTest {

    private final StraenMnchenApi straessenMuenchenApi = Mockito.mock(StraenMnchenApi.class);
    private final StreetsMunichApi streetsMunich = new StreetsMunichImpl(straessenMuenchenApi);

    private final long streetId = 123;
    private final Strasse strasse = new Strasse();

    @BeforeEach
    void setup() {
        // basic street info
        strasse.setStrasseId(streetId);
        strasse.setStrassenname("streetName");
        strasse.setStrassennameKurz("streetNameShort");
        strasse.setStrassennameAbgekuerzt("streetNameAbbreviated");

        // geo info
        final Stadtbezirk stadtbezirk = new Stadtbezirk();
        stadtbezirk.setNummer(1L);
        stadtbezirk.setName("Stadtbezirk 1");

        final StrasseVerwaltungszuteilung verwaltungszuteilung = new StrasseVerwaltungszuteilung();
        verwaltungszuteilung.setStadtbezirke(List.of(stadtbezirk));

        final StrasseGeozuordnungen strasseGeozuordnungen = new StrasseGeozuordnungen();
        strasseGeozuordnungen.setVerwaltungszuteilung(verwaltungszuteilung);

        strasse.setGeozuordnungen(strasseGeozuordnungen);
    }

    @Test
    void testFindStreetsById_Success() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        final Strasse expectedStrasse = this.strasse;
        expectedStrasse.setStrasseId(this.streetId);
        Mockito.when(straessenMuenchenApi.findStrasseByNummer(streetId)).thenReturn(strasse);
        final Strasse result = streetsMunich.findStreetsById(streetId);
        Assertions.assertEquals(expectedStrasse, result);
    }

    @Test
    void testFindStreetsById_ClientErrorException() {
        Mockito.when(straessenMuenchenApi.findStrasseByNummer(streetId)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "message"));
        Assertions.assertThrows(AddressServiceIntegrationClientErrorException.class, () -> streetsMunich.findStreetsById(streetId));
    }

    @Test
    void testFindStreetsById_ServerErrorException() {
        Mockito.when(straessenMuenchenApi.findStrasseByNummer(streetId)).thenThrow(new HttpServerErrorException(HttpStatus.BAD_REQUEST, "message"));
        Assertions.assertThrows(AddressServiceIntegrationServerErrorException.class, () -> streetsMunich.findStreetsById(streetId));
    }

    @Test
    void testFindStreetsById_RestClientException() {
        Mockito.when(straessenMuenchenApi.findStrasseByNummer(streetId)).thenThrow(new RestClientException(""));
        Assertions.assertThrows(AddressServiceIntegrationException.class, () -> streetsMunich.findStreetsById(streetId));
    }

    @Test
    void testListStreets_Success() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        final ListStreetsModel listStreetsModel = ListStreetsModel.builder()
                .streetName("streetName")
                .build();

        final StrasseResponse expectedResponse = new StrasseResponse();
        expectedResponse.setPage(new AddressServicePage());
        final StrasseResponseItem strasseResponseItem = new StrasseResponseItem();
        strasseResponseItem.setScore(1.0f);
        strasseResponseItem.setStrasse(this.strasse);
        expectedResponse.setContent(List.of(strasseResponseItem));

        Mockito.when(straessenMuenchenApi.listStrassen(
                listStreetsModel.getCityDistrictNames(),
                listStreetsModel.getCityDistrictNumbers(),
                listStreetsModel.getStreetName(),
                listStreetsModel.getSortdir(),
                listStreetsModel.getPage(),
                listStreetsModel.getPagesize()
        )).thenReturn(expectedResponse);

        final StrasseResponse result = streetsMunich.listStreets(listStreetsModel);

        Assertions.assertEquals(expectedResponse, result);
    }

    @Test
    void testListStreets_ClientErrorException() {
        ListStreetsModel listStreetsModel = ListStreetsModel.builder().build(); // Create a valid model
        Mockito.when(straessenMuenchenApi.listStrassen(
                listStreetsModel.getCityDistrictNames(),
                listStreetsModel.getCityDistrictNumbers(),
                listStreetsModel.getStreetName(),
                listStreetsModel.getSortdir(),
                listStreetsModel.getPage(),
                listStreetsModel.getPagesize()
        )).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "message"));

        Assertions.assertThrows(AddressServiceIntegrationClientErrorException.class, () -> streetsMunich.listStreets(listStreetsModel));
    }

    @Test
    void testListStreets_ServerErrorException() {
        ListStreetsModel listStreetsModel = ListStreetsModel.builder().build(); // Create a valid model
        Mockito.when(straessenMuenchenApi.listStrassen(
                listStreetsModel.getCityDistrictNames(),
                listStreetsModel.getCityDistrictNumbers(),
                listStreetsModel.getStreetName(),
                listStreetsModel.getSortdir(),
                listStreetsModel.getPage(),
                listStreetsModel.getPagesize()
        )).thenThrow(new HttpServerErrorException(HttpStatus.BAD_REQUEST, "message"));

        Assertions.assertThrows(AddressServiceIntegrationServerErrorException.class, () -> streetsMunich.listStreets(listStreetsModel));
    }

    @Test
    void testListStreets_RestClientException() {
        ListStreetsModel listStreetsModel = ListStreetsModel.builder().build(); // Create a valid model
        Mockito.when(straessenMuenchenApi.listStrassen(
                listStreetsModel.getCityDistrictNames(),
                listStreetsModel.getCityDistrictNumbers(),
                listStreetsModel.getStreetName(),
                listStreetsModel.getSortdir(),
                listStreetsModel.getPage(),
                listStreetsModel.getPagesize()
        )).thenThrow(new RestClientException(""));

        Assertions.assertThrows(AddressServiceIntegrationException.class, () -> streetsMunich.listStreets(listStreetsModel));
    }

}