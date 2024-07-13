package com.stock.market.artifact;

import com.stock.market.artifact.converter.ArtifactToArtifactDTOConverter;
import com.stock.market.artifact.dto.ArtifactDTO;
import com.stock.market.system.Result;
import com.stock.market.system.StatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class ArtifactController {

    private final ArtifactService artifactService;

    private final ArtifactToArtifactDTOConverter artifactToArtifactDTOConverter;

    public ArtifactController(ArtifactService artifactService, ArtifactToArtifactDTOConverter artifactToArtifactDTOConverter) {
        this.artifactService = artifactService;
        this.artifactToArtifactDTOConverter = artifactToArtifactDTOConverter;
    }

    @GetMapping("/api/v1/artifacts/{artifactId}")
    public Result findArtifactById(@PathVariable String artifactId) {
        Artifact foundArtifacts = this.artifactService.findById(artifactId);
        ArtifactDTO artifactDTO = artifactToArtifactDTOConverter.convert(foundArtifacts);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", artifactDTO);
    }

    @GetMapping("/api/v1/artifacts")
    public Result findAllArtifactById() {
        List<Artifact> foundAllArtifacts = this.artifactService.findAll();
        // Convert Artifacts to list of artifactDTO
        List<ArtifactDTO> artifactDTOS = foundAllArtifacts.stream().map(artifactToArtifactDTOConverter::convert).toList();

        return new Result(true,StatusCode.SUCCESS,"Find All Success",artifactDTOS);
    }

    @PostMapping("/api/v1/artifacts")
    public Result addArtifact(@RequestBody ArtifactDTO artifactDTO) {
        return null;
    }

}
