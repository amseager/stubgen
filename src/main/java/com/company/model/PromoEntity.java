package com.company.model;

import lombok.Data;

/**
 * Экземпляр акции
 */
@Data
public class PromoEntity {

	private String id;
	private String promoType;
	private boolean allowWebCheckout;
	private String promoName;
	private Integer promoValue;
	private String promoId;

	private KeyValue param;

}
