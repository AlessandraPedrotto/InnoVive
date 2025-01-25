## Configurazione della Sicurezza in Spring
#### CustomSessionExpiredStrategy
La classe CustomSessionExpiredStrategy implementa l'interfaccia SessionInformationExpiredStrategy, definendo il comportamento da adottare quando una sessione dell'utente scade.

###### Descrizione:
Questa implementazione ascolta l'evento di scadenza della sessione e, quando rileva che la sessione è scaduta, esegue un redirect dell'utente alla pagina di login, con un messaggio di errore personalizzato che indica che la sessione è scaduta.

###### Dettagli della Implementazione:
Metodo onExpiredSessionDetected:
* Quando l'evento di scadenza della sessione è rilevato, il metodo onExpiredSessionDetected viene chiamato.
* Il metodo accede alla richiesta HTTP (HttpServletRequest request) e alla risposta HTTP (HttpServletResponse response).
* Viene quindi eseguito un redirect alla pagina di login (/login) con un parametro errorSession=sessionExpired, che segnala l'errore di scadenza della sessione.
###### Funzionalità:
Gestione personalizzata della scadenza della sessione: invece di una semplice chiusura della sessione o un logout automatico, l'utente viene informato con un messaggio chiaro quando la sessione scade, migliorando l'esperienza dell'utente.
###### Come utilizzarlo:
La classe deve essere configurata come parte della strategia di gestione della sessione in Spring Security, dove è necessario specificare questa classe per gestire l'evento di scadenza della sessione in modo personalizzato.
### SecurityConfig
La classe SecurityConfig configura la sicurezza dell'applicazione utilizzando Spring Security. Ecco una panoramica di ciò che fa, senza il codice:

###### 1. Cifratura delle Password
* La classe definisce un meccanismo per cifrare le password degli utenti prima che vengano salvate nel database. Viene utilizzato un algoritmo di cifratura robusto, BCrypt, che protegge le credenziali da attacchi come il "brute force".
###### 2. Configurazione della Sicurezza HTTP
* Disabilita la protezione CSRF, che, in alcune applicazioni, può essere disabilitata per semplificare la gestione della sicurezza (anche se normalmente non è raccomandato per ambienti di produzione).
* Definisce quali URL sono accessibili senza autenticazione (come le pagine di login, registrazione, risorse statiche, ecc.) e quali richiedono l'autenticazione dell'utente (ad esempio le pagine protette da ruolo, come quelle amministrative).
* La configurazione delle rotte include anche regole per l'accesso in base ai ruoli degli utenti. Per esempio, solo gli utenti con il ruolo "ADMIN" possono accedere a determinati URL.
###### 3. Comportamento del Login
* La configurazione personalizza il comportamento di login definendo una pagina di login personalizzata.
* Quando un utente si autentica con successo, viene creata una sessione che memorizza informazioni sull'utente, come il nome, l'ID e il ruolo. Inoltre, viene creato un cookie contenente l'email dell'utente.
* In caso di errore durante il login (ad esempio, credenziali errate), viene gestita una redirezione con un messaggio di errore.
###### 4. Comportamento del Logout
* Quando un utente effettua il logout, la sessione viene invalidata e tutti i cookie legati alla sessione vengono cancellati, impedendo accessi non autorizzati.
* Dopo il logout, l'utente viene reindirizzato alla pagina principale dell'applicazione.
* Inoltre, quando un utente esce, il suo stato viene aggiornato, per esempio, marcandolo come "OFFLINE".
###### 5. Gestione delle Sessioni
La configurazione gestisce la sessione utente in modo sicuro:
* Scadenza della sessione: Se la sessione scade, l'utente viene reindirizzato alla pagina di login con un messaggio che indica che la sessione è scaduta.
* Numero massimo di sessioni simultanee: Si limita a una sola sessione per utente per evitare che due sessioni vengano aperte simultaneamente con lo stesso account.
* Protezione della sessione: In caso di attacchi di session fixation, la sessione viene migrata a una nuova sessione, mantenendo l'utente sicuro.
###### 6. Gestione delle Sessioni Attive
* Viene utilizzato un sistema di SessionRegistry per tracciare le sessioni attive degli utenti, assicurandosi che le informazioni sulle sessioni siano correttamente gestite e che non ci siano conflitti.
###### In sintesi:
La classe SecurityConfig definisce una serie di misure di sicurezza che riguardano:

* La gestione delle credenziali (cifratura delle password).
* La gestione dell'accesso alle diverse risorse in base ai ruoli e alla sessione dell'utente.
* La personalizzazione del comportamento di login/logout, inclusa la gestione delle sessioni e la protezione contro l'accesso simultaneo con lo stesso account.
