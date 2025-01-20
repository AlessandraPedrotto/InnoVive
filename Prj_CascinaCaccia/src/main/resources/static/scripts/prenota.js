// object to manage checkin and checkout dates
let dateSelection = {
    checkIn: null, // selected checkin date
    checkOut: null, // selected checkout date
    reset() {
        // reset selected dates
        this.checkIn = null;
        this.checkOut = null;
    },
    isDateSelected(date) {
        // check if a date is already selected
        return this.checkIn === date || this.checkOut === date;
    },
};


/**
 * chek if the input year is bisestile  
 * @param {number} year - input year
 * @returns {boolean} - isBisestile true or false
 */
const isBisestile = (year) => {
    return (year % 4 === 0 && year % 100 !== 0) || (year % 400 === 0);
};


/**
 * calculate february days  
 * @param {number} year - input year
 * @returns {number} - days of february
 */
const setFebruaryDays = (year) => {
    return isBisestile(year) ? 29 : 28;
};

// Month names, dynamically updated based on the selected language
let monthNames = [];

/**
 * Update the month names and calendar UI when the language changes
 * @param {object} translations - The loaded translations object
 */
const updateMonthNames = (translations) => {
    monthNames = translations['calendar_month_names'] || [];
    // Refresh the calendar UI
    getCalendar(currentMonth.value, currentYear.value); 

    // Update month picker options
    monthList.textContent = '';
    monthNames.forEach((m, index) => {
        let month = document.createElement('div');
        month.textContent = m;
        month.onclick = () => {
            monthList.classList.remove('show');
            currentMonth.value = index;
            getCalendar(currentMonth.value, currentYear.value);
        };
        monthList.appendChild(month);
    });

    // Update the selected date display
    if (dateSelection.checkIn) {
        updateSelectedDateDisplay('check-in', dateSelection.checkIn);
    }
    if (dateSelection.checkOut) {
        updateSelectedDateDisplay('check-out', dateSelection.checkOut);
    }
};

/**
 * Update the text content of the selected check-in or check-out dates
 * @param {string} type - Either 'check-in' or 'check-out'
 * @param {string} date - The selected date (in format 'YYYY-MM-DD')
 */
const updateSelectedDateDisplay = (type, date) => {
    const dateObj = new Date(date);
    const monthIndex = dateObj.getMonth();
    const day = dateObj.getDate();
    const year = dateObj.getFullYear();

    const monthName = monthNames[monthIndex]; 

    if (type === 'check-in') {
        document.getElementById('selected-check-in').textContent = `${day} ${monthName} ${year}`;
        document.getElementById('check-in-confirm').textContent = `${day} ${monthName} ${year}`;
    } else if (type === 'check-out') {
        document.getElementById('selected-check-out').textContent = `${day} ${monthName} ${year}`;
        document.getElementById('check-out-confirm').textContent = `${day} ${monthName} ${year}`;
    }
};

/**
 * Reload month names when language changes
 * @param {string} lang - The selected language
 */
const reloadMonthNames = (lang) => {
    fetch(`/translation/${lang}.json`)
        .then(response => response.json())
        .then(translations => {
            updateMonthNames(translations);
        })
        .catch(error => console.error('Error updating calendar translations:', error));
};

/**
 * main function to get the calendar 
 * @param {number} month - input month
 * @param {number} year - input year
 * @returns {void} 
 */
const getCalendar = (month, year) => {
    // get the calendar days container
    let calendarDays = document.querySelector('.calendar-days');
    // calendar reset
    calendarDays.textContent = ''; 

    // get the calendar year container
    let calendarYear = document.querySelector('#year');
    // array with number of days for each month
    let monthDays = [31, setFebruaryDays(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    // get the current date
    let currentDate = new Date();

    // setting the month and the year
    monthPicker.textContent = monthNames[month] || 'Month';
    calendarYear.textContent = year;

    // get the first day of the selected month and year
    let firstDay = new Date(year, month, 1);

    // iterate all month days
    for (let i = 0; i < monthDays[month] + firstDay.getDay(); i++) {
        // create a div for the day
        let day = document.createElement('div');

        // check if is a valid day
        if (i >= firstDay.getDay()) {
            let dayNumber = i - firstDay.getDay() + 1;
            let dateToCheck = new Date(year, month, dayNumber);
            let formattedDate = `${year}-${('0' + (month + 1)).slice(-2)}-${('0' + dayNumber).slice(-2)}`;

            day.textContent = dayNumber;

            if (dateToCheck < currentDate.setHours(0, 0, 0, 0)) {
                // if it is a past day is disabled
                day.classList.add('disabled');
            } else {
                day.classList.add('calendar-day-hover');

                if (dateSelection.checkIn === formattedDate) {
                    day.classList.add('selected');  // seleted check in
                } else if (dateSelection.checkOut === formattedDate) {
                    day.classList.add('selected');  // selected check out
                } else if (
                    dateSelection.checkIn &&
                    dateSelection.checkOut &&
                    dateToCheck > new Date(dateSelection.checkIn) &&
                    dateToCheck < new Date(dateSelection.checkOut)
                ) {
                    day.classList.add('range');  // range betweeen check in and check out
                }

                // click listener to select the date
                day.onclick = () => {
                    if (!dateSelection.checkIn) {
                        // check in selection
                        dateSelection.checkIn = formattedDate;
                        day.classList.add('selected');
                        document.getElementById('selected-check-in').textContent = `${dayNumber} ${monthNames[month]} ${year}`;
                        document.getElementById('check-in-confirm').textContent = `${dayNumber} ${monthNames[month]} ${year}`;
                        document.getElementById('hidden-datepicker-checkin').value = formattedDate;
                    } else if (!dateSelection.checkOut && formattedDate > dateSelection.checkIn) {
                        // checkout selection
                        dateSelection.checkOut = formattedDate;
                        day.classList.add('selected');
                        document.getElementById('selected-check-out').textContent = `${dayNumber} ${monthNames[month]} ${year}`;
                        document.getElementById('check-out-confirm').textContent = `${dayNumber} ${monthNames[month]} ${year}`;
                        document.getElementById('hidden-datepicker-checkout').value = formattedDate;
                    } else if (dateSelection.isDateSelected(formattedDate)) {
                        // selection reset
                        dateSelection.reset();
                        document.getElementById('selected-check-in').textContent = '';
                        document.getElementById('selected-check-out').textContent = '';
                        document.getElementById('check-in-confirm').textContent = '';
                        document.getElementById('check-out-confirm').textContent = '';
                        document.getElementById('hidden-datepicker-checkin').value = '';
                        document.getElementById('hidden-datepicker-checkout').value = '';
                        Array.from(document.querySelectorAll('.calendar-days div')).forEach(div => {
                            div.classList.remove('selected', 'range');
                        });
                    }
                    // reload the calendar 
                    getCalendar(currentMonth.value, currentYear.value);
                };
            }
        }
        calendarDays.appendChild(day);  // append the day to the calendar
    }
};

// calendar inizialize
let calendar = document.querySelector('.calendar');
let monthPicker = document.querySelector('#month-picker');
let monthList = calendar.querySelector('.month-list');

let currentDate = new Date();
let currentMonth = { value: currentDate.getMonth() };
let currentYear = { value: currentDate.getFullYear() };

// Initialize with the default language
document.addEventListener('DOMContentLoaded', () => {
    const savedLanguage = localStorage.getItem('selectedLanguage') || 'it'; 
    reloadMonthNames(savedLanguage);
});

// Language change event listener
document.addEventListener('languageChange', (e) => {
    reloadMonthNames(e.detail.lang);
});

// buttons to navigate the year
const prevYear = document.getElementById('prev-year');
const nextYear = document.getElementById('next-year');

prevYear.onclick = () => {
    currentYear.value--;
    getCalendar(currentMonth.value, currentYear.value);
};

nextYear.onclick = () => {
    currentYear.value++;
    getCalendar(currentMonth.value, currentYear.value);
};

monthPicker.onclick = () => {
    monthList.classList.add('show');
};


// submit form button
const submitBTN = document.getElementById('submit-btn');


submitBTN.onclick = () =>{
    // check the date values to submit to the backend
    if (document.getElementById('hidden-datepicker-checkout').value === '') {
        // setting check out date equals to the chec in date
        document.getElementById('hidden-datepicker-checkout').value = document.getElementById('hidden-datepicker-checkin').value
    }
    // submit the form
    document.getElementById('prenota-form').submit();
    
}



// container with checkin and checkout recap
const confermaContainer = document.querySelector('.conferma-container');

// event listener to hide the container when the page is scrolled to the end
window.addEventListener('scroll', () => {
    const scrollY = window.scrollY; 
    const innerHeight = window.innerHeight; 
    const scrollHeight = document.documentElement.scrollHeight; 

    if (scrollY + innerHeight >= scrollHeight) {
        confermaContainer.classList.add('hidden-conferma');
    } else {
        confermaContainer.classList.remove('hidden-conferma');
    }
});