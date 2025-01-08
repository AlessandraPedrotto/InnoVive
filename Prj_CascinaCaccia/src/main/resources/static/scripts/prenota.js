let dateSelection = {
    checkIn: null,
    checkOut: null,
    reset() {
        this.checkIn = null;
        this.checkOut = null;
    },
    isDateSelected(date) {
        return this.checkIn === date || this.checkOut === date;
    },
};

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

let moreDays = false;

const getCalendar = (month, year) => {
    let calendarDays = document.querySelector('.calendar-days');
    calendarDays.textContent = ''; // reset calendar
    let calendarYear = document.querySelector('#year');

    let monthDays = [31, setFebruaryDays(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

    let currentDate = new Date();

    // set month and year
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
                day.classList.add('disabled'); // past day
            } else {
                day.classList.add('calendar-day-hover');

                if (dateToCheck.getDay() == 0) {
                    day.classList.add('domenica'); // domenica
                }

                day.onclick = () => {
                    let formattedDate = `${dayNumber} ${monthNames[month]} ${year}`;
                    let selectedDate = `${year}-${('0' + (month + 1)).slice(-2)}-${('0' + dayNumber).slice(-2)}`;
                console.log(selectedDate.slice(-2));
                
                    if (!dateSelection.checkIn) {
                        // check-in
                        dateSelection.checkIn = selectedDate;
                        day.classList.add('selected');
                        document.getElementById('selected-check-in').textContent = formattedDate;
                        document.getElementById('check-in-confirm').textContent = formattedDate;
                    } else if (!dateSelection.checkOut && selectedDate > dateSelection.checkIn) {
                        // check-out
                        dateSelection.checkOut = selectedDate;
                        day.classList.add('selected');
                        document.getElementById('selected-check-out').textContent = formattedDate;
                        document.getElementById('check-out-confirm').textContent = formattedDate;

                        // raccolgo tutti i div
                        Array.from(document.querySelectorAll('.calendar-days div')).forEach((div)=>{
                            let checkInDate = dateSelection.checkIn;
                            let checkOutDate = dateSelection.checkOut;

                            if (div.textContent > checkInDate.slice(-2) && div.textContent < checkOutDate.slice(-2)) {
                                console.log('range div');
                                
                                div.classList.add('range');

                            }
                        })




                    } else if (dateSelection.isDateSelected(selectedDate)) {
                        // deseleziona data
                        if (dateSelection.checkIn === selectedDate) {
                            dateSelection.checkIn = null;
                            Array.from(document.querySelectorAll('.calendar-days div')).forEach((div)=>{
                                div.classList.remove('range')
                            })
                        } else if (dateSelection.checkOut === selectedDate) {
                            dateSelection.checkOut = null;
                            Array.from(document.querySelectorAll('.calendar-days div')).forEach((div)=>{
                                div.classList.remove('range')
                            })
                        }
                        day.classList.remove('selected');
                        if (!dateSelection.checkIn && !dateSelection.checkOut) {
                            // reset
                            dateSelection.reset();
                            document.getElementById('selected-check-in').textContent = '';
                            document.getElementById('check-in-confirm').textContent = '';
                            document.getElementById('selected-check-out').textContent = '';
                            document.getElementById('check-out-confirm').textContent = '';
                            Array.from(document.querySelectorAll('.calendar-days div')).forEach((div)=>{
                                div.classList.remove('range')
                            })
                        }
                    } else {
                        console.warn('Azione non valida o reset richiesto.');
                    }
                
                    // input value hidden
                    let hiddenDateValue = dateSelection.checkOut 
                        ? `${dateSelection.checkIn} to ${dateSelection.checkOut}`
                        : dateSelection.checkIn || '';
                    document.getElementById('hidden-datepicker').value = hiddenDateValue;
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