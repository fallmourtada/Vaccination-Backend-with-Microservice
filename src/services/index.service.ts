import { AuthService } from './auth.service';
import { UserService } from './users.service';
import { LocationService } from './localites.service';
import { AppointmentService } from './appointments.service';
import { VaccinationService } from './vaccinations.service';
import { VaccinService } from './vaccins.service';

export const services = {
  authService: AuthService,
  userService: UserService,
  locationService: LocationService,
  appointmentService: AppointmentService,
  vaccinationService: VaccinationService,
  vaccinService: VaccinService,
};

export default services;
