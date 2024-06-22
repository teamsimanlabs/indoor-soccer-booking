package com.indoor.controller;

import com.indoor.dao.FieldDao;
import com.indoor.exception.DaoException;
import com.indoor.model.Field;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/fields")
public class FieldController {

    private final FieldDao fieldDao;

    public FieldController(FieldDao fieldDao) {
        this.fieldDao = fieldDao;
    }

    @GetMapping
    public List<Field> getFields() {
        return fieldDao.getFields();
    }

    @GetMapping("/{fieldId}")
    public ResponseEntity<Field> getFieldById(@PathVariable int fieldId) {
        Field field = fieldDao.getFieldById(fieldId);
        if (field == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Field not found.");
        }
        return new ResponseEntity<>(field, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Field createField(@Valid @RequestBody Field newField) {
        try {
            return fieldDao.createField(newField);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Field creation failed.");
        }
    }

    @PutMapping("/{fieldId}")
    public ResponseEntity<Field> updateField(@PathVariable int fieldId, @Valid @RequestBody Field updatedField) {
        updatedField.setFieldId(fieldId);
        if (fieldDao.updateField(updatedField)) {
            return new ResponseEntity<>(updatedField, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field update failed.");
        }
    }

    @DeleteMapping("/{fieldId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteField(@PathVariable int fieldId) {
        if (!fieldDao.deleteField(fieldId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Field not found.");
        }
    }
}
