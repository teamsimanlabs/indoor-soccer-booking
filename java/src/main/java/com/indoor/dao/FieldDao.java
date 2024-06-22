package com.indoor.dao;

import com.indoor.model.Field;

import java.util.List;

public interface FieldDao {

    List<Field> getFields();

    Field getFieldById(int fieldId);

    Field createField(Field field);

    boolean updateField(Field field);

    boolean deleteField(int fieldId);
}
