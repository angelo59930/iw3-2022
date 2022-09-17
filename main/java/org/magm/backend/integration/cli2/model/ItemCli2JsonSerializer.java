package org.magm.backend.integration.cli2.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ItemCli2JsonSerializer extends StdSerializer<ItemCli2>{

	protected ItemCli2JsonSerializer(Class<?> t, boolean dummy) {
		super(t, dummy);
	}

	private static final long serialVersionUID = 4456683128531801354L;

	@Override
	public void serialize(ItemCli2 value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("id", value.getId());
		gen.writeNumberField("quantity", value.getQuantity());
		gen.writeNumberField("price", value.getPrice());
		
		//object product
		gen.writeObjectFieldStart("product");
		gen.writeNumberField("id", value.getProduct().getId());
		gen.writeStringField("product", value.getProduct().getProduct());
		gen.writeNumberField("price", value.getProduct().getPrice());
		gen.writeEndObject();
		
		gen.writeEndObject();
		
	}
	

}
