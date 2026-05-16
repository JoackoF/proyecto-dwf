package udb.edu.sv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import udb.edu.sv.dto.ClaimRequestDTO;
import udb.edu.sv.dto.ClaimResponseDTO;
import udb.edu.sv.entity.Claim;

@Mapper(componentModel = "spring")
public interface ClaimMapper {

    @Mapping(source = "reservation.id", target = "reservationId")
    ClaimResponseDTO toResponseDTO(Claim claim);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reservation", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Claim toEntity(ClaimRequestDTO dto);
}
