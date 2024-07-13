package com.stock.market.system;

import com.stock.market.artifact.Artifact;
import com.stock.market.artifact.ArtifactRepository;
import com.stock.market.wizard.Wizard;
import com.stock.market.wizard.WizardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final ArtifactRepository artifactRepository;

    private final WizardRepository wizardRepository;

    public DBDataInitializer(ArtifactRepository artifactRepository, WizardRepository wizardRepository) {
        this.artifactRepository = artifactRepository;
        this.wizardRepository = wizardRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Artifact a1 = new Artifact();
        a1.setId("1234");
        a1.setName("IT Engineer");
        a1.setDescription("This is my artifact");
        a1.setImageUrl("ImageUrl");

        Artifact a2 = new Artifact();
        a2.setId("12345");
        a2.setName("Software Developer");
        a2.setDescription("This is my artifact");
        a2.setImageUrl("ImageUrl");

        Artifact a3 = new Artifact();
        a3.setId("12346");
        a3.setName("CA Foundation");
        a3.setDescription("This is my artifact");
        a3.setImageUrl("ImageUrl");

        Artifact a4 = new Artifact();
        a4.setId("12347");
        a4.setName("Stock Trader");
        a4.setDescription("This is my artifact");
        a4.setImageUrl("ImageUrl");



        Wizard w1 = new Wizard();
        w1.setId(1);
        w1.setName("Himanshu Sehgal");
        w1.addArtifact(a1);
        w1.addArtifact(a2);

        Wizard w2 = new Wizard();
        w2.setId(2);
        w2.setName("Mohit Sehgal");
        w2.addArtifact(a3);
        w2.addArtifact(a4);

        wizardRepository.save(w1);
        wizardRepository.save(w2);
    }
}
