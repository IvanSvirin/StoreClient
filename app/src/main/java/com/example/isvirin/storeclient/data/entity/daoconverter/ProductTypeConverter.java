package com.example.isvirin.storeclient.data.entity.daoconverter;


import org.greenrobot.greendao.converter.PropertyConverter;

public class ProductTypeConverter implements PropertyConverter<ProductType, String>{
    @Override
    public ProductType convertToEntityProperty(String databaseValue) {
        return ProductType.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(ProductType entityProperty) {
        return entityProperty.name();
    }
}
