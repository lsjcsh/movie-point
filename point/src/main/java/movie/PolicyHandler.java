package movie;

import movie.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    PointRepository pointRepository;
    
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPrinted_(@Payload Printed printed){

        if(printed.isMe()){
            System.out.println("======================================");
            System.out.println("##### listener  : " + printed.toJson());
            System.out.println("======================================");
            
            Point point = new Point();    
            
            point.setBookingId(printed.getId());
            //point.setStatus("Written Point");
          pointRepository.save(point);
        }
    }

}
