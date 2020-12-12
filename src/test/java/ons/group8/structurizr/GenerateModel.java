package ons.group8.structurizr;

import com.structurizr.Workspace;
import com.structurizr.analysis.ComponentFinder;
import com.structurizr.analysis.ReferencedTypesSupportingTypesStrategy;
import com.structurizr.analysis.SpringComponentFinderStrategy;
import com.structurizr.api.StructurizrClient;
import com.structurizr.model.*;
import com.structurizr.view.*;
import org.junit.jupiter.api.Test;

public class GenerateModel {

    private final static int WORKSPACE_ID = 61657;

    private final static String API_KEY = "32495347-6670-4315-98fb-b3ee9ba99eda";

    private final static String API_SECRET = "3770f3c4-4e1d-4d7a-8f22-579b319f79f9";

    public  static void main(String[] args) {
        GenerateModel structurizr = new GenerateModel();
        try {
            structurizr.generateModel();
        } catch (Exception e) {

        }
    }

    @Test
    public void generateModel() throws Exception {

        //set up workspace and model
        //these are the core objects of our
        Workspace workspace = new Workspace("ONS Onboarding - Group 8", "Checklist Project for ONS Onboarding");
        Model model = workspace.getModel();

        // create the basic model (the stuff we can't get from the code)
        SoftwareSystem checklistApp = model.addSoftwareSystem("ONS Onboarding", "Online checklist system");
        Person user = model.addPerson("User", "A person who is assigned checklists to complete");
        Person author = model.addPerson("Author", "A person who is creates checklist templates to assign to users");
        Person admin = model.addPerson("Admin", "A person who is responsible for managing users of the system");

        //set a relationship between the user(s) and the system
        user.uses(checklistApp, "Uses");
        author.uses(checklistApp, "Uses");
        admin.uses(checklistApp, "Uses");


        Container webApplication = checklistApp.addContainer(
                "Spring Boot Application", "The web application", "Embedded web container.  Tomcat 7.0");
        Container relationalDatabase = checklistApp.addContainer(
                "Relational Database", "Stores information regarding the users and checklists.", "MySQL");
        user.uses(webApplication, "Uses", "HTTP");
        author.uses(webApplication, "Uses", "HTTP");
        admin.uses(webApplication, "Uses", "HTTP");

        webApplication.uses(relationalDatabase, "Reads from and writes to", "JPA, port 8443");

        // and now automatically find all Spring @Controller, @Component, @Service and @Repository components
        ComponentFinder componentFinder = new ComponentFinder(
                webApplication,
                "ons.group8",
                new SpringComponentFinderStrategy(
                        new ReferencedTypesSupportingTypesStrategy()
                ));

        componentFinder.findComponents();


        Component adminController = webApplication.getComponentOfType("ons.group8.controllers.AdminController");
        admin.uses(adminController, "Uses", "HTTP");

        Component authorController = webApplication.getComponentOfType("ons.group8.controllers.AuthorController");
        author.uses(authorController, "Uses", "HTTP");

        Component personalChecklistController = webApplication.getComponentOfType("ons.group8.controllers.PersonalChecklistController");
        user.uses(personalChecklistController, "Uses", "HTTP");

        Component loginController = webApplication.getComponentOfType("ons.group8.controllers.LoginController");
        user.uses(loginController, "Uses", "HTTP");
        author.uses(loginController, "Uses", "HTTP");
        admin.uses(loginController, "Uses", "HTTP");

        Component signupController = webApplication.getComponentOfType("ons.group8.controllers.SignupController");
        user.uses(signupController, "Uses", "HTTP");
        author.uses(signupController, "Uses", "HTTP");
        admin.uses(signupController, "Uses", "HTTP");

        // connect all of the repository components to the relational database
        webApplication.getComponents().stream()
                .filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_REPOSITORY))
                .forEach(c -> c.uses(relationalDatabase, "Reads from and writes to", "JPA"));

        //create a SystemContext for the system
        ViewSet viewSet = workspace.getViews();
        SystemContextView contextView = viewSet.createSystemContextView(checklistApp, "context", "The System Context diagram for the ONS Checklist system.");
        contextView.addAllSoftwareSystems();
        contextView.addAllPeople();

        ContainerView containerView = viewSet.createContainerView(checklistApp, "containers", "The Containers diagram for the ONS Checklist system.");
        containerView.addAllPeople();
        containerView.addAllSoftwareSystems();
        containerView.addAllContainers();

        ComponentView componentView = viewSet.createComponentView(webApplication, "components", "The Components diagram for the ONS Checklist web application.");
        componentView.addAllComponents();
        componentView.addAllPeople();
        componentView.add(relationalDatabase);

        // link the architecture model with the code
        for (Component component : webApplication.getComponents()) {
            for (CodeElement codeElement : component.getCode()) {
                String sourcePath = codeElement.getUrl();
                if (sourcePath != null) {
                    codeElement.setUrl(
                            "https://git.cardiff.ac.uk/c1946094/ons-client-project-group-8/-/tree/master");
                }
            }
        }

        // tag and style some elements
        checklistApp.addTags("ONS Onboarding");
        webApplication.getComponents().stream().filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_MVC_CONTROLLER)).forEach(c -> c.addTags("Spring MVC Controller"));
        webApplication.getComponents().stream().filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_MVC_CONTROLLER)).forEach(c -> c.addTags("Spring REST Controller"));

        webApplication.getComponents().stream().filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_SERVICE)).forEach(c -> c.addTags("Spring Service"));
        webApplication.getComponents().stream().filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_REPOSITORY)).forEach(c -> c.addTags("Spring Repository"));
        relationalDatabase.addTags("Database");

        Styles styles = viewSet.getConfiguration().getStyles();
        styles.addElementStyle("ONS Onboarding - Group 8").background("#6CB33E").color("#ffffff");
        styles.addElementStyle(Tags.PERSON).background("#519823").color("#ffffff").shape(Shape.Person);
        styles.addElementStyle(Tags.CONTAINER).background("#91D366").color("#ffffff");
        styles.addElementStyle("Database").shape(Shape.Cylinder);

        styles.addElementStyle("Spring REST Controller").background("#D4FFC0").color("#000000");

        styles.addElementStyle("Spring MVC Controller").background("#D4F3C0").color("#000000");
        styles.addElementStyle("Spring Service").background("#6CB33E").color("#000000");
        styles.addElementStyle("Spring Repository").background("#95D46C").color("#000000");

        StructurizrClient structurizrClient = new StructurizrClient(API_KEY, API_SECRET);
        structurizrClient.putWorkspace(WORKSPACE_ID, workspace);
    }
}
