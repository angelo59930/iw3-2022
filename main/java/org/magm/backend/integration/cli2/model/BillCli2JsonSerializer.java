package org.magm.backend.integration.cli2.model;

import java.io.IOException;

import org.magm.backend.util.JsonUtiles;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class BillCli2JsonSerializer extends StdSerializer<BillCli2>{

	protected BillCli2JsonSerializer(Class<?> t, boolean dummy) {
		super(t, dummy);
	}

	private static final long serialVersionUID = -5021127035575906275L;

	@Override
	public void serialize(BillCli2 value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("id", value.getId()); 
		gen.writeNumberField("number", value.getNumber());
		gen.writeObjectField("issueDate", value.getIssueDate());
		gen.writeObjectField("expirationDate", value.getExpirationDate());
		gen.writeBooleanField("annulled", value.isAnnulled());

		// items
		String itemsStr = JsonUtiles
				.getObjectMapper(ComponentCli2.class, new ComponentCli2JsonSerializer(ComponentCli2.class, false), null)
				.writeValueAsString(value.getItems());
		gen.writeFieldName("items");
		gen.writeRawValue(itemsStr);
		
		gen.writeEndObject();
		
		
	}

}
