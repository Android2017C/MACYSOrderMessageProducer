package com.zensar.service;

import java.util.List;

import com.zensar.dto.FulfillmentOrder;
import com.zensar.dto.ProducerJSONDto;

public interface ProducerJSONService {

	public ProducerJSONDto createNewProducerJSONDto(ProducerJSONDto producerJSONDto);

	public FulfillmentOrder createNewProducerXMLDto(FulfillmentOrder fulfillmentOrder);
	
	public ProducerJSONDto createJsonMessageWithGCP(ProducerJSONDto producerJSONDto);
	
	public FulfillmentOrder createXMLMessageWithGCP(FulfillmentOrder fulfillmentOrder);

	public List<ProducerJSONDto> getAllProducerJSONDto();

}
