package udb.edu.sv.mapper;

import org.springframework.stereotype.Component;
import udb.edu.sv.dto.AirlineRequestDTO;
import udb.edu.sv.dto.AirlineResponseDTO;
import udb.edu.sv.entity.Airline;

@Component
public class AirlineMapper {

    public AirlineResponseDTO toResponseDTO(Airline airline) {
        if (airline == null) return null;

        return AirlineResponseDTO.builder()
                .id(airline.getId())
                .name(airline.getName())
                .code(airline.getCode())
                .build();
    }

    public Airline toEntity(AirlineRequestDTO dto) {
        if (dto == null) return null;

        return Airline.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .build();
    }
}
