package org.magm.backend.integration.cli2.controllers;

import java.util.Calendar;
import java.util.Date;

import org.magm.backend.controllers.BaseRestController;
import org.magm.backend.controllers.Constants;
import org.magm.backend.integration.cli2.model.BillCli2;
import org.magm.backend.integration.cli2.model.BillCli2SlimV1JsonSerializer;
import org.magm.backend.integration.cli2.model.BillCli2SlimV2JsonSerializer;
import org.magm.backend.integration.cli2.model.ItemCli2;
import org.magm.backend.integration.cli2.model.ProductCli2;
import org.magm.backend.integration.cli2.model.ProductCli2SlimV1JsonSerializer;
import org.magm.backend.integration.cli2.model.business.IBillCli2Business;
import org.magm.backend.integration.cli2.model.business.IItemCli2Business;
import org.magm.backend.integration.cli2.model.business.IProductCli2Business;
import org.magm.backend.model.business.BusinessException;
import org.magm.backend.model.business.FoundException;
import org.magm.backend.model.business.NotFoundException;
import org.magm.backend.util.IStandartResponseBusiness;
import org.magm.backend.util.JsonUtiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@Profile({ "cli2", "mysqldev" })
@RestController
@RequestMapping(Constants.URL_INTEGRATION_CLI2)
public class ProductCli2RestController extends BaseRestController {

	@Autowired
	private IProductCli2Business productBusiness;

	@Autowired
	private IStandartResponseBusiness response;

	@Autowired
	private IBillCli2Business billBusiness;

	@Autowired
	private IItemCli2Business itemBusiness;

	// PRODUCTS

	@GetMapping(value = "/list-expired", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listExpired(
			@RequestParam(name = "since", required = false, defaultValue = "1970-01-01 00:00:00") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date since,
			@RequestParam(name = "slim", required = false, defaultValue = "v0") String slimVersion) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(since);
			if (c.get(Calendar.YEAR) == 1970) {
				since = new Date();
			}
			StdSerializer<ProductCli2> ser = null;
			if (slimVersion.equalsIgnoreCase("v1")) {
				ser = new ProductCli2SlimV1JsonSerializer(ProductCli2.class, false);
			} else {
				return new ResponseEntity<>(productBusiness.listExpired(since), HttpStatus.OK);
			}
			String result = JsonUtiles.getObjectMapper(ProductCli2.class, ser, null)
					.writeValueAsString(productBusiness.listExpired(since));
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (BusinessException | JsonProcessingException e) {
			return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// BILLS
	@GetMapping(value = "/bills", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> list(@RequestParam(name = "productid", required = false, defaultValue = "0") long id) {
		try {
			if ((long) id != 0) {
				return new ResponseEntity<>(billBusiness.loadBillByProduct(id), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(billBusiness.list(), HttpStatus.OK);
			}

		} catch (BusinessException e) {
			return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/bills/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> load(@PathVariable("id") long id,
			@RequestParam(name = "slim", required = false, defaultValue = "v0") String slimVersion) {
		try {
			String result;
			StdSerializer<BillCli2> ser = null;

			if (slimVersion.equalsIgnoreCase("v1")) {
				ser = new BillCli2SlimV1JsonSerializer(BillCli2.class, false);
				result = JsonUtiles.getObjectMapper(BillCli2.class, ser, null)
						.writeValueAsString(billBusiness.load(id));
			} else if (slimVersion.equalsIgnoreCase("v2")) {
				ser = new BillCli2SlimV2JsonSerializer(BillCli2.class, false);
				result = JsonUtiles.getObjectMapper(BillCli2.class, ser, null)
						.writeValueAsString(billBusiness.loadSlimView(id));

			} else {
				return new ResponseEntity<>(billBusiness.load(id), HttpStatus.OK);
			}

			return new ResponseEntity<>(result, HttpStatus.OK);

		} catch (BusinessException | JsonProcessingException e) {
			return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * @GetMapping(value = "/bills/{id}", produces =
	 * MediaType.APPLICATION_JSON_VALUE)
	 * public ResponseEntity<?> load(@PathVariable("id") long id) {
	 * try {
	 * return new ResponseEntity<>(billBusiness.load(id), HttpStatus.OK);
	 * } catch (BusinessException e) {
	 * return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR,
	 * e, e.getMessage()),
	 * HttpStatus.INTERNAL_SERVER_ERROR);
	 * } catch (NotFoundException e) {
	 * return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e,
	 * e.getMessage()), HttpStatus.NOT_FOUND);
	 * }
	 * }
	 */
	@GetMapping(value = "/bills/noAnulled", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listNoAnulled() {
		try {
			return new ResponseEntity<>(billBusiness.listNoAnulled(), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/bills")
	public ResponseEntity<?> add(@RequestBody BillCli2 bill) {
		try {
			BillCli2 response = billBusiness.add(bill);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("location", Constants.URL_PRODUCTS + "/" + response.getId());
			return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
		} catch (FoundException e) {
			return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (BusinessException e) {
			return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/bills")
	public ResponseEntity<?> update(@RequestBody BillCli2 bill) {
		try {
			billBusiness.update(bill);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value = "/bills/anulled/{id}")
	public ResponseEntity<?> update(@PathVariable("id") long id) {
		try {
			billBusiness.anulledBill(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}

	// ITEMS

	@PostMapping(value = "/items")
	public ResponseEntity<?> addItems(@RequestBody ItemCli2 item) {
		try {
			ItemCli2 response = itemBusiness.add(item);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("location", Constants.URL_PRODUCTS + "/" + response.getId());
			return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
		} catch (FoundException e) {
			return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (BusinessException e) {
			return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/items/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> loadItem(@PathVariable("id") long id) {
		try {
			return new ResponseEntity<>(itemBusiness.load(id), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value = "/items")
	public ResponseEntity<?> updateItems(@RequestBody ItemCli2 item) {
		try {
			itemBusiness.update(item);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}

}
