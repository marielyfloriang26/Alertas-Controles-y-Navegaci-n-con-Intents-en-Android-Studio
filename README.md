# Alertas, Controles y Navegación con Intents en Android Studio

Aplicación móvil desarrollada en Android Studio diseñada para interactuar con múltiples controles de interfaz, gestión de alertas personalizadas, navegación avanzada entre actividades mediante **Intents implícitos y explícitos**, y control de gestos táctiles.

---

## ¿Qué contiene la aplicación? 

1. **Selección Dinámica de Categorías (Checkboxes):**
   - Interfaz con cuatro opciones principales: **Música**, **Carro**, **Calle** y **Persona**.
   - Al marcar distintas combinaciones y presionar el botón de visualización, la aplicación cambia de manera dinámica la imagen mostrada en pantalla utilizando estructuras condicionales para reflejar la combinación seleccionada.

2. **Alert Dialogs e Información Personalizable:**
   - **Diálogo Principal ("Ver Info"):** Un `AlertDialog` personalizado que muestra una foto de perfil, el nombre del usuario, la materia y la institución educativa.
   - **Edición Rápida:** Incluye un enlace o botón dentro del mismo diálogo que despliega un segundo `AlertDialog` equipado con campos de texto (`EditText`) para actualizar la información de forma inmediata en las variables de la app.

3. **Navegación Avanzada y Transferencia de Datos (Intents Explícitos):**
   - **Segunda Activity de Resumen:** Permite viajar hacia una nueva pantalla enviando mediante un `Intent` explícito los datos acumulados del usuario y las selecciones de categorías realizadas en formato de texto concatenado.
   - **Pantalla de Edición Avanzada:** Un espacio dedicado para modificar parámetros visuales como el fondo o la foto de perfil.

4. **Integración con el Sistema (Intents Implícitos):**
   - **Compartir Contenido (`ACTION_SEND`):** Botón para exportar y compartir las selecciones actuales y datos personales a través de otras aplicaciones instaladas (correo, mensajería, etc.).
   - **Selector de Galería (`ACTION_PICK`):** Permite al usuario acceder a la galería del dispositivo para elegir o actualizar su imagen de perfil dinámicamente.

5. **Detección de Gestos Táctiles:**
   - Implementación de un detector de gestos (`GestureDetector`) para capturar un deslizamiento (*swipe*) de derecha a izquierda en cualquier área de la pantalla y cerrar la aplicación de manera fluida.

---

##  ¿Cómo funciona su flujo de uso?

1. **Pantalla Principal (Dashboard):**
   - El usuario visualiza la interfaz con los cuatro checkboxes de categorías, la imagen principal y los botones de acción rápida.
   - Marca las opciones de su preferencia (ej. *Carro* y *Música*) y presiona el botón para actualizar la imagen representativa en tiempo real.
   - Puede presionar el botón **"Ver Info"** para consultar sus datos personales en un diálogo emergente, con la opción de editarlos al instante.
   
2. **Navegación y Acciones Externas:**
   - Al pulsar el botón de resumen, se activa un **Intent explícito** que empaqueta las selecciones y abre una segunda actividad mostrando el texto consolidado (ej. *“Mi selección: carro, música”*).
   - Desde la aplicación, se puede invocar un **Intent implícito** para compartir este mismo reporte con otras apps o seleccionar una nueva foto de perfil desde el almacenamiento local del teléfono.
   
3. **Salida por Gestos:**
   - En cualquier momento, realizar un movimiento rápido de arrastre (*swipe*) hacia la izquierda sobre la pantalla activa el detector de gestos para finalizar la ejecución de la app.

---

## ¿Qué tecnologías se usaron para esto?

- **Entorno de Desarrollo:** Android Studio
- **Lenguaje:** Java / Kotlin y XML para maquetación de interfaces.
- **Componentes de UI:** `CheckBox`, `ImageView`, `TextView`, `EditText`, `Button`, y `AlertDialog` personalizados.
- **Gestión de Navegación:** `Intent` explícitos (comunicación entre Activities) e `Intents` implícitos (`ACTION_SEND`, `ACTION_PICK`).
- **Control Táctil:** `GestureDetector` para la captura e interpretación de gestos de deslizamiento (*swipe*).
