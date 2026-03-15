package com.clinic.mapper;

import com.clinic.dto.response.MedicalRecordResponse;
import com.clinic.entity.Appointment;
import com.clinic.entity.Doctor;
import com.clinic.entity.MedicalRecord;
import com.clinic.entity.Patient;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-14T00:33:46+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class MedicalRecordMapperImpl implements MedicalRecordMapper {

    @Override
    public MedicalRecordResponse toResponse(MedicalRecord medicalRecord) {
        if ( medicalRecord == null ) {
            return null;
        }

        MedicalRecordResponse medicalRecordResponse = new MedicalRecordResponse();

        medicalRecordResponse.setPatientId( medicalRecordPatientId( medicalRecord ) );
        medicalRecordResponse.setDoctorId( medicalRecordDoctorId( medicalRecord ) );
        medicalRecordResponse.setDoctorName( medicalRecordDoctorName( medicalRecord ) );
        medicalRecordResponse.setAppointmentId( medicalRecordAppointmentId( medicalRecord ) );
        medicalRecordResponse.setAttachmentUrl( medicalRecord.getAttachmentUrl() );
        medicalRecordResponse.setCreatedAt( medicalRecord.getCreatedAt() );
        medicalRecordResponse.setDiagnosis( medicalRecord.getDiagnosis() );
        medicalRecordResponse.setId( medicalRecord.getId() );
        medicalRecordResponse.setPrescriptionNotes( medicalRecord.getPrescriptionNotes() );
        medicalRecordResponse.setUpdatedAt( medicalRecord.getUpdatedAt() );

        return medicalRecordResponse;
    }

    private Long medicalRecordPatientId(MedicalRecord medicalRecord) {
        if ( medicalRecord == null ) {
            return null;
        }
        Patient patient = medicalRecord.getPatient();
        if ( patient == null ) {
            return null;
        }
        Long id = patient.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long medicalRecordDoctorId(MedicalRecord medicalRecord) {
        if ( medicalRecord == null ) {
            return null;
        }
        Doctor doctor = medicalRecord.getDoctor();
        if ( doctor == null ) {
            return null;
        }
        Long id = doctor.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String medicalRecordDoctorName(MedicalRecord medicalRecord) {
        if ( medicalRecord == null ) {
            return null;
        }
        Doctor doctor = medicalRecord.getDoctor();
        if ( doctor == null ) {
            return null;
        }
        String name = doctor.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long medicalRecordAppointmentId(MedicalRecord medicalRecord) {
        if ( medicalRecord == null ) {
            return null;
        }
        Appointment appointment = medicalRecord.getAppointment();
        if ( appointment == null ) {
            return null;
        }
        Long id = appointment.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
