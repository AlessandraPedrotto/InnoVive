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

// function to chek if is a bisestile year 
const isBisestile = (year) => {
    return (year % 4 === 0 && year % 100 !== 0) || (year % 400 === 0);
};

// function to calculate february year 
const setFebruaryDays = (year) => {
    return isBisestile(year) ? 29 : 28;
};

// month names
const monthNames = [
    'Gennaio', 'Febbraio', 'Marzo', 'Aprile', 'Maggio', 'Giugno',
    'Luglio', 'Agosto', 'Settembre', 'Ottobre', 'Novembre', 'Dicembre'
];

// main function to manage the calendar
const getCalendar = (month, year) => {
    let calendarDays = document.querySelector('.calendar-days');
    calendarDays.textContent = ''; // calendar reset

    let calendarYear = document.querySelector('#year');
    let monthDays = [31, setFebruaryDays(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    let currentDate = new Date();

    monthPicker.textContent = monthNames[month];
    calendarYear.textContent = year;

    let firstDay = new Date(year, month, 1);

    for (let i = 0; i < monthDays[month] + firstDay.getDay(); i++) {
        let day = document.createElement('div');

        if (i >= firstDay.getDay()) {
            let dayNumber = i - firstDay.getDay() + 1;
            let dateToCheck = new Date(year, month, dayNumber);
            let formattedDate = `${year}-${('0' + (month + 1)).slice(-2)}-${('0' + dayNumber).slice(-2)}`;

            day.textContent = dayNumber;

            if (dateToCheck < currentDate.setHours(0, 0, 0, 0)) {
                day.classList.add('disabled');
            } else {
                day.classList.add('calendar-day-hover');

                if (dateSelection.checkIn === formattedDate) {
                    day.classList.add('selected');
                } else if (dateSelection.checkOut === formattedDate) {
                    day.classList.add('selected');
                } else if (
                    dateSelection.checkIn &&
                    dateSelection.checkOut &&
                    dateToCheck > new Date(dateSelection.checkIn) &&
                    dateToCheck < new Date(dateSelection.checkOut)
                ) {
                    day.classList.add('range');
                }

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

                    getCalendar(currentMonth.value, currentYear.value);
                };
            }
        }
        calendarDays.appendChild(day);
    }
};

// calendar inizialize
let calendar = document.querySelector('.calendar');
let monthPicker = document.querySelector('#month-picker');
let monthList = calendar.querySelector('.month-list');

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

let currentDate = new Date();
let currentMonth = { value: currentDate.getMonth() };
let currentYear = { value: currentDate.getFullYear() };

getCalendar(currentMonth.value, currentYear.value);

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



const submitBTN = document.getElementById('submit-btn');


submitBTN.onclick = () =>{
    if (document.getElementById('hidden-datepicker-checkout').value === '') {
        document.getElementById('hidden-datepicker-checkout').value = document.getElementById('hidden-datepicker-checkin').value
    }
    console.log(document.getElementById('hidden-datepicker-checkin').value);
    console.log(document.getElementById('hidden-datepicker-checkout').value);
    
    document.getElementById('prenota-form').submit();
    
}



// manage conferma container


const confermaContainer = document.querySelector('.conferma-container');


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
