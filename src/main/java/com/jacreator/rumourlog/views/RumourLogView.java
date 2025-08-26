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
  private final List<String> sourceList = Arrays.asList("Observed", "Print and Media", "Facebook", "Twitter",
      "WhatsApp", "Other");
  private final List<String> diseaseEventList = Arrays.asList("Increasing", "Decreasing", "Static");

  private Map<String, List<String>> wardData = new HashMap<String, List<String>>() {
    {
      put("AMAC", Arrays.asList("City Centre", "Garki", "Kabusa", "Wuse", "Gwarinpa"));
      put("Bwari", Arrays.asList("Bwari Central", "Kuduru", "Igu", "Shere", "Kawu", "Ushafa"));
      put("KUJE", Arrays.asList("Kuje", "Chibiri", "Gaube", "Kwaku"));
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
      // Here, collect and process form data as needed
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

    // Illness Type
    TextField illnessType = new TextField("Type of Illness");

    ComboBox<String> sourceOfInformation = new ComboBox<>("Source of Information");
    sourceOfInformation.setItems(sourceList);
    sourceOfInformation.setRequired(true);

    // Other source of information, please specify
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

    RadioButtonGroup<String> healthFacilityCheck = new RadioButtonGroup<>();
    healthFacilityCheck.setLabel("Has this been verified by a health facility?");
    healthFacilityCheck.setItems("Yes", "No");

    // Dynamic logic: Update LGA options based on state
    stateOfResidence.addValueChangeListener(e -> {
      if ("FCT".equals(e.getValue())) {
        lgaOfResidence.setItems("AMAC", "Bwari", "Kwali");
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
        // Optionally, you can set a default value or clear the field
        communityComboBox.clear();
      } else {
        communityComboBox.setVisible(false);
        communityComboBox.clear();
      }
    });

    sourceOfInformation.addValueChangeListener(e -> {
      String source = e.getValue();
      if ("Other".equals(source)) {
        otherSourceOfInformation.setVisible(true);
      } else {
        otherSourceOfInformation.setVisible(false);
      }
    });

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
