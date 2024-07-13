package com.stock.market.artifact;

import com.stock.market.wizard.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArtifactServiceTest {

    @Mock
    ArtifactRepository artifactRepository;

    @InjectMocks
    ArtifactService artifactService;

    List<Artifact> artifacts;

    @BeforeEach
    void setUp() {
        Artifact a1 = new Artifact();
        a1.setId("1234");
        a1.setName("Himanshu");
        a1.setDescription("This is my artifact");
        a1.setImageUrl("ImageUrl");


        Artifact a2 = new Artifact();
        a2.setId("12345");
        a2.setName("Mohit");
        a2.setDescription("This is my artifact");
        a2.setImageUrl("ImageUrl");

        this.artifacts = new ArrayList<>();
        this.artifacts.add(a1);
        this.artifacts.add(a2);


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        // Given. Arrange inputs and target. Define the behaviour of Mock object artifactRepository
        Artifact a = new Artifact();
        a.setId("1234567845678");
        a.setName("Invisibility Clock");
        a.setDescription("An invisibility clock is used to make the wearer invisible");
        a.setImageUrl("ImageUrl");

        Wizard w = new Wizard();
        w.setId(2);
        w.setName("Harry Potter");

        a.setOwner(w);

        given(artifactRepository.findById("1234567845678")).willReturn(Optional.of(a)); //

        // When. Act on target behaviour. When steps should cover the method to be tested.
        Artifact returnArtifacts = artifactService.findById("1234567845678");
        assertThat(returnArtifacts.getId()).isEqualTo(a.getId());
        assertThat(returnArtifacts.getName()).isEqualTo(a.getName());
        assertThat(returnArtifacts.getDescription()).isEqualTo(a.getDescription());
        assertThat(returnArtifacts.getImageUrl()).isEqualTo(a.getImageUrl());

        // Then. Assert expected outcomes.
        verify(artifactRepository, times(1)).findById("1234567845678");


    }

    @Test
    void testFindBYIdNotFound() {
        // Given.
        given(artifactRepository.findById(Mockito.anyString())).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> {
            Artifact returnArtifact = artifactService.findById("7894567898790");
        });

        // Then
        assertThat(thrown).isInstanceOf(ArtifactNotFoundException.class)
                .hasMessage("Could not find artifact with id 7894567898790 :(");

        verify(artifactRepository, times(1)).findById("7894567898790");
    }

    @Test
    void testFindAllSuccess() {
        // Given.
        given(artifactRepository.findAll()).willReturn(artifacts);

        // When.
        List<Artifact> actualArtifacts = artifactService.findAll();

        assertThat(actualArtifacts.size() == this.artifacts.size());
        verify(artifactRepository,times(1)).findAll();

        // Then.
    }
}