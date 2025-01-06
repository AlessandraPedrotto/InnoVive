package com.cascinacaccia.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.cascinacaccia.entities.BookingForm;
import com.cascinacaccia.entities.Category;
import com.cascinacaccia.entities.Generalform;
import com.cascinacaccia.entities.User;
import com.cascinacaccia.repos.BookingFormDAO;
import com.cascinacaccia.repos.GeneralformDAO;
import com.cascinacaccia.repos.UserDAO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
/*
 * BookingFormService handles the business logic related to booking forms.
 * This includes:
 * - Sending emails to the admin and user when a new form is submitted.
 * - Assigning and removing users to/from information forms.
 * - Updating the status of booking forms.
 */
public class BookingFormService {

	
	@Autowired
	private BookingFormDAO bookingFormDAO;
	@Autowired
    private JavaMailSender mailSender;
	@Autowired
	private UserService userService;
	@Autowired
	private GeneralformDAO generalFormDAO;
	@Autowired
	private UserDAO userDAO;
	
	// Method to validate check-in and check-out dates
    public String validateAndSubmitBookingForm(Date checkIn, Date checkOut) {
        Date today = new Date(); // Get today's date

        // Check if the check-in date is before tomorrow (at least one day after today)
        if (checkIn.before(today) || isSameDay(checkIn, today)) {
            return "Check-in date must be at least one day after today's date.";
        }

        // Check if the check-out date is before tomorrow (at least one day after today)
        if (checkOut.before(today) || isSameDay(checkOut, today)) {
            return "Check-out date must be at least one day after today's date.";
        }

        // Check if check-out is before check-in
        if (checkOut.before(checkIn)) {
            return "Check-out date cannot be before check-in date.";
        }

        // Proceed with the rest of the form processing logic...
        // Save the booking, send emails, etc.

        return "Booking successfully submitted!";
    }

    // Helper method to check if two dates are the same day
    private boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date1).equals(sdf.format(date2));
    }
    
	//method that sends an email to the company when a new form is submitted
    public void sendEmailToAdmin(String adminEmail, String name, String surname, String email, String categoryName, Date checkIn, Date checkOut, String content) throws MessagingException {
    	
    	// Format the check-in and check-out dates as 'dd-MM-yyyy'
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedCheckIn = dateFormat.format(checkIn);
        String formattedCheckOut = dateFormat.format(checkOut);
        
    	String subject = "Nuova prenotazione in arrivo";

        // HTML-formatted email body
        String body = String.format(
            "<div style='font-family: Arial, sans-serif; line-height: 1.6;'>" +
            "  <div style='background: linear-gradient(to right, #FFDD07, #F1921F, #E3007E); padding: 20px; text-align: center;'>" +
            "    <img src='https://cascinacaccia.net/wp-content/uploads/2018/05/cropped-logo-cascina-header-1.png' alt='Company Logo' style='width: 300px;'/>" +
            "  </div>" +
            "  <div style='padding: 20px; color: black; font-size: 14px;'>" +
            "    <p>Ciao,</p>" +
            "    <p>Un nuovo modulo per la richiesta di prenotazione è stato appena inviato. Qua sotto puoi trovare i dettagli:</p>" +
            "    <table style='width: 100%%; border-collapse: collapse; margin-top: 10px;'>" +
            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Nome:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Cognome:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Email:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Categoria:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Periodo:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s - %s</td></tr>" +
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
            name, surname, email, categoryName, formattedCheckIn, formattedCheckOut, content);

        //send the email
        sendHtmlEmail(adminEmail, subject, body);
    }
    
    //method that sends a confirmation email to the user after submitting the form
    public void sendConfirmationEmail(String userEmail, String formName, String formSurname, String formEmail, 
            String categoryName, Date checkIn, Date checkOut, String content) throws MessagingException {
    	
    	// Format the check-in and check-out dates as 'dd-MM-yyyy'
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedCheckIn = dateFormat.format(checkIn);
        String formattedCheckOut = dateFormat.format(checkOut);
        
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
            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Periodo:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s - %s</td></tr>" +
            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Messaggio:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
            "    </table>" +
            "    <p style='margin-top: 20px;'>Se hai altri dubbi non esitare a <a href='http://localhost:8080/informationForm' style='color: #007bff;'>scriverci</a> di nuovo.</p>" +
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
            formName, formSurname, formEmail, categoryName, formattedCheckIn, formattedCheckOut,  content);

        //send the email
        sendHtmlEmail(userEmail, subject, body);
    }
    
    //send assigned task email to the employee
    public void sendAssignedTaskEmail(String userEmail, String userName, String userSurname, 
            String formName, String formSurname, String formEmail, 
            String categoryName, Date checkIn, Date checkOut, String content) throws MessagingException {
		
    	// Format the check-in and check-out dates as 'dd-MM-yyyy'
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedCheckIn = dateFormat.format(checkIn);
        String formattedCheckOut = dateFormat.format(checkOut);
        
    	String subject = "Hai un nuovo task";
			
			// HTML-formatted email body
			String body = String.format(
                "<div style='font-family: Arial, sans-serif; line-height: 1.6;'>" +
                "  <div style='background: linear-gradient(to right, #FFDD07, #F1921F, #E3007E); padding: 20px; text-align: center;'>" +
                "    <img src='https://cascinacaccia.net/wp-content/uploads/2018/05/cropped-logo-cascina-header-1.png' alt='Company Logo' style='width: 300px;'/>" +
                "  </div>" +
                "  <div style='padding: 20px; color: black; font-size: 14px;'>" +
                "    <p>Ciao <strong>%s %s</strong>,</p>" +
                "    <p>Sei stato assegnato ad una nuova prenotazione. Qua sotto puoi trovare i dettagli per contattare il cliente:</p>" +
                "    <table style='width: 100%%; border-collapse: collapse; margin-top: 10px;'>" +
                "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Nome:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
                "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Cognome:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
                "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Email:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
                "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Categoria:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s</td></tr>" +
                "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Periodo:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s - %s</td></tr>" +
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
                userName, userSurname, formName, formSurname, formEmail, categoryName, formattedCheckIn, formattedCheckOut, content);

            //send the email
        	sendHtmlEmail(userEmail, subject, body);
    }

    //method to send the email
    private void sendHtmlEmail(String toEmail, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlBody, true); 

        mailSender.send(message);
    }
    
    //method to assign a user to a task (form)
    public void assignUser(String userId, String bookingFormId) {
    	
    	Optional<BookingForm> bookingForm = bookingFormDAO.findById(bookingFormId);
    	if(bookingForm.isPresent()) {
    		BookingForm bookingFormFound = bookingForm.get();
    		User user = userService.getUserById(userId);
    		
    		//if there is already an assigned user, remove them first
            if (bookingFormFound.getAssignedUser() != null) {
                String existingUserId = bookingFormFound.getAssignedUser().getId();
                removeUser(existingUserId, bookingFormId);
            }
            
    		bookingFormFound.setAssignedUser(user);
    		bookingFormDAO.save(bookingFormFound);
    		
    		Generalform generalform = bookingFormFound.getGeneralform();
            Category category = generalform.getCategory();
            
    		//send email to the assigned user about the new task
            try {
				sendAssignedTaskEmail(user.getEmail(), user.getName(), user.getSurname(),
				        generalform.getName(), generalform.getSurname(),
				        generalform.getEmail(), category.getName(), 
				        bookingFormFound.getCheckIn(),
				        bookingFormFound.getCheckOut(),
				        bookingFormFound.getContent());
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        } else {
            throw new RuntimeException("Booking Form not found with ID: " + bookingFormId);
        }
    }
    
    //method to send the email to the employee when he's revoked from a task
    public void sendRemovedTaskEmail(String userEmail, String userName, String userSurname,
            String formName, String formSurname, String formEmail, String categoryName, Date checkIn, Date checkOut,  
            String content) throws MessagingException{
    	
	    	// Format the check-in and check-out dates as 'dd-MM-yyyy'
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	        String formattedCheckIn = dateFormat.format(checkIn);
	        String formattedCheckOut = dateFormat.format(checkOut);
	        
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
	            "      <tr><td style='padding: 5px; border: 1px solid #ccc;'>Periodo:</td><td style='padding: 5px; border: 1px solid #ccc;'>%s - %s</td></tr>" +
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
	            userName, userSurname, formName, formSurname, formEmail, categoryName, formattedCheckIn, formattedCheckOut, content);
	
	        //send the email
	    	sendHtmlEmail(userEmail, subject, body);	
    }
    
    //method to remove a user from a specific information form
    public void removeUser(String userId, String bookingFormId) {
        Optional<BookingForm> bookingForm = bookingFormDAO.findById(bookingFormId);
        
        if (bookingForm.isPresent()) {
            BookingForm bookingFormFound = bookingForm.get();
            User user = userService.getUserById(userId);
            
            //check if the user is already assigned to this form
            if (bookingFormFound.getAssignedUser() != null && bookingFormFound.getAssignedUser().equals(user)) {
                
            	//remove the user from the assignedUser field
            	bookingFormFound.setAssignedUser(null); 
            	bookingFormDAO.save(bookingFormFound); 
                
                Generalform generalform = bookingFormFound.getGeneralform();
                
                try {
					sendRemovedTaskEmail(user.getEmail(), user.getName(), user.getSurname(),
							bookingFormFound.getGeneralform().getName(), 
							bookingFormFound.getGeneralform().getSurname(), 
							bookingFormFound.getGeneralform().getCategory().getName(),
					        generalform.getEmail(),
					        bookingFormFound.getCheckIn(),
					        bookingFormFound.getCheckOut(),
					        bookingFormFound.getContent());
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            } else {
                throw new RuntimeException("User is not assigned to this Booking Form.");
            }
        } else {
            throw new RuntimeException("Booking Form not found with ID: " + bookingFormId);
        }
    }
    
    //method to get all the assigned tasks (forms) to a user
    public List<Generalform> getAssignedFormsByUser(String userId) {
    	
        //fetch the User entity using the userId from the user repository
        User user = userDAO.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        //fetch the assigned Informationforms for that user
        List<BookingForm> assignedBookingForms = bookingFormDAO.findByAssignedUser(user);

        //extract the IDs of the related Generalform entries
        List<String> generalFormIds = assignedBookingForms.stream()
                .map(bookingform -> bookingform.getGeneralFormId())
                .collect(Collectors.toList());

        //fetch and return the Generalform entries by their IDs
        return generalFormDAO.findAllById(generalFormIds);  
    }
    
    //method to assign a status to a task (form)
    public void assignStatus(String status, String bookingFormId) {
    	Optional<BookingForm> bookingForm = bookingFormDAO.findById(bookingFormId);
    	if(bookingForm.isPresent()) {
    		BookingForm bookingFormFound = bookingForm.get();
    		bookingFormFound.setStatus(status);
    		bookingFormDAO.save(bookingFormFound);
        } else {
            throw new RuntimeException("Booking Form not found with ID: " + bookingFormId);
        }
    }
	
    //method to save the booking forms
	public void saveBookingForm(BookingForm bookingForm) {
		bookingFormDAO.save(bookingForm);
	}
}
