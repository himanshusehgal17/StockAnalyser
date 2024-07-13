package com.stock.market.artifact.converter;

import com.stock.market.artifact.Artifact;
import com.stock.market.artifact.dto.ArtifactDTO;
import com.stock.market.wizard.converter.WizardToWizardDTOConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArtifactToArtifactDTOConverter implements Converter<Artifact, ArtifactDTO> {

    private final WizardToWizardDTOConverter wizardToWizardDTOConverter;

    public ArtifactToArtifactDTOConverter(WizardToWizardDTOConverter wizardToWizardDTOConverter) {
        this.wizardToWizardDTOConverter = wizardToWizardDTOConverter;
    }

    @Override
    public ArtifactDTO convert(Artifact artifact) {
        return new ArtifactDTO(
                artifact.getId(),
                artifact.getName(),
                artifact.getDescription(),
                artifact.getImageUrl(),
                artifact.getOwner() != null ? this.wizardToWizardDTOConverter.convert(artifact.getOwner()) : null
        );
    }
}
