package com.d2c.behavior.services;

import com.d2c.behavior.mongo.dto.PersonSessionDTO;

import java.util.List;

public interface PersonStatService {

    public List<PersonSessionDTO> findPersonSessionList();

    public List<PersonSessionDTO> findVistorSessionList();

}
