package gui.performance;

import datalink.CRUDPerformance;
import datalink.CRUDPerformanceType;
import gui.DateDeselectedListener;
import gui.DateListener;
import gui.DatePicker;
import gui.EmployeeSelectedListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.Employee;
import model.Performance;
import model.PerformanceType;

/**
 *
 * @author Saleh
 */
public class PerformanceInput
        implements
        EmployeeSelectedListener,
        PerformanceSubmittedListener,
        PerformanceDisplayModeListener,
        RowClickedListener {

    private JPanel mainPanel;
    private JPanel panelStoryInputs;
    private JPanel panelMetaInputs;
    private JLabel lbTime;
    private JTextField tfTime;
    private DatePicker datePicker;
    private JComboBox comboStateOfPerformance;
    private JComboBox comboType;
    List<PerformanceType> performanceTypes;
    private PerformanceType selectedPerformanceType;
    private JLabel lbAmount;
    private JTextField tfAmount;
    private JTextField tfTitle;
    private JScrollPane scrollableTextArea;
    private JTextArea taDescription;
    private Color colorFieldRight;
    private Color colorFieldWrong;
    private Color colorDisabled;
    private boolean boolTfTimeFilled;
    private boolean boolDateFilled;
    private boolean boolComboStateFilled;
    private boolean boolComboTypeFilled;
    private boolean boolTfAmountFilled;
    private boolean descriptionDisplayAbility;

    public PerformanceInput() {

        panelMetaInputs = new JPanel();

        GridBagConstraints c;

        colorFieldRight = new Color(226, 252, 237);
        colorFieldWrong = new Color(254, 225, 214);
        colorDisabled = new Color(105, 105, 105);

        lbTime = new JLabel("Time:");
        panelMetaInputs.add(lbTime);

        DocumentRegex docRegx = new DocumentRegex();

        tfTime = new JTextField();
        tfTime.setFont(new Font("SansSerif", Font.BOLD, 12));
        tfTime.setBackground(colorFieldRight);
        tfTime.getDocument().addDocumentListener(docRegx);
        tfTime.setPreferredSize(new Dimension(60, 27));
        panelMetaInputs.add(tfTime);

        datePicker = new DatePicker();
        datePicker.setTodayAsDefault();
        boolDateFilled = true;
        DateListenerImpli dateListenerImpli = new DateListenerImpli();
        datePicker.addDateListener(dateListenerImpli);
        datePicker.addDateDeselectedListener(dateListenerImpli);
        panelMetaInputs.add(datePicker.getDatePicker());

        ItemChangeListener comboBoxListener = new ItemChangeListener();
        comboStateOfPerformance = new JComboBox<>(new String[]{"Select...", "Positive", "Negative"});
        comboStateOfPerformance.addItemListener(comboBoxListener);
        comboStateOfPerformance.setSelectedIndex(0);
        panelMetaInputs.add(comboStateOfPerformance);

        comboType = new JComboBox<>();
        comboType.addItemListener(comboBoxListener);
        comboType.setPreferredSize(new Dimension(145, 25));
        panelMetaInputs.add(comboType);

        lbAmount = new JLabel("Amount+:");
        panelMetaInputs.add(lbAmount);

        tfAmount = new JTextField();
        tfAmount.setFont(new Font("SansSerif", Font.BOLD, 12));
        tfAmount.setBackground(colorFieldRight);
        tfAmount.getDocument().addDocumentListener(docRegx);
        tfAmount.setPreferredSize(new Dimension(95, 27));
        panelMetaInputs.add(tfAmount);

        panelStoryInputs = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        tfTitle = new JTextField();
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        panelStoryInputs.add(tfTitle, c);

        c = new GridBagConstraints();
        taDescription = new JTextArea();
        scrollableTextArea = new JScrollPane(taDescription);
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        panelStoryInputs.add(scrollableTextArea, c);

        mainPanel = new JPanel(new GridBagLayout());

        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 30, 5, 30);
        c.weightx = 1.0;
        mainPanel.add(panelMetaInputs, c);

        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 30, 5, 30);
        c.weightx = 1.0;
        c.weighty = 1.0;
        mainPanel.add(panelStoryInputs, c);
    }

    public JPanel getPerformanceInputsPanel() {
        return mainPanel;
    }

    @Override
    public void employeeSelected(Employee employee) {
//        System.out.println(employee.getName());
    }

    @Override
    public void employeeDeselected() {
        //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getTime() {
        return tfTime.getText();
    }

    public LocalDate getDate() {
        return datePicker.getDate();
    }

    public boolean getStateOfPerformance() {

        String stateName = (String) comboStateOfPerformance.getSelectedItem();
        Boolean state = null;

        if (stateName.equals("Positive")) {
            state = Boolean.TRUE;
        } else if (stateName.equals("Negative")) {
            state = Boolean.FALSE;
        }
        return state;
    }

    public PerformanceType getPerformanceType() {
        return this.selectedPerformanceType;
    }

    public double getAmount() {
        return Double.parseDouble(tfAmount.getText());
    }

    public String getTitle() {
        return tfTitle.getText();
    }

    public String getDescription() {
        if (taDescription.getText().isBlank()) {
            // Useful for database empty entry to be null.
            return null;
        }
        return taDescription.getText();
    }

    public boolean getBoolTfTimeFilled() {
        return boolTfTimeFilled;
    }

    public boolean getBoolDateFilled() {
        return boolDateFilled;
    }

    public boolean getBoolComboState() {
        return boolComboStateFilled;
    }

    public boolean getBoolComboType() {
        return boolComboTypeFilled;
    }

    public boolean getBoolTfAmountFilled() {
        return boolTfAmountFilled;
    }

    protected void clearInputFields() {
        tfTime.setText(null);
        boolTfTimeFilled = false;
        datePicker.setTodayAsDefault();
        // Setting comboStateOfPerformance selected index to zero
        // invokes ItemListener methods which contains the code
        // to set the other linked combo box to zero
        // and also the boolean values to false.
        comboStateOfPerformance.setSelectedIndex(0);
        tfAmount.setText(null);
        boolTfAmountFilled = false;
        tfTitle.setText(null);
        taDescription.setText(null);
    }

    private void setFieldsEditable(boolean editable) {

        tfTime.setEditable(editable);
        tfTime.setForeground(editable ? null : colorDisabled);
        datePicker.setEnabled(editable);
        comboStateOfPerformance.setEnabled(editable);
        comboStateOfPerformance.setForeground(editable ? null : colorDisabled);
        comboType.setEnabled(editable);
        comboType.setForeground(editable ? null : colorDisabled);
        tfAmount.setEditable(editable);
        tfAmount.setForeground(editable ? null : colorDisabled);
        tfTitle.setEditable(editable);
        taDescription.setEditable(editable);
    }

    @Override
    public void performanceSubmitted() {
        clearInputFields();
    }

    @Override
    public void performanceDisplayable() {
        descriptionDisplayAbility = true;
        clearInputFields();
        setFieldsEditable(false);
    }

    @Override
    public void performanceUnDisplayable() {
        descriptionDisplayAbility = false;
        clearInputFields();
        setFieldsEditable(true);
    }

    @Override
    public void rowClickedWithRecordId(int id) {

        if (descriptionDisplayAbility) {

            Performance performance = CRUDPerformance.getById(id);

            LocalDateTime ldt = performance.getDateTime();

            String localTime12 = ldt.toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a"));

            tfTime.setText(localTime12);
            datePicker.setDateValue(ldt.toLocalDate());

            if (performance.getState()) {
                comboStateOfPerformance.setSelectedIndex(1);
            } else {
                comboStateOfPerformance.setSelectedIndex(2);
            }
            comboType.getModel().setSelectedItem(CRUDPerformanceType.getById(performance.getTypeId()));
            tfAmount.setText(String.valueOf(performance.getAmount()));
            tfTitle.setText(performance.getTitle());
            taDescription.setText(performance.getDescription());
            String description = performance.getDescription();
            if (description == null || description.isBlank()) {
                taDescription.setText("No description available!");
            } else {
                taDescription.setText(description);
            }
        }
    }

    private class DateListenerImpli implements DateListener, DateDeselectedListener {

        @Override
        public void dateChanged(LocalDate date) {
            boolDateFilled = true;
        }

        @Override
        public void dateDeselected() {
            boolDateFilled = false;
        }
    }

    class DocumentRegex implements DocumentListener {

        private void doWork() {

            if (tfTime.getText().isEmpty()) {
                tfTime.setBackground(colorFieldRight);
            } else {
                if (tfTime.getText().matches("^(0[1-9]|1[0-2]):([0-5][0-9]) ((a|p)m|(A|P)M)$")) {
                    tfTime.setBackground(colorFieldRight);
                    boolTfTimeFilled = true;
                } else {
                    tfTime.setBackground(colorFieldWrong);
                    boolTfTimeFilled = false;
                }
            }

            if (tfAmount.getText().isEmpty()) {
                tfAmount.setBackground(colorFieldRight);
            } else {
                // Regex match DECIMAL(12,3)
                if (tfAmount.getText().matches("^\\d{0,9}(?:(?<=\\d)\\.(?=\\d)\\d{0,3})?$")) {
                    tfAmount.setBackground(colorFieldRight);
                    boolTfAmountFilled = true;
                } else {
                    tfAmount.setBackground(colorFieldWrong);
                    boolTfAmountFilled = false;
                }
            }
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            doWork();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            doWork();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            doWork();
        }

    }

    private void populateComboTypes(boolean state) {
        performanceTypes = CRUDPerformanceType.getByState(state);
        comboType.removeAllItems();
        comboType.addItem(new PerformanceType(0, "Select...", null));

        performanceTypes.stream().forEach(pType -> {
            comboType.addItem(new PerformanceType(pType.getId(), pType.getType(), pType.getState()));
        });
        boolComboStateFilled = true;
    }

    class ItemChangeListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent event) {
            Object source = event.getSource();
            if (source == comboStateOfPerformance) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    if (event.getItem().equals("Negative")) {
                        populateComboTypes(false);
                    } else if (event.getItem().equals("Positive")) {
                        populateComboTypes(true);
                    } else if (comboStateOfPerformance.getSelectedIndex() == 0) {
                        // If the index is zero, then it is the label
                        // that tells the user to select from the dropdown.
                        // so the value is not yet selected to an effective value.
                        comboType.removeAllItems();
                        boolComboStateFilled = false;
                        boolComboTypeFilled = false;
                    }
                }
            } else if (source == comboType) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    if (comboType.getSelectedIndex() == 0) {
                        // If the index is zero, then it is the label
                        // that tells the user to select from the dropdown.
                        // so the value is not yet selected to an effective value.
                        boolComboTypeFilled = false;
                        selectedPerformanceType = null;
                    } else {
                        selectedPerformanceType = (PerformanceType) event.getItem();
                        boolComboTypeFilled = true;
                    }
                }
            }
        }
    }
}
