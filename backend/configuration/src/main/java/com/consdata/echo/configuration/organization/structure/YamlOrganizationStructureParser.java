package com.consdata.echo.configuration.organization.structure;

import com.consdata.echo.organization.OrganizationalUnit;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
public class YamlOrganizationStructureParser {

    private final String path;
    private final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    @SneakyThrows(IOException.class)
    public OrganizationalUnit parse() {
        OrganizationalUnit output = objectMapper.readerFor(OrganizationalUnit.class).readValue(new File(path));
        if (!output.id().equals("0")) {
            throw new IllegalStateException("Root unit id cannot be different than 0");
        }
        return output;
    }


}
