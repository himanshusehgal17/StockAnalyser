package com.stock.market.artifact;

import com.stock.market.system.StatusCode;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
class ArtifactControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArtifactService artifactService;

    List<Artifact> artifacts;

    @BeforeEach
    void setUp() {

        this.artifacts = new ArrayList<>();

        Artifact a1 = new Artifact();
        a1.setId("1234");
        a1.setName("Himanshu");
        a1.setDescription("This is my artifact");
        a1.setImageUrl("ImageUrl");
        this.artifacts.add(a1);

        Artifact a2 = new Artifact();
        a2.setId("12345");
        a2.setName("Mohit");
        a2.setDescription("This is my artifact");
        a2.setImageUrl("ImageUrl");
        this.artifacts.add(a2);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findArtifactByIdSuccess() throws Exception {

        //Given
        given(this.artifactService.findById("1234")).willReturn(this.artifacts.get(0));

        //When and Then
        this.mockMvc.perform(get("/api/v1/artifacts/1234").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value("1234"))
                .andExpect(jsonPath("$.data.name").value("Himanshu"));

    }

    @Test
    void findArtifactByIdNotFound() throws Exception {

        //Given
        given(this.artifactService.findById("123456")).willThrow(new ArtifactNotFoundException("123456"));

        //When and Then
        this.mockMvc.perform(get("/api/v1/artifacts/123456").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find artifact with id 123456 :("))
                .andExpect(jsonPath("$.data").isEmpty());

    }

    @Test
    void findAllArtifactsSuccess() throws Exception {
        // Given
        given(this.artifactService.findAll()).willReturn(artifacts);

        // When and then
        this.mockMvc.perform(get("/api/v1/artifacts").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.artifacts.size())))
                .andExpect(jsonPath("$.data[0].id").value("1234"))
                .andExpect(jsonPath("$.data[0].name").value("Himanshu"));
    }

}