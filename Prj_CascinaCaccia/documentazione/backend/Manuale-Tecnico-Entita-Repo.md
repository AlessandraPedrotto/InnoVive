## Entità e DAO del progetto

### Entità

#### User
L'entità User rappresenta un utente registrato nel sistema. Essa contiene le informazioni personali dell'utente, i dettagli di autenticazione e le associazioni con altre entità come ruoli, immagini utente e token per il reset della password. Di seguito sono elencati gli attributi principali:

* id: String, la chiave primaria dell'utente, utilizzata per identificare in modo univoco ogni utente.
* name: String, il nome dell'utente.
* surname: String, il cognome dell'utente.
* email: String, l'indirizzo email dell'utente, utilizzato per l'autenticazione.
* password: String, la password dell'utente, memorizzata in modo sicuro.
* lastSeen: LocalDateTime, la data e l'ora dall'ultima volta che le notifiche per nuovi form sono state controllate.
* state: String (con valore predefinito "OFFLINE"), indica lo stato attuale dell'utente.
* lastAccess: LocalDateTime, la data e l'ora dell'ultimo accesso dell'utente al sistema.
###### Relazioni:
* passwordResetToken: una relazione uno-a-molti con PasswordResetToken, che gestisce i token di reset della password associati all'utente.
* userImage: una relazione molti-a-uno con UserImage, che associa un'immagine all'utente.
* roles: una relazione molti-a-molti con Role, che permette a un utente di avere più ruoli, definendo i permessi e le autorizzazioni dell'utente all'interno del sistema.

#### UserImage
L'entità UserImage rappresenta un'immagine associata al profilo di un utente. Essa memorizza l'identificativo univoco dell'immagine e il percorso del file dove è archiviato il link all'immagine. Di seguito sono elencati gli attributi principali:

* id: Long, la chiave primaria dell'immagine, generata automaticamente tramite la strategia GenerationType.IDENTITY.
* imgPath: String, il percorso del file che contiene il link all'immagine.

#### Role
L'entità Role rappresenta i ruoli che possono essere associati agli utenti all'interno del sistema. Ogni ruolo ha un identificativo univoco (ID) e un nome che descrive il ruolo stesso. La tabella roles include ruoli  Admin e Employee. Di seguito sono elencati gli attributi principali:

* id: String, la chiave primaria del ruolo, utilizzata per identificare univocamente ogni ruolo.
* name: String, il nome del ruolo, che descrive la funzione o le autorizzazioni associate al ruolo ("Admin" e "Employee").

#### PasswordResetToken
L'entità PasswordResetToken rappresenta i token generati quando un utente richiede di resettare la propria password dopo averla dimenticata. Ogni token è associato a un utente specifico e ha una data e ora di scadenza. Di seguito sono elencati gli attributi principali:

* id: int, la chiave primaria del token, generata automaticamente tramite la strategia GenerationType.IDENTITY.
* token: String, il token univoco generato per il reset della password.
* expiryDateTime: LocalDateTime, la data e l'ora in cui il token scade, determinando il periodo di validità per il reset della password.
###### Relazioni:
* user: una relazione molti-a-uno con l'entità User, che associa ogni token a un utente specifico. La relazione è gestita tramite la colonna user_id, che fa riferimento alla chiave primaria dell'utente.

#### Generalform
L'entità Generalform rappresenta le informazioni comuni condivise tra le entità BookingForm e InformationForm. Centralizzando questi campi comuni, si evita la duplicazione dei dati nelle tabelle del database. Di seguito sono elencati gli attributi principali:

* id: String, la chiave primaria del modulo generale, utilizzata per identificare univocamente ogni record.
* name: String, il nome della persona che ha inviato il modulo.
* surname: String, il cognome della persona che ha inviato il modulo.
* email: String, l'indirizzo email della persona che ha inviato il modulo.
* submissionDate: LocalDateTime, la data e l'ora in cui il modulo è stato inviato, impostata automaticamente al momento della creazione del modulo.
###### Relazioni:
* category: una relazione molti-a-uno con l'entità Category, che associa ogni modulo generale a una categoria specifica.
* informationForms: una relazione uno-a-molti con l'entità InformationForm, che rappresenta tutti i moduli informativi associati al modulo generale.
* bookingForms: una relazione uno-a-molti con l'entità BookingForm, che rappresenta tutti i moduli di prenotazione associati al modulo generale.

#### Informationform
L'entità Informationform rappresenta le parti specifiche di un modulo che sono uniche per i dettagli informativi, distinguendosi dall'entità BookingForm. Questa entità contiene i campi aggiuntivi e le relazioni non condivise con Generalform, permettendo flessibilità e separazione delle preoccupazioni. Di seguito sono elencati gli attributi principali:

* id: String, la chiave primaria del modulo informativo, utilizzata per identificare univocamente ogni modulo.
* content: String, il contenuto del modulo informativo, che contiene le informazioni specifiche da raccogliere.
* status: String, lo stato del modulo informativo.
###### Relazioni:
* generalform: una relazione molti-a-uno con l'entità Generalform, che associa ogni modulo informativo a un modulo generale. Questo campo è utilizzato per collegare il modulo informativo al modulo generale da cui proviene.
* assignedUser: una relazione molti-a-uno con l'entità User, che rappresenta l'utente a cui è assegnato il modulo informativo. La relazione è gestita tramite la colonna user_id, che fa riferimento alla chiave primaria dell'utente.

#### BookingForm
L'entità BookingForm rappresenta le parti di un modulo specifiche per i dettagli relativi alla prenotazione, distinguendosi dall'entità InformationForm. Essa include campi per la gestione di dati specifici di una prenotazione, come le date di check-in e check-out, pur condividendo dati comuni con l'entità Generalform. Di seguito sono elencati gli attributi principali:

* id: String, la chiave primaria del modulo di prenotazione, utilizzata per identificare univocamente ogni modulo.
* content: String, il contenuto del modulo di prenotazione, che raccoglie dettagli specifici sulla prenotazione stessa.
* status: String, lo stato del modulo di prenotazione.
* checkIn: Date, la data di check-in per la prenotazione.
* checkOut: Date, la data di check-out per la prenotazione.

###### Relazioni:
* generalform: una relazione molti-a-uno con l'entità Generalform, che associa ogni modulo di prenotazione a un modulo generale. Questo campo collega il modulo di prenotazione al modulo da cui proviene.
* assignedUser: una relazione molti-a-uno con l'entità User, che rappresenta l'utente a cui è assegnato il modulo di prenotazione. La relazione è gestita tramite la colonna user_id, che fa riferimento alla chiave primaria dell'utente.

#### Category
L'entità Category rappresenta una tabella contenente tutte le possibili categorie che possono essere associate ai moduli di prenotazione (BookingForm) e ai moduli informativi (InformationForm). Ogni categoria ha un identificatore univoco (ID) e un nome. Di seguito sono elencati gli attributi principali:

* id: String, la chiave primaria della categoria, utilizzata per identificare univocamente ogni categoria.
* name: String, il nome della categoria, che descrive il tipo di modulo.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### DAO

#### UserDAO
L'interfaccia UserDAO estende JpaRepository per fornire operazioni CRUD standard per l'entità User. Questa interfaccia facilita l'interazione con l'entità User, offrendo vari metodi per eseguire operazioni CRUD e query personalizzate basate su attributi specifici dell'utente. Di seguito sono elencati i metodi principali e le funzionalità:

* findAll(): restituisce una lista di tutti gli utenti nel database.
* existsById(String Id): verifica se esiste un utente con l'ID specificato.
* existsByEmail(String email): verifica se esiste un utente con l'email specificata.
* findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(String name, String surname): trova utenti il cui nome e cognome contengono i valori specificati, ignorando il maiuscolo/minuscolo.
* findByEmailContainingIgnoreCase(String email): trova utenti il cui indirizzo email contiene il valore specificato, ignorando il maiuscolo/minuscolo.
* findByNameContainingIgnoreCase(String name): trova utenti il cui nome contiene il valore specificato, ignorando il maiuscolo/minuscolo.
* findBySurnameContainingIgnoreCase(String surname): trova utenti il cui cognome contiene il valore specificato, ignorando il maiuscolo/minuscolo.
* findByEmail(String email): trova un utente tramite la sua email.
* findByName(String name): trova un utente tramite il suo nome.
* findById(String Id): trova un utente tramite il suo ID.
* findUsersByStateAndLastAccessBefore(String state, LocalDateTime cutoffTime): esegue una query per trovare gli utenti che hanno uno stato specifico e un'ultima accesso precedente a un determinato orario di scadenza.

#### UserImageDAO
L'interfaccia UserImageDAO estende JpaRepository per fornire operazioni CRUD standard per l'entità UserImage. Questo repository consente di eseguire operazioni di salvataggio, eliminazione e interrogazione delle immagini utente nel database. Di seguito è riportata una descrizione delle funzionalità principali:

* findAll(): restituisce una lista di tutte le immagini utente presenti nel database.

#### RoleDAO
L'interfaccia RoleDAO estende JpaRepository per fornire operazioni CRUD standard per l'entità Role. Questo repository consente di eseguire operazioni di salvataggio, eliminazione e interrogazione dei ruoli nel database. Di seguito sono riportate le funzionalità principali:

* findByName(String name): restituisce un'opzione contenente un User associato al nome del ruolo specificato. Questo metodo consente di recuperare un ruolo in base al nome.

#### TokenDAO
L'interfaccia TokenDAO estende JpaRepository per fornire operazioni CRUD standard per l'entità PasswordResetToken. Questo repository consente di eseguire operazioni di salvataggio, eliminazione e interrogazione dei token di reset password nel database. Di seguito sono riportate le funzionalità principali:

* findByUser(User user): restituisce un'opzione contenente un PasswordResetToken associato a un determinato utente.
* findByToken(String token): restituisce il PasswordResetToken associato al token di reset password fornito.
* delete(PasswordResetToken token): elimina il token di reset password specificato dal database.
* findByExpiryDateTimeBefore(LocalDateTime expiryDateTime): restituisce una lista di PasswordResetToken la cui data di scadenza è precedente alla data e ora fornita.

#### GeneralformDAO
L'interfaccia GeneralformDAO è il repository per l'accesso all'entità Generalform nel database. Estende JpaRepository per fornire operazioni CRUD standard e include metodi personalizzati per recuperare i record Generalform in base a criteri specifici.

* findByEmailAndCategoryAndNameAndSurname(String email, Category category, String name, String surname): restituisce una lista di Generalform che corrispondono a un'email, categoria, nome e cognome specificati.
* findAll(Sort sort): restituisce tutti i Generalform, ordinati in base ai criteri di ordinamento forniti.
* findAllById(Iterable ids): restituisce tutti i Generalform che corrispondono agli ID specificati.
* findAllByInformationForms_AssignedUser(User user): restituisce tutti i Generalform associati a un determinato utente tramite la relazione con Informationform.

#### InformationformDAO
L'interfaccia InformationformDAO estende JpaRepository per fornire operazioni CRUD standard sull'entità Informationform. Questa interfaccia consente l'interazione con l'entità Informationform, supportando operazioni di base come salvataggio, eliminazione e query sui moduli di informazioni.

* findById(String id): restituisce un Optional<Informationform> che rappresenta il modulo di informazioni corrispondente all'ID fornito. Se il modulo esiste, verrà restituito, altrimenti il risultato sarà vuoto.
* findByAssignedUser(User assignedUser): restituisce una lista di Informationform assegnati a un determinato utente. Permette di recuperare tutti i moduli di informazioni associati a un utente specifico.

#### BookingFormDAO
L'interfaccia BookingFormDAO estende JpaRepository per fornire operazioni CRUD standard sull'entità BookingForm. Questa interfaccia consente l'interazione con l'entità BookingForm, offrendo operazioni di base come salvataggio, eliminazione e query sui moduli di prenotazione.

* findById(String id): restituisce un Optional<BookingForm> che rappresenta il modulo di prenotazione corrispondente all'ID fornito. Se il modulo esiste, verrà restituito, altrimenti il risultato sarà vuoto.
* findByAssignedUser(User assignedUser): restituisce una lista di BookingForm assegnati a un determinato utente. Permette di recuperare tutti i moduli di prenotazione associati a un utente specifico.

#### CategoryDAO
L'interfaccia CategoryDAO estende JpaRepository per fornire operazioni CRUD standard sull'entità Category. Oltre alle operazioni di base, include metodi di query personalizzati per cercare categorie in base a:

* findByName(String name): restituisce una lista di Category che corrispondono al nome fornito.
* findById(String id): restituisce un Optional<Category> per una categoria identificata dal suo ID. Se la categoria esiste, viene restituita; altrimenti, il risultato sarà vuoto.
