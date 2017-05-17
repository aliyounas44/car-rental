package com.epam.carrental.web.beans;

import com.epam.bench.carrental.commons.beans.CarRentalServerInfoDTO;

public class ServerResponse {
    private String requester;
    private CarRentalServerInfoDTO serverResponse;
    private String handshake;

    public ServerResponse(String requester, CarRentalServerInfoDTO serverResponse, String handshake) {
        this.requester = requester;
        this.serverResponse = serverResponse;
        this.handshake = handshake;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public CarRentalServerInfoDTO getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(CarRentalServerInfoDTO serverResponse) {
        this.serverResponse = serverResponse;
    }

    public String getHandshake() {
        return handshake;
    }

    public void setHandshake(String handshake) {
        this.handshake = handshake;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "requester='" + requester + '\'' +
                ", serverResponse=" + serverResponse +
                ", handshake='" + handshake + '\'' +
                '}';
    }
}
