package org.magm.backend.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class AuditSlimJsonSerializer extends StdSerializer<Audit> {

  public AuditSlimJsonSerializer(Class<?> t, boolean dummy) {
    super(t, dummy);
  }

  @Override
  public void serialize(Audit value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeStartObject();

    gen.writeStringField("type", value.getType());
    gen.writeStringField("user", value.getUser().getUsername());
    gen.writeNumberField("idOriginalBill", value.getBill().getId());
    gen.writeObjectField("operationDate", value.getAuditDate());

    gen.writeEndObject();
  }

}
