package com.cascinacaccia.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.cascinacaccia.entities.Category;
import com.cascinacaccia.entities.Generalform;
import com.cascinacaccia.entities.Informationform;
import com.cascinacaccia.entities.User;
import com.cascinacaccia.repos.GeneralformDAO;
import com.cascinacaccia.repos.InformationformDAO;
import com.cascinacaccia.repos.UserDAO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
/*
 * InformationFormService handles the business logic related to information forms.
 * This includes:
 * - Sending emails to the admin and user when a new form is submitted.
 * - Assigning and removing users to/from information forms.
 * - Updating the status of information forms.
 */
public class InformationFormService {

	@Autowired
    private JavaMailSender mailSender;
	@Autowired
	private InformationformDAO informationFormDAO;
	@Autowired
	private UserService userService;
	@Autowired
	private GeneralformDAO generalFormDAO;
	@Autowired
	private UserDAO userDAO;
	
	/*
     * Method to send an email to the admin when a new form is submitted.
     * This email contains details about the user's submission including name,
     * surname, email, category, and message.
     *
     * @param adminEmail The email of the admin to receive the notification
     * @param name The first name of the user submitting the form
     * @param surname The surname of the user submitting the form
     * @param email The email of the user submitting the form
     * @param categoryName The category chosen by the user in the form
     * @param content The content of the form message
     * @throws MessagingException If there is an error sending the email
     */
    public void sendEmailToAdmin(String adminEmail, String name, String surname, String email, String categoryName, String content) throws MessagingException {
    	
    	String subject = "Nuovo form in arrivo";

        // HTML-formatted email body
        String body = String.format(
            "<div style='font-family: Arial, sans-serif; line-height: 1.6;'>" +
            "  <div style='background: linear-gradient(to right, #FFDD07, #F1921F, #E3007E); padding: 20px; text-align: center;'>" +
            "    <img src='https://cascinacaccia.net/wp-content/uploads/2018/05/cropped-logo-cascina-header-1.png' alt='Company Logo' style='width: 300px;'/>" +
            "  </div>" +
            "  <div style='padding: 20px; color: black; font-size: 14px;'>" +
            "    <p>Ciao,</p>" +
            "    <p>Un nuovo modulo per la richiesta di informazioni è stato appena inviato. Qua sotto puoi trovare i dettagli:</p>" +
            "    <table style='width: 100%%; border-collapse: collapse; margin-top: 10px;'>" +
            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Nome:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Cognome:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Email:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Categoria:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Messaggio:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
            "    </table>" +
            "    <p style='margin-top: 20px;'><a href='http://localhost:8080/login' style='color: #007bff;'>Accedi al tuo account </a>per assegnare la richiesta a qualcuno.</p></br>" +
            "    <p style='margin-top: 20px;'>Grazie e buon proseguimento di giornata.</p>" +
            "    <p style='margin-top: 20px;'>Team Cascina Caccia</p>" +
            "    <img src='https://cascinacaccia.net/wp-content/uploads/2018/05/cascinaCaccia_logo_footer.png' alt='Company Logo' style='width: 150px;'/>" +
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
            name, surname, email, categoryName, content);

        //send the email
        sendHtmlEmail(adminEmail, subject, body);
    }
    
    /*
     * Method to send a confirmation email to the user after submitting the form.
     * This email contains a summary of the user's submission including name,
     * surname, email, category, and message.
     *
     * @param userEmail The email of the user to receive the confirmation
     * @param formName The first name of the user submitting the form
     * @param formSurname The surname of the user submitting the form
     * @param formEmail The email of the user submitting the form
     * @param categoryName The category chosen by the user in the form
     * @param content The content of the form message
     * @throws MessagingException If there is an error sending the email
     */
    public void sendConfirmationEmail(String userEmail, String formName, String formSurname, String formEmail, 
            String categoryName, String content) throws MessagingException {
    	
    	String subject = "Form inviato con successo";

        // HTML-formatted email body
        String body = String.format(
            "<div style='font-family: Arial, sans-serif; line-height: 1.6;'>" +
            "  <div style='background: linear-gradient(to right, #FFDD07, #F1921F, #E3007E); padding: 20px; text-align: center;'>" +
            "    <img src='https://cascinacaccia.net/wp-content/uploads/2018/05/cropped-logo-cascina-header-1.png' alt='Company Logo' style='width: 300px;'/>" +
            "  </div>" +
            "  <div style='padding: 20px; color: black; font-size: 14px;'>" +
            "    <p>Ciao <strong>%s %s</strong>,</p>" +
            "    <p>Grazie per averci contattato! Abbiamo ricevuto la tua richiesta, qualcuno provvederà a contattarti il più presto possibile.</p>" +
            "    <p>Qua sotto puoi trovare il riepilogo della tua richiesta:</p>" +
            "    <table style='width: 100%%; border-collapse: collapse; margin-top: 10px;'>" +
            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Nome:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Cognome:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Email:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Categoria:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Messaggio:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
            "    </table>" +
            "    <p style='margin-top: 20px;'>Se hai altri dubbi non esitare a <a href='http://localhost:8080/' style='color: #007bff;'>scriverci</a> di nuovo.</p>" +
            "    <p style='margin-top: 20px;'>Grazie e buon proseguimento di giornata.</p>" +
            "    <p style='margin-top: 20px;'>Team Cascina Caccia</p>" +
            "    <img src='https://cascinacaccia.net/wp-content/uploads/2018/05/cascinaCaccia_logo_footer.png' alt='Company Logo' style='width: 150px;'/>" +
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
            formName, formSurname,
            formName, formSurname, formEmail, categoryName, content);

        //send the email
        sendHtmlEmail(userEmail, subject, body);
    }
    
    /*
     * Method to send an email to the assigned employee regarding a new task.
     * This method sends an HTML-formatted email to the assigned employee with the task details.
     * 
     * @param userEmail The email address of the assigned employee.
     * @param userName The first name of the assigned employee.
     * @param userSurname The surname of the assigned employee.
     * @param formName The first name of the form submitter (customer).
     * @param formSurname The surname of the form submitter (customer).
     * @param formEmail The email address of the form submitter (customer).
     * @param categoryName The category selected by the form submitter.
     * @param content The message content provided by the form submitter.
     * @throws MessagingException If there is an error sending the email.
     */
    public void sendAssignedTaskEmail(String userEmail, String userName, String userSurname, 
            String formName, String formSurname, String formEmail, 
            String categoryName, String content) throws MessagingException {
			String subject = "Hai un nuovo task";
			
			// HTML-formatted email body
			String body = String.format(
                "<div style='font-family: Arial, sans-serif; line-height: 1.6;'>" +
                "  <div style='background: linear-gradient(to right, #FFDD07, #F1921F, #E3007E); padding: 20px; text-align: center;'>" +
                "    <img src='https://cascinacaccia.net/wp-content/uploads/2018/05/cropped-logo-cascina-header-1.png' alt='Company Logo' style='width: 300px;'/>" +
                "  </div>" +
                "  <div style='padding: 20px; color: black; font-size: 14px;'>" +
                "    <p>Ciao <strong>%s %s</strong>,</p>" +
                "    <p>Sei stato assegnato ad un nuovo form. Qua sotto puoi trovare i dettagli per contattare il cliente:</p>" +
                "    <table style='width: 100%%; border-collapse: collapse; margin-top: 10px;'>" +
                "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Nome:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
                "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Cognome:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
                "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Email:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
                "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Categoria:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
                "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Messaggio:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
                "    </table>" +
                "    <p style='margin-top: 20px;'><a href='http://localhost:8080/login' style='color: #007bff;'>Accedi al tuo account</a> per aggiornare lo stato della richiesta.</p></br>" +
                "    <p style='margin-top: 20px;'>Grazie e buon proseguimento di giornata.</p>" +
                "    <p style='margin-top: 20px;'>Team Cascina Caccia</p>" +
                "    <img src='https://cascinacaccia.net/wp-content/uploads/2018/05/cascinaCaccia_logo_footer.png' alt='Company Logo' style='width: 150px;'/>" +
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
                userName, userSurname, formName, formSurname, formEmail, categoryName, content);

            //send the email
        	sendHtmlEmail(userEmail, subject, body);
    }

    /*
     * Method to send an HTML-formatted email.
     * This method sends an email to the specified recipient with the given subject and body content.
     * It uses the MimeMessageHelper to create and send the email.
     *
     * @param toEmail The recipient's email address
     * @param subject The subject of the email
     * @param htmlBody The HTML content of the email body
     * @throws MessagingException If there is an error sending the email
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
     * Method to assign a user to a specific information form (task).
     * This method checks if the form exists, removes any previously assigned user,
     * and then assigns the new user to the task. It also sends an email notification to the assigned user.
     *
     * @param userId The ID of the user to be assigned
     * @param informationFormId The ID of the information form (task) to which the user will be assigned
     * @throws RuntimeException If the information form is not found
     */
    public void assignUser(String userId, String informationFormId) {
    	
    	Optional<Informationform> informationForm = informationFormDAO.findById(informationFormId);
    	if(informationForm.isPresent()) {
    		Informationform informationFormFound = informationForm.get();
    		User user = userService.getUserById(userId);
    		
    		//if there is already an assigned user, remove them first
            if (informationFormFound.getAssignedUser() != null) {
                String existingUserId = informationFormFound.getAssignedUser().getId();
                removeUser(existingUserId, informationFormId);
            }
            
    		informationFormFound.setAssignedUser(user);
    		informationFormDAO.save(informationFormFound);
    		
    		Generalform generalform = informationFormFound.getGeneralform();
            Category category = generalform.getCategory();
            
    		//send email to the assigned user about the new task
            try {
				sendAssignedTaskEmail(user.getEmail(), user.getName(), user.getSurname(),
				        generalform.getName(), generalform.getSurname(),
				        generalform.getEmail(), category.getName(), 
				        informationFormFound.getContent());
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        } else {
            throw new RuntimeException("Information Form not found with ID: " + informationFormId);
        }
    }
    
    /*
     * Method to send an email to a user when they are removed from a task (information form).
     * This email informs the user that they are no longer responsible for the task and provides the details.
     *
     * @param userEmail The email address of the user to be notified
     * @param userName The first name of the user
     * @param userSurname The surname of the user
     * @param formName The name of the form (task) from which the user is removed
     * @param formSurname The surname associated with the form
     * @param formEmail The email associated with the form
     * @param categoryName The category of the form
     * @param content The content/message of the form
     * @throws MessagingException If there is an error sending the email
     */
    public void sendRemovedTaskEmail(String userEmail, String userName, String userSurname,
            String formName, String formSurname, String formEmail, String categoryName, 
            String content) throws MessagingException{
	    	String subject = "Sei stato rimosso da un task";
			
			// HTML-formatted email body
			String body = String.format(
	            "<div style='font-family: Arial, sans-serif; line-height: 1.6;'>" +
	            "  <div style='background: linear-gradient(to right, #FFDD07, #F1921F, #E3007E); padding: 20px; text-align: center;'>" +
	            "    <img src='https://cascinacaccia.net/wp-content/uploads/2018/05/cropped-logo-cascina-header-1.png' alt='Company Logo' style='width: 300px;'/>" +
	            "  </div>" +
	            "  <div style='padding: 20px; color: black; font-size: 14px;'>" +
	            "    <p>Ciao <strong>%s %s</strong>,</p>" +
	            "    <p>Sei stato rimosso da un task, quindi ora non te ne dovrai più occupare. Qua sotto puoi trovare i dettagli della richiesta:</p>" +
	            "    <table style='width: 100%%; border-collapse: collapse; margin-top: 10px;'>" +
	            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Nome:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
	            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Cognome:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
	            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Email:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
	            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Categoria:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
	            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Messaggio:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
	            "    </table>" +
	            "    <p style='margin-top: 20px;'>Se ritieni che questo sia stato un errore contatta l'amministratore della pagina.</p></br>" +
	            "    <p style='margin-top: 20px;'>Grazie e buon proseguimento di giornata.</p>" +
	            "    <p style='margin-top: 20px;'>Team Cascina Caccia</p>" +
	            "    <img src='https://cascinacaccia.net/wp-content/uploads/2018/05/cascinaCaccia_logo_footer.png' alt='Company Logo' style='width: 150px;'/>" +
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
	            userName, userSurname, formName, formSurname, formEmail, categoryName, content);
	
	        //send the email
	    	sendHtmlEmail(userEmail, subject, body);	
    }
    
    /*
     * Method to remove a user from a specific information form.
     * This method checks if the user is assigned to the form, and if so,
     * removes the user and sends an email notifying the user of their removal.
     *
     * @param userId The ID of the user to be removed
     * @param informationFormId The ID of the information form from which the user is to be removed
     * @throws RuntimeException If the user is not assigned to the form or the form is not found
     */
    public void removeUser(String userId, String informationFormId) {
        Optional<Informationform> informationForm = informationFormDAO.findById(informationFormId);
        
        if (informationForm.isPresent()) {
            Informationform informationFormFound = informationForm.get();
            User user = userService.getUserById(userId);
            
            //check if the user is already assigned to this form
            if (informationFormFound.getAssignedUser() != null && informationFormFound.getAssignedUser().equals(user)) {
                
            	//remove the user from the assignedUser field
                informationFormFound.setAssignedUser(null); 
                informationFormDAO.save(informationFormFound); 
                
                Generalform generalform = informationFormFound.getGeneralform();
                
                try {
					sendRemovedTaskEmail(user.getEmail(), user.getName(), user.getSurname(),
					        informationFormFound.getGeneralform().getName(), 
					        informationFormFound.getGeneralform().getSurname(), 
					        informationFormFound.getGeneralform().getCategory().getName(),
					        generalform.getEmail(),
					        informationFormFound.getContent());
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            } else {
                throw new RuntimeException("User is not assigned to this Information Form.");
            }
        } else {
            throw new RuntimeException("Information Form not found with ID: " + informationFormId);
        }
    }
    
    /*
     * Method to get all the assigned tasks (forms) for a given user.
     * This method fetches all the forms assigned to a specific user and returns a list of related Generalform entries.
     *
     * @param userId The ID of the user whose assigned forms are to be fetched
     * @return A list of Generalform entries that the user is assigned to
     * @throws RuntimeException If the user is not found
     */
    public List<Generalform> getAssignedFormsByUser(String userId) {
    	
        //fetch the User entity using the userId from the user repository
        User user = userDAO.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        //fetch the assigned Informationforms for that user
        List<Informationform> assignedInformationForms = informationFormDAO.findByAssignedUser(user);

        //extract the IDs of the related Generalform entries
        List<String> generalFormIds = assignedInformationForms.stream()
                .map(informationform -> informationform.getGeneralFormId())
                .collect(Collectors.toList());

        //fetch and return the Generalform entries by their IDs
        return generalFormDAO.findAllById(generalFormIds);  
    }
    
    /*
     * Method to assign a status to a task (form).
     * This method sets the status of a specific information form and saves the updated status.
     *
     * @param status The status to be assigned to the information form
     * @param informationFormId The ID of the information form to update
     * @throws RuntimeException If the information form is not found
     */
    public void assignStatus(String status, String informationFormId) {
    	Optional<Informationform> informationForm = informationFormDAO.findById(informationFormId);
    	if(informationForm.isPresent()) {
    		Informationform informationFormFound = informationForm.get();
    		informationFormFound.setStatus(status);
    		informationFormDAO.save(informationFormFound);
        } else {
            throw new RuntimeException("Information Form not found with ID: " + informationFormId);
        }
    }
    
    /*
     * Method to save the information form.
     * This method persists the given Informationform entity to the database.
     *
     * @param informationForm The information form entity to be saved
     */
    public void saveInformationForm(Informationform informationForm) {
        informationFormDAO.save(informationForm);
    }
}
