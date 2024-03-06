package com.order.eagle.hub.back.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.eagle.hub.back.entities.Category;
import com.order.eagle.hub.back.entities.Store;
import com.order.eagle.hub.back.entities.dto.category.CategoryGetDTO;
import com.order.eagle.hub.back.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private StoreService storeService;
	
	public List<Category> findByStore(UUID isStore){
		var listCategory = categoryRepository.findByStore(isStore);
		if(listCategory.isEmpty())
			throw new IllegalArgumentException("Loja não possui categorias Cadastradas ou não existe");
		return listCategory;
	}
	
	public Category insertByStore(CategoryGetDTO dto, UUID idStore) {
		var store = storeService.findById(idStore);
		var newCategory = this.toCategory(dto, store);
		
		if(store.getCategories().contains(newCategory)) 
			throw new IllegalArgumentException("Já existe uma categoria com o nome '" + dto.name() + "'");
		
		return categoryRepository.save(newCategory);
	}
	
	public Category updateByStore(CategoryGetDTO dto, UUID idCategory, UUID idStore) {
		if(!categoryRepository.existsById(idCategory))
			throw new IllegalArgumentException("Não é possivel atualizar a categoria, pós a mesma não existe");
		
		var listCategory = this.findByStore(idStore);
		var newDatas = this.toCategory(dto, null);
		
		this.verifyExistsCategorySameName(listCategory, newDatas, idCategory);
		
		var categoryDestityNewDatas = this.findByIdInListCategories(listCategory, idCategory);
		
		if(categoryDestityNewDatas.getName().equals(dto.name().toUpperCase()))
			return categoryDestityNewDatas;
		
		this.changeValuesCategory(categoryDestityNewDatas, newDatas);
		
		return categoryRepository.save(categoryDestityNewDatas);
	}
	
	public void deleteByStore(UUID categotyId, UUID idStore) {
		var category = this.findById(categotyId);
		
		if(!category.getStore().getId().equals(idStore))
			throw new IllegalArgumentException("Não é possivel apagar a categoria de outra loja");
		
		if(!category.getProducts().isEmpty())
			throw new IllegalArgumentException("Não será possivel excluir esta categoria, pôs há produtos relacionados a ela."
					+ " Por favor troque a categoria dos produtos para que seja possivel a exclusão");
		
		categoryRepository.delete(category);;
	}
	
	private Category toCategory(CategoryGetDTO dto, Store store) {
		return new Category(null, dto.name().toUpperCase(), store);
	}
	
	private void verifyExistsCategorySameName(List<Category> listCategory, Category category, UUID idCategory) {
		for(Category cat : listCategory) {
			if(cat.getName().equals(category.getName())) {
				if(!cat.getId().equals(idCategory)) {
					throw new IllegalArgumentException("Já existe uma categoria com o nome '" + cat.getName() + "'");
				}
			}
		}
			
	}
	
	private Category findByIdInListCategories(List<Category> listCategory, UUID idCategory) {
		return listCategory.stream().filter(cat -> cat.getId().equals(idCategory)).findFirst()
				.get();
	}
	
	private void changeValuesCategory(Category category, Category newDatas) {
		category.setName(newDatas.getName());
	}

	private Category findById(UUID categotyId) {
		return categoryRepository.findById(categotyId)
		.orElseThrow(() -> new IllegalArgumentException("Categoria inexistente"));
	}
}
