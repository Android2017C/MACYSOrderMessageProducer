package com.zensar.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.zensar.dto.FulfillmentOrder;
import com.zensar.dto.ProducerJSONDto;
import com.zensar.entity.FulfillmentOrderEntity;
import com.zensar.entity.ProducerJSONEntity;
import com.zensar.rabbitmqconfig.RabbitMQConfigFile;
import com.zensar.repo.ProducerJSONRepo;
import com.zensar.repo.ProducerXMLRepo;

@Service
public class ProducerJSONServiceImpl implements ProducerJSONService {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ProducerJSONRepo producerJSONRepo;
	@Autowired
	private ProducerXMLRepo producerXMLRepo;

	@Autowired
	private PubSubTemplate pubSubTemplate;
	
	@Override
	public ProducerJSONDto createNewProducerJSONDto(ProducerJSONDto producerJSONDto) {

		ProducerJSONEntity producerJSONEntity = getProducerEntityFromDTO(producerJSONDto);
		producerJSONEntity = producerJSONRepo.save(producerJSONEntity);
		return getproducerJSONDTOFromEntity(producerJSONEntity);

	}

	@Override
	public FulfillmentOrder createNewProducerXMLDto(FulfillmentOrder fulfillmentOrder) {
		FulfillmentOrderEntity producerJSONEntity = getFulfillmentOrderEntityFromDTO(fulfillmentOrder);
		producerJSONEntity = producerXMLRepo.save(producerJSONEntity);
		return getFulfillmentOrderDTOFromEntity(producerJSONEntity);
	}

	@Override
	public List<ProducerJSONDto> getAllProducerJSONDto() {
		// TODO Auto-generated method stub
		return null;
	}

	private ProducerJSONEntity getProducerEntityFromDTO(ProducerJSONDto producerJSONDto) {
		ProducerJSONEntity producerjsonEntity = this.modelMapper.map(producerJSONDto, ProducerJSONEntity.class);
		return producerjsonEntity;
	}

	private ProducerJSONDto getproducerJSONDTOFromEntity(ProducerJSONEntity producerJSONEntity) {
		ProducerJSONDto producerJSONDto = this.modelMapper.map(producerJSONEntity, ProducerJSONDto.class);
		return producerJSONDto;
	}

	private List<ProducerJSONDto> getproducerJSONDTOListFromEntityList(List<ProducerJSONEntity> producerEntityList) {
		List<ProducerJSONDto> producerDTOList = new ArrayList<ProducerJSONDto>();
		for (ProducerJSONEntity producerEntity : producerEntityList) {
			producerDTOList.add(getproducerJSONDTOFromEntity(producerEntity));
		}
		return producerDTOList;
	}

	private FulfillmentOrderEntity getFulfillmentOrderEntityFromDTO(FulfillmentOrder fulfillmentOrder) {
		FulfillmentOrderEntity fulfillmentOrderEntity = this.modelMapper.map(fulfillmentOrder,
				FulfillmentOrderEntity.class);
		return fulfillmentOrderEntity;
	}

	private FulfillmentOrder getFulfillmentOrderDTOFromEntity(FulfillmentOrderEntity fulfillmentOrderEntity) {
		FulfillmentOrder fulfillmentOrder = this.modelMapper.map(fulfillmentOrderEntity, FulfillmentOrder.class);
		return fulfillmentOrder;
	}

	private List<FulfillmentOrder> getFulfillmentOrderDTOListFromEntityList(
			List<FulfillmentOrderEntity> fulfillmentOrderEntitiesList) {
		List<FulfillmentOrder> fulfillmentOrdersList = new ArrayList<FulfillmentOrder>();
		for (FulfillmentOrderEntity producerEntity : fulfillmentOrderEntitiesList) {
			fulfillmentOrdersList.add(getFulfillmentOrderDTOFromEntity(producerEntity));
		}
		return fulfillmentOrdersList;
	}

	@Override
	public ProducerJSONDto createJsonMessageWithGCP(ProducerJSONDto producerJSONDto) {
		ObjectMapper mapperObject = new ObjectMapper();
		String jsonPayload = null;
		try {
			jsonPayload = mapperObject.writeValueAsString(producerJSONDto);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		pubSubTemplate.publish(RabbitMQConfigFile.GCP_JSON_TOPIC, jsonPayload);
		return null;
	}

	@Override
	public FulfillmentOrder createXMLMessageWithGCP(FulfillmentOrder fulfillmentOrder) {
		XmlMapper mapperObject = new XmlMapper();
		String xmlPayload = null;
		try {
			xmlPayload = mapperObject.writeValueAsString(fulfillmentOrder);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		pubSubTemplate.publish(RabbitMQConfigFile.GCP_XML_TOPIC, xmlPayload);
		return null;
	}

}
