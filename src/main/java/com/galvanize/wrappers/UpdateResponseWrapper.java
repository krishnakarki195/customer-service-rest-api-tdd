package com.galvanize.wrappers;

import com.galvanize.entities.Note;

import java.util.List;

public class UpdateResponseWrapper {

    private String technician;
    private String appointmentDateTime;
    private String status;
    private List<Note> notes;

    public UpdateResponseWrapper() {
    }

    public UpdateResponseWrapper(String technician,
                                 String appointmentDateTime,
                                 String status, List<Note> notes) {
        this.technician = technician;
        this.appointmentDateTime = appointmentDateTime;
        this.status = status;
        this.notes = notes;
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

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "UpdateResponseWrapper{" +
                "technician='" + technician + '\'' +
                ", appointmentDateTime='" + appointmentDateTime + '\'' +
                ", status='" + status + '\'' +
                ", notes=" + notes +
                '}';
    }
}
