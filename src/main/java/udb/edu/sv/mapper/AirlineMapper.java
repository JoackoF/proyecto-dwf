package udb.edu.sv.mapper;

import org.springframework.stereotype.Component;
import udb.edu.sv.dto.AirlineDTO;
import udb.edu.sv.entity.Airline;

@Component
public class AirlineMapper {

    public AirlineDTO toDTO(Airline airline) {

        if (airline == null) return null;

        return AirlineDTO.builder()
                .id(airline.getId())
                .name(airline.getName())
                .code(airline.getCode())
                .build();
    }

    public Airline toEntity(AirlineDTO dto) {

        if (dto == null) return null;

        return Airline.builder()
                .id(dto.getId())
                .name(dto.getName())
                .code(dto.getCode())
                .build();
    }
}