function formatDateMMMYYYY(date) {

    var month;
    switch(date.getMonth()) {
        case 0: month = "Jan"; break;
        case 1: month = "Feb"; break;
        case 2: month = "Mar"; break;
        case 3: month = "Apr"; break;
        case 4: month = "May"; break;
        case 5: month = "Jun"; break;
        case 6: month = "Jul"; break;
        case 7: month = "Aug"; break;
        case 8: month = "Sep"; break;
        case 9: month = "Oct"; break;
        case 10: month = "Nov"; break;
        case 11: month = "Dec"; break;
    }
    return month + " " + date.getFullYear();
}

var startDate = $('#startDate').datepicker()
    .on('changeDate', function(ev) {
        startDate.hide();
        $('#endDate')[0].focus();
    })
    .data('datepicker');;

var endDate = $('#endDate').datepicker()
    .on('changeDate', function(ev) {
        $('#name').val(formatDateMMMYYYY(ev.date));
        endDate.hide();

        if (ev.date < startDate.date) {
            $("#endDateControl").addClass("error")
        } else {
            $("#endDateControl").removeClass("error")
        }
    })
    .data('datepicker');;