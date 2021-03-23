package name.eric.scheduler.api.timer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel
@Getter
@Setter
public class TaskCreateDto {

    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", message = "time must have format hh:00 between 00:00 and 23:59, e.g. 15:36")
    @ApiModelProperty("time must have format HH:mm in 24h format")
    String time;

    @NotEmpty(message = "a message must be provided")
    @ApiModelProperty("the message that should be logged")
    String message;
}
