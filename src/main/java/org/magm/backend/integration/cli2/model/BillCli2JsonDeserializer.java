package org.magm.backend.integration.cli2.model;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import org.magm.backend.util.JsonUtiles;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class BillCli2JsonDeserializer extends StdDeserializer<BillCli2>{
	
	private static final long serialVersionUID = 6540759615234879627L;

	ItemCli2 itemCli2;
	
	protected BillCli2JsonDeserializer(Class<?> vc, ItemCli2 itemCli2) {
		super(vc);
		this.itemCli2 = itemCli2;
	}

	@Override
	public BillCli2 deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		BillCli2 bc = new BillCli2();
		JsonNode node = p.getCodec().readTree(p);
		
		long id = (long) JsonUtiles.getDouble(node, "id_bill,bill_id,id".split(","), 0);
		
		long billNumber =(long) JsonUtiles.getDouble(node,
				"bill,number,bill_number".split(","), 0);
		
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-mm-dd");
		
		Date issue = new Date();
		
		try {
			 issue = parser.parse(JsonUtiles.getString(node, "bill_issue,issue_bill,issue".split(","), null));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Date expiration = new Date();
		
		try {
			expiration = parser.parse(JsonUtiles.getString(node, "bill_expiration,expiration_bill,expiration".split(","), null));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		boolean annulled = JsonUtiles.getBoolean(node, "annulled".split(","), false);
		
		String s = JsonUtiles.getString(node, "items,bill_items,items_bill".split(","), null); 
		
		Object ob = s;
		
		Set<ItemCli2> it = null;
		
		it.add((ItemCli2) ob); 
		
		bc.setId(id);
		
		bc.setNumber(billNumber);

		bc.setIssueDate(issue);
		
		bc.setExpirationDate(expiration);
		
		bc.setAnnulled(annulled);
		
		bc.setItems(it);
		
		return bc;
	}

}
