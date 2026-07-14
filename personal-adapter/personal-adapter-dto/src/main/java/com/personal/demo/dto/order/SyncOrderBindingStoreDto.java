package com.personal.demo.dto.order;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Li QianQian
 * describe:
 */
@Data
public class SyncOrderBindingStoreDto implements Serializable {


    /**
     * storeCode
     */
    private String storeCode;

    /**
     * storeName
     */
    private String storeName;

    /**
     * 适配样例结构id
     * **/
    private String ognId;
}
