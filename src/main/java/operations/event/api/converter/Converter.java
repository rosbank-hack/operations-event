package operations.event.api.converter;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@Slf4j
public abstract class Converter {

    public static String convertIncomeMessage(ConsumerRecord<String, String> incomeMessage) {
        return incomeMessage.toString();
    }

}
