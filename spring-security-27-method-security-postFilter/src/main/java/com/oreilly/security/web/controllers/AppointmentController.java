package com.oreilly.security.web.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
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
import com.oreilly.security.domain.repositories.util.AppointmentUtils;
import com.oreilly.security.spelutils.SpelExpressionUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/appointments")
public class AppointmentController {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private AutoUserRepository autoUserRepository;

	@Autowired
	private AppointmentUtils appointmentUtils;

	@ModelAttribute("isUser")
	public boolean isUser(HttpServletRequest request) {
		/*return authentication != null && authentication.getAuthorities().contains(
		 				AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER").get(0));*/

		return new SecurityContextHolderAwareRequestWrapper(request, "").isUserInRole("ROLE_USER");
	}

	@ModelAttribute("isValidUser")
	public boolean isValidUser() {
		return SpelExpressionUtils.evalate("@customSecurityVoter.isValidUser()", Boolean.class);
	}

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

		log.info("principal: {}", principal.getClass().getSimpleName());

		Optional<AutoUser> autoUserOptional = autoUserRepository.findByUsername(principal.getUsername());

		if (!autoUserOptional.isPresent()) {
			throw new UsernameNotFoundException("Username not found");
		}

		appointment.setUser(autoUserOptional.get());
		appointment.setStatus("Initial");
		appointmentRepository.save(appointment);

		return this.appointmentRepository.findAll();
	}

	@RequestMapping("/all")
	@PostFilter("principal.autoUserId == filterObject.user.autoUserId") // Not recommended !! list should be filtered by query
	public @ResponseBody List<Appointment> getAppointments(Authentication auth) {
		return this.appointmentRepository.findAll();
		//return this.appointmentRepository.findByUser((AutoUser) auth.getPrincipal());
	}

	@RequestMapping("/{appointmentId}")
	// Not works as jsr is not enabled
	@PostAuthorize("principal.autoUserId == #model[appointment].user.autoUserId or hasRole('ADMIN')") // [appointment] as Model is a Map
	//@PostAuthorize("returnObject == 'appointment'")
	public String getAppointment(@PathVariable("appointmentId") Long appointmentId, Model model) {
		log.info("retrieving appointment id: {}", appointmentId);
		Appointment appointment = appointmentRepository.findOne(appointmentId);
		model.addAttribute("appointment", appointment);
		return "appointment";
	}

	@RequestMapping("/confirm")
	@RolesAllowed("ROLE_ADMIN")
	public @ResponseBody String confirm() {
		return "confirmed";
	}

	@RequestMapping("/cancel")
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_USER" })
	public @ResponseBody String cancel() {
		return "canceled";
	}

	@RequestMapping("/complete")
	@RolesAllowed("ROLE_ADMIN")
	public @ResponseBody String complete() {
		return "completed";
	}

	@RequestMapping("/testPrefilter")
	public @ResponseBody String testPreFilter(Authentication auth) {

		AutoUser user = (AutoUser) auth.getPrincipal();
		AutoUser otherUser = new AutoUser();

		otherUser.setAutoUserId(100L);
		otherUser.setEmail("attacker@lol.com");

		String lol = appointmentUtils.saveAll(
				new ArrayList<Appointment>(Arrays.asList(AppointmentUtils.createAppointment(user),
						AppointmentUtils.createAppointment(otherUser))));

		return lol;
	}

}
