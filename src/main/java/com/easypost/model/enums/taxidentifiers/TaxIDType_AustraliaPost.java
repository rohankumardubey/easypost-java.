package com.easypost.model.enums.taxidentifiers;

import com.easypost.exception.EasyPostException;
import com.easypost.model.enums.EasyPostEnum;
import com.google.gson.annotations.SerializedName;

public enum TaxIDType_AustraliaPost implements EasyPostEnum {
    @SerializedName ("VAT")
    VAT("VAT"),
    @SerializedName("IOSS")
    IOSS("IOSS");

    private String value;

    TaxIDType_AustraliaPost(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TaxIDType_AustraliaPost getEnum(String value) throws EasyPostException {
        return (TaxIDType_AustraliaPost) EasyPostEnum.getEnumFromValue(values(), value);
    }
}
