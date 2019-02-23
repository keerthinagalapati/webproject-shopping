package net.kri.webprojectbackend.dao;

import java.util.List;

import net.kri.webprojectbackend.dto.Category;

public interface CategoryDAO {
	
	
	 List<Category> list();
	 Category get(int id);
	
	

}
