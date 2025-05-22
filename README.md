# Proyecto: Vinilos

Este proyecto es una aplicación móvil desarrollada en **Kotlin**, siguiendo el patrón de arquitectura **MVVM** y utilizando un **Repository Pattern** y **Service Adapter** para la gestión de datos y conexión con la API. La aplicación hasta este punto visualizar un catálogo de álbumes de manera fluida y moderna.


---
# !! Aviso Importante

Al momento de esta entrega, la URL del backend, que originalmente estaba expuesta públicamente (https://backvynils-q6yc.onrender.com/albums), no está funcionando correctamente. El APK, que se encuentra en la carpeta `app/releases/vinilos-Sprint2.apk`, se generó manteniendo la URL de acceso público para garantizar que, en caso de que se solucione el problema, el APK funcione y se conecte correctamente al API en un dispositivo físico.

Sin embargo, en el código principal del repositorio, la URL que conecta al backend se ha actualizado a `10.0.2.2:3000` para facilitar las pruebas locales.(ya que esta es la IP que se expone en un simulador de Android). Para que la aplicación funcione correctamente de manera local, debes asegurarte de seguir los pasos de la sección del **README** para levantar el backend localmente, ya que esta es la URL que el código utilizará durante el desarrollo.



## Requisitos Previos

Antes de correr el proyecto de manera local, asegúrate de tener instalado:

- **Android Studio** (preferentemente versión Electric Eel o superior)
- **JDK** 11 o superior
- **Gradle** (Android Studio ya maneja Gradle de manera interna)
- **Dispositivo físico** o **emulador** configurado para pruebas
- **Conexión a internet**

## Tecnologías Principales

- Kotlin
- Espresso

## Cómo correr el Backend

El backend de la aplicación está disponible en el siguiente repositorio:

[https://github.com/TheSoftwareDesignLab/BackVynils](https://github.com/TheSoftwareDesignLab/BackVynils)

### 1. Clona el repositorio del backend

```bash
git clone https://github.com/TheSoftwareDesignLab/BackVynils.git
```

### 2. Asegúrate de tener Node.js v18 instalado

Puedes verificar la versión de Node.js con el siguiente comando: ``` node -v ```

### 3. Instala las dependencias del backend

Navega al directorio del proyecto y ejecuta el siguiente comando para instalar todas las dependencias necesarias:

``` cd BackVynils ```


``` npm install ```

### 4. Configura la Base de Datos

- En PgAdmin crea una base de datos en alguno de tus servidores con el nombre ```vynils```

- En el repositorio del backend en el archivo ```ormconfig.json``` ubicado en la raiz del repositorio actualiza las credenciales de postgres con tus datos para que se conecten a tu base de datos local


### 5. Ejecuta el BackEnd

Una vez instaladas las dependencias, ejecuta el siguiente comando para iniciar el servidor:

``` npm run start ```

El backend se desplegará en http://localhost:3000.

### 6. (Opcional) Cambiar la URL de conexión en el frontend

Si necesitas cambiar la URL de conexión entre el frontend y el backend, sigue estos pasos:

- Abre el archivo NetworkServiceAdapter.kt en el siguiente directorio:
```Vinilos-MISW-4203/app/src/main/java/com/app/vinilos_misw4203/network/NetworkServiceAdapter.kt```

- En la línea 20, cambia la variable BASE_URL para que apunte a la URL correcta del backend, recuerda que la IP que se debe colocar es la que se genera entre el emulador y tu PC:

    ```const val BASE_URL = "http://10.0.2.2:3000"  // Cambia aquí si es necesario```


## Cómo correr el proyecto localmente

### 1. Clona el repositorio

```bash
git clone <https://github.com/dacostaortiz/Vinilos-MISW-4203.git>

```

### 2. Abre el proyecto en Android Studio

- Selecciona File > Open y navega a la carpeta del proyecto que clonaste.

- Android Studio detectará automáticamente el proyecto como un proyecto de Android.

---
### !!! Aviso importante

Asegurese que en el archivo oculto `local.properties` esté definido correctamente el path al sdk de Android

Ejemplo: `sdk.dir=/Users/<your_user>/Library/Android/sdk`

---

### 3. Sincroniza el proyecto

- Asegúrate de que Gradle sincronice correctamente.

- Si ves un aviso para sincronizar el proyecto, haz clic en “Sync Now”

### 4. Configura tu dispositivio

- Puedes utilizar un emulador o conectar un dispositivo Android físico en modo desarrollador.

- Asegúrate de que el dispositivo esté disponible en la lista de dispositivos de ejecución de Android Studio.

### 5. Compila y Corre la Aplicación

- Haz clic en el botón Run (ícono de play verde) o usa el atajo Shift + F10.

- Selecciona el dispositivo donde deseas ejecutar la aplicación.

- Si encuentras errores de dependencias, puedes limpiar y reconstruir el proyecto:


```bash
./gradlew clean build
```

## Resultados de pruebas

### Pruebas Espresso

Para esta entrega se ha definido que el alcance cubriría las historias HU003 y HU004, para ello se hicieron pruebas E2E en Espresso, los archivos se pueden encontrar en `Vinilos-MISW-4203/app/src/androidTest/java/com/app/vinilos_misw4203/PerformerFragmentTest.kt` y en `Vinilos-MISW-4203/app/src/androidTest/java/com/app/vinilos_misw4203/PerformerDetailFragmentTest.kt`

Para correr las pruebas desde Android Studio desde el directorio haga click derecho sobre el archivo y busque la opción que permita ejecutar estas pruebas, al finalizar la ejecución se podrá ver en la parte inferior de la terminal los resultados.

Para evidencias de este sprint, se pueden encontrar los resultados de las pruebas en el siguiente link de la wiki:
https://github.com/dacostaortiz/Vinilos-MISW-4203/wiki/Sprint-2#resultados-de-pruebas-e2e-espresso

### Inspección del código

Resultados después de correr code inspection en Android Studio con lint.
https://github.com/dacostaortiz/Vinilos-MISW-4203/wiki/Sprint-2#code-inspection

Luego de la ejecución de las microptimizaciones se tiene que ya no se tienen alertas de optimización. 
https://github.com/dacostaortiz/Vinilos-MISW-4203/wiki/Sprint-2#despu%C3%A9s-de-micro-optimizaciones

### Mejoras y micro-optimizaciones

Se aprovechó para mejorar el código a través de la remoción de código inutilizado, la actialización de dependencias y librerías, la eliminación de los overdraw.

También se implementaron buenas prácticas como el uso de caché para reducir el tráfico de red.

https://github.com/dacostaortiz/Vinilos-MISW-4203/wiki/Sprint-2#micro-optimizaciones

### Prácticas para evitar AnRs

Se implementaron corrutinas y se mejoró el recyclerView con el uso de DiffUtil. 
https://github.com/dacostaortiz/Vinilos-MISW-4203/wiki/Sprint-2#pr%C3%A1cticas-para-anrs

### Profiler

Se aprovechó el profiler de Android Studio para ejecutar las pruebas de desempeño.
https://github.com/dacostaortiz/Vinilos-MISW-4203/wiki/Sprint-2#profiler

## Autores

- Mariana Diaz - m.diaza2@uniandes.edu.co
- Diego Acosta - d.acostao@uniandes.edu.co