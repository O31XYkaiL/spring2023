package com.webagregator.webagregator;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import org.junit.jupiter.api.Test;

public class ArchitectureTest {

    private final JavaClasses classes = new ClassFileImporter().importPackages("com.webagregator.webagregator");

    @Test
    public void modelLayerShouldNotDependOnOtherLayers() {
        ArchRule rule = layeredArchitecture()
                .consideringAllDependencies().layer("Model").definedBy("com.webagregator.webagregator.model..")
                .whereLayer("Model").mayNotBeAccessedByAnyLayer();

        rule.check(classes);
    }

    @Test
    public void repositoryLayerShouldOnlyDependOnModelLayer() {
        ArchRule rule = layeredArchitecture()
                .consideringAllDependencies().layer("Repository").definedBy("com.webagregator.webagregator.repository..")
                .whereLayer("Repository").mayOnlyBeAccessedByLayers("Model");

        rule.check(classes);
    }

    @Test
    public void infrastructureLayerShouldOnlyDependOnRepositoryAndSecurityLayers() {
        ArchRule rule = layeredArchitecture()
                .consideringAllDependencies().layer("Infrastructure").definedBy("com.webagregator.webagregator.infrastructure..")
                .whereLayer("Infrastructure").mayOnlyBeAccessedByLayers("Repository", "Security");

        rule.check(classes);
    }

    @Test
    public void serviceLayerShouldOnlyDependOnRepositoryAndInfrastructureLayers() {
        ArchRule rule = layeredArchitecture()
                .consideringAllDependencies().layer("Service").definedBy("com.webagregator.webagregator.service..")
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Repository", "Infrastructure");

        rule.check(classes);
    }

    @Test
    public void controllerLayerShouldOnlyDependOnServiceAndSecurityLayers() {
        ArchRule rule = layeredArchitecture()
                .consideringAllDependencies().layer("Controller").definedBy("com.webagregator.webagregator.controller..")
                .whereLayer("Controller").mayOnlyBeAccessedByLayers("Service", "Security");

        rule.check(classes);
    }

    @Test
    public void securityLayerShouldOnlyDependOnServiceLayer() {
        ArchRule rule = layeredArchitecture()
                .consideringAllDependencies().layer("Security").definedBy("com.webagregator.webagregator.security..")
                .whereLayer("Security").mayOnlyBeAccessedByLayers("Service");

        rule.check(classes);
    }
}
