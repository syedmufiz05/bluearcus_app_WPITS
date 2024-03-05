package com.bluearcus.controller;

import com.bluearcus.dto.CategoryDto;
import com.bluearcus.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@CrossOrigin("*")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/detail/add", method = RequestMethod.POST)
	public CategoryDto addCategory(@RequestParam("ctg_name") String ctgName, HttpServletRequest httpServletRequest)
			throws JsonProcessingException {
		String authToken = httpServletRequest.getHeader("Authorization").replace("Bearer", "");
		return categoryService.addCategory(ctgName, authToken);
	}

	@RequestMapping(value = "/detail/get/{category_id}", method = RequestMethod.GET)
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("category_id") Integer categoryId) {
		return categoryService.getCategory(categoryId);
	}

	@RequestMapping(value = "/detail/get/all", method = RequestMethod.GET)
	public List<CategoryDto> getAllCategory() {
		return categoryService.getAllCategory();
	}

	@RequestMapping(value = "/detail/edit/{category_id}", method = RequestMethod.PUT)
	public ResponseEntity<CategoryDto> editCategory(@PathVariable("category_id") Integer categoryId,
			@RequestParam("category_name") String categoryName) throws JsonProcessingException {
		return categoryService.editCategory(categoryId, categoryName);
	}

	@RequestMapping(value = "/detail/delete/{category_id}", method = RequestMethod.DELETE)
	public String deleteCategory(@PathVariable("category_id") Integer categoryId) {
		return categoryService.deleteCategory(categoryId);
	}
}
