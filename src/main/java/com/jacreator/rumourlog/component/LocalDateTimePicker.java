package com.jacreator.rumourlog.component;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout; // Re-import HorizontalLayout
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.component.Component; // Import for Component
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects; // Import for Objects.equals
// Removed: import com.vaadin.flow.component.orderedlayout.VerticalLayout; // Import for VerticalLayout

/**
 * A custom Vaadin component that combines a DatePicker and a TimePicker
 * to allow selection of a LocalDateTime value.
 */
public class LocalDateTimePicker extends CustomField<LocalDateTime> {

  // DatePicker component for selecting the date part
  private final DatePicker datePicker;
  // TimePicker component for selecting the time part
  private final TimePicker timePicker;

  /**
   * Constructs a new LocalDateTimePicker with a given label.
   *
   * @param label The label for the component.
   */
  public LocalDateTimePicker(String label) {
    setLabel(label); // Set the label for the custom field

    // Initialize DatePicker
    datePicker = new DatePicker();
    datePicker.setPlaceholder("Date"); // Placeholder text for date input
    datePicker.setMax(LocalDate.now()); // Set max date to today, as per original request
    datePicker.setRequired(true); // Make date selection required
    datePicker.setWidth("50%"); // Make date picker take full available width

    // Initialize TimePicker
    timePicker = new TimePicker();
    timePicker.setPlaceholder("Time"); // Placeholder text for time input
    timePicker.setRequired(true); // Make time selection required
    timePicker.setWidth("40%"); // Make time picker take 40% of the available width

    // Add change listeners to update the internal value when date or time changes
    datePicker.addValueChangeListener(event -> updateValue());
    timePicker.addValueChangeListener(event -> updateValue());

    // Create a horizontal layout to hold both pickers
    // This will place the date and time pickers side-by-side
    HorizontalLayout layout = new HorizontalLayout(datePicker, timePicker);
    layout.setSpacing(true); // Add spacing between the date and time pickers
    layout.setPadding(false); // No padding around the layout
    layout.setMargin(false); // No margin around the layout
    layout.setWidthFull(); // Make the layout take full available width, fitting within one column
    add(layout); // Add the layout to the custom field
  }

  /**
   * Constructs a new LocalDateTimePicker without a label.
   */
  public LocalDateTimePicker() {
    this(""); // Call the constructor with an empty label
  }

  /**
   * Returns the current value of the LocalDateTimePicker.
   * This method combines the selected date and time into a LocalDateTime object.
   *
   * @return The combined LocalDateTime value, or null if either date or time is
   *         not set.
   */
  @Override
  protected LocalDateTime generateModelValue() {
    LocalDate date = datePicker.getValue();
    LocalTime time = timePicker.getValue();

    // If both date and time are selected, combine them into a LocalDateTime
    if (date != null && time != null) {
      return LocalDateTime.of(date, time);
    }
    // If either date or time is missing, return null
    return null;
  }

  /**
   * Sets the value of the LocalDateTimePicker.
   * This method separates the LocalDateTime into date and time components
   * and sets them in the respective pickers.
   *
   * @param modelValue The LocalDateTime value to set.
   */
  @Override
  protected void setPresentationValue(LocalDateTime modelValue) {
    if (modelValue != null) {
      // Set the date part
      datePicker.setValue(modelValue.toLocalDate());
      // Set the time part
      timePicker.setValue(modelValue.toLocalTime());
    } else {
      // Clear both pickers if the modelValue is null
      datePicker.clear();
      timePicker.clear();
    }
  }

  /**
   * Sets the maximum selectable date for the DatePicker.
   *
   * @param maxDate The maximum LocalDate allowed.
   */
  public void setMax(LocalDate maxDate) {
    datePicker.setMax(maxDate);
  }

  /**
   * Sets whether the date and time selection is required.
   *
   * @param required True if required, false otherwise.
   */
  public void setRequired(boolean required) {
    datePicker.setRequired(required);
    timePicker.setRequired(required);
    setRequiredIndicatorVisible(required); // Show required indicator for the custom field
  }

  /**
   * Sets the placeholder text for the date input field.
   *
   * @param placeholder The placeholder text.
   */
  public void setDatePlaceholder(String placeholder) {
    datePicker.setPlaceholder(placeholder);
  }

  /**
   * Sets the placeholder text for the time input field.
   *
   * @param placeholder The placeholder text.
   */
  public void setTimePlaceholder(String placeholder) {
    timePicker.setPlaceholder(placeholder);
  }

  /**
   * Clears the selected date and time.
   */
  public void clear() {
    datePicker.clear();
    timePicker.clear();
  }
}