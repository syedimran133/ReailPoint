package com.reail.point.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Locations {
    @SerializedName("connector1Type")
    @Expose
    private String connector1Type;
    @SerializedName("paymentRequiredDetails")
    @Expose
    private String paymentRequiredDetails;
    @SerializedName("deviceOwnerWebsite")
    @Expose
    private String deviceOwnerWebsite;
    @SerializedName("Hydrogen")
    @Expose
    private String hydrogen;
    @SerializedName("connector4Status")
    @Expose
    private String connector4Status;
    @SerializedName("subscriptionRequired")
    @Expose
    private String subscriptionRequired;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("locationType")
    @Expose
    private String locationType;
    @SerializedName("Output KW")
    @Expose
    private String outputKW;
    @SerializedName("paymentRequired")
    @Expose
    private String paymentRequired;
    @SerializedName("locationLongDescription")
    @Expose
    private String locationLongDescription;
    @SerializedName("connector2 OutputKW")
    @Expose
    private String connector2OutputKW;
    @SerializedName("connector3Type")
    @Expose
    private String connector3Type;
    @SerializedName("Points at service stations")
    @Expose
    private String pointsAtServiceStations;
    @SerializedName("Reliability rating")
    @Expose
    private String reliabilityRating;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("deviceControllerTelephoneNo")
    @Expose
    private String deviceControllerTelephoneNo;
    @SerializedName("Home network")
    @Expose
    private String homeNetwork;
    @SerializedName("g")
    @Expose
    private String g;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("Live data")
    @Expose
    private String liveData;
    @SerializedName("connector3OutputKW")
    @Expose
    private String connector3OutputKW;
    @SerializedName("connector4Type")
    @Expose
    private String connector4Type;
    @SerializedName("connector4OutputKW")
    @Expose
    private String connector4OutputKW;
    @SerializedName("l")
    @Expose
    private List<Double> l = null;
    @SerializedName("connector3Status")
    @Expose
    private String connector3Status;
    @SerializedName("ReliaPoint network ID")
    @Expose
    private String reliaPointNetworkID;
    @SerializedName("connector2Type")
    @Expose
    private String connector2Type;
    @SerializedName("deviceNetworks")
    @Expose
    private String deviceNetworks;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("points in car parks")
    @Expose
    private String pointsInCarParks;
    @SerializedName("chargeDeviceStatus")
    @Expose
    private String chargeDeviceStatus;
    @SerializedName("connector1Status")
    @Expose
    private String connector1Status;

    public String getConnector1Type() {
        return connector1Type;
    }

    public void setConnector1Type(String connector1Type) {
        this.connector1Type = connector1Type;
    }

    public String getPaymentRequiredDetails() {
        return paymentRequiredDetails;
    }

    public void setPaymentRequiredDetails(String paymentRequiredDetails) {
        this.paymentRequiredDetails = paymentRequiredDetails;
    }

    public String getDeviceOwnerWebsite() {
        return deviceOwnerWebsite;
    }

    public void setDeviceOwnerWebsite(String deviceOwnerWebsite) {
        this.deviceOwnerWebsite = deviceOwnerWebsite;
    }

    public String getHydrogen() {
        return hydrogen;
    }

    public void setHydrogen(String hydrogen) {
        this.hydrogen = hydrogen;
    }

    public String getConnector4Status() {
        return connector4Status;
    }

    public void setConnector4Status(String connector4Status) {
        this.connector4Status = connector4Status;
    }

    public String getSubscriptionRequired() {
        return subscriptionRequired;
    }

    public void setSubscriptionRequired(String subscriptionRequired) {
        this.subscriptionRequired = subscriptionRequired;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getOutputKW() {
        return outputKW;
    }

    public void setOutputKW(String outputKW) {
        this.outputKW = outputKW;
    }

    public String getPaymentRequired() {
        return paymentRequired;
    }

    public void setPaymentRequired(String paymentRequired) {
        this.paymentRequired = paymentRequired;
    }

    public String getLocationLongDescription() {
        return locationLongDescription;
    }

    public void setLocationLongDescription(String locationLongDescription) {
        this.locationLongDescription = locationLongDescription;
    }

    public String getConnector2OutputKW() {
        return connector2OutputKW;
    }

    public void setConnector2OutputKW(String connector2OutputKW) {
        this.connector2OutputKW = connector2OutputKW;
    }

    public String getConnector3Type() {
        return connector3Type;
    }

    public void setConnector3Type(String connector3Type) {
        this.connector3Type = connector3Type;
    }

    public String getPointsAtServiceStations() {
        return pointsAtServiceStations;
    }

    public void setPointsAtServiceStations(String pointsAtServiceStations) {
        this.pointsAtServiceStations = pointsAtServiceStations;
    }

    public String getReliabilityRating() {
        return reliabilityRating;
    }

    public void setReliabilityRating(String reliabilityRating) {
        this.reliabilityRating = reliabilityRating;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDeviceControllerTelephoneNo() {
        return deviceControllerTelephoneNo;
    }

    public void setDeviceControllerTelephoneNo(String deviceControllerTelephoneNo) {
        this.deviceControllerTelephoneNo = deviceControllerTelephoneNo;
    }

    public String getHomeNetwork() {
        return homeNetwork;
    }

    public void setHomeNetwork(String homeNetwork) {
        this.homeNetwork = homeNetwork;
    }

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getLiveData() {
        return liveData;
    }

    public void setLiveData(String liveData) {
        this.liveData = liveData;
    }

    public String getConnector3OutputKW() {
        return connector3OutputKW;
    }

    public void setConnector3OutputKW(String connector3OutputKW) {
        this.connector3OutputKW = connector3OutputKW;
    }

    public String getConnector4Type() {
        return connector4Type;
    }

    public void setConnector4Type(String connector4Type) {
        this.connector4Type = connector4Type;
    }

    public String getConnector4OutputKW() {
        return connector4OutputKW;
    }

    public void setConnector4OutputKW(String connector4OutputKW) {
        this.connector4OutputKW = connector4OutputKW;
    }

    public List<Double> getL() {
        return l;
    }

    public void setL(List<Double> l) {
        this.l = l;
    }

    public String getConnector3Status() {
        return connector3Status;
    }

    public void setConnector3Status(String connector3Status) {
        this.connector3Status = connector3Status;
    }

    public String getReliaPointNetworkID() {
        return reliaPointNetworkID;
    }

    public void setReliaPointNetworkID(String reliaPointNetworkID) {
        this.reliaPointNetworkID = reliaPointNetworkID;
    }

    public String getConnector2Type() {
        return connector2Type;
    }

    public void setConnector2Type(String connector2Type) {
        this.connector2Type = connector2Type;
    }

    public String getDeviceNetworks() {
        return deviceNetworks;
    }

    public void setDeviceNetworks(String deviceNetworks) {
        this.deviceNetworks = deviceNetworks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPointsInCarParks() {
        return pointsInCarParks;
    }

    public void setPointsInCarParks(String pointsInCarParks) {
        this.pointsInCarParks = pointsInCarParks;
    }

    public String getChargeDeviceStatus() {
        return chargeDeviceStatus;
    }

    public void setChargeDeviceStatus(String chargeDeviceStatus) {
        this.chargeDeviceStatus = chargeDeviceStatus;
    }

    public String getConnector1Status() {
        return connector1Status;
    }

    public void setConnector1Status(String connector1Status) {
        this.connector1Status = connector1Status;
    }
}
