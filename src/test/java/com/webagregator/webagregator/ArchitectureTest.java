package com.webagregator.webagregator;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchitectureTest {
    private static final String EXTERN_LAYER_PACKAGE = "your.package.extern";
    private static final String APP_LAYER_PACKAGE = "your.package.app";
    private static final String DOMAIN_LAYER_PACKAGE = "your.package.domain";

    @Test
    public void testLayeredArchitecture() {
        JavaClasses importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("your.package");

        ArchRule layeredArchitecture = layeredArchitecture()
                .consideringAllDependencies().layer("Extern").definedBy(EXTERN_LAYER_PACKAGE)
                .layer("App").definedBy(APP_LAYER_PACKAGE)
                .layer("Domain").definedBy(DOMAIN_LAYER_PACKAGE)
                .whereLayer("Extern").mayOnlyBeAccessedByLayers("App", "Domain")
                .whereLayer("App").mayOnlyBeAccessedByLayers("Domain")
                .whereLayer("Domain").mayNotBeAccessedByAnyLayer();

        layeredArchitecture.check(importedClasses);
    }
}

