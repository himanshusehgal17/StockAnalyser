package com.stock.market.wizard.converter;


import com.stock.market.wizard.Wizard;
import com.stock.market.wizard.dto.WizardDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WizardToWizardDTOConverter implements Converter<Wizard, WizardDTO> {


    @Override
    public WizardDTO convert(Wizard wizard) {
        WizardDTO wizardDTO = new WizardDTO(wizard.getId(),
                wizard.getName(),
                wizard.getNumberOfArtifacts());
        return wizardDTO;
    }

}
