package com.bluearcus.service;

import com.bluearcus.dto.CategoryDto;
import com.bluearcus.dto.CtgNameDto;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.AccessLogs;
import com.bluearcus.model.Category;
import com.bluearcus.repo.AccessLogsRepository;
import com.bluearcus.repo.CategoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AccessLogsRepository accessLogsRepository;

    @Override
    public CategoryDto addCategory(String ctgName, String authToken) throws JsonProcessingException {
        Category categoryDb = new Category();
        CtgNameDto ctgNameDto = new CtgNameDto();
        ctgNameDto.setCategoryName(ctgName);
        categoryDb.setName(ctgNameDto.getCategoryName());
        categoryRepository.save(categoryDb);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(categoryDb.getId());
        categoryDto.setName(ctgName);
        AccessLogs accessLogs = new AccessLogs();
        accessLogs.setUserId(1212);
        accessLogs.setReqPayload(convertEntityToJson(categoryDto));
        accessLogs.setResponsePayload("");
        accessLogs.setAuthToken(authToken);
        accessLogsRepository.save(accessLogs);
        categoryDb.setAccessLogs(accessLogs);
        categoryRepository.save(categoryDb);
        return new CategoryDto(categoryDto.getCategoryId(), categoryDto.getName());
    }

    @Override
    public ResponseEntity getCategory(Integer categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            Category categoryDb = category.get();
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setCategoryId(categoryDb.getId());
            categoryDto.setName(categoryDb.getName());
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Category Id does n't exist"));
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<CategoryDto> categoryDb = categoryRepository.fetchAllCategory();
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (CategoryDto categoryDto : categoryDb) {
            CategoryDto categoryDtoNew = new CategoryDto();
            categoryDtoNew.setCategoryId(categoryDto.getCategoryId());
            categoryDtoNew.setName(categoryDto.getName());
            categoryDtoList.add(categoryDtoNew);
        }
        return categoryDtoList;
    }

    @Override
    public ResponseEntity editCategory(Integer categoryId, String categoryName) throws JsonProcessingException {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            Category categoryDb = category.get();
            CtgNameDto ctgNameDto = new CtgNameDto();
            ctgNameDto.setCategoryName(!categoryName.isEmpty() ? categoryName : categoryDb.getName());
            categoryDb.setName(!categoryName.isEmpty() ? categoryName : categoryDb.getName());
            CategoryDto categoryDto = new CategoryDto(categoryId, categoryName);
            Optional<AccessLogs> accessLogs = accessLogsRepository.findById(categoryDb.getAccessLogs() != null ? categoryDb.getAccessLogs().getId() : 0);
            if (accessLogs.isPresent()) {
                AccessLogs accessLogsDb = accessLogs.get();
                accessLogsDb.setAuthToken("");
                accessLogsDb.setAccessDateTime(new Date());
                accessLogsDb.setReqPayload(convertEntityToJson(categoryDto));
                accessLogsDb.setResponsePayload("");
                accessLogsDb.setUserId(1212);
                accessLogsRepository.save(accessLogsDb);
                return new ResponseEntity<>(categoryDto, HttpStatus.OK);
            }
            AccessLogs accessLogsNew = new AccessLogs();
            accessLogsNew.setAuthToken("");
            accessLogsNew.setAccessDateTime(new Date());
            accessLogsNew.setReqPayload(convertEntityToJson(categoryDto));
            accessLogsNew.setResponsePayload("");
            accessLogsNew.setUserId(1212);
            accessLogsRepository.save(accessLogsNew);
            categoryDb.setAccessLogs(accessLogsNew);
            categoryRepository.save(categoryDb);
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Category Id does n't exist"));
    }

    @Override
    public String deleteCategory(Integer categoryId) {
        categoryRepository.deleteById(categoryId);
        return "Deleted successfully...";
    }

    private String convertEntityToJson(CategoryDto categoryDto) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String body = ow.writeValueAsString(categoryDto);
        body = body.replaceAll("(\\r|\\n)", "");
        return body;
    }

    private String convertStringToJson(CtgNameDto ctgNameDto) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String body = ow.writeValueAsString(ctgNameDto);
        body = body.replaceAll("(\\r|\\n)", "");
        return body;
    }

    private String fetchValueFromJsonData(String name) {
        String value = name.replaceAll("\\\\", "");
        String catName = value.substring(1, value.length() - 1).replaceAll("\\s", "");
        JSONObject jsonObject = new JSONObject(catName);
        return jsonObject.get("category_name").toString();
    }
}
