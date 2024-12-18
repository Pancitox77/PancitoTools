
# Introducción

Esta es una librería Java con múltiples utilidades.

Los nodos y clases de interfaz gráfica usan JavaFX.

> La mayoría de las clases que utilizan archivos externos se apoyan de
> la clase `Resources`. Se recomienda que se configuren las rutas antes de usar cualquier clase.
> La clase Resources convierte una ruta relativa (dentro de `resources/` como base) a una ruta absoluta.

Aunque no es obligatorio, se recomienda crear la carpeta `i18n/` con un archivo `messages.properties` dentro de `resources/`, para evitar excepciones por traducciones. (Ruta: `resources/i18n/messages.properties`).

## Versión

Este proyecto usa JavaFX 23.0.1 y Java 21.

---

# Herramientas

## Manejo de archivos

- **JsonTools**:
    - Todos sus métodos son *estáticos*.
    - Funciones de lectura/escritura de archivos json.
    - Principalmente utiliza la carpeta *resources/json/* configurada en Resources.java.

## UI

### Nodos

Son nodos propios que no hay en JavaFX por defecto.

- **CircularProgress**: Es una barra de progreso que en lugar de completar una barra lineal,
    va completando el círculo. Funciona como una barra de progreso estándar.

### Transiciones

Son animaciones y efectos.

- La clase `Transitions` funciona de manera estática y provee transiciones entre 2 o más nodos.
    Útil para transiciones entre escenas.
- `NumberPropertyTransition` funciona como una animación de transición de propiedad (establecer
    un valor dentro de un cierto tiempo) para propiedades numéricas.
- `StringPropertyTransition` es similar a NumberPropertyTransition, con la diferencia que va
    cambiando un String, completando las letras faltantes.

## Clases

> [!WARNING] El paquete **signals** está incompleto y no es funcional.

- **Jsonizable**: Interfaz que indica que una clase se puede convertir a su representación JSON.
    - Utiliza el paquete GSON.

### Signals

Las señales funcionan como un sistema de programación reactiva.
Se definen dos funciones, la función emisora y la función receptora.

La función emisora es la que envía la señal, y la función receptora es la que recibe la señal.
Una función emisora emite su señal cuando esta función es llamada.
Una función receptora recibe la señal cuando la función emisora es llamada. Se puede configurar
para que la función receptora se ejecute en un momento determinado (antes o después de ejecutar la función emisora).

## Otras utilidades

- **Lists**: Realiza operaciones a listas. Clase estática.
    - convertir lista a string y viceversa.
    - comprobar que un elemento esté dentro de la lista.
    - comprobar que un elemento es el último de la lista.
    - ordenar una lista de strings por orden alfabético.
- **Resources**: Obtiene recursos internos desde una ruta relativa. Clase estática.
    - Clase principal donde se apoya la mayor parte de la librería.
    - Obtiene distintos tipos de objetos según el tipo, por ejemplo,
        - una Image para imágenes,
        - una URL para vistas FXML,
        - ruta formateada para archivos css,
        - etc
    - Por defecto todas las rutas están inicializadas (si la carpeta no existe, dará error al usar un método relacionado).
    - Además, puede cambiar una vista fxml por otra. Esta transición no tiene animaciones.
- **Strings**: Realizar operaciones sobre strings. Clase estática.
    - convertir a mayúsculas la primera letra de un string.
    - convertir a mayúsculas la primera letra de cada palabra de un string.
- **Translator**: Obtiene traducciones a partir de llaves. Internacionalización. Clase estática.
    - Las traducciones se obtienen de *resources/i18n/messages.properties*.
        - No depende de la clase `Resources`.
    - Las traducciones se extraen de un archivos de propiedades, no se traduce como tal.