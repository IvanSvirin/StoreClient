package com.example.isvirin.storeclient.data.entity.daoconverter;


import org.greenrobot.greendao.converter.PropertyConverter;

public class BrandTypeConverter implements PropertyConverter<BrandType, String>{
    @Override
    public BrandType convertToEntityProperty(String databaseValue) {
        return BrandType.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(BrandType entityProperty) {
        return entityProperty.name();
    }
}
