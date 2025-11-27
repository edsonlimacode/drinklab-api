package com.drinklab.core.jackson;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {


    @Override
    public void serialize(Page<?> page, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {

        generator.writeStartObject();
        generator.writeObjectField("data", page.getContent());
        generator.writeObjectField("totalElements", page.getTotalElements());
        generator.writeObjectField("size", page.getSize());
        generator.writeObjectField("currentPage", page.getNumber());
        generator.writeEndObject();
    }
}
