package com.provid15.core.entity;

import com.provid15.core.ResolutionNote;

public class Resolution {
    public Resolution(String _id, String _resolution, String _format, String _type, ResolutionNote _notes) {
        id = _id;
        resolution = _resolution;
        format = _format;
        type = _type;
        notes = _notes;
    }

    public String id;
    public String resolution;
    public String format;
    public String type;
    public ResolutionNote notes;
}