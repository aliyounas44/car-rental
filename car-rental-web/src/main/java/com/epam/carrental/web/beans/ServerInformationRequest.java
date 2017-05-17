package com.epam.carrental.web.beans;

public class ServerInformationRequest {
    String handShake;

    public String getHandShake() {
        return handShake;
    }

    public void setHandShake(String handShake) {
        this.handShake = handShake;
    }

    @Override
    public String toString() {
        return "ServerInformationRequest{" +
                "handShake='" + handShake + '\'' +
                '}';
    }
}
