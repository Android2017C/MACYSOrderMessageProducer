package com.zensar.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.dto.FulfillmentOrder;
import com.zensar.dto.ProducerJSONDto;
import com.zensar.rabbitmqconfig.RabbitMQConfigFile;
import com.zensar.service.ProducerJSONServiceImpl;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("Macys/OrderMessageProducer")
public class OrderMessageProducerController {

	@Autowired
	ProducerJSONServiceImpl producerJSONServiceImpl;
	@Autowired
	private RabbitTemplate rabbitJSONTemplate;
	@Autowired
	private RabbitTemplate rabbitXMLTemplate;

	@PostMapping(value = "/ProduceMeassages", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create Meassage")
	public ProducerJSONDto createNewProduceMeassage(@RequestBody ProducerJSONDto producerJSONDto) {

		rabbitJSONTemplate.convertAndSend(RabbitMQConfigFile.EXCHANGE, RabbitMQConfigFile.ROUTING_KEY, producerJSONDto);

		return producerJSONServiceImpl.createNewProducerJSONDto(producerJSONDto);
	}
	
	
	
	
	@PostMapping(value = "/gcp/json", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ProducerJSONDto CreateJsonMessageWithGCP(@RequestBody ProducerJSONDto producerJSONDto) {
		return producerJSONServiceImpl.createJsonMessageWithGCP(producerJSONDto);
	}
	
	
	
	
	
	
	

	@PostMapping(value = "/ProduceMeassagesXML", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@ApiOperation(value = "Create NewXMLmesage")
	public FulfillmentOrder createNewProduceMeassageXML(@RequestBody FulfillmentOrder fulfillmentOrder) {

		rabbitXMLTemplate.convertAndSend(RabbitMQConfigFile.EXCHANGE_XML, RabbitMQConfigFile.ROUTING_KEY_XML,
				fulfillmentOrder);

		return producerJSONServiceImpl.createNewProducerXMLDto(fulfillmentOrder);

	}
	
	@PostMapping(value = "/gcp/xml", consumes = { MediaType.APPLICATION_XML_VALUE })
	public FulfillmentOrder createXmlMessageWithGCP(@RequestBody FulfillmentOrder fulfillmentOrder) {
		return producerJSONServiceImpl.createXMLMessageWithGCP(fulfillmentOrder);
	}

}
