package com.consdata.echo.configuration.organization.structure;

import com.consdata.echo.organization.OrganizationalUnit;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.util.List;

@RequiredArgsConstructor
public class YamlOrganizationStructureParser {

    private final String path;
    private final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    @SneakyThrows
    public List<OrganizationalUnit> parse() {
        return objectMapper.readerForListOf(OrganizationalUnit.class).readValue(new File(path));
    }


}
