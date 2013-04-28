Changes
=======
2013-04-22 : Persistence of budget category
2013-04-23 : Added bootstrap-datepicker.js component and jquery-1.9.1.js
2013-04-24 : Added budget new page with startDate, endDate and client side javascript to update the budget name
             Reverted to DateField from DatePicker because the format of the date in the picker unexpectedly changed when the value was updated. Worked FF but not Chrome.
             Working on the Budget.save() methods, need to unit test find budget by start and end date.


bootstap-datepicker.js
----------------------
Version: 12/3/2013
Site: http://www.eyecon.ro/bootstrap-datepicker/
License: Apache 2.0

jquery.js
---------
Version: 1.9.1