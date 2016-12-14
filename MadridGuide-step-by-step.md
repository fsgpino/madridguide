# Madrid Guide step by step


## Presentar el problema que queremos solucionar
	
* What is Madrid Guide?
* Show What is Madrid Guide
* Show screen captures

## Creando el Proyecto

* 💻 crear el proyecto `MadridGuide` en Android Studio
	* crear una nueva app android
		* project name: `MadridGuide` 
	* io.keepcoding.madridguide: empresa keepcoding.io
	* API 14 - API 22 (help me choose)
	* no initial activity
    * explain AS views - change to project view
    * uncheck show empty middle package names
* Setup git
    * add .gitignore
	* Podemos bajar un `.gitignore` de [http://gitignore.io]()
	* crear git rama desarrollo usando SourceTree + gitflow

* Add basic libraries
	* usar gradleplease

```
	compile 'com.jakewharton:butterknife:8.4.0'
  	annotationProcessor 'com.jakewharton:butterknife-compiler:8.4.0'  compile 'com.squareup.picasso:picasso:2.5.2'
    compile 'com.google.code.gson:gson:2.8.0'
    compile 'com.android.volley:volley:1.0.0'
```

## Clase Application

* Añadir clase Application 
	* necesaria para inicializar el modelo, responder a situaciones de memoria baja, etc.
	* Crear clase extiende de Application, MadridGuideApp
	* en el paquete de la App: io.keepcoding.madridguide
	* sobreescribir onCreate
	* quedarnos con una ref weak estática al contexto de la App. Explicar WeakReferences
	* definir en AndroidManifest `android:name="com.agbotraining.everpobre.MadridGuideApp"`
	* crear clase util.Constants para clave Logs de la App

* separar temas de estilos en dos ficheros
* establecer el estilo de la App en el AndroidManifest.xml: 		android:theme="@style/MadridGuideTheme"

* crear la estructura básica de menús y actividades vacías (right now we can't even launch the App - no main activity)
	* crear MainActivity como launcher activity
	* en paquete activities (io.keepcoding.madridguide.activities)
	* crear la Actividad para Shops: ShopsActivity
	* Lanzar ShopsActivity desde botón
	* Usar butterknife para inyectar vistas: quedan como outlets de iOS


## Acceso a BB.DD. con Android: SQLite

* SQLite: una base de datos en un fichero
* Formas de acceder a BB.DD. en Android
### El modelo de MadridGuide
   	* Crear el Modelo de datos a mano
   		* Crear clase Shop en paquete `model`
   			* añadir campos, generar getters y setters, constructor con name
			* create chained setters
			   
```			   
private long id;
private String name;
private String imgUrl;
private String logoImgUrl;
private String address;
private String url;
```			   
			   
   			* id debe ser long ([http://sqlite.org/autoinc.html]())  ⚡️
		* Aggregate Shops to not use directly a List
			* split in two interfaces: one for updating, the other for iterating
			* add build pattern
			* use NonNull / Nullable annotations - explicar

* Crear DBConstants
* Crear el DB Helper (esqueleto, vacío) dentro del paquete manager.db
		   * ¿usar contexto de la App en DB? Esta clase es reusable: nos acoplaríamos innecesariamente
				* añadir métodos para convertir datos a DBHelper (convertir entre tipos)
				* añadir a DBHelper getInstance para construirlo de forma estática (factory estático, que no singleton)
			* añadir scripts de creación de la DB a NotebookDAO y a DBHelper
				* no olvidar añadir FOREIGN KEY a parte N de la relación, para delete on cascade
				* añadir a DBHelper método onOpen para establecer el PRAGMA (delete on cascade)


## DAOs

   		* crear NotebookDAO vacía en paquete com.agbotraining.everpobre.model.dao;
		   (ir pasando código de los pedazos)
		   
   				* insert:
   					* getWritableDatabase()
   					* opciones del INSERT
   				* update
   				* delete, deleteAll
   					* ociones delete
   				* query, queryCursor
   					* getReadableDatabase
   					* diferencias de query frente a execDB
   					* rawQuery
   				* añadir id a Notebook: debe ser Long: leer id en clase NotebookDAO
   				* añadir id a Note: debe ser Long		
   					
				* crear interfaz DAOPersistable para generalizar los DAOs
				* interfaz genérico, explicar
				
```
public interface DAOPersistable<T> {
    long insert(@NonNull T data);
    void update(long id, @NonNull T data);
    void delete(long id);
    void deleteAll();
    @Nullable Cursor queryCursor();
    T query(long id);
}

```
				
				* hacer weak el contexto

# Create Model from Web

- ShopResponse
- ShopEntity

# Create mappers

- Mapper.map( <Model>, <Entity>)