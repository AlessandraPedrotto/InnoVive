package com.cascinacaccia.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cascinacaccia.entities.PasswordResetToken;
import com.cascinacaccia.entities.User;
import com.cascinacaccia.repos.TokenDAO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
/*
 * ForgotPasswordService handles the operations related to password reset functionality.
 * It is responsible for:
 * - Generating a password reset token and sending an email to the user with the reset link.
 * - Checking if a reset token has expired.
 * - Retrieving a reset token by its value.
 * - Cleaning up expired reset tokens periodically.
 */
public class ForgotPasswordService {
	
	@Autowired
	private TokenDAO tokenDAO;
	@Autowired
	private JavaMailSender mailSender;
	
	/*
     * Method to generate a password reset token, create the reset link, 
     * and send an email to the user with the reset link.
     *
     * @param user The user requesting a password reset
     * @param userName The first name of the user
     * @param userSurname The surname of the user
     * @return A string indicating the success or failure of the operation
     */
    public String sendResetEmail(User user, String userName, String userSurname) {
        try {
            String resetLink = generateResetToken(user);
            String subject = "Richiesta reset password";

            // HTML-formatted email body
            String body = String.format(
                    "<div style='font-family: Arial, sans-serif; line-height: 1.6;'>" +
                    "  <div style='background: linear-gradient(to right, #FFDD07, #F1921F, #E3007E); padding: 20px; text-align: center;'>" +
                    "    <img src='https://cascinacaccia.net/wp-content/uploads/2018/05/cropped-logo-cascina-header-1.png' alt='Company Logo' style='width: 300px;'/>" +
                    "  </div>" +
                    "  <div style='padding: 20px; color: black; font-size: 14px;'>" +
                    "    <p>Ciao <strong>%s %s</strong>,</p>" +
                    "    <p>Abbiamo ricevuto la tua richiesta per il reset della password. Per favore clicca qui sotto per accedere alla pagina per il reset:</p>" +
                    "    <p style='font-size: 14px; font-weight: bold; color: #007bff;'>" +
                    "      <a href='%s' style='color: #007bff;'>Reset Your Password</a>" +
                    "    </p>" +
                    "    <p style='margin-top: 20px;'>Questo link scadrà tra 15 minuti. Se non hai chiesto il reset della password ingora questa mail.</p>" +
                    "    <p style='margin-top: 20px;'>Grazie e buon proseguimento di giornata.</p>" +
                    "    <p style='margin-top: 20px;'>Team Cascina Caccia</p>" +
                    "    <img src='https://cascinacaccia.net/wp-content/uploads/2018/05/cascinaCaccia_logo_footer.png' alt='Company Footer Logo' style='width: 150px;'/>" +
                    "  </div>" +
                    "  <div style='width: 100%%; height: 10px; background: linear-gradient(to right, #FFDD07, #F1921F, #E3007E);'></div>" +
                    "  <div style='background-color: rgba(255, 119, 0, 0.56); display: flex; flex-direction: column; align-items: center; padding: 20px; color: black; text-align: center;'>" +
                    "    <div class='footer-content' style='max-width: 800px; text-align: center; margin: 0 auto;'>" +
                    "      <h3 style='margin-bottom: 10px;'>SOCIAL</h3>" +
                    "      <table style='width: 100%%; text-align: center;'>" +
                    "        <tr>" +
                    "          <td>" +
        	        "				<a href=\"https://www.facebook.com/cascinacaccia/?locale=it_IT\" target=\"_blank\">\r\n" +
        			"    				<img class=\"social-icons\" src=\"https://cdn-icons-png.freepik.com/256/2111/2111564.png?ga=GA1.1.272991371.1726556643&semt=ais_hybrid\" alt=\"facebook\" style=\"width: 50px; height: 50px;\"/>\r\n" +
        			"				</a>" +
        			"		   </td>" +
        			"          <td>" +
        			"				<a href=\"https://www.instagram.com/cascina_caccia/\" target=\"_blank\">\r\n" +
        			"    				<img class=\"social-icons\" src=\"https://cdn-icons-png.freepik.com/256/2111/2111679.png?ga=GA1.1.272991371.1726556643\" alt=\"instagram\" style=\"width: 50px; height: 50px;\"/>\r\n" +
        			"				</a>" +
        			"		   </td>" +
                    "          <td>" +
                    "				<a href=\"https://www.youtube.com/@AcmosNet99\" target=\"_blank\">\r\n" +
                    "    				<img class=\"social-icons\" src=\"https://cdn-icons-png.freepik.com/256/2111/2111839.png?ga=GA1.1.272991371.1726556643\" alt=\"youtube\" style=\"width: 50px; height: 50px;\"/>\r\n" +
                    "				</a>" +
                    "		   </td>" +
                    "        </tr>" +
                    "      </table>" +
                    "      <p style='margin-top: 20px; font-size: 13px;'>Copyright © 2025 Libera Piemonte - Tutti i diritti riservati.</p>" +
                    "      <p style='font-size: 13px;'>Via Serra Alta, 6, San Sebastiano da Po (TO)</p>" +
                    "    </div>" +
                    "  </div>" +
                    "</div>", 
                    userName, userSurname, resetLink);

            //send the email
            sendHtmlEmail(user.getEmail(), subject, body);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    /*
     * Method to send an HTML-formatted email.
     * 
     * @param toEmail The recipient's email address
     * @param subject The subject of the email
     * @param htmlBody The body of the email in HTML format
     * @throws MessagingException If there is an issue with sending the email
     */
    private void sendHtmlEmail(String toEmail, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlBody, true); 

        mailSender.send(message);
    }
    
    /*
     * Method to generate a unique password reset token and create a corresponding reset link.
     * The token will expire in 15 minutes.
     *
     * @param user The user for whom the reset token is being generated
     * @return The reset URL containing the generated token
     */
	private String generateResetToken(User user) {
    	
		//generate a new UUID for the token
        UUID uuid = UUID.randomUUID();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expiryDateTime = currentDateTime.plusMinutes(15);
        
        //check if the user already has an existing reset token and delete it if present
        Optional<PasswordResetToken> oldToken = tokenDAO.findByUser(user);
        if (oldToken.isPresent()) {
        	oldToken.get().setUser(null);
        	deleteAndFlushPasswordResetToken(oldToken.get());
        }
        
        //create a new password reset token
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(uuid.toString());
        resetToken.setExpiryDateTime(expiryDateTime);

        //save the token in the database
        tokenDAO.save(resetToken);

        //generate the reset link
        String endpointUrl = "http://localhost:8080/resetPassword/";
        return endpointUrl + resetToken.getToken();
    }
	
	/*
     * Method to delete a password reset token and flush the changes.
     *
     * @param token The token to be deleted
     */
	public void deleteAndFlushPasswordResetToken(PasswordResetToken token) {
		tokenDAO.deleteById(token.getId()); 
	}
	
	/*
     * Method to check if a reset token has expired.
     *
     * @param expiryDateTime The expiration time of the token
     * @return true if the token has expired, false otherwise
     */
    public boolean hasExpired(LocalDateTime expiryDateTime) {
        return expiryDateTime.isBefore(LocalDateTime.now());
    }

    /*
     * Method to find a password reset token by its token value.
     *
     * @param token The token value to search for
     * @return The corresponding PasswordResetToken object, or null if not found
     */
    public PasswordResetToken findResetTokenByToken(String token) {
        return tokenDAO.findByToken(token);
    }
	
    /*
     * Method to clean up expired password reset tokens periodically.
     * This method is scheduled to run every second.
     */
    @Scheduled(fixedRate = 1000)
    public void cleanExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        
        //fetch tokens that have expired
        List<PasswordResetToken> expiredTokens = tokenDAO.findByExpiryDateTimeBefore(now);
        
        //if there are expired tokens, delete them immediately
        if (!expiredTokens.isEmpty()) {
            tokenDAO.deleteAll(expiredTokens);
            System.out.println("Expired tokens cleaned up: " + expiredTokens.size());
        }
    }
}
