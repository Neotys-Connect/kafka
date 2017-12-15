package com.neotys.advanced.action.apache.kafka;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;
import com.neotys.extensions.action.engine.Context;
import com.neotys.extensions.action.engine.SampleResult;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public final class KafkaProducerActionEngine implements ActionEngine {

	private Properties props;
	private String destination;
	private String topic;
	private String port="9092";
	private String message;

	private void parseParameters(List<ActionParameter> parameters) throws ClassNotFoundException {

		props = new Properties();

		for (ActionParameter parameter : parameters)
		{
			switch (parameter.getName().toLowerCase())
			{
				case "topic" :
					this.topic = parameter.getValue();
					break;
				case "message":
					this.message = parameter.getValue();
					break;
				case "destination" :
					this.destination = parameter.getValue();
					break;
				case "port" :
					this.port = parameter.getValue();
					break;
				case "key.serializer":
					this.props.put("key.serializer", Class.forName(parameter.getValue()));
					break;
				case "value.serializer":
					this.props.put("value.serializer", Class.forName(parameter.getValue()));
					break;
				case "retries":
					this.props.put("retries", Integer.parseInt(parameter.getValue()));
					break;
				case "buffer.memory":
					this.props.put("buffer.memory", Long.parseLong(parameter.getValue()));
					break;
				case "batch.size":
					this.props.put("batch.size", Integer.parseInt(parameter.getValue()));
					break;
				default:
					this.props.put(parameter.getName(), parameter.getValue());
					break;
			}
		}

		this.props.put("bootstrap.servers", this.destination + ":" + this.port);

		// Use the default values
		if (!this.props.containsKey("key.serializer")){
			this.props.put("key.serializer", org.apache.kafka.common.serialization.StringSerializer.class);
		}
		if (!this.props.containsKey("value.serializer")){
			this.props.put("value.serializer", org.apache.kafka.common.serialization.StringSerializer.class);
		}
	}

	@Override
	public SampleResult execute(Context context, List<ActionParameter> parameters) {
		final SampleResult sampleResult = new SampleResult();
		final StringBuilder requestBuilder = new StringBuilder();
		final StringBuilder responseBuilder = new StringBuilder();

		// Parse the call parameters
		try {
			parseParameters(parameters);
		} catch (ClassNotFoundException e) {
			getErrorResult(context,sampleResult,e.getMessage(),"01",e);
			e.printStackTrace();
		}

		//Instantiate a Kafka producer
		Producer<String,String> producer = new KafkaProducer<String,String>(this.props);

		sampleResult.sampleStart();

		// Send the message to the topic
		Future<RecordMetadata> response = producer.send(new ProducerRecord<>(this.topic,this.message));

		sampleResult.sampleEnd();

		// Build the checkVU request
		appendLineToStringBuilder(requestBuilder, "KafkaProducer request.");

		try {
			// Build the check VU response
			appendLineToStringBuilder(responseBuilder, "Checksum: "+response.get().checksum());
			appendLineToStringBuilder(responseBuilder, "Offset : "+response.get().offset());
			appendLineToStringBuilder(responseBuilder, "Serialized Key Size : "+response.get().serializedKeySize());
			appendLineToStringBuilder(responseBuilder, "Serialized Value Size : "+response.get().serializedValueSize());
			appendLineToStringBuilder(responseBuilder, "Timestamp : "+response.get().timestamp());
			appendLineToStringBuilder(responseBuilder, "Topic : "+response.get().topic());
			appendLineToStringBuilder(responseBuilder, "Partition : "+response.get().partition());
		} catch (InterruptedException e) {
			getErrorResult(context,sampleResult,e.getMessage(),"02",e);
			e.printStackTrace();
		} catch (ExecutionException e) {
			getErrorResult(context,sampleResult,e.getMessage(),"03",e);
			e.printStackTrace();
		}


		producer.close();

		// Log the request and response to the check VU
		sampleResult.setStatusCode("KAFKA-SEND-OK");
		sampleResult.setRequestContent(requestBuilder.toString());
		sampleResult.setResponseContent(responseBuilder.toString());
		return sampleResult;
	}

	private void appendLineToStringBuilder(final StringBuilder sb, final String line){
		sb.append(line).append("\n");
	}

	/**
	 * This method allows to easily create an error result and log exception.
	 */
	private static SampleResult getErrorResult(final Context context, final SampleResult result, final String errorMessage, final String statusCode, final Exception exception) {
		result.setError(true);
		final StringBuilder errorCodeBuilder = new StringBuilder();
		errorCodeBuilder.append("NL-KafkaProducer-").append(statusCode);
		result.setStatusCode(errorCodeBuilder.toString());
		result.setResponseContent(errorMessage);
		if(exception != null){
			context.getLogger().error(errorMessage, exception);
		} else{
			context.getLogger().error(errorMessage);
		}
		return result;
	}

	@Override
	public void stopExecute() {
		// TODO add code executed when the test have to stop.
	}

}
