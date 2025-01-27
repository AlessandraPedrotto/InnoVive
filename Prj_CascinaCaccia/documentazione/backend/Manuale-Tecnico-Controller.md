## Controllers

### AuthController
##### Descrizione generale
Il AuthController è responsabile per la gestione delle richieste legate all'autenticazione dell'utente, in particolare per la visualizzazione della pagina di login. Il controller gestisce i parametri di errore relativi all'accesso e alla scadenza della sessione, e fornisce messaggi appropriati in base alla lingua selezionata dall'utente. Inoltre, recupera tutte le categorie dal database e le invia alla vista di login per il rendering.

##### Dipendenze
* CategoryDAO: Viene utilizzato per recuperare tutte le categorie dal database.

##### Mapping

##### loginPage (GET)
###### Descrizione:
Gestisce le richieste GET per la pagina di login. Se ci sono errori di accesso o la sessione è scaduta, il metodo li gestisce e aggiunge i messaggi di errore al modello. Se l'utente non specifica una lingua, viene impostato un valore di fallback (italiano). Inoltre, il metodo recupera tutte le categorie e le invia alla vista di login.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### HomeController
##### Descrizione generale
Il HomeController è un controller responsabile per la gestione delle richieste relative alle principali pagine del sito web, come la home page, la pagina di prenotazione, la pagina delle attività e la pagina della struttura. Il controller recupera tutte le categorie dal database e le invia alla vista, insieme a un parametro facoltativo di lingua. In questo modo, le pagine vengono renderizzate con le categorie appropriate e la lingua selezionata dall'utente.

##### Dipendenze
* CategoryDAO: Questo componente è utilizzato per accedere al database e recuperare tutte le categorie disponibili.

##### Mapping

##### home (GET)
###### Descrizione:
Gestisce le richieste per la home page ("/"). Recupera tutte le categorie e le passa al modello, insieme a un parametro facoltativo di lingua.

##### prenota (GET)
###### Descrizione:
Gestisce le richieste per la pagina di prenotazione ("/prenota"). Recupera le categorie dal database e le invia alla vista, insieme al parametro della lingua.

##### attivita (GET)
###### Descrizione:
Gestisce le richieste per la pagina delle attività ("/attivita"). Recupera le categorie dal database e le invia alla vista, insieme al parametro della lingua.

##### struttura (GET)
###### Descrizione:
Gestisce le richieste per la pagina della struttura ("/struttura"). Recupera le categorie dal database e le invia alla vista, insieme al parametro della lingua.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### CustomErrorController
##### Descrizione generale
Il CustomErrorController è un controller dedicato alla gestione degli errori nell'applicazione. Gestisce le richieste inviate all'endpoint /error e visualizza una pagina di errore personalizzata. Questo controller viene invocato automaticamente ogni volta che si verifica un errore nell'applicazione, come un errore 404 (pagina non trovata) o 500 (errore interno del server).

##### Mapping

##### handleError (GET)
###### Descrizione:
Gestisce tutte le richieste alla pagina di errore ("/error"), invocata quando si verifica un errore nell'applicazione. Mostra una pagina di errore personalizzata, fornendo un messaggio relativo all'errore.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### ForgotPasswordController
##### Descrizione generale
Il ForgotPasswordController è un controller che gestisce la logica per il recupero della password dimenticata. Consente agli utenti di avviare il processo di reset della password inviando un'email di recupero. Questo controller fornisce sia la visualizzazione della pagina di recupero password che il processo di invio dell'email di reset. Gestisce anche le risposte in base all'esito dell'operazione (successo o errore), mostrando i relativi messaggi.

##### Dipendenze
* ForgotPasswordService: Fornisce la logica per inviare l'email di recupero password. Questo servizio viene invocato per inviare l'email all'utente che ha richiesto il reset della password.
* UserDAO: La classe di accesso ai dati che interagisce con il database per recuperare le informazioni relative agli utenti. Viene utilizzata per cercare l'utente in base all'email fornita durante la richiesta di reset password.
* CategoryDAO: Interagisce con il database per recuperare tutte le categorie da visualizzare nelle pagine. Questo è utilizzato per popolare la pagina con le categorie disponibili.
* PasswordEncoder: Codifica e verifica le password. Sebbene non venga usato direttamente in questo controller, è probabilmente previsto per la gestione della sicurezza delle password in altre parti dell'applicazione.
* REGEX_PASSWORD: Una costante che rappresenta un'espressione regolare per validare la forza della password (se necessario per la validazione futura).

##### Mapping

##### showForgotPasswordPage (GET)
###### Descrizione:
Gestisce la navigazione verso la pagina di recupero della password. Quando l'utente accede a /forgotPassword, viene visualizzata la pagina in cui l'utente può inserire l'email per il reset della password.

##### processForgotPassword (POST)
###### Descrizione:
Gestisce la logica di reset della password, inviata via email all'utente che ha richiesto il recupero.

##### showResetPasswordForm (GET)
###### Descrizione:
Questo mapping gestisce la visualizzazione del modulo di reset della password quando l'utente clicca sul link che contiene un token di reset. Il token è passato come parte dell'URL, e il controller verifica se il token è valido (non scaduto).

##### processResetPassword (POST)
###### Descrizione:
Questo mapping gestisce il processo di reset della password quando l'utente invia il modulo con la nuova password. Verifica la validità del token, se la password e la conferma della password corrispondono, e se la nuova password soddisfa i criteri di sicurezza.

##### isValidPassword
###### Descrizione:
Questo metodo verifica che la password fornita dall'utente rispetti i criteri di sicurezza stabiliti (almeno 8 caratteri, una lettera maiuscola, un numero e un carattere speciale).

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### RequestController
##### Descrizione generale
Il RequestController gestisce la visualizzazione e l'interazione con le richieste di vari tipi di moduli (Informationform e BookingForm). Questo controller fornisce funzionalità di ricerca, filtro, ordinamento e paginazione per visualizzare i moduli inviati dagli utenti, con possibilità di filtrare i risultati per categorie, stati, date e altre opzioni. Inoltre, gestisce la logica di visualizzazione del numero di nuovi moduli, calcolato in base al timestamp dell'ultima visita dell'utente.

##### Dipendenze
* GeneralformDAO: Responsabile per l'accesso ai dati relativi ai moduli generali (Generalform).
* InformationformDAO: Gestisce l'accesso ai dati relativi ai moduli di tipo informativo (Informationform).
* UserService: Fornisce metodi per la gestione degli utenti, come il recupero dell'utente tramite email.
* CategoryDAO: Gestisce l'accesso ai dati relativi alle categorie di moduli.
* UserDAO: Utilizzato per gestire l'accesso e la modifica dei dati relativi agli utenti.
* BookingFormDAO: Gestisce l'accesso ai dati relativi ai moduli di prenotazione (BookingForm).
* FilterService: Contiene i metodi per applicare filtri, ordinamenti e paginazione sui moduli.

##### Mapping

##### getAllFormRequests (GET)
###### Descrizione:
Questo mapping recupera e visualizza tutte le richieste di moduli, applicando filtri per categorie, stati, tipo di modulo, assegnazione, date e ordinamento.
Gestisce anche la logica di paginazione e il calcolo del numero di nuovi moduli, basato sulla data dell'ultima visita dell'utente.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### UserController
##### Descrizione generale
Il UserController è responsabile della gestione delle interazioni relative agli utenti, in particolare per quanto riguarda la visualizzazione e gestione del profilo utente. Fornisce funzionalità per visualizzare i dettagli del profilo, tra cui il nome, cognome, email, e l'immagine. Inoltre, consente agli utenti di vedere i moduli a loro assegnati, calcolando anche quanti nuovi moduli sono stati inviati dal momento dell'ultima visita.

##### Dipendenze
* UserService: Gestisce le operazioni relative agli utenti, come il recupero dell'utente tramite l'email.
* UserImageDAO: Gestisce l'accesso ai dati relativi alle immagini degli utenti.
* UserDAO: Gestisce l'accesso ai dati degli utenti (come il recupero, salvataggio, e modifica degli utenti).
* BookingFormDAO: Gestisce l'accesso ai dati relativi ai moduli di prenotazione (BookingForm).
* GeneralformDAO: Gestisce l'accesso ai dati dei moduli generali (Generalform).
* PasswordEncoder: Utilizzato per la gestione della codifica delle password.
* InformationformDAO: Gestisce l'accesso ai dati relativi ai moduli informativi (Informationform).
* InformationFormService: Fornisce servizi relativi alla gestione dei moduli informativi, inclusi quelli assegnati a un determinato utente.
* CategoryDAO: Gestisce l'accesso ai dati relativi alle categorie di moduli.

##### Mapping

##### profilePage (GET)
###### Descrizione:
Questo mapping serve a visualizzare il profilo dell'utente autenticato, mostrando informazioni come il nome, il cognome, l'email e l'immagine del profilo. Inoltre, recupera i moduli assegnati all'utente, calcola il numero di nuovi moduli in base all'ultima visita e li visualizza insieme ai dati di profilo.

##### updateHeartbeat (POST)
###### Descrizione:
Il mapping gestisce un aggiornamento del "heartbeat" per l'utente, utilizzato per segnare l'attività dell'utente nel sistema. Quando l'utente invia una richiesta, viene aggiornata la data dell'ultimo accesso dell'utente.

##### updateNameAndSurname (POST)
###### Descrizione:
Questo mapping consente all'utente di aggiornare il proprio nome e cognome nel profilo. Esamina i dati inviati, valida le informazioni e aggiorna il profilo dell'utente.

##### userTasks (GET)
###### Descrizione:
Il mapping userTasks è responsabile della gestione della visualizzazione dei task assegnati all'utente. Viene utilizzato per navigare alla pagina yourTasks, mostrando le informazioni relative ai vari form assegnati all'utente, con la possibilità di applicare filtri e ordinamenti.

##### assignStatus (POST)
###### Descrizione:
Il mapping assignStatus gestisce la modifica dello stato di un Informationform. Viene chiamato quando l'utente desidera aggiornare lo stato di un form informativo.

##### assignStatusBooking (POST)
###### Descrizione:
Il mapping assignStatusBooking gestisce la modifica dello stato di un BookingForm, che è un tipo di form di prenotazione. Funziona in modo simile al metodo precedente, ma applicato ai BookingForm.

##### changePasswordView (GET)
###### Descrizione:
Il mapping changePasswordView gestisce la navigazione verso la pagina in cui l'utente può cambiare la propria password. Viene invocato quando l'utente accede alla pagina di modifica della password.

##### changePassword (POST)
###### Descrizione:
Il mapping changePassword gestisce l'elaborazione della richiesta per cambiare la password dell'utente. Questo metodo viene invocato quando l'utente invia il modulo con le nuove credenziali.

##### isValidPassword
###### Descrizione:
Il metodo isValidPassword si occupa di validare la nuova password dell'utente usando una regex.

##### assignProfileImagePage (GET)
###### Descrizione:
Il mapping assignProfileImagePage gestisce la navigazione alla pagina dove un utente può visualizzare e selezionare un'immagine del profilo.

##### assignProfileImage (POST)
###### Descrizione:
Il mapping assignProfileImage gestisce la logica per assegnare o aggiornare l'immagine del profilo dell'utente.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### AdminController
##### Descrizione generale
La classe AdminController è un controller di Spring MVC che gestisce le operazioni relative alla sezione di amministrazione del sistema, come la visualizzazione e gestione degli utenti. Permette di visualizzare l'elenco degli utenti, gestire la ricerca, il filtro, l'ordinamento, e la paginazione dei risultati.

##### Dipendenze
* UserDAO: Interfaccia che fornisce i metodi per interagire con il database per quanto riguarda gli utenti. È utilizzata per recuperare utenti e verificarne l'esistenza.
* GeneralformDAO: Interfaccia che gestisce la persistenza degli oggetti Generalform. È utilizzata per recuperare tutti i moduli generali nel sistema, che sono utilizzati per calcolare il numero di moduli inviati dopo l'ultima visita di un utente.
* UserService: Servizio che gestisce le operazioni relative agli utenti, come il recupero dei dettagli dell'utente e la modifica dei dati dell'utente.
* FilterService: Servizio che gestisce la logica di filtraggio e ordinamento degli utenti, nonché la gestione della paginazione. Fornisce metodi per filtrare gli utenti in base a una query di ricerca e ordinare i risultati.
* InformationFormService: Servizio che gestisce l'invio e la gestione dei moduli di informazioni. Non è direttamente utilizzato nel metodo listUsers, ma è una parte della logica complessiva dell'applicazione.
* InformationformDAO: Interfaccia per interagire con il database per recuperare oggetti Informationform. Come GeneralformDAO, ma specifico per i moduli di informazioni.
* CategoryDAO: Interfaccia per interagire con il database per la gestione delle categorie. Anche se non viene utilizzato direttamente in questo controller, è parte della gestione complessiva dei dati.
* BookingFormService: Servizio che gestisce le operazioni relative ai moduli di prenotazione. Simile agli altri servizi, ma focalizzato sulle prenotazioni degli utenti.

##### Mapping

##### listUsers (GET)
###### Descrizione:
Questo mapping gestisce le richieste GET per visualizzare una lista di utenti. Può essere configurato con vari parametri per filtrare, ordinare e paginare i risultati.

##### publicProfile (GET)
###### Descrizione:
Mostra il profilo pubblico di un utente, inclusi i dettagli come nome, cognome, email, immagine del profilo e stato ONLINE o OFFLINE.

##### yourTasksPublic (GET)
###### Descrizione:
Visualizza i task pubblici assegnati a un utente, inclusi i moduli generali e di prenotazione. L'amministratore può visualizzare i task assegnati a un utente, applicando filtri per categoria, stato, tipo di modulo e data.

##### assignStatusPublicProfile (POST)
###### Decrizione:
Questo mapping si occupa di aggiornare lo stato di un modulo di tipo Informationform.

##### deleteUser (GET)
###### Descrizione:
Questo mapping permette di visualizzare una pagina di conferma prima di eliminare un utente. Durante il processo di conferma, vengono recuperati tutti i task assegnati all'utente, e vengono filtrati quelli con stato "TO DO" o "IN PROGRESS". Se l'utente ha task in corso, verranno visualizzati prima di procedere con l'eliminazione.

##### deleteUser (POST)
###### Descrizione:
Questo mapping consente di eliminare un utente dal sistema. Quando un amministratore decide di eliminare un utente, il sistema esegue l'operazione e fornisce un feedback sulla riuscita o meno dell'operazione.

##### register (GET)
###### Descrizione:
Questo mapping carica la pagina di registrazione per creare un nuovo utente. È accessibile solo agli amministratori, grazie all'annotazione @PreAuthorize("hasRole('ADMIN')").

##### register (POST)
###### Descrizione:
Questo mapping gestisce la registrazione effettiva di un nuovo utente. Vengono effettuati dei controlli preliminari (verifica se l'email è già in uso e se il formato della password e dell'email è valido), e, se i dati sono corretti, l'utente viene registrato nel sistema.

##### isValidPassword
###### Descrizione:
Questa funzione si occupa di validare la password utilizzando una regular expression. La password deve rispettare determinati criteri, come:

* Essere lunga almeno 8 caratteri
* Contenere una lettera maiuscola
* Contenere un numero
* Contenere un carattere speciale

##### isValidEmail
###### Descrizione:
Questa funzione valida l'email tramite una regular expression. La regex controlla che l'email abbia il formato corretto, ad esempio che contenga un @ e un dominio valido.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### InformationFormController
##### Descrizione generale
Il InformationFormController gestisce diverse funzionalità relative ai moduli di informazioni, tra cui la visualizzazione del modulo, l'assegnazione di utenti ai task, la gestione dello stato dei task, e la rimozione degli utenti dai task. Le operazioni si concentrano sul salvataggio dei dati nei modelli di Generalform e Informationform, l'invio di email ai destinatari, e la gestione dinamica dei task attraverso l'assegnazione di utenti e la modifica dello stato.

##### Dipendenze
* CategoryDAO: Questo DAO viene utilizzato per interagire con il database e recuperare le categorie disponibili. Le categorie vengono utilizzate nel modulo di informazioni per associare ogni modulo a una categoria.
* GeneralformDAO: Questo DAO gestisce le operazioni relative alla tabella dei moduli generali (informazioni di base dell'utente come nome, cognome, email, e categoria).
* InformationformDAO: Questo DAO gestisce le operazioni per i moduli di informazioni veri e propri, che contengono contenuti specifici di un modulo associato a un Generalform.
* InformationFormService: Questo servizio si occupa della logica di invio delle email e delle operazioni aggiuntive, come l'assegnazione degli utenti ai task, la rimozione degli utenti dai task e l'assegnazione dello stato a un task.

##### Mapping

##### informationForm (GET)
###### Descrizione:
Questo mapping gestisce la visualizzazione della pagina del modulo di informazioni. Mostra un modulo con un elenco di categorie predefinite (recuperate tramite categoryDAO), consentendo agli utenti di selezionare una categoria e compilare il modulo. Inoltre, la categoria selezionata viene gestita come una variabile nel modello.

##### submit-form (POST)
###### Descrizione:
Questo mapping gestisce l'invio del modulo di informazioni, raccogliendo dati come nome, cognome, email, categoria e contenuto del modulo, quindi salva questi dati nel database. Inoltre, invia email sia all'amministratore che all'utente che ha inviato il modulo.

##### assignUser (POST)
###### Descrizione:
Questo mapping gestisce l'assegnazione di un utente a un task, utilizzando l'ID del modulo di informazioni (informationFormId) e l'ID dell'utente (userId). L'operazione è gestita tramite il servizio informationFormService.

##### removeUserFromInformationForm (POST)
###### Descrizione:
Questo mapping gestisce la rimozione di un utente da un task, tramite l'ID del modulo di informazioni e l'ID dell'utente.

##### assignStatus (POST)
###### Descrizione:
 Questo mapping consente di aggiornare lo stato di un task (ad esempio "TO DO", "IN PROGRESS", "DONE"), utilizzando l'ID del modulo di informazioni e il nuovo stato.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### BookingFormController
##### Descrizione generale
Il BookingFormController gestisce le operazioni relative alla creazione e gestione dei moduli di prenotazione, consentendo agli utenti di inviare richieste di prenotazione, modificare lo stato dei task, assegnare utenti ai task e rimuoverli. Le informazioni del modulo vengono salvate nel database, e l'invio delle email viene gestito tramite un servizio dedicato. Il controller gestisce anche il flusso di navigazione per visualizzare il modulo di prenotazione, elaborare i dati inviati, e inviare notifiche via email.

##### Dipendenze
* CategoryDAO: Gestisce l'interazione con il database per recuperare le categorie di prenotazione disponibili. Le categorie vengono associate ai moduli di prenotazione.
* GeneralformDAO: Gestisce le operazioni relative alla tabella dei moduli generali (contiene informazioni di base come nome, cognome, email e categoria dell'utente).
* BookingFormDAO: Gestisce le operazioni sui moduli di prenotazione veri e propri, che contengono informazioni relative alla prenotazione (date di check-in e check-out, contenuto della prenotazione, stato del task).
* BookingFormService: Gestisce la logica di invio delle email (sia all'amministratore che di conferma all'utente) e le operazioni sui task, come l'assegnazione degli utenti ai task, la rimozione degli utenti dai task e la gestione dello stato.

##### Mapping

##### bookingForm (GET)
###### Descrizione:
Questo mapping gestisce la visualizzazione della pagina del modulo di prenotazione. Mostra un modulo che consente agli utenti di selezionare una categoria di prenotazione, inserire i propri dati e le date di check-in e check-out.

##### submit-booking (POST)
###### Descrizione:
Questo mapping gestisce l'invio del modulo di prenotazione, raccogliendo dati come nome, cognome, email, categoria, date di check-in e check-out, e il contenuto della prenotazione. Questi dati vengono salvati nel database, e vengono inviate due email: una all'amministratore e una di conferma all'utente.

##### assignUserBooking (POST)
###### Descrizione:
Questo mapping gestisce l'assegnazione di un utente a un task relativo a una prenotazione, utilizzando l'ID del modulo di prenotazione (bookingFormId) e l'ID dell'utente (userId).

##### removeUserFromBookingForm (POST)
###### Descrizione:
Questo mapping gestisce la rimozione di un utente da un task, tramite l'ID del modulo di prenotazione e l'ID dell'utente.

##### assignStatusBooking (POST)
###### Descrizione:
Questo mapping consente di aggiornare lo stato di un task (ad esempio "TO DO", "IN PROGRESS", "DONE") per un modulo di prenotazione.
