package com.estore.api.estoreapi.model.products;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Grouped the classes needed for jackson
 * to recoginze which product type it is
 * reading or writing
 */
@JsonComponent
public class ProductCombinedSerializer {

  /**
   * Class used by jackson when writing a product POJO to JSON data
   */
  public static class ProductSerializer extends JsonSerializer<Product> {

    @Override
    public void serialize(Product value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
      gen.writeStartObject();
      gen.writeNumberField("id", value.getId());
      gen.writeNumberField("productType", value.getProductType());
      gen.writeNumberField("price", value.getPrice());
      gen.writeNumberField("quantity", value.getQuantity());
      gen.writeStringField("name", value.getName());
      gen.writeStringField("description", value.getDescription());
      gen.writeStringField("fileName", value.getFileName());
      gen.writeObjectField("fileSource", value.getFileSource());

      if (value.getProductType() == 1) {

        String[] sponsors = ((BirdProduct) value).getSponsors();
        gen.writeArrayFieldStart("sponsors");
        for (String spon : sponsors) {
          gen.writeString(spon);
        }
        gen.writeEndArray();

      }

      gen.writeEndObject();

    }

  }

  /**
   * Class used by jackson when reading a JSON product to instantiate the correct
   * product POJO
   */
  public static class ProductDeserializer extends StdDeserializer<Product> {
    public ProductDeserializer() {
      this(null);
    }

    public ProductDeserializer(final Class<?> vc) {
      super(vc);
    }

    @Override
    public Product deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
      final JsonNode node = parser.getCodec().readTree(parser);
      final ObjectMapper mapper = (ObjectMapper) parser.getCodec();
      if (node.get("productType").asInt() == 1) {
        return mapper.treeToValue(node, BirdProduct.class);
      } else {
        return mapper.treeToValue(node, Accessory.class);
      }
    }
  }

}
