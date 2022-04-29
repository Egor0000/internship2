package com.europe.internship.internship.UniversityServiceTests;

import com.europe.internship.internship.dtos.UniversityDTO;
import com.europe.internship.internship.entities.University;
import com.europe.internship.internship.services.UniversityService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.BodyInserters;

import javax.servlet.http.HttpServletRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient(timeout = "PT1M")//30 seconds
public class UniversityE2ETests {
    private String serverURL;

    @LocalServerPort
    private int port;

    private final WebTestClient webTestClient;

    @Mock
    private HttpServletRequest request;

    private final UniversityService service;

    @BeforeAll
    public void setUp(){
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        serverURL = String.format("%s:%s", "localhost", port);
    }

    @AfterEach
    public void clearEach() {
//        service.deleteAll();
    }

    @Test
    @DisplayName("Save a valid university and return a valid saved dto")
    public void save_validUniversity_validSaveUniversity(){
        // arrange
        UniversityDTO createDTO = new UniversityDTO();
        createDTO.setName("Universitatea tehnica");
        createDTO.setShortName("UTM");
        createDTO.setFoundationYear(1964);

        // act
        UniversityDTO savedUniversity = webTestClient
                .post()
                .uri(serverURL + "/api/service/university/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createDTO))
                .exchange()
                .expectBody(UniversityDTO.class)
                .returnResult()
                .getResponseBody();
        // assert

        Assertions.assertNotNull(savedUniversity);

        Assertions.assertEquals(createDTO.getName(), savedUniversity.getName());
        Assertions.assertEquals(createDTO.getShortName(), savedUniversity.getShortName());
        Assertions.assertEquals(createDTO.getFoundationYear(), savedUniversity.getFoundationYear());

        this.webTestClient
                .delete()
                .uri(serverURL + "/api/service/university/" + savedUniversity.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @ParameterizedTest
    @ValueSource(strings = {"UTM", "USM", "USAM"})
    @DisplayName("Update a valid university and return a valid updated dto")
    public void updateUniversity(String name) {
        //arrange
        UniversityDTO createDTO = new UniversityDTO();
        createDTO.setName("Universitatea tehnica");
        createDTO.setShortName(name);
        createDTO.setFoundationYear(1964);

        UniversityDTO savedUniversity = webTestClient
                .post()
                .uri(serverURL + "/api/service/university/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createDTO))
                .exchange()
                .expectBody(UniversityDTO.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(savedUniversity);

        savedUniversity.setName("Universitatea de stat");
        savedUniversity.setShortName(name);
        savedUniversity.setFoundationYear(1946);

        // act
        UniversityDTO updatedDto = webTestClient
                .put()
                .uri(serverURL + "/api/service/university/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(savedUniversity))
                .exchange()
                .expectBody(UniversityDTO.class)
                .returnResult()
                .getResponseBody();

        //assert
        Assertions.assertNotNull(updatedDto);
        Assertions.assertEquals(savedUniversity, updatedDto);

        this.webTestClient
                .delete()
                .uri(serverURL + "/api/service/university/" + savedUniversity.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    @DisplayName("Delete unviersity")
    public void deleteUniversityById(){
        //arrange
        UniversityDTO createDTO = new UniversityDTO();
        createDTO.setName("Universitatea tehnica");
        createDTO.setShortName("UTM");
        createDTO.setFoundationYear(1964);

        UniversityDTO savedUniversity = webTestClient
                .post()
                .uri(serverURL + "/api/service/university/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createDTO))
                .exchange()
                .expectBody(UniversityDTO.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(savedUniversity);

        //act
        this.webTestClient
                .delete()
                .uri(serverURL + "/api/service/university/" + savedUniversity.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();

    }

    @Test
    @DisplayName("Get university")
    public void getUniversity(){
        //arrange
        UniversityDTO createDTO = new UniversityDTO();
        createDTO.setName("Universitatea tehnica");
        createDTO.setShortName("UTM");
        createDTO.setFoundationYear(1964);

        UniversityDTO savedUniversity = webTestClient
                .post()
                .uri(serverURL + "/api/service/university/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createDTO))
                .exchange()
                .expectBody(UniversityDTO.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(savedUniversity);

        //act
        UniversityDTO gotUniversity = this.webTestClient
                .get()
                .uri(serverURL + "/api/service/university/" + savedUniversity.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(UniversityDTO.class)
                .returnResult()
                .getResponseBody();

        // assertions
        Assertions.assertNotNull(gotUniversity);
        Assertions.assertEquals(createDTO.getName(), gotUniversity.getName());
        Assertions.assertEquals(createDTO.getShortName(), gotUniversity.getShortName());
        Assertions.assertEquals(createDTO.getFoundationYear(), gotUniversity.getFoundationYear());

        this.webTestClient
                .delete()
                .uri(serverURL + "/api/service/university/" + savedUniversity.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();
    }



}
