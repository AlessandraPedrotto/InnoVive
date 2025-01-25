## Servizzi
### UserService
##### Descrizione Generale
UserService è una classe di servizio che gestisce tutte le operazioni relative agli utenti all'interno del sistema. Queste operazioni comprendono la registrazione di un nuovo utente, la gestione delle credenziali di accesso, la modifica dei dettagli dell'utente (come nome, cognome e immagine del profilo), e la gestione dei ruoli. Inoltre, fornisce funzionalità per il recupero e l'aggiornamento delle informazioni utente, la gestione della password, e la verifica dell'uso delle email già registrate.
La classe implementa l'interfaccia UserDetailsService di Spring Security, consentendo l'autenticazione degli utenti e la gestione della sessione utente. L'oggetto UserService è anche responsabile della gestione degli stati utente (ad esempio, stato online/offline) e del controllo dell'inattività, aggiornando lo stato degli utenti e gestendo il logout automatico per gli utenti inattivi. Inoltre, fornisce metodi per recuperare le immagini del profilo, assegnare o aggiornare l'immagine del profilo e gestire le associazioni con i moduli di informazioni e prenotazione.
##### Dipendenze

* UserDAO: Interfaccia del repository per l'accesso al database degli utenti.
* UserImageDAO: Interfaccia del repository per l'accesso al database delle immagini degli utenti.
* PasswordEncoder: Encoder per la gestione delle password sicure.
* BookingFormService: Servizio per gestire i moduli di prenotazione (Lazy-loaded).
* SessionRegistry: Registry delle sessioni per la gestione delle sessioni attive (Lazy-loaded).
* InformationFormService: Servizio per gestire i moduli delle informazioni (Lazy-loaded).
##### Metodi

##### updateUserLastAccess
###### Descrizione:
Questo metodo aggiorna lo stato dell'utente e l'orario dell'ultimo accesso nel database, utilizzando l'email per identificare l'utente. Viene aggiornato il campo lastAccess dell'utente con l'orario corrente.

##### markInactiveUsersOffline
###### Descrizione:
Questo metodo controlla periodicamente (ogni 2 minuti) gli utenti che sono marcati come "ONLINE" ma non hanno aggiornato il loro orario dell'ultimo accesso negli ultimi 10 minuti. Se vengono trovati utenti inattivi, il loro stato viene aggiornato a "OFFLINE" e vengono disconnessi dal sistema.

##### logoutUserByUsername
###### Descrizione:
Questo metodo gestisce la disconnessione forzata di un utente dal sistema, invalidando tutte le sue sessioni attive. Viene utilizzato per "logout" gli utenti inattivi, esaminando tutte le sessioni attive e scadendo quelle dell'utente identificato tramite il suo username.

##### register
###### Descrizione:
Questo metodo gestisce la registrazione di un nuovo utente. Durante la registrazione:

* Viene generato un nuovo ID per l'utente.
* La password dell'utente viene codificata.
* Viene assegnata l'immagine del profilo predefinita all'utente.
* Viene assegnato un ruolo (di default, "ROLE_EMPLOYEE").
* L'utente viene salvato nel database insieme ai suoi ruoli e immagine del profilo.

##### assignRoles
###### Descrizione:
Questo metodo consente di assegnare ruoli a un utente esistente. Il numero di ruoli deve essere compreso tra 1 e 2. Se il numero di ruoli è maggiore o minore, viene lanciata un'eccezione. Dopo aver verificato il numero di ruoli, i ruoli vengono assegnati all'utente e l'utente viene salvato nel database.

##### loadUserByUsername
###### Descrizione:
Questo metodo viene implementato per soddisfare l'interfaccia UserDetailsService di Spring Security. La funzione principale è caricare un utente in base al suo indirizzo email:

* Recupera l'utente dal database.
* Se l'utente non viene trovato, viene lanciata un'eccezione UsernameNotFoundException.
* Se l'utente ha dei ruoli assegnati, questi vengono mappati in oggetti GrantedAuthority necessari per la gestione della sicurezza in Spring.
* Lo stato dell'utente viene aggiornato a "ONLINE" e il suo ultimo accesso viene registrato nel database.
* Ritorna un oggetto UserDetails contenente le informazioni dell'utente, inclusi i ruoli come autorità di sicurezza.

##### isEmailAlreadyInUse
###### Descrizione:
Questo metodo verifica se un'email è già in uso da un altro utente nel sistema. Viene fatto un controllo confrontando l'email fornita con quelle già presenti nel database. Se l'email è già associata a un altro utente, il metodo ritorna true, altrimenti false.

##### getUserByEmail
###### Descrizione:
Recupera l'utente associato all'email dell'utente autenticato. Questo metodo usa l'oggetto principal, che rappresenta l'utente attualmente autenticato, per estrarre l'email e cercare l'utente nel database.

##### getUserById
###### Descrizione:
Recupera un utente utilizzando il suo ID. Se l'utente con l'ID fornito non esiste, viene lanciata un'eccezione RuntimeException.

##### deleteUserById
###### Descrizione:
Questo metodo rimuove un utente dal sistema, cancellando prima tutte le associazioni con altri oggetti, come i moduli di informazioni e di prenotazione, e poi eliminando l'utente dal database. La procedura avviene in più fasi:

* Recupera l'utente dal database utilizzando l'ID fornito.
* Rimuove le associazioni con i moduli di informazioni e di prenotazione, impostando a null il riferimento all'utente nei moduli associati.
* Rimuove i ruoli dell'utente.
* Salva l'utente con i ruoli rimossi.
* Elimina l'utente dal database. 

##### getUserRoles
###### Descrizione:
Recupera i ruoli associati a un utente dato il suo ID. Se l'utente esiste nel database, restituisce una lista dei suoi ruoli. Se l'utente non viene trovato, viene lanciata un'eccezione EntityNotFoundException.

##### changePassword
###### Descrizione:
Permette di cambiare la password di un utente, previa verifica che la vecchia password fornita corrisponda a quella attuale. Se la password vecchia non è corretta, viene lanciata un'eccezione. La nuova password viene quindi codificata e aggiornata nel database.

##### getAllUserImg
###### Descrizione:
Recupera tutte le immagini utente disponibili nel database. La lista restituita può essere utilizzata per la gestione o visualizzazione delle immagini degli utenti.

##### assignOrUpdateProfileImage
###### Descrizione:
Assegna o aggiorna l'immagine del profilo di un utente specificato dal suo ID. Recupera l'immagine dal database utilizzando l'ID fornito e la associa all'utente. Successivamente, l'utente aggiornato viene salvato nel database.

##### getAllUsers
###### Descrizione:
Recupera tutti gli utenti dal database. Questo metodo può essere utilizzato per ottenere una lista di tutti gli utenti registrati nel sistema.

##### updateNameAndSurname
###### Descrizione:
Aggiorna il nome e il cognome di un utente identificato dal suo ID. I nuovi valori di vengono salvati nel database.

### ForgotPasswordService
##### Descrizione Generale
ForgotPasswordService gestisce tutte le operazioni relative al processo di reset della password. Questo servizio è incaricato di generare un token per il reset della password, inviare un'email all'utente con il link per il reset, verificare se un token di reset è scaduto, e recuperare un token utilizzando il suo valore. Inoltre, gestisce la pulizia periodica dei token di reset scaduti per mantenere il sistema sicuro. Il servizio utilizza il meccanismo di invio di email in formato HTML, includendo link personalizzati e informazioni aggiuntive per un'esperienza utente migliore.

##### Dipendenze
* TokenDAO: Gestisce l'accesso e le operazioni sui token di reset della password nel database.
* JavaMailSender: Fornisce il supporto per inviare email in formato HTML (incluso il reset link) agli utenti.

##### Metodi
##### sendResetEmail
###### Descrizione:
Questo metodo genera un token di reset per l'utente, crea il link di reset della password e invia un'email all'utente con il link di reset. L'email è formattata in HTML e include il nome e cognome dell'utente, un link per il reset della password, e un messaggio che avvisa che il link scadrà in 15 minuti.

##### sendHtmlEmail
###### Descrizione:
Questo metodo invia un'email formattata in HTML all'utente. Viene utilizzato dal metodo sendResetEmail per inviare l'email contenente il link di reset della password.

##### sendResetEmail
###### Descrizione:
Questo metodo genera un token di reset per l'utente, crea il link di reset della password e invia un'email all'utente con il link di reset. L'email è formattata in HTML e include il nome e cognome dell'utente, un link per il reset della password, e un messaggio che avvisa che il link scadrà in 15 minuti.

##### sendHtmlEmail
###### Descrizione:
Questo metodo invia un'email formattata in HTML all'utente. Viene utilizzato dal metodo sendResetEmail per inviare l'email contenente il link di reset della password.

##### generateResetToken
###### Descrizione:
Questo metodo genera un token univoco di reset della password per l'utente specificato. Il token viene associato all'utente e ha una scadenza di 15 minuti. Se l'utente ha già un token di reset attivo, il vecchio token viene rimosso prima di crearne uno nuovo. Il metodo genera anche un URL di reset che contiene il token generato.

##### deleteAndFlushPasswordResetToken
###### Descrizione:
Questo metodo elimina un token di reset della password dal database e "flush" (applica) le modifiche, garantendo che il token non venga più utilizzato.

### FilterService
##### Descrizione Generale
Il servizio FilterService offre una serie di metodi per filtrare, ordinare e paginare gli utenti nel database, semplificando la gestione e la visualizzazione di grandi quantità di dati. Fornisce funzionalità per recuperare tutti gli utenti, ordinare la lista in base al cognome, cercare gli utenti per email, nome o cognome, e suddividere i risultati in pagine. L'uso di questi metodi consente di ottenere informazioni in modo efficiente e mirato, migliorando la navigabilità e l'usabilità dell'applicazione.

##### Dipendenze
* UserDAO: Gestisce l'accesso al database per le operazioni relative agli utenti, come il recupero dell'elenco di tutti gli utenti.

##### Metodi
##### getAllUsers
###### Descrizione:
Recupera tutti gli utenti presenti nel database. Questo metodo restituisce una lista completa di tutti gli utenti, utile per operazioni che richiedono l'accesso a tutti i dati disponibili.

##### sortUsersBySurname
###### Descrizione:
Ordina una lista di utenti in base al cognome, in ordine crescente o decrescente, a seconda del valore del parametro ascending. Se ascending è true, l'ordinamento sarà crescente, altrimenti sarà decrescente.

##### searchUsers
###### Descrizione:
Esegue la ricerca di utenti in base alla query fornita. Se la query contiene uno spazio, viene trattata come una ricerca per nome completo (nome + cognome), altrimenti viene eseguita una ricerca per nome, cognome o email.

##### handleSinglePartQuery
###### Descrizione:
Gestisce le query che contengono un solo termine (nome, cognome, o email). La ricerca avviene separatamente per ogni campo (email, nome, cognome) e i risultati vengono combinati ed eliminati i duplicati.

##### getPaginatedList
###### Descrizione:
Restituisce una sotto-lista (pagina) della lista originale in base al numero della pagina e al numero di risultati per pagina. Gestisce anche i casi in cui la pagina o la dimensione siano valori non validi, e restituisce null per liste vuote.

##### getTotalPages
###### Descrizione:
Calcola il numero totale di pagine richieste per paginare l'elenco, dato il numero di risultati per pagina. Utilizza la divisione con arrotondamento per determinare quante pagine siano necessarie.

##### getStartPage
###### Descrizione:
Calcola il numero di pagina di inizio per un blocco di pagine. Utile per la navigazione in blocchi di pagine, ad esempio, per saltare a un blocco specifico.

##### getEndPage
###### Descrizione:
Calcola il numero di pagina finale per un blocco di pagine, garantendo che non superi il numero totale di pagine disponibili.

##### sortBySurname
###### Descrizione:
Ordina una lista di oggetti Generalform in base al cognome, in ordine crescente o decrescente, a seconda del valore del parametro ascending.

##### sortBySubmissionDate
###### Descrizione:
Ordina una lista di oggetti Generalform in base alla data di invio, in ordine crescente o decrescente, a seconda del valore del parametro ascending.

##### sortList
###### Descrizione:
Metodo generico per ordinare una lista di oggetti di qualsiasi tipo, utilizzando un comparatore personalizzato e l'ordine specificato (crescente o decrescente).

##### filterFormsByCategories
###### Descrizione:
Filtra una lista di oggetti Generalform restituendo solo quelli il cui categoryId è presente nella lista fornita.

##### filterFormsByStatuses
###### Descrizione:
Filtra una lista di oggetti Generalform restituendo solo quelli il cui stato di Informationform o Bookingform corrisponde a uno degli stati nella lista fornita. La ricerca avviene sia nei moduli Informationform che Bookingform correlati a ciascun Generalform.

##### filterByInformationForm
###### Descrizione:
Filtra una lista di oggetti Generalform restituendo solo quelli che hanno uno o più Informationform associati.

##### filterByBookingForm
###### Descrizione:
Filtra una lista di oggetti Generalform restituendo solo quelli che hanno uno o più Bookingform associati.

##### filterByAssignment
###### Descrizione:
Filtra una lista di oggetti Generalform in base alla presenza o assenza di un modulo correlato (Informationform o Bookingform) assegnato a un utente. Se isAssigned è true, vengono restituiti solo i moduli con moduli assegnati. Se isAssigned è false, vengono restituiti solo i moduli senza moduli assegnati.

##### filterByDateRange
###### Descrizione:
Filtra una lista di oggetti Generalform restituendo solo quelli la cui data di sottomissione (submissionDate) rientra nell'intervallo di date specificato.

### InformationFormService
##### Descrizione generale
La classe InformationFormService gestisce la logica di business relativa ai moduli di informazioni. In particolare, si occupa di:

* Inviare notifiche email all'amministratore e all'utente quando viene inviato un nuovo modulo.
* Gestire l'assegnazione e la rimozione degli utenti dai moduli di informazioni.
* Aggiornare lo stato dei moduli di informazioni.
In sintesi, questa classe gestisce le operazioni relative ai moduli di informazioni, garantendo la corretta comunicazione tramite email e la gestione degli utenti associati a ciascun modulo.

##### Dipendenze
* JavaMailSender: Utilizzato per inviare email, in particolare per inviare notifiche agli amministratori quando viene inviato un nuovo modulo di informazioni.
* InformationformDAO: Responsabile dell'interazione con il database per le operazioni sui moduli di informazioni.
* UserService: Gestisce le operazioni relative agli utenti, come l'assegnazione e la gestione delle credenziali.
* GeneralformDAO: Interagisce con il database per operazioni generali sui moduli (Generalform).
* UserDAO: Gestisce le operazioni dirette sugli utenti nel database.

##### Metodi
##### sendEmailToAdmin
###### Descrizione:
Questo metodo invia un'email all'amministratore per informarlo di una nuova sottomissione del modulo di informazioni. L'email contiene dettagli sul modulo inviato, come il nome e il cognome dell'utente, la sua email, la categoria scelta, e il contenuto del messaggio.

##### sendConfirmationEmail
###### Descrizione:
Il metodo sendConfirmationEmail invia un'email di conferma all'utente dopo che ha inviato un modulo di richiesta informazioni. L'email contiene un riepilogo dei dati inviati nel modulo, come il nome, il cognome, l'email, la categoria selezionata e il contenuto del messaggio. Questo fornisce all'utente una conferma visibile della sua richiesta.

##### sendAssignedTaskEmail
###### Descrizione:
Il metodo sendAssignedTaskEmail invia un'email a un dipendente a cui è stato assegnato un nuovo compito. L'email contiene i dettagli della richiesta del cliente, come nome, cognome, email, categoria e messaggio, insieme a un link che permette al dipendente di accedere al proprio account per aggiornare lo stato della richiesta.

##### sendHtmlEmail
###### Descrizione:
Il metodo sendHtmlEmail si occupa dell'invio dell'email. Utilizza:

* MimeMessage per creare il messaggio email.
* MimeMessageHelper per gestire i dettagli dell'email, come l'indirizzo del destinatario, il soggetto e il corpo HTML.
* Il metodo invia l'email usando mailSender.send(message). In caso di errore, viene stampato un messaggio di errore nel log e l'eccezione viene rilanciata.

##### assignUser
###### Descrizione:
Il metodo assignUser ha il compito di assegnare un utente a un modulo di informazioni (un compito) specifico. Se il modulo esiste e c'è già un utente assegnato, il metodo rimuove l'assegnazione precedente e assegna il nuovo utente. Una volta completata l'assegnazione, viene inviata un'email di notifica all'utente assegnato, con i dettagli relativi al compito.

##### sendRemovedTaskEmail
###### Descrizione: 
Il metodo sendRemovedTaskEmail ha l'obiettivo di inviare un'email a un utente per notificargli che è stato rimosso da un task (form di informazioni). L'email fornisce i dettagli relativi al task e invita l'utente a contattare l'amministratore in caso di errori.

##### removeUser
###### Descrizione:
Il metodo removeUser rimuove un utente da un modulo di informazioni specifico. Controlla se l'utente è effettivamente assegnato al modulo e, in tal caso, lo rimuove e invia un'email di notifica all'utente informandolo della rimozione.

##### List<Generalform> getAssignedFormsByUser(String userId)
###### Descrizione:
Il metodo getAssignedFormsByUser recupera tutti i moduli di informazioni (tasks) a cui un determinato utente è assegnato. Restituisce una lista di entità Generalform correlate ai moduli assegnati.

##### assignStatus
###### Descrizione:
Il metodo assignStatus assegna uno stato specifico a un modulo di informazioni (task). Imposta lo stato del modulo e salva il modulo aggiornato nel database.

##### saveInformationForm
###### Descrizione:
Il metodo saveInformationForm salva un modulo di informazioni (task) nel database. Questo metodo persiste l'entità Informationform fornita come parametro.

### BookingFormService
##### Descrizione generale
Il servizio BookingFormService gestisce la logica aziendale relativa ai moduli di prenotazione, supportando operazioni come la validazione delle date di prenotazione, l'invio di notifiche via email e la gestione degli utenti associati ai moduli. Questo servizio consente di validare e inviare correttamente i moduli di prenotazione, garantendo che tutte le operazioni siano eseguite senza errori. La classe interagisce con diversi componenti del sistema, come i DAO per il recupero dei dati e il sistema di invio email, per supportare la funzionalità completa di gestione delle prenotazioni.

##### Dipendenze
* BookingFormDAO: Gestisce l'accesso al database per le operazioni relative ai moduli di prenotazione, come il salvataggio e il recupero dei dati.
* JavaMailSender: Gestisce l'invio di email, utilizzato per inviare notifiche a utenti e amministratori riguardo alle prenotazioni.
* UserService: Gestisce la logica relativa agli utenti, come la creazione, la gestione e il recupero degli utenti nel sistema.
* GeneralformDAO: Gestisce l'accesso al database per i moduli generali, potenzialmente legati a moduli di prenotazione.
* UserDAO: Gestisce l'accesso al database per la gestione dei dati degli utenti.

##### Metodi
##### validateAndSubmitBookingForm
###### Descrizione:
Questo metodo valida le date di check-in e check-out fornite per una prenotazione. Verifica che le date siano almeno un giorno dopo la data corrente, che il check-out non avvenga prima del check-in e che entrambe le date siano valide. Se tutte le condizioni sono soddisfatte, la prenotazione può essere considerata valida, altrimenti vengono restituiti messaggi di errore per ogni problema riscontrato.

##### isSameDay
###### Descrizione:
Questo metodo ausiliario confronta due date per determinare se rappresentano lo stesso giorno, ignorando l'ora. Viene utilizzato nel metodo di validazione per assicurarsi che il check-in e il check-out non siano sulla stessa giornata, se non è previsto dal sistema.

##### sendEmailToAdmin
###### Descrizione:
Questo metodo invia un'email all'amministratore con i dettagli di una nuova prenotazione. Viene formattato un messaggio HTML contenente tutte le informazioni fornite nel modulo di prenotazione, come nome, cognome, email, categoria della prenotazione, date di check-in e check-out e il contenuto del messaggio. L'email è inviata utilizzando il sistema di invio email configurato nella classe.

##### sendConfirmationEmail
###### Descrizione: 
Il metodo sendConfirmationEmail invia un'email di conferma all'utente che ha inviato il modulo di prenotazione. L'email conferma il successo della richiesta e fornisce un riepilogo dei dettagli del modulo inviato, come nome, cognome, email, categoria, periodo di prenotazione e messaggio.

##### sendAssignedTaskEmail
###### Descrizione:
Il metodo sendAssignedTaskEmail invia un'email a un dipendente per informarlo che gli è stato assegnato un nuovo compito. L'email contiene i dettagli relativi alla prenotazione del cliente, incluse le informazioni di contatto del cliente e i dettagli del compito.

##### assignUser
###### Descrizione:
Il metodo assignUser assegna un utente a un modulo di prenotazione (task) specifico. Se il modulo di prenotazione ha già un utente assegnato, l'utente precedente viene rimosso prima di assegnare il nuovo utente. Successivamente, viene inviato un messaggio email al nuovo utente assegnato per informarlo del nuovo compito.

##### sendRemovedTaskEmail
###### Descrizione:
Il metodo sendRemovedTaskEmail invia un'email a un utente per informarlo che è stato rimosso da un compito (modulo di prenotazione). L'email fornisce i dettagli relativi al compito da cui l'utente è stato rimosso, come il nome del cliente, la categoria del compito, le date di check-in e check-out e il contenuto del modulo.

##### removeUser
###### Descrizione:
Il metodo removeUser rimuove un utente da un modulo di prenotazione specifico. Il metodo verifica se l'utente è effettivamente assegnato al modulo di prenotazione. Se l'utente è assegnato, il metodo lo rimuove e invia un'email di notifica all'utente informandolo della rimozione.

##### getAssignedFormsByUserBooking
###### Descrizione:
Il metodo getAssignedFormsByUserBooking recupera tutti i moduli di prenotazione assegnati a un determinato utente. Restituisce una lista di moduli Generalform associati ai moduli di prenotazione a cui l'utente è assegnato.

##### assignStatus
###### Descrizione:
Il metodo assignStatus assegna uno stato specifico a un modulo di prenotazione e salva l'aggiornamento nel database.

##### saveBookingForm
###### Descrizione:
Il metodo saveBookingForm salva un'entità BookingForm nel database.