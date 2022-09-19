package org.magm.backend.integration.cli2.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.magm.backend.integration.cli1.model.ProductCli1JsonDeserializer;
import org.magm.backend.model.Item;
import org.magm.backend.util.JsonUtiles;

import java.io.IOException;

public class BillCli2SlimV2JsonSerializer extends StdSerializer<BillCli2> {

    private static final long serialVersionUID = -4721801379495083847L;

    public BillCli2SlimV2JsonSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    @Override
    public void serialize(BillCli2 billCli2, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();

        for (Item itemCli2 : billCli2.getItems()) {

            jsonGenerator.writeNumberField("id", itemCli2.getId());
            jsonGenerator.writeStringField("version", "v2");
            jsonGenerator.writeNumberField("quantity", itemCli2.getQuantity());
            jsonGenerator.writeNumberField("price", itemCli2.getPrice());

            String productStr = JsonUtiles
                    .getObjectMapper(ProductCli2.class, new ProductCli2SlimV1JsonSerializer(ProductCli2.class, false),
                            null)
                    .writeValueAsString(itemCli2.getProduct());
            jsonGenerator.writeFieldName("products");
            jsonGenerator.writeRawValue(productStr);
            
        }
        jsonGenerator.writeEndObject();
    }
}
