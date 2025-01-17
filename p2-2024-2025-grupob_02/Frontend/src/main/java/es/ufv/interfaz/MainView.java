package es.ufv.interfaz;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import es.ufv.model.Turismo;
import es.ufv.model.Comunidad;
import es.ufv.model.Periodo;
import es.ufv.services.TurismoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Route
public class MainView extends VerticalLayout {

    private final Grid<Turismo> grid = new Grid<>(Turismo.class);
    private final Grid<Map.Entry<String, List<Comunidad>>> comunidadesGrid = new Grid<>();
    private final Grid<Turismo> gridAgrupadas = new Grid<>(Turismo.class);
    private final TurismoService turismoService;
    private final DatePicker datePicker = new DatePicker("Filtrar por fecha");
    private final ComboBox<String> comunidadesAgrupadasComboBox = new ComboBox<>("Seleccionar Comunidad Destino");
    private List<Turismo> originalItems;
    private Map<String, List<Turismo>> comunidadesAgrupadas;

    public MainView(@Autowired TurismoService service) {
        this.turismoService = service;

        // Tabs para cambiar entre vistas
        Tab turismoTab = new Tab("Turismo");
        Tab comunidadesTab = new Tab("Comunidades Agrupadas");
        Tabs tabs = new Tabs(turismoTab, comunidadesTab);

        // Contenedor principal para las vistas
        VerticalLayout turismoLayout = crearTurismoLayout();
        VerticalLayout comunidadesLayout = crearComunidadesLayout();

        tabs.addSelectedChangeListener(event -> {
            removeAll();
            add(tabs); // Reañadir las pestañas
            if (event.getSelectedTab().equals(turismoTab)) {
                add(turismoLayout);
            } else if (event.getSelectedTab().equals(comunidadesTab)) {
                add(comunidadesLayout);
            }
        });

        // Añadir elementos iniciales
        add(tabs, turismoLayout); // Mostrar turismo por defecto
    }

    private VerticalLayout crearTurismoLayout() {
        VerticalLayout layout = new VerticalLayout();

        // Configuración inicial del grid
        grid.removeAllColumns();
        grid.addColumn(turismo -> {
            Comunidad origen = turismo.getComunidadOrigen();
            return (origen != null && origen.getProvincia() != null) ? origen.getProvincia() : "Sin dato";
        }).setHeader("Provincia Origen");

        grid.addColumn(turismo -> {
            Comunidad destino = turismo.getComunidadDestino();
            return (destino != null && destino.getComunidad() != null) ? destino.getComunidad() : "Sin dato";
        }).setHeader("Comunidad Origen");

        grid.addColumn(turismo -> {
            Comunidad destino = turismo.getComunidadDestino();
            return (destino != null && destino.getProvincia() != null) ? destino.getProvincia() : "Sin dato";
        }).setHeader("Provincia Destino");

        grid.addColumn(turismo -> {
            Periodo periodo = turismo.getPeriodo();
            return (periodo != null && periodo.getNombre() != null) ? periodo.getNombre() : "Sin dato";
        }).setHeader("Periodo");

        grid.addColumn(turismo -> Integer.toString(turismo.getTotal())).setHeader("Total");

        grid.addComponentColumn(turismo -> {
            Button deleteButton = new Button("Eliminar");
            deleteButton.addClickListener(event -> {
                BotonBorrarTurismo(turismo.getId());
            });
            return deleteButton;
        }).setHeader("Eliminar");

        refreshGrid();

        // Añadir funcionalidad al DatePicker
        datePicker.addValueChangeListener(event -> filterGridByDate(event.getValue()));

        // Botón "Nuevo" debajo de la tabla centrado
        Button addButton = new Button("Nuevo", e -> BotonNuevoTurismo());
        HorizontalLayout addButtonLayout = new HorizontalLayout(addButton);
        addButtonLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        // Layout principal
        HorizontalLayout toolbar = new HorizontalLayout(datePicker);
        layout.add(toolbar, grid, addButtonLayout);

        // Listener para editar con doble clic
        grid.addItemDoubleClickListener(event -> openEditor(event.getItem()));

        return layout;
    }

    private VerticalLayout crearComunidadesLayout() {
        VerticalLayout layout = new VerticalLayout();

        // Configuración del ComboBox para comunidades agrupadas
        comunidadesAgrupadas = turismoService.getComunidadesAgrupadas();
        List<String> comunidadesKeys = comunidadesAgrupadas.keySet().stream()
                .filter(key -> comunidadesAgrupadas.get(key) != null && !comunidadesAgrupadas.get(key).isEmpty())
                .filter(key -> comunidadesAgrupadas.get(key).stream()
                        .anyMatch(turismo -> turismo.getComunidadDestino() != null &&
                                turismo.getComunidadDestino().getComunidad() != null &&
                                !turismo.getComunidadDestino().getComunidad().isEmpty()))
                .sorted()
                .collect(Collectors.toList());

        comunidadesAgrupadasComboBox.setItems(comunidadesKeys);
        comunidadesAgrupadasComboBox.addValueChangeListener(event -> {
            String selectedKey = event.getValue();
            if (selectedKey != null) {
                List<Turismo> turismos = comunidadesAgrupadas.get(selectedKey);
                gridAgrupadas.setItems(turismos);
            } else {
                gridAgrupadas.setItems();
            }
        });

        // Configuración del grid para mostrar los datos agrupados
        gridAgrupadas.removeAllColumns();
        gridAgrupadas.addColumn(turismo -> {
            Comunidad destino = turismo.getComunidadDestino();
            return (destino != null && destino.getComunidad() != null) ? destino.getComunidad() : "Sin dato";
        }).setHeader("Comunidad Destino");

        gridAgrupadas.addColumn(turismo -> turismo.getPeriodo().getNombre()).setHeader("Periodo");
        gridAgrupadas.addColumn(turismo -> Integer.toString(turismo.getTotal())).setHeader("Total");

        layout.add(comunidadesAgrupadasComboBox, gridAgrupadas);
        return layout;
    }

    private void refreshGrid() {
        originalItems = turismoService.getAllTurismo();
        grid.setItems(originalItems);
    }

    private void filterGridByDate(LocalDate selectedDate) {
        if (selectedDate == null) {
            grid.setItems(originalItems);
            return;
        }
        String anio = Integer.toString(selectedDate.getYear());
        String mes = String.format("%02d", selectedDate.getMonthValue());

        String dateAPeriodo = anio + "M" + mes;

        List<Turismo> filteredItems = originalItems.stream()
                .filter(item -> {
                    String itemPeriod = item.getPeriodo() != null ? item.getPeriodo().getNombre() : null;
                    return itemPeriod != null && itemPeriod.equals(dateAPeriodo);
                })
                .collect(Collectors.toList());

        grid.setItems(filteredItems);
    }

    private void openEditor(Turismo turismo) {
        Dialog dialog = new Dialog();
        FormLayout form = new FormLayout();

        TextField comunidadOrigenField = new TextField("Comunidad Origen");
        comunidadOrigenField.setValue(turismo.getComunidadOrigen() != null ? turismo.getComunidadOrigen().getComunidad() : "");

        TextField comunidadDestinoField = new TextField("Comunidad Destino");
        comunidadDestinoField.setValue(turismo.getComunidadDestino() != null ? turismo.getComunidadDestino().getComunidad() : "");

        TextField provinciaOrigenField = new TextField("Provincia Origen");
        provinciaOrigenField.setValue(turismo.getComunidadOrigen() != null ? turismo.getComunidadOrigen().getProvincia() : "");

        TextField provinciaDestinoField = new TextField("Provincia Destino");
        provinciaDestinoField.setValue(turismo.getComunidadDestino() != null ? turismo.getComunidadDestino().getProvincia() : "");

        TextField periodoField = new TextField("Periodo");
        periodoField.setValue(turismo.getPeriodo() != null ? turismo.getPeriodo().getNombre() : "");

        TextField totalField = new TextField("Total");
        totalField.setValue(Integer.toString(turismo.getTotal()));

        form.add(comunidadOrigenField, comunidadDestinoField, provinciaOrigenField, provinciaDestinoField, periodoField, totalField);

        Button saveButton = new Button("Aceptar", e -> {
            turismo.setComunidadOrigen(new Comunidad(comunidadOrigenField.getValue(), provinciaOrigenField.getValue()));
            turismo.setComunidadDestino(new Comunidad(comunidadDestinoField.getValue(), provinciaDestinoField.getValue()));
            turismo.setPeriodo(new Periodo(periodoField.getValue()));
            turismo.setTotal(Integer.parseInt(totalField.getValue()));
            if (turismo.getId() == null) {
                turismo.setId(UUID.randomUUID());
                turismoService.createTurismo(turismo);

            } else {
                turismoService.updateTurismo(turismo.getId(), turismo);
            }
            dialog.close();
            refreshGrid();
        });

        Button cancelButton = new Button("Cancelar", e -> dialog.close());
        VerticalLayout formLayout = new VerticalLayout(
                form,
                new HorizontalLayout(saveButton, cancelButton)
        );
        dialog.add(formLayout);
        dialog.open();
    }

    private void BotonNuevoTurismo() {
        Dialog dialog = new Dialog();

        TextField comunidadOrigenField = new TextField("Comunidad Origen");
        TextField provinciaOrigenField = new TextField("Provincia Origen");
        TextField comunidadDestinoField = new TextField("Comunidad Destino");
        TextField provinciaDestinoField = new TextField("Provincia Destino");
        TextField periodoField = new TextField("Periodo (ejemplo: 2024M06)");
        TextField totalField = new TextField("Total");

        Button saveButton = new Button("Guardar", event -> {
            // Validación de entradas
            if (comunidadOrigenField.isEmpty() || provinciaOrigenField.isEmpty() ||
                    comunidadDestinoField.isEmpty() || provinciaDestinoField.isEmpty() ||
                    periodoField.isEmpty() || totalField.isEmpty()) {
                Notification.show("Todos los campos son obligatorios.");
                return;
            }

            int total;
            try {
                total = Integer.parseInt(totalField.getValue());
            } catch (NumberFormatException e) {
                Notification.show("El campo Total debe ser un número válido.");
                return;
            }

            // Creación de objetos
            Comunidad comunidadOrigen = new Comunidad(comunidadOrigenField.getValue(), provinciaOrigenField.getValue());
            Comunidad comunidadDestino = new Comunidad(comunidadDestinoField.getValue(), provinciaDestinoField.getValue());
            Periodo periodo = new Periodo(periodoField.getValue());

            Turismo turismoNuevo = new Turismo(comunidadOrigen, comunidadDestino, periodo, total);

            // Manejo de excepciones al guardar
            try {
                turismoService.createTurismo(turismoNuevo);
                originalItems.add(turismoNuevo);
                refreshGrid();
                Notification.show("Nuevo turismo añadido con éxito.");
                dialog.close();
            } catch (Exception e) {
                Notification.show("Error al guardar el turismo: " + e.getMessage());
            }
        });

        Button cancelButton = new Button("Cancelar", event -> dialog.close());

        VerticalLayout formLayout = new VerticalLayout(
                comunidadOrigenField,
                provinciaOrigenField,
                comunidadDestinoField,
                provinciaDestinoField,
                periodoField,
                totalField,
                new HorizontalLayout(saveButton, cancelButton)
        );

        dialog.add(formLayout);
        dialog.open();
    }

    private void BotonBorrarTurismo(UUID id) {
        turismoService.deleteTurismo(id);
        originalItems.remove(turismoService.getTurismoById(id)); // Elimina de la lista local del front
        refreshGrid();
    }
}