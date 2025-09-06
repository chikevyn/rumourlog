package com.jacreator.rumourlog.views;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jacreator.rumourlog.component.LocalDateTimePicker;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class RumourLogView extends VerticalLayout {
  private final List<String> Lgas = Arrays.asList("AMAC", "Bwari", "Kwali", "Nsukka", "Enugu south", "Udi");

  // Updated as per instructions
  private final List<String> signalList = Arrays.asList(
      "Any child under 15 years with sudden weakness of the limbs",
      "A cluster of persons with fever and respiratory symptoms (cough, running nose, nasal congestion, difficulty breathing etc.) in the same community within a week",
      "Any person with skin sores or other unusual symptoms, after contact with sick or dead animals",
      "Any person presenting with increased number of frequent watery stool (three or more times a day) and severe fatigue (Person is unable to go to school, work or requiring hospitalisation)",
      "Three or more children with a fever and rash in the same community within a week or children absent from a school due to the same illness in the same week",
      "Any illness or sudden death with signs/symptoms the community has not seen before",
      "Any person with fever and unexplained bleeding from body parts or unexplained death with bleeding",
      "Increase/cluster of deaths among animals/birds in a localised area (e.g., farm, park, game reserve...etc) with similar signs and symptoms",
      "Frequent abortion among animals (goat, sheep, cattle) with or without decrease in milk production",
      "Abnormal behaviour/aggression among one or more dogs in the community",
      "Unusual change in the sources of drinking water (e.g., colour, taste, odour, suspended solids)");

  // Updated as per instructions
  private final List<String> typeOfSignalList = Arrays.asList("Human", "Animal", "Environment");

  private final List<String> diseaseEventList = Arrays.asList("Increasing", "Decreasing", "Static");

  private Map<String, List<String>> wardData = new HashMap<String, List<String>>() {
    {
      put("AMAC", Arrays.asList("City Centre", "Garki", "Kabusa", "Wuse", "Gwarinpa"));
      put("Bwari", Arrays.asList("Bwari Central", "Kuduru", "Igu", "Shere", "Kawu", "Ushafa"));
      put("Kuje", Arrays.asList("Kuje", "Chibiri", "Gaube", "Kwaku")); // Fixed casing
      put("Nsukka", Arrays.asList("IBEKU", "ALOR-UNO", "EDE-UKWU", "EDE-NTA", "EDEM-ANI"));
      put("Enugu south", Arrays.asList("Akwuke", "Amechi I", "Achara Layout East", "Achara Layout West"));
      put("Udi", Arrays.asList("Oghu", "Affa", "Okpatu", "Awhum", "Ukana", "Abor"));
    }
  };

  public RumourLogView() {
    setWidthFull();
    // Add beautiful title
    H1 title = new H1("Rumour Log");
    title.addClassName("page-title");
    add(title);

    // Submit button
    Button submit = new Button("Submit form", event -> {
      Notification.show("Form submitted!");
    });

    setWidthFull();
    setJustifyContentMode(JustifyContentMode.CENTER);
    setAlignItems(Alignment.CENTER);
    setDefaultHorizontalComponentAlignment(Alignment.CENTER);

    submit.addClassName("blue-submit");
    HorizontalLayout buttonLayout = new HorizontalLayout(submit);
    buttonLayout.setWidthFull();
    buttonLayout.setJustifyContentMode(JustifyContentMode.END);
    buttonLayout.setPadding(false);
    buttonLayout.setSpacing(false);

    add(title, buildForm(), buttonLayout);
  }

  private FormLayout buildForm() {
    FormLayout form = new FormLayout();

    // community informant
    TextField communityInformant = new TextField("Name of Community Informant");

    // Telephone Number
    TextField telephoneNumber = new TextField("Telephone Number");

    ComboBox<String> stateOfResidence = new ComboBox<>("Select State");
    stateOfResidence.setItems("FCT", "Enugu");
    stateOfResidence.setRequired(true);

    // LGA Of Residence
    ComboBox<String> lgaOfResidence = new ComboBox<>("Select LGA");
    lgaOfResidence.setRequired(true);

    // Ward Of Residence
    ComboBox<String> wardOfResidence = new ComboBox<>("Select Ward");
    wardOfResidence.setRequired(true);

    // Community
    TextField communityComboBox = new TextField("Community");
    communityComboBox.setRequired(true);
    communityComboBox.setVisible(false);

    // Date of Result Available
    DatePicker dateResultAvailable = new DatePicker("Reporting Date");
    dateResultAvailable.setMax(LocalDate.now());
    dateResultAvailable.setRequired(true);

    // Changed "Type of Illness" to "Signal" (dropdown)
    ComboBox<String> illnessType = new ComboBox<>("Signal");
    illnessType.setItems(signalList);
    illnessType.setRequired(true);

    // Changed "Source of Information" to "Types of Signal" (dropdown)
    ComboBox<String> sourceOfInformation = new ComboBox<>("Types of Signal");
    sourceOfInformation.setItems(typeOfSignalList);
    sourceOfInformation.setRequired(true);

    // Other source of information removed because no longer needed
    // (kept but hidden permanently for code stability)
    TextField otherSourceOfInformation = new TextField("Other source of information, please specify");
    otherSourceOfInformation.setVisible(false);

    // date and time when did this happen
    LocalDateTimePicker whenDidThisHappen = new LocalDateTimePicker("When did this happen");
    whenDidThisHappen.setMax(LocalDate.now());
    whenDidThisHappen.setRequired(true);

    // date/time of detection
    LocalDateTimePicker dateOfDetection = new LocalDateTimePicker("Date/Time this was Detected");
    dateOfDetection.setMax(LocalDate.now());
    dateOfDetection.setRequired(true);

    TextField peopleAffected = new TextField("How many people have been affected?");

    RadioButtonGroup<String> deathResult = new RadioButtonGroup<>();
    deathResult.setLabel("Has anyone died? If Yes, please specify");
    deathResult.setItems("Yes", "No");

    TextField deathDetails = new TextField("If Yes, please specify");
    deathDetails.setVisible(false);

    RadioButtonGroup<String> animalResult = new RadioButtonGroup<>();
    animalResult.setLabel("Are there sick or dead animals involved?");
    animalResult.setItems("Yes", "No");

    RadioButtonGroup<String> diseaseEvent = new RadioButtonGroup<>();
    diseaseEvent.setLabel("Is the event ongoing as at the time of this report?");
    diseaseEvent.setItems("Yes", "No");

    ComboBox<String> diseaseEventDetails = new ComboBox<>("If Yes, please specify");
    diseaseEventDetails.setItems(diseaseEventList);
    diseaseEventDetails.setVisible(false);

    TextField actionTaken = new TextField("What action has been taken");

    // Changed label as per instruction
    RadioButtonGroup<String> healthFacilityCheck = new RadioButtonGroup<>();
    healthFacilityCheck.setLabel("Has this been verified by a DSNO?");
    healthFacilityCheck.setItems("Yes", "No");

    // Dynamic logic: Update LGA options based on state
    stateOfResidence.addValueChangeListener(e -> {
      if ("FCT".equals(e.getValue())) {
        lgaOfResidence.setItems("AMAC", "Bwari", "Kwali", "Kuje");
      } else if ("Enugu".equals(e.getValue())) {
        lgaOfResidence.setItems("Nsukka", "Enugu south", "Udi");
      } else {
        lgaOfResidence.clear();
      }
    });
    lgaOfResidence.addValueChangeListener(e -> {
      String lga = e.getValue();
      if (lga != null && wardData.containsKey(lga)) {
        wardOfResidence.setItems(wardData.get(lga));
      } else {
        wardOfResidence.clear();
        wardOfResidence.setItems();
      }
    });

    // Show community field when a ward is selected
    wardOfResidence.addValueChangeListener(e -> {
      String ward = e.getValue();
      if (ward != null && !ward.isEmpty()) {
        communityComboBox.setVisible(true);
        communityComboBox.clear();
      } else {
        communityComboBox.setVisible(false);
        communityComboBox.clear();
      }
    });

    // Show death details only if "Yes"
    deathResult.addValueChangeListener(e -> {
      if ("Yes".equals(e.getValue())) {
        deathDetails.setVisible(true);
      } else {
        deathDetails.setVisible(false);
      }
    });

    diseaseEvent.addValueChangeListener(e -> {
      if ("Yes".equals(e.getValue())) {
        diseaseEventDetails.setVisible(true);
      } else {
        diseaseEventDetails.setVisible(false);
      }
    });

    form.setResponsiveSteps(
        new FormLayout.ResponsiveStep("0", 1),
        new FormLayout.ResponsiveStep("600px", 2),
        new FormLayout.ResponsiveStep("700px", 3));

    form.add(communityInformant, telephoneNumber,
        stateOfResidence, lgaOfResidence,
        wardOfResidence, communityComboBox,
        dateResultAvailable, illnessType,
        sourceOfInformation, otherSourceOfInformation, whenDidThisHappen,
        dateOfDetection, peopleAffected, deathResult,
        deathDetails, animalResult, diseaseEvent,
        diseaseEventDetails, actionTaken, healthFacilityCheck);

    return form;
  }
}
