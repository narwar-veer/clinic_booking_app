package com.clinic.mapper;

import com.clinic.dto.response.SlotResponse;
import com.clinic.entity.Clinic;
import com.clinic.entity.Doctor;
import com.clinic.entity.Slot;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-14T00:33:46+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class SlotMapperImpl implements SlotMapper {

    @Override
    public SlotResponse toResponse(Slot slot) {
        if ( slot == null ) {
            return null;
        }

        SlotResponse slotResponse = new SlotResponse();

        slotResponse.setClinicId( slotClinicId( slot ) );
        slotResponse.setClinicName( slotClinicClinicName( slot ) );
        slotResponse.setDoctorId( slotClinicDoctorId( slot ) );
        slotResponse.setDoctorName( slotClinicDoctorName( slot ) );
        slotResponse.setBlocked( slot.getIsBlocked() );
        slotResponse.setBookedCount( slot.getBookedCount() );
        slotResponse.setEndTime( slot.getEndTime() );
        slotResponse.setId( slot.getId() );
        slotResponse.setMaxPatients( slot.getMaxPatients() );
        slotResponse.setSlotDate( slot.getSlotDate() );
        slotResponse.setStartTime( slot.getStartTime() );

        slotResponse.setAvailableCapacity( slot.getMaxPatients() - slot.getBookedCount() );

        return slotResponse;
    }

    private Long slotClinicId(Slot slot) {
        if ( slot == null ) {
            return null;
        }
        Clinic clinic = slot.getClinic();
        if ( clinic == null ) {
            return null;
        }
        Long id = clinic.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String slotClinicClinicName(Slot slot) {
        if ( slot == null ) {
            return null;
        }
        Clinic clinic = slot.getClinic();
        if ( clinic == null ) {
            return null;
        }
        String clinicName = clinic.getClinicName();
        if ( clinicName == null ) {
            return null;
        }
        return clinicName;
    }

    private Long slotClinicDoctorId(Slot slot) {
        if ( slot == null ) {
            return null;
        }
        Clinic clinic = slot.getClinic();
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

    private String slotClinicDoctorName(Slot slot) {
        if ( slot == null ) {
            return null;
        }
        Clinic clinic = slot.getClinic();
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
