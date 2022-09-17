package org.magm.backend.integration.cli2.model;

import java.io.IOException;

import org.magm.backend.model.Item;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class BillCli2SlimV1JsonSerializer extends StdSerializer<BillCli2>{

	private static final long serialVersionUID = -2957189280265216562L;

	protected BillCli2SlimV1JsonSerializer(Class<?> t, boolean dummy) {
		super(t, dummy);
	}

	@Override
	public void serialize(BillCli2 value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		
		
		//issueDate, expirationDate, billNumber, annulled, total(cantidad * precio)
		gen.writeStartObject();
		gen.writeStringField("version", "v1");
		gen.writeNumberField("number", value.getNumber());
		gen.writeObjectField("issueDate", value.getIssueDate());
		gen.writeObjectField("expirationDate",value.getExpirationDate());
		gen.writeBooleanField("annulled",value.isAnnulled());
		
		double total = 0;
		
		for(Item item: value.getItems()) {
			total = total + item.getPrice() * item.getQuantity();
		}
		
		gen.writeNumberField("total", total);
		gen.writeEndObject();
		
	}
}
