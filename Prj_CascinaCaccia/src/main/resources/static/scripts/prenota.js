const isBisestile = (year) => {
    return (year % 4 === 0 && year % 100 !== 0) || (year % 400 === 0);
};

const setFebruaryDays = (year) => {
    return isBisestile(year) ? 29 : 28;
};

const monthNames = [
    'Gennaio', 'Febbraio', 'Marzo', 'Aprile', 'Maggio', 'Giugno',
    'Luglio', 'Agosto', 'Settembre', 'Ottobre', 'Novembre', 'Dicembre'
];

const getCalendar = (month, year) => {
    let calendarDays = document.querySelector('.calendar-days');
    calendarDays.textContent = ''; // Svuota il calendario
    let calendarYear = document.querySelector('#year');

    let monthDays = [31, setFebruaryDays(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

    let currentDate = new Date();

    // Setta mese e anno
    monthPicker.textContent = monthNames[month];
    calendarYear.textContent = year;

    let firstDay = new Date(year, month, 1);

    for (let i = 0; i < monthDays[month] + firstDay.getDay(); i++) {
        let day = document.createElement('div');
        if (i >= firstDay.getDay()) {
            let dayNumber = i - firstDay.getDay() + 1;
            day.textContent = dayNumber;

            let dateToCheck = new Date(year, month, dayNumber);

            if (dateToCheck < currentDate.setHours(0, 0, 0, 0)) {
                day.classList.add('disabled'); // Giorno passato
            } else {
                day.classList.add('calendar-day-hover');

                if (dateToCheck.getDay() == 0) {
                    day.classList.add('domenica'); // Colore arancione per la domenica
                }

                day.onclick = () => {
                    let allDays = document.querySelectorAll('.calendar-day-hover');
                    allDays.forEach((day) => day.classList.remove('selected'));

                    day.classList.add('selected');

                    let formattedDate = `${dayNumber} ${monthNames[month]} ${year}`;
                    document.getElementById('selected-date').textContent = formattedDate;
                    let selectedDate = `${year}-${('0' + (month + 1)).slice(-2)}-${('0' + dayNumber).slice(-2)}`;
                    document.getElementById('hidden-datepicker').value = selectedDate;
                };
            }
        }
        calendarDays.appendChild(day);
    }
};

// Inizializza calendario e navigazione
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

// Mostra lista mesi
monthPicker.onclick = () => {
    monthList.classList.add('show');
};


document.getElementById('submitButton').addEventListener('click', function() {
    document.getElementById('prenota-form').submit();
  });