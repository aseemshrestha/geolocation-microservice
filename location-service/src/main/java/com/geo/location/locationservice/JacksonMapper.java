package com.geo.location.locationservice;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Singleton JacksonMapper
 * @author  Aseem Shrestha
 */
public enum JacksonMapper
{
    INSTANCE;

    private final ObjectMapper mapper = new ObjectMapper();

    JacksonMapper()
    {
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
    }

    public ObjectMapper getObjectMapper()
    {
        return mapper;
    }

}