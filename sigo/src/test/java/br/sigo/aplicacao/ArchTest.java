package br.sigo.aplicacao;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("br.sigo.aplicacao");

        noClasses()
            .that()
                .resideInAnyPackage("br.sigo.aplicacao.service..")
            .or()
                .resideInAnyPackage("br.sigo.aplicacao.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..br.sigo.aplicacao.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
