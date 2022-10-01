package org.magm.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.magm.backend.model.Audit;
import org.magm.backend.model.AuditSlimJsonSerializer;
import org.magm.backend.model.business.BusinessException;
import org.magm.backend.model.business.IAuditBusiness;
import org.magm.backend.util.IStandartResponseBusiness;
import org.magm.backend.util.JsonUtiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@RestController
@RequestMapping(Constants.URL_VIEW)
public class AuditRestController extends BaseRestController {
  @Autowired
  private IStandartResponseBusiness response;

  @Autowired
  private IAuditBusiness auditBusiness;


  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> list() {
    try {
      StdSerializer<Audit> ser = null;
      ser = new AuditSlimJsonSerializer(Audit.class, false);
      
      String result = JsonUtiles.getObjectMapper(Audit.class, ser, null)
          .writeValueAsString(auditBusiness.list());

      return new ResponseEntity<>(result, HttpStatus.OK);
      //return new ResponseEntity<>(auditBusiness.list(), HttpStatus.OK);
    } catch (BusinessException | JsonProcessingException e) {
      return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
