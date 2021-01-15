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

    private final static int WORKSPACE_ID = 62004;

    private final static String API_KEY = "aa1afc51-af21-4cc6-861b-b32bf2b3acc4";

    private final static String API_SECRET = "3484ad9a-eb78-4b45-83d0-59ce46f349a0";

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
        Workspace workspace = new Workspace("ONS Onboarding - Group 8 - Private", "Checklist Project for ONS Onboarding");
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
                "Spring Boot Application", "The web application", "Embedded web container.  Tomcat 9.0");
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


        // Controllers
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


        // Services
        Component authorService = webApplication.getComponentOfType("ons.group8.services.AuthorService");
        Component personalChecklistService = webApplication.getComponentOfType("ons.group8.services.PersonalChecklistService");
        Component adminService = webApplication.getComponentOfType("ons.group8.services.AdminService");
        Component lockoutService = webApplication.getComponentOfType("ons.group8.services.LockOutService");
        Component userService = webApplication.getComponentOfType("ons.group8.services.UserService");
        Component myUserDetailsService = webApplication.addComponent("MyUserDetailsService", "", "Spring Security");
        myUserDetailsService.addTags("Spring Service");


        // Repos
        Component personalChecklistRepo = webApplication.getComponentOfType("ons.group8.repositories.PersonalChecklistRepositoryJPA");
        Component checklistTemplateRepo = webApplication.getComponentOfType("ons.group8.repositories.ChecklistTemplateRepositoryJPA");


        // templates
        Component loginTemplate = webApplication.addComponent("login", "Template for users to login", "Thymeleaf");
        loginTemplate.addTags("Thymeleaf");
        loginController.uses(loginTemplate, "as view", "Spring MVC");

        Component indexTemplate = webApplication.addComponent("index", "Template for the homepage once a user has logged in", "Thymeleaf");
        indexTemplate.addTags("Thymeleaf");
        loginController.uses(indexTemplate, "as view", "Spring MVC");

        Component userRolesTemplate = webApplication.addComponent("user-roles", "Template for admins to manage users of the system", "Thymeleaf");
        userRolesTemplate.addTags("Thymeleaf");
        adminController.uses(userRolesTemplate, "as view", "Spring MVC");

        Component userRoleFormTemplate = webApplication.addComponent("userrole-form", "Template containing a form so that admins can edit the roles of a user", "Thymeleaf");
        userRoleFormTemplate.addTags("Thymeleaf");
        adminController.uses(userRoleFormTemplate, "as view", "Spring MVC");

        Component messageTemplate = webApplication.addComponent("message", "Template that displays a message to the user", "Thymeleaf");
        messageTemplate.addTags("Thymeleaf");
        adminController.uses(messageTemplate, "as view", "Spring MVC");
        signupController.uses(messageTemplate, "as view", "Spring MVC");
        authorController.uses(messageTemplate, "as view", "Spring MVC");
        personalChecklistController.uses(messageTemplate, "as view", "Spring MVC");

        Component errorTemplate = webApplication.addComponent("error", "Displays an error message to the user when Whitelabel error occurs", "Thymeleaf");
        errorTemplate.addTags("Thymeleaf");
        adminController.uses(errorTemplate, "as view", "Spring MVC");
        signupController.uses(errorTemplate, "as view", "Spring MVC");
        authorController.uses(errorTemplate, "as view", "Spring MVC");
        personalChecklistController.uses(errorTemplate, "as view", "Spring MVC");

        Component registerTemplate = webApplication.addComponent("register", "Template for users to sign up for an account", "Thymeleaf");
        registerTemplate.addTags("Thymeleaf");
        signupController.uses(registerTemplate, "as view", "Spring MVC");

        Component authorsChecklistTemplateListTemplate = webApplication.addComponent("view-all-checklist-templates", "Template for an author to view a list of their checklist templates", "Thymeleaf");
        authorsChecklistTemplateListTemplate.addTags("Thymeleaf");
        authorController.uses(authorsChecklistTemplateListTemplate, "as view", "Spring MVC");

        Component checklistTemplateTemplate = webApplication.addComponent("view-checklist-template", "Template for an author to view a checklist template", "Thymeleaf");
        checklistTemplateTemplate.addTags("Thymeleaf");
        authorController.uses(checklistTemplateTemplate, "as view", "Spring MVC");

        Component checklistTemplateTitleDescriptionTemplate = webApplication.addComponent("checklist-title-and-description", "Template for an author add a title and description when creating a checklist template", "Thymeleaf");
        checklistTemplateTitleDescriptionTemplate.addTags("Thymeleaf");
        authorController.uses(checklistTemplateTitleDescriptionTemplate, "as view", "Spring MVC");

        Component checklistTemplateTopicTemplate = webApplication.addComponent("checklist-topic", "Template for an author add topics and checklist items when creating a checklist template", "Thymeleaf");
        checklistTemplateTopicTemplate.addTags("Thymeleaf");
        authorController.uses(checklistTemplateTopicTemplate, "as view", "Spring MVC");

        Component checklistTemplateAssignUsersTemplate = webApplication.addComponent("assign-to", "Template for an author to assign users when creating a checklist template", "Thymeleaf");
        checklistTemplateAssignUsersTemplate.addTags("Thymeleaf");
        authorController.uses(checklistTemplateAssignUsersTemplate, "as view", "Spring MVC");

        Component personalChecklistListTemplate = webApplication.addComponent("personal-checklist-list", "Template for a user to view a list of their assigned checklists", "Thymeleaf");
        personalChecklistListTemplate.addTags("Thymeleaf");
        personalChecklistController.uses(personalChecklistListTemplate, "as view", "Spring MVC");

        Component viewChecklistTemplate = webApplication.addComponent("viewChecklistStarter", "Template for a user to view an assigned checklist", "Thymeleaf");
        viewChecklistTemplate.addTags("Thymeleaf");
        personalChecklistController.uses(viewChecklistTemplate, "as view", "Spring MVC");


        // Configuration components
        Component mvcConfig = webApplication.addComponent("MvcConfig", "Configuration for view controllers", "Spring MVC");
        mvcConfig.addTags("Configuration");

        Component loginSuccessHandler = webApplication.addComponent("LoginSuccessHandler", "Handles a successful login", "Spring");
        loginSuccessHandler.addTags("Configuration");
        loginSuccessHandler.uses(lockoutService, "");
        loginSuccessHandler.uses(userService, "");

        Component loginFailureHandler = webApplication.addComponent("LoginFailureHandler", "Handles a failed login", "Spring");
        loginFailureHandler.addTags("Configuration");
        loginFailureHandler.uses(lockoutService, "");
        loginFailureHandler.uses(userService, "");

        Component webSecurityConfig = webApplication.addComponent("WebSecurityConfig", "Configuration for Spring Security", "Spring Security");
        webSecurityConfig.addTags("Configuration");
        webSecurityConfig.uses(myUserDetailsService, "");
        webSecurityConfig.uses(loginSuccessHandler, "");
        webSecurityConfig.uses(loginFailureHandler, "");




        //create a SystemContext for the system
        ViewSet viewSet = workspace.getViews();
        SystemContextView contextView = viewSet.createSystemContextView(checklistApp, "context", "The System Context diagram for the ONS Checklist system.");
        contextView.addAllSoftwareSystems();
        contextView.addAllPeople();
        contextView.enableAutomaticLayout();

        ContainerView containerView = viewSet.createContainerView(checklistApp, "containers", "The Containers diagram for the ONS Checklist system.");
        containerView.addAllPeople();
        containerView.addAllSoftwareSystems();
        containerView.addAllContainers();
        containerView.enableAutomaticLayout();

        ComponentView componentView = viewSet.createComponentView(webApplication, "components", "The Components diagram for the ONS Checklist web application.");
        componentView.addAllComponents();
        componentView.addAllPeople();
        componentView.add(relationalDatabase);

        ComponentView componentViewAdmin = viewSet.createComponentView(webApplication, "admin components", "The Components diagram for the ONS Checklist web application when taking on an admin role.");
        componentViewAdmin.add(admin);
        componentViewAdmin.addAllComponents();
        componentViewAdmin.remove(authorController);
        componentViewAdmin.remove(authorService);
        componentViewAdmin.remove(authorsChecklistTemplateListTemplate);
        componentViewAdmin.remove(checklistTemplateTemplate);
        componentViewAdmin.remove(checklistTemplateTitleDescriptionTemplate);
        componentViewAdmin.remove(checklistTemplateTopicTemplate);
        componentViewAdmin.remove(checklistTemplateAssignUsersTemplate);
        componentViewAdmin.remove(personalChecklistController);
        componentViewAdmin.remove(personalChecklistService);
        componentViewAdmin.remove(personalChecklistListTemplate);
        componentViewAdmin.remove(viewChecklistTemplate);
        componentViewAdmin.remove(personalChecklistRepo);
        componentViewAdmin.remove(checklistTemplateRepo);
        componentViewAdmin.add(relationalDatabase);

        ComponentView componentViewAuthor = viewSet.createComponentView(webApplication, "author components", "The Components diagram for the ONS Checklist web application when taking on an author role.");
        componentViewAuthor.add(author);
        componentViewAuthor.addAllComponents();
        componentViewAuthor.remove(adminController);
        componentViewAuthor.remove(adminService);
        componentViewAuthor.remove(personalChecklistController);
        componentViewAuthor.remove(personalChecklistService);
        componentViewAuthor.remove(personalChecklistListTemplate);
        componentViewAuthor.remove(viewChecklistTemplate);
        componentViewAuthor.remove(personalChecklistRepo);
        componentViewAuthor.remove(userRoleFormTemplate);
        componentViewAuthor.remove(userRolesTemplate);
        componentViewAuthor.add(relationalDatabase);

        ComponentView componentViewUser = viewSet.createComponentView(webApplication, "user components", "The Components diagram for the ONS Checklist web application when taking on a user role.");
        componentViewUser.add(user);
        componentViewUser.addAllComponents();
        componentViewUser.remove(adminController);
        componentViewUser.remove(adminService);
        componentViewUser.remove(userRoleFormTemplate);
        componentViewUser.remove(userRolesTemplate);
        componentViewUser.remove(authorController);
        componentViewUser.remove(authorService);
        componentViewUser.remove(authorsChecklistTemplateListTemplate);
        componentViewUser.remove(checklistTemplateTemplate);
        componentViewUser.remove(checklistTemplateTitleDescriptionTemplate);
        componentViewUser.remove(checklistTemplateTopicTemplate);
        componentViewUser.remove(checklistTemplateAssignUsersTemplate);
        componentViewUser.remove(checklistTemplateRepo);
        componentViewUser.add(relationalDatabase);

        // link the architecture model with the code
        for (Component component : webApplication.getComponents()) {
            for (CodeElement codeElement : component.getCode()) {
                String sourcePath = codeElement.getUrl();
                if (sourcePath != null) {
                    codeElement.setUrl(
                            "https://git.cardiff.ac.uk/c1989618/ons-client-project-group-8/-/tree/master");
                }
            }
        }

        // tag and style some elements
        checklistApp.addTags("ONS Onboarding");
        webApplication.getComponents().stream().filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_MVC_CONTROLLER)).forEach(c -> c.addTags("Spring MVC Controller"));
        webApplication.getComponents().stream().filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_REST_CONTROLLER)).forEach(c -> c.addTags("Spring REST Controller"));

        webApplication.getComponents().stream().filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_SERVICE)).forEach(c -> c.addTags("Spring Service"));
        webApplication.getComponents().stream().filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_REPOSITORY)).forEach(c -> c.addTags("Spring Repository"));
        relationalDatabase.addTags("Database");

        Styles styles = viewSet.getConfiguration().getStyles();
        styles.addElementStyle("ONS Onboarding - Group 8 - Private").background("#6CB33E").color("#ffffff");
        styles.addElementStyle(Tags.PERSON).background("#519823").color("#ffffff").shape(Shape.Person);
        styles.addElementStyle(Tags.CONTAINER).background("#91D366").color("#ffffff");
        styles.addElementStyle("Database").shape(Shape.Cylinder);

        styles.addElementStyle("Spring REST Controller").background("#D4F3C0").color("#000000");

        styles.addElementStyle("Spring MVC Controller").background("#D4F3C0").color("#000000");
        styles.addElementStyle("Spring Service").background("#6CB33E").color("#000000");
        styles.addElementStyle("Spring Repository").background("#95D46C").color("#000000");
        styles.addElementStyle("Thymeleaf").background("#eeeeee").color("#000077").shape(Shape.RoundedBox);
        styles.addElementStyle("Configuration").background("#a9c299").color("#000000");

        StructurizrClient structurizrClient = new StructurizrClient(API_KEY, API_SECRET);
        structurizrClient.putWorkspace(WORKSPACE_ID, workspace);
    }

}
