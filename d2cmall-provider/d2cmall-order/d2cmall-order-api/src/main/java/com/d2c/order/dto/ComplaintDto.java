package com.d2c.order.dto;

import com.d2c.order.model.Complainant;
import com.d2c.order.model.Complaint;
import com.d2c.order.model.ComplaintTrack;

import java.util.ArrayList;
import java.util.List;

public class ComplaintDto extends Complaint {

    private static final long serialVersionUID = 1L;
    /**
     * 投诉人
     */
    private Complainant complainant;
    /**
     * 投诉单
     */
    private List<ComplaintDto> complaints = new ArrayList<ComplaintDto>();
    /**
     * 投诉处理单
     */
    private List<ComplaintTrack> complaintTracks = new ArrayList<ComplaintTrack>();

    public ComplaintDto() {
        super();
    }

    public List<ComplaintDto> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<ComplaintDto> complaints) {
        this.complaints = complaints;
    }

    public List<ComplaintTrack> getComplaintTracks() {
        return complaintTracks;
    }

    public void setComplaintTracks(List<ComplaintTrack> complaintTracks) {
        this.complaintTracks = complaintTracks;
    }

    public Complainant getComplainant() {
        return complainant;
    }

    public void setComplainant(Complainant complainant) {
        this.complainant = complainant;
    }

}
