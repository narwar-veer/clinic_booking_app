package com.clinic.mapper;

import com.clinic.dto.response.DoctorResponse;
import com.clinic.entity.Doctor;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-14T00:33:46+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class DoctorMapperImpl implements DoctorMapper {

    @Override
    public DoctorResponse toResponse(Doctor doctor) {
        if ( doctor == null ) {
            return null;
        }

        DoctorResponse doctorResponse = new DoctorResponse();

        doctorResponse.setQualifications( doctor.getDegrees() );
        doctorResponse.setAwards( doctor.getAwards() );
        doctorResponse.setBio( doctor.getBio() );
        doctorResponse.setDegrees( doctor.getDegrees() );
        doctorResponse.setExperienceYears( doctor.getExperienceYears() );
        doctorResponse.setId( doctor.getId() );
        doctorResponse.setName( doctor.getName() );
        doctorResponse.setPhoto( doctor.getPhoto() );
        doctorResponse.setRegistrationNumber( doctor.getRegistrationNumber() );
        doctorResponse.setSpecialization( doctor.getSpecialization() );

        return doctorResponse;
    }
}
