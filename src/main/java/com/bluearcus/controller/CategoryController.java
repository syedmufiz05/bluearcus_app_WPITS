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

	@PostMapping("/detail/add")
	public CategoryDto addCategory(@RequestParam("ctg_name") String ctgName, HttpServletRequest httpServletRequest)
			throws JsonProcessingException {
		String authToken = httpServletRequest.getHeader("Authorization").replace("Bearer", "");
		return categoryService.addCategory(ctgName, authToken);
	}

	@GetMapping("/detail/get/{category_id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("category_id") Integer categoryId) {
		return categoryService.getCategory(categoryId);
	}

	@GetMapping("/detail/get/all")
	public List<CategoryDto> getAllCategory() {
		return categoryService.getAllCategory();
	}

	@PutMapping("/detail/edit/{category_id}")
	public ResponseEntity<CategoryDto> editCategory(@PathVariable("category_id") Integer categoryId,
			@RequestParam("category_name") String categoryName) throws JsonProcessingException {
		return categoryService.editCategory(categoryId, categoryName);
	}

	@DeleteMapping("/detail/delete/{category_id}")
	public String deleteCategory(@PathVariable("category_id") Integer categoryId) {
		return categoryService.deleteCategory(categoryId);
	}
}
