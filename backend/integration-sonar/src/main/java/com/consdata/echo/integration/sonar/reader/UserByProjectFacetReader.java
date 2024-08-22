package com.consdata.echo.integration.sonar.reader;

import com.consdata.echo.integration.sonar.connector.Facet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.List;

@RequiredArgsConstructor
public class UserByProjectFacetReader implements ItemReader<Facet> {

    private List<String> users;
    private List<String> projects;

    @Override
    public Facet read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
