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
	
	//method that sends an email to the company when a new form is submitted
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
            "</div>",
            name, surname, email, categoryName, content);

        //send the email
        sendHtmlEmail(adminEmail, subject, body);
    }
    
    //method that sends a confirmation email to the user after submitting the form
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
            "    <p style='margin-top: 20px;'>Se hai altri dubbi non esitare a <a href='http://localhost:8080/informationForm' style='color: #007bff;'>scriverci</a> di nuovo.</p>" +
            "    <p style='margin-top: 20px;'>Grazie e buon proseguimento di giornata.</p>" +
            "    <p style='margin-top: 20px;'>Team Cascina Caccia</p>" +
            "    <img src='https://cascinacaccia.net/wp-content/uploads/2018/05/cascinaCaccia_logo_footer.png' alt='Company Logo' style='width: 150px;'/>" +
            "  </div>" +
            "</div>",
            formName, formSurname,
            formName, formSurname, formEmail, categoryName, content);

        //send the email
        sendHtmlEmail(userEmail, subject, body);
    }
    
    //send assigned task email to the employee
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
                "</div>",
                userName, userSurname, formName, formSurname, formEmail, categoryName, content);

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
    
    //method to send the email to the employee when he's revoked from a task
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
	            "</div>",
	            userName, userSurname, formName, formSurname, formEmail, categoryName, content);
	
	        //send the email
	    	sendHtmlEmail(userEmail, subject, body);	
    }
    
    //method to remove a user from a specific information form
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
    
    //method to get all the assigned tasks (forms) to a user
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
    
    //method to assign a status to a task (form)
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
    
    //method to save the information forms
    public void saveInformationForm(Informationform informationForm) {
        informationFormDAO.save(informationForm);
    }
}
