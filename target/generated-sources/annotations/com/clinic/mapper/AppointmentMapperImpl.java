package com.clinic.mapper;

import com.clinic.dto.response.AppointmentResponse;
import com.clinic.entity.Appointment;
import com.clinic.entity.Clinic;
import com.clinic.entity.Doctor;
import com.clinic.entity.Gender;
import com.clinic.entity.Patient;
import com.clinic.entity.Slot;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-14T00:33:46+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class AppointmentMapperImpl implements AppointmentMapper {

    @Override
    public AppointmentResponse toResponse(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }

        AppointmentResponse appointmentResponse = new AppointmentResponse();

        appointmentResponse.setPatientId( appointmentPatientId( appointment ) );
        appointmentResponse.setPatientName( appointmentPatientName( appointment ) );
        appointmentResponse.setPatientPhone( appointmentPatientPhone( appointment ) );
        appointmentResponse.setPatientAge( appointmentPatientAge( appointment ) );
        appointmentResponse.setPatientGender( appointmentPatientGender( appointment ) );
        appointmentResponse.setSlotId( appointmentSlotId( appointment ) );
        appointmentResponse.setClinicId( appointmentSlotClinicId( appointment ) );
        appointmentResponse.setClinicName( appointmentSlotClinicClinicName( appointment ) );
        appointmentResponse.setDoctorName( appointmentSlotClinicDoctorName( appointment ) );
        appointmentResponse.setSlotDate( appointmentSlotSlotDate( appointment ) );
        appointmentResponse.setSlotStartTime( appointmentSlotStartTime( appointment ) );
        appointmentResponse.setSlotEndTime( appointmentSlotEndTime( appointment ) );
        appointmentResponse.setCreatedAt( appointment.getCreatedAt() );
        appointmentResponse.setId( appointment.getId() );
        appointmentResponse.setRating( appointment.getRating() );
        appointmentResponse.setStatus( appointment.getStatus() );
        appointmentResponse.setSymptoms( appointment.getSymptoms() );
        appointmentResponse.setTestimonial( appointment.getTestimonial() );
        appointmentResponse.setVisitedAt( appointment.getVisitedAt() );

        return appointmentResponse;
    }

    private Long appointmentPatientId(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        Patient patient = appointment.getPatient();
        if ( patient == null ) {
            return null;
        }
        Long id = patient.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String appointmentPatientName(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        Patient patient = appointment.getPatient();
        if ( patient == null ) {
            return null;
        }
        String name = patient.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String appointmentPatientPhone(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        Patient patient = appointment.getPatient();
        if ( patient == null ) {
            return null;
        }
        String phone = patient.getPhone();
        if ( phone == null ) {
            return null;
        }
        return phone;
    }

    private Integer appointmentPatientAge(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        Patient patient = appointment.getPatient();
        if ( patient == null ) {
            return null;
        }
        Integer age = patient.getAge();
        if ( age == null ) {
            return null;
        }
        return age;
    }

    private Gender appointmentPatientGender(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        Patient patient = appointment.getPatient();
        if ( patient == null ) {
            return null;
        }
        Gender gender = patient.getGender();
        if ( gender == null ) {
            return null;
        }
        return gender;
    }

    private Long appointmentSlotId(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        Slot slot = appointment.getSlot();
        if ( slot == null ) {
            return null;
        }
        Long id = slot.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long appointmentSlotClinicId(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        Slot slot = appointment.getSlot();
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

    private String appointmentSlotClinicClinicName(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        Slot slot = appointment.getSlot();
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

    private String appointmentSlotClinicDoctorName(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        Slot slot = appointment.getSlot();
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

    private LocalDate appointmentSlotSlotDate(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        Slot slot = appointment.getSlot();
        if ( slot == null ) {
            return null;
        }
        LocalDate slotDate = slot.getSlotDate();
        if ( slotDate == null ) {
            return null;
        }
        return slotDate;
    }

    private LocalTime appointmentSlotStartTime(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        Slot slot = appointment.getSlot();
        if ( slot == null ) {
            return null;
        }
        LocalTime startTime = slot.getStartTime();
        if ( startTime == null ) {
            return null;
        }
        return startTime;
    }

    private LocalTime appointmentSlotEndTime(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        Slot slot = appointment.getSlot();
        if ( slot == null ) {
            return null;
        }
        LocalTime endTime = slot.getEndTime();
        if ( endTime == null ) {
            return null;
        }
        return endTime;
    }
}
