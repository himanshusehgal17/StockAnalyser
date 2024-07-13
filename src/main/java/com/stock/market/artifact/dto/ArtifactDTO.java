package com.stock.market.artifact.dto;

import com.stock.market.wizard.dto.WizardDTO;

public record ArtifactDTO (String id,
                           String name,
                           String description,
                           String imageUrl,
                           WizardDTO owner){
}
