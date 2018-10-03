package com.oreilly.security.web.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oreilly.security.domain.entities.Appointment;
import com.oreilly.security.domain.entities.AutoUser;
import com.oreilly.security.domain.repositories.AppointmentRepository;
import com.oreilly.security.domain.repositories.AutoUserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/appointments")
public class AppointmentController {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private AutoUserRepository autoUserRepository;

	@ModelAttribute
	public Appointment getAppointment() {
		return new Appointment();
	}

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String getAppointmentPage() {
		return "appointments";
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public List<Appointment> saveAppointment(@AuthenticationPrincipal UserDetails principal,
			@ModelAttribute Appointment appointment) {

		// User principal = (User)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Optional<AutoUser> autoUserOptional = autoUserRepository.findByUsername(principal.getUsername());

		AutoUser autoUser = (AutoUser) principal;

		if (!autoUserOptional.isPresent()) {
			log.info("{} not present", autoUser);
			autoUserRepository.save(autoUser);
		} else {
			log.info("{} present", autoUser);
			
			AutoUser mergedAutoUSer = mergeAutoUser(autoUserOptional.get(), autoUser);
			autoUserRepository.save(mergedAutoUSer);
			autoUser = mergedAutoUSer;
		}

		log.info("principal: {}", principal);

		appointment.setUser(autoUser);
		appointment.setStatus("Initial");
		appointmentRepository.save(appointment);

		return this.appointmentRepository.findAll();
	}

	private AutoUser mergeAutoUser(AutoUser autoUserFromDB, AutoUser autoUserFromLdap) {
		
		//autoUserFromLdap comes from authentication.
		
		autoUserFromDB.setFirstName(autoUserFromLdap.getFirstName());
		autoUserFromDB.setLastName(autoUserFromLdap.getLastName());
		autoUserFromDB.setEmail(autoUserFromLdap.getEmail());
		
		return autoUserFromDB;
	}

	@ResponseBody
	@RequestMapping("/all")
	public List<Appointment> getAppointments() {
		return this.appointmentRepository.findAll();
	}

	@RequestMapping("/{appointmentId}")
	public String getAppointment(@PathVariable("appointmentId") Long appointmentId, Model model) {
		Appointment appointment = appointmentRepository.findOne(appointmentId);
		model.addAttribute("appointment", appointment);
		return "appointment";
	}

}
