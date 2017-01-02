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

   		* crear ShopDAO vacía en paquete com.agbotraining.everpobre.model.dao;
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
					* explicar import static

## Probando el modelo de datos 

   	* Qué son las pruebas de unidad
   	* Tipos de pruebas de unidad: JUnit y Android Test Case
   	* tests en src > androidTest
   		* crear configuración ejecución para lanzar los tests
   		* escribir los tests de unidad
			* clase de test de unidad Android: AndroidTestCase
			* crear ShopTests
			* explicar signatura métodos de test
			* lanzar proyecto de test: ver tests pasados
			* crear tests desde clase, añadir métodos desde test
			* crear ShopsTests, para probar agregado


		* Esto no es TDD
		* qué debemos probar: modelo en memoria, red y acceso a BD al menos
		* cómo debemos pensar para escribir los tests de unidad
			* primero pensar en cómo queremos que se comporten nuestras clases, luego "ejercitarlas" y comprobar que se comportan como esperamos
	* crear ShopDAOTests
		* test podemos insertar
		* contar registros
		* extraer fichero sqlite del simulador y explorar
			* abrir perspectiva DDMS
			* pull fichero
			* usar SQLite Studio
		* test delete
			* un test no depende del resto. Explicar
		* test deleteAll



## Mostrando un AdapterView con todos los Notebooks
	
* add to `build.gradle` 

```
    compile 'com.android.support:recyclerview-v7:24.2.1'
```

* create ShopRowViewHolder
* create row_shop.xml layout file
	* relative layout: los campos no deben estar "en orden"
	* tools: namespace
	* extraer los estilos al fichero de styles

* El control recyclerview
	
```
    <android.support.v7.widget.RecyclerView
        android:id="@+id/shops_recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    </android.support.v7.widget.RecyclerView>

```

* create Adapter using ViewHolder
public class ShopsAdapter extends RecyclerView.Adapter<ShopRowViewHolder> {
		* esta clase podría testearse :-D

* añadirmos una variable adapter
	* en onCreate del fragment, establecer el adapter
	
* añadir Listener
			
* debemos cargar algunos Notebooks de prueba al iniciar MainActivity
* en MainActivity necesitamos:
	* recuperar el Fragmento
	* obtener un cursor con todos los Notebooks
	* crear un adapter
* el fragment se carga "solo" al estar en el layout de mainActivity
	* se hace en Hilo Main!


## Touch each row

- add listener to adapter
- extract listener interface to own file
- add listener to ShopsFragment
- pass click back to activity
- add constants to create intent method in Navigator 	

```java
public static Intent navigateFromShopsActivityToShopDetailActivity(final ShopsActivity shopsActivity, Shop detail) {
	final Intent i = new Intent(shopsActivity, ShopDetailActivity.class);

	i.putExtra(Constants.INTENT_KEY_SHOP_DETAIL, detail);

	shopsActivity.startActivity(i);

	return i;
}

```		

- We need to make our model implement Serializable
- Serializable vs Parcelable

--- v0.3


## Content Provider / Cursor Loaders

- Mandamiento: no accederás a DB en el hilo main
	* ANR: Android Not Responding y bases de datos
	* El diálogo que debemos siempre evitar.
	* Lanzar tareas en segundo plano con AsyncTask _repaso_
	* podemos usar AsyncTask a mano, perfecto para bases de datos que no se se van a usar fuera de nuestra App
	* no lo vamos a hacer: ejercicio para el lector

* Creating our own Content provider
	* qué es un Content Provider
	* ¿Por qué lo necesitamos si ya tenemos los DAO? --> para usar un Cursor Loader
	* un Provider es como una API REST en nuestro programa Android
		* content://io.keepcoding.everpobre.provider/notebooks
		* content://io.keepcoding.everpobre.provider/notes
		
- add to Manifest.xml
(dentro de Application)
	`<provider android:name=".provider.EverpobreProvider" android:authorities="com.agbotraining.everpobre.provider"/>` 
	* definir URI provider y principales (NOTEBOOKS y NOTES)
	* añadir UriMatcher
	* escribir onCreate
	* insert
	* query
	* delete
	* update
	* getType
	* Escribir nuevos tests de unidad del content provider
	* añadir métodos estáticos de conveniencia
	* añadir tests de estos métodos estáticos 

- add to ShopsActivity

```
public class ShopsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

...

@Override
public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
	return null;
}

@Override
public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

}

@Override
public void onLoaderReset(Loader<Cursor> loader) {

}


```

* Añadir en onActivityCreated:

```
LoaderManager loader = getLoaderManager();
loader.initLoader(0, null, this);
```

- delete all code loading data on the Main Thread


--- v0.4


# Create Model from Web

- ShopResponse
- ShopEntity

# Create mappers

- Mapper.map( <Model>, <Entity>)


## Create Interactors
	* what is an Interactor
	* how to model the API
	* always call back on Main Thread
	* create MainThread class

- remove Cursor Loaders calls
- use interactors to fetch & cache data

--- v0.5


