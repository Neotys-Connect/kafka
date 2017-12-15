# Kafka

## kafka-producer
This advanced action allows you to publish messages to Kafka topics from [NeoLoad](https://www.neotys.com/neoload/overview)

 | Property | Value |
 | -----| -------------- |
 | Maturity | Experimental |
 | Author   | Neotys Professional Services |
 | License  | [BSD Simplified](https://www.neotys.com/documents/legal/bsd-neotys.txt) |
 | NeoLoad  | 6.1 (Enterprise or Professional Edition w/ Integration & Advanced Usage and NeoLoad Web option required)|
 | Requirements | |
 | Bundled in NeoLoad | No
 | Download Binaries | See the [latest release](https://github.com/ttheol/kafka/releases/latest)
 
 ## Installation
 
 1. Download the [latest release](https://github.com/ttheol/kafka/releases/latest)
 1. Read the NeoLoad documentation to see [How to install a custom Advanced Action](https://www.neotys.com/documents/doc/neoload/latest/en/html/#25928.htm)
 
 ## Usage
 
 1. Read the NeoLoad documentation to see [How to use an Advanced action](https://www.neotys.com/documents/doc/neoload/latest/en/html/#25929.htm)
 
 ## Parameters
 | Name             | Description |
 | -----            | ----- |
 | topic | The topic to send the message to. |
 | message | The message to send |
 | destination | The name or IP of the destination broker |
 | port (optional) | The port of the destination server. Default value is "9092". |
 | key.serializer (optional) | Serializer class for key that implements the Serializer interface. Default value is "org.apache.kafka.common.serialization.StringSerializer". |
 | value.serializer (optional) | Serializer class for value that implements the Serializer interface. Default value is "org.apache.kafka.common.serialization.StringSerializer". |
 | acks (optional) | The number of acknowledgments the producer requires the leader to have received before considering a request complete. This controls the durability of records that are sent. The following settings are allowed: |

## Status Codes
* NL-KafkaProducer-01
* NL-KafkaProducer-02
* NL-KafkaProducer-03
