# password-store
 
 This is a simple application which can be used to store passwords and according information safely.
 
 [User manual](manual.md)
 
## Used technologies, libraries

* Java 8
* Kotlin 
* JavaFX, TornadoFX
* JetBrains Exposed
 
## Implementation
The app consists uses a four layered architecture (after the dash the containing packeges):
+ View - `view`
+ ViewModel - `viewmodel`
+ Domain - `model, services`
+ Data Access - `repository`

### Data Access Layer
For easy installing I used a simple SQLite database to store the data.
With this solution, the user does not require a separate DB instance and all the data can be stored in a simple file.
I used JetBrains Exposed library to use an ORM to access the DB entities by a type safe way.

To hide the implementation of the data handling I used the Repository pattern which gives back Domain entities,
 and provides methods for handling them (update, remove, etc.). It also contains the
 logic that provides the encryption.
 
 The repository provides asynchronicity by kotlin coroutines, but all of its suspend functions
 works correctly if they were started on the App's UI thread.
 
 ### Domain
 
 The domain only contains a single singleton service handling the actions to the passwords.
 I made it singleton so all the upper classes would see the same instance, simplifying communication.
 It is not dependent on the View layers, and could be made independent from the Repository as well using
 the Dependency inversion principle (using an interface as an act-between).
 
 ### ViewModel
 The application uses the MVVM design for the separation of the view layout from its operating logic.
 It holds the data for the View components, and handles its manipulation. Using TornadoFX-s injection pattern to provide communication if neccesary.
 
 ### View
 The application uses the JavaFX components, by using the provide extensions. I haven't used the FXML, because
 TornadoFX provides a similar quasi declarative UI desingning method, but it uses Kotlin code, thus it's type safe.
 
 I created a few extensions for TornadoFx classes, for example enabling async actions on buttons, these can be found in
 extension package.
 
 ## Running
 
 The application's main function can be found in MyApp.kt, which starts the tornadofx application.