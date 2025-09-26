package com.gestionvaccination.appointmentservice.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Envoie un rappel de rendez-vous de vaccination
     *
     * @param toEmail Email du destinataire (parent)
     * @param parentNom Nom du parent
     * @param enfantNom Nom de l'enfant
     * @param enfantPrenom Prénom de l'enfant
     * @param dateVaccination Date du rendez-vous de vaccination
     * @param nomVaccin Nom du vaccin à effectuer
     * @param joursRestants Nombre de jours restants avant le rendez-vous
     */
    public void sendVaccinationReminder(String toEmail, String parentNom, String enfantPrenom, String enfantNom,
                                        LocalDate dateVaccination, String nomVaccin, int joursRestants) {
        try {
            String subject = getSubjectBasedOnDays(joursRestants);

            String htmlContent = generateReminderEmailHtml(
                    parentNom, enfantPrenom, enfantNom,
                    dateVaccination.format(dateFormatter),
                    nomVaccin, joursRestants
            );

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Email de rappel envoyé à {} pour la vaccination de {} prévue le {}",
                    toEmail, enfantPrenom, dateVaccination);

        } catch (MessagingException e) {
            log.error("Erreur lors de l'envoi de l'email de rappel à {}", toEmail, e);
        }
    }

    /**
     * Génère le sujet de l'email en fonction du nombre de jours restants
     */
    private String getSubjectBasedOnDays(int joursRestants) {
        return switch (joursRestants) {
            case 0 -> "🚨 AUJOURD'HUI : Rappel de vaccination pour votre enfant";
            case 1 -> "⏰ DEMAIN : Rappel de vaccination pour votre enfant";
            case 2 -> "📅 Dans 2 jours : Rappel de vaccination pour votre enfant";
            default -> "📌 Rappel de vaccination pour votre enfant";
        };
    }

    /**
     * Génère le contenu HTML de l'email de rappel
     */
    private String generateReminderEmailHtml(String parentNom, String enfantPrenom, String enfantNom,
                                             String dateFormatted, String nomVaccin, int joursRestants) {

        String urgenceMessage = switch (joursRestants) {
            case 0 -> "<span style=\"color: #d9534f; font-weight: bold;\">AUJOURD'HUI</span>";
            case 1 -> "<span style=\"color: #f0ad4e; font-weight: bold;\">DEMAIN</span>";
            case 2 -> "dans <span style=\"font-weight: bold;\">2 jours</span>";
            default -> "";
        };

        return """
        <html>
        <head>
          <style>
            body {
              font-family: 'Segoe UI', Arial, sans-serif;
              background-color: #f6f9fc;
              padding: 20px;
              margin: 0;
            }
            .container {
              max-width: 600px;
              margin: auto;
              background-color: #ffffff;
              border-radius: 10px;
              padding: 30px;
              box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            }
            .header {
              background-color: #28a745;
              color: white;
              padding: 20px;
              border-radius: 8px 8px 0 0;
              text-align: center;
            }
            .vaccine-info {
              background-color: #e8f4f4;
              border-left: 5px solid #28a745;
              padding: 15px;
              margin: 20px 0;
              border-radius: 4px;
            }
            .important {
              color: #d9534f;
              font-weight: bold;
            }
            .date-box {
              background-color: #f8f9fa;
              border: 2px solid #dee2e6;
              border-radius: 6px;
              padding: 10px;
              text-align: center;
              margin: 15px 0;
              font-size: 18px;
              font-weight: bold;
              color: #495057;
            }
            .footer {
              font-size: 13px;
              color: #6c757d;
              text-align: center;
              margin-top: 30px;
              padding-top: 15px;
              border-top: 1px solid #e9ecef;
            }
            .action-btn {
              display: block;
              width: fit-content;
              margin: 25px auto;
              background-color: #28a745;
              color: white;
              text-decoration: none;
              padding: 12px 25px;
              border-radius: 5px;
              font-weight: bold;
              text-align: center;
            }
          </style>
        </head>
        <body>
          <div class="container">
            <div class="header">
              <h1>Rappel de Vaccination</h1>
            </div>
            
            <p>Bonjour %s,</p>
            
            <p>Nous vous rappelons que le rendez-vous de vaccination pour <strong>%s %s</strong> est prévu %s.</p>
            
            <div class="vaccine-info">
              <p><strong>📍 Vaccin à effectuer :</strong> %s</p>
              <p><strong>📅 Date prévue :</strong></p>
              <div class="date-box">%s</div>
            </div>
            
            <p>Ce vaccin est important pour garantir une protection optimale de votre enfant contre certaines maladies graves.</p>
            
            <a href="#" class="action-btn">Voir les détails du rendez-vous</a>
            
            <p>Si vous ne pouvez pas vous présenter à ce rendez-vous, veuillez nous contacter dès que possible pour le reprogrammer.</p>
            
            <div class="footer">
              <p>Merci de votre attention à la santé de votre enfant.<br>
              Service de Gestion des Vaccinations</p>
            </div>
          </div>
        </body>
        </html>
        """.formatted(parentNom, enfantPrenom, enfantNom, urgenceMessage, nomVaccin, dateFormatted);
    }
}