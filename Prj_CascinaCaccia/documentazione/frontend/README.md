# EducatiON
**ITS ICT Piemonte** Laboratorio Integrato's final project, EducatiON is a web platform developed for Cascina Caccia.
## Index 

1. [Introduction](#introduction)  
2. [Usage](#usage)  
3. [Description](#description)  
4. [Configuration and Technical Characteristics](#configuration-and-technical-characteristics)  
5. [Files and Project Structure](#files-and-project-structure)  
6. [Features delivered](#features-delivered)  
7. [Bonuses delivered](#bonuses-delivered)  
8. [Browser compatibility](#browser-compatibility)  
9. [Authors and contact information](#authors-and-contact-information)  
10. [Useful links](#useful-links)  


## Introduction 
This project was developed to support [Cascina Caccia](https://cascinacaccia.net/) , one of Piedmont's most significant properties confiscated from organized crime and dedicated to the memory of Bruno Caccia, a magistrate assassinated in Turin by the 'Ndrangheta in 1983. Cascina Caccia serves as a beacon of justice and social responsibility, yet in recent years, visits from schools and organizations such as scout groups have significantly declined.
To address this challenge, we have created a digital platform called EducatiON that revitalizes Cascina Caccia's engagement with schools and associations.

## Description

### Home Page
- **Hero Section:** A visually striking hero section introduces the mission and activities of Cascina Caccia EducatiON.
- **Interactive Boxes:** Highlighted sections that redirect users to:
  - Educational activities.
  - Cascina Caccia' structure.
  - Booking options.
- **User Tools:**
  - **Chatbot:** Accessible from every page to assist users with FAQs.
  - **Information Form:** Quick access for submitting requests.

### Attivita Page
- Organized into categories for:
  - **Scuole medie**
  - **Scuole superiori**
  - **Attività per tutti**
- Each category features descriptions and media content.

### Struttura Page
- Highlights of key environments, including:
  - Meeting rooms.
  - Bedrooms.
  - Outdoor spaces.
  - Educational areas.

### Prenota Page
- **Reservation Form:** Form interface for entering booking details.
- **Custom Calendar:** Intuitive tool for selecting check-in and check-out dates.

### Area riservata Page
- **Login for Admins and Administrators:**
  - Secure access to manage platform content and user interactions.
  - User roles and permissions configured for efficient administration.


## Usage

The application runs on Java Spring Boot. If you are not using an IDE, you will need to:  

1. **Install Java JDK**  
   - Make sure Java JDK is installed on your system.  

2. **Download Maven**  
   - Ensure Maven is installed for dependency management and building the project.  

3. **Verify Installation**  
   - Use the following commands to confirm the installations:  
     ```bash
     java -version
     mvn -v
     ```

4. **Import the Database**  
   - Import the `caccia` database into XAMPP.  

5. **Navigate to the Project Directory**  
   - Open a terminal and move to the project's root directory.  

6. **Build the Project**  
   - Run the following command to clean and build the project, skipping tests:  
     ```bash
     mvn clean install -DskipTests
     ```

7. **Run the Application**  
   - Start the application using the command:  
     ```bash
     mvn spring-boot:run
     ```  
   The application will now be running and accessible.


## Configuration and Technical Characteristics  

The project has the following technologies for its development:  

### Backend
- Java Spring Boot
- MySQL

### Frontend
- HTML/CSS
- Javascript

### Workflow
- Version Control GitHub


## Files and Project Structure


```bash
Prj_CascinaCaccia/
├── documentazione/                     # Documentation files for the project
│   ├── backend/                        # Backend documentation
│   └── frontend/                        # Frontend documentation
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── cascinacaccia/
│   │   │           ├── controllers/     
│   │   │           ├── entities/          
│   │   │           ├── repos/     
│   │   │           ├── services/        
│   │   │           └── PrjCascinaCacciaApplication.java 
│   │   ├── resources/
│   │   │   ├── static/                 # Contains static files
│   │   │   │   ├── docs/               # Contains PDF
│   │   │   │   ├── img/                # Images for the application
│   │   │   │   ├── scripts/            # JavaScript files
│   │   │   │   ├── styles/             # CSS files
│   │   │   │   └── translation/        # Translation files
│   │   │   ├── templates/              # Contains HTML files
│   │   │   └── application.properties  # Configuration file for application settings
│   ├── test/
│   │   └── java/                       
├── target/                             # Contains compiled code and packaged application after build
├── pom.xml                             # Maven configuration file with dependencies and build settings
├── sitemap.xml                         # Sitemap
└── README.md                           # Documentation file
```
## Features Delivered 
 
1. **Data Collection Forms**  
  - **Information Request Form** : Allows interested groups to request information.
 
  - **Reservation Form** : Enables users to book a period at Cascina Caccia.

Both forms trigger the following actions upon submission:

  - Automatic email response sent to the user.

  - Data stored in the database for reference.

  - Email notification sent to administrators, with the request visible in the back office.
 
2. **Back Office** 
Accessible after login, the back office provides functionalities to:
  - Visualize requests received from the forms.

  - Manage the status of requests (e.g., "Received," "In Progress," "Completed").

  - Filter and sort requests for more efficient management.

  - Oversee administrators and employees.


## Bonuses Delivered 
 
1. **Chatbot** 
A chatbot is integrated to manage frequently asked questions (FAQs).
 
2. **Advanced Back Office** 
Includes additional functionalities to manage administrative tasks.
 
3. **Multilingual Support** 
The website is also available in English.


## Browser compatibility

- **Chrome v132.0.6834.110**: tested and fully compatible
- **Brave v1.75.50**: tested and fully compatible
- **Safari v18.2**: tested and fully compatible 

## Authors and Contact Information 
The project was developed by **InnoVive** , a team of 8 members, organized into the following roles:
### Digital Team 

- **Erica Cavallin**  - [erica.cavallin@edu.itspiemonte.it]()
 
- **Francesca Ulla**  - [francesca.ulla@edu.itspiemonte.it]()
 
- **Eugenio Catena**  - [eugenio.catena@edu.itspiemonte.it]()
 

### Web Team 
 
- **Daniele Margiacchi**  - [daniele.margiacchi@edu.itspiemonte.it]()
 
- **Marco Giaccone**  - [marco.giaccone@edu.itspiemonte.it]()
 
- **Niccolò Camusso**  - [niccolo.camusso@edu.itspiemonte.it]()

### Backend Team 
 
- **Alessandra Pedrotto**  - [alessandra.pedrotto@edu.itspiemonte.it]()
 
- **Ayoub Balmane**  - [ayoub.balmane@edu.itspiemonte.it]()


## Useful Links
- [Cascina Caccia](https://cascinacaccia.net/)
- [ACMOS Association](https://associazione.acmos.net/)
- [Educational Page for Schools](https://cascinacaccia.net/educazione/scuole/)

