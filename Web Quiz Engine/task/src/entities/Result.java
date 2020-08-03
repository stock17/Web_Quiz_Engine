package entities;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Result {

    OK(true, "Congratulations, you're right!"),
    ERROR(false, "Wrong answer! Please, try again.");

    private final boolean success;
    private final String feedback;


    Result(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}
