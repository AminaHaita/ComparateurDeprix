package com.model.dao;
import java.util.List;

import com.model.beans.Product;
public interface ProductDao {
	List<Product> lister();
	List<Product> listerParCategorie(String idp);
	List<Product> listerParMotCle(String keyword);
	Product getProductById(String idp);
	/*ajouté par oussema*/
	List<Product> listerFavoriProductByIdList(List<Long> list ) ;	
}
