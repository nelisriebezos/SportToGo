# SportToGo
Welkom in de readme van SportToGo.
In deze readme zal ik uitleggen hoe je dit project werkende kan krijgen op jouw eigen pc/laptop, waar de integratie tests te vinden zijn en hoe je gebruik maakt van de website.

#Tomcat
Om te beginnen moet je Tomcat instellen in IntelliJ. Start IntelliJ op nadat je Tomcat hebt geinstalleerd en ergens apart hebt staan. In het start-up scherm druk op 'Customize' -> ' All settings' ->  'Build, Execution, Deployment' -> Application Servers. Druk hier op de + en voeg Tomcat Server toe (als het goed is weet je zelf waar je Tomcat op je pc hebt staan). Druk dan op OK.

Nadat je Tomcat hebt geconfigureerd moet je het ook nog toevoegen aan de run configuration van het project. Dit doe je door op Run/Debug Configurations te drukken. Je krijgt dan een window waarin je jouw run configurations hebt. Druk op + in de linker bovenste hoek. Zoek Tomcat Server in de lijst en kies de local variant. Nadat je de Tomcat server hebt toegevoegd ga naar de Deployment tab en druk op + onder Deploy at server startup.  Selecteer hier war exploded en zorg ervoor dat de Application context op / staat.'
Ga dan terug naar de Server tab en stel daar de volgende instellingen in: On Update action: Redeploy, On frame deactivation: Update resources.
Het project is nu klaar om gestart te worden.

#Integratie
Om bij de integratie tests te komen ga naar: http://localhost:8080/integratieLogin.html <br/>
Op elke pagina staan beschrijvingen van de business rules waar de tests aan moeten voldoen. De POST/DELETE/PUT methodes die worden aangeroepen zijn hetzelfde als de methodes die in het project worden gebruikt.
Er staat een account aan met voorgemaakte data om mee te testen: <br/>
Gebruikernaam: test <br/>
Wachtwoord: test
