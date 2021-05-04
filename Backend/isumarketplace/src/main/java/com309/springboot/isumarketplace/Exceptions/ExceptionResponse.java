package com309.springboot.isumarketplace.Exceptions;

import java.util.Date;

public class ExceptionResponse {
    private Date timeStamp;
    private String detailMessage;
    private String details;
    private String httpCodeMessage;

    public ExceptionResponse(Date timestamp, String message, String details, String httpCodeMessage){
        super();
        this.timeStamp = timestamp;
        this.detailMessage = message;
        this.details = details;
        this.httpCodeMessage = httpCodeMessage;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return detailMessage;
    }

    public String getDetails() {
        return details;
    }

    public String getHttpCodeMessage() {
        return httpCodeMessage;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setMessage(String message) {
        this.detailMessage = message;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setHttpCodeMessage(String httpCodeMessage) {
        this.httpCodeMessage = httpCodeMessage;
    }
}
