package com.liebherr.hau.localapibackend.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI localApiBackendApi() {
        return new OpenAPI()
                .info(new Info().title("LocalAPI Portal API")
                        .description("The backend service used by the localAPI Portal UI")
                        .version("0.0.1"));
    }

    @Bean
    @SuppressWarnings({"rawtypes", "unchecked"})
    public OpenApiCustomizer customizeOutput() {
        return openApi -> {
            // Now handle the components we're transferring ... sort properties alphabetically
            openApi.getComponents().getSchemas().forEach((name, schema) -> {
                if (schema != null && schema.getProperties().size() > 1) {
                    Map<String, Schema> unsortedMap = schema.getProperties();
                    LinkedHashMap<String, Schema> sortedMap = new LinkedHashMap<>();

                    unsortedMap
                            .entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByKey())
                            .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

                    schema.setProperties(sortedMap);
                }
            });

            openApi.getPaths().values().stream().forEach(pathItem ->
                    pathItem.readOperationsMap()
                            .forEach((httpMethod, operation) -> {
                                String suffix = "Using" + httpMethod;
                                // Two strange issues solved here:
                                // This bean method is invoked twice:
                                // - when you call the "/ui/v3/api-docs" endpoint or create the frontside API
                                // - when you call the Swagger UI
                                // In both calls the operation ID can be mangled: ("getUserSettings_1") or already
                                // changed and mangled: "getUserSettingsUsingGET_1"
                                // resulting in "getUserSettingsUsingGET_1UsingGET"!
                                // So first safe the original ID as description if not set yet:
                                if (operation.getDescription() == null) {
                                    operation.setDescription(operation.getOperationId());
                                } else {
                                    // Otherwise: restore from the description:
                                    operation.setOperationId(operation.getDescription());
                                }

                                String operationId = operation.getOperationId();

                                // Generate "UsingXXX" suffix like in Springfox
                                operation.setOperationId(operationId + suffix);

                                // Sort parameters alphabetically
                                List<Parameter> params = operation.getParameters();
                                if (params != null && params.size() > 1) {
                                    params.sort(Comparator.comparing(Parameter::getName));
                                }
                            }));
        };
    }
}