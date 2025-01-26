## main.js
## Functions

<dl>
<dt><a href="#toggleMenu">toggleMenu()</a> ⇒ <code>void</code></dt>
<dd><p>toggle function to manage responsive navbar menu</p>
</dd>
<dt><a href="#toggleChatbot">toggleChatbot()</a> ⇒ <code>void</code></dt>
<dd><p>toggle the visibility of the chatbot container</p>
</dd>
<dt><a href="#toggleLanguage">toggleLanguage(language)</a></dt>
<dd><p>Toggle between languages (Italian or English)</p>
</dd>
<dt><a href="#handleQuestionClick">handleQuestionClick(question, buttonsContainer)</a> ⇒ <code>void</code></dt>
<dd><p>function to handle the clicked question</p>
</dd>
<dt><a href="#changeLanguage">changeLanguage(lang)</a></dt>
<dd><p>Changes the language of the application and loads the corresponding translations.
The selected language is stored in localStorage, and the corresponding translation
file is fetched from the server. The translations are then applied globally.</p>
</dd>
<dt><a href="#showInitialMessage">showInitialMessage()</a> ⇒ <code>void</code></dt>
<dd><p>function that show initial message and disable the previous buttons</p>
</dd>
<dt><a href="#disableAllButtons">disableAllButtons()</a> ⇒ <code>void</code></dt>
<dd><p>function that disable all buttons from previous responses</p>
</dd>
<dt><a href="#getBotResponse">getBotResponse(userMessage)</a> ⇒ <code>string</code></dt>
<dd><p>function that return the bot response base on user input</p>
</dd>
<dt><a href="#getLocalizedText">getLocalizedText(italianText, englishText)</a> ⇒ <code>string</code></dt>
<dd><p>Function to return localized text based on the selected language</p>
</dd>
<dt><a href="#scrollToBottom">scrollToBottom()</a> ⇒ <code>void</code></dt>
<dd><p>function to scroll to the bottom of the chat</p>
</dd>
<dt><a href="#toggleInformationForm">toggleInformationForm()</a> ⇒ <code>void</code></dt>
<dd><p>toggle the visibility of the information form container</p>
</dd>
<dt><a href="#email">email(email)</a> ⇒ <code>void</code></dt>
<dd><p>Sends a heartbeat request to the server to keep the user session alive.
This function is invoked every 10 seconds if a valid user email is found in the cookies.</p>
</dd>
<dt><a href="#getCookie">getCookie(name)</a> ⇒ <code>string</code> | <code>null</code></dt>
<dd><p>Retrieves the value of a cookie by its name.</p>
</dd>
</dl>

<a name="toggleMenu"></a>

## toggleMenu() ⇒ <code>void</code>
toggle function to manage responsive navbar menu

**Kind**: global function  
<a name="toggleChatbot"></a>

## toggleChatbot() ⇒ <code>void</code>
toggle the visibility of the chatbot container

**Kind**: global function  
<a name="toggleLanguage"></a>

## toggleLanguage(language)
Toggle between languages (Italian or English)

**Kind**: global function  

| Param | Type | Description |
| --- | --- | --- |
| language | <code>string</code> | The selected language ('it' or 'en') |

<a name="handleQuestionClick"></a>

## handleQuestionClick(question, buttonsContainer) ⇒ <code>void</code>
function to handle the clicked question

**Kind**: global function  

| Param | Type | Description |
| --- | --- | --- |
| question | <code>string</code> | the selected question |
| buttonsContainer | <code>object</code> | the node element with all buttons |

<a name="changeLanguage"></a>

## changeLanguage(lang)
Changes the language of the application and loads the corresponding translations.
The selected language is stored in localStorage, and the corresponding translation
file is fetched from the server. The translations are then applied globally.

**Kind**: global function  
**Throws**:

- <code>Error</code> If there is an issue with fetching the translation file.


| Param | Type | Description |
| --- | --- | --- |
| lang | <code>string</code> | The language code to switch to (e.g., 'en', 'it', etc.). |

**Example**  
```js
// Change the language to Italian
changeLanguage('it');
```
<a name="showInitialMessage"></a>

## showInitialMessage() ⇒ <code>void</code>
function that show initial message and disable the previous buttons

**Kind**: global function  
<a name="disableAllButtons"></a>

## disableAllButtons() ⇒ <code>void</code>
function that disable all buttons from previous responses

**Kind**: global function  
<a name="getBotResponse"></a>

## getBotResponse(userMessage) ⇒ <code>string</code>
function that return the bot response base on user input

**Kind**: global function  
**Returns**: <code>string</code> - response  

| Param | Type | Description |
| --- | --- | --- |
| userMessage | <code>string</code> | the user input |

<a name="getLocalizedText"></a>

## getLocalizedText(italianText, englishText) ⇒ <code>string</code>
Function to return localized text based on the selected language

**Kind**: global function  
**Returns**: <code>string</code> - - The text based on the current selected language  

| Param | Type | Description |
| --- | --- | --- |
| italianText | <code>string</code> | The Italian version of the text |
| englishText | <code>string</code> | The English version of the text |

<a name="scrollToBottom"></a>

## scrollToBottom() ⇒ <code>void</code>
function to scroll to the bottom of the chat

**Kind**: global function  
<a name="toggleInformationForm"></a>

## toggleInformationForm() ⇒ <code>void</code>
toggle the visibility of the information form container

**Kind**: global function  
<a name="email"></a>

## email(email) ⇒ <code>void</code>
Sends a heartbeat request to the server to keep the user session alive.
This function is invoked every 10 seconds if a valid user email is found in the cookies.

**Kind**: global function  

| Param | Type | Description |
| --- | --- | --- |
| email | <code>string</code> | The user's email fetched from the cookies. |

<a name="getCookie"></a>

## getCookie(name) ⇒ <code>string</code> \| <code>null</code>
Retrieves the value of a cookie by its name.

**Kind**: global function  
**Returns**: <code>string</code> \| <code>null</code> - - The value of the cookie, or null if the cookie is not found.  

| Param | Type | Description |
| --- | --- | --- |
| name | <code>string</code> | The name of the cookie to retrieve. |



## attivita.js

<a name="initializeMenuBehavior"></a>

## initializeMenuBehavior()
Initializes the navigation menu behavior by setting up event listeners
for buttons and managing the display of corresponding sections.

This function:
- Sets up a smooth scroll to the secondary menu.
- Hides all sections and ensures only the target section is displayed.
- Updates button states (active/inactive) based on the user interaction.

**Kind**: global function  


## prenota.js

## Functions

<dl>
<dt><a href="#isBisestile">isBisestile(year)</a> ⇒ <code>boolean</code></dt>
<dd><p>chek if the input year is bisestile</p>
</dd>
<dt><a href="#setFebruaryDays">setFebruaryDays(year)</a> ⇒ <code>number</code></dt>
<dd><p>calculate february days</p>
</dd>
<dt><a href="#updateMonthNames">updateMonthNames(translations)</a></dt>
<dd><p>Update the month names and calendar UI when the language changes</p>
</dd>
<dt><a href="#updateSelectedDateDisplay">updateSelectedDateDisplay(type, date)</a></dt>
<dd><p>Update the text content of the selected check-in or check-out dates</p>
</dd>
<dt><a href="#reloadMonthNames">reloadMonthNames(lang)</a></dt>
<dd><p>Reload month names when language changes</p>
</dd>
<dt><a href="#getCalendar">getCalendar(month, year)</a> ⇒ <code>void</code></dt>
<dd><p>main function to get the calendar</p>
</dd>
</dl>

## Typedefs

<dl>
<dt><a href="#DateSelection">DateSelection</a> : <code>Object</code></dt>
<dd><p>object to manage check-in and check-out dates.</p>
</dd>
</dl>

<a name="isBisestile"></a>

## isBisestile(year) ⇒ <code>boolean</code>
chek if the input year is bisestile

**Kind**: global function  
**Returns**: <code>boolean</code> - - isBisestile true or false  

| Param | Type | Description |
| --- | --- | --- |
| year | <code>number</code> | input year |

<a name="setFebruaryDays"></a>

## setFebruaryDays(year) ⇒ <code>number</code>
calculate february days

**Kind**: global function  
**Returns**: <code>number</code> - - days of february  

| Param | Type | Description |
| --- | --- | --- |
| year | <code>number</code> | input year |

<a name="updateMonthNames"></a>

## updateMonthNames(translations)
Update the month names and calendar UI when the language changes

**Kind**: global function  

| Param | Type | Description |
| --- | --- | --- |
| translations | <code>object</code> | The loaded translations object |

<a name="updateSelectedDateDisplay"></a>

## updateSelectedDateDisplay(type, date)
Update the text content of the selected check-in or check-out dates

**Kind**: global function  

| Param | Type | Description |
| --- | --- | --- |
| type | <code>string</code> | Either 'check-in' or 'check-out' |
| date | <code>string</code> | The selected date (in format 'YYYY-MM-DD') |

<a name="reloadMonthNames"></a>

## reloadMonthNames(lang)
Reload month names when language changes

**Kind**: global function  

| Param | Type | Description |
| --- | --- | --- |
| lang | <code>string</code> | The selected language |

<a name="getCalendar"></a>

## getCalendar(month, year) ⇒ <code>void</code>
main function to get the calendar

**Kind**: global function  

| Param | Type | Description |
| --- | --- | --- |
| month | <code>number</code> | input month |
| year | <code>number</code> | input year |

<a name="DateSelection"></a>

## DateSelection : <code>Object</code>
object to manage check-in and check-out dates.

**Kind**: global typedef  
**Properties**

| Name | Type | Description |
| --- | --- | --- |
| checkIn | <code>string</code> \| <code>null</code> | Selected check-in date (in format 'YYYY-MM-DD') or null if not selected |
| checkOut | <code>string</code> \| <code>null</code> | Selected check-out date (in format 'YYYY-MM-DD') or null if not selected |
| reset | <code>function</code> | Resets the selected dates |
| isDateSelected | <code>function</code> | Checks if a specific date is selected. |



## offline.js

## Functions

<dl>
<dt><a href="#email">email(email)</a> ⇒ <code>void</code></dt>
<dd><p>Sends a heartbeat request to the server to keep the user session alive.
This function is invoked every 10 seconds if a valid user email is found in the cookies.</p>
</dd>
<dt><a href="#getCookie">getCookie(name)</a> ⇒ <code>string</code> | <code>null</code></dt>
<dd><p>Retrieves the value of a cookie by its name.</p>
</dd>
</dl>

<a name="email"></a>

## email(email) ⇒ <code>void</code>
Sends a heartbeat request to the server to keep the user session alive.
This function is invoked every 10 seconds if a valid user email is found in the cookies.

**Kind**: global function  

| Param | Type | Description |
| --- | --- | --- |
| email | <code>string</code> | The user's email fetched from the cookies. |

<a name="getCookie"></a>

## getCookie(name) ⇒ <code>string</code> \| <code>null</code>
Retrieves the value of a cookie by its name.

**Kind**: global function  
**Returns**: <code>string</code> \| <code>null</code> - - The value of the cookie, or null if the cookie is not found.  

| Param | Type | Description |
| --- | --- | --- |
| name | <code>string</code> | The name of the cookie to retrieve. |


## translation.js

## Functions

<dl>
<dt><a href="#currentLanguage">currentLanguage()</a></dt>
<dd></dd>
<dt><a href="#changeLanguage">changeLanguage(lang)</a></dt>
<dd><p>Changes the language of the page by loading a translation file 
and updating the content of all relevant elements.</p>
</dd>
<dt><a href="#setLangFlagFromStorage">setLangFlagFromStorage()</a></dt>
<dd><p>Sets the language flag on the page based on the saved language in localStorage.</p>
</dd>
<dt><a href="#updateUIWithTranslations">updateUIWithTranslations(translations)</a></dt>
<dd><p>Logs the translations to the console (can be customized to update UI with translations).</p>
</dd>
<dt><a href="#getLocalizedText">getLocalizedText(italianText, englishText)</a> ⇒ <code>string</code></dt>
<dd><p>Returns the correct text based on the selected language.</p>
</dd>
<dt><a href="#updateLanguageSwitcher">updateLanguageSwitcher(lang)</a></dt>
<dd><p>Updates the language switcher UI based on the selected language.
It changes the flag image and the text for both desktop and mobile views.</p>
</dd>
<dt><a href="#toggleLanguageMobile">toggleLanguageMobile()</a></dt>
<dd><p>Toggles the language on mobile view by switching between &#39;it&#39; and &#39;en&#39;.
It changes the language and reloads the page with the new language.</p>
</dd>
</dl>

<a name="currentLanguage"></a>

## currentLanguage()
**Kind**: global function  
**File:**: translation.js  
**Author:**: InnoVive
This script handles changing the language of the website.
It allows users to select a language, store their choice in localStorage, 
and updates the page content by loading a translation file.
It also updates the language in the URL and on the page.

- Saves the selected language in localStorage.
- Loads the translation file for the selected language.
- Updates the text content of page elements based on the language.
- Updates the language flag and text for language switching.  
<a name="changeLanguage"></a>

## changeLanguage(lang)
Changes the language of the page by loading a translation file 
and updating the content of all relevant elements.

**Kind**: global function  

| Param | Type | Description |
| --- | --- | --- |
| lang | <code>string</code> | The language code (e.g., 'it' for Italian, 'en' for English). |

<a name="setLangFlagFromStorage"></a>

## setLangFlagFromStorage()
Sets the language flag on the page based on the saved language in localStorage.

**Kind**: global function  
<a name="updateUIWithTranslations"></a>

## updateUIWithTranslations(translations)
Logs the translations to the console (can be customized to update UI with translations).

**Kind**: global function  

| Param | Type | Description |
| --- | --- | --- |
| translations | <code>Object</code> | The translations loaded from the JSON file. |

<a name="getLocalizedText"></a>

## getLocalizedText(italianText, englishText) ⇒ <code>string</code>
Returns the correct text based on the selected language.

**Kind**: global function  
**Returns**: <code>string</code> - The text in the selected language.  

| Param | Type | Description |
| --- | --- | --- |
| italianText | <code>string</code> | The text in Italian. |
| englishText | <code>string</code> | The text in English. |

<a name="updateLanguageSwitcher"></a>

## updateLanguageSwitcher(lang)
Updates the language switcher UI based on the selected language.
It changes the flag image and the text for both desktop and mobile views.

**Kind**: global function  

| Param | Type | Description |
| --- | --- | --- |
| lang | <code>string</code> | The selected language (either 'it' or 'en'). |

<a name="toggleLanguageMobile"></a>

## toggleLanguageMobile()
Toggles the language on mobile view by switching between 'it' and 'en'.
It changes the language and reloads the page with the new language.

**Kind**: global function  
