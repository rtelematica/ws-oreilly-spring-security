package com.oreilly.security.domain.repositories.util;

import java.util.List;

import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Component;

import com.oreilly.security.domain.entities.Appointment;
import com.oreilly.security.domain.entities.AutoUser;

@Component
public class AppointmentUtils {

	@PreFilter("principal.autoUserId == filterObject.user.autoUserId") // filterObject is the current item iterated on collection
	public String saveAll(List<Appointment> appointments) {

		StringBuilder sb = new StringBuilder();

		for (Appointment appointment : appointments) {
			sb.append(appointment.getUser().getAutoUserId() + " " + appointment.getUser().getEmail());
			sb.append(" ");
		}

		return sb.toString();
	}

	public static Appointment createAppointment(AutoUser user) {
		Appointment appointment = new Appointment();
		appointment.setUser(user);
		return appointment;
	}

}
