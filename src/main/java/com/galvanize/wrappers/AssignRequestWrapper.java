package com.galvanize.wrappers;

public class AssignRequestWrapper {

    private String technician;
    private String appointmentDate;
    private String appointmentTime;

    public  AssignRequestWrapper(){}

    public AssignRequestWrapper(String technician, String appointmentDate, String appointmentTime) {
        this.technician = technician;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    @Override
    public String toString() {
        return "AssignRequestWrapper{" +
                "technician='" + technician + '\'' +
                ", appointmentDate='" + appointmentDate + '\'' +
                ", appointmentTime='" + appointmentTime + '\'' +
                '}';
    }
}
