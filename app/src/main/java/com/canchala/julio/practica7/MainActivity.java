package com.canchala.julio.practica7;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.Firebase;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    /*
     DECLARACIONES
     */
    private Firebase mRef;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private CharSequence activityTitle;
    private CharSequence itemTitle;
    private String[] tagTitles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        Firebase.setAndroidContext(this);

        mRef=new Firebase("https://fatto.firebaseio.com/");


        itemTitle = activityTitle = getTitle();
        tagTitles = getResources().getStringArray(R.array.Tags);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        // Setear una sombra sobre el contenido principal cuando el drawer se despliegue
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        //Crear elementos de la lista
        ArrayList<DrawerItem> items = new ArrayList<DrawerItem>();
        items.add(new DrawerItem(tagTitles[0], R.drawable.ic_reloj));
        items.add(new DrawerItem(tagTitles[1], R.drawable.ic_reto));
        items.add(new DrawerItem(tagTitles[2], R.drawable.ic_contacto));
        items.add(new DrawerItem(tagTitles[3], R.drawable.ic_ajuste));
        items.add(new DrawerItem(tagTitles[4], R.drawable.ic_acercade));


        // Relacionar el adaptador y la escucha de la lista del drawer
        drawerList.setAdapter(new DrawerListAdapter(this, items));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        // Habilitar el icono de la app por si hay algun estilo que lo deshabilito
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Crear ActionBarDrawerToggle para la apertura y cierre
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(itemTitle);

                /*Usa este metodo si vas a modificar la action bar
                con cada fragmento
                 */
                //invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(activityTitle);

                /*Usa este metodo si vas a modificar la action bar
                con cada fragmento
                 */
                //invalidateOptionsMenu();
            }
        };
        //Seteamos la escucha
        drawerLayout.setDrawerListener(drawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            // Toma los eventos de seleccion del toggle aqui
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* La escucha del ListView en el Drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // Reemplazar el contenido del layout principal por un fragmento

        Bundle extras=getIntent().getExtras();

        String name=extras.getString("nombre");
        String ema=extras.getString("email");

        FragmentManager fragmentManager = getSupportFragmentManager();


        switch (position)
        {
            case 0:
                Alarmas fragmenta=new Alarmas();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmenta).commit();
                break;
            case 1:
                Retoss fragmentb=new Retoss();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentb).commit();
                break;
            case 2:
                Contactos fragmentc=new Contactos();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentc).commit();
                break;
            case 3:
                Ajustes fragmentd=new Ajustes();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentd).commit();
                break;
            case 4:
                Info fragmente=new Info();

                Bundle args=new Bundle();
                args.putString("nam", name);
                args.putString("cor", ema);

                fragmente.setArguments(args);


                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmente).commit();

                break;
            default:
                ArticleFragment fragmentz = new ArticleFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentz).commit();
        }


        // Se actualiza el item seleccionado y el titulo, despues de cerrar el drawer
        drawerList.setItemChecked(position, true);
        setTitle(tagTitles[position]);
        drawerLayout.closeDrawer(drawerList);
    }

    /* Metodo auxiliar para setear el titulo de la action bar */
    @Override
    public void setTitle(CharSequence title) {
        itemTitle = title;
        getSupportActionBar().setTitle(itemTitle);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sincronizar el estado del drawer
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Cambiar las configuraciones del drawer si hubo modificaciones
        drawerToggle.onConfigurationChanged(newConfig);
    }
}