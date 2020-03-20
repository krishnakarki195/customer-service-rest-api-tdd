package com.galvanize.wrappers;

public class CreateResponseWrapper {

    private String requestNumber;
    private String customerName;
    private String customerAddress;
    private String phoneNumber;
    private String description;
    private String technician;

    public CreateResponseWrapper(){}

    public CreateResponseWrapper(String requestNumber,
                                 String customerName,
                                 String customerAddress,
                                 String phoneNumber,
                                 String description,
                                 String technician) {
        this.requestNumber = requestNumber;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.technician = technician;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
    }

    @Override
    public String toString() {
        return "CreateResponseWrapper{" +
                "requestNumber='" + requestNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", description='" + description + '\'' +
                ", technician='" + technician + '\'' +
                '}';
    }
}
