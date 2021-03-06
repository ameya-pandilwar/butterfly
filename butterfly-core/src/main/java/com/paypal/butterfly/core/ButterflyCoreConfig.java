package com.paypal.butterfly.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring Boot configuration class to automatically
 * register Butterfly Core Spring beans
 *
 * @author facarvalho
 */
@Configuration
@Import({
        ExtensionRegistry.class,
        ButterflyFacadeImpl.class,
        TransformationEngine.class,
        TransformationValidatorImpl.class,
        CompressionHandler.class,
        ManualInstructionsHandler.class,
        GitHubMdFileManualInstructionsWriter.class,
})
public class ButterflyCoreConfig {
}
