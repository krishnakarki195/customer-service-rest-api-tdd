package com.galvanize.wrappers;

public class GetAllResponseWrapper {

    private String requestNumber;
    private String requestDateTIme;
    private String customerName;
    private String customerAddress;
    private String phoneNumber;
    private String description;
    private String technician;
    private String appointmentDateTime;
    private String status;

    public GetAllResponseWrapper(){}


    public GetAllResponseWrapper(String requestNumber,
                                 String requestDateTIme,
                                 String customerName,
                                 String customerAddress,
                                 String phoneNumber,
                                 String description,
                                 String technician,
                                 String appointmentDateTime,
                                 String status) {
        this.requestNumber = requestNumber;
        this.requestDateTIme = requestDateTIme;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.technician = technician;
        this.appointmentDateTime = appointmentDateTime;
        this.status = status;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public String getRequestDateTIme() {
        return requestDateTIme;
    }

    public void setRequestDateTIme(String requestDateTIme) {
        this.requestDateTIme = requestDateTIme;
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

    public String getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(String appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "GetAllResponseWrapper{" +
                "requestNumber='" + requestNumber + '\'' +
                ", requestDateTIme='" + requestDateTIme + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", description='" + description + '\'' +
                ", technician='" + technician + '\'' +
                ", appointmentDateTime='" + appointmentDateTime + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
