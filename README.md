# GeoRegistro

## Descripción
Aplicación Android desarrollada en Java para el registro de incidencias georreferenciadas. Permite capturar datos en terreno, guardarlos en la nube y visualizarlos en un mapa.

## Versión de Android Studio
Desarrollado y probado en Android Studio Ladybug / Koala (API 34+).

## Instrucciones de Ejecución
1. Clonar el repositorio en Android Studio.
2. Sincronizar el proyecto con Gradle.
3. Conectar un dispositivo físico o emulador (preferiblemente dispositivo físico para probar el sensor acelerómetro).
4. Ejecutar la aplicación (Run 'app').

## Configuración de Firebase y Google Maps
- **Firebase:** El proyecto está conectado a Firestore Cloud. El archivo `google-services.json` está integrado en el proyecto, pero la base de datos fue securizada mediante reglas restrictivas directamente en la consola de Firebase.
- **Google Maps:** La lógica de Google Maps (marcadores y captura de coordenadas) está 100% implementada. Sin embargo, por las restricciones de facturación de Google Cloud (no me acepto las tarjetas), no se generó una API Key definitiva. 
  * **Para evaluar el mapa:** Se debe ir al archivo `AndroidManifest.xml` y reemplazar el valor `"ClaveApiNoGenerada"` por su propia clave en la etiqueta `<meta-data android:name="com.google.android.geo.API_KEY".../>`.

## Capturas de Pantalla
<img width="238" height="518" alt="image" src="https://github.com/user-attachments/assets/8f15f18a-4442-4142-b23b-3a88a2a8e26a" />
<img width="231" height="515" alt="image" src="https://github.com/user-attachments/assets/51ed872c-a8b4-404e-9add-66f92a2eade6" />
<img width="231" height="513" alt="image" src="https://github.com/user-attachments/assets/1d38f76d-b859-4dc1-afdc-c595b4c73dad" />
