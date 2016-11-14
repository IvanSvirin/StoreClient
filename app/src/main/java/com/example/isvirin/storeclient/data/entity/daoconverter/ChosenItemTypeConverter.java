package com.example.isvirin.storeclient.data.entity.daoconverter;


import org.greenrobot.greendao.converter.PropertyConverter;

public class ChosenItemTypeConverter implements PropertyConverter<ChosenItemType, String>{
    @Override
    public ChosenItemType convertToEntityProperty(String databaseValue) {
        return ChosenItemType.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(ChosenItemType entityProperty) {
        return entityProperty.name();
    }
}
