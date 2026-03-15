package com.clinic.mapper;

import com.clinic.dto.response.ClinicResponse;
import com.clinic.entity.Clinic;
import com.clinic.entity.Doctor;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-14T00:33:46+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class ClinicMapperImpl implements ClinicMapper {

    @Override
    public ClinicResponse toResponse(Clinic clinic) {
        if ( clinic == null ) {
            return null;
        }

        ClinicResponse clinicResponse = new ClinicResponse();

        clinicResponse.setDoctorId( clinicDoctorId( clinic ) );
        clinicResponse.setDoctorName( clinicDoctorName( clinic ) );
        clinicResponse.setAddress( clinic.getAddress() );
        clinicResponse.setClinicName( clinic.getClinicName() );
        clinicResponse.setEmail( clinic.getEmail() );
        clinicResponse.setId( clinic.getId() );
        clinicResponse.setMapLocation( clinic.getMapLocation() );
        clinicResponse.setPhone( clinic.getPhone() );
        clinicResponse.setWhatsapp( clinic.getWhatsapp() );

        return clinicResponse;
    }

    private Long clinicDoctorId(Clinic clinic) {
        if ( clinic == null ) {
            return null;
        }
        Doctor doctor = clinic.getDoctor();
        if ( doctor == null ) {
            return null;
        }
        Long id = doctor.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String clinicDoctorName(Clinic clinic) {
        if ( clinic == null ) {
            return null;
        }
        Doctor doctor = clinic.getDoctor();
        if ( doctor == null ) {
            return null;
        }
        String name = doctor.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
