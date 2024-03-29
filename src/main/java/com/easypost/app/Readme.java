// java -cp "target/easypost-java-2.0.4.jar:target/gson-2.2.4.jar" Readme
package com.easypost.app;

import com.easypost.EasyPost;
import com.easypost.exception.EasyPostException;
import com.easypost.model.Address;
import com.easypost.model.CustomsInfo;
import com.easypost.model.CustomsItem;
import com.easypost.model.Parcel;
import com.easypost.model.Shipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Readme {

    public static void main(final String[] args) {
        EasyPost.apiKey = "cueqNZUb3ldeWTNX7MU3Mel8UXtaAMUi";

        Map<String, Object> fromAddressMap = new HashMap<String, Object>();
        fromAddressMap.put("name", "Simpler Postage Inc");
        fromAddressMap.put("street1", "388 Townsend St");
        fromAddressMap.put("street2", "Apt 20");
        fromAddressMap.put("city", "San Francisco");
        fromAddressMap.put("state", "CA");
        fromAddressMap.put("zip", "94107");
        fromAddressMap.put("phone", "415-456-7890");

        Map<String, Object> toAddressMap = new HashMap<String, Object>();
        toAddressMap.put("name", "Sawyer Bateman");
        toAddressMap.put("street1", "1A Larkspur Cres");
        toAddressMap.put("street2", "");
        toAddressMap.put("city", "St. Albert");
        toAddressMap.put("state", "AB");
        toAddressMap.put("zip", "T8N2M4");
        toAddressMap.put("phone", "780-483-2746");
        toAddressMap.put("country", "CA");

        Map<String, Object> parcelMap = new HashMap<String, Object>();
        parcelMap.put("weight", 22.9);
        parcelMap.put("height", 12.1);
        parcelMap.put("width", 8);
        parcelMap.put("length", 19.8);

        Map<String, Object> customsItem1Map = new HashMap<String, Object>();
        customsItem1Map.put("description", "EasyPost T-shirts");
        customsItem1Map.put("quantity", 2);
        customsItem1Map.put("value", 23.56);
        customsItem1Map.put("weight", 18.8);
        customsItem1Map.put("origin_country", "us");
        customsItem1Map.put("hs_tariff_number", "123456");

        Map<String, Object> customsItem2Map = new HashMap<String, Object>();
        customsItem2Map.put("description", "EasyPost Stickers");
        customsItem2Map.put("quantity", 11);
        customsItem2Map.put("value", 8.98);
        customsItem2Map.put("weight", 3.2);
        customsItem2Map.put("origin_country", "us");
        customsItem2Map.put("hs_tariff_number", "654321");

        try {
            Address fromAddress = Address.create(fromAddressMap);
            Address toAddress = Address.create(toAddressMap);
            Parcel parcel = Parcel.create(parcelMap);


            try {
                Address verified = toAddress.verify();
            } catch (EasyPostException e) {
                System.out.println(e.getMessage());
            }

            // customs
            Map<String, Object> customsInfoMap = new HashMap<String, Object>();
            customsInfoMap.put("integrated_form_type", "form_2976");
            customsInfoMap.put("customs_certify", true);
            customsInfoMap.put("customs_signer", "Dr. Pepper");
            customsInfoMap.put("contents_type", "gift");
            customsInfoMap.put("eel_pfc", "NOEEI 30.37(a)");
            customsInfoMap.put("non_delivery_option", "return");
            customsInfoMap.put("restriction_type", "none");
            CustomsItem customsItem1 = CustomsItem.create(customsItem1Map);
            CustomsItem customsItem2 = CustomsItem.create(customsItem2Map);
            List<CustomsItem> customsItemsList = new ArrayList<CustomsItem>();
            customsItemsList.add(customsItem1);
            customsItemsList.add(customsItem2);
            customsInfoMap.put("customs_items", customsItemsList);
            CustomsInfo customsInfo = CustomsInfo.create(customsInfoMap);

            // create shipment
            Map<String, Object> shipmentMap = new HashMap<String, Object>();
            shipmentMap.put("to_address", toAddress);
            shipmentMap.put("from_address", fromAddress);
            shipmentMap.put("parcel", parcel);
            shipmentMap.put("customs_info", customsInfo);

            Shipment shipment = Shipment.create(shipmentMap);

            // buy postage
            List<String> buyCarriers = new ArrayList<String>();
            buyCarriers.add("USPS");
            List<String> buyServices = new ArrayList<String>();
            buyServices.add("Priority");
            // List<String> buyServiceCodes = new ArrayList<String>();
            // buyServiceCodes.add("fedex.fedex_ground");

            Map<String, Object> buyMap = new HashMap<String, Object>();
            buyMap.put("rate", shipment.lowestRate(buyCarriers, buyServices));
            buyMap.put("insurance", 249.99);

            // shipment = shipment.buy(shipment.lowestRate(buyCarriers, buyServices));
            shipment = shipment.buy(buyMap);

            System.out.println(shipment.prettyPrint());

        } catch (EasyPostException e) {
            e.printStackTrace();
        }
    }
}
