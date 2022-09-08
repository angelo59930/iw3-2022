package org.magm.backend.model.business;

import java.util.List;

import org.magm.backend.model.Product;

public interface IProductBusiness {	
	public Product load(long id) throws NotFoundException, BusinessException;
	
	public Product load(String product) throws NotFoundException, BusinessException;
	
	public List<Product> list() throws BusinessException;

	public Product add(Product product) throws FoundException, BusinessException;

	public Product update(Product product) throws NotFoundException, BusinessException;

	public void delete(long id) throws NotFoundException, BusinessException;

}	
