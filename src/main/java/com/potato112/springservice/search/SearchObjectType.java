package com.potato112.springservice.search;

import com.potato112.springservice.crud.model.RentalCar;
import com.potato112.springservice.repository.entities.auth.ViewName;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum SearchObjectType {

    INVESTMENT(RentalCar.class) {
        @Override
        public Map<String, SearchFieldType> getCustomSearchTypes() {

            Map<String, SearchFieldType> customSearchTypes = new HashMap<>();
            customSearchTypes.put("color", SearchFieldType.STRING);
            return customSearchTypes;
        }
        @Override
        public String[] getAdditionalJoinedSearchFields() {

            String[] joinedField = new String[] {
                 "rentalAgreements.rentalClient.country",
                 "rentalAgreements.notes"
            };
            return joinedField;
        }
    };

    // TODO add more next entities
    // ..............
    // ..............

    private static final Map<Class<?>, SearchObjectType> CLASS_TO_TYPE_MAP = new HashMap<>();

    static {
        Stream.of(SearchObjectType.values()).forEach(searchObjectType -> CLASS_TO_TYPE_MAP.put(searchObjectType.objectType(), searchObjectType));
    }


    private final Class<?> obType;

    SearchObjectType(Class<?> clazz) {
        obType = clazz;
    }

    public String[] getAdditionalJoinedSearchFields() {
        return new String[]{};
    }

    public Map<String, SearchFieldType> getCustomSearchTypes() {

        return Collections.emptyMap();
    }

    public String getBrandPropertyName() {
        return StringUtils.EMPTY;
    }

    public Class<?> objectType() {
        return obType;
    }

    public static SearchObjectType getByObjectType(Class<?> clazz) {
        return CLASS_TO_TYPE_MAP.get(clazz);
    }


}
