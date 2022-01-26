package ru.yastrebov.onemicr.kafka;

import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.yastrebov.onemicr.dto.EmployeeDto;

@Component
public class KafkaProducer {

    public String sendMessage(String message) {
        KafkaTemplate kafkaTemplate = null;

        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send("employeeDB", message);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + message
                        + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(@NotNull Throwable exception) {
                System.out.println("Unable to sent message=["
                        + message + "] due to: " + exception.getMessage());
            }
        });

        return message;
    }

    public String createMessageForSending(EmployeeDto employeeDto) {

        return String.format(" Employee DB with those values: id = %s, firstName = %s, lastName = %s, age = %d, " +
                        "experience = %f, position = %s, project = %s, hireDate = %s, gender = %s",
                employeeDto.getId(), employeeDto.getFirstName(), employeeDto.getLastName(), employeeDto.getAge(), employeeDto.getExperience(),
                employeeDto.getPosition(), employeeDto.getProject(), employeeDto.getHireDate(), employeeDto.getGender());
    }
}
