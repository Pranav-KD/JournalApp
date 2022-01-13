# Journal Application
| Project| Journal |
 | ----------- | ----------- | 
| Name | Pranav Kumar Dargan |
| ID | 2019A7PS0111G | 
| email | f20190111@goa.bits-pilani.ac.in |    
 --- 
# About
Journal App is an application that allows a user to note down what they did, in the following format :
1. A Title expressing the activity they did
2. The date on which they did the activity
3. The times when they started and ended said activity.

---   
## Known Bugs
-  Entries can sometimes be created without any text present in the title, if the user chooses to add a new Entry but does not actually save it.

--- 
## Development

1. Task one was done using the navigation graph  editor inside android studio. The main edit required from the navigation graph provided was to add the new info fragment to the graph. Also, every transition was implemented in code using the `NavigationController`.

2. The database was created to have the queries such as delete, insert and update, which was used while modifying the elements that are displayed.



3. The `Delete` button was added to the menu of the `EntryDetailsFragment` view, which when clicked created a dialog that deleted the entry if the user pressed `yes`, and also took the user back to the `EntryListFragment`.




4. The `Share` button was made and added to the menu of `EntryDetailsFragment`, which then took the data of the selected entry and created a String to be shared in the given format. This string was then used to create an implicit intent, which allowed the user to share their activity on whatever platform they chose to do so.

5. The info button was added to the menu of `EntryListFragment`, and an action was added to the navigation graph to lead to the new `EntryInfoActivity` Fragment. This Activity then displayed the info about the application

6. **Accessibility :**
   Accessibility was tested by [integrating it with expresso](https://developer.android.com/guide/topics/ui/accessibility/testing#espresso). These tests used the UI available to the user, and then would report any accessibility issues present.





---   
The monkey tool was used and the application did not crash on any run
  
---   

### Resources
1. [Persistent Data](https://swaroopjoshi.in/courses/mobile-app-dev/07-activity-lifecycle/), [Fragments and Navigation](https://swaroopjoshi.in/courses/mobile-app-dev/08-intents/)  and [Menu bar and Dialogs](https://swaroopjoshi.in/courses/mobile-app-dev/14-misc/#menubar-and-dialogs)
2. [Android Documentation](https://developer.android.com/)
