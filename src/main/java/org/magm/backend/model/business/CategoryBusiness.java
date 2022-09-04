package org.magm.backend.model.business;

import java.util.List;
import java.util.Optional;
import org.magm.backend.model.Category;
import org.magm.backend.model.persistence.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class CategoryBusiness implements ICategoryBusiness{

	@Autowired
	private ICategoryRepository CategoryDAO;
	
	@Override
	public Category load(long id) throws NotFoundException, BusinessException {
		Optional<Category> r;
		try {
			r=CategoryDAO.findById(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}
		if(r.isEmpty()) {
			throw NotFoundException.builder().message("No se encuentra el Categoryo id=" + id).build();
		}
			
		return r.get();
	}

	@Override
	public Category load(String Category) throws NotFoundException, BusinessException {
		Optional<Category> r;
		try {
			r = CategoryDAO.findOneByCategory(Category);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}
		if (r.isEmpty()) {
			throw NotFoundException.builder().message("No se encuentra el Categoryo '"+Category+"'").build();
		}
		return r.get();
	}

	@Override
	public List<Category> list() throws BusinessException {
		try {
			return CategoryDAO.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}

	}

	@Override
	public Category add(Category Category) throws FoundException, BusinessException {
		try {
			load(Category.getId());
			throw FoundException.builder().message("Se encuentró el Categoryo id=" + Category.getId()).build();
		} catch (NotFoundException e) {
		}
		try {
			load(Category.getCategory());
			throw FoundException.builder().message("Se encuentró el Categoryo '" + Category.getCategory() +"'").build();
		} catch (NotFoundException e) {
		}

		try {
			return CategoryDAO.save(Category);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}

	}

	@Override
	public Category update(Category Category) throws NotFoundException, BusinessException {
		load(Category.getId());
		try {
			return CategoryDAO.save(Category);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}

	}

	@Override
	public void delete(long id) throws NotFoundException, BusinessException {
		load(id);
		try {
			 CategoryDAO.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}


	}

}
