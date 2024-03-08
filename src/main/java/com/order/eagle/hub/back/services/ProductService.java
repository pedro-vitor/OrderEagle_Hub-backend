package com.order.eagle.hub.back.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.order.eagle.hub.back.entities.Category;
import com.order.eagle.hub.back.entities.Product;
import com.order.eagle.hub.back.entities.Store;
import com.order.eagle.hub.back.entities.dto.product.ProductGetDTO;
import com.order.eagle.hub.back.entities.enums.Situations;
import com.order.eagle.hub.back.repositories.ProductRepository;
import com.order.eagle.hub.back.services.util.ToolsService;

@Service
public class ProductService {

	private final String PATH_UPLOAD_IMG_PRODUCT = "src/main/resources/static/product/";
	
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private CategoryService categoryService;
	
	public Product findById(UUID productId) {
		return this.productRepository.findById(productId)
				.orElseThrow(() -> new IllegalArgumentException("Produto com o ID: '" + productId + "' inexistente"));
	}
	
	public List<Product> findByStore(UUID storeId){
		return this.productRepository.findByStore(storeId);
	}
	
	public Product insertByStore(ProductGetDTO dto, UUID storeID) {
		var store = storeService.findById(storeID);
		var category = categoryService.findById(dto.category(), storeID);
		
		var newProduct = this.toProduct(dto, category, store);
		
		return this.productRepository.save(newProduct);
	}
	
	public Product updateByStore(ProductGetDTO dto, UUID productId, UUID storeId) {
		var productForUpdate = this.findById(productId);
		var category = productForUpdate.getCategory().getId().equals(dto.category())
						? productForUpdate.getCategory()
						: categoryService.findById(dto.category(), storeId);
			
		this.changeValuesForUpdate(productForUpdate, dto, category);
		
		return this.productRepository.save(productForUpdate);
	}
	
	public void deleteByStore(UUID productId, UUID storeId) {
		var productForDelete = this.findById(productId);
		
		if(!productForDelete.getStore().getId().equals(storeId)) {
			throw new IllegalArgumentException("Não é possivel deletar o produto de outra loja.");
		}
		
		productForDelete.setSituation(Situations.DISABLED);
		productRepository.save(productForDelete);
	}
	
	public Product uploadImgProduct(UUID productId, MultipartFile logo) {
		var product = this.findById(productId);
		var pathLogo = ToolsService.saveImg(logo, this.PATH_UPLOAD_IMG_PRODUCT + product.getStore().getId());
		product.setImgUrl(pathLogo);
		return productRepository.save(product);
	}

	private Product toProduct(ProductGetDTO dto,Category category, Store store) {
		return new Product(null, dto.name(), dto.description(), dto.price(), store,  category);
		
	}
	
	private void changeValuesForUpdate(Product productForUpdate, ProductGetDTO dto, Category category) {
		productForUpdate.setName(dto.name());
		productForUpdate.setDescription(dto.description());
		productForUpdate.setPrice(dto.price());
		productForUpdate.setCategory(category);
	}
}
