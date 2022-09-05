package org.magm.backend.integration.cli1.model.business;

import java.util.List;
import java.util.Optional;

import org.magm.backend.integration.cli1.model.ProductCli1;
import org.magm.backend.integration.cli1.model.persistance.IProductCli1Repository;
import org.magm.backend.model.business.BusinessException;
import org.magm.backend.model.business.FoundException;
import org.magm.backend.model.business.IProductBusiness;
import org.magm.backend.model.business.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ProductCli1Business implements IProductCli1Business{
	
	@Autowired
	private IProductCli1Repository productDAO;

	@Override
	public ProductCli1 load(String codCli1) throws NotFoundException, BusinessException {
		Optional<ProductCli1> r;
		try {
			r = productDAO.findOneByCodCli1(codCli1);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}
		if (r.isEmpty()) {
			throw NotFoundException.builder().message("No se encuentra el ProductCli1 "+codCli1).build();
		}
		return r.get();
	}

	@Override
	public List<ProductCli1> list() throws BusinessException {
		try {
			return productDAO.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}

	}
	
	@Autowired
	private IProductBusiness productBaseBusiness;

	@Override
	public ProductCli1 add(ProductCli1 product) throws FoundException, BusinessException {
		try {
			productBaseBusiness.load(product.getId());
			throw FoundException.builder().message("Se encuentró el Producto id=" + product.getId()).build();
		} catch (NotFoundException e) {
			
		}
		if(productDAO.findOneByCodCli1(product.getCodCli1()).isPresent()) {
			throw FoundException.builder().message("Se encuentró el Producto codigo=" + product.getCodCli1()).build();
			
		}
		try {
			return productDAO.save(product);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}

	}
	
}
