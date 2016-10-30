package com.example.isvirin.storeclient.data.entity.daoconverter;


import org.greenrobot.greendao.converter.PropertyConverter;

public class CategoryTypeConverter implements PropertyConverter<CategoryType, String>{
    @Override
    public CategoryType convertToEntityProperty(String databaseValue) {
        return CategoryType.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(CategoryType entityProperty) {
        return entityProperty.name();
    }
}
