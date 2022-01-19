package br.com.paisdavizinhanca.paisdavizinhanca;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class TelaInicial extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtNome, txtEmail, id;

    NavigationView nav_header ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicial);

        //nomeUsuario = getIntent().getExtras().getString("nome_usuario");
        //emailUsuario = getIntent().getExtras().getString("email_usuario");
        //idUsuario = getIntent().getExtras().getString("id_usuario");

        nav_header = (NavigationView) findViewById(R.id.nav_view);
        nav_header.setNavigationItemSelectedListener(this);
        View header = nav_header.getHeaderView(0);

        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        txtNome = (TextView) header.findViewById(R.id.txtNome);
        txtEmail = (TextView) header.findViewById(R.id.txtEmail);
        id = (TextView) header.findViewById(R.id.idUsuario);
        txtNome.setText(User.getInstance(this).getUsername());
        txtEmail.setText(User.getInstance(this).getUserEmail());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageView play = findViewById(R.id.imageView1);
        play.setImageResource(R.drawable.playkid);
        AnimationDrawable playkid = (AnimationDrawable) play.getDrawable();
        playkid.start();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tela_inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_logout){
            User.getInstance(this).logout();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.pesquisarGrupo) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flmain, new PesquisarGrupo());
            ft.commit();

        } else if (id == R.id.addGrupo) {

            Intent abreCriaGrupo = new Intent(TelaInicial.this, TelaCriarGrupo.class);
            startActivity(abreCriaGrupo);

        } else if (id == R.id.viewGrupo) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flmain, new GruposOnline());
            ft.commit();

        } else if (id == R.id.mostrarMap) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
